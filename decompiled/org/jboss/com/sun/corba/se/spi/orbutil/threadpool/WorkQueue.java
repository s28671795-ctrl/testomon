/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.orbutil.threadpool;

import org.jboss.com.sun.corba.se.spi.orbutil.threadpool.ThreadPool;
import org.jboss.com.sun.corba.se.spi.orbutil.threadpool.Work;

public interface WorkQueue {
    public void addWork(Work var1);

    public String getName();

    public long totalWorkItemsAdded();

    public int workItemsInQueue();

    public long averageTimeInQueue();

    public void setThreadPool(ThreadPool var1);

    public ThreadPool getThreadPool();
}

