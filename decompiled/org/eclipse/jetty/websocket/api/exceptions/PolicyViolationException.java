/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.api.exceptions;

import org.eclipse.jetty.websocket.api.exceptions.CloseException;

public class PolicyViolationException
extends CloseException {
    public PolicyViolationException(String message) {
        super(1008, message);
    }

    public PolicyViolationException(String message, Throwable t2) {
        super(1008, message, t2);
    }

    public PolicyViolationException(Throwable t2) {
        super(1008, t2);
    }
}

