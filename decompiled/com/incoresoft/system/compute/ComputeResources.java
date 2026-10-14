/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.system.compute;

import java.time.Duration;

public record ComputeResources(double cpuUsage, double totalMem, double freeMem, Duration uptime) {
}

