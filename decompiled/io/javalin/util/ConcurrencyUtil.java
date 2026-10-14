/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.util;

import io.javalin.util.LoomUtil;
import io.javalin.util.NamedThreadFactory;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0007J\b\u0010\u000e\u001a\u00020\u0004H\u0007J \u0010\u000f\u001a\u00020\u00102\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012H\u0007J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\f\u001a\u00020\rH\u0007R \u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u0014\n\u0000\u0012\u0004\b\u0005\u0010\u0002\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t\u00a8\u0006\u0016"}, d2={"Lio/javalin/util/ConcurrencyUtil;", "", "()V", "useLoom", "", "getUseLoom$annotations", "getUseLoom", "()Z", "setUseLoom", "(Z)V", "executorService", "Ljava/util/concurrent/ExecutorService;", "name", "", "isLoomAvailable", "jettyThreadPool", "Lorg/eclipse/jetty/util/thread/ThreadPool;", "minThreads", "", "maxThreads", "newSingleThreadScheduledExecutor", "Ljava/util/concurrent/ScheduledExecutorService;", "javalin"})
@SourceDebugExtension(value={"SMAP\nConcurrencyUtil.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ConcurrencyUtil.kt\nio/javalin/util/ConcurrencyUtil\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,172:1\n1#2:173\n*E\n"})
public final class ConcurrencyUtil {
    @NotNull
    public static final ConcurrencyUtil INSTANCE = new ConcurrencyUtil();
    private static boolean useLoom = true;

    private ConcurrencyUtil() {
    }

    public final boolean getUseLoom() {
        return useLoom;
    }

    public final void setUseLoom(boolean bl) {
        useLoom = bl;
    }

    public static /* synthetic */ void getUseLoom$annotations() {
    }

    /*
     * Unable to fully structure code
     */
    @JvmStatic
    @NotNull
    public static final ExecutorService executorService(@NotNull String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        if (!ConcurrencyUtil.useLoom) ** GOTO lbl-1000
        if (ConcurrencyUtil.INSTANCE.isLoomAvailable()) {
            v0 = true;
        } else lbl-1000:
        // 2 sources

        {
            v0 = var1_1 = false;
        }
        if (var1_1) {
            v1 = LoomUtil.INSTANCE.getExecutorService(name);
        } else if (!var1_1) {
            v2 = Executors.newCachedThreadPool(new NamedThreadFactory(name));
            v1 = v2;
            Intrinsics.checkNotNullExpressionValue(v2, "newCachedThreadPool(NamedThreadFactory(name))");
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return v1;
    }

    @JvmStatic
    @NotNull
    public static final ScheduledExecutorService newSingleThreadScheduledExecutor(@NotNull String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(new NamedThreadFactory(name));
        Intrinsics.checkNotNullExpressionValue(scheduledExecutorService, "newSingleThreadScheduled\u2026NamedThreadFactory(name))");
        return scheduledExecutorService;
    }

    /*
     * Unable to fully structure code
     */
    @JvmStatic
    @NotNull
    public static final ThreadPool jettyThreadPool(@NotNull String name, int minThreads, int maxThreads) {
        Intrinsics.checkNotNullParameter(name, "name");
        if (!ConcurrencyUtil.useLoom) ** GOTO lbl-1000
        if (ConcurrencyUtil.INSTANCE.isLoomAvailable()) {
            v0 = true;
        } else lbl-1000:
        // 2 sources

        {
            v0 = var3_3 = false;
        }
        if (var3_3) {
            v1 = LoomUtil.INSTANCE.getThreadPool(name);
        } else if (!var3_3) {
            $this$jettyThreadPool_u24lambda_u240 = var4_4 = new QueuedThreadPool(maxThreads, minThreads, 60000);
            $i$a$-apply-ConcurrencyUtil$jettyThreadPool$1 = false;
            $this$jettyThreadPool_u24lambda_u240.setName(name);
            v1 = var4_4;
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return v1;
    }

    @JvmStatic
    public static final boolean isLoomAvailable() {
        return LoomUtil.INSTANCE.getLoomAvailable();
    }
}

