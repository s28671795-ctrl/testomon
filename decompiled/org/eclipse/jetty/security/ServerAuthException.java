/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.security;

import java.security.GeneralSecurityException;

public class ServerAuthException
extends GeneralSecurityException {
    public ServerAuthException() {
    }

    public ServerAuthException(String s2) {
        super(s2);
    }

    public ServerAuthException(String s2, Throwable throwable) {
        super(s2, throwable);
    }

    public ServerAuthException(Throwable throwable) {
        super(throwable);
    }
}

