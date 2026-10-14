/*
 * Decompiled with CFR 0.152.
 */
package io.minio.credentials;

import io.minio.credentials.Credentials;
import io.minio.credentials.EnvironmentProvider;

public class AwsEnvironmentProvider
extends EnvironmentProvider {
    private final String getAccessKey() {
        String value = this.getProperty("AWS_ACCESS_KEY_ID");
        return value != null ? value : this.getProperty("AWS_ACCESS_KEY");
    }

    private final String getSecretKey() {
        String value = this.getProperty("AWS_SECRET_ACCESS_KEY");
        return value != null ? value : this.getProperty("AWS_SECRET_KEY");
    }

    @Override
    public Credentials fetch() {
        return new Credentials(this.getAccessKey(), this.getSecretKey(), this.getProperty("AWS_SESSION_TOKEN"), null);
    }
}

