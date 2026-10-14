/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.function.Supplier;

final class ThreadGuard {
    static final Guard RECORD_TOSTRING = new Guard();

    ThreadGuard() {
    }

    static final void run(Guard guard, Runnable unguardedOperation, Runnable guardedOperation) {
        ThreadGuard.run(guard, () -> {
            unguardedOperation.run();
            return null;
        }, () -> {
            guardedOperation.run();
            return null;
        });
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static final <V> V run(Guard guard, Supplier<V> unguardedOperation, Supplier<V> guardedOperation) {
        boolean unguarded;
        boolean bl = unguarded = guard.tl.get() == null;
        if (unguarded) {
            guard.tl.set(Guard.class);
        }
        try {
            if (unguarded) {
                V v = unguardedOperation.get();
                return v;
            }
            V v = guardedOperation.get();
            return v;
        }
        finally {
            if (unguarded) {
                guard.tl.remove();
            }
        }
    }

    static final class Guard {
        final ThreadLocal<Object> tl = new ThreadLocal();

        Guard() {
        }
    }

    static interface GuardedOperation<V> {
        public V unguarded();

        public V guarded();
    }
}

