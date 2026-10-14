/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import io.grpc.netty.shaded.io.netty.buffer.ByteBuf;
import io.grpc.netty.shaded.io.netty.buffer.ByteBufAllocator;
import io.grpc.netty.shaded.io.netty.buffer.CompositeByteBuf;
import io.grpc.netty.shaded.io.netty.handler.codec.ByteToMessageDecoder;
import io.grpc.netty.shaded.io.netty.util.ReferenceCounted;

class NettyAdaptiveCumulator
implements ByteToMessageDecoder.Cumulator {
    private final int composeMinSize;

    NettyAdaptiveCumulator(int composeMinSize) {
        Preconditions.checkArgument(composeMinSize >= 0, "composeMinSize must be non-negative");
        this.composeMinSize = composeMinSize;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final ByteBuf cumulate(ByteBufAllocator alloc, ByteBuf cumulation, ByteBuf in) {
        if (!cumulation.isReadable()) {
            cumulation.release();
            return in;
        }
        CompositeByteBuf composite = null;
        try {
            if (cumulation instanceof CompositeByteBuf && cumulation.refCnt() == 1) {
                composite = (CompositeByteBuf)cumulation;
                if (composite.writerIndex() != composite.capacity()) {
                    composite.capacity(composite.writerIndex());
                }
            } else {
                composite = alloc.compositeBuffer(Integer.MAX_VALUE).addFlattenedComponents(true, cumulation);
            }
            this.addInput(alloc, composite, in);
            in = null;
            CompositeByteBuf compositeByteBuf = composite;
            return compositeByteBuf;
        }
        finally {
            if (in != null) {
                in.release();
                if (composite != null && composite != cumulation) {
                    composite.release();
                }
            }
        }
    }

    @VisibleForTesting
    void addInput(ByteBufAllocator alloc, CompositeByteBuf composite, ByteBuf in) {
        if (NettyAdaptiveCumulator.shouldCompose(composite, in, this.composeMinSize)) {
            composite.addFlattenedComponents(true, in);
        } else {
            NettyAdaptiveCumulator.mergeWithCompositeTail(alloc, composite, in);
        }
    }

    @VisibleForTesting
    static boolean shouldCompose(CompositeByteBuf composite, ByteBuf in, int composeMinSize) {
        int componentCount = composite.numComponents();
        if (composite.numComponents() == 0) {
            return true;
        }
        int inputSize = in.readableBytes();
        int tailStart = composite.toByteIndex(componentCount - 1);
        int tailSize = composite.writerIndex() - tailStart;
        return tailSize + inputSize >= composeMinSize;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @VisibleForTesting
    static void mergeWithCompositeTail(ByteBufAllocator alloc, CompositeByteBuf composite, ByteBuf in) {
        int inputSize = in.readableBytes();
        int tailComponentIndex = composite.numComponents() - 1;
        int tailStart = composite.toByteIndex(tailComponentIndex);
        int tailSize = composite.writerIndex() - tailStart;
        int newTailSize = inputSize + tailSize;
        ByteBuf tail = composite.component(tailComponentIndex);
        ReferenceCounted newTail = null;
        try {
            if (tail.refCnt() == 1 && !tail.isReadOnly() && newTailSize <= tail.maxCapacity()) {
                newTail = tail.retain();
                ByteBuf sliceDuplicate = composite.internalComponent(tailComponentIndex).duplicate();
                ((ByteBuf)newTail).setIndex(sliceDuplicate.readerIndex(), sliceDuplicate.writerIndex());
                ((ByteBuf)newTail).writeBytes(in);
            } else {
                newTail = alloc.buffer(alloc.calculateNewCapacity(newTailSize, Integer.MAX_VALUE));
                ((ByteBuf)newTail).setBytes(0, composite, tailStart, tailSize).setBytes(tailSize, in, in.readerIndex(), inputSize).writerIndex(newTailSize);
                in.readerIndex(in.writerIndex());
            }
            int prevReader = composite.readerIndex();
            composite.removeComponent(tailComponentIndex).setIndex(0, tailStart);
            composite.addFlattenedComponents(true, (ByteBuf)newTail);
            newTail = null;
            composite.readerIndex(prevReader);
            in.release();
        }
        finally {
            if (newTail != null) {
                newTail.release();
            }
        }
    }
}

