/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.protocol.controllers.api.v2;

import com.google.inject.Inject;
import com.incoresoft.middleware.http.AppRole;
import com.incoresoft.middleware.http.controllers.Controller;
import com.incoresoft.vezha.dto.timezones.TimezonesDTO;
import com.incoresoft.vezha.models.timezones.TimeZoneManager;
import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.Optional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@OpenAPIDefinition(info=@Info(title="Middleware-Core", description="Middleware-Core HTTP API"))
public class ApiTimeZoneControllerV2
implements Controller {
    private static final String API_PATH = "/api/v2/timezones";
    private final TimeZoneManager timeZoneManager;

    @Override
    @Inject
    public void init(Javalin app) {
        app.routes(() -> ApiBuilder.path(API_PATH, () -> ApiBuilder.get(this::getTimeZones, AppRole.ANYONE)));
    }

    @GET
    @Path(value="/api/v2/timezones")
    @Operation(tags={"Timezones"}, summary="Get list of timezones", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=TimezonesDTO.class)))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden")})
    public void getTimeZones(@Parameter(hidden=true) Context ctx) {
        Optional.ofNullable(this.timeZoneManager.getTimezones()).ifPresentOrElse(ctx::json, NotFoundResponse::new);
    }

    @Inject
    public ApiTimeZoneControllerV2(TimeZoneManager timeZoneManager) {
        this.timeZoneManager = timeZoneManager;
    }
}

