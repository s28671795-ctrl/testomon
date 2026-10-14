/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.jaas;

import java.io.Serializable;
import java.security.Principal;
import org.ldaptive.LdapEntry;
import org.ldaptive.LdapUtils;

public class LdapPrincipal
implements Principal,
Serializable,
Comparable<Principal> {
    private static final int HASH_CODE_SEED = 419;
    private static final long serialVersionUID = 762147223399104252L;
    private final String ldapName;
    private final LdapEntry ldapEntry;

    public LdapPrincipal(String name, LdapEntry entry) {
        this.ldapName = name;
        this.ldapEntry = entry;
    }

    @Override
    public String getName() {
        return this.ldapName;
    }

    public LdapEntry getLdapEntry() {
        return this.ldapEntry;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof LdapPrincipal) {
            LdapPrincipal v = (LdapPrincipal)o;
            return LdapUtils.areEqual(this.ldapName, v.ldapName);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(419, this.ldapName);
    }

    @Override
    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "ldapName=" + this.ldapName + ", " + "ldapEntry=" + (this.ldapEntry != null ? this.ldapEntry : "") + "]";
    }

    @Override
    public int compareTo(Principal p) {
        return this.ldapName.compareTo(p.getName());
    }
}

