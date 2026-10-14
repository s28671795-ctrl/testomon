/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import io.minio.ObjectConditionalReadArgs;
import io.minio.errors.InternalException;
import java.util.Objects;

public class ComposeSource
extends ObjectConditionalReadArgs {
    private Long objectSize = null;
    private Multimap<String, String> headers = null;

    protected ComposeSource() {
    }

    public ComposeSource(ObjectConditionalReadArgs args2) {
        this.extraHeaders = args2.extraHeaders;
        this.extraQueryParams = args2.extraQueryParams;
        this.bucketName = args2.bucketName;
        this.region = args2.region;
        this.objectName = args2.objectName;
        this.versionId = args2.versionId;
        this.ssec = args2.ssec;
        this.offset = args2.offset;
        this.length = args2.length;
        this.matchETag = args2.matchETag;
        this.notMatchETag = args2.notMatchETag;
        this.modifiedSince = args2.modifiedSince;
        this.unmodifiedSince = args2.unmodifiedSince;
    }

    private void throwException(long objectsize, long arg, String argName) {
        StringBuilder builder = new StringBuilder().append("source ").append(this.bucketName).append("/").append(this.objectName);
        if (this.versionId != null) {
            builder.append("?versionId=").append(this.versionId);
        }
        builder.append(": ").append(argName).append(" ").append(arg).append(" is beyond object size ").append(this.objectSize);
        throw new IllegalArgumentException(builder.toString());
    }

    private void validateSize(long objectSize) {
        if (this.offset != null && this.offset >= objectSize) {
            this.throwException(objectSize, this.offset, "offset");
        }
        if (this.length != null) {
            if (this.length > objectSize) {
                this.throwException(objectSize, this.length, "length");
            }
            if (this.offset + this.length > objectSize) {
                this.throwException(objectSize, this.offset + this.length, "compose size");
            }
        }
    }

    public void buildHeaders(long objectSize, String etag) {
        this.validateSize(objectSize);
        this.objectSize = objectSize;
        Multimap<String, String> headers = this.genCopyHeaders();
        if (!headers.containsKey("x-amz-copy-source-if-match")) {
            headers.put("x-amz-copy-source-if-match", etag);
        }
        this.headers = Multimaps.unmodifiableMultimap(headers);
    }

    public long objectSize() throws InternalException {
        if (this.objectSize == null) {
            throw new InternalException("buildHeaders(long objectSize, String etag) must be called prior to this method invocation", null);
        }
        return this.objectSize;
    }

    public Multimap<String, String> headers() throws InternalException {
        if (this.headers == null) {
            throw new InternalException("buildHeaders(long objectSize, String etag) must be called prior to this method invocation", null);
        }
        return this.headers;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ComposeSource)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        ComposeSource that = (ComposeSource)o;
        return Objects.equals(this.objectSize, that.objectSize) && Objects.equals(this.headers, that.headers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.objectSize, this.headers);
    }

    public static final class Builder
    extends ObjectConditionalReadArgs.Builder<Builder, ComposeSource> {
    }
}

