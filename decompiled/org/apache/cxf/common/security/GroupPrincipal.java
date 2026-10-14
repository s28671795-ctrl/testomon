/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.security;

import java.security.Principal;
import java.util.Enumeration;

public interface GroupPrincipal
extends Principal {
    public boolean addMember(Principal var1);

    public boolean removeMember(Principal var1);

    public boolean isMember(Principal var1);

    public Enumeration<? extends Principal> members();
}

