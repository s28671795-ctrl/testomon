/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.exception;

import org.eclipse.jetty.websocket.core.exception.CloseException;

public class BadPayloadException
extends CloseException {
    public BadPayloadException(String message) {
        super(1007, message);
    }

    public BadPayloadException(String message, Throwable t2) {
        super(1007, message, t2);
    }

    public BadPayloadException(Throwable t2) {
        super(1007, t2);
    }
}

