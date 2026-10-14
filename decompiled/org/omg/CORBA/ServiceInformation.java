/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.ServiceDetail;
import org.omg.CORBA.portable.IDLEntity;

public final class ServiceInformation
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public int[] service_options;
    public ServiceDetail[] service_details;

    public ServiceInformation() {
    }

    public ServiceInformation(int[] service_options, ServiceDetail[] service_details) {
        this.service_options = service_options;
        this.service_details = service_details;
    }
}

