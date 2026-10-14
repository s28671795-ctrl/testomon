/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.workqueue;

import java.util.concurrent.Executor;

public final class SynchronousExecutor
implements Executor {
    private static final SynchronousExecutor INSTANCE = new SynchronousExecutor();

    private SynchronousExecutor() {
    }

    @Override
    public void execute(Runnable command) {
        command.run();
    }

    public static SynchronousExecutor getInstance() {
        return INSTANCE;
    }

    public static boolean isA(Executor executor2) {
        return executor2 == INSTANCE;
    }
}

