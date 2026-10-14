/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.control;

import org.ldaptive.LdapUtils;
import org.ldaptive.asn1.DERBuffer;
import org.ldaptive.control.AbstractControl;
import org.ldaptive.control.RequestControl;
import org.ldaptive.control.ResponseControl;

public class GenericControl
extends AbstractControl
implements RequestControl,
ResponseControl {
    private static final int HASH_CODE_SEED = 7039;
    private byte[] value;

    public GenericControl(String oid, byte[] encoded) {
        this(oid, false, encoded);
    }

    public GenericControl(String oid, DERBuffer encoded) {
        this(oid, false, encoded);
    }

    public GenericControl(String oid, boolean critical, byte[] encoded) {
        super(oid, critical);
        this.value = encoded;
    }

    public GenericControl(String oid, boolean critical, DERBuffer encoded) {
        super(oid, critical);
        this.decode(encoded);
    }

    @Override
    public boolean hasValue() {
        return this.value != null;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof GenericControl && super.equals(o)) {
            GenericControl v = (GenericControl)o;
            return LdapUtils.areEqual(this.value, v.value);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(7039, this.getOID(), this.getCriticality(), this.value);
    }

    @Override
    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "criticality=" + this.getCriticality() + ", " + "oid=" + this.getOID() + ", " + "value=" + LdapUtils.base64Encode(this.value) + "]";
    }

    @Override
    public byte[] encode() {
        return this.value;
    }

    @Override
    public void decode(DERBuffer encoded) {
        this.value = encoded.getRemainingBytes();
    }
}

