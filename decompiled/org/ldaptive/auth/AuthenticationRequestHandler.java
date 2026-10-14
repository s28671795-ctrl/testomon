/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.auth;

import org.ldaptive.LdapException;
import org.ldaptive.auth.AuthenticationRequest;

public interface AuthenticationRequestHandler {
    public void handle(String var1, AuthenticationRequest var2) throws LdapException;
}

