/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.auth;

import org.ldaptive.LdapException;
import org.ldaptive.auth.User;

public interface DnResolver {
    public String resolve(User var1) throws LdapException;
}

