/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

public class DefaultExecutor
implements Executor {
    private static final Executor EXECUTOR = ForkJoinPool.getCommonPoolParallelism() > 1 ? ForkJoinPool.commonPool() : command -> new Thread(command).start();

    @Override
    public final void execute(Runnable command) {
        EXECUTOR.execute(command);
    }
}

