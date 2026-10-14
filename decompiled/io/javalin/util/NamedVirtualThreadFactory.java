/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.util;

import io.javalin.util.NamedThreadFactory;
import io.javalin.util.ReflectiveVirtualThreadBuilder;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016\u00a8\u0006\t"}, d2={"Lio/javalin/util/NamedVirtualThreadFactory;", "Lio/javalin/util/NamedThreadFactory;", "prefix", "", "(Ljava/lang/String;)V", "newThread", "Ljava/lang/Thread;", "runnable", "Ljava/lang/Runnable;", "javalin"})
public class NamedVirtualThreadFactory
extends NamedThreadFactory {
    public NamedVirtualThreadFactory(@NotNull String prefix) {
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        super(prefix);
    }

    @Override
    @NotNull
    public Thread newThread(@NotNull Runnable runnable2) {
        Intrinsics.checkNotNullParameter(runnable2, "runnable");
        return new ReflectiveVirtualThreadBuilder().name(this.getPrefix() + "-Virtual-" + this.getThreadCount().getAndIncrement()).unstarted(runnable2);
    }
}

