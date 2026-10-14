/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.protocol.controllers.api.v2;

import com.google.inject.Inject;
import com.incoresoft.dto.system.CombinedDiskSpaceInfoDTO;
import com.incoresoft.dto.system.DiskSpaceInfoDTO;
import com.incoresoft.middleware.http.AppRole;
import com.incoresoft.middleware.http.controllers.Controller;
import com.incoresoft.plugin.commons.http.answers.ErrorAnswer;
import com.incoresoft.system.disk.DiskSpaceInfo;
import com.incoresoft.system.disk.DiskSpaceManager;
import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;
import io.javalin.http.Context;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@OpenAPIDefinition(info=@Info(title="Middleware-Core", description="Middleware-Core HTTP API", version="v2"))
public class ApiSystemSpaceControllerV2
implements Controller {
    private static final Logger log = LoggerFactory.getLogger(ApiSystemSpaceControllerV2.class);
    private static final String API_PATH = "/api/v2/system_space";
    private final DiskSpaceManager diskSpaceManager;

    @Override
    @Inject
    public void init(Javalin app) {
        app.routes(() -> ApiBuilder.path(API_PATH, () -> ApiBuilder.get(this::checkSystemSpace, AppRole.LOGGED_IN, AppRole.API_CALL)));
    }

    @GET
    @Path(value="/api/v2/system_space")
    @Operation(tags={"System space"}, summary="Check system and storage space", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, responses={@ApiResponse(responseCode="200", description="Successful operation", content={@Content(mediaType="application/json", schema=@Schema(implementation=CombinedDiskSpaceInfoDTO.class))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void checkSystemSpace(@Parameter(hidden=true) Context context) {
        DiskSpaceInfo systemDiskSpaceInfo = this.diskSpaceManager.getDiskSpace(System.getProperty("user.dir"));
        DiskSpaceInfo storageDiskSpaceInfo = this.diskSpaceManager.getStorageDiskSpace();
        context.json(new CombinedDiskSpaceInfoDTO(new DiskSpaceInfoDTO(systemDiskSpaceInfo), new DiskSpaceInfoDTO(storageDiskSpaceInfo)));
    }

    @Inject
    public ApiSystemSpaceControllerV2(DiskSpaceManager diskSpaceManager) {
        this.diskSpaceManager = diskSpaceManager;
    }
}

