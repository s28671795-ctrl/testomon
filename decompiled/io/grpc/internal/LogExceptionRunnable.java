/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class LogExceptionRunnable
implements Runnable {
    private static final Logger log = Logger.getLogger(LogExceptionRunnable.class.getName());
    private final Runnable task;

    public LogExceptionRunnable(Runnable task) {
        this.task = Preconditions.checkNotNull(task, "task");
    }

    @Override
    public void run() {
        try {
            this.task.run();
        }
        catch (Throwable t2) {
            log.log(Level.SEVERE, "Exception while executing runnable " + this.task, t2);
            Throwables.throwIfUnchecked(t2);
            throw new AssertionError((Object)t2);
        }
    }

    public String toString() {
        return "LogExceptionRunnable(" + this.task + ")";
    }
}

