/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.exception;

import org.eclipse.jetty.websocket.core.exception.WebSocketException;

public class WebSocketTimeoutException
extends WebSocketException {
    private static final long serialVersionUID = -6145098200250676673L;

    public WebSocketTimeoutException(String message) {
        super(message);
    }

    public WebSocketTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebSocketTimeoutException(Throwable cause) {
        super(cause);
    }
}

