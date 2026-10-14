/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.plugin.bundled;

import io.javalin.Javalin;
import io.javalin.event.EventListener;
import io.javalin.event.HandlerMetaInfo;
import io.javalin.event.WsHandlerMetaInfo;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.plugin.bundled.RouteOverviewUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2={"Lio/javalin/plugin/bundled/RouteOverviewRenderer;", "Lio/javalin/http/Handler;", "app", "Lio/javalin/Javalin;", "(Lio/javalin/Javalin;)V", "getApp", "()Lio/javalin/Javalin;", "handlerMetaInfoList", "", "Lio/javalin/event/HandlerMetaInfo;", "wsHandlerMetaInfoList", "Lio/javalin/event/WsHandlerMetaInfo;", "handle", "", "ctx", "Lio/javalin/http/Context;", "javalin"})
public final class RouteOverviewRenderer
implements Handler {
    @NotNull
    private final Javalin app;
    @NotNull
    private final List<HandlerMetaInfo> handlerMetaInfoList;
    @NotNull
    private final List<WsHandlerMetaInfo> wsHandlerMetaInfoList;

    public RouteOverviewRenderer(@NotNull Javalin app) {
        Intrinsics.checkNotNullParameter(app, "app");
        this.app = app;
        this.handlerMetaInfoList = new ArrayList();
        this.wsHandlerMetaInfoList = new ArrayList();
        this.app.events(arg_0 -> RouteOverviewRenderer._init_$lambda$1(this, arg_0));
        this.app.events(arg_0 -> RouteOverviewRenderer._init_$lambda$3(this, arg_0));
    }

    @NotNull
    public final Javalin getApp() {
        return this.app;
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public void handle(@NotNull Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        v0 = ctx.header("Accept");
        if (v0 == null) ** GOTO lbl-1000
        var2_2 = v0;
        v1 = Locale.ROOT;
        Intrinsics.checkNotNullExpressionValue(v1, "ROOT");
        v2 = var2_2.toLowerCase(v1);
        Intrinsics.checkNotNullExpressionValue(v2, "this as java.lang.String).toLowerCase(locale)");
        v0 = v2;
        if (v2 != null) {
            v3 = StringsKt.contains$default((CharSequence)v0, "application/json", false, 2, null);
        } else lbl-1000:
        // 2 sources

        {
            v3 = false;
        }
        if (v3) {
            ctx.header("Content-Type", "application/json");
            ctx.result(RouteOverviewUtil.createJsonOverview(this.handlerMetaInfoList, this.wsHandlerMetaInfoList));
        } else {
            ctx.html(RouteOverviewUtil.createHtmlOverview(this.handlerMetaInfoList, this.wsHandlerMetaInfoList));
        }
    }

    private static final void lambda$1$lambda$0(RouteOverviewRenderer this$0, HandlerMetaInfo handlerInfo) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        List<HandlerMetaInfo> list = this$0.handlerMetaInfoList;
        Intrinsics.checkNotNullExpressionValue(handlerInfo, "handlerInfo");
        list.add(handlerInfo);
    }

    private static final void _init_$lambda$1(RouteOverviewRenderer this$0, EventListener it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        it.handlerAdded(arg_0 -> RouteOverviewRenderer.lambda$1$lambda$0(this$0, arg_0));
    }

    private static final void lambda$3$lambda$2(RouteOverviewRenderer this$0, WsHandlerMetaInfo handlerInfo) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        List<WsHandlerMetaInfo> list = this$0.wsHandlerMetaInfoList;
        Intrinsics.checkNotNullExpressionValue(handlerInfo, "handlerInfo");
        list.add(handlerInfo);
    }

    private static final void _init_$lambda$3(RouteOverviewRenderer this$0, EventListener it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        it.wsHandlerAdded(arg_0 -> RouteOverviewRenderer.lambda$3$lambda$2(this$0, arg_0));
    }
}

