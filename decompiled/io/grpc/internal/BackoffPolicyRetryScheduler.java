/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import io.grpc.SynchronizationContext;
import io.grpc.internal.BackoffPolicy;
import io.grpc.internal.RetryScheduler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

final class BackoffPolicyRetryScheduler
implements RetryScheduler {
    private final ScheduledExecutorService scheduledExecutorService;
    private final SynchronizationContext syncContext;
    private final BackoffPolicy.Provider policyProvider;
    private BackoffPolicy policy;
    private SynchronizationContext.ScheduledHandle scheduledHandle;
    private static final Logger logger = Logger.getLogger(BackoffPolicyRetryScheduler.class.getName());

    BackoffPolicyRetryScheduler(BackoffPolicy.Provider policyProvider, ScheduledExecutorService scheduledExecutorService, SynchronizationContext syncContext) {
        this.policyProvider = policyProvider;
        this.scheduledExecutorService = scheduledExecutorService;
        this.syncContext = syncContext;
    }

    @Override
    public void schedule(Runnable retryOperation) {
        this.syncContext.throwIfNotInThisSynchronizationContext();
        if (this.policy == null) {
            this.policy = this.policyProvider.get();
        }
        if (this.scheduledHandle != null && this.scheduledHandle.isPending()) {
            return;
        }
        long delayNanos = this.policy.nextBackoffNanos();
        this.scheduledHandle = this.syncContext.schedule(retryOperation, delayNanos, TimeUnit.NANOSECONDS, this.scheduledExecutorService);
        logger.log(Level.FINE, "Scheduling DNS resolution backoff for {0}ns", delayNanos);
    }

    @Override
    public void reset() {
        this.syncContext.throwIfNotInThisSynchronizationContext();
        this.syncContext.execute(() -> {
            if (this.scheduledHandle != null && this.scheduledHandle.isPending()) {
                this.scheduledHandle.cancel();
            }
            this.policy = null;
        });
    }
}

