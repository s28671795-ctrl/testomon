/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class Task
implements Runnable {
    private static final AtomicInteger counter = new AtomicInteger();
    public volatile boolean stop;
    private volatile Object result;
    private volatile boolean finished;
    private Thread thread;
    private volatile Exception ex;

    public abstract void call() throws Exception;

    @Override
    public void run() {
        try {
            this.call();
        }
        catch (Exception exception) {
            this.ex = exception;
        }
        this.finished = true;
    }

    public Task execute() {
        return this.execute(this.getClass().getName() + ":" + counter.getAndIncrement());
    }

    public Task execute(String string) {
        this.thread = new Thread(this, string);
        this.thread.setDaemon(true);
        this.thread.start();
        return this;
    }

    public Object get() {
        Exception exception = this.getException();
        if (exception != null) {
            throw new RuntimeException(exception);
        }
        return this.result;
    }

    public boolean isFinished() {
        return this.finished;
    }

    public Exception getException() {
        this.join();
        if (this.ex != null) {
            return this.ex;
        }
        return null;
    }

    public void join() {
        this.stop = true;
        if (this.thread == null) {
            throw new IllegalStateException("Thread not started");
        }
        try {
            this.thread.join();
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
    }
}

