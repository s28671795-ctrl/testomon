/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.thread;

public interface TaskRunner {
    public void wakeup() throws InterruptedException;

    public void shutdown() throws InterruptedException;

    public void shutdown(long var1) throws InterruptedException;
}

