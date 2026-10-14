/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.http.websocketx;

import io.grpc.netty.shaded.io.netty.buffer.ByteBuf;
import io.grpc.netty.shaded.io.netty.channel.ChannelPipeline;
import io.grpc.netty.shaded.io.netty.handler.codec.MessageAggregator;
import io.grpc.netty.shaded.io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.grpc.netty.shaded.io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.grpc.netty.shaded.io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.grpc.netty.shaded.io.netty.handler.codec.http.websocketx.WebSocketFrame;

public class WebSocketFrameAggregator
extends MessageAggregator<WebSocketFrame, WebSocketFrame, ContinuationWebSocketFrame, WebSocketFrame> {
    public WebSocketFrameAggregator(int maxContentLength) {
        super(maxContentLength);
    }

    @Override
    protected boolean isStartMessage(WebSocketFrame msg) throws Exception {
        return msg instanceof TextWebSocketFrame || msg instanceof BinaryWebSocketFrame;
    }

    @Override
    protected boolean isContentMessage(WebSocketFrame msg) throws Exception {
        return msg instanceof ContinuationWebSocketFrame;
    }

    @Override
    protected boolean isLastContentMessage(ContinuationWebSocketFrame msg) throws Exception {
        return this.isContentMessage(msg) && msg.isFinalFragment();
    }

    @Override
    protected boolean isAggregated(WebSocketFrame msg) throws Exception {
        if (msg.isFinalFragment()) {
            return !this.isContentMessage(msg);
        }
        return !this.isStartMessage(msg) && !this.isContentMessage(msg);
    }

    @Override
    protected boolean isContentLengthInvalid(WebSocketFrame start2, int maxContentLength) {
        return false;
    }

    @Override
    protected Object newContinueResponse(WebSocketFrame start2, int maxContentLength, ChannelPipeline pipeline) {
        return null;
    }

    @Override
    protected boolean closeAfterContinueResponse(Object msg) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    protected boolean ignoreContentAfterContinueResponse(Object msg) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    protected WebSocketFrame beginAggregation(WebSocketFrame start2, ByteBuf content) throws Exception {
        if (start2 instanceof TextWebSocketFrame) {
            return new TextWebSocketFrame(true, start2.rsv(), content);
        }
        if (start2 instanceof BinaryWebSocketFrame) {
            return new BinaryWebSocketFrame(true, start2.rsv(), content);
        }
        throw new Error();
    }
}

