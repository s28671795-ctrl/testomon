/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.util;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VirtualThreads {
    private static final Logger LOG = LoggerFactory.getLogger(VirtualThreads.class);
    private static final Executor executor = VirtualThreads.probeVirtualThreadExecutor();
    private static final Method isVirtualThread = VirtualThreads.probeIsVirtualThread();

    private static Executor probeVirtualThreadExecutor() {
        try {
            return (Executor)Executors.class.getMethod("newVirtualThreadPerTaskExecutor", new Class[0]).invoke(null, new Object[0]);
        }
        catch (Throwable x) {
            return null;
        }
    }

    private static Method probeIsVirtualThread() {
        try {
            return Thread.class.getMethod("isVirtual", new Class[0]);
        }
        catch (Throwable x) {
            return null;
        }
    }

    private static Method getIsVirtualThreadMethod() {
        return isVirtualThread;
    }

    private static void warn() {
        LOG.warn("Virtual thread support is not available (or not enabled via --enable-preview) in the current Java runtime ({})", (Object)System.getProperty("java.version"));
    }

    public static boolean areSupported() {
        return executor != null;
    }

    @Deprecated(forRemoval=true)
    public static void executeOnVirtualThread(Runnable task) {
        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Starting in virtual thread: {}", (Object)task);
            }
            VirtualThreads.getDefaultVirtualThreadsExecutor().execute(task);
        }
        catch (Throwable x) {
            VirtualThreads.warn();
            throw new UnsupportedOperationException(x);
        }
    }

    public static boolean isVirtualThread() {
        try {
            return (Boolean)VirtualThreads.getIsVirtualThreadMethod().invoke((Object)Thread.currentThread(), new Object[0]);
        }
        catch (Throwable x) {
            VirtualThreads.warn();
            return false;
        }
    }

    public static Executor getDefaultVirtualThreadsExecutor() {
        return executor;
    }

    public static Executor getVirtualThreadsExecutor(Executor executor2) {
        if (executor2 instanceof Configurable) {
            return ((Configurable)((Object)executor2)).getVirtualThreadsExecutor();
        }
        return null;
    }

    public static boolean isUseVirtualThreads(Executor executor2) {
        if (executor2 instanceof Configurable) {
            return ((Configurable)((Object)executor2)).getVirtualThreadsExecutor() != null;
        }
        return false;
    }

    private VirtualThreads() {
    }

    public static interface Configurable {
        default public Executor getVirtualThreadsExecutor() {
            return null;
        }

        default public void setVirtualThreadsExecutor(Executor executor2) {
            if (executor2 != null && !VirtualThreads.areSupported()) {
                VirtualThreads.warn();
                throw new UnsupportedOperationException();
            }
        }

        @Deprecated(forRemoval=true)
        default public boolean isUseVirtualThreads() {
            return this.getVirtualThreadsExecutor() != null;
        }

        @Deprecated(forRemoval=true)
        default public void setUseVirtualThreads(boolean useVirtualThreads) {
            this.setVirtualThreadsExecutor(useVirtualThreads ? executor : null);
        }
    }
}

