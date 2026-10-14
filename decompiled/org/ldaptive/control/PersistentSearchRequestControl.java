/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.control;

import java.util.EnumSet;
import org.ldaptive.LdapUtils;
import org.ldaptive.asn1.BooleanType;
import org.ldaptive.asn1.ConstructedDEREncoder;
import org.ldaptive.asn1.IntegerType;
import org.ldaptive.asn1.UniversalDERTag;
import org.ldaptive.control.AbstractControl;
import org.ldaptive.control.PersistentSearchChangeType;
import org.ldaptive.control.RequestControl;

public class PersistentSearchRequestControl
extends AbstractControl
implements RequestControl {
    public static final String OID = "2.16.840.1.113730.3.4.3";
    private static final int HASH_CODE_SEED = 761;
    private EnumSet<PersistentSearchChangeType> changeTypes;
    private boolean changesOnly;
    private boolean returnEcs;

    public PersistentSearchRequestControl() {
        super(OID);
    }

    public PersistentSearchRequestControl(EnumSet<PersistentSearchChangeType> types) {
        super(OID);
        this.setChangeTypes(types);
    }

    public PersistentSearchRequestControl(EnumSet<PersistentSearchChangeType> types, boolean critical) {
        super(OID, critical);
        this.setChangeTypes(types);
    }

    public PersistentSearchRequestControl(EnumSet<PersistentSearchChangeType> types, boolean co, boolean re) {
        super(OID);
        this.setChangeTypes(types);
        this.setChangesOnly(co);
        this.setReturnEcs(re);
    }

    public PersistentSearchRequestControl(EnumSet<PersistentSearchChangeType> types, boolean co, boolean re, boolean critical) {
        super(OID, critical);
        this.setChangeTypes(types);
        this.setChangesOnly(co);
        this.setReturnEcs(re);
    }

    @Override
    public boolean hasValue() {
        return true;
    }

    public EnumSet<PersistentSearchChangeType> getChangeTypes() {
        return this.changeTypes;
    }

    public void setChangeTypes(EnumSet<PersistentSearchChangeType> types) {
        this.changeTypes = types;
    }

    public boolean getChangesOnly() {
        return this.changesOnly;
    }

    public void setChangesOnly(boolean b) {
        this.changesOnly = b;
    }

    public boolean getReturnEcs() {
        return this.returnEcs;
    }

    public void setReturnEcs(boolean b) {
        this.returnEcs = b;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof PersistentSearchRequestControl && super.equals(o)) {
            PersistentSearchRequestControl v = (PersistentSearchRequestControl)o;
            return LdapUtils.areEqual(this.changeTypes, v.changeTypes) && LdapUtils.areEqual(this.changesOnly, v.changesOnly) && LdapUtils.areEqual(this.returnEcs, v.returnEcs);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(761, this.getOID(), this.getCriticality(), this.changeTypes, this.changesOnly, this.returnEcs);
    }

    @Override
    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "criticality=" + this.getCriticality() + ", " + "changeTypes=" + this.changeTypes + ", " + "changesOnly=" + this.changesOnly + ", " + "returnEcs=" + this.returnEcs + "]";
    }

    @Override
    public byte[] encode() {
        int types = 0;
        for (PersistentSearchChangeType type : this.getChangeTypes()) {
            types |= type.value();
        }
        ConstructedDEREncoder se = new ConstructedDEREncoder(UniversalDERTag.SEQ, new IntegerType(types), new BooleanType(this.getChangesOnly()), new BooleanType(this.getReturnEcs()));
        return se.encode();
    }
}

