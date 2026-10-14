/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.cameraanalytics;

public class StoredImageRetrievalException
extends RuntimeException {
    private final String errorMessage;

    public StoredImageRetrievalException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }
}

