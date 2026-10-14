/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import io.minio.ObjectReadArgs;
import io.minio.S3Escaper;
import io.minio.Time;
import java.time.ZonedDateTime;
import java.util.Objects;

public abstract class ObjectConditionalReadArgs
extends ObjectReadArgs {
    protected Long offset;
    protected Long length;
    protected String matchETag;
    protected String notMatchETag;
    protected ZonedDateTime modifiedSince;
    protected ZonedDateTime unmodifiedSince;

    public Long offset() {
        return this.offset;
    }

    public Long length() {
        return this.length;
    }

    public String matchETag() {
        return this.matchETag;
    }

    public String notMatchETag() {
        return this.notMatchETag;
    }

    public ZonedDateTime modifiedSince() {
        return this.modifiedSince;
    }

    public ZonedDateTime unmodifiedSince() {
        return this.unmodifiedSince;
    }

    public Multimap<String, String> getHeaders() {
        Long offset = this.offset;
        Long length = this.length;
        if (length != null && offset == null) {
            offset = 0L;
        }
        String range = null;
        if (offset != null) {
            range = "bytes=" + offset + "-";
            if (length != null) {
                range = range + (offset + length - 1L);
            }
        }
        HashMultimap<String, String> headers = HashMultimap.create();
        if (range != null) {
            headers.put("Range", range);
        }
        if (this.matchETag != null) {
            headers.put("if-match", this.matchETag);
        }
        if (this.notMatchETag != null) {
            headers.put("if-none-match", this.notMatchETag);
        }
        if (this.modifiedSince != null) {
            headers.put("if-modified-since", this.modifiedSince.format(Time.HTTP_HEADER_DATE_FORMAT));
        }
        if (this.unmodifiedSince != null) {
            headers.put("if-unmodified-since", this.unmodifiedSince.format(Time.HTTP_HEADER_DATE_FORMAT));
        }
        if (this.ssec != null) {
            headers.putAll(Multimaps.forMap(this.ssec.headers()));
        }
        return headers;
    }

    public Multimap<String, String> genCopyHeaders() {
        HashMultimap<String, String> headers = HashMultimap.create();
        String copySource = S3Escaper.encodePath("/" + this.bucketName + "/" + this.objectName);
        if (this.versionId != null) {
            copySource = copySource + "?versionId=" + S3Escaper.encode(this.versionId);
        }
        headers.put("x-amz-copy-source", copySource);
        if (this.ssec != null) {
            headers.putAll(Multimaps.forMap(this.ssec.copySourceHeaders()));
        }
        if (this.matchETag != null) {
            headers.put("x-amz-copy-source-if-match", this.matchETag);
        }
        if (this.notMatchETag != null) {
            headers.put("x-amz-copy-source-if-none-match", this.notMatchETag);
        }
        if (this.modifiedSince != null) {
            headers.put("x-amz-copy-source-if-modified-since", this.modifiedSince.format(Time.HTTP_HEADER_DATE_FORMAT));
        }
        if (this.unmodifiedSince != null) {
            headers.put("x-amz-copy-source-if-unmodified-since", this.unmodifiedSince.format(Time.HTTP_HEADER_DATE_FORMAT));
        }
        return headers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ObjectConditionalReadArgs)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        ObjectConditionalReadArgs that = (ObjectConditionalReadArgs)o;
        return Objects.equals(this.offset, that.offset) && Objects.equals(this.length, that.length) && Objects.equals(this.matchETag, that.matchETag) && Objects.equals(this.notMatchETag, that.notMatchETag) && Objects.equals(this.modifiedSince, that.modifiedSince) && Objects.equals(this.unmodifiedSince, that.unmodifiedSince);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.offset, this.length, this.matchETag, this.notMatchETag, this.modifiedSince, this.unmodifiedSince);
    }

    public static abstract class Builder<B extends Builder<B, A>, A extends ObjectConditionalReadArgs>
    extends ObjectReadArgs.Builder<B, A> {
        private void validateLength(Long length) {
            if (length != null && length <= 0L) {
                throw new IllegalArgumentException("length should be greater than zero");
            }
        }

        private void validateOffset(Long offset) {
            if (offset != null && offset < 0L) {
                throw new IllegalArgumentException("offset should be zero or greater");
            }
        }

        public B offset(Long offset) {
            this.validateOffset(offset);
            this.operations.add(args2 -> {
                args2.offset = offset;
            });
            return (B)this;
        }

        public B length(Long length) {
            this.validateLength(length);
            this.operations.add(args2 -> {
                args2.length = length;
            });
            return (B)this;
        }

        public B matchETag(String etag) {
            this.validateNullOrNotEmptyString(etag, "etag");
            this.operations.add(args2 -> {
                args2.matchETag = etag;
            });
            return (B)this;
        }

        public B notMatchETag(String etag) {
            this.validateNullOrNotEmptyString(etag, "etag");
            this.operations.add(args2 -> {
                args2.notMatchETag = etag;
            });
            return (B)this;
        }

        public B modifiedSince(ZonedDateTime modifiedTime) {
            this.operations.add(args2 -> {
                args2.modifiedSince = modifiedTime;
            });
            return (B)this;
        }

        public B unmodifiedSince(ZonedDateTime unmodifiedTime) {
            this.operations.add(args2 -> {
                args2.unmodifiedSince = unmodifiedTime;
            });
            return (B)this;
        }
    }
}

