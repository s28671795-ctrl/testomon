/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GIOP;

import org.omg.CORBA.portable.IDLEntity;
import org.omg.GIOP.TargetAddress;

public final class LocateRequestHeader_1_2
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public int request_id;
    public TargetAddress target;

    public LocateRequestHeader_1_2() {
    }

    public LocateRequestHeader_1_2(int request_id, TargetAddress target) {
        this.request_id = request_id;
        this.target = target;
    }
}

