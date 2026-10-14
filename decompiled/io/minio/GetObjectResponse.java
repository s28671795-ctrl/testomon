/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.GenericResponse;
import java.io.FilterInputStream;
import java.io.InputStream;
import okhttp3.Headers;

public class GetObjectResponse
extends FilterInputStream {
    private GenericResponse response;

    public GetObjectResponse(Headers headers, String bucket, String region, String object, InputStream body2) {
        super(body2);
        this.response = new GenericResponse(headers, bucket, region, object);
    }

    public Headers headers() {
        return this.response.headers();
    }

    public String bucket() {
        return this.response.bucket();
    }

    public String region() {
        return this.response.region();
    }

    public String object() {
        return this.response.object();
    }
}

