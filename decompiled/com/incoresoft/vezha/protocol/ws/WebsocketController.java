/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.protocol.ws;

import com.google.inject.Inject;
import com.incoresoft.middleware.http.AppRole;
import com.incoresoft.vezha.protocol.ws.AnalyticsWSBroadcaster;
import com.incoresoft.vezha.protocol.ws.NotificationsWSBroadcaster;
import com.incoresoft.vezha.protocol.ws.ServersWSBroadcaster;
import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebsocketController {
    private static final Logger log = LoggerFactory.getLogger(WebsocketController.class);
    private final AnalyticsWSBroadcaster analyticsStatusesWSBroadcaster;
    private final NotificationsWSBroadcaster notificationsWSBroadcaster;
    private final ServersWSBroadcaster serversWSBroadcaster;

    @Inject
    public void init(Javalin app) {
        app.routes(() -> ApiBuilder.path("/api/v2/ws", () -> {
            ApiBuilder.ws("analytics", this.analyticsStatusesWSBroadcaster::init, AppRole.LOGGED_IN, AppRole.API_CALL);
            ApiBuilder.ws("notifications", this.notificationsWSBroadcaster::init, AppRole.LOGGED_IN, AppRole.API_CALL);
            ApiBuilder.ws("servers", this.serversWSBroadcaster::init, AppRole.LOGGED_IN, AppRole.API_CALL);
        }));
    }

    @Inject
    public WebsocketController(AnalyticsWSBroadcaster analyticsStatusesWSBroadcaster, NotificationsWSBroadcaster notificationsWSBroadcaster, ServersWSBroadcaster serversWSBroadcaster) {
        this.analyticsStatusesWSBroadcaster = analyticsStatusesWSBroadcaster;
        this.notificationsWSBroadcaster = notificationsWSBroadcaster;
        this.serversWSBroadcaster = serversWSBroadcaster;
    }
}

