/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.protocol.controllers.api.v2;

import com.alibaba.fastjson.JSON;
import com.google.inject.Inject;
import com.incoresoft.Utils.ApiParams;
import com.incoresoft.dto.sounds.SoundsDTO;
import com.incoresoft.dto.sounds.UploadSoundFileDTO;
import com.incoresoft.middleware.audit.Auditor;
import com.incoresoft.middleware.http.AppRole;
import com.incoresoft.middleware.http.controllers.Controller;
import com.incoresoft.middleware.http.exceptions.ErrorException;
import com.incoresoft.middleware.model.permission.CorePermissions;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.model.user.UsersManager;
import com.incoresoft.middleware.notifications.NotificationsManager;
import com.incoresoft.middleware.notifications.NotificationsPanel;
import com.incoresoft.notifications.sounds.SoundItem;
import com.incoresoft.notifications.sounds.SoundsManager;
import com.incoresoft.plugin.commons.http.answers.ErrorAnswer;
import com.incoresoft.plugin.commons.http.error.BaseError;
import com.incoresoft.plugin.commons.validation.BeanValidator;
import com.incoresoft.vezha.audit.VezhaAuditEventAction;
import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import io.javalin.http.UploadedFile;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@OpenAPIDefinition(info=@Info(title="Middleware-Core", description="Middleware-Core HTTP API"))
public class ApiSoundsControllerV2
implements Controller {
    private static final Logger log = LoggerFactory.getLogger(ApiSoundsControllerV2.class);
    private static final String API_PATH = "/api/v2/sounds";
    private final UsersManager usersManager;
    private final SoundsManager soundsManager;
    private final Auditor auditor;
    private final NotificationsPanel notificationsPanel;
    private final BeanValidator validator;

    @Override
    @Inject
    public void init(Javalin app) {
        app.routes(() -> ApiBuilder.path(API_PATH, () -> {
            ApiBuilder.get(this::getSounds, AppRole.LOGGED_IN, AppRole.API_CALL);
            ApiBuilder.post(this::saveSounds, AppRole.LOGGED_IN, AppRole.API_CALL, CorePermissions.EditNotificationSounds);
            ApiBuilder.post("upload", this::uploadSoundFile, AppRole.LOGGED_IN, AppRole.API_CALL, CorePermissions.EditNotificationSounds);
            ApiBuilder.path("{id}", () -> {
                ApiBuilder.post("toggle", this::toggleSound, AppRole.LOGGED_IN, AppRole.API_CALL, CorePermissions.EditNotificationSounds);
                ApiBuilder.patch("reset", this::resetSound, AppRole.LOGGED_IN, AppRole.API_CALL, CorePermissions.EditNotificationSounds);
            });
        }));
    }

    @GET
    @Path(value="/api/v2/sounds")
    @Operation(tags={"Sounds"}, summary="Get list of plugins sounds.", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", array=@ArraySchema(arraySchema=@Schema(description="List of users"), schema=@Schema(implementation=SoundsDTO.class)))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void getSounds(@Parameter(hidden=true) Context ctx) {
        ctx.json(this.soundsManager.getAll().stream().map(it -> new SoundsDTO((SoundItem)it, this.getTitle(it.id()))).toList());
    }

    @POST
    @Path(value="/api/v2/sounds")
    @Operation(tags={"Sounds"}, summary="Save sounds settings", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, requestBody=@RequestBody(required=true, content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=SoundsDTO.class)))}), responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", schema=@Schema(implementation=SoundsDTO.class))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void saveSounds(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        List<SoundItem> soundItems = JSON.parseArray(ctx.body(), SoundsDTO.class).stream().map(SoundItem::new).toList();
        this.soundsManager.updateAll(soundItems);
        ctx.status(204);
        this.auditor.audit(ctx, user, VezhaAuditEventAction.SOUNDS_UPDATED, "middleware-core");
    }

    @POST
    @Path(value="/api/v2/sounds/upload")
    @Operation(tags={"Sounds"}, summary="Upload sounds file", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, requestBody=@RequestBody(content={@Content(mediaType="multipart/form-data", encoding={@Encoding(name="file", contentType="application/octet-stream")}, schema=@Schema(implementation=UploadSoundFileDTO.class))}), responses={@ApiResponse(responseCode="204", description="Successful operation"), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found Sounds Item")})
    public void uploadSoundFile(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        UploadSoundFileDTO uploadSoundFileDTO = new ApiParams(ctx).getFormParamsAsClass(UploadSoundFileDTO.class);
        this.validator.validateObjectOrThrow(uploadSoundFileDTO, new Class[0]);
        Optional.ofNullable(this.soundsManager.get(uploadSoundFileDTO.getId())).orElseThrow(NotFoundResponse::new);
        UploadedFile uploadedVideo = ctx.uploadedFile("file");
        if (uploadedVideo == null) {
            throw new ErrorException(BaseError.RequiredParams, "file");
        }
        if (!Objects.equals(uploadedVideo.contentType(), "audio/mpeg")) {
            throw new ErrorException(BaseError.FileFormatError, "file");
        }
        try {
            this.soundsManager.uploadFile(uploadSoundFileDTO.getId(), uploadedVideo.content());
        }
        catch (IOException e) {
            log.error("Could not upload sound file.", e);
            throw new ErrorException(BaseError.FileUploadError, "file");
        }
        ctx.status(204);
        this.auditor.audit(ctx, user, VezhaAuditEventAction.SOUNDS_UPLOADED, "middleware-core");
    }

    @POST
    @Path(value="/api/v2/sounds/{id}/toggle")
    @Operation(tags={"Sounds"}, summary="Toggle sound", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="id", description="Notification panel item id to toggle sound for")}, responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", schema=@Schema(implementation=SoundsDTO.class))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void toggleSound(@Parameter(hidden=true) Context ctx) {
        String id = ctx.pathParamAsClass("id", String.class).getOrThrow(e -> new ErrorException(BaseError.RequiredParams, "id"));
        if (this.soundsManager.get(id).isEmpty()) {
            throw new NotFoundResponse();
        }
        this.soundsManager.toggle(id);
        ctx.json(new SoundsDTO(this.soundsManager.get(id).orElseThrow(), this.getTitle(id)));
    }

    @PATCH
    @Path(value="/api/v2/sounds/{id}/reset")
    @Operation(tags={"Sounds"}, summary="Reset sound", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="id", description="Notification panel item id to reset sound for")}, responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", schema=@Schema(implementation=SoundsDTO.class))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void resetSound(@Parameter(hidden=true) Context ctx) {
        String id = ctx.pathParamAsClass("id", String.class).getOrThrow(e -> new ErrorException(BaseError.RequiredParams, "id"));
        Optional<SoundItem> soundItem = this.soundsManager.get(id);
        if (soundItem.isEmpty()) {
            throw new NotFoundResponse();
        }
        this.soundsManager.resetSoundFile(id);
        ctx.json(new SoundsDTO(this.soundsManager.get(id).orElseThrow(), this.getTitle(id)));
    }

    private String getTitle(String id) {
        return this.notificationsPanel.getNotificationsManagers().stream().filter(it -> it.getId().equals(id)).findFirst().map(NotificationsManager::getTitleLocaleKey).orElse("");
    }

    @Inject
    public ApiSoundsControllerV2(UsersManager usersManager, SoundsManager soundsManager, Auditor auditor, NotificationsPanel notificationsPanel, BeanValidator validator) {
        this.usersManager = usersManager;
        this.soundsManager = soundsManager;
        this.auditor = auditor;
        this.notificationsPanel = notificationsPanel;
        this.validator = validator;
    }
}

