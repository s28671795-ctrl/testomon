/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.security;

import java.security.Principal;

public interface SecurityContext {
    public Principal getUserPrincipal();

    public boolean isUserInRole(String var1);
}

