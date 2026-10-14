/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.http.servlet;

import jakarta.servlet.http.HttpServletResponse;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u00e6\u0080\u0001\u0018\u00002\u00020\u0001J\u001c\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\n\u0010\u0006\u001a\u00060\u0007j\u0002`\bH&\u00f8\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001\u00a8\u0006\t\u00c0\u0006\u0001"}, d2={"Lio/javalin/http/servlet/JavaLangErrorHandler;", "", "handle", "", "res", "Ljakarta/servlet/http/HttpServletResponse;", "err", "Ljava/lang/Error;", "Lkotlin/Error;", "javalin"})
public interface JavaLangErrorHandler {
    public void handle(@NotNull HttpServletResponse var1, @NotNull Error var2);
}

