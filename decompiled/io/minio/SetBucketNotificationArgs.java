/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.BucketArgs;
import io.minio.messages.NotificationConfiguration;
import java.util.Objects;

public class SetBucketNotificationArgs
extends BucketArgs {
    private NotificationConfiguration config;

    public NotificationConfiguration config() {
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
        if (!(o instanceof SetBucketNotificationArgs)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        SetBucketNotificationArgs that = (SetBucketNotificationArgs)o;
        return Objects.equals(this.config, that.config);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.config);
    }

    public static final class Builder
    extends BucketArgs.Builder<Builder, SetBucketNotificationArgs> {
        private void validateConfig(NotificationConfiguration config) {
            this.validateNotNull(config, "notification configuration");
        }

        @Override
        protected void validate(SetBucketNotificationArgs args2) {
            super.validate(args2);
            this.validateConfig(args2.config);
        }

        public Builder config(NotificationConfiguration config) {
            this.validateConfig(config);
            this.operations.add(args2 -> ((SetBucketNotificationArgs)args2).config = config);
            return this;
        }
    }
}

