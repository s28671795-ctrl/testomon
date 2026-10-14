/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.control;

import org.ldaptive.LdapUtils;
import org.ldaptive.asn1.OctetStringType;
import org.ldaptive.control.AbstractControl;
import org.ldaptive.control.RequestControl;

public class ProxyAuthorizationControl
extends AbstractControl
implements RequestControl {
    public static final String OID = "2.16.840.1.113730.3.4.18";
    private static final int HASH_CODE_SEED = 7001;
    private static final byte[] EMPTY_AUTHZ = new byte[0];
    private String authorizationId;

    public ProxyAuthorizationControl() {
        super(OID, true);
    }

    public ProxyAuthorizationControl(String id) {
        super(OID, true);
        this.setAuthorizationId(id);
    }

    @Override
    public boolean hasValue() {
        return true;
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
        if (o instanceof ProxyAuthorizationControl && super.equals(o)) {
            ProxyAuthorizationControl v = (ProxyAuthorizationControl)o;
            return LdapUtils.areEqual(this.authorizationId, v.authorizationId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(7001, this.getOID(), this.getCriticality(), this.authorizationId);
    }

    @Override
    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "criticality=" + this.getCriticality() + ", " + "authorizationId=" + this.authorizationId + "]";
    }

    @Override
    public byte[] encode() {
        return this.getAuthorizationId() != null ? OctetStringType.toBytes(this.getAuthorizationId()) : EMPTY_AUTHZ;
    }
}

