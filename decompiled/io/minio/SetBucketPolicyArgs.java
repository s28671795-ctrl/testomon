/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.BucketArgs;
import java.util.Objects;

public class SetBucketPolicyArgs
extends BucketArgs {
    private String config;

    public String config() {
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
        if (!(o instanceof SetBucketPolicyArgs)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        SetBucketPolicyArgs that = (SetBucketPolicyArgs)o;
        return Objects.equals(this.config, that.config);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.config);
    }

    public static final class Builder
    extends BucketArgs.Builder<Builder, SetBucketPolicyArgs> {
        private void validateConfig(String config) {
            this.validateNotNull(config, "policy configuration");
        }

        @Override
        protected void validate(SetBucketPolicyArgs args2) {
            super.validate(args2);
            this.validateConfig(args2.config);
        }

        public Builder config(String config) {
            this.validateConfig(config);
            this.operations.add(args2 -> ((SetBucketPolicyArgs)args2).config = config);
            return this;
        }
    }
}

