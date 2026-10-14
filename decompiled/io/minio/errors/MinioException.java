/*
 * Decompiled with CFR 0.152.
 */
package io.minio.errors;

public class MinioException
extends Exception {
    private static final long serialVersionUID = -7241010318779326306L;
    String httpTrace = null;

    public MinioException() {
    }

    public MinioException(String message) {
        super(message);
    }

    public MinioException(String message, String httpTrace) {
        super(message);
        this.httpTrace = httpTrace;
    }

    public String httpTrace() {
        return this.httpTrace;
    }
}

