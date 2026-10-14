/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.connector;

public interface ConnectorStatus {
    public static final String ERROR = "error";
    public static final String CONNECTING = "connecting";
    public static final String CONNECTED = "connected";
    public static final String DISCONNECTED = "disconnected";

    public String getCode();

    public Throwable getException();
}

