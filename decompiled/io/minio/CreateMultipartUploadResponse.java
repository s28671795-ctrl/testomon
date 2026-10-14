/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.GenericResponse;
import io.minio.messages.InitiateMultipartUploadResult;
import okhttp3.Headers;

public class CreateMultipartUploadResponse
extends GenericResponse {
    private InitiateMultipartUploadResult result;

    public CreateMultipartUploadResponse(Headers headers, String bucket, String region, String object, InitiateMultipartUploadResult result) {
        super(headers, bucket, region, object);
        this.result = result;
    }

    public InitiateMultipartUploadResult result() {
        return this.result;
    }
}

