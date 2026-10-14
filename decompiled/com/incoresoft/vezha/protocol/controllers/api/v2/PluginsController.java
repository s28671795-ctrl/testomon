/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.protocol.controllers.api.v2;

import com.github.zafarkhaja.semver.Version;
import com.google.inject.Inject;
import com.incoresoft.audit.CoreAuditEventAction;
import com.incoresoft.dto.audit.messages.plugins.AllPluginsStartedAuditMessage;
import com.incoresoft.dto.audit.messages.plugins.AllPluginsStoppedAuditMessage;
import com.incoresoft.dto.audit.messages.plugins.AllPluginsUpdatedAuditMessage;
import com.incoresoft.dto.audit.messages.plugins.PluginDisabledAuditMessage;
import com.incoresoft.dto.audit.messages.plugins.PluginEnabledAuditMessage;
import com.incoresoft.dto.audit.messages.plugins.PluginInstalledAuditMessage;
import com.incoresoft.dto.audit.messages.plugins.PluginUninstalledAuditMessage;
import com.incoresoft.dto.audit.messages.plugins.PluginUpdatedAuditMessage;
import com.incoresoft.dto.plugins.InstalledPluginInfoDTO;
import com.incoresoft.dto.plugins.InstalledPluginsPaginationAnswerDTO;
import com.incoresoft.dto.plugins.PluginVersionDTO;
import com.incoresoft.dto.plugins.UploadPluginFileDTO;
import com.incoresoft.http.InstallationListener;
import com.incoresoft.middleware.audit.AuditEvent;
import com.incoresoft.middleware.audit.Auditor;
import com.incoresoft.middleware.http.AppRole;
import com.incoresoft.middleware.http.controllers.Controller;
import com.incoresoft.middleware.http.exceptions.ErrorException;
import com.incoresoft.middleware.http.serialization.Serialization;
import com.incoresoft.middleware.model.permission.CorePermissions;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.model.user.UsersManager;
import com.incoresoft.plugin.commons.audit.AuditEventBuilder;
import com.incoresoft.plugin.commons.http.answers.ErrorAnswer;
import com.incoresoft.plugin.commons.http.error.BaseError;
import com.incoresoft.plugin.commons.utils.JavalinUtils;
import com.incoresoft.plugin.commons.validation.BeanValidator;
import com.incoresoft.plugins.exceptions.PluginNotFoundInSystemException;
import com.incoresoft.plugins.models.InstalledPluginInfo;
import com.incoresoft.plugins.models.PluginType;
import com.incoresoft.vezha.plugins.VezhaPluginsFacade;
import com.incoresoft.vezha.plugins.analytics.AnalyticsPluginsManagerFacade;
import com.incoresoft.vezha.plugins.event_manager.EventManagerPluginsManagerFacade;
import com.incoresoft.vezha.utils.PluginTypeParser;
import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@OpenAPIDefinition(info=@Info(title="Middleware-Core", description="Middleware-Core HTTP API", version="v2"))
public class PluginsController
implements Controller {
    private static final Logger log = LoggerFactory.getLogger(PluginsController.class);
    private static final String API_PATH = "/api/v1/plugins";
    private final UsersManager usersManager;
    private final Auditor auditor;
    private final AnalyticsPluginsManagerFacade analyticsPluginsManagerFacade;
    private final EventManagerPluginsManagerFacade eventManagerPluginsManagerFacade;
    private BeanValidator beanValidator;

    @Override
    @Inject
    public void init(Javalin app) {
        app.routes(() -> ApiBuilder.path(API_PATH, () -> {
            ApiBuilder.get("installed", this::getInstalledPlugins, AppRole.LOGGED_IN, AppRole.API_CALL, CorePermissions.ManageSystemUpdatesAndPlugins);
            ApiBuilder.put("update", this::updateAllPlugins, AppRole.LOGGED_IN, AppRole.API_CALL, CorePermissions.ManageSystemUpdatesAndPlugins);
            ApiBuilder.path("{plugin_type}", () -> {
                ApiBuilder.put("update", this::updateAllPluginsByType, AppRole.LOGGED_IN, AppRole.API_CALL, CorePermissions.ManageSystemUpdatesAndPlugins);
                ApiBuilder.put("stop", this::stopAllPlugins, AppRole.LOGGED_IN, AppRole.API_CALL, CorePermissions.ManageSystemUpdatesAndPlugins);
                ApiBuilder.put("start", this::startAllPlugins, AppRole.LOGGED_IN, AppRole.API_CALL, CorePermissions.ManageSystemUpdatesAndPlugins);
                ApiBuilder.post(this::uploadPlugin, AppRole.LOGGED_IN, AppRole.API_CALL, CorePermissions.ManageSystemUpdatesAndPlugins);
                ApiBuilder.path("{id}", () -> {
                    ApiBuilder.patch("start", this::startPlugin, AppRole.LOGGED_IN, AppRole.API_CALL, CorePermissions.ManageSystemUpdatesAndPlugins);
                    ApiBuilder.patch("stop", this::stopPlugin, AppRole.LOGGED_IN, AppRole.API_CALL, CorePermissions.ManageSystemUpdatesAndPlugins);
                    ApiBuilder.delete(this::uninstallPlugin, AppRole.LOGGED_IN, AppRole.API_CALL, CorePermissions.ManageSystemUpdatesAndPlugins);
                    ApiBuilder.patch("update", this::updatePlugin, AppRole.LOGGED_IN, AppRole.API_CALL, CorePermissions.ManageSystemUpdatesAndPlugins);
                });
            });
        }));
    }

    @GET
    @Path(value="/api/v1/plugins/installed")
    @Operation(tags={"Plugins"}, summary="Get installed plugins info", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", schema=@Schema(implementation=InstalledPluginsPaginationAnswerDTO.class))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void getInstalledPlugins(@Parameter(hidden=true) Context ctx) {
        ArrayList<InstalledPluginInfoDTO> dtos = new ArrayList<InstalledPluginInfoDTO>();
        dtos.addAll(this.analyticsPluginsManagerFacade.getInstalledPluginsInfos().stream().map(InstalledPluginInfoDTO::new).sorted(Comparator.comparing(InstalledPluginInfoDTO::getName)).toList());
        dtos.addAll(this.eventManagerPluginsManagerFacade.getInstalledPluginsInfos().stream().map(InstalledPluginInfoDTO::new).sorted(Comparator.comparing(InstalledPluginInfoDTO::getName)).toList());
        ctx.json(new InstalledPluginsPaginationAnswerDTO((List<InstalledPluginInfoDTO>)dtos, (long)dtos.size(), dtos.size()));
    }

    @PUT
    @Path(value="/api/v1/plugins/update")
    @Operation(tags={"Plugins"}, summary="Update all plugins", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, responses={@ApiResponse(responseCode="202", description="Accepted"), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void updateAllPlugins(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        Map<String, Version> pluginsBeforeUpdate = Stream.concat(this.analyticsPluginsManagerFacade.getInstalledPluginsInfos().stream(), this.eventManagerPluginsManagerFacade.getInstalledPluginsInfos().stream()).collect(Collectors.toUnmodifiableMap(InstalledPluginInfo::getId, InstalledPluginInfo::getVersion));
        List futures = Stream.concat(this.analyticsPluginsManagerFacade.updateAll(new InstallationListener[0]).stream(), this.eventManagerPluginsManagerFacade.updateAll(new InstallationListener[0]).stream()).toList();
        CompletableFuture<Void> allPluginsUpdatedFuture = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        AuditEventBuilder builder = new AuditEventBuilder();
        builder.setContext(ctx);
        allPluginsUpdatedFuture.whenComplete((r, e) -> {
            Map<String, Version> pluginsAfterUpdate = Stream.concat(this.analyticsPluginsManagerFacade.getInstalledPluginsInfos().stream(), this.eventManagerPluginsManagerFacade.getInstalledPluginsInfos().stream()).collect(Collectors.toUnmodifiableMap(InstalledPluginInfo::getId, InstalledPluginInfo::getVersion));
            List<PluginUpdatedAuditMessage> changes = pluginsBeforeUpdate.keySet().stream().filter(pluginsAfterUpdate::containsKey).filter(it -> !Objects.equals(pluginsBeforeUpdate.get(it), pluginsAfterUpdate.get(it))).sorted().map(it -> new PluginUpdatedAuditMessage((String)it, ((Version)pluginsBeforeUpdate.get(it)).toString(), ((Version)pluginsAfterUpdate.get(it)).toString())).toList();
            AllPluginsUpdatedAuditMessage dto = new AllPluginsUpdatedAuditMessage(changes);
            AuditEvent auditEvent = builder.setUserId(user.getId()).setClientId(user.getClientId()).setAction(CoreAuditEventAction.PLUGIN_UPDATED).setSourceId("middleware-core").setMessage(Serialization.writeValueAsString(dto.build())).build();
            this.auditor.audit(auditEvent);
        });
        ctx.status(202);
    }

    @PUT
    @Path(value="/api/v1/plugins/{plugin_type}/update")
    @Operation(tags={"Plugins"}, summary="Update all plugins by type", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="plugin_type", example="analytics", description="Plugin type", required=true, schema=@Schema(allowableValues={"analytics", "event-manager"}))}, responses={@ApiResponse(responseCode="202", description="Accepted"), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void updateAllPluginsByType(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        VezhaPluginsFacade facade = this.getFacade(ctx);
        facade.updateAll(new InstallationListener[0]);
        ctx.status(202);
        this.auditor.audit(ctx, user, CoreAuditEventAction.PLUGIN_UPDATED, "middleware-core", new AllPluginsUpdatedAuditMessage());
    }

    @PUT
    @Path(value="/api/v1/plugins/{plugin_type}/start")
    @Operation(tags={"Plugins"}, summary="Start all plugins", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="plugin_type", example="analytics", description="Plugin type", required=true, schema=@Schema(allowableValues={"analytics", "event-manager"}))}, responses={@ApiResponse(responseCode="204", description="Successful operation "), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void startAllPlugins(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        VezhaPluginsFacade facade = this.getFacade(ctx);
        facade.startAll();
        ctx.status(204);
        this.auditor.audit(ctx, user, CoreAuditEventAction.PLUGIN_ENABLED, "middleware-core", new AllPluginsStartedAuditMessage());
    }

    @PUT
    @Path(value="/api/v1/plugins/{plugin_type}/stop")
    @Operation(tags={"Plugins"}, summary="Stop all plugins", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="plugin_type", example="analytics", description="Plugin type", required=true, schema=@Schema(allowableValues={"analytics", "event-manager"}))}, responses={@ApiResponse(responseCode="204", description="Successful operation "), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void stopAllPlugins(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        VezhaPluginsFacade facade = this.getFacade(ctx);
        facade.stopAll();
        ctx.status(204);
        this.auditor.audit(ctx, user, CoreAuditEventAction.PLUGIN_DISABLED, "middleware-core", new AllPluginsStoppedAuditMessage());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @POST
    @Path(value="/api/v1/plugins/{plugin_type}")
    @Operation(tags={"Plugins"}, summary="Upload plugin", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="plugin_type", example="analytics", description="Plugin type", required=true, schema=@Schema(allowableValues={"analytics", "event-manager"}))}, requestBody=@RequestBody(content={@Content(mediaType="multipart/form-data", encoding={@Encoding(name="file", contentType="application/octet-stream")}, schema=@Schema(implementation=UploadPluginFileDTO.class))}), responses={@ApiResponse(responseCode="204", description="Successful operation"), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found Sounds Item")})
    public void uploadPlugin(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        VezhaPluginsFacade facade = this.getFacade(ctx);
        JavalinUtils.validateFileJarExtension(ctx, "file");
        File pluginFile = JavalinUtils.receiveAndSaveFile(ctx, "file", "temp");
        try {
            facade.installPlugin(pluginFile.toPath());
        }
        finally {
            try {
                Files.deleteIfExists(pluginFile.toPath());
            }
            catch (Exception e) {
                log.error("Failed to delete temp file {}", (Object)pluginFile.toPath(), (Object)e);
            }
        }
        ctx.status(204);
        this.auditor.audit(ctx, user, CoreAuditEventAction.PLUGIN_INSTALLED, "middleware-core", new PluginInstalledAuditMessage(pluginFile.getName()));
    }

    @DELETE
    @Path(value="/api/v1/plugins/{plugin_type}/{id}")
    @Operation(tags={"Plugins"}, summary="Uninstall plugin by id", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="plugin_type", example="analytics", description="Plugin type", required=true, schema=@Schema(allowableValues={"analytics", "event-manager"})), @Parameter(in=ParameterIn.PATH, name="id", example="middleware-alpr-plugin", description="Plugin ID", required=true)}, responses={@ApiResponse(responseCode="204", description="Successful operation "), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void uninstallPlugin(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        String pluginId = ctx.pathParamAsClass("id", String.class).getOrThrow(i -> new ErrorException(BaseError.RequiredParams, "id"));
        VezhaPluginsFacade facade = this.getFacade(ctx);
        facade.uninstallPlugin(pluginId);
        ctx.status(204);
        this.auditor.audit(ctx, user, CoreAuditEventAction.PLUGIN_DELETED, "middleware-core", new PluginUninstalledAuditMessage(pluginId));
    }

    @PATCH
    @Path(value="/api/v1/plugins/{plugin_type}/{id}/start")
    @Operation(tags={"Plugins"}, summary="Start plugin by id", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="plugin_type", example="analytics", description="Plugin type", required=true, schema=@Schema(allowableValues={"analytics", "event-manager"})), @Parameter(in=ParameterIn.PATH, name="id", example="middleware-alpr-plugin", description="Plugin ID", required=true)}, responses={@ApiResponse(responseCode="204", description="Successful operation "), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void startPlugin(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        String pluginId = ctx.pathParamAsClass("id", String.class).getOrThrow(i -> new ErrorException(BaseError.RequiredParams, "id"));
        VezhaPluginsFacade facade = this.getFacade(ctx);
        facade.startPlugin(pluginId);
        ctx.status(204);
        this.auditor.audit(ctx, user, CoreAuditEventAction.PLUGIN_ENABLED, "middleware-core", new PluginEnabledAuditMessage(pluginId));
    }

    @PATCH
    @Path(value="/api/v1/plugins/{plugin_type}/{id}/stop")
    @Operation(tags={"Plugins"}, summary="Stop plugin by id", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="plugin_type", example="analytics", description="Plugin type", required=true, schema=@Schema(allowableValues={"analytics", "event-manager"})), @Parameter(in=ParameterIn.PATH, name="id", example="middleware-alpr-plugin", description="Plugin ID", required=true)}, responses={@ApiResponse(responseCode="204", description="Successful operation "), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void stopPlugin(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        String pluginId = ctx.pathParamAsClass("id", String.class).getOrThrow(i -> new ErrorException(BaseError.RequiredParams, "id"));
        VezhaPluginsFacade facade = this.getFacade(ctx);
        facade.stopPlugin(pluginId);
        this.auditor.audit(ctx, user, CoreAuditEventAction.PLUGIN_DISABLED, "middleware-core", new PluginDisabledAuditMessage(pluginId));
    }

    @PATCH
    @Path(value="/api/v1/plugins/{plugin_type}/{id}/update")
    @Operation(tags={"Plugins"}, summary="Update plugin by id", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="plugin_type", example="analytics", description="Plugin type", required=true, schema=@Schema(allowableValues={"analytics", "event-manager"})), @Parameter(in=ParameterIn.PATH, name="id", example="middleware-alpr-plugin", description="Plugin ID", required=true)}, requestBody=@RequestBody(content={@Content(mediaType="application/json", schema=@Schema(implementation=PluginVersionDTO.class))}), responses={@ApiResponse(responseCode="202", description="Accepted"), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void updatePlugin(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        String pluginId = ctx.pathParamAsClass("id", String.class).getOrThrow(i -> new ErrorException(BaseError.RequiredParams, "id"));
        VezhaPluginsFacade facade = this.getFacade(ctx);
        PluginVersionDTO pluginVersionDTO = ctx.bodyAsClass(PluginVersionDTO.class);
        this.beanValidator.validateObjectOrThrow(pluginVersionDTO, new Class[0]);
        InstalledPluginInfo installedPluginInfo = facade.getInstalledPluginsInfos().stream().filter(it -> it.getId().equals(pluginId)).findFirst().orElseThrow(() -> new PluginNotFoundInSystemException(pluginId));
        facade.updatePlugin(pluginId, pluginVersionDTO.version(), new InstallationListener[0]);
        ctx.status(202);
        PluginUpdatedAuditMessage message = new PluginUpdatedAuditMessage(pluginId, installedPluginInfo.getVersion().toString(), pluginVersionDTO.version().toString());
        this.auditor.audit(ctx, user, CoreAuditEventAction.PLUGIN_UPDATED, "middleware-core", message);
    }

    private VezhaPluginsFacade getFacade(Context ctx) {
        String pluginTypeValue = ctx.pathParam("plugin_type");
        PluginType pluginType = PluginTypeParser.getByValue(pluginTypeValue).orElseThrow(NotFoundResponse::new);
        return switch (pluginType) {
            case PluginType.ANALYTICS_PLUGIN -> this.analyticsPluginsManagerFacade;
            case PluginType.EVENT_MANAGER_PLUGIN -> this.eventManagerPluginsManagerFacade;
            default -> throw new NotFoundResponse();
        };
    }

    @Inject
    public PluginsController(UsersManager usersManager, Auditor auditor, AnalyticsPluginsManagerFacade analyticsPluginsManagerFacade, EventManagerPluginsManagerFacade eventManagerPluginsManagerFacade, BeanValidator beanValidator) {
        this.usersManager = usersManager;
        this.auditor = auditor;
        this.analyticsPluginsManagerFacade = analyticsPluginsManagerFacade;
        this.eventManagerPluginsManagerFacade = eventManagerPluginsManagerFacade;
        this.beanValidator = beanValidator;
    }
}

