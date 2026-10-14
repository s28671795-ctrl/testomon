/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ad.control;

import org.ldaptive.LdapUtils;
import org.ldaptive.asn1.ConstructedDEREncoder;
import org.ldaptive.asn1.IntegerType;
import org.ldaptive.asn1.OctetStringType;
import org.ldaptive.asn1.UniversalDERTag;
import org.ldaptive.control.AbstractControl;
import org.ldaptive.control.RequestControl;

public class VerifyNameControl
extends AbstractControl
implements RequestControl {
    public static final String OID = "1.2.840.113556.1.4.1338";
    private static final int HASH_CODE_SEED = 977;
    private String serverName;

    public VerifyNameControl() {
        super(OID);
    }

    public VerifyNameControl(String name) {
        super(OID);
        this.setServerName(name);
    }

    public VerifyNameControl(String name, boolean critical) {
        super(OID, critical);
        this.setServerName(name);
    }

    @Override
    public boolean hasValue() {
        return true;
    }

    public String getServerName() {
        return this.serverName;
    }

    public void setServerName(String name) {
        this.serverName = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof VerifyNameControl && super.equals(o)) {
            VerifyNameControl v = (VerifyNameControl)o;
            return LdapUtils.areEqual(this.serverName, v.serverName);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(977, this.getOID(), this.getCriticality(), this.serverName);
    }

    @Override
    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "criticality=" + this.getCriticality() + ", " + "serverName=" + this.serverName + "]";
    }

    @Override
    public byte[] encode() {
        ConstructedDEREncoder se = new ConstructedDEREncoder(UniversalDERTag.SEQ, new IntegerType(0), new OctetStringType(this.serverName));
        return se.encode();
    }
}

