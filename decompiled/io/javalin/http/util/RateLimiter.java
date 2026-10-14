/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.http.util;

import io.javalin.http.Context;
import io.javalin.http.HttpResponseException;
import io.javalin.http.HttpStatus;
import io.javalin.http.util.RateLimitUtil;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\bR\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2={"Lio/javalin/http/util/RateLimiter;", "", "timeUnit", "Ljava/util/concurrent/TimeUnit;", "(Ljava/util/concurrent/TimeUnit;)V", "keyToRequestCount", "Ljava/util/concurrent/ConcurrentHashMap;", "", "", "getTimeUnit", "()Ljava/util/concurrent/TimeUnit;", "timeUnitString", "incrementCounter", "", "ctx", "Lio/javalin/http/Context;", "requestLimit", "javalin"})
public final class RateLimiter {
    @NotNull
    private final TimeUnit timeUnit;
    @NotNull
    private final String timeUnitString;
    @NotNull
    private final ConcurrentHashMap<String, Integer> keyToRequestCount;

    /*
     * WARNING - void declaration
     */
    public RateLimiter(@NotNull TimeUnit timeUnit) {
        void it;
        Intrinsics.checkNotNullParameter((Object)timeUnit, "timeUnit");
        this.timeUnit = timeUnit;
        Object object = this.timeUnit.toString();
        Locale locale = Locale.ROOT;
        Intrinsics.checkNotNullExpressionValue(locale, "ROOT");
        String string = ((String)object).toLowerCase(locale);
        Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(locale)");
        this.timeUnitString = StringsKt.removeSuffix(string, (CharSequence)"s");
        Object object2 = object = new ConcurrentHashMap();
        RateLimiter rateLimiter = this;
        boolean bl = false;
        RateLimitUtil.INSTANCE.getExecutor().scheduleAtFixedRate(() -> RateLimiter.keyToRequestCount$lambda$1$lambda$0((ConcurrentHashMap)it), 0L, 1L, this.timeUnit);
        rateLimiter.keyToRequestCount = object;
    }

    @NotNull
    public final TimeUnit getTimeUnit() {
        return this.timeUnit;
    }

    public final void incrementCounter(@NotNull Context ctx, int requestLimit) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        this.keyToRequestCount.compute(RateLimitUtil.INSTANCE.getKeyFunction().invoke(ctx), (arg_0, arg_1) -> RateLimiter.incrementCounter$lambda$2(new Function2<String, Integer, Integer>(requestLimit, this){
            final /* synthetic */ int $requestLimit;
            final /* synthetic */ RateLimiter this$0;
            {
                this.$requestLimit = $requestLimit;
                this.this$0 = $receiver;
                super(2);
            }

            @Nullable
            public final Integer invoke(@NotNull String string, @Nullable Integer count) {
                Integer n;
                Intrinsics.checkNotNullParameter(string, "<anonymous parameter 0>");
                if (count == null) {
                    n = 1;
                } else if (count < this.$requestLimit) {
                    n = count + 1;
                } else {
                    throw new HttpResponseException(HttpStatus.TOO_MANY_REQUESTS, "Rate limit exceeded - Server allows " + this.$requestLimit + " requests per " + RateLimiter.access$getTimeUnitString$p(this.this$0) + ".", null, 4, null);
                }
                return n;
            }
        }, arg_0, arg_1));
    }

    private static final void keyToRequestCount$lambda$1$lambda$0(ConcurrentHashMap $it) {
        Intrinsics.checkNotNullParameter($it, "$it");
        $it.clear();
    }

    private static final Integer incrementCounter$lambda$2(Function2 $tmp0, Object p0, Object p1) {
        Intrinsics.checkNotNullParameter($tmp0, "$tmp0");
        return (Integer)$tmp0.invoke(p0, p1);
    }

    public static final /* synthetic */ String access$getTimeUnitString$p(RateLimiter $this) {
        return $this.timeUnitString;
    }
}

