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
import io.javalin.plugin.bundled.DevLoggerPluginKt;
import io.javalin.routing.PathMatcher;
import io.javalin.util.JavalinLogger;
import io.javalin.websocket.WsConfig;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0010\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t\u00a8\u0006\u000f"}, d2={"Lio/javalin/plugin/bundled/DevLoggingPlugin;", "Lio/javalin/plugin/Plugin;", "Lio/javalin/plugin/PluginLifecycleInit;", "()V", "matcher", "Lio/javalin/routing/PathMatcher;", "getMatcher", "()Lio/javalin/routing/PathMatcher;", "setMatcher", "(Lio/javalin/routing/PathMatcher;)V", "apply", "", "app", "Lio/javalin/Javalin;", "init", "javalin"})
public final class DevLoggingPlugin
implements Plugin,
PluginLifecycleInit {
    public PathMatcher matcher;

    @NotNull
    public final PathMatcher getMatcher() {
        PathMatcher pathMatcher = this.matcher;
        if (pathMatcher != null) {
            return pathMatcher;
        }
        Intrinsics.throwUninitializedPropertyAccessException("matcher");
        return null;
    }

    public final void setMatcher(@NotNull PathMatcher pathMatcher) {
        Intrinsics.checkNotNullParameter(pathMatcher, "<set-?>");
        this.matcher = pathMatcher;
    }

    @Override
    public void apply(@NotNull Javalin app) {
        Intrinsics.checkNotNullParameter(app, "app");
        app.cfg.requestLogger.http((arg_0, arg_1) -> DevLoggingPlugin.apply$lambda$0(this, arg_0, arg_1));
        app.cfg.requestLogger.ws(DevLoggingPlugin::apply$lambda$1);
        this.setMatcher(app.javalinServlet().getMatcher());
    }

    @Override
    public void init(@NotNull Javalin app) {
        Intrinsics.checkNotNullParameter(app, "app");
        app.events(DevLoggingPlugin::init$lambda$3);
    }

    private static final void apply$lambda$0(DevLoggingPlugin this$0, Context ctx, float ms) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        DevLoggerPluginKt.requestDevLogger(this$0.getMatcher(), ctx, ms);
    }

    private static final void apply$lambda$1(WsConfig it) {
        Intrinsics.checkNotNullParameter(it, "it");
        DevLoggerPluginKt.wsDevLogger(it);
    }

    private static final void init$lambda$3$lambda$2(HandlerMetaInfo handlerMetaInfo) {
        JavalinLogger.info$default("JAVALIN HANDLER REGISTRATION DEBUG LOG: " + handlerMetaInfo.getHttpMethod() + "[" + handlerMetaInfo.getPath() + "]", null, 2, null);
    }

    private static final void init$lambda$3(EventListener on) {
        on.handlerAdded(DevLoggingPlugin::init$lambda$3$lambda$2);
    }
}

