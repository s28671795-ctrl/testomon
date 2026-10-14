/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GIOP;

import org.omg.CORBA.portable.IDLEntity;
import org.omg.GIOP.ReplyStatusType_1_0;
import org.omg.IOP.ServiceContext;

public final class ReplyHeader_1_0
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public ServiceContext[] service_context;
    public int request_id;
    public ReplyStatusType_1_0 reply_status;

    public ReplyHeader_1_0() {
    }

    public ReplyHeader_1_0(ServiceContext[] service_context, int request_id, ReplyStatusType_1_0 reply_status) {
        this.service_context = service_context;
        this.request_id = request_id;
        this.reply_status = reply_status;
    }
}

