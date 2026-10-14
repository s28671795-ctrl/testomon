/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.ServerSideEncryption;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ServerSideEncryptionS3
extends ServerSideEncryption {
    private static final Map<String, String> headers;

    @Override
    public final Map<String, String> headers() {
        return headers;
    }

    @Override
    public final boolean tlsRequired() {
        return false;
    }

    public String toString() {
        return "SSE-S3";
    }

    static {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("X-Amz-Server-Side-Encryption", "AES256");
        headers = Collections.unmodifiableMap(map);
    }
}

