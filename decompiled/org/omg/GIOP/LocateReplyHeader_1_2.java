/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GIOP;

import org.omg.CORBA.portable.IDLEntity;
import org.omg.GIOP.LocateStatusType_1_2;

public final class LocateReplyHeader_1_2
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public int request_id;
    public LocateStatusType_1_2 locate_status;

    public LocateReplyHeader_1_2() {
    }

    public LocateReplyHeader_1_2(int request_id, LocateStatusType_1_2 locate_status) {
        this.request_id = request_id;
        this.locate_status = locate_status;
    }
}

