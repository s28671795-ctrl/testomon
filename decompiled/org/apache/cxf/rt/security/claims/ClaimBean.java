/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.rt.security.claims;

import org.apache.cxf.rt.security.claims.Claim;
import org.apache.cxf.security.claims.authorization.ClaimMode;

public class ClaimBean {
    private final Claim claim;
    private final String claimFormat;
    private final ClaimMode claimMode;
    private final boolean matchAll;

    public ClaimBean(Claim claim, String claimFormat, ClaimMode claimMode, boolean matchAll) {
        this.claim = claim;
        this.claimFormat = claimFormat;
        this.claimMode = claimMode;
        this.matchAll = matchAll;
    }

    public String getClaimFormat() {
        return this.claimFormat;
    }

    public Claim getClaim() {
        return this.claim;
    }

    public boolean isMatchAll() {
        return this.matchAll;
    }

    public ClaimMode getClaimMode() {
        return this.claimMode;
    }
}

