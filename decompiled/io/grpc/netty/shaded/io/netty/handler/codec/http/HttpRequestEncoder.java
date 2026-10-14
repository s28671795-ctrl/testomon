/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.http;

import io.grpc.netty.shaded.io.netty.buffer.ByteBuf;
import io.grpc.netty.shaded.io.netty.buffer.ByteBufUtil;
import io.grpc.netty.shaded.io.netty.handler.codec.http.HttpObjectEncoder;
import io.grpc.netty.shaded.io.netty.handler.codec.http.HttpRequest;
import io.grpc.netty.shaded.io.netty.handler.codec.http.HttpResponse;
import io.grpc.netty.shaded.io.netty.util.CharsetUtil;

public class HttpRequestEncoder
extends HttpObjectEncoder<HttpRequest> {
    private static final char SLASH = '/';
    private static final char QUESTION_MARK = '?';
    private static final int SLASH_AND_SPACE_SHORT = 12064;
    private static final int SPACE_SLASH_AND_SPACE_MEDIUM = 0x202F20;

    @Override
    public boolean acceptOutboundMessage(Object msg) throws Exception {
        return super.acceptOutboundMessage(msg) && !(msg instanceof HttpResponse);
    }

    @Override
    protected void encodeInitialLine(ByteBuf buf, HttpRequest request) throws Exception {
        ByteBufUtil.copy(request.method().asciiName(), buf);
        String uri = request.uri();
        if (uri.isEmpty()) {
            ByteBufUtil.writeMediumBE(buf, 0x202F20);
        } else {
            CharSequence uriCharSequence = uri;
            boolean needSlash = false;
            int start2 = uri.indexOf("://");
            if (start2 != -1 && uri.charAt(0) != '/') {
                int index = uri.indexOf(63, start2 += 3);
                if (index == -1) {
                    if (uri.lastIndexOf(47) < start2) {
                        needSlash = true;
                    }
                } else if (uri.lastIndexOf(47, index) < start2) {
                    uriCharSequence = new StringBuilder(uri).insert(index, '/');
                }
            }
            buf.writeByte(32).writeCharSequence(uriCharSequence, CharsetUtil.UTF_8);
            if (needSlash) {
                ByteBufUtil.writeShortBE(buf, 12064);
            } else {
                buf.writeByte(32);
            }
        }
        request.protocolVersion().encode(buf);
        ByteBufUtil.writeShortBE(buf, 3338);
    }
}

