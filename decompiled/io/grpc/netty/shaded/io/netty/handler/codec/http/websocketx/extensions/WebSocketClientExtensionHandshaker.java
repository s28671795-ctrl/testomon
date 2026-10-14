/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.http.websocketx.extensions;

import io.grpc.netty.shaded.io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtension;
import io.grpc.netty.shaded.io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionData;

public interface WebSocketClientExtensionHandshaker {
    public WebSocketExtensionData newRequestData();

    public WebSocketClientExtension handshakeExtension(WebSocketExtensionData var1);
}

