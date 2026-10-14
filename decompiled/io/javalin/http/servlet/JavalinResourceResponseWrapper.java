/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.http.servlet;

import io.javalin.http.Context;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponseWrapper;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2={"Lio/javalin/http/servlet/JavalinResourceResponseWrapper;", "Ljakarta/servlet/http/HttpServletResponseWrapper;", "ctx", "Lio/javalin/http/Context;", "(Lio/javalin/http/Context;)V", "getOutputStream", "Ljakarta/servlet/ServletOutputStream;", "javalin"})
final class JavalinResourceResponseWrapper
extends HttpServletResponseWrapper {
    @NotNull
    private final Context ctx;

    public JavalinResourceResponseWrapper(@NotNull Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        super(ctx.res());
        this.ctx = ctx;
    }

    @Override
    @NotNull
    public ServletOutputStream getOutputStream() {
        return this.ctx.outputStream();
    }
}

