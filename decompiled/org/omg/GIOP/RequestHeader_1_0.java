/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GIOP;

import org.omg.CORBA.portable.IDLEntity;
import org.omg.IOP.ServiceContext;

public final class RequestHeader_1_0
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public ServiceContext[] service_context;
    public int request_id;
    public boolean response_expected;
    public byte[] object_key;
    public String operation = "";
    public byte[] requesting_principal;

    public RequestHeader_1_0() {
    }

    public RequestHeader_1_0(ServiceContext[] service_context, int request_id, boolean response_expected, byte[] object_key, String operation, byte[] requesting_principal) {
        this.service_context = service_context;
        this.request_id = request_id;
        this.response_expected = response_expected;
        this.object_key = object_key;
        this.operation = operation;
        this.requesting_principal = requesting_principal;
    }
}

