/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GIOP;

import org.omg.CORBA.portable.IDLEntity;
import org.omg.GIOP.TargetAddress;
import org.omg.IOP.ServiceContext;

public final class RequestHeader_1_2
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public int request_id;
    public byte response_flags;
    public byte[] reserved;
    public TargetAddress target;
    public String operation = "";
    public ServiceContext[] service_context;

    public RequestHeader_1_2() {
    }

    public RequestHeader_1_2(int request_id, byte response_flags, byte[] reserved, TargetAddress target, String operation, ServiceContext[] service_context) {
        this.request_id = request_id;
        this.response_flags = response_flags;
        this.reserved = reserved;
        this.target = target;
        this.operation = operation;
        this.service_context = service_context;
    }
}

