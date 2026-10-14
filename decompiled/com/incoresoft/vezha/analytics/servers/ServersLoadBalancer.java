/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.servers;

import com.google.inject.Inject;
import com.incoresoft.vezha.analytics.servers.Server;
import com.incoresoft.vezha.analytics.servers.models.GpuCard;

public class ServersLoadBalancer {
    public static int compareServersLoadByGpu(Server firstServer, Server secondServer) {
        double gpuUsage1 = firstServer.getGraphics().values().stream().mapToDouble(GpuCard::getGpuUsage).average().orElse(0.0);
        double gpuUsage2 = secondServer.getGraphics().values().stream().mapToDouble(GpuCard::getGpuUsage).average().orElse(0.0);
        return Double.compare(gpuUsage1, gpuUsage2);
    }

    public static int compareServersLoadByCpu(Server firstServer, Server secondServer) {
        return Double.compare(firstServer.getCpuUsage(), secondServer.getCpuUsage());
    }

    @Inject
    public ServersLoadBalancer() {
    }
}

