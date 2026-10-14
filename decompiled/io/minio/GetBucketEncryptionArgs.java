/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.BucketArgs;

public class GetBucketEncryptionArgs
extends BucketArgs {
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder
    extends BucketArgs.Builder<Builder, GetBucketEncryptionArgs> {
    }
}

