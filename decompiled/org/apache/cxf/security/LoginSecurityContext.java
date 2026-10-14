/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.security;

import java.security.Principal;
import java.util.Set;
import javax.security.auth.Subject;
import org.apache.cxf.security.SecurityContext;

public interface LoginSecurityContext
extends SecurityContext {
    public Subject getSubject();

    public Set<Principal> getUserRoles();
}

