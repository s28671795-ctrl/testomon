/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.BucketArgs;
import io.minio.messages.SseConfiguration;
import java.util.Objects;

public class SetBucketEncryptionArgs
extends BucketArgs {
    private SseConfiguration config;

    public SseConfiguration config() {
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
        if (!(o instanceof SetBucketEncryptionArgs)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        SetBucketEncryptionArgs that = (SetBucketEncryptionArgs)o;
        return Objects.equals(this.config, that.config);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.config);
    }

    public static final class Builder
    extends BucketArgs.Builder<Builder, SetBucketEncryptionArgs> {
        private void validateConfig(SseConfiguration config) {
            this.validateNotNull(config, "encryption configuration");
        }

        @Override
        protected void validate(SetBucketEncryptionArgs args2) {
            super.validate(args2);
            this.validateConfig(args2.config);
        }

        public Builder config(SseConfiguration config) {
            this.validateConfig(config);
            this.operations.add(args2 -> ((SetBucketEncryptionArgs)args2).config = config);
            return this;
        }
    }
}

