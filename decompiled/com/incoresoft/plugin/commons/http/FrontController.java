/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.incoresoft.middleware.http.AppRole;
import com.incoresoft.plugin.commons.http.websocket.NotificationsWS;
import com.incoresoft.plugin.commons.http.websocket.ViewAnalyticWS;
import com.incoresoft.plugin.commons.http.websocket.WSBroadcaster;
import com.incoresoft.plugin.commons.openapi.OpenApiHandler;
import com.incoresoft.plugin.commons.utils.ContentTypeUtils;
import io.javalin.Javalin;
import io.javalin.http.NotFoundResponse;
import io.javalin.openapi.plugin.redoc.ReDocHandler;
import io.javalin.openapi.plugin.swagger.SwaggerHandler;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

public class FrontController {
    private static final Map<String, String> cacheControl = Map.of("Cache-Control", "public, max-age=3600");
    private static final Set<String> filesExcludedFromCaching = Set.of("app.js");
    private final Javalin javalin;
    private final ViewAnalyticWS viewAnalyticWS;
    private final NotificationsWS notificationsWS;
    private final WSBroadcaster analyticsBroadcaster;
    private final WSBroadcaster notificationsBroadcaster;
    private final String pluginName;
    private final String iconPath;

    @Deprecated
    public FrontController(@NotNull Javalin javalin, @NotNull ViewAnalyticWS viewAnalyticWS, @NotNull NotificationsWS notificationsWS, @NotNull String pluginName, @NotNull String iconPath) {
        this.javalin = Objects.requireNonNull(javalin);
        this.viewAnalyticWS = Objects.requireNonNull(viewAnalyticWS);
        this.notificationsWS = Objects.requireNonNull(notificationsWS);
        this.analyticsBroadcaster = null;
        this.notificationsBroadcaster = null;
        this.pluginName = Objects.requireNonNull(pluginName);
        this.iconPath = Objects.requireNonNull(iconPath);
        this.init();
    }

    public FrontController(@NotNull Javalin javalin, @NotNull WSBroadcaster analyticsBroadcaster, @NotNull WSBroadcaster notificationsBroadcaster, @NotNull String pluginName, @NotNull String iconPath) {
        this.javalin = Objects.requireNonNull(javalin);
        this.viewAnalyticWS = null;
        this.notificationsWS = null;
        this.analyticsBroadcaster = Objects.requireNonNull(analyticsBroadcaster);
        this.notificationsBroadcaster = Objects.requireNonNull(notificationsBroadcaster);
        this.pluginName = Objects.requireNonNull(pluginName);
        this.iconPath = Objects.requireNonNull(iconPath);
        this.init();
    }

    public void init() {
        this.javalin.get("resources/" + this.pluginName + "/icon", ctx -> {
            ctx.contentType(ContentTypeUtils.guessContentType(this.iconPath));
            InputStream resource = this.getClass().getClassLoader().getResourceAsStream(this.iconPath);
            if (resource == null) {
                throw new NotFoundResponse();
            }
            ctx.result(resource);
        }, AppRole.ANYONE);
        this.javalin.get("resources/" + this.pluginName + "/*", ctx -> {
            InputStream resource;
            String path = ctx.path().replaceFirst("/resources/" + this.pluginName + "/", "");
            ctx.contentType(ContentTypeUtils.guessContentType(path));
            if (this.fileMustBeCached(path)) {
                cacheControl.forEach(ctx::header);
            }
            if ((resource = this.getClass().getClassLoader().getResourceAsStream("public/" + path)) == null) {
                throw new NotFoundResponse();
            }
            ctx.result(resource);
        }, AppRole.ANYONE);
        if (this.viewAnalyticWS != null) {
            this.javalin.ws("analytic/" + this.pluginName, wsHandler -> wsHandler.onMessage(ctx -> {
                JSONObject message = JSON.parseObject(ctx.message());
                int vaId = message.getIntValue("va_id");
                if (message.getString("method").equals("subscribe") && vaId != 0) {
                    this.viewAnalyticWS.subscribe(ctx, vaId);
                }
            }), AppRole.LOGGED_IN);
        }
        if (this.notificationsWS != null) {
            this.javalin.ws("notification/" + this.pluginName, wsHandler -> {
                wsHandler.onConnect(this.notificationsWS::subscribe);
                wsHandler.onClose(this.notificationsWS::unsubscribe);
                wsHandler.onError(this.notificationsWS::unsubscribe);
            }, AppRole.LOGGED_IN);
        }
        if (this.analyticsBroadcaster != null) {
            this.javalin.ws("/api/v2/ws/" + this.pluginName + "/analytics/{analytics_id}", this.analyticsBroadcaster::init, AppRole.LOGGED_IN, AppRole.API_CALL);
        }
        if (this.notificationsBroadcaster != null) {
            this.javalin.ws("/api/v2/ws/" + this.pluginName + "/notifications", this.notificationsBroadcaster::init, AppRole.LOGGED_IN, AppRole.API_CALL);
        }
        this.javalin.get("openapi/" + this.pluginName, new OpenApiHandler(this.getClass().getPackage().getName(), this.getClass().getClassLoader()), AppRole.ANYONE);
        this.javalin.get("swagger/" + this.pluginName, new SwaggerHandler(this.pluginName, "/openapi/" + this.pluginName, "5.9.0", null, ""), AppRole.ANYONE);
        this.javalin.get("redoc/" + this.pluginName, new ReDocHandler(this.pluginName, "/openapi/" + this.pluginName, "2.0.0", "", ""), AppRole.ANYONE);
    }

    private boolean fileMustBeCached(String path) {
        return !filesExcludedFromCaching.contains(path);
    }
}

