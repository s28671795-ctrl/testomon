/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.GenericResponse;
import io.minio.messages.ListVersionsResult;
import okhttp3.Headers;

public class ListObjectVersionsResponse
extends GenericResponse {
    private ListVersionsResult result;

    public ListObjectVersionsResponse(Headers headers, String bucket, String region, ListVersionsResult result) {
        super(headers, bucket, region, null);
        this.result = result;
    }

    public ListVersionsResult result() {
        return this.result;
    }
}

