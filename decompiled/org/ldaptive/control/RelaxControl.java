/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.control;

import org.ldaptive.LdapUtils;
import org.ldaptive.control.AbstractControl;
import org.ldaptive.control.RequestControl;

public class RelaxControl
extends AbstractControl
implements RequestControl {
    public static final String OID = "1.3.6.1.4.1.4203.666.5.12";
    private static final int HASH_CODE_SEED = 10711;

    public RelaxControl() {
        super(OID, true);
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
        return o instanceof RelaxControl && super.equals(o);
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(10711, this.getOID(), this.getCriticality());
    }

    @Override
    public byte[] encode() {
        return null;
    }
}

