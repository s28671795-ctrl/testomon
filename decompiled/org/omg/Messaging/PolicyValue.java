/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Messaging;

import org.omg.CORBA.portable.IDLEntity;

public final class PolicyValue
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public int ptype;
    public byte[] pvalue;

    public PolicyValue() {
    }

    public PolicyValue(int ptype, byte[] pvalue) {
        this.ptype = ptype;
        this.pvalue = pvalue;
    }
}

