/*
 * Decompiled with CFR 0.152.
 */
package io.minio.credentials;

import io.minio.credentials.Credentials;
import io.minio.credentials.EnvironmentProvider;

public class MinioEnvironmentProvider
extends EnvironmentProvider {
    @Override
    public Credentials fetch() {
        return new Credentials(this.getProperty("MINIO_ACCESS_KEY"), this.getProperty("MINIO_SECRET_KEY"), null, null);
    }
}

