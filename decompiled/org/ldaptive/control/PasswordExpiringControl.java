/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.control;

import org.ldaptive.LdapUtils;
import org.ldaptive.asn1.DERBuffer;
import org.ldaptive.asn1.OctetStringType;
import org.ldaptive.control.AbstractControl;
import org.ldaptive.control.ResponseControl;

public class PasswordExpiringControl
extends AbstractControl
implements ResponseControl {
    public static final String OID = "2.16.840.1.113730.3.4.5";
    private static final int HASH_CODE_SEED = 797;
    private int timeBeforeExpiration;

    public PasswordExpiringControl() {
        super(OID);
    }

    public PasswordExpiringControl(boolean critical) {
        super(OID, critical);
    }

    public PasswordExpiringControl(int time) {
        super(OID);
        this.setTimeBeforeExpiration(time);
    }

    public PasswordExpiringControl(int time, boolean critical) {
        super(OID, critical);
        this.setTimeBeforeExpiration(time);
    }

    public int getTimeBeforeExpiration() {
        return this.timeBeforeExpiration;
    }

    public void setTimeBeforeExpiration(int time) {
        this.timeBeforeExpiration = time;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof PasswordExpiringControl && super.equals(o)) {
            PasswordExpiringControl v = (PasswordExpiringControl)o;
            return LdapUtils.areEqual(this.timeBeforeExpiration, v.timeBeforeExpiration);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(797, this.getOID(), this.getCriticality(), this.timeBeforeExpiration);
    }

    @Override
    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "criticality=" + this.getCriticality() + ", " + "timeBeforeExpiration=" + this.timeBeforeExpiration + "]";
    }

    @Override
    public void decode(DERBuffer encoded) {
        String time = OctetStringType.decode(encoded);
        this.setTimeBeforeExpiration(Integer.valueOf(time));
    }
}

