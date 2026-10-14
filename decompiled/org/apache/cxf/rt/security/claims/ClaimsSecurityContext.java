/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.rt.security.claims;

import org.apache.cxf.rt.security.claims.ClaimCollection;
import org.apache.cxf.security.LoginSecurityContext;

public interface ClaimsSecurityContext
extends LoginSecurityContext {
    public ClaimCollection getClaims();
}

