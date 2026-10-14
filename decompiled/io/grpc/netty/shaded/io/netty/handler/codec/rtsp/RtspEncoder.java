/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.rtsp;

import io.grpc.netty.shaded.io.netty.buffer.ByteBuf;
import io.grpc.netty.shaded.io.netty.buffer.ByteBufUtil;
import io.grpc.netty.shaded.io.netty.handler.codec.UnsupportedMessageTypeException;
import io.grpc.netty.shaded.io.netty.handler.codec.http.HttpMessage;
import io.grpc.netty.shaded.io.netty.handler.codec.http.HttpObjectEncoder;
import io.grpc.netty.shaded.io.netty.handler.codec.http.HttpRequest;
import io.grpc.netty.shaded.io.netty.handler.codec.http.HttpResponse;
import io.grpc.netty.shaded.io.netty.util.CharsetUtil;
import io.grpc.netty.shaded.io.netty.util.internal.StringUtil;

public class RtspEncoder
extends HttpObjectEncoder<HttpMessage> {
    private static final int CRLF_SHORT = 3338;

    @Override
    public boolean acceptOutboundMessage(Object msg) throws Exception {
        return super.acceptOutboundMessage(msg) && (msg instanceof HttpRequest || msg instanceof HttpResponse);
    }

    @Override
    protected void encodeInitialLine(ByteBuf buf, HttpMessage message) throws Exception {
        if (message instanceof HttpRequest) {
            HttpRequest request = (HttpRequest)message;
            ByteBufUtil.copy(request.method().asciiName(), buf);
            buf.writeByte(32);
            buf.writeCharSequence(request.uri(), CharsetUtil.UTF_8);
            buf.writeByte(32);
            buf.writeCharSequence(request.protocolVersion().toString(), CharsetUtil.US_ASCII);
            ByteBufUtil.writeShortBE(buf, 3338);
        } else if (message instanceof HttpResponse) {
            HttpResponse response = (HttpResponse)message;
            buf.writeCharSequence(response.protocolVersion().toString(), CharsetUtil.US_ASCII);
            buf.writeByte(32);
            ByteBufUtil.copy(response.status().codeAsText(), buf);
            buf.writeByte(32);
            buf.writeCharSequence(response.status().reasonPhrase(), CharsetUtil.US_ASCII);
            ByteBufUtil.writeShortBE(buf, 3338);
        } else {
            throw new UnsupportedMessageTypeException("Unsupported type " + StringUtil.simpleClassName(message));
        }
    }
}

