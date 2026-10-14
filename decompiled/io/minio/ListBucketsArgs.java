/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.BaseArgs;

public class ListBucketsArgs
extends BaseArgs {
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder
    extends BaseArgs.Builder<Builder, ListBucketsArgs> {
        @Override
        protected void validate(ListBucketsArgs args2) {
        }
    }
}

