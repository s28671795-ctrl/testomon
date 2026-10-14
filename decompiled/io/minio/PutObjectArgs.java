/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.PutObjectBaseArgs;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class PutObjectArgs
extends PutObjectBaseArgs {
    private BufferedInputStream stream;

    public BufferedInputStream stream() {
        return this.stream;
    }

    @Override
    public String contentType() throws IOException {
        String contentType = super.contentType();
        return contentType != null ? contentType : "application/octet-stream";
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PutObjectArgs)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        PutObjectArgs that = (PutObjectArgs)o;
        return Objects.equals(this.stream, that.stream);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.stream);
    }

    public static final class Builder
    extends PutObjectBaseArgs.Builder<Builder, PutObjectArgs> {
        @Override
        protected void validate(PutObjectArgs args2) {
            super.validate(args2);
            this.validateNotNull(args2.stream, "stream");
        }

        public Builder stream(InputStream stream, long objectSize, long partSize) {
            this.validateNotNull(stream, "stream");
            long[] partinfo = this.getPartInfo(objectSize, partSize);
            long pSize = partinfo[0];
            int pCount = (int)partinfo[1];
            BufferedInputStream bis = stream instanceof BufferedInputStream ? (BufferedInputStream)stream : new BufferedInputStream(stream);
            return this.setStream(bis, objectSize, pSize, pCount);
        }

        private Builder setStream(BufferedInputStream stream, long objectSize, long partSize, int partCount) {
            this.operations.add(args2 -> ((PutObjectArgs)args2).stream = stream);
            this.operations.add(args2 -> {
                args2.objectSize = objectSize;
            });
            this.operations.add(args2 -> {
                args2.partSize = partSize;
            });
            this.operations.add(args2 -> {
                args2.partCount = partCount;
            });
            return this;
        }

        public Builder contentType(String contentType) {
            this.validateNotEmptyString(contentType, "content type");
            this.operations.add(args2 -> {
                args2.contentType = contentType;
            });
            return this;
        }
    }
}

