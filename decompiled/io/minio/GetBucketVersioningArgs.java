/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.BucketArgs;

public class GetBucketVersioningArgs
extends BucketArgs {
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder
    extends BucketArgs.Builder<Builder, GetBucketVersioningArgs> {
    }
}

