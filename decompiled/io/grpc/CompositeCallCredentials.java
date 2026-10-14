/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import com.google.common.base.Preconditions;
import io.grpc.CallCredentials;
import io.grpc.Context;
import io.grpc.ExperimentalApi;
import io.grpc.Metadata;
import io.grpc.Status;
import java.util.concurrent.Executor;

@ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1914")
public final class CompositeCallCredentials
extends CallCredentials {
    private final CallCredentials credentials1;
    private final CallCredentials credentials2;

    public CompositeCallCredentials(CallCredentials creds1, CallCredentials creds2) {
        this.credentials1 = Preconditions.checkNotNull(creds1, "creds1");
        this.credentials2 = Preconditions.checkNotNull(creds2, "creds2");
    }

    @Override
    public void applyRequestMetadata(CallCredentials.RequestInfo requestInfo, Executor appExecutor, CallCredentials.MetadataApplier applier) {
        this.credentials1.applyRequestMetadata(requestInfo, appExecutor, new WrappingMetadataApplier(requestInfo, appExecutor, applier, Context.current()));
    }

    private static final class CombiningMetadataApplier
    extends CallCredentials.MetadataApplier {
        private final CallCredentials.MetadataApplier delegate;
        private final Metadata firstHeaders;

        public CombiningMetadataApplier(CallCredentials.MetadataApplier delegate, Metadata firstHeaders) {
            this.delegate = delegate;
            this.firstHeaders = firstHeaders;
        }

        @Override
        public void apply(Metadata headers) {
            Preconditions.checkNotNull(headers, "headers");
            Metadata combined = new Metadata();
            combined.merge(this.firstHeaders);
            combined.merge(headers);
            this.delegate.apply(combined);
        }

        @Override
        public void fail(Status status) {
            this.delegate.fail(status);
        }
    }

    private final class WrappingMetadataApplier
    extends CallCredentials.MetadataApplier {
        private final CallCredentials.RequestInfo requestInfo;
        private final Executor appExecutor;
        private final CallCredentials.MetadataApplier delegate;
        private final Context context;

        public WrappingMetadataApplier(CallCredentials.RequestInfo requestInfo, Executor appExecutor, CallCredentials.MetadataApplier delegate, Context context) {
            this.requestInfo = requestInfo;
            this.appExecutor = appExecutor;
            this.delegate = Preconditions.checkNotNull(delegate, "delegate");
            this.context = Preconditions.checkNotNull(context, "context");
        }

        @Override
        public void apply(Metadata headers) {
            Preconditions.checkNotNull(headers, "headers");
            Context previous = this.context.attach();
            try {
                CompositeCallCredentials.this.credentials2.applyRequestMetadata(this.requestInfo, this.appExecutor, new CombiningMetadataApplier(this.delegate, headers));
            }
            finally {
                this.context.detach(previous);
            }
        }

        @Override
        public void fail(Status status) {
            this.delegate.fail(status);
        }
    }
}

