/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics;

import com.alibaba.fastjson.JSON;
import com.google.inject.Provider;
import com.incoresoft.config.ConfigManager;
import com.incoresoft.license.model.LicenseGrabStatus;
import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.AnalyticsStatus;
import com.incoresoft.middleware.analytics.state.AnalyticsStateListener;
import com.incoresoft.middleware.http.exceptions.ServerNotFoundException;
import com.incoresoft.middleware.mq.MessageQueueConnector;
import com.incoresoft.system.disk.DiskSpaceManager;
import com.incoresoft.vezha.analytics.AnalyticsManagerImpl;
import com.incoresoft.vezha.analytics.BaseAnalytics;
import com.incoresoft.vezha.analytics.errors.CoreAnalyticsErrors;
import com.incoresoft.vezha.analytics.models.AnalyticsParamsAction;
import com.incoresoft.vezha.analytics.models.AnalyticsStartParams;
import com.incoresoft.vezha.analytics.servers.GpuMemoryReserveCalculator;
import com.incoresoft.vezha.analytics.servers.Server;
import com.incoresoft.vezha.analytics.servers.ServerBusyException;
import com.incoresoft.vezha.analytics.servers.ServersMonitor;
import com.incoresoft.vezha.analytics.servers.dto.RunningAnalyticsDTO;
import com.incoresoft.vezha.analytics.servers.models.RunningAnalytics;
import com.incoresoft.vezha.analytics.status.AnalyticsHeartBeat;
import com.incoresoft.vezha.analytics.status.AnalyticsStatusImpl;
import com.incoresoft.vezha.config.models.VezhaConfig;
import com.incoresoft.vezha.license.LicenseUsageManager;
import com.incoresoft.vezha.protocol.ws.AnalyticsWSBroadcaster;
import com.incoresoft.vezha.ws.analytics.events.AnalyticsServerErrorsEvent;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnalyticsQueue
implements Runnable,
AnalyticsStateListener {
    private static final Logger log = LoggerFactory.getLogger(AnalyticsQueue.class);
    private final ConcurrentLinkedQueue<Analytics> analyticsQueue = new ConcurrentLinkedQueue();
    private final MessageQueueConnector mqConnector;
    private final AnalyticsManagerImpl analyticsManager;
    private final ServersMonitor serversMonitor;
    private final LicenseUsageManager licenseUsageManager;
    private final Provider<DiskSpaceManager> diskSpaceManagerProvider;
    private final ConfigManager<VezhaConfig> configManager;
    private final AnalyticsWSBroadcaster analyticsWSBroadcaster;
    private final GpuMemoryReserveCalculator gpuMemoryReserveCalculator;

    @Override
    public void run() {
        try {
            this.processAnalytics(false);
        }
        catch (Throwable e) {
            log.error("Error in AnalyticsQueue", e);
        }
    }

    public void processAnalytics(boolean block) {
        if (!block && this.analyticsQueue.isEmpty()) {
            return;
        }
        Analytics polled = this.analyticsQueue.poll();
        BaseAnalytics analytics = this.castToBaseAnalytics(polled);
        if (analytics == null) {
            return;
        }
        if (analytics.getStatus() == AnalyticsStatus.started) {
            return;
        }
        if (analytics.getStatus() == AnalyticsStatus.paused) {
            return;
        }
        try {
            Server optimalServer = this.serversMonitor.getOptimalServer(analytics);
            this.sendStartAnalytics(analytics, optimalServer);
        }
        catch (ServerBusyException e) {
            log.debug("All servers are within the start debounce for analytics {}, retrying", (Object)analytics.getId());
            this.offer(analytics);
        }
        catch (ServerNotFoundException e) {
            log.debug("No server can hold analytics {}, re-queueing", (Object)analytics.getId());
            this.offer(analytics);
            this.reportNoAvailableServer(analytics);
        }
        catch (Exception e) {
            log.error("Failed to start analytics {}", (Object)analytics.getId(), (Object)e);
            this.offer(analytics);
            this.reportNoAvailableServer(analytics);
        }
    }

    private void reportNoAvailableServer(BaseAnalytics analytics) {
        if (!analytics.hasError(CoreAnalyticsErrors.NO_AVAILABLE_SERVER)) {
            analytics.addErrorIfAbsent(CoreAnalyticsErrors.NO_AVAILABLE_SERVER);
            this.analyticsWSBroadcaster.broadcast(new AnalyticsServerErrorsEvent(analytics), user -> user.hasAccessTo(analytics));
        }
    }

    public int size() {
        return this.analyticsQueue.size();
    }

    public void offer(Analytics analytics) {
        BaseAnalytics base = this.castToBaseAnalytics(analytics);
        if (base == null) {
            return;
        }
        if (this.analyticsQueue.stream().noneMatch(it -> it.getId() == base.getId())) {
            this.analyticsQueue.offer(base);
        }
    }

    public boolean remove(Analytics analytics) {
        BaseAnalytics base = this.castToBaseAnalytics(analytics);
        if (base == null) {
            return false;
        }
        return this.analyticsQueue.remove(base);
    }

    public void removeAndStop(Analytics analytics) {
        this.remove(analytics);
        this.sendStopAnalytics(analytics);
    }

    public void removeAndStop(int id, Server server) {
        this.analyticsQueue.removeIf(it -> it.getId() == id);
        this.gpuMemoryReserveCalculator.clearPlacement(id);
        this.publishToNATS(server.getServerTopic(), new AnalyticsStartParams(AnalyticsParamsAction.stop, id));
    }

    public void removeAndStop(Analytics analytics, Server server) {
        this.remove(analytics);
        this.sendStopAnalytics(analytics, server);
    }

    public void updateSignature(BaseAnalytics analytics) {
        if (analytics.getAnalyticsStatus().getServerValue() == null) {
            return;
        }
        if (analytics.getAnalyticsStatus().getStartSignature() == null) {
            this.analyticsManager.initEncryption(analytics);
        }
        this.publishToNATS(analytics.getAnalyticsStatus().getServerValue().getServerTopic(), new AnalyticsStartParams(AnalyticsParamsAction.update, analytics));
    }

    private void sendStartAnalytics(Analytics inAnalytics, Server server) {
        BaseAnalytics analytics = this.castToBaseAnalytics(inAnalytics);
        if (analytics == null) {
            return;
        }
        int optimalGPU = server.getOptimalGpu(analytics, this.configManager.getConfig().getLoadBalancer(), this.gpuMemoryReserveCalculator.forServer(server));
        this.sendStartAnalytics(inAnalytics, server, optimalGPU);
    }

    private void sendStartAnalytics(Analytics inAnalytics, Server server, int optimalGPU) {
        BaseAnalytics analytics = this.castToBaseAnalytics(inAnalytics);
        if (analytics == null) {
            return;
        }
        boolean licenseHeld = false;
        try {
            boolean errorsFound = false;
            if (this.blockedByLowDiskSpace()) {
                analytics.addErrorIfAbsent(CoreAnalyticsErrors.NO_SPACE_ON_DISK);
                errorsFound = true;
            }
            if (this.licenseUsageManager.grabLicense(analytics) == LicenseGrabStatus.LICENSED) {
                licenseHeld = true;
            } else {
                analytics.addErrorIfAbsent(CoreAnalyticsErrors.NO_AVAILABLE_LICENSE);
                errorsFound = true;
            }
            if (errorsFound) {
                if (licenseHeld) {
                    this.licenseUsageManager.releaseLicense(analytics);
                    licenseHeld = false;
                }
                this.offer(analytics);
                this.analyticsWSBroadcaster.broadcast(new AnalyticsServerErrorsEvent(analytics), user -> user.hasAccessTo(analytics));
                return;
            }
            analytics.removeErrorIfPresent(CoreAnalyticsErrors.NO_SPACE_ON_DISK);
            analytics.removeErrorIfPresent(CoreAnalyticsErrors.NO_AVAILABLE_LICENSE);
            Optional.ofNullable(analytics.getAnalyticsStatus()).ifPresent(it -> it.setLastGpuIdValue(optimalGPU));
            if (this.isBlankSignature(analytics)) {
                analytics.initEncryptionKey();
            }
            log.info("Starting analytics {} on server {}, GPU {}", analytics.getId(), server.getId(), optimalGPU);
            this.publishToNATS(server.getServerTopic(), new AnalyticsStartParams(AnalyticsParamsAction.start, analytics));
            licenseHeld = false;
            boolean embedded = this.analyticsManager.isEmbeddedStream(analytics);
            if (optimalGPU >= 0 && !embedded) {
                this.gpuMemoryReserveCalculator.recordPlacement(analytics, server.getId(), optimalGPU);
            }
            analytics.setHeartBeatValue(new AnalyticsHeartBeat(server.getId(), optimalGPU));
            this.updateServersMonitorSafe(analytics, server, optimalGPU, embedded);
        }
        catch (RuntimeException e) {
            if (licenseHeld) {
                this.licenseUsageManager.releaseLicense(analytics);
            }
            throw e;
        }
    }

    public boolean blockedByLowDiskSpace() {
        return this.configManager.getConfig().isCheckDiskSpace() && this.diskSpaceManagerProvider.get().isStorageDiskSpaceLow();
    }

    private boolean isBlankSignature(BaseAnalytics analytics) {
        return Optional.ofNullable(analytics.getAnalyticsStatus()).map(AnalyticsStatusImpl::getStartSignatureValue).map(String::isBlank).orElse(true);
    }

    private void updateServersMonitorSafe(BaseAnalytics analytics, Server server, int optimalGPU, boolean embedded) {
        Server monitored = this.serversMonitor.getServerById(server.getId());
        if (monitored == null) {
            log.warn("Server {} not found in ServersMonitor during updateServersMonitor()", (Object)server.getId());
            return;
        }
        monitored.getRunningAnalyticsMap().put(analytics.getId(), new RunningAnalytics(new RunningAnalyticsDTO(analytics.getId(), 0L, optimalGPU, Collections.emptySet(), null, null)));
        if (optimalGPU >= 0 && !embedded) {
            monitored.updateLastGpuRunTime(optimalGPU);
        }
    }

    private void publishToNATS(String topic, AnalyticsStartParams params) {
        byte[] data = JSON.toJSONString(params).getBytes(StandardCharsets.UTF_8);
        this.mqConnector.publish(topic, data);
    }

    private void sendStopAnalytics(Analytics analytics) {
        Optional.ofNullable(this.castToBaseAnalytics(analytics)).map(BaseAnalytics::getAnalyticsStatus).map(AnalyticsStatusImpl::getServerValue).ifPresent(server -> this.sendStopAnalytics(analytics, (Server)server));
    }

    private void sendStopAnalytics(Analytics analytics, Server server) {
        this.gpuMemoryReserveCalculator.clearPlacement(analytics.getId());
        this.publishToNATS(server.getServerTopic(), new AnalyticsStartParams(AnalyticsParamsAction.stop, this.castToBaseAnalytics(analytics)));
    }

    @Override
    public void onAnalyticsStarted(Analytics analytics) {
        this.analyticsManager.initEncryption(analytics);
        BaseAnalytics base = this.castToBaseAnalytics(analytics);
        if (base == null) {
            return;
        }
        if (base.hasError(CoreAnalyticsErrors.NO_AVAILABLE_SERVER)) {
            base.removeErrorIfPresent(CoreAnalyticsErrors.NO_AVAILABLE_SERVER);
            this.analyticsWSBroadcaster.broadcast(new AnalyticsServerErrorsEvent(base), user -> user.hasAccessTo(base));
        }
        this.remove(base);
    }

    @Override
    public void onAnalyticsPending(Analytics analytics) {
        this.analyticsManager.initEncryption(analytics);
    }

    @Override
    public void onAnalyticsPaused(Analytics analytics) {
        this.sendStopAnalytics(analytics);
    }

    @Override
    public void onAnalyticsStopped(Analytics analytics) {
        this.removeAndStop(analytics);
    }

    private BaseAnalytics castToBaseAnalytics(Analytics analytics) {
        return analytics instanceof BaseAnalytics ? (BaseAnalytics)analytics : null;
    }

    public AnalyticsQueue(MessageQueueConnector mqConnector, AnalyticsManagerImpl analyticsManager, ServersMonitor serversMonitor, LicenseUsageManager licenseUsageManager, Provider<DiskSpaceManager> diskSpaceManagerProvider, ConfigManager<VezhaConfig> configManager, AnalyticsWSBroadcaster analyticsWSBroadcaster, GpuMemoryReserveCalculator gpuMemoryReserveCalculator) {
        this.mqConnector = mqConnector;
        this.analyticsManager = analyticsManager;
        this.serversMonitor = serversMonitor;
        this.licenseUsageManager = licenseUsageManager;
        this.diskSpaceManagerProvider = diskSpaceManagerProvider;
        this.configManager = configManager;
        this.analyticsWSBroadcaster = analyticsWSBroadcaster;
        this.gpuMemoryReserveCalculator = gpuMemoryReserveCalculator;
    }
}

