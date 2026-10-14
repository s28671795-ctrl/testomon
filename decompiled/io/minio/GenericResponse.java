/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import okhttp3.Headers;

public class GenericResponse {
    private Headers headers;
    private String bucket;
    private String region;
    private String object;

    public GenericResponse(Headers headers, String bucket, String region, String object) {
        this.headers = headers;
        this.bucket = bucket;
        this.region = region;
        this.object = object;
    }

    public Headers headers() {
        return this.headers;
    }

    public String bucket() {
        return this.bucket;
    }

    public String region() {
        return this.region;
    }

    public String object() {
        return this.object;
    }
}

