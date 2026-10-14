/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.exception;

import org.eclipse.jetty.websocket.core.exception.WebSocketTimeoutException;

public class WebSocketWriteTimeoutException
extends WebSocketTimeoutException {
    public WebSocketWriteTimeoutException(String message) {
        super(message);
    }
}

