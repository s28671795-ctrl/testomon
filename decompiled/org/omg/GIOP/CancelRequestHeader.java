/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GIOP;

import org.omg.CORBA.portable.IDLEntity;

public final class CancelRequestHeader
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public int request_id;

    public CancelRequestHeader() {
    }

    public CancelRequestHeader(int request_id) {
        this.request_id = request_id;
    }
}

