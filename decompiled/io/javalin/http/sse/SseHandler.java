/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.http.sse;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.sse.SseClient;
import jakarta.servlet.http.HttpServletResponse;
import java.util.function.Consumer;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\u0002\u0010\u0007J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2={"Lio/javalin/http/sse/SseHandler;", "Lio/javalin/http/Handler;", "timeout", "", "clientConsumer", "Ljava/util/function/Consumer;", "Lio/javalin/http/sse/SseClient;", "(JLjava/util/function/Consumer;)V", "handle", "", "ctx", "Lio/javalin/http/Context;", "javalin"})
public final class SseHandler
implements Handler {
    private final long timeout;
    @NotNull
    private final Consumer<SseClient> clientConsumer;

    @JvmOverloads
    public SseHandler(long timeout2, @NotNull Consumer<SseClient> clientConsumer) {
        Intrinsics.checkNotNullParameter(clientConsumer, "clientConsumer");
        this.timeout = timeout2;
        this.clientConsumer = clientConsumer;
    }

    public /* synthetic */ SseHandler(long l, Consumer consumer, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            l = 0L;
        }
        this(l, consumer);
    }

    @Override
    public void handle(@NotNull Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        if (Intrinsics.areEqual(ctx.header("Accept"), "text/event-stream")) {
            HttpServletResponse httpServletResponse;
            HttpServletResponse $this$handle_u24lambda_u240 = httpServletResponse = ctx.res();
            boolean bl = false;
            $this$handle_u24lambda_u240.setStatus(200);
            $this$handle_u24lambda_u240.setCharacterEncoding("UTF-8");
            $this$handle_u24lambda_u240.setContentType("text/event-stream");
            $this$handle_u24lambda_u240.addHeader("Connection", "close");
            $this$handle_u24lambda_u240.addHeader("Cache-Control", "no-cache");
            $this$handle_u24lambda_u240.addHeader("X-Accel-Buffering", "no");
            $this$handle_u24lambda_u240.flushBuffer();
            Context.async$default(ctx, this.timeout, null, () -> SseHandler.handle$lambda$1(this, ctx), 2, null);
        }
    }

    @JvmOverloads
    public SseHandler(@NotNull Consumer<SseClient> clientConsumer) {
        Intrinsics.checkNotNullParameter(clientConsumer, "clientConsumer");
        this(0L, clientConsumer, 1, null);
    }

    private static final void handle$lambda$1(SseHandler this$0, Context $ctx) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter($ctx, "$ctx");
        this$0.clientConsumer.accept(new SseClient($ctx));
    }
}

