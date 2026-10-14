/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.BucketArgs;
import io.minio.messages.DeleteObject;
import java.util.LinkedList;
import java.util.Objects;

public class RemoveObjectsArgs
extends BucketArgs {
    private boolean bypassGovernanceMode;
    private Iterable<DeleteObject> objects = new LinkedList<DeleteObject>();

    public boolean bypassGovernanceMode() {
        return this.bypassGovernanceMode;
    }

    public Iterable<DeleteObject> objects() {
        return this.objects;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RemoveObjectsArgs)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        RemoveObjectsArgs that = (RemoveObjectsArgs)o;
        return this.bypassGovernanceMode == that.bypassGovernanceMode && Objects.equals(this.objects, that.objects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.bypassGovernanceMode, this.objects);
    }

    public static final class Builder
    extends BucketArgs.Builder<Builder, RemoveObjectsArgs> {
        public Builder bypassGovernanceMode(boolean flag) {
            this.operations.add(args2 -> ((RemoveObjectsArgs)args2).bypassGovernanceMode = flag);
            return this;
        }

        public Builder objects(Iterable<DeleteObject> objects) {
            this.validateNotNull(objects, "objects");
            this.operations.add(args2 -> ((RemoveObjectsArgs)args2).objects = objects);
            return this;
        }
    }
}

