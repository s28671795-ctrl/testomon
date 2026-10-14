/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.ObjectVersionArgs;
import io.minio.messages.Retention;
import java.util.Objects;

public class SetObjectRetentionArgs
extends ObjectVersionArgs {
    private Retention config;
    private boolean bypassGovernanceMode;

    public Retention config() {
        return this.config;
    }

    public boolean bypassGovernanceMode() {
        return this.bypassGovernanceMode;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SetObjectRetentionArgs)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        SetObjectRetentionArgs that = (SetObjectRetentionArgs)o;
        return this.bypassGovernanceMode == that.bypassGovernanceMode && Objects.equals(this.config, that.config);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.config, this.bypassGovernanceMode);
    }

    public static final class Builder
    extends ObjectVersionArgs.Builder<Builder, SetObjectRetentionArgs> {
        private void validateConfig(Retention config) {
            this.validateNotNull(config, "retention configuration");
        }

        @Override
        protected void validate(SetObjectRetentionArgs args2) {
            super.validate(args2);
            this.validateConfig(args2.config);
        }

        public Builder config(Retention config) {
            this.validateConfig(config);
            this.operations.add(args2 -> ((SetObjectRetentionArgs)args2).config = config);
            return this;
        }

        public Builder bypassGovernanceMode(boolean bypassGovernanceMode) {
            this.operations.add(args2 -> ((SetObjectRetentionArgs)args2).bypassGovernanceMode = bypassGovernanceMode);
            return this;
        }
    }
}

