/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.analytics;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.AnalyticsGroupsManager;
import com.incoresoft.middleware.analytics.AnalyticsManager;
import com.incoresoft.middleware.analytics.AnalyticsStatus;
import com.incoresoft.middleware.analytics.dto.analytics.validation.FirstStep;
import com.incoresoft.middleware.analytics.dto.objectinzone.module.PluginAnalyticsDTO;
import com.incoresoft.middleware.audit.Auditor;
import com.incoresoft.middleware.http.AppRole;
import com.incoresoft.middleware.model.analytics.module.AnalyticsModule;
import com.incoresoft.middleware.model.permission.Permission;
import com.incoresoft.middleware.model.role.RolesManager;
import com.incoresoft.middleware.model.stream.StreamsManager;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.model.user.UsersManager;
import com.incoresoft.middleware.plugin.VezhaExtension;
import com.incoresoft.plugin.commons.analytics.AnalyticsControllerV3;
import com.incoresoft.plugin.commons.analytics.dto.GroupsAndAnalyticsDTO;
import com.incoresoft.plugin.commons.analytics.dto.PluginAnalyticsWithGroupDTO;
import com.incoresoft.plugin.commons.analytics.fileprocessing.VideoFileProgressTracker;
import com.incoresoft.plugin.commons.analytics.model.AnalyticsImpl;
import com.incoresoft.plugin.commons.analytics.model.VezhaAnalyticsRestrictionsConverter;
import com.incoresoft.plugin.commons.audit.AuditEventActions;
import com.incoresoft.plugin.commons.audit.template.analytics.AnalyticsCreatedAuditMessage;
import com.incoresoft.plugin.commons.audit.template.analytics.AnalyticsUpdatedAuditMessage;
import com.incoresoft.plugin.commons.dto.ErrorDTO;
import com.incoresoft.plugin.commons.params.AnalyticsParams;
import com.incoresoft.plugin.commons.recursiveresponse.AnalyticsRecursiveResponseGenerator;
import com.incoresoft.plugin.commons.utils.AnalyticsUtils;
import com.incoresoft.plugin.commons.utils.QueryParamsParser;
import com.incoresoft.plugin.commons.validation.BeanValidator;
import com.incoresoft.plugin.commons.vms.analytics.model.VmsAnalyticsRestrictionsConverter;
import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.Optional;
import java.util.function.Predicate;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;

public class AnalyticsControllerV4
extends AnalyticsControllerV3 {
    private final AnalyticsGroupsManager analyticsGroupsManager;
    private final QueryParamsParser queryParamsParser;

    public AnalyticsControllerV4(UsersManager usersManager, StreamsManager streamsManager, AnalyticsManager analyticsManager, AnalyticsGroupsManager analyticsGroupsManager, VideoFileProgressTracker progressTracker, VezhaExtension pluginExtension, Auditor auditor, VezhaAnalyticsRestrictionsConverter analyticsRestrictionsConverter, RolesManager roleManager, BeanValidator validator, QueryParamsParser queryParamsParser) {
        super(usersManager, streamsManager, analyticsManager, progressTracker, pluginExtension, auditor, analyticsRestrictionsConverter, roleManager, validator);
        this.analyticsGroupsManager = analyticsGroupsManager;
        this.queryParamsParser = queryParamsParser;
    }

    public AnalyticsControllerV4(UsersManager usersManager, StreamsManager streamsManager, AnalyticsManager analyticsManager, AnalyticsGroupsManager analyticsGroupsManager, VideoFileProgressTracker progressTracker, Class<? extends AnalyticsModule> analyticsModuleClass, String pluginName, Permission viewAnalyticsPermission, Permission manageAnalyticsPermission, Auditor auditor, VmsAnalyticsRestrictionsConverter analyticsRestrictionsConverter, RolesManager roleManager, BeanValidator validator, QueryParamsParser queryParamsParser) {
        super(usersManager, streamsManager, analyticsManager, progressTracker, analyticsModuleClass, pluginName, viewAnalyticsPermission, manageAnalyticsPermission, auditor, analyticsRestrictionsConverter, roleManager, validator);
        this.analyticsGroupsManager = analyticsGroupsManager;
        this.queryParamsParser = queryParamsParser;
    }

    @Override
    public void init(Javalin app) {
        app.routes(() -> ApiBuilder.path("api/v2", () -> ApiBuilder.path(this.pluginName, () -> ApiBuilder.path("analytics", () -> {
            ApiBuilder.get(this::getVideoAnalyticsV2, AppRole.LOGGED_IN, AppRole.API_CALL, this.viewAnalyticsPermission);
            ApiBuilder.post(this::addVideoAnalytics, AppRole.LOGGED_IN, AppRole.API_CALL, this.manageAnalyticsPermission);
            ApiBuilder.post("validate", this::validateVideoAnalytic, AppRole.LOGGED_IN, AppRole.API_CALL, this.manageAnalyticsPermission);
            ApiBuilder.post("group_start", this::groupStartAnalytics, AppRole.LOGGED_IN, AppRole.API_CALL, this.manageAnalyticsPermission);
            ApiBuilder.post("group_stop", this::groupStopAnalytics, AppRole.LOGGED_IN, AppRole.API_CALL, this.manageAnalyticsPermission);
            ApiBuilder.path("{id}", () -> {
                ApiBuilder.get(this::getVideoAnalyticV2, AppRole.LOGGED_IN, AppRole.API_CALL, this.viewAnalyticsPermission);
                ApiBuilder.put(this::updateVideoAnalytics, AppRole.LOGGED_IN, AppRole.API_CALL, this.manageAnalyticsPermission);
                ApiBuilder.delete(this::deleteVideoAnalytic, AppRole.LOGGED_IN, AppRole.API_CALL, this.manageAnalyticsPermission);
                ApiBuilder.post("start", this::startVideoAnalytic, AppRole.LOGGED_IN, AppRole.API_CALL, this.manageAnalyticsPermission);
                ApiBuilder.post("stop", this::stopVideoAnalytic, AppRole.LOGGED_IN, AppRole.API_CALL, this.manageAnalyticsPermission);
            });
        }))));
    }

    @Override
    @GET
    @Operation(tags={"Analytics"}, summary="Get analytics", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.QUERY, name="matching_text", example="camera", description="Matching text for id and name fields of analytics", schema=@Schema(implementation=String.class)), @Parameter(in=ParameterIn.QUERY, name="status", example="started", description="Analytics status", schema=@Schema(implementation=AnalyticsStatus.class)), @Parameter(in=ParameterIn.QUERY, name="show_deleted", description="Add deleted analytics to request", example="true", schema=@Schema(implementation=Boolean.class))}, responses={@ApiResponse(responseCode="200", description="Successful operation", content={@Content(mediaType="application/json", schema=@Schema(implementation=GroupsAndAnalyticsDTO.class))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", array=@ArraySchema(arraySchema=@Schema(description="List of errors"), schema=@Schema(implementation=ErrorDTO.class)))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void getVideoAnalyticsV2(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        AnalyticsParams analyticsParams = this.queryParamsParser.from(ctx, AnalyticsParams.class);
        Predicate<Analytics> analyticsPredicate = AnalyticsUtils.getPredicate(analyticsParams, user, this.pluginName, this.analyticsManager);
        ctx.json(new AnalyticsRecursiveResponseGenerator(user, analyticsPredicate, this.pluginName, analyticsParams.isShowDeleted(), this.streamsManager, this.analyticsManager, this.analyticsGroupsManager, this.progressTracker, analyticsParams).getResponse());
    }

    @Override
    @POST
    @RequestBody(required=true, content={@Content(mediaType="application/json", schema=@Schema(implementation=PluginAnalyticsWithGroupDTO.class))})
    @ApiResponse(responseCode="200", description="Successful operation", content={@Content(mediaType="application/json", schema=@Schema(implementation=PluginAnalyticsDTO.class))})
    public void addVideoAnalytics(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        PluginAnalyticsWithGroupDTO dto = this.parseAnalytics(ctx);
        AnalyticsImpl analyticsImpl = new AnalyticsImpl(dto, this.pluginName, user, this.analyticsRestrictionsConverter);
        Analytics analytics = this.analyticsManager.add(analyticsImpl);
        this.auditor.audit(ctx, user, AuditEventActions.ANALYTICS_ADDED, this.pluginName, new AnalyticsCreatedAuditMessage(analytics));
        ctx.json(this.getAnalyticsDTO(user, analytics));
    }

    @Override
    @PUT
    @RequestBody(required=true, content={@Content(mediaType="application/json", schema=@Schema(implementation=PluginAnalyticsWithGroupDTO.class))})
    @ApiResponse(responseCode="200", description="Successful operation", content={@Content(mediaType="application/json", schema=@Schema(implementation=PluginAnalyticsDTO.class))})
    public void updateVideoAnalytics(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        PluginAnalyticsWithGroupDTO dto = this.parseAnalytics(ctx);
        Integer id = ctx.pathParamAsClass("id", Integer.TYPE).getOrThrow(e -> new BadRequestResponse("id"));
        dto.setId(id);
        Analytics analyticsById = Optional.ofNullable((Analytics)this.analyticsManager.get(dto.getId())).filter(it -> it.getPluginName().equalsIgnoreCase(this.pluginName)).orElseThrow(NotFoundResponse::new);
        AnalyticsImpl analyticsImpl = new AnalyticsImpl(analyticsById, dto, this.pluginName, user, this.analyticsRestrictionsConverter);
        Analytics analytics = this.analyticsManager.update(analyticsImpl);
        this.auditor.audit(ctx, user, AuditEventActions.ANALYTICS_UPDATED, this.pluginName, new AnalyticsUpdatedAuditMessage(analytics).build());
        ctx.json(this.getAnalyticsDTO(user, analytics));
    }

    protected PluginAnalyticsWithGroupDTO parseAnalytics(Context ctx) {
        JSONObject body2 = JSON.parseObject(ctx.body());
        PluginAnalyticsWithGroupDTO dto = JSON.toJavaObject(body2, PluginAnalyticsWithGroupDTO.class);
        dto.update((AnalyticsModule)JSON.parseObject(body2.getJSONObject("module").toJSONString(), this.analyticsModuleClass), this.streamsManager);
        this.validator.validateObjectOrThrow(dto, FirstStep.class);
        this.validator.validateObjectOrThrow(dto, new Class[0]);
        return dto;
    }
}

