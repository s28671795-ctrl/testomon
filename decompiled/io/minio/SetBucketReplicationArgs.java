/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.BucketArgs;
import io.minio.messages.ReplicationConfiguration;
import java.util.Objects;

public class SetBucketReplicationArgs
extends BucketArgs {
    private ReplicationConfiguration config;
    private String objectLockToken;

    public ReplicationConfiguration config() {
        return this.config;
    }

    public String objectLockToken() {
        return this.objectLockToken;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SetBucketReplicationArgs)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        SetBucketReplicationArgs that = (SetBucketReplicationArgs)o;
        return Objects.equals(this.config, that.config) && Objects.equals(this.objectLockToken, that.objectLockToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.config, this.objectLockToken);
    }

    public static final class Builder
    extends BucketArgs.Builder<Builder, SetBucketReplicationArgs> {
        private void validateConfig(ReplicationConfiguration config) {
            this.validateNotNull(config, "replication configuration");
        }

        private void validateObjectLockToken(String token) {
            this.validateNullOrNotEmptyString(token, "object lock token");
        }

        @Override
        protected void validate(SetBucketReplicationArgs args2) {
            super.validate(args2);
            this.validateConfig(args2.config);
            this.validateObjectLockToken(args2.objectLockToken);
        }

        public Builder config(ReplicationConfiguration config) {
            this.validateConfig(config);
            this.operations.add(args2 -> ((SetBucketReplicationArgs)args2).config = config);
            return this;
        }

        public Builder objectLockToken(String token) {
            this.validateObjectLockToken(token);
            this.operations.add(args2 -> ((SetBucketReplicationArgs)args2).objectLockToken = token);
            return this;
        }
    }
}

