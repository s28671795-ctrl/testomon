/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ad.control;

import org.ldaptive.LdapUtils;
import org.ldaptive.control.AbstractControl;
import org.ldaptive.control.RequestControl;

public class ShowDeactivatedLinkControl
extends AbstractControl
implements RequestControl {
    public static final String OID = "1.2.840.113556.1.4.2065";
    private static final int HASH_CODE_SEED = 967;

    public ShowDeactivatedLinkControl() {
        super(OID);
    }

    public ShowDeactivatedLinkControl(boolean critical) {
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
        return o instanceof ShowDeactivatedLinkControl && super.equals(o);
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(967, this.getOID(), this.getCriticality());
    }

    @Override
    public byte[] encode() {
        return null;
    }
}

