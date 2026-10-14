/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.GenericResponse;
import okhttp3.Headers;

public class UploadPartResponse
extends GenericResponse {
    private String uploadId;
    private int partNumber;
    private String etag;

    public UploadPartResponse(Headers headers, String bucket, String region, String object, String uploadId, int partNumber, String etag) {
        super(headers, bucket, region, object);
        this.uploadId = uploadId;
        this.partNumber = partNumber;
        this.etag = etag;
    }

    public String uploadId() {
        return this.uploadId;
    }

    public int partNumber() {
        return this.partNumber;
    }

    public String etag() {
        return this.etag;
    }
}

