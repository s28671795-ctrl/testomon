/*
 * Decompiled with CFR 0.152.
 */
package org.h2.security.auth;

public class AuthenticationException
extends Exception {
    private static final long serialVersionUID = 1L;

    public AuthenticationException() {
    }

    public AuthenticationException(String string) {
        super(string);
    }

    public AuthenticationException(Throwable throwable) {
        super(throwable);
    }

    public AuthenticationException(String string, Throwable throwable) {
        super(string, throwable);
    }
}

