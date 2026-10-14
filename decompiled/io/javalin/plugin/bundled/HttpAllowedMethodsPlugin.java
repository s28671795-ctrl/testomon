/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.plugin.bundled;

import io.javalin.Javalin;
import io.javalin.event.EventListener;
import io.javalin.event.HandlerMetaInfo;
import io.javalin.http.Context;
import io.javalin.plugin.Plugin;
import io.javalin.plugin.PluginLifecycleInit;
import io.javalin.plugin.bundled.HttpAllowedMethodsPlugin;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\bH\u0002J\u0010\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0010\u0010\u0010\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000eH\u0016R \u0010\u0004\u001a\u0014\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2={"Lio/javalin/plugin/bundled/HttpAllowedMethodsPlugin;", "Lio/javalin/plugin/Plugin;", "Lio/javalin/plugin/PluginLifecycleInit;", "()V", "endpoints", "", "", "", "Lio/javalin/event/HandlerMetaInfo;", "addOptionsToList", "", "handlerMetaInfo", "apply", "app", "Lio/javalin/Javalin;", "createOptionsEndPoint", "init", "javalin"})
@SourceDebugExtension(value={"SMAP\nHttpAllowedMethodsPlugin.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HttpAllowedMethodsPlugin.kt\nio/javalin/plugin/bundled/HttpAllowedMethodsPlugin\n+ 2 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n+ 3 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,52:1\n361#2,7:53\n215#3,2:60\n*S KotlinDebug\n*F\n+ 1 HttpAllowedMethodsPlugin.kt\nio/javalin/plugin/bundled/HttpAllowedMethodsPlugin\n*L\n36#1:53,7\n41#1:60,2\n*E\n"})
public final class HttpAllowedMethodsPlugin
implements Plugin,
PluginLifecycleInit {
    @NotNull
    private final Map<String, Set<HandlerMetaInfo>> endpoints = new LinkedHashMap();

    @Override
    public void init(@NotNull Javalin app) {
        Intrinsics.checkNotNullParameter(app, "app");
        app.events(arg_0 -> HttpAllowedMethodsPlugin.init$lambda$1(this, arg_0));
    }

    @Override
    public void apply(@NotNull Javalin app) {
        Intrinsics.checkNotNullParameter(app, "app");
        app.events(arg_0 -> HttpAllowedMethodsPlugin.apply$lambda$3(this, app, arg_0));
    }

    /*
     * WARNING - void declaration
     */
    private final void addOptionsToList(HandlerMetaInfo handlerMetaInfo) {
        Object object;
        void $this$getOrPut$iv;
        Map<String, Set<HandlerMetaInfo>> map = this.endpoints;
        String key$iv = handlerMetaInfo.getPath();
        boolean $i$f$getOrPut = false;
        Object value$iv = $this$getOrPut$iv.get(key$iv);
        if (value$iv == null) {
            boolean bl = false;
            HandlerMetaInfo[] handlerMetaInfoArray = new HandlerMetaInfo[]{handlerMetaInfo};
            Set<HandlerMetaInfo> answer$iv = SetsKt.mutableSetOf(handlerMetaInfoArray);
            $this$getOrPut$iv.put(key$iv, answer$iv);
            object = answer$iv;
        } else {
            object = value$iv;
        }
        Set endpoint = (Set)object;
        endpoint.add(handlerMetaInfo);
    }

    private final void createOptionsEndPoint(Javalin app) {
        Map<String, Set<HandlerMetaInfo>> $this$forEach$iv = this.endpoints;
        boolean $i$f$forEach = false;
        Iterator<Map.Entry<String, Set<HandlerMetaInfo>>> iterator2 = $this$forEach$iv.entrySet().iterator();
        while (iterator2.hasNext()) {
            Map.Entry<String, Set<HandlerMetaInfo>> element$iv;
            Map.Entry<String, Set<HandlerMetaInfo>> endpoint = element$iv = iterator2.next();
            boolean bl = false;
            app.options(endpoint.getKey(), arg_0 -> HttpAllowedMethodsPlugin.createOptionsEndPoint$lambda$6$lambda$5(endpoint, arg_0));
        }
    }

    private static final void init$lambda$1$lambda$0(HttpAllowedMethodsPlugin this$0, HandlerMetaInfo handlerInfo) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullExpressionValue(handlerInfo, "handlerInfo");
        this$0.addOptionsToList(handlerInfo);
    }

    private static final void init$lambda$1(HttpAllowedMethodsPlugin this$0, EventListener it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        it.handlerAdded(arg_0 -> HttpAllowedMethodsPlugin.init$lambda$1$lambda$0(this$0, arg_0));
    }

    private static final void apply$lambda$3$lambda$2(HttpAllowedMethodsPlugin this$0, Javalin $app) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter($app, "$app");
        this$0.createOptionsEndPoint($app);
    }

    private static final void apply$lambda$3(HttpAllowedMethodsPlugin this$0, Javalin $app, EventListener it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter($app, "$app");
        it.serverStarted(() -> HttpAllowedMethodsPlugin.apply$lambda$3$lambda$2(this$0, $app));
    }

    private static final void createOptionsEndPoint$lambda$6$lambda$5(Map.Entry $endpoint, Context ctx) {
        Intrinsics.checkNotNullParameter($endpoint, "$endpoint");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        ctx.header("Access-Control-Allow-Methods", CollectionsKt.joinToString$default((Iterable)$endpoint.getValue(), ",", null, null, 0, null, createOptionsEndPoint.1.1.1.INSTANCE, 30, null));
    }
}

