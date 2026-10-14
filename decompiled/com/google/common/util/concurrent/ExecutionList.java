/*
 * Decompiled with CFR 0.152.
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.ElementTypesAreNonnullByDefault;
import com.google.common.util.concurrent.LazyLogger;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import javax.annotation.CheckForNull;

@ElementTypesAreNonnullByDefault
@J2ktIncompatible
@GwtIncompatible
public final class ExecutionList {
    private static final LazyLogger log = new LazyLogger(ExecutionList.class);
    @CheckForNull
    @GuardedBy(value="this")
    private RunnableExecutorPair runnables;
    @GuardedBy(value="this")
    private boolean executed;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void add(Runnable runnable2, Executor executor2) {
        Preconditions.checkNotNull(runnable2, "Runnable was null.");
        Preconditions.checkNotNull(executor2, "Executor was null.");
        ExecutionList executionList = this;
        synchronized (executionList) {
            if (!this.executed) {
                this.runnables = new RunnableExecutorPair(runnable2, executor2, this.runnables);
                return;
            }
        }
        ExecutionList.executeListener(runnable2, executor2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void execute() {
        RunnableExecutorPair list;
        ExecutionList executionList = this;
        synchronized (executionList) {
            if (this.executed) {
                return;
            }
            this.executed = true;
            list = this.runnables;
            this.runnables = null;
        }
        RunnableExecutorPair reversedList = null;
        while (list != null) {
            RunnableExecutorPair tmp = list;
            list = list.next;
            tmp.next = reversedList;
            reversedList = tmp;
        }
        while (reversedList != null) {
            ExecutionList.executeListener(reversedList.runnable, reversedList.executor);
            reversedList = reversedList.next;
        }
    }

    private static void executeListener(Runnable runnable2, Executor executor2) {
        try {
            executor2.execute(runnable2);
        }
        catch (Exception e) {
            log.get().log(Level.SEVERE, "RuntimeException while executing runnable " + runnable2 + " with executor " + executor2, e);
        }
    }

    private static final class RunnableExecutorPair {
        final Runnable runnable;
        final Executor executor;
        @CheckForNull
        RunnableExecutorPair next;

        RunnableExecutorPair(Runnable runnable2, Executor executor2, @CheckForNull RunnableExecutorPair next) {
            this.runnable = runnable2;
            this.executor = executor2;
            this.next = next;
        }
    }
}

