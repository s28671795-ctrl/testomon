/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import com.google.common.io.BaseEncoding;
import io.minio.ServerSideEncryption;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import javax.security.auth.DestroyFailedException;

public class ServerSideEncryptionCustomerKey
extends ServerSideEncryption {
    private boolean isDestroyed = false;
    private final SecretKey secretKey;
    private final Map<String, String> headers;
    private final Map<String, String> copySourceHeaders;

    public ServerSideEncryptionCustomerKey(SecretKey key) throws InvalidKeyException, NoSuchAlgorithmException {
        if (key == null || !key.getAlgorithm().equals("AES") || key.getEncoded().length != 32) {
            throw new IllegalArgumentException("Secret key must be 256 bit AES key");
        }
        if (key.isDestroyed()) {
            throw new IllegalArgumentException("Secret key already destroyed");
        }
        this.secretKey = key;
        byte[] keyBytes = key.getEncoded();
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(keyBytes);
        String customerKey = BaseEncoding.base64().encode(keyBytes);
        String customerKeyMd5 = BaseEncoding.base64().encode(md5.digest());
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("X-Amz-Server-Side-Encryption-Customer-Algorithm", "AES256");
        map.put("X-Amz-Server-Side-Encryption-Customer-Key", customerKey);
        map.put("X-Amz-Server-Side-Encryption-Customer-Key-Md5", customerKeyMd5);
        this.headers = Collections.unmodifiableMap(map);
        map = new HashMap();
        map.put("X-Amz-Copy-Source-Server-Side-Encryption-Customer-Algorithm", "AES256");
        map.put("X-Amz-Copy-Source-Server-Side-Encryption-Customer-Key", customerKey);
        map.put("X-Amz-Copy-Source-Server-Side-Encryption-Customer-Key-Md5", customerKeyMd5);
        this.copySourceHeaders = Collections.unmodifiableMap(map);
    }

    @Override
    public final Map<String, String> headers() {
        if (this.isDestroyed) {
            throw new IllegalStateException("Secret key was destroyed");
        }
        return this.headers;
    }

    @Override
    public final Map<String, String> copySourceHeaders() {
        if (this.isDestroyed) {
            throw new IllegalStateException("Secret key was destroyed");
        }
        return this.copySourceHeaders;
    }

    public final void destroy() throws DestroyFailedException {
        this.secretKey.destroy();
        this.isDestroyed = true;
    }

    public String toString() {
        return "SSE-C";
    }
}

