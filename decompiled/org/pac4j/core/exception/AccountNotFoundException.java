/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.exception;

import org.pac4j.core.exception.CredentialsException;

public class AccountNotFoundException
extends CredentialsException {
    private static final long serialVersionUID = -2405351263139588633L;

    public AccountNotFoundException(String message) {
        super(message);
    }

    public AccountNotFoundException(Throwable t2) {
        super(t2);
    }
}

