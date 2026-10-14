/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.orbutil.threadpool;

import org.jboss.com.sun.corba.se.spi.orbutil.threadpool.ThreadPool;

public interface ThreadPoolChooser {
    public ThreadPool getThreadPool();

    public ThreadPool getThreadPool(int var1);

    public String[] getThreadPoolIds();
}

