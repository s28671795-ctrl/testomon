/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.api;

import org.eclipse.jetty.websocket.api.Session;

public interface WebSocketSessionListener {
    default public void onWebSocketSessionCreated(Session session) {
    }

    default public void onWebSocketSessionOpened(Session session) {
    }

    default public void onWebSocketSessionClosed(Session session) {
    }
}

