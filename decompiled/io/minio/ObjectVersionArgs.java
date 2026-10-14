/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.ObjectArgs;
import java.util.Objects;

public abstract class ObjectVersionArgs
extends ObjectArgs {
    protected String versionId;

    public String versionId() {
        return this.versionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ObjectVersionArgs)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        ObjectVersionArgs that = (ObjectVersionArgs)o;
        return Objects.equals(this.versionId, that.versionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.versionId);
    }

    public static abstract class Builder<B extends Builder<B, A>, A extends ObjectVersionArgs>
    extends ObjectArgs.Builder<B, A> {
        public B versionId(String versionId) {
            this.operations.add(args2 -> {
                args2.versionId = versionId;
            });
            return (B)this;
        }
    }
}

