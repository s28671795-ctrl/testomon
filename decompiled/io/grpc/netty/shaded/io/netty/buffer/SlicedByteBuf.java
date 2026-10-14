/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.buffer;

import io.grpc.netty.shaded.io.netty.buffer.AbstractUnpooledSlicedByteBuf;
import io.grpc.netty.shaded.io.netty.buffer.ByteBuf;

@Deprecated
public class SlicedByteBuf
extends AbstractUnpooledSlicedByteBuf {
    private int length;

    public SlicedByteBuf(ByteBuf buffer, int index, int length) {
        super(buffer, index, length);
    }

    @Override
    final void initLength(int length) {
        this.length = length;
    }

    @Override
    final int length() {
        return this.length;
    }

    @Override
    public int capacity() {
        return this.length;
    }
}

