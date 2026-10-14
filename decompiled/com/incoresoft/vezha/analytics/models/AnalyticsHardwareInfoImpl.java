/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.models;

import com.incoresoft.middleware.model.analytics.AnalyticsHardwareInfo;
import com.incoresoft.middleware.model.analytics.AnalyticsServer;
import com.incoresoft.middleware.model.analytics.Gpu;
import com.incoresoft.vezha.analytics.BaseAnalytics;
import com.incoresoft.vezha.analytics.servers.Server;
import com.incoresoft.vezha.analytics.servers.models.GpuCard;
import com.incoresoft.vezha.analytics.status.AnalyticsStatusImpl;
import java.util.Objects;

public class AnalyticsHardwareInfoImpl
implements AnalyticsHardwareInfo {
    private Server analyticsServer;
    private GpuCard gpuCard;

    public AnalyticsHardwareInfoImpl(BaseAnalytics analytics) {
        AnalyticsStatusImpl analyticsStatus = analytics.getAnalyticsStatus();
        this.analyticsServer = analyticsStatus.getServerValue();
        this.gpuCard = this.analyticsServer.getGraphics().values().stream().filter(gpu -> Objects.equals(gpu.getId(), analyticsStatus.getLastGpuIdValue())).findFirst().orElse(null);
    }

    @Override
    public AnalyticsServer getAnalyticsServer() {
        return this.analyticsServer;
    }

    @Override
    public Gpu getGpu() {
        return this.gpuCard;
    }

    public void setAnalyticsServer(Server analyticsServer) {
        this.analyticsServer = analyticsServer;
    }

    public void setGpuCard(GpuCard gpuCard) {
        this.gpuCard = gpuCard;
    }

    public GpuCard getGpuCard() {
        return this.gpuCard;
    }
}

