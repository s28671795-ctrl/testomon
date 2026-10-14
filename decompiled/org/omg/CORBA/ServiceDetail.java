/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.portable.IDLEntity;

public final class ServiceDetail
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public int service_detail_type;
    public byte[] service_detail;

    public ServiceDetail() {
    }

    public ServiceDetail(int service_detail_type, byte[] service_detail) {
        this.service_detail_type = service_detail_type;
        this.service_detail = service_detail;
    }
}

