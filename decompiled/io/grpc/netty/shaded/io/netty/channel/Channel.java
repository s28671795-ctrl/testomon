/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.channel;

import io.grpc.netty.shaded.io.netty.buffer.ByteBufAllocator;
import io.grpc.netty.shaded.io.netty.channel.ChannelConfig;
import io.grpc.netty.shaded.io.netty.channel.ChannelFuture;
import io.grpc.netty.shaded.io.netty.channel.ChannelId;
import io.grpc.netty.shaded.io.netty.channel.ChannelMetadata;
import io.grpc.netty.shaded.io.netty.channel.ChannelOutboundBuffer;
import io.grpc.netty.shaded.io.netty.channel.ChannelOutboundInvoker;
import io.grpc.netty.shaded.io.netty.channel.ChannelPipeline;
import io.grpc.netty.shaded.io.netty.channel.ChannelPromise;
import io.grpc.netty.shaded.io.netty.channel.EventLoop;
import io.grpc.netty.shaded.io.netty.channel.RecvByteBufAllocator;
import io.grpc.netty.shaded.io.netty.util.AttributeMap;
import java.net.SocketAddress;

public interface Channel
extends AttributeMap,
ChannelOutboundInvoker,
Comparable<Channel> {
    public ChannelId id();

    public EventLoop eventLoop();

    public Channel parent();

    public ChannelConfig config();

    public boolean isOpen();

    public boolean isRegistered();

    public boolean isActive();

    public ChannelMetadata metadata();

    public SocketAddress localAddress();

    public SocketAddress remoteAddress();

    public ChannelFuture closeFuture();

    public boolean isWritable();

    public long bytesBeforeUnwritable();

    public long bytesBeforeWritable();

    public Unsafe unsafe();

    public ChannelPipeline pipeline();

    public ByteBufAllocator alloc();

    @Override
    public Channel read();

    @Override
    public Channel flush();

    public static interface Unsafe {
        public RecvByteBufAllocator.Handle recvBufAllocHandle();

        public SocketAddress localAddress();

        public SocketAddress remoteAddress();

        public void register(EventLoop var1, ChannelPromise var2);

        public void bind(SocketAddress var1, ChannelPromise var2);

        public void connect(SocketAddress var1, SocketAddress var2, ChannelPromise var3);

        public void disconnect(ChannelPromise var1);

        public void close(ChannelPromise var1);

        public void closeForcibly();

        public void deregister(ChannelPromise var1);

        public void beginRead();

        public void write(Object var1, ChannelPromise var2);

        public void flush();

        public ChannelPromise voidPromise();

        public ChannelOutboundBuffer outboundBuffer();
    }
}

