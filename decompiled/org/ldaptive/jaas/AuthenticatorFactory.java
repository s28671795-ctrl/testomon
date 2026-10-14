/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.jaas;

import java.util.Map;
import org.ldaptive.auth.AuthenticationRequest;
import org.ldaptive.auth.Authenticator;

public interface AuthenticatorFactory {
    public Authenticator createAuthenticator(Map<String, ?> var1);

    public AuthenticationRequest createAuthenticationRequest(Map<String, ?> var1);
}

