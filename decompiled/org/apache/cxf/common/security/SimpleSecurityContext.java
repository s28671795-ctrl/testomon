/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.security;

import java.security.Principal;
import org.apache.cxf.common.security.SimplePrincipal;
import org.apache.cxf.security.SecurityContext;

public class SimpleSecurityContext
implements SecurityContext {
    private Principal principal;

    public SimpleSecurityContext(String name) {
        this(new SimplePrincipal(name));
    }

    public SimpleSecurityContext(Principal principal) {
        this.principal = principal;
    }

    @Override
    public Principal getUserPrincipal() {
        return this.principal;
    }

    @Override
    public boolean isUserInRole(String role) {
        return false;
    }
}

