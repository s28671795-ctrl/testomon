/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.PartSource;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

class HttpRequestBody
extends RequestBody {
    private PartSource partSource;
    private byte[] bytes;
    private int length;
    private String contentType;

    HttpRequestBody(PartSource partSource, String contentType) {
        this.partSource = partSource;
        this.contentType = contentType;
    }

    HttpRequestBody(byte[] bytes, int length, String contentType) {
        this.bytes = bytes;
        this.length = length;
        this.contentType = contentType;
    }

    @Override
    public MediaType contentType() {
        MediaType mediaType = null;
        if (this.contentType != null) {
            mediaType = MediaType.parse(this.contentType);
        }
        return mediaType == null ? MediaType.parse("application/octet-stream") : mediaType;
    }

    @Override
    public long contentLength() {
        return this.partSource != null ? this.partSource.size() : (long)this.length;
    }

    @Override
    public void writeTo(BufferedSink sink2) throws IOException {
        if (this.partSource != null) {
            sink2.write(this.partSource.source(), this.partSource.size());
        } else {
            sink2.write(this.bytes, 0, this.length);
        }
    }
}

