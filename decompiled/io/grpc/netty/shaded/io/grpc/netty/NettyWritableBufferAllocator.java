/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import io.grpc.internal.WritableBuffer;
import io.grpc.internal.WritableBufferAllocator;
import io.grpc.netty.shaded.io.grpc.netty.NettyWritableBuffer;
import io.grpc.netty.shaded.io.netty.buffer.ByteBufAllocator;

class NettyWritableBufferAllocator
implements WritableBufferAllocator {
    private static final int MIN_BUFFER = 4096;
    private static final int MAX_BUFFER = 0x100000;
    private final ByteBufAllocator allocator;

    NettyWritableBufferAllocator(ByteBufAllocator allocator) {
        this.allocator = allocator;
    }

    @Override
    public WritableBuffer allocate(int capacityHint) {
        capacityHint = Math.min(0x100000, Math.max(4096, capacityHint));
        return new NettyWritableBuffer(this.allocator.buffer(capacityHint, capacityHint));
    }
}

