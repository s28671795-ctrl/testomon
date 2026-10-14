/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.buffer;

public interface ByteBufAllocatorMetric {
    public long usedHeapMemory();

    public long usedDirectMemory();
}

