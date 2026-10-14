/*
 * Decompiled with CFR 0.152.
 */
package io.minio.errors;

import io.minio.errors.MinioException;

public class InsufficientDataException
extends MinioException {
    private static final long serialVersionUID = -1619719290805056566L;

    public InsufficientDataException(String message) {
        super(message);
    }
}

