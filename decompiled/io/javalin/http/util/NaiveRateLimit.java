/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.http.util;

import io.javalin.http.Context;
import io.javalin.http.util.RateLimitUtil;
import io.javalin.http.util.RateLimiter;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007\u00a8\u0006\u000b"}, d2={"Lio/javalin/http/util/NaiveRateLimit;", "", "()V", "requestPerTimeUnit", "", "ctx", "Lio/javalin/http/Context;", "numRequests", "", "timeUnit", "Ljava/util/concurrent/TimeUnit;", "javalin"})
public final class NaiveRateLimit {
    @NotNull
    public static final NaiveRateLimit INSTANCE = new NaiveRateLimit();

    private NaiveRateLimit() {
    }

    @JvmStatic
    public static final void requestPerTimeUnit(@NotNull Context ctx, int numRequests, @NotNull TimeUnit timeUnit) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter((Object)timeUnit, "timeUnit");
        RateLimitUtil.INSTANCE.getLimiters().computeIfAbsent(timeUnit, arg_0 -> NaiveRateLimit.requestPerTimeUnit$lambda$0(new Function1<TimeUnit, RateLimiter>(timeUnit){
            final /* synthetic */ TimeUnit $timeUnit;
            {
                this.$timeUnit = $timeUnit;
                super(1);
            }

            @NotNull
            public final RateLimiter invoke(@NotNull TimeUnit it) {
                Intrinsics.checkNotNullParameter((Object)((Object)it), "it");
                return new RateLimiter(this.$timeUnit);
            }
        }, arg_0)).incrementCounter(ctx, numRequests);
    }

    private static final RateLimiter requestPerTimeUnit$lambda$0(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter($tmp0, "$tmp0");
        return (RateLimiter)$tmp0.invoke(p0);
    }
}

