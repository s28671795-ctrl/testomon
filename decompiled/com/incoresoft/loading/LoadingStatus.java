/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.loading;

import com.incoresoft.middleware.connector.ConnectorStatus;

public enum LoadingStatus {
    ERROR,
    WAITING,
    PROCESSING,
    OK;


    public static LoadingStatus from(ConnectorStatus status) {
        return switch (status.getCode()) {
            case "connected" -> OK;
            case "connecting" -> PROCESSING;
            case "error" -> ERROR;
            default -> throw new IllegalArgumentException("Unknown status code: " + status.getCode());
        };
    }
}

