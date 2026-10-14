/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.util;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import org.apache.activemq.util.StopWatch;
import org.apache.activemq.util.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ThreadPoolUtils {
    private static final Logger LOG = LoggerFactory.getLogger(ThreadPoolUtils.class);
    public static final long DEFAULT_SHUTDOWN_AWAIT_TERMINATION = 10000L;

    public static void shutdown(ExecutorService executorService2) {
        ThreadPoolUtils.doShutdown(executorService2, 0L);
    }

    public static List<Runnable> shutdownNow(ExecutorService executorService2) {
        List<Runnable> answer = null;
        if (!executorService2.isShutdown()) {
            LOG.debug("Forcing shutdown of ExecutorService: {}", (Object)executorService2);
            answer = executorService2.shutdownNow();
            if (LOG.isTraceEnabled()) {
                LOG.trace("Shutdown of ExecutorService: {} is shutdown: {} and terminated: {}.", executorService2, executorService2.isShutdown(), executorService2.isTerminated());
            }
        }
        return answer;
    }

    public static void shutdownGraceful(ExecutorService executorService2) {
        ThreadPoolUtils.doShutdown(executorService2, 10000L);
    }

    public static void shutdownGraceful(ExecutorService executorService2, long shutdownAwaitTermination) {
        ThreadPoolUtils.doShutdown(executorService2, shutdownAwaitTermination);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    private static void doShutdown(ExecutorService executorService, long shutdownAwaitTermination) {
        if (executorService == null) {
            return;
        }
        if (!executorService.isShutdown()) {
            warned = false;
            watch = new StopWatch();
            ThreadPoolUtils.LOG.trace("Shutdown of ExecutorService: {} with await termination: {} millis", (Object)executorService, (Object)shutdownAwaitTermination);
            executorService.shutdown();
            if (shutdownAwaitTermination > 0L) {
                try {
                    if (ThreadPoolUtils.awaitTermination(executorService, shutdownAwaitTermination)) ** GOTO lbl42
                    warned = true;
                    ThreadPoolUtils.LOG.warn("Forcing shutdown of ExecutorService: {} due first await termination elapsed.", (Object)executorService);
                    executorService.shutdownNow();
                    if (ThreadPoolUtils.awaitTermination(executorService, shutdownAwaitTermination)) ** GOTO lbl42
                    ThreadPoolUtils.LOG.warn("Cannot completely force shutdown of ExecutorService: {} due second await termination elapsed.", (Object)executorService);
                }
                catch (InterruptedException e) {
                    warned = true;
                    ThreadPoolUtils.LOG.warn("Forcing shutdown of ExecutorService: {} due interrupted.", (Object)executorService);
                    try {
                        executorService.shutdownNow();
                    }
                    finally {
                        Thread.currentThread().interrupt();
                    }
                }
            } else if (shutdownAwaitTermination < 0L) {
                try {
                    ThreadPoolUtils.awaitTermination(executorService);
                }
                catch (InterruptedException e) {
                    warned = true;
                    ThreadPoolUtils.LOG.warn("Forcing shutdown of ExecutorService: {} due interrupted.", (Object)executorService);
                    try {
                        executorService.shutdownNow();
                    }
                    finally {
                        Thread.currentThread().interrupt();
                    }
                }
            }
lbl42:
            // 8 sources

            if (warned) {
                ThreadPoolUtils.LOG.info("Shutdown of ExecutorService: {} is shutdown: {} and terminated: {} took: {}.", new Object[]{executorService, executorService.isShutdown(), executorService.isTerminated(), TimeUtils.printDuration(watch.taken())});
            } else if (ThreadPoolUtils.LOG.isDebugEnabled()) {
                ThreadPoolUtils.LOG.debug("Shutdown of ExecutorService: {} is shutdown: {} and terminated: {} took: {}.", new Object[]{executorService, executorService.isShutdown(), executorService.isTerminated(), TimeUtils.printDuration(watch.taken())});
            }
        }
    }

    public static void awaitTermination(ExecutorService executorService2) throws InterruptedException {
        StopWatch watch = new StopWatch();
        long interval = 2000L;
        while (!executorService2.awaitTermination(2000L, TimeUnit.MILLISECONDS)) {
            LOG.info("Waited {} for ExecutorService: {} to terminate...", (Object)TimeUtils.printDuration(watch.taken()), (Object)executorService2);
        }
        return;
    }

    public static boolean awaitTermination(ExecutorService executorService2, long shutdownAwaitTermination) throws InterruptedException {
        StopWatch watch = new StopWatch();
        long interval = Math.min(2000L, shutdownAwaitTermination);
        boolean done = false;
        while (!done && interval > 0L) {
            if (executorService2.awaitTermination(interval, TimeUnit.MILLISECONDS)) {
                done = true;
                continue;
            }
            LOG.info("Waited {} for ExecutorService: {} to terminate...", (Object)TimeUtils.printDuration(watch.taken()), (Object)executorService2);
            interval = Math.min(2000L, shutdownAwaitTermination - watch.taken());
        }
        return done;
    }
}

