/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.servers;

import java.util.Map;

public record GpuMemoryReserve(Map<Integer, Double> reservedGbByGpu) {
    public static final GpuMemoryReserve EMPTY = new GpuMemoryReserve(Map.of());

    public GpuMemoryReserve {
        reservedGbByGpu = reservedGbByGpu == null ? Map.of() : Map.copyOf(reservedGbByGpu);
    }

    public double reservedGb(int gpuId) {
        return this.reservedGbByGpu.getOrDefault(gpuId, 0.0);
    }

    public boolean isEmpty() {
        return this.reservedGbByGpu.isEmpty();
    }
}

