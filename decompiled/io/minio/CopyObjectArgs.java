/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.ComposeObjectArgs;
import io.minio.CopySource;
import io.minio.Directive;
import io.minio.ObjectWriteArgs;
import java.util.Objects;
import okhttp3.HttpUrl;

public class CopyObjectArgs
extends ObjectWriteArgs {
    private CopySource source = null;
    private Directive metadataDirective;
    private Directive taggingDirective;

    protected CopyObjectArgs() {
    }

    public CopyObjectArgs(ComposeObjectArgs args2) {
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
        this.source = new CopySource(args2.sources().get(0));
    }

    public CopySource source() {
        return this.source;
    }

    public Directive metadataDirective() {
        return this.metadataDirective;
    }

    public Directive taggingDirective() {
        return this.taggingDirective;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public void validateSse(HttpUrl url2) {
        super.validateSse(url2);
        this.source.validateSsec(url2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CopyObjectArgs)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        CopyObjectArgs that = (CopyObjectArgs)o;
        return Objects.equals(this.source, that.source) && this.metadataDirective == that.metadataDirective && this.taggingDirective == that.taggingDirective;
    }

    @Override
    public int hashCode() {
        return Objects.hash(new Object[]{super.hashCode(), this.source, this.metadataDirective, this.taggingDirective});
    }

    public static final class Builder
    extends ObjectWriteArgs.Builder<Builder, CopyObjectArgs> {
        @Override
        protected void validate(CopyObjectArgs args2) {
            super.validate(args2);
            this.validateNotNull(args2.source, "copy source");
            if (args2.source.offset() != null || args2.source.length() != null) {
                if (args2.metadataDirective != null && args2.metadataDirective == Directive.COPY) {
                    throw new IllegalArgumentException("COPY metadata directive is not applicable to source object with range");
                }
                if (args2.taggingDirective != null && args2.taggingDirective == Directive.COPY) {
                    throw new IllegalArgumentException("COPY tagging directive is not applicable to source object with range");
                }
            }
        }

        public Builder source(CopySource source2) {
            this.validateNotNull(source2, "copy source");
            this.operations.add(args2 -> ((CopyObjectArgs)args2).source = source2);
            return this;
        }

        public Builder metadataDirective(Directive directive) {
            this.operations.add(args2 -> ((CopyObjectArgs)args2).metadataDirective = directive);
            return this;
        }

        public Builder taggingDirective(Directive directive) {
            this.operations.add(args2 -> ((CopyObjectArgs)args2).taggingDirective = directive);
            return this;
        }
    }
}

