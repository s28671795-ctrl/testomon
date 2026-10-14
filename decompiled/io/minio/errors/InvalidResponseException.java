/*
 * Decompiled with CFR 0.152.
 */
package io.minio.errors;

import io.minio.errors.MinioException;

public class InvalidResponseException
extends MinioException {
    private static final long serialVersionUID = -4793742105569629274L;

    public InvalidResponseException(int responseCode, String contentType, String body2, String httpTrace) {
        super("Non-XML response from server. Response code: " + responseCode + ", Content-Type: " + contentType + ", body: " + body2, httpTrace);
    }
}

