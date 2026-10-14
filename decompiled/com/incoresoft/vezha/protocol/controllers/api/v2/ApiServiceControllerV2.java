/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.protocol.controllers.api.v2;

import com.github.zafarkhaja.semver.Version;
import com.google.inject.Inject;
import com.incoresoft.audit.CoreAuditEventAction;
import com.incoresoft.cleaner.CleanerTasksExecutor;
import com.incoresoft.cleaner.exceptions.CleanerIsAlreadyRunning;
import com.incoresoft.core.ApplicationReloadManager;
import com.incoresoft.core.CoreServiceManager;
import com.incoresoft.dto.audit.messages.service.ServiceUpdatedAuditMessage;
import com.incoresoft.dto.plugins.PluginVersionDTO;
import com.incoresoft.dto.services.ServiceUpdateStateDTO;
import com.incoresoft.dto.services.ServiceUpdateStatesDTO;
import com.incoresoft.extensions.ExtensionsManager;
import com.incoresoft.middleware.audit.Auditor;
import com.incoresoft.middleware.http.AppRole;
import com.incoresoft.middleware.http.controllers.Controller;
import com.incoresoft.middleware.model.permission.CorePermissions;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.notifications.NotificationsManager;
import com.incoresoft.middleware.notifications.NotificationsPanel;
import com.incoresoft.middleware.plugin.SystemExtension;
import com.incoresoft.middleware.plugin.VezhaExtension;
import com.incoresoft.notifications.sounds.SoundsManager;
import com.incoresoft.plugin.commons.dto.ErrorDTO;
import com.incoresoft.plugin.commons.utils.OsUtils;
import com.incoresoft.plugin.commons.utils.UserUtils;
import com.incoresoft.services.ServiceUpdateState;
import com.incoresoft.vezha.audit.VezhaAuditEventAction;
import com.incoresoft.vezha.dto.notificationspanel.NotificationsPanelElementDTO;
import com.incoresoft.vezha.locales.AvailableLocalesManager;
import com.incoresoft.vezha.models.users.UsersManagerImpl;
import com.incoresoft.vezha.systemlogs.TextLogsExporter;
import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.InternalServerErrorResponse;
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
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@OpenAPIDefinition(info=@Info(title="Middleware-Core", description="Middleware-Core HTTP API"))
public class ApiServiceControllerV2
implements Controller {
    private static final Logger log = LoggerFactory.getLogger(ApiServiceControllerV2.class);
    private final String API_PATH = "/api/v2/services";
    private final UsersManagerImpl usersManager;
    private final ExtensionsManager extensionsManager;
    private final NotificationsPanel notificationsPanel;
    private final SoundsManager soundsManager;
    private final AvailableLocalesManager availableLocalesManager;
    private final CleanerTasksExecutor cleanerTasksExecutor;
    private final CoreServiceManager coreServiceManager;
    private final ApplicationReloadManager reloadManager;
    private final Auditor auditor;
    private final TextLogsExporter logsExporter;

    @Override
    @Inject
    public void init(Javalin app) {
        app.routes(() -> ApiBuilder.path("/api/v2/services", () -> {
            ApiBuilder.get(this::getVersionsInfo, AppRole.LOGGED_IN);
            ApiBuilder.post("middleware_core", this::update, AppRole.LOGGED_IN, CorePermissions.ManageSystemUpdatesAndPlugins);
            ApiBuilder.post("middleware_core/file", this::updateCoreByFile, AppRole.LOGGED_IN, CorePermissions.ManageSystemUpdatesAndPlugins);
            ApiBuilder.get("notification_panel", this::getNotificationPanelInfo, AppRole.LOGGED_IN);
            ApiBuilder.post("restart_service", this::restartService, AppRole.LOGGED_IN);
            ApiBuilder.post("reboot_machine", this::rebootMachine, AppRole.LOGGED_IN);
            ApiBuilder.patch("cleaner/start", this::startCleaner, AppRole.LOGGED_IN);
            ApiBuilder.get("extensions/installed", this::getExtensionsInfo, AppRole.LOGGED_IN);
            ApiBuilder.get("available_languages", this::getAvailableLocales, AppRole.LOGGED_IN);
            ApiBuilder.get("logs/export", this::exportLogs, AppRole.LOGGED_IN);
        }));
    }

    @GET
    @Path(value="/api/v2/services")
    @Operation(tags={"Service"}, summary="Get version info", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", schema=@Schema(implementation=ServiceUpdateStatesDTO.class))}), @ApiResponse(responseCode="400", description="Bad request", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="500", description="Internal Server Error", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))})})
    public void getVersionsInfo(@Parameter(hidden=true) Context ctx) {
        ctx.json(new ServiceUpdateStatesDTO(List.of(new ServiceUpdateStateDTO(this.coreServiceManager.getServiceUpdateState()))));
    }

    @POST
    @Path(value="/api/v2/services/middleware_core")
    @Operation(tags={"Service"}, summary="Update core version", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.QUERY, name="version", description="Version", schema=@Schema(implementation=String.class, example="23.3.535"))}, responses={@ApiResponse(responseCode="202", description="Accepted"), @ApiResponse(responseCode="400", description="Bad request", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="500", description="Internal Server Error", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))})})
    public void update(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        UserUtils.requireRoot(user);
        Version version = Optional.ofNullable(ctx.queryParam("version")).map(Version::valueOf).orElseThrow();
        ServiceUpdateState serviceUpdateState = this.coreServiceManager.getServiceUpdateState();
        this.coreServiceManager.updateCore(version);
        ctx.status(202);
        this.auditor.audit(ctx, user, CoreAuditEventAction.SYSTEM_UPDATED, "middleware-core", new ServiceUpdatedAuditMessage(serviceUpdateState.getId(), version.toString(), serviceUpdateState.getCurrentVersion().toString()));
    }

    @POST
    @Path(value="/api/v2/services/middleware_core/file")
    @Operation(tags={"Service"}, summary="Update core version", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, requestBody=@RequestBody(content={@Content(mediaType="application/octet-stream", schema=@Schema(type="string", format="binary"))}), responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", schema=@Schema(implementation=PluginVersionDTO.class))}), @ApiResponse(responseCode="400", description="Bad request", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="500", description="Internal Server Error", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))})})
    public void updateCoreByFile(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        UserUtils.requireRoot(user);
        long contentLength = ctx.req().getContentLengthLong();
        if (contentLength <= 0L) {
            throw new BadRequestResponse("Uploaded file is empty");
        }
        ServiceUpdateState serviceUpdateState = this.coreServiceManager.getServiceUpdateState();
        try (InputStream inputStream2 = ctx.bodyInputStream();){
            CompletableFuture<Version> future = this.coreServiceManager.updateCore(inputStream2, contentLength);
            Version newVersion = future.get();
            this.auditor.audit(ctx, user, CoreAuditEventAction.SYSTEM_UPDATED, "middleware-core", new ServiceUpdatedAuditMessage(serviceUpdateState.getId(), newVersion.toString(), serviceUpdateState.getCurrentVersion().toString()));
            ctx.json(new PluginVersionDTO(newVersion));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GET
    @Path(value="/api/v2/services/notification_panel")
    @Operation(tags={"Service"}, summary="Retrieves elements of notification panel", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", array=@ArraySchema(arraySchema=@Schema(description="List of clients"), schema=@Schema(implementation=NotificationsPanelElementDTO.class)))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden")})
    public void getNotificationPanelInfo(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        List<NotificationsPanelElementDTO> notificationsPanelElements = this.notificationsPanel.getNotificationsManagers().stream().filter(Objects::nonNull).filter(it -> user.hasAllPermissions(it.requiredPermissions())).map(it -> new NotificationsPanelElementDTO((NotificationsManager<?>)it, this.soundsManager)).toList();
        ctx.json(notificationsPanelElements);
    }

    @POST
    @Path(value="/api/v2/services/reboot_machine")
    @Operation(tags={"Service"}, summary="Reboots this server.", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, responses={@ApiResponse(responseCode="204", description="Successful operation"), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden")})
    public void rebootMachine(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        UserUtils.requireRoot(user);
        CompletableFuture.runAsync(this::rebootMachine, CompletableFuture.delayedExecutor(1L, TimeUnit.SECONDS));
        ctx.status(204);
        this.auditor.audit(ctx, user, VezhaAuditEventAction.MIDDLEWARE_SERVER_REBOOTED, "middleware-core");
    }

    @POST
    @Path(value="/api/v2/services/restart_service")
    @Operation(tags={"Service"}, summary="Restarts Middleware-Core service.", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, responses={@ApiResponse(responseCode="204", description="Successful operation"), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden")})
    public void restartService(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        UserUtils.requireRoot(user);
        this.reloadManager.reload();
        ctx.status(204);
        this.auditor.audit(ctx, user, VezhaAuditEventAction.MIDDLEWARE_SERVICE_RESTARTED, "middleware-core");
    }

    @PATCH
    @Path(value="/api/v2/services/cleaner/start")
    @Operation(tags={"Service"}, summary="Starts saved data cleaner..", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, responses={@ApiResponse(responseCode="204", description="Successful operation"), @ApiResponse(responseCode="400", description="Bad request", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))}), @ApiResponse(responseCode="401", description="Unauthorized", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))}), @ApiResponse(responseCode="403", description="Forbidden", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))})})
    public void startCleaner(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        UserUtils.requireRoot(user);
        if (this.cleanerTasksExecutor.isRunning()) {
            throw new CleanerIsAlreadyRunning();
        }
        this.cleanerTasksExecutor.cleanNow();
        ctx.status(204);
        this.auditor.audit(ctx, user, VezhaAuditEventAction.CLEANER_TRIGGERED, "middleware-core");
    }

    @GET
    @Path(value="/api/v2/services/available_languages")
    @Operation(tags={"Service"}, summary="Retrieves available languages", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", array=@ArraySchema(arraySchema=@Schema(description="List of languages", example="[\"en\", \"uk\"]"), schema=@Schema(implementation=String.class)))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden")})
    public void getAvailableLocales(@Parameter(hidden=true) Context ctx) {
        ctx.json(this.availableLocalesManager.getAvailableLanguages().stream().map(Locale::getLanguage).toList());
    }

    @GET
    @Path(value="/api/v2/services/extensions/installed")
    @Operation(tags={"Service"}, summary="Retrieves extensions info", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", array=@ArraySchema(arraySchema=@Schema(description="List of installed plugins", example="[\"alpr\", \"face\"]"), schema=@Schema(implementation=String.class)))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden")})
    public void getExtensionsInfo(@Parameter(hidden=true) Context ctx) {
        List<String> installedPlugins = this.extensionsManager.getAll(VezhaExtension.class).stream().map(SystemExtension::getPluginName).filter(Objects::nonNull).toList();
        ctx.json(installedPlugins);
    }

    private void rebootMachine() {
        String rebootCommand = "";
        if (OsUtils.getOperatingSystemType() == OsUtils.OSType.Windows) {
            rebootCommand = "shutdown -r -t 0";
        } else if (OsUtils.getOperatingSystemType() == OsUtils.OSType.Linux) {
            rebootCommand = "reboot";
        } else {
            throw new RuntimeException(String.format("Cannot reboot OS \"%s\"", OsUtils.getOperatingSystemType().name()));
        }
        try {
            Runtime.getRuntime().exec(rebootCommand);
        }
        catch (IOException e) {
            throw new InternalServerErrorResponse();
        }
    }

    @GET
    @Path(value="/api/v2/services/logs/export")
    @Operation(tags={"Service"}, summary="Export system logs and list of installed modules in .zip file", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, responses={@ApiResponse(responseCode="200", description="Successful operation", content={@Content(mediaType="application/octet-stream", schema=@Schema(implementation=byte[].class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="500", description="Internal Server Error", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))})})
    public void exportLogs(@Parameter(hidden=true) Context context) {
        User user = this.usersManager.getUser(context);
        UserUtils.requireRoot(user);
        context.result(this.logsExporter.exportLogs()).contentType("application/octet-stream").header("Content-Disposition", "attachment; filename=\"logs.zip\"").status(200);
    }

    @Inject
    public ApiServiceControllerV2(UsersManagerImpl usersManager, ExtensionsManager extensionsManager, NotificationsPanel notificationsPanel, SoundsManager soundsManager, AvailableLocalesManager availableLocalesManager, CleanerTasksExecutor cleanerTasksExecutor, CoreServiceManager coreServiceManager, ApplicationReloadManager reloadManager, Auditor auditor, TextLogsExporter logsExporter) {
        this.usersManager = usersManager;
        this.extensionsManager = extensionsManager;
        this.notificationsPanel = notificationsPanel;
        this.soundsManager = soundsManager;
        this.availableLocalesManager = availableLocalesManager;
        this.cleanerTasksExecutor = cleanerTasksExecutor;
        this.coreServiceManager = coreServiceManager;
        this.reloadManager = reloadManager;
        this.auditor = auditor;
        this.logsExporter = logsExporter;
    }
}

