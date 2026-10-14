/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.services.exceptions;

public class ServiceIsNotBeingUpdatedException
extends RuntimeException {
    private final String serviceId;

    public ServiceIsNotBeingUpdatedException(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceId() {
        return this.serviceId;
    }
}

