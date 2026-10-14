/*
 * Decompiled with CFR 0.152.
 */
package org.h2.security.auth;

public class AuthConfigException
extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AuthConfigException() {
    }

    public AuthConfigException(String string) {
        super(string);
    }

    public AuthConfigException(Throwable throwable) {
        super(throwable);
    }

    public AuthConfigException(String string, Throwable throwable) {
        super(string, throwable);
    }
}

