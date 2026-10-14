/*
 * Decompiled with CFR 0.152.
 */
package scala.concurrent;

public final class BatchingExecutorStatics$ {
    public static final BatchingExecutorStatics$ MODULE$ = new BatchingExecutorStatics$();
    private static final Runnable[] emptyBatchArray = new Runnable[0];

    public final Runnable[] emptyBatchArray() {
        return emptyBatchArray;
    }

    public final int syncPreBatchDepth() {
        return 16;
    }

    public final int runLimit() {
        return 1024;
    }

    private BatchingExecutorStatics$() {
    }
}

