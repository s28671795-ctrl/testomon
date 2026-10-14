/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.ObjectVersionArgs;
import io.minio.ServerSideEncryptionCustomerKey;
import java.util.Objects;
import okhttp3.HttpUrl;

public abstract class ObjectReadArgs
extends ObjectVersionArgs {
    protected ServerSideEncryptionCustomerKey ssec;

    public ServerSideEncryptionCustomerKey ssec() {
        return this.ssec;
    }

    protected void validateSsec(HttpUrl url2) {
        this.checkSse(this.ssec, url2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ObjectReadArgs)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        ObjectReadArgs that = (ObjectReadArgs)o;
        return Objects.equals(this.ssec, that.ssec);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.ssec);
    }

    public static abstract class Builder<B extends Builder<B, A>, A extends ObjectReadArgs>
    extends ObjectVersionArgs.Builder<B, A> {
        public B ssec(ServerSideEncryptionCustomerKey ssec) {
            this.operations.add(args2 -> {
                args2.ssec = ssec;
            });
            return (B)this;
        }
    }
}

