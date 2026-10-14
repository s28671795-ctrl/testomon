/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.listeners;

import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.AnalyticsStatus;
import com.incoresoft.vezha.analytics.AnalyticsManagerImpl;
import com.incoresoft.vezha.analytics.BaseAnalytics;
import com.incoresoft.vezha.analytics.cameraanalytics.InitializationRequester;
import com.incoresoft.vezha.analytics.servers.Server;
import com.incoresoft.vezha.analytics.servers.ServersMonitor;
import com.incoresoft.vezha.analytics.servers.listeners.ServerActionListener;
import com.incoresoft.vezha.analytics.servers.models.RunningAnalytics;
import com.incoresoft.vezha.analytics.status.AnalyticsHeartBeat;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerActionListenerImpl
implements ServerActionListener {
    private static final Logger log = LoggerFactory.getLogger(ServerActionListenerImpl.class);
    private final Map<String, Instant> lastHeartBeat = new ConcurrentHashMap<String, Instant>();
    private final AnalyticsManagerImpl analyticsManager;
    private final InitializationRequester initializationRequester;
    private final ServersMonitor serversMonitor;

    public ServerActionListenerImpl(@NotNull AnalyticsManagerImpl analyticsManager, @NotNull InitializationRequester initializationRequester, @NotNull ServersMonitor serversMonitor) {
        this.analyticsManager = Objects.requireNonNull(analyticsManager);
        this.initializationRequester = Objects.requireNonNull(initializationRequester);
        this.serversMonitor = Objects.requireNonNull(serversMonitor);
    }

    @Override
    public void onNewServer(Server server) {
        this.initializationRequester.requestInitialization(server.getId());
        this.serversMonitor.sendCurrentStorageSettings(server.getId());
    }

    @Override
    public void onServerStart(Server server) {
        log.info("SERVER: start id " + server.getId());
        Set runningAnalyticsIds = server.getRunningAnalytics().stream().map(RunningAnalytics::getId).collect(Collectors.toSet());
        this.analyticsManager.getAll().stream().filter(it -> it.getStatus() != AnalyticsStatus.stopped).filter(it -> it.getHardwareInfo() != null).filter(it -> it.getHardwareInfo().getAnalyticsServer().getId().equals(server.getId())).map(Analytics::getId).filter(it -> !runningAnalyticsIds.contains(it)).map(this.analyticsManager::get).filter(Objects::nonNull).map(BaseAnalytics.class::cast).forEach(this.analyticsManager::enqueue);
        this.analyticsManager.getAll().stream().filter(it -> it.getStatus() != AnalyticsStatus.stopped).filter(it -> it.getHardwareInfo() == null).map(Analytics::getId).filter(it -> !runningAnalyticsIds.contains(it)).map(this.analyticsManager::get).filter(Objects::nonNull).map(BaseAnalytics.class::cast).forEach(this.analyticsManager::enqueue);
        this.initializationRequester.requestInitialization(server.getId());
        this.serversMonitor.sendCurrentStorageSettings(server.getId());
    }

    @Override
    public void onServerDeath(Server server) {
        log.info("ANALYTICS: server " + server.getId() + "died");
        server.getRunningAnalytics().stream().map(RunningAnalytics::getId).map(this.analyticsManager::get).filter(Objects::nonNull).map(BaseAnalytics.class::cast).forEach(this.analyticsManager::enqueue);
    }

    @Override
    public void onServerHeartBeat(Server server) {
        long diff;
        long timestamp = Instant.now().getEpochSecond();
        long step1 = 0L;
        long step2 = 0L;
        long step3 = 0L;
        long step4 = 0L;
        if (!this.lastHeartBeat.containsKey(server.getId()) || this.lastHeartBeat.get(server.getId()).plusSeconds(1L).isBefore(Instant.now())) {
            this.lastHeartBeat.put(server.getId(), Instant.now());
            server.getRunningAnalytics().stream().map(RunningAnalytics::getId).filter(id -> this.analyticsManager.get((Integer)id) == null).forEach(id -> this.analyticsManager.removeAndStop((int)id, server));
            step1 = Instant.now().getEpochSecond() - timestamp;
            server.getRunningAnalytics().stream().map(RunningAnalytics::getId).map(this.analyticsManager::get).filter(Objects::nonNull).map(BaseAnalytics.class::cast).forEach(it -> it.setHeartBeatValue(new AnalyticsHeartBeat(server.getId(), server.analyticsGpuId(it.getId()))));
            step2 = Instant.now().getEpochSecond() - timestamp;
            server.getRunningAnalytics().stream().map(RunningAnalytics::getId).map(this.analyticsManager::get).filter(Objects::nonNull).map(BaseAnalytics.class::cast).filter(it -> it.getAnalyticsStatus().getServerValue() != null).filter(it -> it.getAnalyticsStatus().getServerValue().getId().equals(server.getId())).forEach(it -> it.getAnalyticsStatus().setServerValue(server));
            step3 = Instant.now().getEpochSecond() - timestamp;
            server.getRunningAnalytics().stream().filter(it -> this.analyticsManager.get(it.getId()) != null).forEach(it -> this.analyticsManager.setAnalyticsSideErrors((BaseAnalytics)this.analyticsManager.get(it.getId()), it.getErrors()));
            step4 = Instant.now().getEpochSecond() - timestamp;
        }
        if ((diff = Instant.now().getEpochSecond() - timestamp) > 10L) {
            log.warn("Heartbeat took " + diff + " seconds");
            log.warn("step1: " + step1);
            log.warn("step2: " + step2);
            log.warn("step3: " + step3);
            log.warn("step4: " + step4);
        }
    }
}

