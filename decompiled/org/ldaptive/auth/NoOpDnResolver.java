/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.auth;

import org.ldaptive.LdapException;
import org.ldaptive.auth.DnResolver;
import org.ldaptive.auth.User;

public class NoOpDnResolver
implements DnResolver {
    @Override
    public String resolve(User user) throws LdapException {
        return user != null ? user.getIdentifier() : null;
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "]";
    }
}

