/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.control;

import java.util.ArrayList;
import java.util.Arrays;
import org.ldaptive.LdapUtils;
import org.ldaptive.asn1.AbstractDERType;
import org.ldaptive.asn1.ConstructedDEREncoder;
import org.ldaptive.asn1.ContextType;
import org.ldaptive.asn1.DEREncoder;
import org.ldaptive.asn1.OctetStringType;
import org.ldaptive.asn1.UniversalDERTag;
import org.ldaptive.control.AbstractControl;
import org.ldaptive.control.RequestControl;
import org.ldaptive.control.SortKey;

public class SortRequestControl
extends AbstractControl
implements RequestControl {
    public static final String OID = "1.2.840.113556.1.4.473";
    private static final int HASH_CODE_SEED = 727;
    private SortKey[] sortKeys;

    public SortRequestControl() {
        super(OID);
    }

    public SortRequestControl(SortKey[] keys2) {
        super(OID);
        this.setSortKeys(keys2);
    }

    public SortRequestControl(SortKey[] keys2, boolean critical) {
        super(OID, critical);
        this.setSortKeys(keys2);
    }

    @Override
    public boolean hasValue() {
        return true;
    }

    public SortKey[] getSortKeys() {
        return this.sortKeys;
    }

    public void setSortKeys(SortKey[] keys2) {
        this.sortKeys = keys2;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof SortRequestControl && super.equals(o)) {
            SortRequestControl v = (SortRequestControl)o;
            return LdapUtils.areEqual(this.sortKeys, v.sortKeys);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(727, this.getOID(), this.getCriticality(), this.sortKeys);
    }

    @Override
    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "criticality=" + this.getCriticality() + ", " + "sortKeys=" + Arrays.toString(this.sortKeys) + "]";
    }

    @Override
    public byte[] encode() {
        DEREncoder[] keyEncoders = new DEREncoder[this.sortKeys.length];
        for (int i = 0; i < this.sortKeys.length; ++i) {
            ArrayList<AbstractDERType> l = new ArrayList<AbstractDERType>();
            l.add(new OctetStringType(this.sortKeys[i].getAttributeDescription()));
            if (this.sortKeys[i].getMatchingRuleId() != null) {
                l.add(new ContextType(0, this.sortKeys[i].getMatchingRuleId()));
            }
            if (this.sortKeys[i].getReverseOrder()) {
                l.add(new ContextType(1, this.sortKeys[i].getReverseOrder()));
            }
            keyEncoders[i] = new ConstructedDEREncoder(UniversalDERTag.SEQ, l.toArray(new DEREncoder[0]));
        }
        ConstructedDEREncoder se = new ConstructedDEREncoder(UniversalDERTag.SEQ, keyEncoders);
        return se.encode();
    }
}

