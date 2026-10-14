/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.http.websocketx.extensions;

import io.grpc.netty.shaded.io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionData;
import io.grpc.netty.shaded.io.netty.handler.codec.http.websocketx.extensions.WebSocketServerExtension;

public interface WebSocketServerExtensionHandshaker {
    public WebSocketServerExtension handshakeExtension(WebSocketExtensionData var1);
}

