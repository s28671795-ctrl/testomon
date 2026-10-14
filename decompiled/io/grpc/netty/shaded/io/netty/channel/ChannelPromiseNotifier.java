/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.channel;

import io.grpc.netty.shaded.io.netty.channel.ChannelFuture;
import io.grpc.netty.shaded.io.netty.channel.ChannelFutureListener;
import io.grpc.netty.shaded.io.netty.channel.ChannelPromise;
import io.grpc.netty.shaded.io.netty.util.concurrent.PromiseNotifier;

@Deprecated
public final class ChannelPromiseNotifier
extends PromiseNotifier<Void, ChannelFuture>
implements ChannelFutureListener {
    public ChannelPromiseNotifier(ChannelPromise ... promises) {
        super(promises);
    }

    public ChannelPromiseNotifier(boolean logNotifyFailure, ChannelPromise ... promises) {
        super(logNotifyFailure, promises);
    }
}

