/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.DownloadObjectArgs;
import io.minio.ObjectConditionalReadArgs;

public class GetObjectArgs
extends ObjectConditionalReadArgs {
    protected GetObjectArgs() {
    }

    public GetObjectArgs(DownloadObjectArgs args2) {
        this.extraHeaders = args2.extraHeaders;
        this.extraQueryParams = args2.extraQueryParams;
        this.bucketName = args2.bucketName;
        this.region = args2.region;
        this.objectName = args2.objectName;
        this.versionId = args2.versionId;
        this.ssec = args2.ssec;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder
    extends ObjectConditionalReadArgs.Builder<Builder, GetObjectArgs> {
    }
}

