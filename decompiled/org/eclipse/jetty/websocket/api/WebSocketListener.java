/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.api;

import org.eclipse.jetty.websocket.api.WebSocketConnectionListener;

public interface WebSocketListener
extends WebSocketConnectionListener {
    default public void onWebSocketBinary(byte[] payload, int offset, int len) {
    }

    default public void onWebSocketText(String message) {
    }
}

