/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ad.control;

import org.ldaptive.LdapUtils;
import org.ldaptive.asn1.ConstructedDEREncoder;
import org.ldaptive.asn1.IntegerType;
import org.ldaptive.asn1.UniversalDERTag;
import org.ldaptive.control.AbstractControl;
import org.ldaptive.control.RequestControl;

public class SearchOptionsControl
extends AbstractControl
implements RequestControl {
    public static final String OID = "1.2.840.113556.1.4.1340";
    private static final int HASH_CODE_SEED = 953;
    private Flag flag = Flag.DOMAIN_SCOPE;

    public SearchOptionsControl() {
        super(OID);
    }

    public SearchOptionsControl(Flag f) {
        super(OID);
        this.setFlag(f);
    }

    public SearchOptionsControl(Flag f, boolean critical) {
        super(OID, critical);
        this.setFlag(f);
    }

    @Override
    public boolean hasValue() {
        return true;
    }

    public Flag getFlag() {
        return this.flag;
    }

    public void setFlag(Flag f) {
        this.flag = f;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof SearchOptionsControl && super.equals(o)) {
            SearchOptionsControl v = (SearchOptionsControl)o;
            return LdapUtils.areEqual((Object)this.flag, (Object)v.flag);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(953, new Object[]{this.getOID(), this.getCriticality(), this.flag});
    }

    @Override
    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "criticality=" + this.getCriticality() + ", " + "flag=" + (Object)((Object)this.flag) + "]";
    }

    @Override
    public byte[] encode() {
        ConstructedDEREncoder se = new ConstructedDEREncoder(UniversalDERTag.SEQ, new IntegerType(this.getFlag().ordinal()));
        return se.encode();
    }

    public static enum Flag {
        DOMAIN_SCOPE,
        PHANTOM_ROOT;

    }
}

