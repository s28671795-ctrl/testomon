/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.api;

import java.nio.ByteBuffer;
import org.eclipse.jetty.websocket.api.WebSocketConnectionListener;

public interface WebSocketPartialListener
extends WebSocketConnectionListener {
    default public void onWebSocketPartialBinary(ByteBuffer payload, boolean fin) {
    }

    default public void onWebSocketPartialText(String payload, boolean fin) {
    }
}

