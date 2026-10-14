/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.ObjectVersionArgs;
import java.util.Objects;

public class RemoveObjectArgs
extends ObjectVersionArgs {
    private boolean bypassGovernanceMode;

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
        if (!(o instanceof RemoveObjectArgs)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        RemoveObjectArgs that = (RemoveObjectArgs)o;
        return this.bypassGovernanceMode == that.bypassGovernanceMode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.bypassGovernanceMode);
    }

    public static final class Builder
    extends ObjectVersionArgs.Builder<Builder, RemoveObjectArgs> {
        public Builder bypassGovernanceMode(boolean flag) {
            this.operations.add(args2 -> ((RemoveObjectArgs)args2).bypassGovernanceMode = flag);
            return this;
        }
    }
}

