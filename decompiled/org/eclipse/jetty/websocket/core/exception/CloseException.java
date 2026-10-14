/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.exception;

import org.eclipse.jetty.websocket.core.exception.WebSocketException;

public class CloseException
extends WebSocketException {
    private final int statusCode;

    public CloseException(int closeCode, String message) {
        super(message);
        this.statusCode = closeCode;
    }

    public CloseException(int closeCode, String message, Throwable cause) {
        super(message, cause);
        this.statusCode = closeCode;
    }

    public CloseException(int closeCode, Throwable cause) {
        super(cause);
        this.statusCode = closeCode;
    }

    public int getStatusCode() {
        return this.statusCode;
    }
}

