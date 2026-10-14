/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.auth;

import org.ldaptive.LdapEntry;
import org.ldaptive.auth.AuthenticationCriteria;
import org.ldaptive.auth.AuthenticationHandlerResponse;
import org.ldaptive.auth.EntryResolver;

public class NoOpEntryResolver
implements EntryResolver {
    @Override
    public LdapEntry resolve(AuthenticationCriteria criteria, AuthenticationHandlerResponse response) {
        return (LdapEntry)LdapEntry.builder().dn(criteria.getDn()).build();
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "]";
    }
}

