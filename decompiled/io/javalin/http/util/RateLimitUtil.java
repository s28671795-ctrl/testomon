/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.http.util;

import io.javalin.http.Context;
import io.javalin.http.util.RateLimitUtil;
import io.javalin.http.util.RateLimiter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0015\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\tH\u0002R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R&\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001d\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00120\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006\u0017"}, d2={"Lio/javalin/http/util/RateLimitUtil;", "", "()V", "executor", "Ljava/util/concurrent/ScheduledExecutorService;", "getExecutor", "()Ljava/util/concurrent/ScheduledExecutorService;", "keyFunction", "Lkotlin/Function1;", "Lio/javalin/http/Context;", "", "getKeyFunction", "()Lkotlin/jvm/functions/Function1;", "setKeyFunction", "(Lkotlin/jvm/functions/Function1;)V", "limiters", "Ljava/util/concurrent/ConcurrentHashMap;", "Ljava/util/concurrent/TimeUnit;", "Lio/javalin/http/util/RateLimiter;", "getLimiters", "()Ljava/util/concurrent/ConcurrentHashMap;", "ip", "ctx", "javalin"})
public final class RateLimitUtil {
    @NotNull
    public static final RateLimitUtil INSTANCE = new RateLimitUtil();
    @NotNull
    private static final ConcurrentHashMap<TimeUnit, RateLimiter> limiters = new ConcurrentHashMap();
    @NotNull
    private static Function1<? super Context, String> keyFunction = keyFunction.1.INSTANCE;
    @NotNull
    private static final ScheduledExecutorService executor;

    private RateLimitUtil() {
    }

    @NotNull
    public final ConcurrentHashMap<TimeUnit, RateLimiter> getLimiters() {
        return limiters;
    }

    @NotNull
    public final Function1<Context, String> getKeyFunction() {
        return keyFunction;
    }

    public final void setKeyFunction(@NotNull Function1<? super Context, String> function1) {
        Intrinsics.checkNotNullParameter(function1, "<set-?>");
        keyFunction = function1;
    }

    @NotNull
    public final ScheduledExecutorService getExecutor() {
        return executor;
    }

    private final String ip(Context ctx) {
        String[] stringArray;
        Object object = ctx.header("X-Forwarded-For");
        if (object == null || (object = StringsKt.split$default((CharSequence)object, stringArray = new String[]{","}, false, 0, 6, null)) == null || (object = (String)object.get(0)) == null) {
            object = ctx.ip();
        }
        return object;
    }

    public static final /* synthetic */ String access$ip(RateLimitUtil $this, Context ctx) {
        return $this.ip(ctx);
    }

    static {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        Intrinsics.checkNotNullExpressionValue(scheduledExecutorService, "newSingleThreadScheduledExecutor()");
        executor = scheduledExecutorService;
    }
}

