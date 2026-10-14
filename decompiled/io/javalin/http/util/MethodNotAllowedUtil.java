/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.http.util;

import io.javalin.http.Context;
import io.javalin.http.HandlerType;
import io.javalin.http.servlet.JavalinServletContextKt;
import io.javalin.routing.PathMatcher;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ(\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\t0\u000b2\u0006\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a8\u0006\u000f"}, d2={"Lio/javalin/http/util/MethodNotAllowedUtil;", "", "()V", "findAvailableHttpHandlerTypes", "", "Lio/javalin/http/HandlerType;", "matcher", "Lio/javalin/routing/PathMatcher;", "requestUri", "", "getAvailableHandlerTypes", "", "ctx", "Lio/javalin/http/Context;", "availableHandlerTypes", "javalin"})
@SourceDebugExtension(value={"SMAP\nMethodNotAllowedUtil.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MethodNotAllowedUtil.kt\nio/javalin/http/util/MethodNotAllowedUtil\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,23:1\n3792#2:24\n4307#2,2:25\n*S KotlinDebug\n*F\n+ 1 MethodNotAllowedUtil.kt\nio/javalin/http/util/MethodNotAllowedUtil\n*L\n17#1:24\n17#1:25,2\n*E\n"})
public final class MethodNotAllowedUtil {
    @NotNull
    public static final MethodNotAllowedUtil INSTANCE = new MethodNotAllowedUtil();

    private MethodNotAllowedUtil() {
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final List<HandlerType> findAvailableHttpHandlerTypes(@NotNull PathMatcher matcher, @NotNull String requestUri) {
        void $this$filterTo$iv$iv;
        Intrinsics.checkNotNullParameter(matcher, "matcher");
        Intrinsics.checkNotNullParameter(requestUri, "requestUri");
        HandlerType[] $this$filter$iv = HandlerType.values();
        boolean $i$f$filter = false;
        HandlerType[] handlerTypeArray = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        int n = ((void)$this$filterTo$iv$iv).length;
        for (int i = 0; i < n; ++i) {
            void element$iv$iv;
            void it = element$iv$iv = $this$filterTo$iv$iv[i];
            boolean bl = false;
            boolean bl2 = it.isHttpMethod() && !((Collection)matcher.findEntries((HandlerType)it, requestUri)).isEmpty();
            if (!bl2) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        return (List)destination$iv$iv;
    }

    @NotNull
    public final Map<String, String> getAvailableHandlerTypes(@NotNull Context ctx, @NotNull List<? extends HandlerType> availableHandlerTypes) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(availableHandlerTypes, "availableHandlerTypes");
        return MapsKt.mapOf(TuplesKt.to(JavalinServletContextKt.acceptsHtml(ctx) ? "Available methods" : "availableMethods", CollectionsKt.joinToString$default(availableHandlerTypes, ", ", null, null, 0, null, null, 62, null)));
    }
}

