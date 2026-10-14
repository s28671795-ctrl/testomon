/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.workqueue;

import java.util.concurrent.Executor;

public final class OneShotAsyncExecutor
implements Executor {
    private static final OneShotAsyncExecutor INSTANCE = new OneShotAsyncExecutor();

    private OneShotAsyncExecutor() {
    }

    @Override
    public void execute(Runnable command) {
        new Thread(command).start();
    }

    public static OneShotAsyncExecutor getInstance() {
        return INSTANCE;
    }

    public static boolean isA(Executor executor2) {
        return executor2 == INSTANCE;
    }
}

