/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.GenericResponse;
import io.minio.messages.CopyPartResult;
import okhttp3.Headers;

public class UploadPartCopyResponse
extends GenericResponse {
    private String uploadId;
    private int partNumber;
    private CopyPartResult result;

    public UploadPartCopyResponse(Headers headers, String bucket, String region, String object, String uploadId, int partNumber, CopyPartResult result) {
        super(headers, bucket, region, object);
        this.uploadId = uploadId;
        this.partNumber = partNumber;
        this.result = result;
    }

    public String uploadId() {
        return this.uploadId;
    }

    public int partNumber() {
        return this.partNumber;
    }

    public CopyPartResult result() {
        return this.result;
    }
}

