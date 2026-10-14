/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.auth;

import org.ldaptive.LdapException;
import org.ldaptive.auth.AuthenticationResponse;

public interface AuthenticationResponseHandler {
    public void handle(AuthenticationResponse var1) throws LdapException;
}

