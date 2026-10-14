/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.plugin.bundled;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.plugin.Plugin;
import io.javalin.plugin.bundled.GlobalHeaderConfig;
import java.util.Iterator;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2={"Lio/javalin/plugin/bundled/GlobalHeadersPlugin;", "Lio/javalin/plugin/Plugin;", "globalHeaderConfig", "Lio/javalin/plugin/bundled/GlobalHeaderConfig;", "(Lio/javalin/plugin/bundled/GlobalHeaderConfig;)V", "apply", "", "app", "Lio/javalin/Javalin;", "javalin"})
@SourceDebugExtension(value={"SMAP\nGlobalHeadersPlugin.kt\nKotlin\n*S Kotlin\n*F\n+ 1 GlobalHeadersPlugin.kt\nio/javalin/plugin/bundled/GlobalHeadersPlugin\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,23:1\n215#2,2:24\n*S KotlinDebug\n*F\n+ 1 GlobalHeadersPlugin.kt\nio/javalin/plugin/bundled/GlobalHeadersPlugin\n*L\n16#1:24,2\n*E\n"})
public final class GlobalHeadersPlugin
implements Plugin {
    @NotNull
    private final GlobalHeaderConfig globalHeaderConfig;

    public GlobalHeadersPlugin(@NotNull GlobalHeaderConfig globalHeaderConfig) {
        Intrinsics.checkNotNullParameter(globalHeaderConfig, "globalHeaderConfig");
        this.globalHeaderConfig = globalHeaderConfig;
    }

    @Override
    public void apply(@NotNull Javalin app) {
        Intrinsics.checkNotNullParameter(app, "app");
        app.before(arg_0 -> GlobalHeadersPlugin.apply$lambda$1(this, arg_0));
    }

    private static final void apply$lambda$1(GlobalHeadersPlugin this$0, Context ctx) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Map<String, String> $this$forEach$iv = this$0.globalHeaderConfig.getHeaders();
        boolean $i$f$forEach = false;
        Iterator<Map.Entry<String, String>> iterator2 = $this$forEach$iv.entrySet().iterator();
        while (iterator2.hasNext()) {
            Map.Entry<String, String> element$iv;
            Map.Entry<String, String> entry = element$iv = iterator2.next();
            boolean bl = false;
            String name = entry.getKey();
            String value = entry.getValue();
            ctx.header(name, value);
        }
    }
}

