/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.transport.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelPromise;
import io.netty.channel.CoalescingBufferQueue;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.UnsupportedMessageTypeException;
import io.netty.util.ReferenceCountUtil;
import java.net.SocketAddress;
import javax.security.sasl.SaslClient;
import javax.security.sasl.SaslException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SaslHandler
extends SimpleChannelInboundHandler<ByteBuf>
implements ChannelOutboundHandler {
    private final Logger logger = LoggerFactory.getLogger(SaslHandler.class);
    private final SaslClient saslClient;
    private CoalescingBufferQueue queue;

    public SaslHandler(SaslClient sc) {
        this.saslClient = sc;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        this.queue = new CoalescingBufferQueue(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        this.dispose();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] bytes = new byte[msg.readableBytes()];
        msg.readBytes(bytes);
        ctx.fireChannelRead(Unpooled.wrappedBuffer(this.saslClient.unwrap(bytes, 0, bytes.length)));
    }

    @Override
    public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        ctx.bind(localAddress, promise);
    }

    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        ctx.connect(remoteAddress, localAddress, promise);
    }

    @Override
    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        this.dispose();
        ctx.close(promise);
    }

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        this.dispose();
        ctx.close(promise);
    }

    private void dispose() {
        try {
            this.saslClient.dispose();
        }
        catch (SaslException e) {
            this.logger.warn("Error disposing of SASL client", e);
        }
        if (this.queue != null && !this.queue.isEmpty()) {
            this.queue.releaseAndFailAll(new ChannelException("SASL client closed"));
        }
        this.queue = null;
    }

    @Override
    public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        ctx.deregister(promise);
    }

    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        ctx.read();
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (!(msg instanceof ByteBuf)) {
            UnsupportedMessageTypeException exception = new UnsupportedMessageTypeException(msg, ByteBuf.class);
            ReferenceCountUtil.safeRelease(msg);
            promise.setFailure(exception);
        }
        this.queue.add((ByteBuf)msg, promise);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
        if (this.queue.isEmpty()) {
            return;
        }
        ByteBuf buf = null;
        try {
            ChannelPromise promise = ctx.newPromise();
            int readableBytes = this.queue.readableBytes();
            buf = this.queue.remove(readableBytes, promise);
            byte[] bytes = new byte[readableBytes];
            buf.readBytes(bytes);
            byte[] wrappedBytes = this.saslClient.wrap(bytes, 0, bytes.length);
            ctx.writeAndFlush(Unpooled.wrappedBuffer(wrappedBytes), promise);
            if (buf == null) return;
        }
        catch (Throwable throwable) {
            if (buf == null) throw throwable;
            ReferenceCountUtil.safeRelease(buf);
            throw throwable;
        }
        ReferenceCountUtil.safeRelease(buf);
    }
}

