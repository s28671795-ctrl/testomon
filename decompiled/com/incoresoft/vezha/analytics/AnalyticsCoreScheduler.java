/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics;

import com.google.inject.Inject;
import com.incoresoft.loading.AppLoadingListener;
import com.incoresoft.loading.LoadingStage;
import com.incoresoft.vezha.analytics.servers.ServersMonitor;
import com.incoresoft.vezha.config.VezhaConfigManager;
import com.incoresoft.vezha.config.models.VezhaConfig;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

public class AnalyticsCoreScheduler {
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(new BasicThreadFactory.Builder().namingPattern("AnalyticsCoreScheduler-%d").build());
    private final ServersMonitor serversMonitor;
    private final VezhaConfigManager configManager;
    private boolean started;
    private Runnable analyticsLoadingFinishCallback;

    public void start(AppLoadingListener loadingListener) {
        this.scheduler.schedule(() -> {
            if (((VezhaConfig)this.configManager.getConfig()).isCheckServerDeath()) {
                this.scheduler.scheduleWithFixedDelay(this.serversMonitor::removeDeadServers, 10L, 10L, TimeUnit.SECONDS);
            }
            LoadingStage interruptedStage = loadingListener.getCurrentLoadingStage();
            loadingListener.onStageChanged(LoadingStage.LOADING_ANALYTICS);
            this.started = true;
            loadingListener.onStageChanged(interruptedStage);
            if (this.analyticsLoadingFinishCallback != null) {
                this.analyticsLoadingFinishCallback.run();
            }
        }, 10L, TimeUnit.SECONDS);
    }

    public void setAnalyticsLoadingFinishCallback(Runnable callback) {
        if (this.isStarted()) {
            throw new IllegalStateException("Analytics already loaded, no need to add callback.");
        }
        this.analyticsLoadingFinishCallback = Objects.requireNonNull(callback);
    }

    @Inject
    public AnalyticsCoreScheduler(ServersMonitor serversMonitor, VezhaConfigManager configManager) {
        this.serversMonitor = serversMonitor;
        this.configManager = configManager;
    }

    public boolean isStarted() {
        return this.started;
    }
}

