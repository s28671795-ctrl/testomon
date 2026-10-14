/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.exception;

import org.eclipse.jetty.websocket.core.exception.WebSocketException;

public class InvalidWebSocketException
extends WebSocketException {
    public InvalidWebSocketException(String message) {
        super(message);
    }

    public InvalidWebSocketException(String message, Throwable cause) {
        super(message, cause);
    }
}

