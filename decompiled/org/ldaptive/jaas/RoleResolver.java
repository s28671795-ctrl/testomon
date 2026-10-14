/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.jaas;

import java.util.Set;
import org.ldaptive.LdapException;
import org.ldaptive.SearchRequest;
import org.ldaptive.jaas.LdapRole;

public interface RoleResolver {
    public Set<LdapRole> search(SearchRequest var1) throws LdapException;
}

