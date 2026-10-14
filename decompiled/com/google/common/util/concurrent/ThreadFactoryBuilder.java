/*
 * Decompiled with CFR 0.152.
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.ElementTypesAreNonnullByDefault;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.CheckForNull;

@ElementTypesAreNonnullByDefault
@J2ktIncompatible
@GwtIncompatible
public final class ThreadFactoryBuilder {
    @CheckForNull
    private String nameFormat = null;
    @CheckForNull
    private Boolean daemon = null;
    @CheckForNull
    private Integer priority = null;
    @CheckForNull
    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler = null;
    @CheckForNull
    private ThreadFactory backingThreadFactory = null;

    @CanIgnoreReturnValue
    public ThreadFactoryBuilder setNameFormat(String nameFormat) {
        String unused2 = ThreadFactoryBuilder.format(nameFormat, 0);
        this.nameFormat = nameFormat;
        return this;
    }

    @CanIgnoreReturnValue
    public ThreadFactoryBuilder setDaemon(boolean daemon) {
        this.daemon = daemon;
        return this;
    }

    @CanIgnoreReturnValue
    public ThreadFactoryBuilder setPriority(int priority) {
        Preconditions.checkArgument(priority >= 1, "Thread priority (%s) must be >= %s", priority, 1);
        Preconditions.checkArgument(priority <= 10, "Thread priority (%s) must be <= %s", priority, 10);
        this.priority = priority;
        return this;
    }

    @CanIgnoreReturnValue
    public ThreadFactoryBuilder setUncaughtExceptionHandler(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.uncaughtExceptionHandler = Preconditions.checkNotNull(uncaughtExceptionHandler);
        return this;
    }

    @CanIgnoreReturnValue
    public ThreadFactoryBuilder setThreadFactory(ThreadFactory backingThreadFactory) {
        this.backingThreadFactory = Preconditions.checkNotNull(backingThreadFactory);
        return this;
    }

    public ThreadFactory build() {
        return ThreadFactoryBuilder.doBuild(this);
    }

    private static ThreadFactory doBuild(ThreadFactoryBuilder builder) {
        final String nameFormat = builder.nameFormat;
        final Boolean daemon = builder.daemon;
        final Integer priority = builder.priority;
        final Thread.UncaughtExceptionHandler uncaughtExceptionHandler = builder.uncaughtExceptionHandler;
        final ThreadFactory backingThreadFactory = builder.backingThreadFactory != null ? builder.backingThreadFactory : Executors.defaultThreadFactory();
        final AtomicLong count = nameFormat != null ? new AtomicLong(0L) : null;
        return new ThreadFactory(){

            @Override
            public Thread newThread(Runnable runnable2) {
                Thread thread2 = backingThreadFactory.newThread(runnable2);
                Objects.requireNonNull(thread2);
                if (nameFormat != null) {
                    thread2.setName(ThreadFactoryBuilder.format(nameFormat, new Object[]{Objects.requireNonNull(count).getAndIncrement()}));
                }
                if (daemon != null) {
                    thread2.setDaemon(daemon);
                }
                if (priority != null) {
                    thread2.setPriority(priority);
                }
                if (uncaughtExceptionHandler != null) {
                    thread2.setUncaughtExceptionHandler(uncaughtExceptionHandler);
                }
                return thread2;
            }
        };
    }

    private static String format(String format, Object ... args2) {
        return String.format(Locale.ROOT, format, args2);
    }
}

