/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.api;

import java.nio.ByteBuffer;
import org.eclipse.jetty.websocket.api.WebSocketConnectionListener;

public interface WebSocketPingPongListener
extends WebSocketConnectionListener {
    default public void onWebSocketPing(ByteBuffer payload) {
    }

    default public void onWebSocketPong(ByteBuffer payload) {
    }
}

