/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.services.exceptions;

public class ServiceFileIsInvalidException
extends RuntimeException {
    private final String serviceId;

    public ServiceFileIsInvalidException(Throwable throwable, String serviceId) {
        super(throwable);
        this.serviceId = serviceId;
    }

    public String getServiceId() {
        return this.serviceId;
    }
}

