/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSIIOP;

import org.omg.CORBA.portable.IDLEntity;

public final class AS_ContextSec
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public short target_supports;
    public short target_requires;
    public byte[] client_authentication_mech;
    public byte[] target_name;

    public AS_ContextSec() {
    }

    public AS_ContextSec(short target_supports, short target_requires, byte[] client_authentication_mech, byte[] target_name) {
        this.target_supports = target_supports;
        this.target_requires = target_requires;
        this.client_authentication_mech = client_authentication_mech;
        this.target_name = target_name;
    }
}

