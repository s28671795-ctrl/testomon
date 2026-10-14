/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.auth;

import org.ldaptive.LdapEntry;
import org.ldaptive.LdapException;
import org.ldaptive.auth.AuthenticationCriteria;
import org.ldaptive.auth.AuthenticationHandlerResponse;

public interface EntryResolver {
    public LdapEntry resolve(AuthenticationCriteria var1, AuthenticationHandlerResponse var2) throws LdapException;
}

