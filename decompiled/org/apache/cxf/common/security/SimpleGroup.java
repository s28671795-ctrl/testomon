/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.security;

import java.security.Principal;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.apache.cxf.common.security.GroupPrincipal;
import org.apache.cxf.common.security.SimplePrincipal;

public class SimpleGroup
extends SimplePrincipal
implements GroupPrincipal {
    private static final long serialVersionUID = 1L;
    private Set<Principal> members = new HashSet<Principal>();

    public SimpleGroup(String groupName) {
        super(groupName);
    }

    public SimpleGroup(String groupName, String memberName) {
        super(groupName);
        this.members.add(new SimplePrincipal(memberName));
    }

    public SimpleGroup(String groupName, Principal member) {
        super(groupName);
        this.members.add(member);
    }

    @Override
    public boolean isMember(Principal p) {
        return this.members.contains(p);
    }

    @Override
    public boolean addMember(Principal p) {
        return this.members.add(p);
    }

    @Override
    public Enumeration<? extends Principal> members() {
        final Iterator<Principal> it = this.members.iterator();
        return new Enumeration<Principal>(){

            @Override
            public boolean hasMoreElements() {
                return it.hasNext();
            }

            @Override
            public Principal nextElement() {
                return (Principal)it.next();
            }
        };
    }

    @Override
    public boolean removeMember(Principal p) {
        return this.members.remove(p);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SimpleGroup)) {
            return false;
        }
        SimpleGroup other = (SimpleGroup)obj;
        return this.members.equals(other.members) && super.equals(obj);
    }

    @Override
    public int hashCode() {
        return this.getName().hashCode() + 37 * this.members.hashCode();
    }
}

