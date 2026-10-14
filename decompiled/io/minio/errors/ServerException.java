/*
 * Decompiled with CFR 0.152.
 */
package io.minio.errors;

import io.minio.errors.MinioException;

public class ServerException
extends MinioException {
    private static final long serialVersionUID = 6395201577368980633L;
    private int statusCode;

    public ServerException(String message, int statusCode, String httpTrace) {
        super(message, httpTrace);
        this.statusCode = statusCode;
    }

    public int statusCode() {
        return this.statusCode;
    }
}

