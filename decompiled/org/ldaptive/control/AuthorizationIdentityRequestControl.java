/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.control;

import org.ldaptive.LdapUtils;
import org.ldaptive.control.AbstractControl;
import org.ldaptive.control.RequestControl;

public class AuthorizationIdentityRequestControl
extends AbstractControl
implements RequestControl {
    public static final String OID = "2.16.840.1.113730.3.4.16";
    private static final int HASH_CODE_SEED = 7013;

    public AuthorizationIdentityRequestControl() {
        super(OID);
    }

    public AuthorizationIdentityRequestControl(boolean critical) {
        super(OID, critical);
    }

    @Override
    public boolean hasValue() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        return o instanceof AuthorizationIdentityRequestControl && super.equals(o);
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(7013, this.getOID(), this.getCriticality());
    }

    @Override
    public byte[] encode() {
        return null;
    }
}

