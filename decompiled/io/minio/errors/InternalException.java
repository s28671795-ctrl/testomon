/*
 * Decompiled with CFR 0.152.
 */
package io.minio.errors;

import io.minio.errors.MinioException;

public class InternalException
extends MinioException {
    private static final long serialVersionUID = 138336287983212416L;

    public InternalException(String message, String httpTrace) {
        super(message, httpTrace);
    }
}

