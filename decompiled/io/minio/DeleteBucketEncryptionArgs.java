/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.BucketArgs;

public class DeleteBucketEncryptionArgs
extends BucketArgs {
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder
    extends BucketArgs.Builder<Builder, DeleteBucketEncryptionArgs> {
    }
}

