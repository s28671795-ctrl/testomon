/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.BucketArgs;
import io.minio.messages.LifecycleConfiguration;
import java.util.Objects;

public class SetBucketLifecycleArgs
extends BucketArgs {
    private LifecycleConfiguration config;

    public LifecycleConfiguration config() {
        return this.config;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SetBucketLifecycleArgs)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        SetBucketLifecycleArgs that = (SetBucketLifecycleArgs)o;
        return Objects.equals(this.config, that.config);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.config);
    }

    public static final class Builder
    extends BucketArgs.Builder<Builder, SetBucketLifecycleArgs> {
        private void validateConfig(LifecycleConfiguration config) {
            this.validateNotNull(config, "lifecycle configuration");
        }

        @Override
        protected void validate(SetBucketLifecycleArgs args2) {
            super.validate(args2);
            this.validateConfig(args2.config);
        }

        public Builder config(LifecycleConfiguration config) {
            this.validateConfig(config);
            this.operations.add(args2 -> ((SetBucketLifecycleArgs)args2).config = config);
            return this;
        }
    }
}

