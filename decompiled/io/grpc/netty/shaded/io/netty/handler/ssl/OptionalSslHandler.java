/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.ssl;

import io.grpc.netty.shaded.io.netty.buffer.ByteBuf;
import io.grpc.netty.shaded.io.netty.channel.ChannelHandler;
import io.grpc.netty.shaded.io.netty.channel.ChannelHandlerContext;
import io.grpc.netty.shaded.io.netty.handler.codec.ByteToMessageDecoder;
import io.grpc.netty.shaded.io.netty.handler.ssl.SslContext;
import io.grpc.netty.shaded.io.netty.handler.ssl.SslHandler;
import io.grpc.netty.shaded.io.netty.util.ReferenceCountUtil;
import io.grpc.netty.shaded.io.netty.util.internal.ObjectUtil;
import java.util.List;

public class OptionalSslHandler
extends ByteToMessageDecoder {
    private final SslContext sslContext;

    public OptionalSslHandler(SslContext sslContext) {
        this.sslContext = ObjectUtil.checkNotNull(sslContext, "sslContext");
    }

    @Override
    protected void decode(ChannelHandlerContext context, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 5) {
            return;
        }
        if (SslHandler.isEncrypted(in)) {
            this.handleSsl(context);
        } else {
            this.handleNonSsl(context);
        }
    }

    private void handleSsl(ChannelHandlerContext context) {
        SslHandler sslHandler = null;
        try {
            sslHandler = this.newSslHandler(context, this.sslContext);
            context.pipeline().replace(this, this.newSslHandlerName(), (ChannelHandler)sslHandler);
            sslHandler = null;
        }
        finally {
            if (sslHandler != null) {
                ReferenceCountUtil.safeRelease(sslHandler.engine());
            }
        }
    }

    private void handleNonSsl(ChannelHandlerContext context) {
        ChannelHandler handler = this.newNonSslHandler(context);
        if (handler != null) {
            context.pipeline().replace(this, this.newNonSslHandlerName(), handler);
        } else {
            context.pipeline().remove(this);
        }
    }

    protected String newSslHandlerName() {
        return null;
    }

    protected SslHandler newSslHandler(ChannelHandlerContext context, SslContext sslContext) {
        return sslContext.newHandler(context.alloc());
    }

    protected String newNonSslHandlerName() {
        return null;
    }

    protected ChannelHandler newNonSslHandler(ChannelHandlerContext context) {
        return null;
    }
}

