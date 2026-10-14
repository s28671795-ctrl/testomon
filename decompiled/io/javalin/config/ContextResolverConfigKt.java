/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.config;

import io.javalin.config.ContextResolverConfig;
import io.javalin.http.Context;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000\u0012\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0002\u001a\u00020\u0003*\u00020\u0004\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2={"CONTEXT_RESOLVER_KEY", "", "contextResolver", "Lio/javalin/config/ContextResolverConfig;", "Lio/javalin/http/Context;", "javalin"})
public final class ContextResolverConfigKt {
    @NotNull
    public static final String CONTEXT_RESOLVER_KEY = "javalin-context-resolver";

    @NotNull
    public static final ContextResolverConfig contextResolver(@NotNull Context $this$contextResolver) {
        Intrinsics.checkNotNullParameter($this$contextResolver, "<this>");
        return (ContextResolverConfig)$this$contextResolver.appAttribute(CONTEXT_RESOLVER_KEY);
    }
}

