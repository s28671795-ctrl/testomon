/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.exception;

import org.pac4j.core.exception.CredentialsException;

public class BadCredentialsException
extends CredentialsException {
    private static final long serialVersionUID = 106849753775292065L;

    public BadCredentialsException(String message) {
        super(message);
    }

    public BadCredentialsException(Throwable t2) {
        super(t2);
    }

    public BadCredentialsException(String message, Throwable t2) {
        super(message, t2);
    }
}

