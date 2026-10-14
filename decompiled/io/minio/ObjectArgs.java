/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.BucketArgs;
import io.minio.ServerSideEncryption;
import java.util.Objects;
import okhttp3.HttpUrl;

public abstract class ObjectArgs
extends BucketArgs {
    protected String objectName;

    public String object() {
        return this.objectName;
    }

    protected void checkSse(ServerSideEncryption sse, HttpUrl url2) {
        if (sse == null) {
            return;
        }
        if (sse.tlsRequired() && !url2.isHttps()) {
            throw new IllegalArgumentException(sse + " operations must be performed over a secure connection.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ObjectArgs)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        ObjectArgs that = (ObjectArgs)o;
        return Objects.equals(this.objectName, that.objectName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.objectName);
    }

    public static abstract class Builder<B extends Builder<B, A>, A extends ObjectArgs>
    extends BucketArgs.Builder<B, A> {
        protected void validateObjectName(String name) {
            this.validateNotEmptyString(name, "object name");
            if (this.skipValidation) {
                return;
            }
            for (String token : name.split("/")) {
                if (!token.equals(".") && !token.equals("..")) continue;
                throw new IllegalArgumentException("object name with '.' or '..' path segment is not supported");
            }
        }

        @Override
        protected void validate(A args2) {
            super.validate(args2);
            this.validateObjectName(((ObjectArgs)args2).objectName);
        }

        public B object(String name) {
            this.validateObjectName(name);
            this.operations.add(args2 -> {
                args2.objectName = name;
            });
            return (B)this;
        }
    }
}

