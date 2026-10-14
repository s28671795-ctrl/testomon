/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IOP;

import org.omg.CORBA.portable.IDLEntity;

public final class ServiceContext
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public int context_id;
    public byte[] context_data;

    public ServiceContext() {
    }

    public ServiceContext(int context_id, byte[] context_data) {
        this.context_id = context_id;
        this.context_data = context_data;
    }
}

