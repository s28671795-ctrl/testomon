/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.profile.jwt;

import java.util.Date;
import java.util.List;
import org.pac4j.core.profile.CommonProfile;

public abstract class AbstractJwtProfile
extends CommonProfile {
    private static final long serialVersionUID = -6146872796913837767L;

    public String getSubject() {
        return this.getId();
    }

    public String getIssuer() {
        return (String)this.getAttribute("iss");
    }

    public List<String> getAudience() {
        return this.extractAttributeValues("aud");
    }

    public Date getExpirationDate() {
        return (Date)this.getAttribute("exp");
    }

    public Date getNotBefore() {
        return (Date)this.getAttribute("nbf");
    }

    public Date getIssuedAt() {
        return (Date)this.getAttribute("iat");
    }
}

