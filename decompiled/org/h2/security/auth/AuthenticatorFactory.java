/*
 * Decompiled with CFR 0.152.
 */
package org.h2.security.auth;

import org.h2.security.auth.Authenticator;
import org.h2.security.auth.DefaultAuthenticator;

public class AuthenticatorFactory {
    public static Authenticator createAuthenticator() {
        return DefaultAuthenticator.getInstance();
    }
}

