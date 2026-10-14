/*
 * Decompiled with CFR 0.152.
 */
package io.netty.util.concurrent;

import io.netty.util.concurrent.RejectedExecutionHandler;
import io.netty.util.concurrent.SingleThreadEventExecutor;
import io.netty.util.internal.ObjectUtil;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public final class RejectedExecutionHandlers {
    private static final RejectedExecutionHandler REJECT = new RejectedExecutionHandler(){

        @Override
        public void rejected(Runnable task, SingleThreadEventExecutor executor2) {
            throw new RejectedExecutionException();
        }
    };

    private RejectedExecutionHandlers() {
    }

    public static RejectedExecutionHandler reject() {
        return REJECT;
    }

    public static RejectedExecutionHandler backoff(final int retries, long backoffAmount, TimeUnit unit) {
        ObjectUtil.checkPositive(retries, "retries");
        final long backOffNanos = unit.toNanos(backoffAmount);
        return new RejectedExecutionHandler(){

            @Override
            public void rejected(Runnable task, SingleThreadEventExecutor executor2) {
                if (!executor2.inEventLoop()) {
                    for (int i = 0; i < retries; ++i) {
                        executor2.wakeup(false);
                        LockSupport.parkNanos(backOffNanos);
                        if (!executor2.offerTask(task)) continue;
                        return;
                    }
                }
                throw new RejectedExecutionException();
            }
        };
    }
}

