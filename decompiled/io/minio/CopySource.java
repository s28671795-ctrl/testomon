/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.ObjectConditionalReadArgs;

public class CopySource
extends ObjectConditionalReadArgs {
    protected CopySource() {
    }

    public CopySource(ObjectConditionalReadArgs args2) {
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

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder
    extends ObjectConditionalReadArgs.Builder<Builder, CopySource> {
    }
}

