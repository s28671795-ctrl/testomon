/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.jaas;

import java.io.Serializable;
import java.security.Principal;
import org.ldaptive.LdapEntry;
import org.ldaptive.LdapUtils;

public class LdapDnPrincipal
implements Principal,
Serializable,
Comparable<Principal> {
    private static final int HASH_CODE_SEED = 409;
    private static final long serialVersionUID = 8345846704852267195L;
    private final String ldapDn;
    private final LdapEntry ldapEntry;

    public LdapDnPrincipal(String name, LdapEntry entry) {
        this.ldapDn = name;
        this.ldapEntry = entry;
    }

    @Override
    public String getName() {
        return this.ldapDn;
    }

    public LdapEntry getLdapEntry() {
        return this.ldapEntry;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof LdapDnPrincipal) {
            LdapDnPrincipal v = (LdapDnPrincipal)o;
            return LdapUtils.areEqual(this.ldapDn, v.ldapDn);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(409, this.ldapDn);
    }

    @Override
    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "ldapDn=" + this.ldapDn + ", " + "ldapEntry=" + (this.ldapEntry != null ? this.ldapEntry : "") + "]";
    }

    @Override
    public int compareTo(Principal p) {
        return this.ldapDn.compareTo(p.getName());
    }
}

