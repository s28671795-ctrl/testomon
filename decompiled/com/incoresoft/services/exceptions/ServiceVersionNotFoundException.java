/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.services.exceptions;

public class ServiceVersionNotFoundException
extends RuntimeException {
    private final String serviceId;
    private final String version;

    public ServiceVersionNotFoundException(String serviceId, String version) {
        this.serviceId = serviceId;
        this.version = version;
    }

    public String getServiceId() {
        return this.serviceId;
    }

    public String getVersion() {
        return this.version;
    }
}

