/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.BucketArgs;

public class GetBucketPolicyArgs
extends BucketArgs {
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder
    extends BucketArgs.Builder<Builder, GetBucketPolicyArgs> {
    }
}

