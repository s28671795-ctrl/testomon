/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.base.Preconditions;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

public final class SerializingExecutor
implements Executor,
Runnable {
    private static final Logger log = Logger.getLogger(SerializingExecutor.class.getName());
    private static final AtomicHelper atomicHelper = SerializingExecutor.getAtomicHelper();
    private static final int STOPPED = 0;
    private static final int RUNNING = -1;
    private Executor executor;
    private final Queue<Runnable> runQueue = new ConcurrentLinkedQueue<Runnable>();
    private volatile int runState = 0;

    private static AtomicHelper getAtomicHelper() {
        AtomicHelper helper;
        try {
            helper = new FieldUpdaterAtomicHelper(AtomicIntegerFieldUpdater.newUpdater(SerializingExecutor.class, "runState"));
        }
        catch (Throwable t2) {
            log.log(Level.SEVERE, "FieldUpdaterAtomicHelper failed", t2);
            helper = new SynchronizedAtomicHelper();
        }
        return helper;
    }

    public SerializingExecutor(Executor executor2) {
        Preconditions.checkNotNull(executor2, "'executor' must not be null.");
        this.executor = executor2;
    }

    public void setExecutor(Executor executor2) {
        Preconditions.checkNotNull(executor2, "'executor' must not be null.");
        this.executor = executor2;
    }

    @Override
    public void execute(Runnable r) {
        this.runQueue.add(Preconditions.checkNotNull(r, "'r' must not be null."));
        this.schedule(r);
    }

    private void schedule(@Nullable Runnable removable) {
        if (atomicHelper.runStateCompareAndSet(this, 0, -1)) {
            boolean success = false;
            try {
                this.executor.execute(this);
                success = true;
            }
            finally {
                if (!success) {
                    if (removable != null) {
                        this.runQueue.remove(removable);
                    }
                    atomicHelper.runStateSet(this, 0);
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        try {
            Runnable r;
            Executor oldExecutor = this.executor;
            while (oldExecutor == this.executor && (r = this.runQueue.poll()) != null) {
                try {
                    r.run();
                }
                catch (RuntimeException e) {
                    log.log(Level.SEVERE, "Exception while executing runnable " + r, e);
                }
            }
        }
        finally {
            atomicHelper.runStateSet(this, 0);
        }
        if (!this.runQueue.isEmpty()) {
            this.schedule(null);
        }
    }

    private static final class SynchronizedAtomicHelper
    extends AtomicHelper {
        private SynchronizedAtomicHelper() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean runStateCompareAndSet(SerializingExecutor obj, int expect, int update) {
            SerializingExecutor serializingExecutor = obj;
            synchronized (serializingExecutor) {
                if (obj.runState == expect) {
                    obj.runState = update;
                    return true;
                }
                return false;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void runStateSet(SerializingExecutor obj, int newValue) {
            SerializingExecutor serializingExecutor = obj;
            synchronized (serializingExecutor) {
                obj.runState = newValue;
            }
        }
    }

    private static final class FieldUpdaterAtomicHelper
    extends AtomicHelper {
        private final AtomicIntegerFieldUpdater<SerializingExecutor> runStateUpdater;

        private FieldUpdaterAtomicHelper(AtomicIntegerFieldUpdater<SerializingExecutor> runStateUpdater) {
            this.runStateUpdater = runStateUpdater;
        }

        @Override
        public boolean runStateCompareAndSet(SerializingExecutor obj, int expect, int update) {
            return this.runStateUpdater.compareAndSet(obj, expect, update);
        }

        @Override
        public void runStateSet(SerializingExecutor obj, int newValue) {
            this.runStateUpdater.set(obj, newValue);
        }
    }

    private static abstract class AtomicHelper {
        private AtomicHelper() {
        }

        public abstract boolean runStateCompareAndSet(SerializingExecutor var1, int var2, int var3);

        public abstract void runStateSet(SerializingExecutor var1, int var2);
    }
}

