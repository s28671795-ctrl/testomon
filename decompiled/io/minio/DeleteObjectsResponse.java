/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.GenericResponse;
import io.minio.messages.DeleteResult;
import okhttp3.Headers;

public class DeleteObjectsResponse
extends GenericResponse {
    private DeleteResult result;

    public DeleteObjectsResponse(Headers headers, String bucket, String region, DeleteResult result) {
        super(headers, bucket, region, null);
        this.result = result;
    }

    public DeleteResult result() {
        return this.result;
    }
}

