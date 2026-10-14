/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0084\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0084\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\fX\u0084\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0013"}, d2={"Lio/javalin/util/NamedThreadFactory;", "Ljava/util/concurrent/ThreadFactory;", "prefix", "", "(Ljava/lang/String;)V", "group", "Ljava/lang/ThreadGroup;", "getGroup", "()Ljava/lang/ThreadGroup;", "getPrefix", "()Ljava/lang/String;", "threadCount", "Ljava/util/concurrent/atomic/AtomicInteger;", "getThreadCount", "()Ljava/util/concurrent/atomic/AtomicInteger;", "newThread", "Ljava/lang/Thread;", "runnable", "Ljava/lang/Runnable;", "javalin"})
public class NamedThreadFactory
implements ThreadFactory {
    @NotNull
    private final String prefix;
    @Nullable
    private final ThreadGroup group;
    @NotNull
    private final AtomicInteger threadCount;

    public NamedThreadFactory(@NotNull String prefix) {
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        this.prefix = prefix;
        this.group = Thread.currentThread().getThreadGroup();
        this.threadCount = new AtomicInteger(0);
    }

    @NotNull
    protected final String getPrefix() {
        return this.prefix;
    }

    @Nullable
    protected final ThreadGroup getGroup() {
        return this.group;
    }

    @NotNull
    protected final AtomicInteger getThreadCount() {
        return this.threadCount;
    }

    @Override
    @NotNull
    public Thread newThread(@NotNull Runnable runnable2) {
        Intrinsics.checkNotNullParameter(runnable2, "runnable");
        return new Thread(this.group, runnable2, this.prefix + "-" + this.threadCount.getAndIncrement(), 0L);
    }
}

