/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.protocol.controllers.api.v2.analytics;

import com.google.inject.Inject;
import com.incoresoft.Utils.ApiParams;
import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.AnalyticsManager;
import com.incoresoft.middleware.audit.Auditor;
import com.incoresoft.middleware.http.AppRole;
import com.incoresoft.middleware.http.controllers.Controller;
import com.incoresoft.middleware.http.exceptions.ErrorException;
import com.incoresoft.middleware.model.permission.CorePermissions;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.plugin.commons.http.error.BaseError;
import com.incoresoft.plugin.commons.utils.UserUtils;
import com.incoresoft.plugin.commons.validation.BeanValidator;
import com.incoresoft.vezha.analytics.BaseAnalytics;
import com.incoresoft.vezha.audit.VezhaAuditEventAction;
import com.incoresoft.vezha.audit.messages.analytics.servers.AnalyticsServerRebootActionAuditMessage;
import com.incoresoft.vezha.audit.messages.analytics.servers.AnalyticsServerSettingsUpdatedAuditMessage;
import com.incoresoft.vezha.audit.messages.analytics.servers.AnalyticsServiceRestartActionAuditMessage;
import com.incoresoft.vezha.dto.servers.FrontServerDTO;
import com.incoresoft.vezha.dto.servers.LogFileContentDTO;
import com.incoresoft.vezha.dto.servers.LogFileDTO;
import com.incoresoft.vezha.dto.servers.RunningAnalyticsFrontDTO;
import com.incoresoft.vezha.dto.servers.ServerSettingsDTO;
import com.incoresoft.vezha.dto.servers.servercontroller.ServerAnalyticsDTO;
import com.incoresoft.vezha.models.servers.ServerManager;
import com.incoresoft.vezha.models.servers.ServerSettings;
import com.incoresoft.vezha.models.users.UsersManagerImpl;
import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;
import io.javalin.http.Context;
import io.javalin.http.InternalServerErrorResponse;
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
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@OpenAPIDefinition(info=@Info(title="Middleware-Core", description="Middleware-Core HTTP API", version="v2"))
public class ApiAnalyticsServersControllerV2
implements Controller {
    private static final int DEFAULT_LINES_FROM_FILE_END_COUNT = 1000;
    private static final String API_PATH = "/api/v2/servers";
    private final ServerManager serverManager;
    private final AnalyticsManager analyticsManager;
    private final UsersManagerImpl usersManager;
    private final Auditor auditor;
    private final BeanValidator validator;

    @Override
    @Inject
    public void init(Javalin app) {
        app.routes(() -> ApiBuilder.path(API_PATH, () -> {
            ApiBuilder.get(this::getServers, AppRole.LOGGED_IN, AppRole.API_CALL, CorePermissions.ViewServers);
            ApiBuilder.get("analytics", this::getAnalytics, AppRole.LOGGED_IN, AppRole.API_CALL, CorePermissions.ViewServers);
            ApiBuilder.path("{id}", () -> {
                ApiBuilder.post(this::updateServerInfo, AppRole.LOGGED_IN, AppRole.API_CALL, CorePermissions.ManageAnalyticsServers);
                ApiBuilder.post("restart_service", this::restartService, AppRole.LOGGED_IN, AppRole.API_CALL);
                ApiBuilder.post("reboot_machine", this::rebootMachine, AppRole.LOGGED_IN, AppRole.API_CALL);
                ApiBuilder.path("log_files", () -> {
                    ApiBuilder.get(this::getLogFiles, AppRole.LOGGED_IN, AppRole.API_CALL, CorePermissions.ViewServers);
                    ApiBuilder.get("read", this::getLogFileContent, AppRole.LOGGED_IN, AppRole.API_CALL, CorePermissions.ViewServers);
                });
            });
        }));
    }

    @POST
    @Path(value="/api/v2/servers/{id}")
    @Operation(tags={"Servers"}, summary="Updates settings of specified analytics server.", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="id", description="Id of server to update.", example="1")}, requestBody=@RequestBody(required=true, content={@Content(mediaType="application/json", schema=@Schema(implementation=ServerSettingsDTO.class))}), responses={@ApiResponse(responseCode="204", description="Successful operation"), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden")})
    public void updateServerInfo(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        String serverId = ctx.pathParamAsClass("id", String.class).check(Objects::nonNull, "id").getOrThrow(e -> new ErrorException(BaseError.RequiredParams, "id"));
        FrontServerDTO old = this.serverManager.getServerDTO(serverId).orElseThrow(NotFoundResponse::new);
        ServerSettingsDTO dto = new ApiParams(ctx).getBodyAsClass(ServerSettingsDTO.class);
        this.validator.validateObjectOrThrow(dto, new Class[0]);
        FrontServerDTO updated = this.serverManager.updateServer(new ServerSettings(serverId, dto));
        ctx.status(204);
        if (dto.getExternalInterface() != null) {
            AnalyticsServerSettingsUpdatedAuditMessage message = new AnalyticsServerSettingsUpdatedAuditMessage(old, updated);
            this.auditor.audit(ctx, user, VezhaAuditEventAction.ANALYTICS_SERVER_SETTINGS_UPDATED, "middleware-core", message);
        }
    }

    @GET
    @Path(value="/api/v2/servers")
    @Operation(tags={"Servers"}, summary="Retrieves list of all analytics servers.", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=FrontServerDTO.class)))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden")})
    public void getServers(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        List<FrontServerDTO> servers = this.serverManager.getServersDTO();
        for (FrontServerDTO server : servers) {
            List<RunningAnalyticsFrontDTO> runningAnalytics = server.getRunningAnalytics();
            if (runningAnalytics == null) continue;
            server.setRunningAnalytics(runningAnalytics.stream().filter(it -> Optional.ofNullable(it).map(RunningAnalyticsFrontDTO::getId).map(this.analyticsManager::get).filter(user::hasAccessTo).isPresent()).toList());
        }
        ctx.json(servers);
    }

    @POST
    @Path(value="/api/v2/servers/{id}/restart_service")
    @Operation(tags={"Servers"}, summary="Restarts specified Process Runner service.", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="id", description="Id of server to restart service.", example="1")}, responses={@ApiResponse(responseCode="204", description="Successful operation"), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden")})
    public void restartService(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        UserUtils.requireRoot(user);
        String id = ctx.pathParamAsClass("id", String.class).getOrThrow(e -> new ErrorException(BaseError.RequiredParams, "id"));
        FrontServerDTO server = this.serverManager.getServerDTO(id).orElseThrow(NotFoundException::new);
        this.serverManager.restartServer(id);
        ctx.status(204);
        this.auditor.audit(ctx, user, VezhaAuditEventAction.ANALYTICS_SERVICE_RESTARTED, "middleware-core", new AnalyticsServiceRestartActionAuditMessage(server));
    }

    @POST
    @Path(value="/api/v2/servers/{id}/reboot_machine")
    @Operation(tags={"Servers"}, summary="Reboots specified analytics server.", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="id", description="Id of server to reboot.", example="1")}, responses={@ApiResponse(responseCode="204", description="Successful operation"), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden")})
    public void rebootMachine(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        UserUtils.requireRoot(user);
        String id = ctx.pathParamAsClass("id", String.class).getOrThrow(e -> new ErrorException(BaseError.RequiredParams, "id"));
        FrontServerDTO server = this.serverManager.getServerDTO(id).orElseThrow(NotFoundException::new);
        this.serverManager.rebootMachine(id);
        ctx.status(204);
        this.auditor.audit(ctx, user, VezhaAuditEventAction.ANALYTICS_SERVER_REBOOTED, "middleware-core", new AnalyticsServerRebootActionAuditMessage(server));
    }

    @GET
    @Path(value="/api/v2/servers/{id}/log_files")
    @Operation(tags={"Servers"}, summary="Retrieves list of log files for specified analytics server.", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="id", example="1", description="Server ID", required=true)}, responses={@ApiResponse(responseCode="200", description="Successful operation", content={@Content(mediaType="application/json", array=@ArraySchema(arraySchema=@Schema(description="List of log files"), schema=@Schema(implementation=LogFileDTO.class)))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden")})
    public void getLogFiles(@Parameter(hidden=true) Context ctx) {
        String serverId = ctx.pathParamAsClass("id", String.class).getOrThrow(e -> new ErrorException(BaseError.RequiredParams, "id"));
        List<LogFileDTO> result = this.serverManager.getLogFiles(serverId).stream().map(LogFileDTO::new).toList();
        ctx.json(result);
    }

    @GET
    @Path(value="/api/v2/servers/{id}/log_files/read")
    @Operation(tags={"Servers"}, summary="Retrieves contents of specified log file.", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="id", example="1", description="Server ID", required=true), @Parameter(in=ParameterIn.QUERY, name="file", example="log/analytics.log", description="Log file to retrieve", required=true), @Parameter(in=ParameterIn.QUERY, name="lines_from_file_end", example="1000", description="Number of lines to retrieve")}, responses={@ApiResponse(responseCode="200", description="Successful operation", content={@Content(mediaType="application/json", schema=@Schema(implementation=LogFileContentDTO.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden")})
    public void getLogFileContent(@Parameter(hidden=true) Context ctx) {
        String serverId = ctx.pathParamAsClass("id", String.class).getOrThrow(e -> new ErrorException(BaseError.RequiredParams, "id"));
        String fileName = ctx.queryParamAsClass("file", String.class).getOrThrow(e -> new ErrorException(BaseError.RequiredParams, "file"));
        Integer linesFromFileEnd = ctx.queryParamAsClass("lines_from_file_end", Integer.class).getOrDefault(1000);
        Optional.ofNullable(this.serverManager.getLogFileContent(serverId, fileName, linesFromFileEnd)).map(LogFileContentDTO::new).ifPresentOrElse(ctx::json, InternalServerErrorResponse::new);
    }

    public void getAnalytics(@Parameter(hidden=true) Context ctx) {
        List<ServerAnalyticsDTO> result = this.analyticsManager.getAll().stream().sorted(Comparator.comparingInt(Analytics::getId)).map(BaseAnalytics.class::cast).map(ServerAnalyticsDTO::new).toList();
        ctx.json(result);
    }

    @Inject
    public ApiAnalyticsServersControllerV2(ServerManager serverManager, AnalyticsManager analyticsManager, UsersManagerImpl usersManager, Auditor auditor, BeanValidator validator) {
        this.serverManager = serverManager;
        this.analyticsManager = analyticsManager;
        this.usersManager = usersManager;
        this.auditor = auditor;
        this.validator = validator;
    }
}

