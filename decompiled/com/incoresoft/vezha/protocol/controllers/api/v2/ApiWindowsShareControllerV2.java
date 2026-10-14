/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.protocol.controllers.api.v2;

import com.google.inject.Inject;
import com.incoresoft.Utils.ApiParams;
import com.incoresoft.middleware.http.AppRole;
import com.incoresoft.middleware.http.controllers.Controller;
import com.incoresoft.middleware.http.exceptions.ErrorException;
import com.incoresoft.middleware.windowsshare.WindowsShareConfig;
import com.incoresoft.plugin.commons.http.answers.ErrorAnswer;
import com.incoresoft.plugin.commons.http.error.BaseError;
import com.incoresoft.plugin.commons.validation.BeanValidator;
import com.incoresoft.vezha.dto.windowsshare.WindowsSharedFolderConfigDTO;
import com.incoresoft.vezha.windowsshare.WindowsShareConfigManager;
import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;
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
import java.util.Optional;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

@OpenAPIDefinition(info=@Info(title="Middleware-Core", description="Middleware-Core HTTP API", version="v2"))
public class ApiWindowsShareControllerV2
implements Controller {
    private final String API_PATH = "/api/v2/windows_share";
    private final WindowsShareConfigManager manager;
    private final BeanValidator validator;

    @Override
    @Inject
    public void init(Javalin javalin) {
        javalin.routes(() -> ApiBuilder.path("/api/v2/windows_share", () -> {
            ApiBuilder.get(this::getConfigs, AppRole.LOGGED_IN, AppRole.API_CALL);
            ApiBuilder.post(this::addConfig, AppRole.LOGGED_IN, AppRole.API_CALL);
            ApiBuilder.path("{id}", () -> {
                ApiBuilder.get(this::getConfig, AppRole.LOGGED_IN, AppRole.API_CALL);
                ApiBuilder.put(this::editConfig, AppRole.LOGGED_IN, AppRole.API_CALL);
                ApiBuilder.delete(this::deleteConfig, AppRole.LOGGED_IN, AppRole.API_CALL);
            });
        }));
    }

    @GET
    @Path(value="/api/v2/windows_share")
    @Operation(tags={"SMB protocol"}, summary="Retrieves list of all saved windows shared folder configurations.", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", array=@ArraySchema(arraySchema=@Schema(description="List of configurations"), schema=@Schema(implementation=WindowsSharedFolderConfigDTO.class)))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden")})
    public void getConfigs(@Parameter(hidden=true) Context ctx) {
        ctx.json(this.manager.getAll().stream().map(WindowsSharedFolderConfigDTO::new).peek(WindowsSharedFolderConfigDTO::hidePassword).toList());
    }

    @GET
    @Path(value="/api/v2/windows_share/{id}")
    @Operation(tags={"SMB protocol"}, summary="Get configuration by ID", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="id", example="1", description="Configuration ID", required=true)}, responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", schema=@Schema(implementation=WindowsSharedFolderConfigDTO.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden")})
    public void getConfig(@Parameter(hidden=true) Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).getOrThrow(e -> new ErrorException(BaseError.RequiredParams, "id"));
        WindowsSharedFolderConfigDTO dto = Optional.of(id).filter(this.manager::exists).map(this.manager::get).map(WindowsSharedFolderConfigDTO::new).orElseThrow(NotFoundResponse::new);
        dto.hidePassword();
        ctx.json(dto);
    }

    @POST
    @Path(value="/api/v2/windows_share")
    @Operation(tags={"SMB protocol"}, summary="Add new configuration.", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, requestBody=@RequestBody(required=true, content={@Content(mediaType="application/json", schema=@Schema(implementation=WindowsSharedFolderConfigDTO.class))}), responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", schema=@Schema(implementation=WindowsSharedFolderConfigDTO.class))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void addConfig(@Parameter(hidden=true) Context ctx) {
        this.addOrUpdateConfig(ctx, false);
    }

    @PUT
    @Path(value="/api/v2/windows_share/{id}")
    @Operation(tags={"SMB protocol"}, summary="Update existing configuration", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="id", description="Id of config to be updated.")}, requestBody=@RequestBody(required=true, content={@Content(mediaType="application/json", schema=@Schema(implementation=WindowsSharedFolderConfigDTO.class))}), responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", schema=@Schema(implementation=WindowsSharedFolderConfigDTO.class))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void editConfig(@Parameter(hidden=true) Context ctx) {
        this.addOrUpdateConfig(ctx, true);
    }

    private void addOrUpdateConfig(Context ctx, boolean update) {
        WindowsSharedFolderConfigDTO dto = new ApiParams(ctx).getBodyAsClass(WindowsSharedFolderConfigDTO.class);
        if (update) {
            int id = ctx.pathParamAsClass("id", Integer.class).getOrThrow(e -> new ErrorException(BaseError.RequiredParams, "id"));
            Optional.of(id).filter(this.manager::exists).ifPresentOrElse(dto::setId, NotFoundResponse::new);
        }
        this.validator.validateObjectOrThrow(dto, new Class[0]);
        WindowsShareConfig windowsShareConfig = dto.toModel();
        windowsShareConfig = update ? this.manager.update(windowsShareConfig) : this.manager.add(windowsShareConfig);
        ctx.json(new WindowsSharedFolderConfigDTO(windowsShareConfig));
    }

    @DELETE
    @Path(value="/api/v2/windows_share/{id}")
    @Operation(tags={"SMB protocol"}, summary="Delete configuration by ID", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="id", example="1", description="Client ID", required=true)}, responses={@ApiResponse(responseCode="204", description="Successful operation"), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void deleteConfig(@Parameter(hidden=true) Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).getOrThrow(e -> new ErrorException(BaseError.RequiredParams, "id"));
        Optional.of(id).filter(this.manager::exists).ifPresentOrElse(this.manager::delete, NotFoundResponse::new);
        ctx.status(204);
    }

    @Inject
    public ApiWindowsShareControllerV2(WindowsShareConfigManager manager, BeanValidator validator) {
        this.manager = manager;
        this.validator = validator;
    }
}

