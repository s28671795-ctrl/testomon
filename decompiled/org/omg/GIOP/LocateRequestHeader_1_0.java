/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GIOP;

import org.omg.CORBA.portable.IDLEntity;

public final class LocateRequestHeader_1_0
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public int request_id;
    public byte[] object_key;

    public LocateRequestHeader_1_0() {
    }

    public LocateRequestHeader_1_0(int request_id, byte[] object_key) {
        this.request_id = request_id;
        this.object_key = object_key;
    }
}

