/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.orbutil.threadpool;

import org.jboss.com.sun.corba.se.spi.orbutil.threadpool.NoSuchWorkQueueException;
import org.jboss.com.sun.corba.se.spi.orbutil.threadpool.WorkQueue;

public interface ThreadPool {
    public WorkQueue getAnyWorkQueue();

    public WorkQueue getWorkQueue(int var1) throws NoSuchWorkQueueException;

    public int numberOfWorkQueues();

    public int minimumNumberOfThreads();

    public int maximumNumberOfThreads();

    public long idleTimeoutForThreads();

    public int currentNumberOfThreads();

    public int numberOfAvailableThreads();

    public int numberOfBusyThreads();

    public long currentProcessedCount();

    public long averageWorkCompletionTime();

    public String getName();
}

