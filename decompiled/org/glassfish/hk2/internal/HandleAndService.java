/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.internal;

import org.glassfish.hk2.api.ServiceHandle;

public class HandleAndService {
    private final ServiceHandle<?> handle;
    private final Object service;

    public HandleAndService(ServiceHandle<?> handle2, Object service2) {
        this.handle = handle2;
        this.service = service2;
    }

    public ServiceHandle<?> getHandle() {
        return this.handle;
    }

    public Object getService() {
        return this.service;
    }
}

