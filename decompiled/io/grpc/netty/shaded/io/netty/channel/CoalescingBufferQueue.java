/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.channel;

import io.grpc.netty.shaded.io.netty.buffer.ByteBuf;
import io.grpc.netty.shaded.io.netty.buffer.ByteBufAllocator;
import io.grpc.netty.shaded.io.netty.buffer.CompositeByteBuf;
import io.grpc.netty.shaded.io.netty.buffer.Unpooled;
import io.grpc.netty.shaded.io.netty.channel.AbstractCoalescingBufferQueue;
import io.grpc.netty.shaded.io.netty.channel.Channel;
import io.grpc.netty.shaded.io.netty.channel.ChannelPromise;
import io.grpc.netty.shaded.io.netty.util.internal.ObjectUtil;

public final class CoalescingBufferQueue
extends AbstractCoalescingBufferQueue {
    private final Channel channel;

    public CoalescingBufferQueue(Channel channel) {
        this(channel, 4);
    }

    public CoalescingBufferQueue(Channel channel, int initSize) {
        this(channel, initSize, false);
    }

    public CoalescingBufferQueue(Channel channel, int initSize, boolean updateWritability) {
        super(updateWritability ? channel : null, initSize);
        this.channel = ObjectUtil.checkNotNull(channel, "channel");
    }

    public ByteBuf remove(int bytes, ChannelPromise aggregatePromise) {
        return this.remove(this.channel.alloc(), bytes, aggregatePromise);
    }

    public void releaseAndFailAll(Throwable cause) {
        this.releaseAndFailAll(this.channel, cause);
    }

    @Override
    protected ByteBuf compose(ByteBufAllocator alloc, ByteBuf cumulation, ByteBuf next) {
        if (cumulation instanceof CompositeByteBuf) {
            CompositeByteBuf composite = (CompositeByteBuf)cumulation;
            composite.addComponent(true, next);
            return composite;
        }
        return this.composeIntoComposite(alloc, cumulation, next);
    }

    @Override
    protected ByteBuf removeEmptyValue() {
        return Unpooled.EMPTY_BUFFER;
    }
}

