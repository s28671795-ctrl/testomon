/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.channel;

import io.grpc.netty.shaded.io.netty.channel.EventLoop;
import io.grpc.netty.shaded.io.netty.channel.EventLoopGroup;
import io.grpc.netty.shaded.io.netty.util.concurrent.AbstractEventExecutor;

public abstract class AbstractEventLoop
extends AbstractEventExecutor
implements EventLoop {
    protected AbstractEventLoop() {
    }

    protected AbstractEventLoop(EventLoopGroup parent) {
        super(parent);
    }

    @Override
    public EventLoopGroup parent() {
        return (EventLoopGroup)super.parent();
    }

    @Override
    public EventLoop next() {
        return (EventLoop)super.next();
    }
}

