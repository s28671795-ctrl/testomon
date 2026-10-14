/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.exception;

import org.pac4j.core.exception.TechnicalException;

public class CredentialsException
extends TechnicalException {
    private static final long serialVersionUID = 6013115966613706463L;

    public CredentialsException(String message) {
        super(message);
    }

    public CredentialsException(Throwable t2) {
        super(t2);
    }

    public CredentialsException(String message, Throwable t2) {
        super(message, t2);
    }
}

