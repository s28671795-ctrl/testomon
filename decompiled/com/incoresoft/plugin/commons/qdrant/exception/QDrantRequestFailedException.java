/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.qdrant.exception;

public class QDrantRequestFailedException
extends RuntimeException {
    private final int statusCode;

    public QDrantRequestFailedException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return this.statusCode;
    }
}

