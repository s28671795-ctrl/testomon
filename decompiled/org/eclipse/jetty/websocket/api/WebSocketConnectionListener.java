/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.api;

import org.eclipse.jetty.websocket.api.Session;

public interface WebSocketConnectionListener {
    default public void onWebSocketClose(int statusCode, String reason) {
    }

    default public void onWebSocketConnect(Session session) {
    }

    default public void onWebSocketError(Throwable cause) {
    }
}

