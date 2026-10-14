/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.jaas;

import java.io.Serializable;
import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.ldaptive.LdapUtils;

public class LdapGroup
implements Principal,
Serializable {
    private static final int HASH_CODE_SEED = 431;
    private static final long serialVersionUID = 7947097145323474960L;
    private final String groupName;
    private final Set<Principal> members = new HashSet<Principal>();

    public LdapGroup(String name) {
        this.groupName = name;
    }

    @Override
    public String getName() {
        return this.groupName;
    }

    public void addMember(Principal user) {
        this.members.add(user);
    }

    public void removeMember(Principal user) {
        this.members.remove(user);
    }

    public boolean isMember(Principal member) {
        for (Principal p : this.members) {
            if (p.getName() == null || !p.getName().equals(member.getName())) continue;
            return true;
        }
        return false;
    }

    public Set<Principal> getMembers() {
        return Collections.unmodifiableSet(this.members);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof LdapGroup) {
            LdapGroup v = (LdapGroup)o;
            return LdapUtils.areEqual(this.groupName, v.groupName) && LdapUtils.areEqual(this.members, v.members);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(431, this.groupName, this.members);
    }

    @Override
    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "groupName=" + this.groupName + ", " + "members=" + this.members + "]";
    }
}

