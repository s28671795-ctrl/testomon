/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.GenericResponse;
import io.minio.messages.ListBucketResultV2;
import okhttp3.Headers;

public class ListObjectsV2Response
extends GenericResponse {
    private ListBucketResultV2 result;

    public ListObjectsV2Response(Headers headers, String bucket, String region, ListBucketResultV2 result) {
        super(headers, bucket, region, null);
        this.result = result;
    }

    public ListBucketResultV2 result() {
        return this.result;
    }
}

