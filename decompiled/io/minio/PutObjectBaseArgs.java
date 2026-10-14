/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.ObjectWriteArgs;
import java.io.IOException;
import java.util.Objects;

public abstract class PutObjectBaseArgs
extends ObjectWriteArgs {
    protected long objectSize;
    protected long partSize;
    protected int partCount;
    protected String contentType;
    protected boolean preloadData;

    public long objectSize() {
        return this.objectSize;
    }

    public long partSize() {
        return this.partSize;
    }

    public int partCount() {
        return this.partCount;
    }

    public String contentType() throws IOException {
        if (this.contentType != null) {
            return this.contentType;
        }
        if (this.headers().containsKey("Content-Type")) {
            return this.headers().get("Content-Type").iterator().next();
        }
        return null;
    }

    public boolean preloadData() {
        return this.preloadData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PutObjectBaseArgs)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        PutObjectBaseArgs that = (PutObjectBaseArgs)o;
        return this.objectSize == that.objectSize && this.partSize == that.partSize && this.partCount == that.partCount && Objects.equals(this.contentType, that.contentType) && this.preloadData == that.preloadData;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.objectSize, this.partSize, this.partCount, this.contentType, this.preloadData);
    }

    public static abstract class Builder<B extends Builder<B, A>, A extends PutObjectBaseArgs>
    extends ObjectWriteArgs.Builder<B, A> {
        private void validateSizes(long objectSize, long partSize) {
            if (partSize > 0L) {
                if (partSize < 0x500000L) {
                    throw new IllegalArgumentException("part size " + partSize + " is not supported; minimum allowed 5MiB");
                }
                if (partSize > 0x140000000L) {
                    throw new IllegalArgumentException("part size " + partSize + " is not supported; maximum allowed 5GiB");
                }
            }
            if (objectSize >= 0L) {
                if (objectSize > 0x50000000000L) {
                    throw new IllegalArgumentException("object size " + objectSize + " is not supported; maximum allowed 5TiB");
                }
            } else if (partSize <= 0L) {
                throw new IllegalArgumentException("valid part size must be provided when object size is unknown");
            }
        }

        protected long[] getPartInfo(long objectSize, long partSize) {
            long partCount;
            this.validateSizes(objectSize, partSize);
            if (objectSize < 0L) {
                return new long[]{partSize, -1L};
            }
            if (partSize <= 0L) {
                double dPartSize = Math.ceil((double)objectSize / 10000.0);
                dPartSize = Math.ceil(dPartSize / 5242880.0) * 5242880.0;
                partSize = (long)dPartSize;
            }
            if (partSize > objectSize) {
                partSize = objectSize;
            }
            long l = partCount = partSize > 0L ? (long)Math.ceil((double)objectSize / (double)partSize) : 1L;
            if (partCount > 10000L) {
                throw new IllegalArgumentException("object size " + objectSize + " and part size " + partSize + " make more than " + 10000 + "parts for upload");
            }
            return new long[]{partSize, partCount};
        }

        @Deprecated
        public B preloadData(boolean preloadData) {
            this.operations.add(args2 -> {
                args2.preloadData = preloadData;
            });
            return (B)this;
        }
    }
}

