/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.vms.plugins;

import java.net.http.WebSocket;

final class ProxyWebSocketConnection {
    private WebSocket webSocket;
    private boolean terminated;

    ProxyWebSocketConnection() {
    }

    synchronized void attach(WebSocket webSocket) {
        if (this.terminated || this.webSocket != null) {
            webSocket.abort();
            return;
        }
        this.webSocket = webSocket;
    }

    synchronized WebSocket webSocket() {
        return this.terminated ? null : this.webSocket;
    }

    synchronized WebSocket terminate() {
        if (this.terminated) {
            return null;
        }
        this.terminated = true;
        WebSocket terminatedWebSocket = this.webSocket;
        this.webSocket = null;
        return terminatedWebSocket;
    }
}

