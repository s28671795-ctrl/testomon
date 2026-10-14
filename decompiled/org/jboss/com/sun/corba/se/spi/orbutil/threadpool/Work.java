/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.orbutil.threadpool;

public interface Work {
    public void doWork();

    public void setEnqueueTime(long var1);

    public long getEnqueueTime();

    public String getName();
}

