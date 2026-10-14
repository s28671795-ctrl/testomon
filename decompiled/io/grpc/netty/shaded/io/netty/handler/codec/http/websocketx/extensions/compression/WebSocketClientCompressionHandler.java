/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.http.websocketx.extensions.compression;

import io.grpc.netty.shaded.io.netty.channel.ChannelHandler;
import io.grpc.netty.shaded.io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandler;
import io.grpc.netty.shaded.io.netty.handler.codec.http.websocketx.extensions.compression.DeflateFrameClientExtensionHandshaker;
import io.grpc.netty.shaded.io.netty.handler.codec.http.websocketx.extensions.compression.PerMessageDeflateClientExtensionHandshaker;

@ChannelHandler.Sharable
public final class WebSocketClientCompressionHandler
extends WebSocketClientExtensionHandler {
    public static final WebSocketClientCompressionHandler INSTANCE = new WebSocketClientCompressionHandler();

    private WebSocketClientCompressionHandler() {
        super(new PerMessageDeflateClientExtensionHandshaker(), new DeflateFrameClientExtensionHandshaker(false), new DeflateFrameClientExtensionHandshaker(true));
    }
}

