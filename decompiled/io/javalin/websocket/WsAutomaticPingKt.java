/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.websocket;

import io.javalin.util.ConcurrencyUtilKt;
import io.javalin.websocket.WsAutomaticPingKt;
import io.javalin.websocket.WsContext;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u00004\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\b\u001a(\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016\"\u001b\u0010\u0000\u001a\u00020\u00018FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0002\u0010\u0003\"-\u0010\u0006\u001a\u0014\u0012\u0004\u0012\u00020\b\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\t0\u00078FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\f\u0010\u0005\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0017"}, d2={"executor", "Ljava/util/concurrent/ScheduledExecutorService;", "getExecutor", "()Ljava/util/concurrent/ScheduledExecutorService;", "executor$delegate", "Lkotlin/Lazy;", "pingFutures", "Ljava/util/concurrent/ConcurrentHashMap;", "Lio/javalin/websocket/WsContext;", "Ljava/util/concurrent/ScheduledFuture;", "getPingFutures", "()Ljava/util/concurrent/ConcurrentHashMap;", "pingFutures$delegate", "disableAutomaticPings", "", "ctx", "enableAutomaticPings", "interval", "", "unit", "Ljava/util/concurrent/TimeUnit;", "applicationData", "Ljava/nio/ByteBuffer;", "javalin"})
public final class WsAutomaticPingKt {
    @NotNull
    private static final Lazy executor$delegate = ConcurrencyUtilKt.javalinLazy$default(null, executor.2.INSTANCE, 1, null);
    @NotNull
    private static final Lazy pingFutures$delegate = ConcurrencyUtilKt.javalinLazy$default(null, pingFutures.2.INSTANCE, 1, null);

    @NotNull
    public static final ScheduledExecutorService getExecutor() {
        Lazy lazy = executor$delegate;
        return (ScheduledExecutorService)lazy.getValue();
    }

    @NotNull
    public static final ConcurrentHashMap<WsContext, ScheduledFuture<?>> getPingFutures() {
        Lazy lazy = pingFutures$delegate;
        return (ConcurrentHashMap)lazy.getValue();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static final void enableAutomaticPings(@NotNull WsContext ctx, long interval, @NotNull TimeUnit unit, @Nullable ByteBuffer applicationData) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter((Object)unit, "unit");
        WsContext wsContext = ctx;
        synchronized (wsContext) {
            boolean bl = false;
            WsAutomaticPingKt.disableAutomaticPings(ctx);
            ((Map)WsAutomaticPingKt.getPingFutures()).put(ctx, WsAutomaticPingKt.getExecutor().scheduleAtFixedRate(() -> WsAutomaticPingKt.enableAutomaticPings$lambda$1$lambda$0(ctx, applicationData), interval, interval, unit));
            Unit unit2 = Unit.INSTANCE;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static final void disableAutomaticPings(@NotNull WsContext ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        WsContext wsContext = ctx;
        synchronized (wsContext) {
            boolean bl = false;
            ScheduledFuture<?> scheduledFuture = WsAutomaticPingKt.getPingFutures().get(ctx);
            if (scheduledFuture != null) {
                scheduledFuture.cancel(false);
            }
            ScheduledFuture<?> scheduledFuture2 = WsAutomaticPingKt.getPingFutures().remove(ctx);
        }
    }

    private static final void enableAutomaticPings$lambda$1$lambda$0(WsContext $ctx, ByteBuffer $applicationData) {
        Intrinsics.checkNotNullParameter($ctx, "$ctx");
        $ctx.sendPing($applicationData);
    }
}

