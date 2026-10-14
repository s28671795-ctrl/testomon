/*
 * Decompiled with CFR 0.152.
 */
package com.codahale.metrics;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

public class InstrumentedThreadFactory
implements ThreadFactory {
    private static final AtomicLong nameCounter = new AtomicLong();
    private final ThreadFactory delegate;
    private final Meter created;
    private final Counter running;
    private final Meter terminated;

    public InstrumentedThreadFactory(ThreadFactory delegate, MetricRegistry registry) {
        this(delegate, registry, "instrumented-thread-delegate-" + nameCounter.incrementAndGet());
    }

    public InstrumentedThreadFactory(ThreadFactory delegate, MetricRegistry registry, String name) {
        this.delegate = delegate;
        this.created = registry.meter(MetricRegistry.name(name, "created"));
        this.running = registry.counter(MetricRegistry.name(name, "running"));
        this.terminated = registry.meter(MetricRegistry.name(name, "terminated"));
    }

    @Override
    public Thread newThread(Runnable runnable2) {
        InstrumentedRunnable wrappedRunnable = new InstrumentedRunnable(runnable2);
        Thread thread2 = this.delegate.newThread(wrappedRunnable);
        this.created.mark();
        return thread2;
    }

    private class InstrumentedRunnable
    implements Runnable {
        private final Runnable task;

        InstrumentedRunnable(Runnable task) {
            this.task = task;
        }

        @Override
        public void run() {
            InstrumentedThreadFactory.this.running.inc();
            try {
                this.task.run();
            }
            finally {
                InstrumentedThreadFactory.this.running.dec();
                InstrumentedThreadFactory.this.terminated.mark();
            }
        }
    }
}

