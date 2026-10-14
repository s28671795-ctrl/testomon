/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.orbutil.concurrent;

public interface Sync {
    public static final long ONE_SECOND = 1000L;
    public static final long ONE_MINUTE = 60000L;
    public static final long ONE_HOUR = 3600000L;
    public static final long ONE_DAY = 86400000L;
    public static final long ONE_WEEK = 604800000L;
    public static final long ONE_YEAR = 31556952000L;
    public static final long ONE_CENTURY = 3155695200000L;

    public void acquire() throws InterruptedException;

    public boolean attempt(long var1) throws InterruptedException;

    public void release();
}

