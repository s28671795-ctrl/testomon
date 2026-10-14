/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.GenericResponse;
import io.minio.messages.ListPartsResult;
import okhttp3.Headers;

public class ListPartsResponse
extends GenericResponse {
    private ListPartsResult result;

    public ListPartsResponse(Headers headers, String bucket, String region, String object, ListPartsResult result) {
        super(headers, bucket, region, object);
        this.result = result;
    }

    public ListPartsResult result() {
        return this.result;
    }
}

