/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.ipfilter;

import io.grpc.netty.shaded.io.netty.channel.ChannelFuture;
import io.grpc.netty.shaded.io.netty.channel.ChannelFutureListener;
import io.grpc.netty.shaded.io.netty.channel.ChannelHandler;
import io.grpc.netty.shaded.io.netty.channel.ChannelHandlerContext;
import io.grpc.netty.shaded.io.netty.handler.ipfilter.AbstractRemoteAddressFilter;
import io.grpc.netty.shaded.io.netty.util.internal.ConcurrentSet;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Set;

@ChannelHandler.Sharable
public class UniqueIpFilter
extends AbstractRemoteAddressFilter<InetSocketAddress> {
    private final Set<InetAddress> connected = new ConcurrentSet<InetAddress>();

    @Override
    protected boolean accept(ChannelHandlerContext ctx, InetSocketAddress remoteAddress) throws Exception {
        final InetAddress remoteIp = remoteAddress.getAddress();
        if (!this.connected.add(remoteIp)) {
            return false;
        }
        ctx.channel().closeFuture().addListener(new ChannelFutureListener(){

            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                UniqueIpFilter.this.connected.remove(remoteIp);
            }
        });
        return true;
    }
}

