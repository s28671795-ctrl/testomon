/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.BucketArgs;
import io.minio.messages.VersioningConfiguration;
import java.util.Objects;

public class SetBucketVersioningArgs
extends BucketArgs {
    private VersioningConfiguration config;

    public VersioningConfiguration config() {
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
        if (!(o instanceof SetBucketVersioningArgs)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        SetBucketVersioningArgs that = (SetBucketVersioningArgs)o;
        return Objects.equals(this.config, that.config);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.config);
    }

    public static final class Builder
    extends BucketArgs.Builder<Builder, SetBucketVersioningArgs> {
        private void validateConfig(VersioningConfiguration config) {
            this.validateNotNull(config, "versioning configuration");
        }

        @Override
        protected void validate(SetBucketVersioningArgs args2) {
            super.validate(args2);
            this.validateConfig(args2.config);
        }

        public Builder config(VersioningConfiguration config) {
            this.validateConfig(config);
            this.operations.add(args2 -> ((SetBucketVersioningArgs)args2).config = config);
            return this;
        }
    }
}

