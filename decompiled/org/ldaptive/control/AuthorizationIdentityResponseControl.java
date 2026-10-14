/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.control;

import org.ldaptive.LdapUtils;
import org.ldaptive.asn1.DERBuffer;
import org.ldaptive.asn1.OctetStringType;
import org.ldaptive.control.AbstractControl;
import org.ldaptive.control.ResponseControl;

public class AuthorizationIdentityResponseControl
extends AbstractControl
implements ResponseControl {
    public static final String OID = "2.16.840.1.113730.3.4.15";
    private static final int HASH_CODE_SEED = 7019;
    private String authorizationId;

    public AuthorizationIdentityResponseControl() {
        super(OID);
    }

    public AuthorizationIdentityResponseControl(boolean critical) {
        super(OID, critical);
    }

    public AuthorizationIdentityResponseControl(String id) {
        this(id, false);
    }

    public AuthorizationIdentityResponseControl(String id, boolean critical) {
        super(OID, critical);
        this.setAuthorizationId(id);
    }

    public String getAuthorizationId() {
        return this.authorizationId;
    }

    public void setAuthorizationId(String id) {
        this.authorizationId = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof AuthorizationIdentityResponseControl && super.equals(o)) {
            AuthorizationIdentityResponseControl v = (AuthorizationIdentityResponseControl)o;
            return LdapUtils.areEqual(this.authorizationId, v.authorizationId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(7019, this.getOID(), this.getCriticality(), this.authorizationId);
    }

    @Override
    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "criticality=" + this.getCriticality() + ", " + "authorizationId=" + this.authorizationId + "]";
    }

    @Override
    public void decode(DERBuffer encoded) {
        this.setAuthorizationId(OctetStringType.decode(encoded));
    }
}

