/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.protocol.controllers.api.v2.analytics;

import com.alibaba.fastjson.JSON;
import com.google.inject.Inject;
import com.incoresoft.Utils.ApiParams;
import com.incoresoft.extensions.ExtensionsManager;
import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.AnalyticsGroupsManager;
import com.incoresoft.middleware.analytics.AnalyticsManager;
import com.incoresoft.middleware.analytics.AnalyticsStatus;
import com.incoresoft.middleware.analytics.AnalyticsTypes;
import com.incoresoft.middleware.analytics.dto.analytics.AnalyticsDTO;
import com.incoresoft.middleware.audit.Auditor;
import com.incoresoft.middleware.http.AppRole;
import com.incoresoft.middleware.model.clients.ClientId;
import com.incoresoft.middleware.model.permission.CorePermissions;
import com.incoresoft.middleware.model.stream.StreamsManager;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.model.user.UsersManager;
import com.incoresoft.middleware.plugin.SystemExtension;
import com.incoresoft.middleware.plugin.VezhaExtension;
import com.incoresoft.plugin.commons.analytics.dto.GroupsAndAnalyticsDTO;
import com.incoresoft.plugin.commons.analytics.fileprocessing.VideoFileProgressTracker;
import com.incoresoft.plugin.commons.audit.AuditEventActions;
import com.incoresoft.plugin.commons.audit.template.analytics.AnalyticsDeletedAuditMessage;
import com.incoresoft.plugin.commons.audit.template.analytics.AnalyticsStartedAuditMessage;
import com.incoresoft.plugin.commons.audit.template.analytics.AnalyticsStoppedAuditMessage;
import com.incoresoft.plugin.commons.http.answers.ErrorAnswer;
import com.incoresoft.plugin.commons.recursiveresponse.AnalyticsRecursiveResponseGenerator;
import com.incoresoft.vezha.dto.analytics.AnalyticsIdListDTO;
import com.incoresoft.vezha.dto.analytics.AnalyticsWithGroupsParams;
import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;
import io.javalin.http.Context;
import io.javalin.http.ForbiddenResponse;
import io.javalin.http.NotFoundResponse;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@OpenAPIDefinition(info=@Info(title="Middleware-Core", description="Middleware-Core HTTP API", version="v2"))
public class ApiAnalyticsControllerV2 {
    private static final String API_PATH = "api/v2/analytics";
    private final UsersManager usersManager;
    private final StreamsManager streamsManager;
    private final AnalyticsManager analyticsManager;
    private final AnalyticsGroupsManager analyticsGroupsManager;
    private final VideoFileProgressTracker progressTracker;
    private final ExtensionsManager extensionsManager;
    private final Auditor auditor;

    @Inject
    public void init(Javalin app) {
        app.routes(() -> ApiBuilder.path(API_PATH, () -> {
            ApiBuilder.get(this::getAllVideoAnalytics, AppRole.LOGGED_IN, AppRole.API_CALL);
            ApiBuilder.get("with_groups", this::getAllVideoAnalyticsWithGroups, AppRole.LOGGED_IN, AppRole.API_CALL);
            ApiBuilder.post("group_start", this::groupStartAnalytics, AppRole.LOGGED_IN, AppRole.API_CALL);
            ApiBuilder.post("group_stop", this::groupStopAnalytics, AppRole.LOGGED_IN, AppRole.API_CALL);
            ApiBuilder.path("{id}", () -> {
                ApiBuilder.get(this::getVideoAnalytics, AppRole.LOGGED_IN, AppRole.API_CALL);
                ApiBuilder.delete(this::deleteVideoAnalytics, AppRole.LOGGED_IN, AppRole.API_CALL);
                ApiBuilder.post("start", this::startVideoAnalytics, AppRole.LOGGED_IN, AppRole.API_CALL);
                ApiBuilder.post("stop", this::stopVideoAnalytics, AppRole.LOGGED_IN, AppRole.API_CALL);
            });
        }));
    }

    @GET
    @Path(value="api/v2/analytics")
    @Operation(tags={"Analytics"}, summary="Returns list of analytics", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.QUERY, name="show_deleted", description="Add deleted analytics to request", example="true", schema=@Schema(implementation=Boolean.class))}, responses={@ApiResponse(responseCode="200", description="Successful operation", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=AnalyticsDTO.class)))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void getAllVideoAnalytics(@Parameter(hidden=true) Context context) {
        User user = this.usersManager.getUser(context);
        List<AnalyticsDTO> analytics = this.analyticsManager.getAll().stream().filter(user::hasAccessTo).map(it -> new AnalyticsDTO(user, (Analytics)it, this.streamsManager.get(it.getStreamUUID()), this.progressTracker.getProgress(it.getId()))).toList();
        context.json(analytics);
    }

    @GET
    @Path(value="api/v2/analytics/with_groups")
    @Operation(tags={"Analytics"}, summary="Returns list of analytics with groups", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.QUERY, name="status", description="Status of analytics", schema=@Schema(implementation=AnalyticsStatus.class), example="started"), @Parameter(in=ParameterIn.QUERY, name="plugins", description="List of plugin ids", schema=@Schema(type="object", example="[\"alpr\",\"face\"]")), @Parameter(in=ParameterIn.QUERY, name="stream_uuids", description="List of stream uuids", schema=@Schema(type="object", example="[\"3bfc82b3-8780-4bd4-9fc6-f1a71e4990a0\",\"cbfa1d6d-140c-4847-88c4-c317216a2f80\"]"))}, responses={@ApiResponse(responseCode="200", description="Successful operation", content={@Content(mediaType="application/json", schema=@Schema(implementation=GroupsAndAnalyticsDTO.class))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void getAllVideoAnalyticsWithGroups(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        AnalyticsWithGroupsParams analyticsParams = new ApiParams(ctx).getQueryParamsAsClass(AnalyticsWithGroupsParams.class);
        AnalyticsStatus status = analyticsParams.getStatus().isBlank() ? null : AnalyticsStatus.valueOf(analyticsParams.getStatus());
        Set plugins = this.extensionsManager.getAll(VezhaExtension.class).stream().map(SystemExtension::getPluginName).filter(it -> analyticsParams.getPlugins() == null || analyticsParams.getPlugins().contains(it)).collect(Collectors.toSet());
        Predicate<Analytics> analyticsPredicate = it -> !(!user.hasAccessTo((ClientId)it) || !plugins.contains(it.getPluginName()) || analyticsParams.getStreamUuids() != null && !analyticsParams.getStreamUuids().contains(it.getStreamUUID()) || status != null && it.getStatus() != status);
        ctx.json(new AnalyticsRecursiveResponseGenerator(user, analyticsPredicate, false, this.streamsManager, this.analyticsManager, this.analyticsGroupsManager, this.progressTracker).getResponse());
    }

    @GET
    @Path(value="api/v2/analytics/{id}")
    @Operation(tags={"Analytics"}, summary="Get video analytics by ID", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="id", example="1", description="Analytics ID", required=true), @Parameter(in=ParameterIn.QUERY, name="types", description="Filter analytics by type", example="[\"alpr\", \"face\"]", array=@ArraySchema(schema=@Schema(implementation=AnalyticsTypes.class)))}, responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", schema=@Schema(implementation=AnalyticsDTO.class))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void getVideoAnalytics(@Parameter(hidden=true) Context context) {
        User user = this.usersManager.getUser(context);
        int id = Integer.parseInt(context.pathParam("id"));
        HashSet<AnalyticsTypes> types = new HashSet<AnalyticsTypes>(JSON.parseArray(Objects.requireNonNullElse(context.queryParam("types"), "[]"), AnalyticsTypes.class));
        AnalyticsDTO analytics = Optional.ofNullable((Analytics)this.analyticsManager.get(id)).filter(user::hasAccessTo).filter(it -> {
            if (types.isEmpty()) return true;
            if (!it.getModule().getAnalyticsTypes().stream().anyMatch(types::contains)) return false;
            return true;
        }).map(it -> new AnalyticsDTO(user, (Analytics)it, this.streamsManager.get(it.getStreamUUID()), this.progressTracker.getProgress(it.getId()))).orElseThrow(NotFoundResponse::new);
        context.json(analytics);
    }

    @DELETE
    @Path(value="api/v2/analytics/{id}")
    @Operation(tags={"Analytics"}, summary="Delete video analytics by ID", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="id", example="1", description="Analytics ID", required=true)}, responses={@ApiResponse(responseCode="204", description="Successful deleted"), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void deleteVideoAnalytics(@Parameter(hidden=true) Context context) {
        int id;
        Analytics analytics;
        User user = this.usersManager.getUser(context);
        if (!user.hasAccessTo(analytics = (Analytics)this.analyticsManager.get(id = Integer.parseInt(context.pathParam("id"))))) {
            throw new NotFoundResponse();
        }
        this.analyticsManager.delete(analytics);
        this.auditor.audit(context, user, AuditEventActions.ANALYTICS_DELETED, "middleware-core", new AnalyticsDeletedAuditMessage(analytics));
    }

    @POST
    @Path(value="api/v2/analytics/{id}/start")
    @Operation(tags={"Analytics"}, summary="Start video analytics by ID", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="id", example="1", description="Analytics ID", required=true)}, responses={@ApiResponse(responseCode="204", description="Analytics started"), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void startVideoAnalytics(@Parameter(hidden=true) Context context) {
        int id;
        Analytics analytics;
        User user = this.usersManager.getUser(context);
        if (!user.hasAccessTo(analytics = (Analytics)this.analyticsManager.get(id = Integer.parseInt(context.pathParam("id"))))) {
            throw new NotFoundResponse();
        }
        if (analytics.getRestrictions() != null && !analytics.getRestrictions().isAllowed(user, CorePermissions.StartAnalytics)) {
            throw new ForbiddenResponse();
        }
        this.analyticsManager.start(analytics.getId());
        context.status(204);
        this.auditor.audit(context, user, AuditEventActions.ANALYTICS_STARTED, "middleware-core", new AnalyticsStartedAuditMessage(analytics));
    }

    @POST
    @Path(value="api/v2/analytics/{id}/stop")
    @Operation(tags={"Analytics"}, summary="Stop video analytics by ID", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="id", example="1", description="Analytics ID", required=true)}, responses={@ApiResponse(responseCode="204", description="Analytics stopped"), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void stopVideoAnalytics(@Parameter(hidden=true) Context context) {
        int id;
        Analytics analytics;
        User user = this.usersManager.getUser(context);
        if (!user.hasAccessTo(analytics = (Analytics)this.analyticsManager.get(id = Integer.parseInt(context.pathParam("id"))))) {
            throw new NotFoundResponse();
        }
        if (analytics.getRestrictions() != null && !analytics.getRestrictions().isAllowed(user, CorePermissions.StopAnalytics)) {
            throw new ForbiddenResponse();
        }
        this.analyticsManager.stop(analytics.getId());
        context.status(204);
        this.auditor.audit(context, user, AuditEventActions.ANALYTICS_STOPPED, "middleware-core", new AnalyticsStoppedAuditMessage(analytics));
    }

    @POST
    @Path(value="api/v2/analytics/group_start")
    @Operation(tags={"Analytics"}, summary="Start list of analytics.", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, requestBody=@RequestBody(required=true, content={@Content(mediaType="application/json", schema=@Schema(implementation=AnalyticsIdListDTO.class))}), responses={@ApiResponse(responseCode="204", description="Successful operation"), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden")})
    public void groupStartAnalytics(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        AnalyticsIdListDTO analyticsToStart = new ApiParams(ctx).getBodyAsClass(AnalyticsIdListDTO.class);
        List<Analytics> filtered2 = this.analyticsManager.getAll(user::hasAccessTo).stream().filter(it -> it.getRestrictions() == null || it.getRestrictions().isAllowed(user, CorePermissions.StartAnalytics)).filter(it -> analyticsToStart.ids().contains(it.getId())).toList();
        for (Analytics analytics : filtered2) {
            try {
                this.analyticsManager.start(analytics.getId());
                this.auditor.audit(ctx, user, AuditEventActions.ANALYTICS_STARTED, "middleware-core", new AnalyticsStartedAuditMessage(analytics).build());
            }
            catch (Exception exception) {}
        }
        ctx.status(204);
    }

    @POST
    @Path(value="api/v2/analytics/group_stop")
    @Operation(tags={"Analytics"}, summary="Stop list of analytics.", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, requestBody=@RequestBody(required=true, content={@Content(mediaType="application/json", schema=@Schema(implementation=AnalyticsIdListDTO.class))}), responses={@ApiResponse(responseCode="204", description="Successful operation"), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden")})
    public void groupStopAnalytics(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        AnalyticsIdListDTO analyticsToStop = new ApiParams(ctx).getBodyAsClass(AnalyticsIdListDTO.class);
        List<Analytics> filtered2 = this.analyticsManager.getAll(user::hasAccessTo).stream().filter(it -> it.getRestrictions() == null || it.getRestrictions().isAllowed(user, CorePermissions.StopAnalytics)).filter(it -> analyticsToStop.ids().contains(it.getId())).toList();
        for (Analytics analytics : filtered2) {
            try {
                this.analyticsManager.stop(analytics.getId());
                this.auditor.audit(ctx, user, AuditEventActions.ANALYTICS_STOPPED, "middleware-core", new AnalyticsStoppedAuditMessage(analytics).build());
            }
            catch (Exception exception) {}
        }
        ctx.status(204);
    }

    @Inject
    public ApiAnalyticsControllerV2(UsersManager usersManager, StreamsManager streamsManager, AnalyticsManager analyticsManager, AnalyticsGroupsManager analyticsGroupsManager, VideoFileProgressTracker progressTracker, ExtensionsManager extensionsManager, Auditor auditor) {
        this.usersManager = usersManager;
        this.streamsManager = streamsManager;
        this.analyticsManager = analyticsManager;
        this.analyticsGroupsManager = analyticsGroupsManager;
        this.progressTracker = progressTracker;
        this.extensionsManager = extensionsManager;
        this.auditor = auditor;
    }
}

