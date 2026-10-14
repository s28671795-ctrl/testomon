/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.http.servlet;

import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;
import io.javalin.http.ExceptionHandler;
import io.javalin.http.HttpResponseException;
import io.javalin.http.HttpStatus;
import io.javalin.http.InternalServerErrorResponse;
import io.javalin.http.servlet.HttpResponseExceptionMapper;
import io.javalin.jetty.JettyUtil;
import io.javalin.util.JavalinLogger;
import io.javalin.util.Util;
import jakarta.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletionException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001d\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0000\u00a2\u0006\u0002\b\u0015J\u001f\u0010\u0016\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0014H\u0000\u00a2\u0006\u0002\b\u001bJ\u0014\u0010\u001c\u001a\u00020\u001d2\n\u0010\u001e\u001a\u00060\nj\u0002`\u000bH\u0002J\u001c\u0010\u001f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\n\u0010\u001e\u001a\u00060\nj\u0002`\u000bH\u0002R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R5\u0010\u0007\u001a&\u0012\u0010\u0012\u000e\u0012\n\b\u0001\u0012\u00060\nj\u0002`\u000b0\t\u0012\u0010\u0012\u000e\u0012\b\u0012\u00060\nj\u0002`\u000b\u0018\u00010\f0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u00a8\u0006 "}, d2={"Lio/javalin/http/servlet/ExceptionMapper;", "", "cfg", "Lio/javalin/config/JavalinConfig;", "(Lio/javalin/config/JavalinConfig;)V", "getCfg", "()Lio/javalin/config/JavalinConfig;", "handlers", "", "Ljava/lang/Class;", "Ljava/lang/Exception;", "Lkotlin/Exception;", "Lio/javalin/http/ExceptionHandler;", "getHandlers", "()Ljava/util/Map;", "handle", "", "ctx", "Lio/javalin/http/Context;", "t", "", "handle$javalin", "handleUnexpectedThrowable", "", "res", "Ljakarta/servlet/http/HttpServletResponse;", "throwable", "handleUnexpectedThrowable$javalin", "noUserHandler", "", "exception", "uncaughtException", "javalin"})
public final class ExceptionMapper {
    @NotNull
    private final JavalinConfig cfg;
    @NotNull
    private final Map<Class<? extends Exception>, ExceptionHandler<Exception>> handlers;

    public ExceptionMapper(@NotNull JavalinConfig cfg) {
        Intrinsics.checkNotNullParameter(cfg, "cfg");
        this.cfg = cfg;
        this.handlers = new LinkedHashMap();
    }

    @NotNull
    public final JavalinConfig getCfg() {
        return this.cfg;
    }

    @NotNull
    public final Map<Class<? extends Exception>, ExceptionHandler<Exception>> getHandlers() {
        return this.handlers;
    }

    public final void handle$javalin(@NotNull Context ctx, @NotNull Throwable t2) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(t2, "t");
        if (t2 instanceof CompletionException && t2.getCause() instanceof Exception) {
            Throwable throwable = t2.getCause();
            Intrinsics.checkNotNull(throwable, "null cannot be cast to non-null type java.lang.Exception{ kotlin.TypeAliasesKt.Exception }");
            this.handle$javalin(ctx, (Exception)throwable);
            return;
        }
        if (JettyUtil.INSTANCE.isSomewhatExpectedException(t2)) {
            JettyUtil.INSTANCE.logDebugAndSetError(t2, ctx.res());
        } else if (t2 instanceof Exception && HttpResponseExceptionMapper.INSTANCE.canHandle(t2) && this.noUserHandler((Exception)t2)) {
            HttpResponseExceptionMapper.INSTANCE.handle((HttpResponseException)t2, ctx);
        } else if (t2 instanceof Exception) {
            Unit unit;
            ExceptionHandler<Exception> exceptionHandler = Util.INSTANCE.findByClass(this.handlers, t2.getClass());
            if (exceptionHandler != null) {
                exceptionHandler.handle((Exception)t2, ctx);
                unit = Unit.INSTANCE;
            } else {
                unit = null;
            }
            if (unit == null) {
                this.uncaughtException(ctx, (Exception)t2);
            }
        } else {
            this.handleUnexpectedThrowable$javalin(ctx.res(), t2);
        }
    }

    private final void uncaughtException(Context ctx, Exception exception) {
        JavalinLogger.warn("Uncaught exception", exception);
        HttpResponseExceptionMapper.INSTANCE.handle(new InternalServerErrorResponse(null, null, 3, null), ctx);
    }

    @Nullable
    public final Void handleUnexpectedThrowable$javalin(@NotNull HttpServletResponse res, @NotNull Throwable throwable) {
        Intrinsics.checkNotNullParameter(res, "res");
        Intrinsics.checkNotNullParameter(throwable, "throwable");
        res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.getCode());
        if (throwable instanceof Error) {
            this.cfg.pvt.getJavaLangErrorHandler$javalin().handle(res, (Error)throwable);
        } else if (JettyUtil.INSTANCE.isSomewhatExpectedException(throwable)) {
            JettyUtil.INSTANCE.logDebugAndSetError(throwable, res);
        } else {
            JavalinLogger.error("Exception occurred while servicing http-request", throwable);
        }
        return null;
    }

    private final boolean noUserHandler(Exception exception) {
        return this.handlers.get(exception.getClass()) == null && this.handlers.get(HttpResponseException.class) == null;
    }
}

