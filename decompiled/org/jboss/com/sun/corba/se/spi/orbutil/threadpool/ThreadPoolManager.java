/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.orbutil.threadpool;

import org.jboss.com.sun.corba.se.spi.orbutil.threadpool.NoSuchThreadPoolException;
import org.jboss.com.sun.corba.se.spi.orbutil.threadpool.ThreadPool;
import org.jboss.com.sun.corba.se.spi.orbutil.threadpool.ThreadPoolChooser;

public interface ThreadPoolManager {
    public ThreadPool getThreadPool(String var1) throws NoSuchThreadPoolException;

    public ThreadPool getThreadPool(int var1) throws NoSuchThreadPoolException;

    public int getThreadPoolNumericId(String var1);

    public String getThreadPoolStringId(int var1);

    public ThreadPool getDefaultThreadPool();

    public ThreadPoolChooser getThreadPoolChooser(String var1);

    public ThreadPoolChooser getThreadPoolChooser(int var1);

    public void setThreadPoolChooser(String var1, ThreadPoolChooser var2);

    public int getThreadPoolChooserNumericId(String var1);
}

