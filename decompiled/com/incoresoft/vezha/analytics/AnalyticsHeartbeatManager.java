/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics;

import com.incoresoft.middleware.analytics.AnalyticsStatus;
import com.incoresoft.vezha.analytics.AnalyticsManagerImpl;
import com.incoresoft.vezha.analytics.BaseAnalytics;
import com.incoresoft.vezha.analytics.servers.Server;
import com.incoresoft.vezha.analytics.servers.ServersMonitor;
import com.incoresoft.vezha.analytics.servers.models.GpuCard;
import com.incoresoft.vezha.config.VezhaConfigManager;
import com.incoresoft.vezha.config.models.VezhaConfig;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnalyticsHeartbeatManager
implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(AnalyticsHeartbeatManager.class);
    private static final Duration OVERLOAD_PAUSE_DELAY = Duration.ofSeconds(3L);
    private final AnalyticsManagerImpl analyticsManager;
    private final VezhaConfigManager configManager;
    private final ServersMonitor serversMonitor;
    private final AtomicInteger counter = new AtomicInteger(0);
    private int lastRestartedAnalyticsId = -1;
    private final ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor(r -> {
        Thread t2 = new Thread(r, "analytics-hb-timer");
        t2.setDaemon(true);
        return t2;
    });
    private final ExecutorService vexec = Executors.newVirtualThreadPerTaskExecutor();

    @Override
    public void run() {
        this.performSafe(this::checkStoppedAnalytics, "checkStoppedAnalytics");
        this.performSafe(this::checkWrongSignature, "checkWrongSignature");
        this.performSafe(this::processingAnalytics, "processingAnalytics");
        this.performSafe(this::updateWrongServer, "wrongServerUpdate");
        this.performSafe(this::updateHeartbeatTimeout, "heartbeatTimeoutUpdate");
        this.performSafe(this::pauseAnalytics, "pausedAnalytics");
        this.performSafe(this::checkServerOverload, "checkServerOverload");
    }

    private void performSafe(Runnable action, String errorLogMessage) {
        try {
            action.run();
        }
        catch (Exception e) {
            log.error(errorLogMessage, e);
        }
    }

    private List<BaseAnalytics> getFilteredAnalytics(Predicate<BaseAnalytics> predicate) {
        return this.analyticsManager.getAll().stream().map(it -> (BaseAnalytics)it).filter(it -> it.getHeartBeatValue() != null).filter(it -> it.getAnalyticsStatus() != null).filter(predicate).toList();
    }

    private void checkStoppedAnalytics() {
        List<BaseAnalytics> list = this.getFilteredAnalytics(it -> it.getStatus() == AnalyticsStatus.stopped);
        list.forEach(this.analyticsManager::removeAndStop);
    }

    private void checkWrongSignature() {
        List<BaseAnalytics> list = this.getFilteredAnalytics(it -> it.getStatus() == AnalyticsStatus.started && it.getAnalyticsStatus().getStartSignature() == null);
        list.forEach(this.analyticsManager::updateSignature);
    }

    private void processingAnalytics() {
        List<BaseAnalytics> list = this.getFilteredAnalytics(it -> it.getStatus() == AnalyticsStatus.processing && this.isConfirmedByServer((BaseAnalytics)it));
        list.forEach(this.analyticsManager::setStartedStatus);
    }

    private boolean isConfirmedByServer(BaseAnalytics analytics) {
        Server server = this.serversMonitor.getServerById(analytics.getHeartBeatValue().getServerId());
        return server != null && server.getRunningAnalyticsMap().containsKey(analytics.getId());
    }

    private void pauseAnalytics() {
        List<BaseAnalytics> list = this.getFilteredAnalytics(it -> it.getStatus() == AnalyticsStatus.paused && it.getAnalyticsStatus().getServer() == null);
        list.forEach(this.analyticsManager::enqueue);
    }

    private void updateWrongServer() {
        List<BaseAnalytics> list = this.getFilteredAnalytics(it -> it.getStatus() != AnalyticsStatus.stopped && it.isServerWrong());
        list.forEach(this.analyticsManager::updateServer);
    }

    private void updateHeartbeatTimeout() {
        List<BaseAnalytics> list = this.getFilteredAnalytics(it -> it.getStatus() != AnalyticsStatus.stopped && it.isHeartbeatTimeout());
        list.forEach(this.analyticsManager::enqueue);
    }

    private void checkServerOverload() {
        if (!((VezhaConfig)this.configManager.getConfig()).getLoadBalancer().isCheckServerOverloaded()) {
            return;
        }
        if (this.counter.incrementAndGet() <= 60) {
            return;
        }
        this.counter.set(0);
        this.serversMonitor.getServers().values().stream().filter(server -> server.getGraphics().values().stream().anyMatch(this::isGPUOverloaded)).findFirst().ifPresent(server -> server.getGraphics().values().stream().filter(this::isGPUOverloaded).findFirst().ifPresent(gpu -> {
            log.info("Server {}: GPU {} is overloaded (usage={}%, nvDec={}, freeMem={} GB), will try to move one analytics off it", server.getId(), gpu.getId(), gpu.getGpuUsage(), gpu.getNvDec(), gpu.getFreeMemory().get());
            this.scheduleToRestartAnalytics(server.getId(), gpu.getId());
        }));
    }

    private void scheduleToRestartAnalytics(String serverId, int gpuId) {
        List<BaseAnalytics> analytics = this.getFilteredAnalytics(it -> it.getStatus() == AnalyticsStatus.started && it.getAnalyticsStatus().getServerValue() != null && Objects.equals(it.getAnalyticsStatus().getServerValue().getId(), serverId) && Objects.equals(it.getAnalyticsStatus().getLastGpuIdValue(), gpuId));
        int previousVictim = this.lastRestartedAnalyticsId;
        BaseAnalytics analyticsToStop = analytics.stream().filter(it -> it.getId() != previousVictim).findFirst().orElseGet(() -> analytics.stream().findFirst().orElse(null));
        if (analyticsToStop == null) {
            log.debug("Server {} GPU {} is overloaded but no started analytics is pinned to it, nothing to move", (Object)serverId, (Object)gpuId);
            return;
        }
        this.lastRestartedAnalyticsId = analyticsToStop.getId();
        this.timer.schedule(() -> this.vexec.submit(() -> {
            try {
                log.info("Restarting analytics {} on server {} due to GPU overload (gpuId={})", analyticsToStop.getId(), serverId, gpuId);
                this.analyticsManager.stop(analyticsToStop.getId());
                this.analyticsManager.start(analyticsToStop.getId());
            }
            catch (Throwable e) {
                log.error("Failed to pause analytics {} due to overload", (Object)analyticsToStop.getId(), (Object)e);
            }
        }), OVERLOAD_PAUSE_DELAY.toMillis(), TimeUnit.MILLISECONDS);
    }

    private boolean isGPUOverloaded(GpuCard gpu) {
        return gpu.getGpuUsage() > 99.0 || gpu.getNvDec() > 95.0 || gpu.getFreeMemory().get() < Math.min(0.5, gpu.getTotalMem() * 0.1);
    }

    public AnalyticsHeartbeatManager(AnalyticsManagerImpl analyticsManager, VezhaConfigManager configManager, ServersMonitor serversMonitor) {
        this.analyticsManager = analyticsManager;
        this.configManager = configManager;
        this.serversMonitor = serversMonitor;
    }
}

