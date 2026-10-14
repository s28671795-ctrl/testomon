/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.ObjectVersionArgs;

public class DeleteObjectTagsArgs
extends ObjectVersionArgs {
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder
    extends ObjectVersionArgs.Builder<Builder, DeleteObjectTagsArgs> {
    }
}

