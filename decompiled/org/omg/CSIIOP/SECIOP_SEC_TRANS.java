/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSIIOP;

import org.omg.CORBA.portable.IDLEntity;
import org.omg.CSIIOP.TransportAddress;

public final class SECIOP_SEC_TRANS
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public short target_supports;
    public short target_requires;
    public byte[] mech_oid;
    public byte[] target_name;
    public TransportAddress[] addresses;

    public SECIOP_SEC_TRANS() {
    }

    public SECIOP_SEC_TRANS(short target_supports, short target_requires, byte[] mech_oid, byte[] target_name, TransportAddress[] addresses) {
        this.target_supports = target_supports;
        this.target_requires = target_requires;
        this.mech_oid = mech_oid;
        this.target_name = target_name;
        this.addresses = addresses;
    }
}

