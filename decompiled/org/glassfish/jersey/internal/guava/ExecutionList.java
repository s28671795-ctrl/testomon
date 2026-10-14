/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.guava;

import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.jersey.internal.guava.Preconditions;

final class ExecutionList {
    private static final Logger log = Logger.getLogger(ExecutionList.class.getName());
    private RunnableExecutorPair runnables;
    private boolean executed;

    private static void executeListener(Runnable runnable2, Executor executor2) {
        try {
            executor2.execute(runnable2);
        }
        catch (RuntimeException e) {
            log.log(Level.SEVERE, "RuntimeException while executing runnable " + runnable2 + " with executor " + executor2, e);
        }
    }

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

    private static final class RunnableExecutorPair {
        final Runnable runnable;
        final Executor executor;
        RunnableExecutorPair next;

        RunnableExecutorPair(Runnable runnable2, Executor executor2, RunnableExecutorPair next) {
            this.runnable = runnable2;
            this.executor = executor2;
            this.next = next;
        }
    }
}

