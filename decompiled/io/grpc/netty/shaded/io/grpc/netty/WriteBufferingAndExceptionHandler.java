/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import com.google.common.base.Preconditions;
import io.grpc.Status;
import io.grpc.netty.shaded.io.grpc.netty.ForcefulCloseCommand;
import io.grpc.netty.shaded.io.grpc.netty.GracefulCloseCommand;
import io.grpc.netty.shaded.io.grpc.netty.ProtocolNegotiationEvent;
import io.grpc.netty.shaded.io.grpc.netty.Utils;
import io.grpc.netty.shaded.io.netty.buffer.ByteBuf;
import io.grpc.netty.shaded.io.netty.buffer.ByteBufUtil;
import io.grpc.netty.shaded.io.netty.channel.ChannelDuplexHandler;
import io.grpc.netty.shaded.io.netty.channel.ChannelFuture;
import io.grpc.netty.shaded.io.netty.channel.ChannelFutureListener;
import io.grpc.netty.shaded.io.netty.channel.ChannelHandler;
import io.grpc.netty.shaded.io.netty.channel.ChannelHandlerContext;
import io.grpc.netty.shaded.io.netty.channel.ChannelPromise;
import io.grpc.netty.shaded.io.netty.util.ReferenceCountUtil;
import java.net.SocketAddress;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

final class WriteBufferingAndExceptionHandler
extends ChannelDuplexHandler {
    private static final Logger logger = Logger.getLogger(WriteBufferingAndExceptionHandler.class.getName());
    private final Queue<ChannelWrite> bufferedWrites = new ArrayDeque<ChannelWrite>();
    private final ChannelHandler next;
    private boolean writing;
    private boolean flushRequested;
    private Throwable failCause;

    WriteBufferingAndExceptionHandler(ChannelHandler next) {
        this.next = Preconditions.checkNotNull(next, "next");
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        ctx.pipeline().addBefore(ctx.name(), null, this.next);
        super.handlerAdded(ctx);
        ctx.pipeline().fireUserEventTriggered(ProtocolNegotiationEvent.DEFAULT);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if (!this.bufferedWrites.isEmpty()) {
            Status status = Status.INTERNAL.withDescription("Buffer removed before draining writes");
            this.failWrites(status.asRuntimeException());
        }
        super.handlerRemoved(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Status status = Status.UNAVAILABLE.withDescription("Connection closed while performing protocol negotiation for " + ctx.pipeline().names());
        this.failWrites(status.asRuntimeException());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        assert (cause != null);
        Throwable previousFailure = this.failCause;
        Status status = Utils.statusFromThrowable(cause).augmentDescription("Channel Pipeline: " + ctx.pipeline().names());
        this.failWrites(status.asRuntimeException());
        if (ctx.channel().isActive() && previousFailure == null) {
            final class LogOnFailure
            implements ChannelFutureListener {
                LogOnFailure() {
                }

                @Override
                public void operationComplete(ChannelFuture future) {
                    if (!future.isSuccess()) {
                        logger.log(Level.FINE, "Failed closing channel", future.cause());
                    }
                }
            }
            ctx.close().addListener(new LogOnFailure());
        }
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
        if (this.failCause != null) {
            promise.setFailure(this.failCause);
            ReferenceCountUtil.release(msg);
        } else {
            if (msg instanceof GracefulCloseCommand || msg instanceof ForcefulCloseCommand) {
                ctx.close();
            }
            this.bufferedWrites.add(new ChannelWrite(msg, promise));
        }
    }

    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        super.connect(ctx, remoteAddress, localAddress, promise);
        final class ConnectListener
        implements ChannelFutureListener {
            ConnectListener() {
            }

            @Override
            public void operationComplete(ChannelFuture future) {
                if (!future.isSuccess()) {
                    WriteBufferingAndExceptionHandler.this.failWrites(future.cause());
                }
            }
        }
        promise.addListener(new ConnectListener());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            if (logger.isLoggable(Level.FINE)) {
                Object loggedMsg = msg instanceof ByteBuf ? ByteBufUtil.hexDump((ByteBuf)msg) : msg;
                logger.log(Level.FINE, "Unexpected channelRead()->{0} reached end of pipeline {1}", new Object[]{loggedMsg, ctx.pipeline().names()});
            }
            this.exceptionCaught(ctx, Status.INTERNAL.withDescription("channelRead() missed by ProtocolNegotiator handler: " + msg).asRuntimeException());
        }
        finally {
            ReferenceCountUtil.safeRelease(msg);
        }
    }

    @Override
    public void flush(ChannelHandlerContext ctx) {
        this.flushRequested = true;
    }

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise future) throws Exception {
        Status status = Status.UNAVAILABLE.withDescription("Connection closing while performing protocol negotiation for " + ctx.pipeline().names());
        this.failWrites(status.asRuntimeException());
        super.close(ctx, future);
    }

    final void writeBufferedAndRemove(ChannelHandlerContext ctx) {
        if (!ctx.channel().isActive() || this.writing) {
            return;
        }
        this.writing = true;
        while (!this.bufferedWrites.isEmpty()) {
            ChannelWrite write = this.bufferedWrites.poll();
            ctx.write(write.msg, write.promise);
        }
        if (this.flushRequested) {
            ctx.flush();
        }
        ctx.pipeline().remove(this);
    }

    private void failWrites(Throwable cause) {
        if (this.failCause == null) {
            this.failCause = cause;
        } else {
            logger.log(Level.FINE, "Ignoring duplicate failure", cause);
        }
        while (!this.bufferedWrites.isEmpty()) {
            ChannelWrite write = this.bufferedWrites.poll();
            write.promise.setFailure(cause);
            ReferenceCountUtil.release(write.msg);
        }
    }

    private static final class ChannelWrite {
        final Object msg;
        final ChannelPromise promise;

        ChannelWrite(Object msg, ChannelPromise promise) {
            this.msg = msg;
            this.promise = promise;
        }
    }
}

