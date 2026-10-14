/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.GenericResponse;
import io.minio.messages.ListMultipartUploadsResult;
import okhttp3.Headers;

public class ListMultipartUploadsResponse
extends GenericResponse {
    private ListMultipartUploadsResult result;

    public ListMultipartUploadsResponse(Headers headers, String bucket, String region, ListMultipartUploadsResult result) {
        super(headers, bucket, region, null);
        this.result = result;
    }

    public ListMultipartUploadsResult result() {
        return this.result;
    }
}

