/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.incoresoft.vms.drivers.sdk.DeviceManager
 *  com.incoresoft.vms.drivers.sdk.exceptions.items.DeviceItemsDisabledException
 *  com.incoresoft.vms.drivers.sdk.exceptions.items.DeviceItemsInactiveException
 *  com.incoresoft.vms.drivers.sdk.item.DeviceItemSettings
 */
package com.incoresoft.plugin.commons.vms.plugins;

import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.AnalyticsManager;
import com.incoresoft.middleware.http.AppRole;
import com.incoresoft.middleware.http.serialization.Serialization;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.model.user.UsersManager;
import com.incoresoft.middleware.vms.vezhaintegration.VezhaIntegrationManager;
import com.incoresoft.plugin.commons.vms.plugins.ProxyController;
import com.incoresoft.plugin.commons.vms.plugins.proxy.ForwardingRule;
import com.incoresoft.vms.drivers.sdk.DeviceManager;
import com.incoresoft.vms.drivers.sdk.exceptions.items.DeviceItemsDisabledException;
import com.incoresoft.vms.drivers.sdk.exceptions.items.DeviceItemsInactiveException;
import com.incoresoft.vms.drivers.sdk.item.DeviceItemSettings;
import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.HandlerType;
import io.javalin.http.NotFoundResponse;
import java.io.ByteArrayInputStream;
import java.net.http.HttpClient;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import okhttp3.OkHttpClient;

public class VezhaIntegrationProxyController
extends ProxyController {
    private final AnalyticsManager analyticsManager;
    private final DeviceManager deviceManager;
    private final ForwardingRule groupStartAnalyticsRule;
    private final ForwardingRule startVideoAnalyticsRule;

    public VezhaIntegrationProxyController(UsersManager usersManager, VezhaIntegrationManager vezhaIntegrationManager, HttpClient httpClient, OkHttpClient okHttpClient, DeviceManager deviceManager, AnalyticsManager analyticsManager, String pluginName, List<ForwardingRule> httpForwardingRules, Map<String, String> wsRoutes, Map<String, String> sseRoutes) {
        super(usersManager, vezhaIntegrationManager, httpClient, okHttpClient, pluginName, httpForwardingRules, wsRoutes, sseRoutes);
        this.analyticsManager = analyticsManager;
        this.deviceManager = deviceManager;
        this.groupStartAnalyticsRule = new ForwardingRule(String.format("/api/v2/%s/analytics/group_start", pluginName), String.format("/api/v2/%s/analytics/group_start", pluginName), Set.of(HandlerType.POST));
        this.startVideoAnalyticsRule = new ForwardingRule(String.format("/api/v2/%s/analytics/{id}/start", pluginName), String.format("/api/v2/%s/analytics/{id}/start", pluginName), Set.of(HandlerType.POST));
    }

    @Override
    public void init(Javalin app) {
        app.routes(() -> {
            ApiBuilder.post(this.startVideoAnalyticsRule.matchedPath(), this::startVideoAnalytic, AppRole.LOGGED_IN, AppRole.API_CALL);
            ApiBuilder.post(this.groupStartAnalyticsRule.matchedPath(), this::groupStartAnalytics, AppRole.LOGGED_IN, AppRole.API_CALL);
        });
        super.init(app);
    }

    private void startVideoAnalytic(Context ctx) {
        User user = this.usersManager.getUser(ctx);
        int analyticsId = ctx.pathParamAsClass("id", Integer.class).getOrThrow(e -> new BadRequestResponse("id"));
        Analytics analytics = Optional.ofNullable((Analytics)this.analyticsManager.get(analyticsId)).filter(user::hasAccessTo).orElseThrow(NotFoundResponse::new);
        DeviceItemSettings camera = (DeviceItemSettings)this.deviceManager.getCameraByStreamUuid(analytics.getStreamUUID()).orElseThrow(() -> new RuntimeException(String.format("Camera by stream UUID %s not found", analytics.getStreamUUID())));
        if (!camera.enabled()) {
            throw new DeviceItemsDisabledException(Set.of(Integer.valueOf(camera.getId())));
        }
        if (!camera.isActive()) {
            throw new DeviceItemsInactiveException(Set.of(Integer.valueOf(camera.getId())));
        }
        this.proxyForwardingRestHandler(ctx, this.startVideoAnalyticsRule);
    }

    private void groupStartAnalytics(Context ctx) {
        String rawRequestBody = ctx.body();
        Set<Integer> analyticsToStart = Set.copyOf(Serialization.readAsList(rawRequestBody, Integer.class));
        Collection<Analytics> analytics = this.analyticsManager.getAll(it -> analyticsToStart.contains(it.getId()));
        Set streamUuids = analytics.stream().map(Analytics::getStreamUUID).collect(Collectors.toSet());
        List cameras = this.deviceManager.getCamerasByStreamUuids(streamUuids);
        Set inactiveCameraIds = cameras.stream().filter(it -> !it.isActive()).map(DeviceItemSettings::id).collect(Collectors.toSet());
        if (!inactiveCameraIds.isEmpty()) {
            throw new DeviceItemsInactiveException(inactiveCameraIds);
        }
        Set disabledCameraIds = cameras.stream().filter(it -> !it.enabled()).map(DeviceItemSettings::id).collect(Collectors.toSet());
        if (!disabledCameraIds.isEmpty()) {
            throw new DeviceItemsDisabledException(disabledCameraIds);
        }
        this.proxyForwardingRestHandler(ctx, new ByteArrayInputStream(rawRequestBody.getBytes()), this.groupStartAnalyticsRule);
    }
}

