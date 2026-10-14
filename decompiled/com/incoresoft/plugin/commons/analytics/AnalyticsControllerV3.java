/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.analytics;

import com.incoresoft.middleware.analytics.AnalyticsManager;
import com.incoresoft.middleware.audit.Auditor;
import com.incoresoft.middleware.http.AppRole;
import com.incoresoft.middleware.model.analytics.module.AnalyticsModule;
import com.incoresoft.middleware.model.permission.Permission;
import com.incoresoft.middleware.model.role.RolesManager;
import com.incoresoft.middleware.model.stream.StreamsManager;
import com.incoresoft.middleware.model.user.UsersManager;
import com.incoresoft.middleware.plugin.VezhaExtension;
import com.incoresoft.plugin.commons.analytics.AnalyticsControllerV2;
import com.incoresoft.plugin.commons.analytics.fileprocessing.VideoFileProgressTracker;
import com.incoresoft.plugin.commons.analytics.model.VezhaAnalyticsRestrictionsConverter;
import com.incoresoft.plugin.commons.validation.BeanValidator;
import com.incoresoft.plugin.commons.vms.analytics.model.VmsAnalyticsRestrictionsConverter;
import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;
import io.javalin.http.Context;
import io.swagger.v3.oas.annotations.Parameter;
import javax.ws.rs.POST;

public class AnalyticsControllerV3
extends AnalyticsControllerV2 {
    public AnalyticsControllerV3(UsersManager usersManager, StreamsManager streamsManager, AnalyticsManager analyticsManager, VideoFileProgressTracker progressTracker, VezhaExtension pluginExtension, Auditor auditor, VezhaAnalyticsRestrictionsConverter analyticsRestrictionsConverter, RolesManager roleManager, BeanValidator validator) {
        super(usersManager, streamsManager, analyticsManager, progressTracker, pluginExtension, auditor, analyticsRestrictionsConverter, roleManager, validator);
    }

    public AnalyticsControllerV3(UsersManager usersManager, StreamsManager streamsManager, AnalyticsManager analyticsManager, VideoFileProgressTracker progressTracker, Class<? extends AnalyticsModule> analyticsModuleClass, String pluginName, Permission viewAnalyticsPermission, Permission manageAnalyticsPermission, Auditor auditor, VmsAnalyticsRestrictionsConverter analyticsRestrictionsConverter, RolesManager roleManager, BeanValidator validator) {
        super(usersManager, streamsManager, analyticsManager, progressTracker, analyticsModuleClass, pluginName, viewAnalyticsPermission, manageAnalyticsPermission, auditor, analyticsRestrictionsConverter, roleManager, validator);
    }

    @Override
    public void init(Javalin app) {
        app.routes(() -> ApiBuilder.path("api/v2", () -> ApiBuilder.path(this.pluginName, () -> ApiBuilder.path("analytics", () -> {
            ApiBuilder.get(this::getVideoAnalyticsV2, AppRole.LOGGED_IN, AppRole.API_CALL);
            ApiBuilder.post(this::addVideoAnalytics, AppRole.LOGGED_IN, AppRole.API_CALL, this.manageAnalyticsPermission);
            ApiBuilder.post("validate", this::validateVideoAnalytic, AppRole.LOGGED_IN, AppRole.API_CALL, this.manageAnalyticsPermission);
            ApiBuilder.post("group_start", this::groupStartAnalytics, AppRole.LOGGED_IN, AppRole.API_CALL);
            ApiBuilder.post("group_stop", this::groupStopAnalytics, AppRole.LOGGED_IN, AppRole.API_CALL);
            ApiBuilder.path("{id}", () -> {
                ApiBuilder.get(this::getVideoAnalyticV2, AppRole.LOGGED_IN, AppRole.API_CALL);
                ApiBuilder.put(this::updateVideoAnalytics, AppRole.LOGGED_IN, AppRole.API_CALL, this.manageAnalyticsPermission);
                ApiBuilder.delete(this::deleteVideoAnalytic, AppRole.LOGGED_IN, AppRole.API_CALL, this.manageAnalyticsPermission);
                ApiBuilder.post("start", this::startVideoAnalytic, AppRole.LOGGED_IN, AppRole.API_CALL, this.manageAnalyticsPermission);
                ApiBuilder.post("stop", this::stopVideoAnalytic, AppRole.LOGGED_IN, AppRole.API_CALL, this.manageAnalyticsPermission);
            });
        }))));
    }

    @Override
    @POST
    public void startVideoAnalytic(@Parameter(hidden=true) Context ctx) {
        super.startVideoAnalytic(ctx);
    }

    @Override
    @POST
    public void stopVideoAnalytic(@Parameter(hidden=true) Context ctx) {
        super.stopVideoAnalytic(ctx);
    }
}

