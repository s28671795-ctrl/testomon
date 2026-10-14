/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.GenericResponse;
import okhttp3.Headers;

public class AbortMultipartUploadResponse
extends GenericResponse {
    private String uploadId;

    public AbortMultipartUploadResponse(Headers headers, String bucket, String region, String object, String uploadId) {
        super(headers, bucket, region, object);
        this.uploadId = uploadId;
    }

    public String uploadId() {
        return this.uploadId;
    }
}

