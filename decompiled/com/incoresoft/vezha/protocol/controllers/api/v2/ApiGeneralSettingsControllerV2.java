/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.protocol.controllers.api.v2;

import com.google.inject.Inject;
import com.incoresoft.Utils.ApiParams;
import com.incoresoft.backup.database.data.DatabaseDataDeletionManager;
import com.incoresoft.core.ApplicationReloadManager;
import com.incoresoft.dto.settings.GeneralSettingsDTO;
import com.incoresoft.middleware.audit.Auditor;
import com.incoresoft.middleware.http.AppRole;
import com.incoresoft.middleware.http.controllers.Controller;
import com.incoresoft.middleware.model.permission.CorePermissions;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.plugin.commons.http.answers.ErrorAnswer;
import com.incoresoft.plugin.commons.utils.UserUtils;
import com.incoresoft.plugin.commons.validation.BeanValidator;
import com.incoresoft.settings.SettingsManager;
import com.incoresoft.vezha.audit.VezhaAuditEventAction;
import com.incoresoft.vezha.config.VezhaConfigManager;
import com.incoresoft.vezha.dto.settings.VmsIntegrationRequestDTO;
import com.incoresoft.vezha.dto.settings.VmsIntegrationResponseDTO;
import com.incoresoft.vezha.models.users.UsersManagerImpl;
import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.util.Base64;
import java.util.Optional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@OpenAPIDefinition(info=@Info(title="Middleware-Core", description="Middleware-Core HTTP API", version="v2"))
public class ApiGeneralSettingsControllerV2
implements Controller {
    private static final String API_PATH = "/api/v2/settings/general";
    private final SettingsManager settingsManager;
    private final UsersManagerImpl usersManager;
    private final Auditor auditor;
    private final VezhaConfigManager configManager;
    private final BeanValidator validator;
    private final ApplicationReloadManager reloadManager;
    private final DatabaseDataDeletionManager databaseDataDeletionManager;

    @Override
    @Inject
    public void init(Javalin app) {
        app.routes(() -> ApiBuilder.path(API_PATH, () -> {
            ApiBuilder.get(this::getGeneralSettings, AppRole.LOGGED_IN, AppRole.API_CALL);
            ApiBuilder.post(this::saveGeneralSettings, AppRole.LOGGED_IN, AppRole.API_CALL, CorePermissions.ManageGeneralSettings, CorePermissions.ManageBranding);
            ApiBuilder.post("enable_vms_integration", this::enableVmsIntegration);
            ApiBuilder.post("disable_vms_integration", this::disableVmsIntegration, AppRole.LOGGED_IN, AppRole.API_CALL);
        }));
    }

    @GET
    @Path(value="/api/v2/settings/general")
    @Operation(tags={"General settings"}, summary="Get general settings", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", schema=@Schema(implementation=GeneralSettingsDTO.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden")})
    public void getGeneralSettings(@Parameter(hidden=true) Context ctx) {
        Optional.ofNullable(this.settingsManager.getGeneralSettings()).ifPresentOrElse(ctx::json, BadRequestResponse::new);
    }

    @POST
    @Path(value="/api/v2/settings/general")
    @Operation(tags={"General settings"}, summary="Save general settings", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, requestBody=@RequestBody(required=true, content={@Content(mediaType="application/json", schema=@Schema(implementation=GeneralSettingsDTO.class))}), responses={@ApiResponse(responseCode="204", description="Successful operation "), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void saveGeneralSettings(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        GeneralSettingsDTO dto = new ApiParams(ctx).getBodyAsClass(GeneralSettingsDTO.class);
        this.settingsManager.saveGeneralSettings(dto);
        ctx.status(204);
        Optional.ofNullable(this.settingsManager.getGeneralSettings()).map(GeneralSettingsDTO::getGoogleMapsApiKey).ifPresent(key -> this.auditor.audit(ctx, user, VezhaAuditEventAction.GOOGLE_MAPS_API_KEY_UPDATED, "middleware-core", key));
    }

    @POST
    @Path(value="/api/v2/settings/general/enable_vms_integration")
    @Operation(tags={"General settings"}, summary="Enable VMS integration", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, requestBody=@RequestBody(required=true, content={@Content(mediaType="application/json", schema=@Schema(implementation=VmsIntegrationRequestDTO.class))}), responses={@ApiResponse(responseCode="200", description="Successful operation", content={@Content(mediaType="application/json", schema=@Schema(implementation=VmsIntegrationRequestDTO.class))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found"), @ApiResponse(responseCode="500", description="Internal Server Error", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))})})
    public void enableVmsIntegration(@Parameter(hidden=true) Context ctx) throws IOException {
        if (this.configManager.isVmsIntegrationEnabled()) {
            throw new BadRequestResponse("VMS integration is already enabled");
        }
        VmsIntegrationRequestDTO dto = new ApiParams(ctx).getBodyAsClass(VmsIntegrationRequestDTO.class);
        this.validator.validateObjectOrThrow(dto, new Class[0]);
        User user = this.usersManager.login(ctx, dto.getUsername(), dto.getPassword());
        if (!user.isRoot()) {
            throw new BadRequestResponse("Only root user can enable VMS integration");
        }
        String apiKey = this.usersManager.issueToken(user, "VMS Integration Token", Duration.ofDays(180L)).getLeft();
        byte[] publicKey = Base64.getDecoder().decode(dto.getPublicKey());
        Files.write(Paths.get("public-auth-key.pem", new String[0]), publicKey, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        this.configManager.enableVmsIntegration();
        ctx.json(new VmsIntegrationResponseDTO(apiKey));
    }

    @POST
    @Path(value="/api/v2/settings/general/disable_vms_integration")
    @Operation(tags={"General settings"}, summary="Disable VMS integration", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.QUERY, name="delete_database_data", description="Flag indicating whether delete all database data", example="false", schema=@Schema(implementation=Boolean.class))}, responses={@ApiResponse(responseCode="204", description="Successful operation"), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found"), @ApiResponse(responseCode="500", description="Internal Server Error", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))})})
    public void disableVmsIntegration(@Parameter(hidden=true) Context context) {
        UserUtils.requireRoot(this.usersManager.getUser(context));
        if (!this.configManager.isVmsIntegrationEnabled()) {
            throw new BadRequestResponse("VMS integration is already disabled");
        }
        boolean deleteDatabaseData = Boolean.parseBoolean(context.queryParam("delete_database_data"));
        this.configManager.disableVmsIntegration();
        if (deleteDatabaseData) {
            this.databaseDataDeletionManager.deleteData();
        }
        context.status(204);
        this.reloadManager.reload();
    }

    @Inject
    public ApiGeneralSettingsControllerV2(SettingsManager settingsManager, UsersManagerImpl usersManager, Auditor auditor, VezhaConfigManager configManager, BeanValidator validator, ApplicationReloadManager reloadManager, DatabaseDataDeletionManager databaseDataDeletionManager) {
        this.settingsManager = settingsManager;
        this.usersManager = usersManager;
        this.auditor = auditor;
        this.configManager = configManager;
        this.validator = validator;
        this.reloadManager = reloadManager;
        this.databaseDataDeletionManager = databaseDataDeletionManager;
    }
}

