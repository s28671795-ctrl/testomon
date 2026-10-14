/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.protocol.controllers.api.v2;

import com.google.inject.Inject;
import com.incoresoft.config.ConfigConnectionsValidator;
import com.incoresoft.core.ApplicationReloadManager;
import com.incoresoft.dto.config.SetupConfigDTO;
import com.incoresoft.dto.config.SetupDatabaseConfigDTO;
import com.incoresoft.dto.config.storage.SetupStorageConfigDTO;
import com.incoresoft.middleware.http.AppRole;
import com.incoresoft.middleware.http.controllers.Controller;
import com.incoresoft.plugin.commons.dto.ErrorDTO;
import com.incoresoft.plugin.commons.http.answers.ErrorAnswer;
import com.incoresoft.plugin.commons.validation.BeanValidator;
import com.incoresoft.system.IpAddressesManager;
import com.incoresoft.vezha.config.VezhaSetupManager;
import com.incoresoft.vezha.dto.config.IpAddressesDTO;
import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;
import io.javalin.http.Context;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@OpenAPIDefinition(info=@Info(title="Middleware-Core", description="Middleware-Core HTTP API", version="v2"))
public class SetupController
implements Controller {
    private static final Logger log = LoggerFactory.getLogger(SetupController.class);
    private static final String API_PATH = "/api/v1/setup";
    private final BeanValidator validator;
    private final ConfigConnectionsValidator configConnectionsValidator;
    private final VezhaSetupManager setupManager;
    private final ApplicationReloadManager reloadManager;
    private final IpAddressesManager ipAddressesManager;

    @Override
    @Inject
    public void init(Javalin app) {
        app.routes(() -> ApiBuilder.path(API_PATH, () -> {
            ApiBuilder.patch("test_db", this::testDbConnection, AppRole.ANYONE);
            ApiBuilder.patch("test_storage", this::testStorageConnection, AppRole.ANYONE);
            ApiBuilder.post(this::saveSystemSettings, AppRole.ANYONE);
            ApiBuilder.get("ip_addresses", this::getIpAddresses, AppRole.ANYONE);
        }));
    }

    @PATCH
    @Path(value="/api/v1/setup/test_db")
    @Operation(tags={"First Launch Config"}, summary="Test database connection", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, requestBody=@RequestBody(required=true, content={@Content(mediaType="application/json", schema=@Schema(implementation=SetupDatabaseConfigDTO.class))}), responses={@ApiResponse(responseCode="204", description="Successful operation"), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void testDbConnection(@Parameter(hidden=true) Context ctx) {
        SetupDatabaseConfigDTO dto = ctx.bodyAsClass(SetupDatabaseConfigDTO.class);
        this.validator.validateObjectOrThrow(dto, new Class[0]);
        this.configConnectionsValidator.testDatabaseConnection(dto);
        ctx.status(204);
    }

    @PATCH
    @Path(value="/api/v1/setup/test_storage")
    @Operation(tags={"First Launch Config"}, summary="Test storage connection", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, requestBody=@RequestBody(required=true, content={@Content(mediaType="application/json", schema=@Schema(implementation=SetupStorageConfigDTO.class))}), responses={@ApiResponse(responseCode="204", description="Successful operation"), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void testStorageConnection(@Parameter(hidden=true) Context ctx) {
        SetupStorageConfigDTO dto = ctx.bodyAsClass(SetupStorageConfigDTO.class);
        this.validator.validateObjectOrThrow(dto, new Class[0]);
        this.configConnectionsValidator.testStorageConnection(dto);
        ctx.status(204);
    }

    @POST
    @Path(value="/api/v1/setup")
    @Operation(tags={"First Launch Config"}, summary="Update system settings", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, requestBody=@RequestBody(required=true, content={@Content(mediaType="application/json", schema=@Schema(implementation=SetupConfigDTO.class))}), responses={@ApiResponse(responseCode="204", description="Successful operation"), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void saveSystemSettings(@Parameter(hidden=true) Context ctx) {
        SetupConfigDTO dto = ctx.bodyAsClass(SetupConfigDTO.class);
        this.validator.validateObjectOrThrow(dto, new Class[0]);
        this.setupManager.saveSetupConfiguration(dto);
        ctx.status(204);
        this.reloadManager.reload();
    }

    @GET
    @Path(value="/api/v1/setup/ip_addresses")
    @Operation(tags={"First Launch Config"}, summary="Get IPv4 addresses", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", schema=@Schema(implementation=IpAddressesDTO.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))}), @ApiResponse(responseCode="500", description="Internal server error", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))})})
    public void getIpAddresses(@Parameter(hidden=true) Context ctx) {
        IpAddressesDTO result = new IpAddressesDTO(this.ipAddressesManager.getIPv4Addresses());
        ctx.json(result);
    }

    @Inject
    public SetupController(BeanValidator validator, ConfigConnectionsValidator configConnectionsValidator, VezhaSetupManager setupManager, ApplicationReloadManager reloadManager, IpAddressesManager ipAddressesManager) {
        this.validator = validator;
        this.configConnectionsValidator = configConnectionsValidator;
        this.setupManager = setupManager;
        this.reloadManager = reloadManager;
        this.ipAddressesManager = ipAddressesManager;
    }
}

