/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.analytics;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.AnalyticsManager;
import com.incoresoft.middleware.analytics.AnalyticsStatus;
import com.incoresoft.middleware.analytics.dto.analytics.AnalyticsDTO;
import com.incoresoft.middleware.analytics.dto.analytics.VideoAnalyticsDTO;
import com.incoresoft.middleware.analytics.dto.analytics.validation.FirstStep;
import com.incoresoft.middleware.audit.Auditor;
import com.incoresoft.middleware.http.AppRole;
import com.incoresoft.middleware.http.controllers.Controller;
import com.incoresoft.middleware.model.analytics.module.AnalyticsModule;
import com.incoresoft.middleware.model.clients.ClientId;
import com.incoresoft.middleware.model.folders.Folder;
import com.incoresoft.middleware.model.permission.CorePermissions;
import com.incoresoft.middleware.model.permission.Permission;
import com.incoresoft.middleware.model.role.RolesManager;
import com.incoresoft.middleware.model.stream.Stream;
import com.incoresoft.middleware.model.stream.StreamType;
import com.incoresoft.middleware.model.stream.StreamsManager;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.model.user.UsersManager;
import com.incoresoft.middleware.plugin.VezhaExtension;
import com.incoresoft.plugin.commons.analytics.dto.GroupAnalyticsActionResponseDTO;
import com.incoresoft.plugin.commons.analytics.dto.GroupedAnalyticsResponseFormatter;
import com.incoresoft.plugin.commons.analytics.dto.GroupsAndAnalyticsDTO;
import com.incoresoft.plugin.commons.analytics.fileprocessing.VideoFileProgressTracker;
import com.incoresoft.plugin.commons.analytics.model.AnalyticsImpl;
import com.incoresoft.plugin.commons.analytics.model.AnalyticsRestrictionsConverter;
import com.incoresoft.plugin.commons.analytics.model.VezhaAnalyticsRestrictionsConverter;
import com.incoresoft.plugin.commons.audit.AuditEventActions;
import com.incoresoft.plugin.commons.audit.template.analytics.AnalyticsCreatedAuditMessage;
import com.incoresoft.plugin.commons.audit.template.analytics.AnalyticsDeletedAuditMessage;
import com.incoresoft.plugin.commons.audit.template.analytics.AnalyticsStartedAuditMessage;
import com.incoresoft.plugin.commons.audit.template.analytics.AnalyticsStoppedAuditMessage;
import com.incoresoft.plugin.commons.audit.template.analytics.AnalyticsUpdatedAuditMessage;
import com.incoresoft.plugin.commons.http.answers.ErrorAnswer;
import com.incoresoft.plugin.commons.params.StreamsParams;
import com.incoresoft.plugin.commons.validation.BeanValidator;
import com.incoresoft.plugin.commons.vms.analytics.model.VmsAnalyticsRestrictionsConverter;
import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.ForbiddenResponse;
import io.javalin.http.NotFoundResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

@SecuritySchemes(value={@SecurityScheme(name="bearerAuth", type=SecuritySchemeType.HTTP, scheme="bearer", description="API bearer access token. If session id is provided, api token can be omitted."), @SecurityScheme(name="cookieAuth", type=SecuritySchemeType.APIKEY, in=SecuritySchemeIn.COOKIE, paramName="sid", description="Current session id. If api token is provided, session id can be omitted.")})
public class AnalyticsControllerV2
implements Controller {
    protected final UsersManager usersManager;
    protected final StreamsManager streamsManager;
    protected final AnalyticsManager analyticsManager;
    protected final VideoFileProgressTracker progressTracker;
    protected final Class<? extends AnalyticsModule> analyticsModuleClass;
    protected final String pluginName;
    protected final Auditor auditor;
    protected final AnalyticsRestrictionsConverter analyticsRestrictionsConverter;
    protected final RolesManager roleManager;
    protected final Permission viewAnalyticsPermission;
    protected final Permission manageAnalyticsPermission;
    protected final BeanValidator validator;

    public AnalyticsControllerV2(UsersManager usersManager, StreamsManager streamsManager, AnalyticsManager analyticsManager, VideoFileProgressTracker progressTracker, VezhaExtension pluginExtension, Auditor auditor, VezhaAnalyticsRestrictionsConverter analyticsRestrictionsConverter, RolesManager roleManager, BeanValidator beanValidator) {
        this.usersManager = Objects.requireNonNull(usersManager);
        this.streamsManager = Objects.requireNonNull(streamsManager);
        this.analyticsManager = Objects.requireNonNull(analyticsManager);
        this.progressTracker = Objects.requireNonNull(progressTracker);
        this.analyticsModuleClass = Objects.requireNonNull(pluginExtension.getModuleClass());
        this.pluginName = pluginExtension.getPluginName();
        this.viewAnalyticsPermission = Objects.requireNonNull(pluginExtension.getViewAnalyticsPermission());
        this.manageAnalyticsPermission = Objects.requireNonNull(pluginExtension.getManageAnalyticsPermission());
        this.auditor = auditor;
        this.analyticsRestrictionsConverter = Objects.requireNonNull(analyticsRestrictionsConverter);
        this.roleManager = Objects.requireNonNull(roleManager);
        this.validator = Objects.requireNonNull(beanValidator);
    }

    public AnalyticsControllerV2(UsersManager usersManager, StreamsManager streamsManager, AnalyticsManager analyticsManager, VideoFileProgressTracker progressTracker, Class<? extends AnalyticsModule> analyticsModuleClass, String pluginName, Permission viewAnalyticsPermission, Permission manageAnalyticsPermission, Auditor auditor, VmsAnalyticsRestrictionsConverter analyticsRestrictionsConverter, RolesManager roleManager, BeanValidator validator) {
        this.usersManager = Objects.requireNonNull(usersManager);
        this.streamsManager = Objects.requireNonNull(streamsManager);
        this.analyticsManager = Objects.requireNonNull(analyticsManager);
        this.progressTracker = Objects.requireNonNull(progressTracker);
        this.analyticsModuleClass = Objects.requireNonNull(analyticsModuleClass);
        this.pluginName = pluginName;
        this.viewAnalyticsPermission = Objects.requireNonNull(viewAnalyticsPermission);
        this.manageAnalyticsPermission = Objects.requireNonNull(manageAnalyticsPermission);
        this.auditor = auditor;
        this.analyticsRestrictionsConverter = Objects.requireNonNull(analyticsRestrictionsConverter);
        this.roleManager = Objects.requireNonNull(roleManager);
        this.validator = Objects.requireNonNull(validator);
    }

    @Override
    public void init(Javalin app) {
        app.routes(() -> ApiBuilder.path("api/v2", () -> ApiBuilder.path(this.pluginName, () -> ApiBuilder.path("analytics", () -> {
            ApiBuilder.get(this::getVideoAnalyticsV2, AppRole.LOGGED_IN, AppRole.API_CALL, this.viewAnalyticsPermission);
            ApiBuilder.post(this::addVideoAnalytics, AppRole.LOGGED_IN, AppRole.API_CALL, this.manageAnalyticsPermission);
            ApiBuilder.post("validate", this::validateVideoAnalytic, AppRole.LOGGED_IN, AppRole.API_CALL, this.manageAnalyticsPermission);
            ApiBuilder.post("group_start", this::groupStartAnalytics, AppRole.LOGGED_IN, AppRole.API_CALL);
            ApiBuilder.post("group_stop", this::groupStopAnalytics, AppRole.LOGGED_IN, AppRole.API_CALL);
            ApiBuilder.path("{id}", () -> {
                ApiBuilder.get(this::getVideoAnalyticV2, AppRole.LOGGED_IN, AppRole.API_CALL, this.viewAnalyticsPermission);
                ApiBuilder.put(this::updateVideoAnalytics, AppRole.LOGGED_IN, AppRole.API_CALL, this.manageAnalyticsPermission);
                ApiBuilder.delete(this::deleteVideoAnalytic, AppRole.LOGGED_IN, AppRole.API_CALL, this.manageAnalyticsPermission);
                ApiBuilder.get("start", this::startVideoAnalytic, AppRole.LOGGED_IN, AppRole.API_CALL, this.manageAnalyticsPermission);
                ApiBuilder.get("stop", this::stopVideoAnalytic, AppRole.LOGGED_IN, AppRole.API_CALL, this.manageAnalyticsPermission);
            });
        }))));
    }

    @GET
    @Operation(tags={"Analytics"}, summary="Returns list of analytics", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.QUERY, name="stream_type", description="Video stream type values for analytics", schema=@Schema(implementation=StreamType.class)), @Parameter(in=ParameterIn.QUERY, name="status", description="Status of analytics", schema=@Schema(implementation=AnalyticsStatus.class)), @Parameter(in=ParameterIn.QUERY, name="name", description="Name of analytics", schema=@Schema(implementation=String.class)), @Parameter(in=ParameterIn.QUERY, name="offset", example="0", description="Number of analytics to skip, counting from the start of sorted analytics list (analytics sorted by id in descending order).", schema=@Schema(implementation=Integer.class)), @Parameter(in=ParameterIn.QUERY, name="limit", example="10", description="Number of analytics to retrieve, counting from the start of sorted analytics list (analytics sorted by id in descending order).", schema=@Schema(implementation=Integer.class)), @Parameter(in=ParameterIn.QUERY, name="show_deleted", description="Add deleted analytics to request", example="true", schema=@Schema(implementation=Boolean.class))}, responses={@ApiResponse(responseCode="200", description="Successful operation", content={@Content(mediaType="application/json", schema=@Schema(implementation=GroupsAndAnalyticsDTO.class))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void getVideoAnalyticsV2(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        Set vaIds = Optional.of(this.getAnalytics(ctx).stream().map(Analytics::getId).toList()).map(it -> new StreamsParams().getVaIdsForUser((List<Integer>)it, user, this.analyticsManager, this.streamsManager)).orElseThrow(NotFoundResponse::new);
        if (vaIds.isEmpty()) {
            ctx.json(new GroupedAnalyticsResponseFormatter(user, this.streamsManager, this.progressTracker, Collections.emptyList(), Collections.emptyList()).get());
            return;
        }
        List<Analytics> filtered2 = this.getAnalytics(ctx).stream().filter(it -> vaIds.isEmpty() || vaIds.contains(it.getId())).sorted((a1, a2) -> Integer.compare(a2.getId(), a1.getId())).toList();
        List<Folder> streamFolders = this.streamsManager.getStreamFolders().stream().filter(it -> user.isRoot() || it.getClientId() == user.getClientId()).toList();
        ctx.json(new GroupedAnalyticsResponseFormatter(user, this.streamsManager, this.progressTracker, streamFolders, filtered2).get());
    }

    @GET
    @Operation(tags={"Analytics"}, summary="Get video analytics by ID", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="id", example="1", description="Analytics ID", required=true), @Parameter(in=ParameterIn.QUERY, name="show_deleted", description="Add deleted analytics to request", example="true", schema=@Schema(implementation=Boolean.class))}, responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", schema=@Schema(implementation=AnalyticsDTO.class))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void getVideoAnalyticV2(@Parameter(hidden=true) Context ctx) {
        boolean returnArchivedAnalytics = ctx.queryParamAsClass("show_deleted", Boolean.class).getOrDefault(false);
        User user = this.usersManager.getUser(ctx);
        Integer id = ctx.pathParamAsClass("id", Integer.TYPE).getOrThrow(e -> new BadRequestResponse("id"));
        Analytics analytics = (Analytics)this.analyticsManager.get(id);
        if (analytics == null && returnArchivedAnalytics) {
            analytics = (Analytics)this.analyticsManager.getAnalyticsArchive().get(id);
        }
        analytics = Optional.ofNullable(analytics).filter(it -> it.getPluginName().equalsIgnoreCase(this.pluginName) && user.hasAccessTo((ClientId)it)).orElseThrow(NotFoundResponse::new);
        if (!returnArchivedAnalytics && analytics.getStatus() == AnalyticsStatus.deleted) {
            throw new NotFoundResponse();
        }
        ctx.json(this.getAnalyticsDTO(user, analytics));
    }

    @POST
    @Operation(tags={"Analytics"}, summary="Validate video analytics", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, requestBody=@RequestBody(required=true, content={@Content(schema=@Schema(implementation=VideoAnalyticsDTO.class))}), responses={@ApiResponse(responseCode="204", description="Validation success"), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void validateVideoAnalytic(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        this.parseVideoAnalyticsDTO(ctx, user);
        ctx.status(204);
    }

    @POST
    @Operation(tags={"Analytics"}, summary="Add new video analytics", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, requestBody=@RequestBody(required=true, content={@Content(schema=@Schema(implementation=VideoAnalyticsDTO.class))}), responses={@ApiResponse(responseCode="200", description="Analytics added", content={@Content(mediaType="application/json", schema=@Schema(implementation=AnalyticsDTO.class))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void addVideoAnalytics(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        VideoAnalyticsDTO videoAnalyticsDTO = this.parseVideoAnalyticsDTO(ctx, user);
        AnalyticsImpl analyticsImpl = new AnalyticsImpl(videoAnalyticsDTO, this.pluginName, user, this.analyticsRestrictionsConverter);
        Analytics analytics = this.analyticsManager.add(analyticsImpl);
        this.auditor.audit(ctx, user, AuditEventActions.ANALYTICS_ADDED, this.pluginName, new AnalyticsCreatedAuditMessage(analytics).build());
        ctx.json(this.getAnalyticsDTO(user, analytics));
    }

    @PUT
    @Operation(tags={"Analytics"}, summary="Update video analytics by ID", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="id", example="1", description="Analytics ID", required=true)}, requestBody=@RequestBody(required=true, content={@Content(schema=@Schema(implementation=VideoAnalyticsDTO.class))}), responses={@ApiResponse(responseCode="200", description="Successful operation", content={@Content(mediaType="application/json", schema=@Schema(implementation=AnalyticsDTO.class))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void updateVideoAnalytics(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        VideoAnalyticsDTO videoAnalyticsDTO = this.parseVideoAnalyticsDTO(ctx, user);
        Integer id = ctx.pathParamAsClass("id", Integer.TYPE).getOrThrow(e -> new BadRequestResponse("id"));
        videoAnalyticsDTO.setId(id);
        Analytics analyticsById = Optional.ofNullable((Analytics)this.analyticsManager.get(videoAnalyticsDTO.getId())).filter(it -> it.getPluginName().equalsIgnoreCase(this.pluginName) && user.hasAccessTo((ClientId)it)).orElseThrow(NotFoundResponse::new);
        if (analyticsById.getRestrictions() != null && !analyticsById.getRestrictions().isAllowed(user, CorePermissions.EditAnalytics)) {
            throw new ForbiddenResponse();
        }
        AnalyticsImpl analyticsImpl = new AnalyticsImpl(analyticsById, videoAnalyticsDTO, this.pluginName, user, this.analyticsRestrictionsConverter);
        Analytics analytics = this.analyticsManager.update(analyticsImpl);
        this.auditor.audit(ctx, user, AuditEventActions.ANALYTICS_UPDATED, this.pluginName, new AnalyticsUpdatedAuditMessage(analytics).build());
        ctx.json(this.getAnalyticsDTO(user, analytics));
    }

    @DELETE
    @Operation(tags={"Analytics"}, summary="Delete video analytics by ID", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="id", example="1", description="Analytics ID", required=true)}, responses={@ApiResponse(responseCode="204", description="Successful deleted"), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void deleteVideoAnalytic(@Parameter(hidden=true) Context ctx) {
        Integer id = ctx.pathParamAsClass("id", Integer.TYPE).getOrThrow(e -> new BadRequestResponse("id"));
        User user = this.usersManager.getUser(ctx);
        Analytics analytics = Optional.ofNullable((Analytics)this.analyticsManager.get(id)).filter(it -> it.getPluginName().equalsIgnoreCase(this.pluginName) && user.hasAccessTo((ClientId)it)).orElseThrow(NotFoundResponse::new);
        if (analytics.getRestrictions() != null && !analytics.getRestrictions().isAllowed(user, CorePermissions.EditAnalytics)) {
            throw new ForbiddenResponse();
        }
        this.analyticsManager.delete(id);
        ctx.status(204);
        this.auditor.audit(ctx, user, AuditEventActions.ANALYTICS_DELETED, this.pluginName, new AnalyticsDeletedAuditMessage(analytics).build());
    }

    @GET
    @Operation(tags={"Analytics"}, summary="Start video analytic", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="id", example="1", description="Analytics ID", required=true)}, responses={@ApiResponse(responseCode="204", description="Analytics started"), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void startVideoAnalytic(@Parameter(hidden=true) Context ctx) {
        Integer id = ctx.pathParamAsClass("id", Integer.TYPE).getOrThrow(e -> new BadRequestResponse("id"));
        User user = this.usersManager.getUser(ctx);
        Analytics analytics = Optional.ofNullable((Analytics)this.analyticsManager.get(id)).filter(it -> it.getPluginName().equalsIgnoreCase(this.pluginName) && user.hasAccessTo((ClientId)it)).orElseThrow(NotFoundResponse::new);
        if (analytics.getRestrictions() != null && !analytics.getRestrictions().isAllowed(user, CorePermissions.StartAnalytics)) {
            throw new ForbiddenResponse();
        }
        this.analyticsManager.start(id);
        ctx.status(204);
        this.auditor.audit(ctx, user, AuditEventActions.ANALYTICS_STARTED, this.pluginName, new AnalyticsStartedAuditMessage(analytics).build());
    }

    @GET
    @Operation(tags={"Analytics"}, summary="Stop video analytic", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="id", example="1", description="Analytics ID", required=true)}, responses={@ApiResponse(responseCode="204", description="Analytics stopped"), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void stopVideoAnalytic(@Parameter(hidden=true) Context ctx) {
        Integer id = ctx.pathParamAsClass("id", Integer.TYPE).getOrThrow(e -> new BadRequestResponse("id"));
        User user = this.usersManager.getUser(ctx);
        Analytics analytics = Optional.ofNullable((Analytics)this.analyticsManager.get(id)).filter(it -> it.getPluginName().equalsIgnoreCase(this.pluginName) && user.hasAccessTo((ClientId)it)).orElseThrow(NotFoundResponse::new);
        if (analytics.getRestrictions() != null && !analytics.getRestrictions().isAllowed(user, CorePermissions.StopAnalytics)) {
            throw new ForbiddenResponse();
        }
        this.analyticsManager.stop(id);
        this.progressTracker.resetProgress(id);
        ctx.status(204);
        this.auditor.audit(ctx, user, AuditEventActions.ANALYTICS_STOPPED, this.pluginName, new AnalyticsStoppedAuditMessage(analytics).build());
    }

    @POST
    @Operation(tags={"Analytics"}, summary="Start multiple video analytic", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, requestBody=@RequestBody(required=true, content={@Content(mediaType="application/json", array=@ArraySchema(arraySchema=@Schema(description="Array of analytics ids to start"), schema=@Schema(implementation=Integer.class)), examples={@ExampleObject(value="[1,2,3]")})}), responses={@ApiResponse(responseCode="200", description="Analytics queued for stop", content={@Content(mediaType="application/json", schema=@Schema(implementation=GroupAnalyticsActionResponseDTO.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void groupStartAnalytics(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        Set<Integer> analyticsToStart = Set.copyOf(JSON.parseArray(ctx.body(), Integer.class));
        List<Analytics> filtered2 = this.getAnalytics(ctx).stream().filter(it -> analyticsToStart.contains(it.getId())).toList();
        HashSet<Integer> analyticsToSkip = new HashSet<Integer>();
        for (Analytics analytics : filtered2) {
            if (!analytics.getRestrictions().isAllowed(user, CorePermissions.StartAnalytics)) {
                analyticsToSkip.add(analytics.getId());
                continue;
            }
            try {
                this.analyticsManager.start(analytics.getId());
                this.auditor.audit(ctx, user, AuditEventActions.ANALYTICS_STARTED, this.pluginName, new AnalyticsStartedAuditMessage(analytics).build());
            }
            catch (Exception ignored) {
                analyticsToSkip.add(analytics.getId());
            }
        }
        ctx.json(new GroupAnalyticsActionResponseDTO(analyticsToSkip));
    }

    @POST
    @Operation(tags={"Analytics"}, summary="Stop multiple video analytic", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, requestBody=@RequestBody(required=true, content={@Content(mediaType="application/json", array=@ArraySchema(arraySchema=@Schema(description="Array of analytics ids to stop"), schema=@Schema(implementation=Integer.class)), examples={@ExampleObject(value="[1,2,3]")})}), responses={@ApiResponse(responseCode="200", description="Analytics queued for stop", content={@Content(mediaType="application/json", schema=@Schema(implementation=GroupAnalyticsActionResponseDTO.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void groupStopAnalytics(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        Set<Integer> analyticsToStop = Set.copyOf(JSON.parseArray(ctx.body(), Integer.class));
        List<Analytics> filtered2 = this.getAnalytics(ctx).stream().filter(it -> analyticsToStop.contains(it.getId())).toList();
        HashSet<Integer> analyticsToSkip = new HashSet<Integer>();
        for (Analytics analytics : filtered2) {
            if (!analytics.getRestrictions().isAllowed(user, CorePermissions.StopAnalytics)) {
                analyticsToSkip.add(analytics.getId());
                continue;
            }
            try {
                this.analyticsManager.stop(analytics.getId());
                this.progressTracker.resetProgress(analytics.getId());
                this.auditor.audit(ctx, user, AuditEventActions.ANALYTICS_STOPPED, this.pluginName, new AnalyticsStoppedAuditMessage(analytics).build());
            }
            catch (Exception ignored) {
                analyticsToSkip.add(analytics.getId());
            }
        }
        ctx.json(new GroupAnalyticsActionResponseDTO(analyticsToSkip));
    }

    protected VideoAnalyticsDTO parseVideoAnalyticsDTO(Context ctx, User user) {
        JSONObject body2 = JSON.parseObject(ctx.body());
        VideoAnalyticsDTO videoAnalyticsDTO = JSON.toJavaObject(body2, VideoAnalyticsDTO.class);
        videoAnalyticsDTO.update(JSON.parseObject(body2.getJSONObject("module").toJSONString(), this.analyticsModuleClass), this.streamsManager);
        this.validator.validateObjectOrThrow(videoAnalyticsDTO, FirstStep.class);
        this.validator.validateObjectOrThrow(videoAnalyticsDTO, new Class[0]);
        return videoAnalyticsDTO;
    }

    @NotNull
    private List<Analytics> getAnalytics(Context ctx) {
        User user = this.usersManager.getUser(ctx);
        if (!user.hasPermission(this.viewAnalyticsPermission)) {
            throw new ForbiddenResponse();
        }
        String streamType = ctx.queryParamAsClass("stream_type", String.class).getOrDefault(null);
        String statusStr = ctx.queryParamAsClass("status", String.class).getOrDefault(null);
        AnalyticsStatus status = StringUtils.isBlank(statusStr) ? null : AnalyticsStatus.valueOf(statusStr);
        String name = ctx.queryParamAsClass("name", String.class).getOrDefault("");
        boolean returnArchivedAnalytics = ctx.queryParamAsClass("show_deleted", Boolean.class).getOrDefault(false);
        Predicate<Analytics> analyticsFilteringPredicate = analytics -> {
            if (!Objects.equals(analytics.getPluginName(), this.pluginName)) {
                return false;
            }
            if (!user.hasAccessTo((ClientId)analytics)) {
                return false;
            }
            if (status != null && analytics.getStatus() != status) {
                return false;
            }
            return AnalyticsControllerV2.nameFilter(name, analytics);
        };
        Predicate<Analytics> analyticsStreamFilteringPredicate = it -> {
            if (streamType != null) {
                Stream stream = this.streamsManager.get(it.getStreamUUID());
                if (stream == null) {
                    return false;
                }
                if (streamType.startsWith("!") && stream.getType().name().equals(streamType.substring(1))) {
                    return false;
                }
                return stream.getType().name().equals(streamType);
            }
            return true;
        };
        LinkedList<Analytics> analytics2 = new LinkedList<Analytics>(this.analyticsManager.getAll(analyticsFilteringPredicate.and(analyticsStreamFilteringPredicate)));
        if (returnArchivedAnalytics) {
            analytics2.addAll(this.analyticsManager.getAnalyticsArchive().getAll(analyticsFilteringPredicate));
        }
        return analytics2;
    }

    private static boolean nameFilter(String name, Analytics analytics) {
        return name == null || StringUtils.containsIgnoreCase(analytics.getName(), name) || String.valueOf(analytics.getId()).contains(name) || String.valueOf(analytics.getStreamId()).contains(name);
    }

    protected AnalyticsDTO getAnalyticsDTO(User user, Analytics analytics) {
        return new AnalyticsDTO(user, analytics, this.streamsManager.get(analytics.getStreamUUID()), this.progressTracker.getProgress(analytics.getId()));
    }
}

