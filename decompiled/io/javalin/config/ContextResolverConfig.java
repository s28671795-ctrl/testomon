/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.config;

import io.javalin.config.ContextResolverConfig;
import io.javalin.http.Context;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002R\u001e\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u00048\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R \u0010\u0007\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u00048\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u001e\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u00048\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u001e\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u00048\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u001e\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u00048\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2={"Lio/javalin/config/ContextResolverConfig;", "", "()V", "fullUrl", "Lkotlin/Function1;", "Lio/javalin/http/Context;", "", "host", "ip", "scheme", "url", "javalin"})
public final class ContextResolverConfig {
    @JvmField
    @NotNull
    public Function1<? super Context, String> ip = ip.1.INSTANCE;
    @JvmField
    @NotNull
    public Function1<? super Context, String> host = host.1.INSTANCE;
    @JvmField
    @NotNull
    public Function1<? super Context, String> scheme = scheme.1.INSTANCE;
    @JvmField
    @NotNull
    public Function1<? super Context, String> url = url.1.INSTANCE;
    @JvmField
    @NotNull
    public Function1<? super Context, String> fullUrl = fullUrl.1.INSTANCE;
}

