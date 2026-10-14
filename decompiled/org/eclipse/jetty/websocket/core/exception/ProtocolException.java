/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.exception;

import org.eclipse.jetty.websocket.core.exception.CloseException;

public class ProtocolException
extends CloseException {
    public ProtocolException(String message) {
        super(1002, message);
    }

    public ProtocolException(String message, Throwable t2) {
        super(1002, message, t2);
    }

    public ProtocolException(Throwable t2) {
        super(1002, t2);
    }
}

