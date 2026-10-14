/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSIIOP;

import org.omg.CORBA.portable.IDLEntity;
import org.omg.CSIIOP.TransportAddress;

public final class TLS_SEC_TRANS
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public short target_supports;
    public short target_requires;
    public TransportAddress[] addresses;

    public TLS_SEC_TRANS() {
    }

    public TLS_SEC_TRANS(short target_supports, short target_requires, TransportAddress[] addresses) {
        this.target_supports = target_supports;
        this.target_requires = target_requires;
        this.addresses = addresses;
    }
}

