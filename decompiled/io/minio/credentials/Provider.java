/*
 * Decompiled with CFR 0.152.
 */
package io.minio.credentials;

import io.minio.credentials.Credentials;

public interface Provider {
    public Credentials fetch();
}

