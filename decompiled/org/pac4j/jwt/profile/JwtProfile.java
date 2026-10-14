/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.jwt.profile;

import org.pac4j.core.profile.jwt.AbstractJwtProfile;

public class JwtProfile
extends AbstractJwtProfile {
    private static final long serialVersionUID = -1688563185891542198L;

    public String getJwtId() {
        return (String)this.getAttribute("jti");
    }
}

