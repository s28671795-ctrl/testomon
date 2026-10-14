/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.control;

import org.ldaptive.LdapUtils;
import org.ldaptive.asn1.DERBuffer;
import org.ldaptive.asn1.OctetStringType;
import org.ldaptive.control.AbstractControl;
import org.ldaptive.control.ResponseControl;

public class PasswordExpiredControl
extends AbstractControl
implements ResponseControl {
    public static final String OID = "2.16.840.1.113730.3.4.4";
    private static final int HASH_CODE_SEED = 787;

    public PasswordExpiredControl() {
        super(OID);
    }

    public PasswordExpiredControl(boolean critical) {
        super(OID, critical);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        return o instanceof PasswordExpiredControl && super.equals(o);
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(787, this.getOID(), this.getCriticality());
    }

    @Override
    public void decode(DERBuffer encoded) {
        String value = OctetStringType.decode(encoded);
        if (!"0".equals(value)) {
            throw new IllegalArgumentException("Response control value should always be '0'");
        }
    }
}

