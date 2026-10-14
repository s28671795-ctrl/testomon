/*
 * Decompiled with CFR 0.152.
 */
package io.netty.util.concurrent;

import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.internal.ObjectUtil;

final class FastThreadLocalRunnable
implements Runnable {
    private final Runnable runnable;

    private FastThreadLocalRunnable(Runnable runnable2) {
        this.runnable = ObjectUtil.checkNotNull(runnable2, "runnable");
    }

    @Override
    public void run() {
        try {
            this.runnable.run();
        }
        finally {
            FastThreadLocal.removeAll();
        }
    }

    static Runnable wrap(Runnable runnable2) {
        return runnable2 instanceof FastThreadLocalRunnable ? runnable2 : new FastThreadLocalRunnable(runnable2);
    }
}

