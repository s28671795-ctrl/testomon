/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.buffer;

import io.grpc.netty.shaded.io.netty.buffer.ByteBufAllocator;
import io.grpc.netty.shaded.io.netty.buffer.UnpooledUnsafeDirectByteBuf;
import io.grpc.netty.shaded.io.netty.util.internal.PlatformDependent;
import java.nio.ByteBuffer;

final class WrappedUnpooledUnsafeDirectByteBuf
extends UnpooledUnsafeDirectByteBuf {
    WrappedUnpooledUnsafeDirectByteBuf(ByteBufAllocator alloc, long memoryAddress, int size, boolean doFree) {
        super(alloc, PlatformDependent.directBuffer(memoryAddress, size), size, doFree);
    }

    @Override
    protected void freeDirect(ByteBuffer buffer) {
        PlatformDependent.freeMemory(this.memoryAddress);
    }
}

