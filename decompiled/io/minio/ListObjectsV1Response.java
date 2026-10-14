/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.GenericResponse;
import io.minio.messages.ListBucketResultV1;
import okhttp3.Headers;

public class ListObjectsV1Response
extends GenericResponse {
    private ListBucketResultV1 result;

    public ListObjectsV1Response(Headers headers, String bucket, String region, ListBucketResultV1 result) {
        super(headers, bucket, region, null);
        this.result = result;
    }

    public ListBucketResultV1 result() {
        return this.result;
    }
}

