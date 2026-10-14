/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.ComposeSource;
import io.minio.CopyObjectArgs;
import io.minio.ObjectWriteArgs;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import okhttp3.HttpUrl;

public class ComposeObjectArgs
extends ObjectWriteArgs {
    List<ComposeSource> sources;

    protected ComposeObjectArgs() {
    }

    public ComposeObjectArgs(CopyObjectArgs args2) {
        this.extraHeaders = args2.extraHeaders;
        this.extraQueryParams = args2.extraQueryParams;
        this.bucketName = args2.bucketName;
        this.region = args2.region;
        this.objectName = args2.objectName;
        this.headers = args2.headers;
        this.userMetadata = args2.userMetadata;
        this.sse = args2.sse;
        this.tags = args2.tags;
        this.retention = args2.retention;
        this.legalHold = args2.legalHold;
        this.sources = new LinkedList<ComposeSource>();
        this.sources.add(new ComposeSource(args2.source()));
    }

    public List<ComposeSource> sources() {
        return this.sources;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public void validateSse(HttpUrl url2) {
        super.validateSse(url2);
        for (ComposeSource source2 : this.sources) {
            source2.validateSsec(url2);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ComposeObjectArgs)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        ComposeObjectArgs that = (ComposeObjectArgs)o;
        return Objects.equals(this.sources, that.sources);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.sources);
    }

    public static final class Builder
    extends ObjectWriteArgs.Builder<Builder, ComposeObjectArgs> {
        private void validateSources(List<ComposeSource> sources) {
            if (sources == null || sources.isEmpty()) {
                throw new IllegalArgumentException("compose sources cannot be empty");
            }
        }

        @Override
        protected void validate(ComposeObjectArgs args2) {
            super.validate(args2);
            this.validateSources(args2.sources);
        }

        public Builder sources(List<ComposeSource> sources) {
            this.validateSources(sources);
            this.operations.add(args2 -> {
                args2.sources = sources;
            });
            return this;
        }
    }
}

