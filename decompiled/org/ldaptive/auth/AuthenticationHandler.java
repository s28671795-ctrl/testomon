/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.auth;

import org.ldaptive.LdapException;
import org.ldaptive.auth.AuthenticationCriteria;
import org.ldaptive.auth.AuthenticationHandlerResponse;

public interface AuthenticationHandler {
    public AuthenticationHandlerResponse authenticate(AuthenticationCriteria var1) throws LdapException;
}

