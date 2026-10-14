/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.exception;

public class TechnicalException
extends RuntimeException {
    private static final long serialVersionUID = 536639932593211210L;

    public TechnicalException(String message) {
        super(message);
    }

    public TechnicalException(Throwable t2) {
        super(t2);
    }

    public TechnicalException(String message, Throwable t2) {
        super(message, t2);
    }
}

