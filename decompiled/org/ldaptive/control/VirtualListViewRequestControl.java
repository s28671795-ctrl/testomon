/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.control;

import java.util.ArrayList;
import org.ldaptive.LdapUtils;
import org.ldaptive.asn1.AbstractDERType;
import org.ldaptive.asn1.ConstructedDEREncoder;
import org.ldaptive.asn1.ContextDERTag;
import org.ldaptive.asn1.DEREncoder;
import org.ldaptive.asn1.DERTag;
import org.ldaptive.asn1.IntegerType;
import org.ldaptive.asn1.OctetStringType;
import org.ldaptive.asn1.UniversalDERTag;
import org.ldaptive.control.AbstractControl;
import org.ldaptive.control.RequestControl;

public class VirtualListViewRequestControl
extends AbstractControl
implements RequestControl {
    public static final String OID = "2.16.840.1.113730.3.4.9";
    private static final int HASH_CODE_SEED = 769;
    private int beforeCount;
    private int afterCount;
    private int targetOffset;
    private int contentCount;
    private String assertionValue;
    private byte[] contextID;

    public VirtualListViewRequestControl() {
        super(OID);
    }

    public VirtualListViewRequestControl(int offset, int before, int after) {
        this(offset, before, after, 0, null, false);
    }

    public VirtualListViewRequestControl(int offset, int before, int after, boolean critical) {
        this(offset, before, after, 0, null, critical);
    }

    public VirtualListViewRequestControl(int offset, int before, int after, int count, byte[] context) {
        this(offset, before, after, count, context, false);
    }

    public VirtualListViewRequestControl(int offset, int before, int after, int count, byte[] context, boolean critical) {
        super(OID, critical);
        this.setTargetOffset(offset);
        this.setBeforeCount(before);
        this.setAfterCount(after);
        this.setContentCount(count);
        this.setContextID(context);
    }

    public VirtualListViewRequestControl(String assertion, int before, int after) {
        this(assertion, before, after, null, false);
    }

    public VirtualListViewRequestControl(String assertion, int before, int after, boolean critical) {
        this(assertion, before, after, null, critical);
    }

    public VirtualListViewRequestControl(String assertion, int before, int after, byte[] context) {
        this(assertion, before, after, context, false);
    }

    public VirtualListViewRequestControl(String assertion, int before, int after, byte[] context, boolean critical) {
        super(OID, critical);
        this.setAssertionValue(assertion);
        this.setBeforeCount(before);
        this.setAfterCount(after);
        this.setContextID(context);
    }

    @Override
    public boolean hasValue() {
        return true;
    }

    public int getBeforeCount() {
        return this.beforeCount;
    }

    public void setBeforeCount(int count) {
        this.beforeCount = count;
    }

    public int getAfterCount() {
        return this.afterCount;
    }

    public void setAfterCount(int count) {
        this.afterCount = count;
    }

    public int getTargetOffset() {
        return this.targetOffset;
    }

    public void setTargetOffset(int offset) {
        this.targetOffset = offset;
    }

    public int getContentCount() {
        return this.contentCount;
    }

    public void setContentCount(int count) {
        this.contentCount = count;
    }

    public String getAssertionValue() {
        return this.assertionValue;
    }

    public void setAssertionValue(String value) {
        this.assertionValue = value;
    }

    public byte[] getContextID() {
        return this.contextID;
    }

    public void setContextID(byte[] id) {
        this.contextID = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof VirtualListViewRequestControl && super.equals(o)) {
            VirtualListViewRequestControl v = (VirtualListViewRequestControl)o;
            return LdapUtils.areEqual(this.beforeCount, v.beforeCount) && LdapUtils.areEqual(this.afterCount, v.afterCount) && LdapUtils.areEqual(this.targetOffset, v.targetOffset) && LdapUtils.areEqual(this.contentCount, v.contentCount) && LdapUtils.areEqual(this.assertionValue, v.assertionValue) && LdapUtils.areEqual(this.contextID, v.contextID);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(769, this.getOID(), this.getCriticality(), this.beforeCount, this.afterCount, this.targetOffset, this.contentCount, this.assertionValue, this.contextID);
    }

    @Override
    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "criticality=" + this.getCriticality() + ", " + "beforeCount=" + this.beforeCount + ", " + "afterCount=" + this.afterCount + ", " + "targetOffset=" + this.targetOffset + ", " + "contentCount=" + this.contentCount + ", " + "assertionValue=" + LdapUtils.base64Encode(this.contextID) + "]";
    }

    @Override
    public byte[] encode() {
        ArrayList<AbstractDERType> l = new ArrayList<AbstractDERType>();
        if (this.getAssertionValue() != null) {
            l.add(new IntegerType(this.getBeforeCount()));
            l.add(new IntegerType(this.getAfterCount()));
            l.add(new OctetStringType((DERTag)new ContextDERTag(1, false), this.getAssertionValue()));
            if (this.getContextID() != null) {
                l.add(new OctetStringType(this.getContextID()));
            }
        } else {
            l.add(new IntegerType(this.getBeforeCount()));
            l.add(new IntegerType(this.getAfterCount()));
            l.add(new ConstructedDEREncoder(new ContextDERTag(0, true), new IntegerType(this.getTargetOffset()), new IntegerType(this.getContentCount())));
            if (this.getContextID() != null) {
                l.add(new OctetStringType(this.getContextID()));
            }
        }
        ConstructedDEREncoder se = new ConstructedDEREncoder(UniversalDERTag.SEQ, l.toArray(new DEREncoder[0]));
        return se.encode();
    }
}

