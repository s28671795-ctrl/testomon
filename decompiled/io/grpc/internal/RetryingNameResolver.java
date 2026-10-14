/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import io.grpc.Attributes;
import io.grpc.NameResolver;
import io.grpc.Status;
import io.grpc.SynchronizationContext;
import io.grpc.internal.ForwardingNameResolver;
import io.grpc.internal.RetryScheduler;

final class RetryingNameResolver
extends ForwardingNameResolver {
    private final NameResolver retriedNameResolver;
    private final RetryScheduler retryScheduler;
    private final SynchronizationContext syncContext;
    static final Attributes.Key<ResolutionResultListener> RESOLUTION_RESULT_LISTENER_KEY = Attributes.Key.create("io.grpc.internal.RetryingNameResolver.RESOLUTION_RESULT_LISTENER_KEY");

    RetryingNameResolver(NameResolver retriedNameResolver, RetryScheduler retryScheduler, SynchronizationContext syncContext) {
        super(retriedNameResolver);
        this.retriedNameResolver = retriedNameResolver;
        this.retryScheduler = retryScheduler;
        this.syncContext = syncContext;
    }

    @Override
    public void start(NameResolver.Listener2 listener) {
        super.start(new RetryingListener(listener));
    }

    @Override
    public void shutdown() {
        super.shutdown();
        this.retryScheduler.reset();
    }

    @VisibleForTesting
    NameResolver getRetriedNameResolver() {
        return this.retriedNameResolver;
    }

    class ResolutionResultListener {
        ResolutionResultListener() {
        }

        public void resolutionAttempted(Status successStatus) {
            if (successStatus.isOk()) {
                RetryingNameResolver.this.retryScheduler.reset();
            } else {
                RetryingNameResolver.this.retryScheduler.schedule(new DelayedNameResolverRefresh());
            }
        }
    }

    private class RetryingListener
    extends NameResolver.Listener2 {
        private NameResolver.Listener2 delegateListener;

        RetryingListener(NameResolver.Listener2 delegateListener) {
            this.delegateListener = delegateListener;
        }

        @Override
        public void onResult(NameResolver.ResolutionResult resolutionResult) {
            if (resolutionResult.getAttributes().get(RESOLUTION_RESULT_LISTENER_KEY) != null) {
                throw new IllegalStateException("RetryingNameResolver can only be used once to wrap a NameResolver");
            }
            this.delegateListener.onResult(resolutionResult.toBuilder().setAttributes(resolutionResult.getAttributes().toBuilder().set(RESOLUTION_RESULT_LISTENER_KEY, new ResolutionResultListener()).build()).build());
        }

        @Override
        public void onError(Status error) {
            this.delegateListener.onError(error);
            RetryingNameResolver.this.syncContext.execute(() -> RetryingNameResolver.this.retryScheduler.schedule(new DelayedNameResolverRefresh()));
        }
    }

    @VisibleForTesting
    class DelayedNameResolverRefresh
    implements Runnable {
        DelayedNameResolverRefresh() {
        }

        @Override
        public void run() {
            RetryingNameResolver.this.refresh();
        }
    }
}

