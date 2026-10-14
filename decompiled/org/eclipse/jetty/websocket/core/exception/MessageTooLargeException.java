/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.exception;

import org.eclipse.jetty.websocket.core.exception.CloseException;

public class MessageTooLargeException
extends CloseException {
    public MessageTooLargeException(String message) {
        super(1009, message);
    }

    public MessageTooLargeException(String message, Throwable t2) {
        super(1009, message, t2);
    }

    public MessageTooLargeException(Throwable t2) {
        super(1009, t2);
    }
}

