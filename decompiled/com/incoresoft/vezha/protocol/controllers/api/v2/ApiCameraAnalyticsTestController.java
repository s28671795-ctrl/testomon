/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.protocol.controllers.api.v2;

import com.alibaba.fastjson.JSON;
import com.google.inject.Inject;
import com.incoresoft.middleware.http.AppRole;
import com.incoresoft.middleware.http.controllers.Controller;
import com.incoresoft.vezha.analytics.cameraanalytics.InitializationRequestReceiver;
import com.incoresoft.vezha.dto.servers.cameraanalytics.InitializationDTO;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@OpenAPIDefinition(info=@Info(title="Middleware-Core", description="Middleware-Core HTTP API", version="v2"))
public class ApiCameraAnalyticsTestController
implements Controller {
    private static final String API_PATH = "/api/v2/camera_analytics_test";
    private final InitializationRequestReceiver initializationRequestReceiver;

    @Override
    @Inject
    public void init(Javalin app) {
        app.routes(() -> app.post(API_PATH, this::tryInitialize, AppRole.LOGGED_IN, AppRole.API_CALL));
    }

    @POST
    @Path(value="/api/v2/camera_analytics_test")
    @Operation(tags={"Camera Analytics"}, summary="Request to test canera analytics initialization", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, requestBody=@RequestBody(required=true, content={@Content(mediaType="application/json", schema=@Schema(implementation=InitializationDTO.class))}), responses={@ApiResponse(responseCode="204", description="Successful operation"), @ApiResponse(responseCode="400", description="Bad Request"), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="500", description="Internal Server Error")})
    public void tryInitialize(Context ctx) {
        InitializationDTO initializationDTO = JSON.parseObject(ctx.body(), InitializationDTO.class);
        this.initializationRequestReceiver.handleInitializationRequest(initializationDTO);
        ctx.status(204);
    }

    @Inject
    public ApiCameraAnalyticsTestController(InitializationRequestReceiver initializationRequestReceiver) {
        this.initializationRequestReceiver = initializationRequestReceiver;
    }
}

