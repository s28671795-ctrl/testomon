/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.rt.security.claims;

import org.apache.cxf.rt.security.claims.Claim;

public class SAMLClaim
extends Claim {
    public static final String SAML_ROLE_ATTRIBUTENAME_DEFAULT = "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/role";
    private static final long serialVersionUID = 5530712294179589442L;
    private String nameFormat;
    private String name;
    private String friendlyName;

    public String getNameFormat() {
        return this.nameFormat;
    }

    public void setNameFormat(String nameFormat) {
        this.nameFormat = nameFormat;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFriendlyName() {
        return this.friendlyName;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    @Override
    public int hashCode() {
        int hashCode = super.hashCode();
        if (this.nameFormat != null) {
            hashCode = 31 * hashCode + this.nameFormat.hashCode();
        }
        if (this.name != null) {
            hashCode = 31 * hashCode + this.name.hashCode();
        }
        if (this.friendlyName != null) {
            hashCode = 31 * hashCode + this.friendlyName.hashCode();
        }
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof SAMLClaim)) {
            return false;
        }
        if (this.nameFormat == null && ((SAMLClaim)obj).getNameFormat() != null) {
            return false;
        }
        if (this.nameFormat != null && !this.nameFormat.equals(((SAMLClaim)obj).getNameFormat())) {
            return false;
        }
        if (this.name == null && ((SAMLClaim)obj).getName() != null) {
            return false;
        }
        if (this.name != null && !this.name.equals(((SAMLClaim)obj).getName())) {
            return false;
        }
        if (this.friendlyName == null && ((SAMLClaim)obj).getFriendlyName() != null) {
            return false;
        }
        return this.friendlyName == null || this.friendlyName.equals(((SAMLClaim)obj).getFriendlyName());
    }
}

