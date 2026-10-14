/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.BucketArgs;
import java.util.Objects;

public class MakeBucketArgs
extends BucketArgs {
    private boolean objectLock;

    public boolean objectLock() {
        return this.objectLock;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MakeBucketArgs)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        MakeBucketArgs that = (MakeBucketArgs)o;
        return this.objectLock == that.objectLock;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.objectLock);
    }

    public static final class Builder
    extends BucketArgs.Builder<Builder, MakeBucketArgs> {
        public Builder objectLock(boolean objectLock) {
            this.operations.add(args2 -> ((MakeBucketArgs)args2).objectLock = objectLock);
            return this;
        }
    }
}

