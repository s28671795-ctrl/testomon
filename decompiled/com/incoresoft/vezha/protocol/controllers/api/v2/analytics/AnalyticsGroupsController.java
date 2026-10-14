/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.protocol.controllers.api.v2.analytics;

import com.google.inject.Inject;
import com.incoresoft.Utils.ApiParams;
import com.incoresoft.extensions.ExtensionsManager;
import com.incoresoft.middleware.analytics.AnalyticsGroup;
import com.incoresoft.middleware.analytics.AnalyticsGroupsManager;
import com.incoresoft.middleware.audit.Auditor;
import com.incoresoft.middleware.http.AppRole;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.model.user.UsersManager;
import com.incoresoft.middleware.plugin.VezhaExtension;
import com.incoresoft.plugin.commons.analytics.dto.AnalyticsGroupDTO;
import com.incoresoft.plugin.commons.dto.ErrorDTO;
import com.incoresoft.plugin.commons.validation.BeanValidator;
import com.incoresoft.vezha.audit.VezhaAuditEventAction;
import com.incoresoft.vezha.audit.messages.analytics.groups.AnalyticsGroupActionAuditMessage;
import com.incoresoft.vezha.audit.messages.analytics.groups.AnalyticsGroupsUpdatedAuditMessage;
import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
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
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@OpenAPIDefinition(info=@Info(title="FACE API", description="FACE HTTP API", version="v2"))
public class AnalyticsGroupsController {
    private static final String API_PATH = "/api/v2/analytics/groups";
    private final AnalyticsGroupsManager analyticsGroupsManager;
    private final UsersManager usersManager;
    private final ExtensionsManager extensionsManager;
    private final BeanValidator validator;
    private final Auditor auditor;

    @Inject
    public void init(Javalin app) {
        app.routes(() -> ApiBuilder.path(API_PATH, () -> {
            ApiBuilder.get(this::getAll, AppRole.LOGGED_IN, AppRole.API_CALL);
            ApiBuilder.path("{plugin_id}", () -> {
                ApiBuilder.get(this::getAllByPlugin, AppRole.LOGGED_IN, AppRole.API_CALL);
                ApiBuilder.post(this::addGroup, AppRole.LOGGED_IN, AppRole.API_CALL);
                ApiBuilder.path("{id}", () -> {
                    ApiBuilder.patch(this::updateGroup, AppRole.LOGGED_IN, AppRole.API_CALL);
                    ApiBuilder.delete(this::deleteGroup, AppRole.LOGGED_IN, AppRole.API_CALL);
                });
            });
        }));
    }

    @Path(value="/api/v2/analytics/groups")
    @GET
    @Operation(tags={"Analytics Groups"}, summary="Get analytics groups", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, responses={@ApiResponse(responseCode="200", description="Successful operation", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=AnalyticsGroupDTO.class)))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", array=@ArraySchema(arraySchema=@Schema(description="List of errors"), schema=@Schema(implementation=ErrorDTO.class)))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void getAll(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        ctx.json(this.analyticsGroupsManager.getAll().stream().filter(user::hasAccessTo).map(AnalyticsGroupDTO::new).toList());
    }

    @Path(value="/api/v2/analytics/groups/{plugin_id}")
    @GET
    @Operation(tags={"Analytics Groups"}, summary="Get analytics groups by plugin", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="plugin_id", description="Plugin id", schema=@Schema(implementation=String.class), required=true)}, responses={@ApiResponse(responseCode="200", description="Successful operation", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=AnalyticsGroupDTO.class)))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", array=@ArraySchema(arraySchema=@Schema(description="List of errors"), schema=@Schema(implementation=ErrorDTO.class)))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void getAllByPlugin(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        String pluginId = this.extractPluginParameter(ctx);
        ctx.json(this.analyticsGroupsManager.getAll(pluginId, user).stream().map(AnalyticsGroupDTO::new).toList());
    }

    @POST
    @Path(value="/api/v2/analytics/groups/{plugin_id}")
    @Operation(tags={"Analytics Groups"}, summary="Add analytics group", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="plugin_id", description="Plugin id", schema=@Schema(implementation=String.class), required=true)}, requestBody=@RequestBody(content={@Content(mediaType="application/json", schema=@Schema(implementation=AnalyticsGroupDTO.class))}), responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", schema=@Schema(implementation=AnalyticsGroupDTO.class))}), @ApiResponse(responseCode="400", description="Bad request", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="404", description="Not Found", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))}), @ApiResponse(responseCode="500", description="Internal server error", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))})})
    public void addGroup(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        String pluginId = this.extractPluginParameter(ctx);
        AnalyticsGroupDTO dto = new ApiParams(ctx).getBodyAsClass(AnalyticsGroupDTO.class);
        dto.setPluginName(pluginId);
        dto.setUserId(user.getId());
        this.validator.validateObjectOrThrow(dto, new Class[0]);
        AnalyticsGroup group = this.analyticsGroupsManager.add(new AnalyticsGroup(dto.getId(), dto.getName(), dto.getPluginName(), dto.getParentId(), user.getClientId()));
        ctx.json(new AnalyticsGroupDTO(group));
        this.auditor.audit(ctx, user, VezhaAuditEventAction.ANALYTICS_GROUP_ADDED, "middleware-core", new AnalyticsGroupActionAuditMessage(group));
    }

    @PATCH
    @Path(value="/api/v2/analytics/groups/{plugin_id}/{id}")
    @Operation(tags={"Analytics Groups"}, summary="Update analytics group", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="plugin_id", description="Plugin id", schema=@Schema(implementation=String.class), required=true), @Parameter(in=ParameterIn.PATH, name="id", description="Analytics group id", example="1", required=true)}, requestBody=@RequestBody(content={@Content(mediaType="application/json", schema=@Schema(implementation=AnalyticsGroupDTO.class))}), responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", schema=@Schema(implementation=AnalyticsGroupDTO.class))}), @ApiResponse(responseCode="400", description="Bad request", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="404", description="Not Found", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))}), @ApiResponse(responseCode="500", description="Internal server error", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))})})
    public void updateGroup(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        int id = ctx.pathParamAsClass("id", Integer.TYPE).getOrThrow(ignored -> new BadRequestResponse("id"));
        String pluginId = ctx.pathParamAsClass("plugin_id", String.class).getOrThrow(ignored -> new BadRequestResponse("Plugin id is required"));
        AnalyticsGroup old = this.analyticsGroupsManager.get(id).filter(it -> it.getPluginName().equals(pluginId)).filter(user::hasAccessTo).orElseThrow(NotFoundResponse::new);
        AnalyticsGroupDTO dto = ctx.bodyAsClass(AnalyticsGroupDTO.class);
        dto.setId(id);
        dto.setPluginName(old.getPluginName());
        dto.setUserId(user.getId());
        this.validator.validateObjectOrThrow(dto, new Class[0]);
        AnalyticsGroup updated = this.analyticsGroupsManager.update(new AnalyticsGroup(dto.getId(), dto.getName(), dto.getPluginName(), dto.getParentId(), old.getClientId()));
        ctx.json(new AnalyticsGroupDTO(updated));
        this.auditor.audit(ctx, user, VezhaAuditEventAction.ANALYTICS_GROUP_UPDATED, "middleware-core", new AnalyticsGroupsUpdatedAuditMessage(old, updated));
    }

    @DELETE
    @Path(value="/api/v2/analytics/groups/{plugin_id}/{id}")
    @Operation(tags={"Analytics Groups"}, summary="Delete existing analytics group", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="plugin_id", description="Plugin id", schema=@Schema(implementation=String.class), required=true), @Parameter(in=ParameterIn.PATH, name="id", description="Analytics group id", example="1", required=true)}, responses={@ApiResponse(responseCode="204", description="Successful operation "), @ApiResponse(responseCode="400", description="Bad request", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="404", description="Not Found", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))}), @ApiResponse(responseCode="500", description="Internal server error", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))})})
    public void deleteGroup(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        int id = ctx.pathParamAsClass("id", Integer.TYPE).getOrThrow(ignored -> new BadRequestResponse("id"));
        String pluginId = ctx.pathParamAsClass("plugin_id", String.class).getOrThrow(ignored -> new BadRequestResponse("Plugin id is required"));
        AnalyticsGroup group = this.analyticsGroupsManager.get(id).filter(it -> it.getPluginName().equals(pluginId)).filter(user::hasAccessTo).orElseThrow(NotFoundResponse::new);
        this.analyticsGroupsManager.delete(id);
        ctx.status(204);
        this.auditor.audit(ctx, user, VezhaAuditEventAction.ANALYTICS_GROUP_DELETED, "middleware-core", new AnalyticsGroupActionAuditMessage(group));
    }

    private String extractPluginParameter(Context ctx) {
        String pluginId = ctx.pathParamAsClass("plugin_id", String.class).getOrThrow(ignored -> new BadRequestResponse("Plugin id is required"));
        this.extensionsManager.getAll(VezhaExtension.class).stream().filter(extension -> extension.getPluginName().equals(pluginId)).findAny().orElseThrow(() -> new NotFoundResponse("Plugin not found"));
        return pluginId;
    }

    @Inject
    public AnalyticsGroupsController(AnalyticsGroupsManager analyticsGroupsManager, UsersManager usersManager, ExtensionsManager extensionsManager, BeanValidator validator, Auditor auditor) {
        this.analyticsGroupsManager = analyticsGroupsManager;
        this.usersManager = usersManager;
        this.extensionsManager = extensionsManager;
        this.validator = validator;
        this.auditor = auditor;
    }
}

