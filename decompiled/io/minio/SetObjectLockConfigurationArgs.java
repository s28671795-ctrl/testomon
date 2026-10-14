/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.BucketArgs;
import io.minio.messages.ObjectLockConfiguration;
import java.util.Objects;

public class SetObjectLockConfigurationArgs
extends BucketArgs {
    private ObjectLockConfiguration config;

    public ObjectLockConfiguration config() {
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
        if (!(o instanceof SetObjectLockConfigurationArgs)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        SetObjectLockConfigurationArgs that = (SetObjectLockConfigurationArgs)o;
        return Objects.equals(this.config, that.config);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.config);
    }

    public static final class Builder
    extends BucketArgs.Builder<Builder, SetObjectLockConfigurationArgs> {
        private void validateConfig(ObjectLockConfiguration config) {
            this.validateNotNull(config, "object-lock configuration");
        }

        @Override
        protected void validate(SetObjectLockConfigurationArgs args2) {
            super.validate(args2);
            this.validateConfig(args2.config);
        }

        public Builder config(ObjectLockConfiguration config) {
            this.validateConfig(config);
            this.operations.add(args2 -> ((SetObjectLockConfigurationArgs)args2).config = config);
            return this;
        }
    }
}

