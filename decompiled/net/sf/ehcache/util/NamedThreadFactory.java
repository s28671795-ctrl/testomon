/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThreadFactory
implements ThreadFactory {
    private static AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;
    private final boolean daemon;

    public NamedThreadFactory(String namePrefix, boolean daemon) {
        this.namePrefix = namePrefix;
        this.daemon = daemon;
    }

    public NamedThreadFactory(String namePrefix) {
        this(namePrefix, false);
    }

    @Override
    public Thread newThread(Runnable runnable2) {
        Thread thread2 = new Thread(runnable2, this.namePrefix + " thread-" + threadNumber.getAndIncrement());
        thread2.setDaemon(this.daemon);
        return thread2;
    }
}

