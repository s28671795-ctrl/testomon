/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.http;

import io.grpc.netty.shaded.io.netty.buffer.Unpooled;
import io.grpc.netty.shaded.io.netty.channel.ChannelFutureListener;
import io.grpc.netty.shaded.io.netty.channel.ChannelHandlerContext;
import io.grpc.netty.shaded.io.netty.channel.ChannelInboundHandlerAdapter;
import io.grpc.netty.shaded.io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.grpc.netty.shaded.io.netty.handler.codec.http.FullHttpResponse;
import io.grpc.netty.shaded.io.netty.handler.codec.http.HttpHeaderNames;
import io.grpc.netty.shaded.io.netty.handler.codec.http.HttpRequest;
import io.grpc.netty.shaded.io.netty.handler.codec.http.HttpResponse;
import io.grpc.netty.shaded.io.netty.handler.codec.http.HttpResponseStatus;
import io.grpc.netty.shaded.io.netty.handler.codec.http.HttpUtil;
import io.grpc.netty.shaded.io.netty.handler.codec.http.HttpVersion;
import io.grpc.netty.shaded.io.netty.util.ReferenceCountUtil;

public class HttpServerExpectContinueHandler
extends ChannelInboundHandlerAdapter {
    private static final FullHttpResponse EXPECTATION_FAILED = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.EXPECTATION_FAILED, Unpooled.EMPTY_BUFFER);
    private static final FullHttpResponse ACCEPT = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE, Unpooled.EMPTY_BUFFER);

    protected HttpResponse acceptMessage(HttpRequest request) {
        return ACCEPT.retainedDuplicate();
    }

    protected HttpResponse rejectResponse(HttpRequest request) {
        return EXPECTATION_FAILED.retainedDuplicate();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        HttpRequest req;
        if (msg instanceof HttpRequest && HttpUtil.is100ContinueExpected(req = (HttpRequest)msg)) {
            HttpResponse accept = this.acceptMessage(req);
            if (accept == null) {
                HttpResponse rejection = this.rejectResponse(req);
                ReferenceCountUtil.release(msg);
                ctx.writeAndFlush(rejection).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                return;
            }
            ctx.writeAndFlush(accept).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            req.headers().remove(HttpHeaderNames.EXPECT);
        }
        super.channelRead(ctx, msg);
    }

    static {
        EXPECTATION_FAILED.headers().set((CharSequence)HttpHeaderNames.CONTENT_LENGTH, (Object)0);
        ACCEPT.headers().set((CharSequence)HttpHeaderNames.CONTENT_LENGTH, (Object)0);
    }
}

