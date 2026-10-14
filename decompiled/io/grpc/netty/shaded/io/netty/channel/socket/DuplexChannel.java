/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.channel.socket;

import io.grpc.netty.shaded.io.netty.channel.Channel;
import io.grpc.netty.shaded.io.netty.channel.ChannelFuture;
import io.grpc.netty.shaded.io.netty.channel.ChannelPromise;

public interface DuplexChannel
extends Channel {
    public boolean isInputShutdown();

    public ChannelFuture shutdownInput();

    public ChannelFuture shutdownInput(ChannelPromise var1);

    public boolean isOutputShutdown();

    public ChannelFuture shutdownOutput();

    public ChannelFuture shutdownOutput(ChannelPromise var1);

    public boolean isShutdown();

    public ChannelFuture shutdown();

    public ChannelFuture shutdown(ChannelPromise var1);
}

