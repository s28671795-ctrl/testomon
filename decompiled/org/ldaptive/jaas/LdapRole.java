/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.jaas;

import java.io.Serializable;
import java.security.Principal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.ldaptive.LdapAttribute;
import org.ldaptive.LdapEntry;
import org.ldaptive.LdapUtils;
import org.ldaptive.SearchResponse;

public class LdapRole
implements Principal,
Serializable,
Comparable<Principal> {
    private static final int HASH_CODE_SEED = 421;
    private static final long serialVersionUID = 1578734888816839199L;
    private final String roleName;

    public LdapRole(String name) {
        this.roleName = name;
    }

    @Override
    public String getName() {
        return this.roleName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof LdapRole) {
            LdapRole v = (LdapRole)o;
            return LdapUtils.areEqual(this.roleName, v.roleName);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(421, this.roleName);
    }

    @Override
    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "roleName=" + this.roleName + "]";
    }

    @Override
    public int compareTo(Principal p) {
        return this.roleName.compareTo(p.getName());
    }

    public static Set<LdapRole> toRoles(SearchResponse result) {
        HashSet<LdapRole> r = new HashSet<LdapRole>();
        for (LdapEntry le : result.getEntries()) {
            r.addAll(LdapRole.toRoles(le));
        }
        return r;
    }

    public static Set<LdapRole> toRoles(LdapEntry entry) {
        return LdapRole.toRoles(entry.getAttributes());
    }

    public static Set<LdapRole> toRoles(Collection<LdapAttribute> attributes) {
        HashSet<LdapRole> r = new HashSet<LdapRole>();
        if (attributes != null) {
            for (LdapAttribute ldapAttr : attributes) {
                r.addAll(ldapAttr.getStringValues().stream().map(LdapRole::new).collect(Collectors.toList()));
            }
        }
        return r;
    }
}

