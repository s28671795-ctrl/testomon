/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.vms.plugins.proxy;

import io.grpc.netty.shaded.io.netty.handler.codec.http.websocketx.WebSocketCloseStatus;
import io.javalin.websocket.WsContext;
import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.net.http.WebSocket;
import java.nio.ByteBuffer;
import java.util.concurrent.CompletionStage;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProxyWebSocketClient
implements WebSocket.Listener {
    private static final Logger log = LoggerFactory.getLogger(ProxyWebSocketClient.class);
    private final URI serverUri;
    private final WsContext wsContext;
    private final Consumer<WebSocket> terminationHandler;
    private volatile StringBuffer textBuffer = new StringBuffer();
    private volatile ByteArrayOutputStream binaryBuffer = new ByteArrayOutputStream();

    public ProxyWebSocketClient(WsContext wsContext, URI serverUri, Consumer<WebSocket> terminationHandler) {
        this.serverUri = serverUri;
        this.wsContext = wsContext;
        this.terminationHandler = terminationHandler;
    }

    @Override
    public void onOpen(WebSocket webSocket) {
        log.info("Opened websocket connection with remote server: {}", (Object)this.serverUri);
        WebSocket.Listener.super.onOpen(webSocket);
    }

    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        this.textBuffer.append(data);
        if (last) {
            this.wsContext.send(this.textBuffer.toString());
            this.textBuffer = new StringBuffer();
        }
        return WebSocket.Listener.super.onText(webSocket, data, last);
    }

    @Override
    public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
        this.terminationHandler.accept(webSocket);
        this.wsContext.closeSession(statusCode, reason);
        log.info("Closed websocket connection with remote server: {}", (Object)this.serverUri);
        return WebSocket.Listener.super.onClose(webSocket, statusCode, reason);
    }

    @Override
    public void onError(WebSocket webSocket, Throwable error) {
        this.terminationHandler.accept(webSocket);
        webSocket.abort();
        this.wsContext.closeSession(WebSocketCloseStatus.INTERNAL_SERVER_ERROR.code(), "Server error: " + error.getMessage());
        log.error("Error interacting with remote websocket: {}. Error: {}", (Object)this.serverUri, (Object)error.getMessage());
        WebSocket.Listener.super.onError(webSocket, error);
    }

    @Override
    public CompletionStage<?> onBinary(WebSocket webSocket, ByteBuffer data, boolean last) {
        this.binaryBuffer.writeBytes(data.array());
        if (last) {
            this.wsContext.send(this.binaryBuffer.toByteArray());
            this.binaryBuffer = new ByteArrayOutputStream();
        }
        return WebSocket.Listener.super.onBinary(webSocket, data, last);
    }

    @Override
    public CompletionStage<?> onPing(WebSocket webSocket, ByteBuffer message) {
        this.wsContext.sendPing(message);
        return WebSocket.Listener.super.onPing(webSocket, message);
    }

    @Override
    public CompletionStage<?> onPong(WebSocket webSocket, ByteBuffer message) {
        return WebSocket.Listener.super.onPong(webSocket, message);
    }
}

