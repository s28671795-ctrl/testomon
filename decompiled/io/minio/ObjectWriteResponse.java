/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.GenericResponse;
import okhttp3.Headers;

public class ObjectWriteResponse
extends GenericResponse {
    private String etag;
    private String versionId;

    public ObjectWriteResponse(Headers headers, String bucket, String region, String object, String etag, String versionId) {
        super(headers, bucket, region, object);
        this.etag = etag;
        this.versionId = versionId;
    }

    public String etag() {
        return this.etag;
    }

    public String versionId() {
        return this.versionId;
    }
}

