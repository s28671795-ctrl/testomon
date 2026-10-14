/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.protocol.controllers.api.v2;

import com.google.inject.Inject;
import com.incoresoft.Utils.ApiParams;
import com.incoresoft.middleware.http.AppRole;
import com.incoresoft.middleware.http.controllers.Controller;
import com.incoresoft.middleware.model.user.UsersManager;
import com.incoresoft.plugin.commons.dto.ErrorDTO;
import com.incoresoft.plugin.commons.utils.UserUtils;
import com.incoresoft.plugin.commons.validation.BeanValidator;
import com.incoresoft.vezha.axisstationpro.AxisStationProSettings;
import com.incoresoft.vezha.axisstationpro.AxisStationProSettingsManager;
import com.incoresoft.vezha.dto.axisstationpro.AxisStationProSettingsDTO;
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
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@OpenAPIDefinition(info=@Info(title="Middleware-Core", description="Middleware-Core HTTP API"))
public class AxisStationProSettingsController
implements Controller {
    private static final String API_PATH = "/api/v1/axis_station_pro_settings";
    private final UsersManager usersManager;
    private final AxisStationProSettingsManager axisStationProSettingsManager;
    private final BeanValidator validator;

    @Override
    @Inject
    public void init(Javalin javalin) {
        javalin.routes(() -> ApiBuilder.path(API_PATH, () -> {
            ApiBuilder.get(this::getSettings, AppRole.LOGGED_IN, AppRole.API_CALL);
            ApiBuilder.post(this::saveSettings, AppRole.LOGGED_IN, AppRole.API_CALL);
        }));
    }

    @GET
    @Path(value="/api/v1/axis_station_pro_settings")
    @Operation(tags={"Axis Station Pro Settings"}, summary="Get Axis Station Pro Settings", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, responses={@ApiResponse(responseCode="200", description="Successful operation", content={@Content(mediaType="application/json", schema=@Schema(implementation=AxisStationProSettingsDTO.class))}), @ApiResponse(responseCode="400", description="Bad request", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="500", description="Internal server error", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))})})
    public void getSettings(@Parameter(hidden=true) Context context) {
        UserUtils.requireRoot(this.usersManager.getUser(context));
        context.json(new AxisStationProSettingsDTO(this.axisStationProSettingsManager.getSettings()));
    }

    @POST
    @Path(value="/api/v1/axis_station_pro_settings")
    @Operation(tags={"Axis Station Pro Settings"}, summary="Save Axis Station Pro Settings", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, requestBody=@RequestBody(required=true, content={@Content(mediaType="application/json", schema=@Schema(implementation=AxisStationProSettingsDTO.class))}), responses={@ApiResponse(responseCode="204", description="Successful operation"), @ApiResponse(responseCode="400", description="Bad request", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="500", description="Internal server error", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))})})
    public void saveSettings(@Parameter(hidden=true) Context context) {
        UserUtils.requireRoot(this.usersManager.getUser(context));
        AxisStationProSettingsDTO dto = new ApiParams(context).getBodyAsClass(AxisStationProSettingsDTO.class);
        this.validator.validateObjectOrThrow(dto, new Class[0]);
        this.axisStationProSettingsManager.saveSettings(new AxisStationProSettings(dto));
    }

    @Inject
    public AxisStationProSettingsController(UsersManager usersManager, AxisStationProSettingsManager axisStationProSettingsManager, BeanValidator validator) {
        this.usersManager = usersManager;
        this.axisStationProSettingsManager = axisStationProSettingsManager;
        this.validator = validator;
    }
}

