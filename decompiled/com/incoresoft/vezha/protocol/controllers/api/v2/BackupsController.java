/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.protocol.controllers.api.v2;

import com.google.inject.Inject;
import com.incoresoft.audit.CoreAuditEventAction;
import com.incoresoft.dto.audit.messages.backups.BackupDeletedAuditMessage;
import com.incoresoft.dto.audit.messages.backups.BackupDownloadedAuditMessage;
import com.incoresoft.dto.plugins.UploadPluginFileDTO;
import com.incoresoft.middleware.audit.Auditor;
import com.incoresoft.middleware.http.AppRole;
import com.incoresoft.middleware.http.controllers.Controller;
import com.incoresoft.middleware.http.exceptions.ErrorException;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.model.user.UsersManager;
import com.incoresoft.plugin.commons.dto.ErrorDTO;
import com.incoresoft.plugin.commons.http.answers.ErrorAnswer;
import com.incoresoft.plugin.commons.http.error.BaseError;
import com.incoresoft.plugin.commons.params.PaginationParams;
import com.incoresoft.plugin.commons.params.StartEndDateParams;
import com.incoresoft.plugin.commons.utils.JavalinFile;
import com.incoresoft.plugin.commons.utils.JavalinUtils;
import com.incoresoft.plugin.commons.utils.QueryParamsParser;
import com.incoresoft.plugin.commons.utils.UserUtils;
import com.incoresoft.plugin.commons.validation.BeanValidator;
import com.incoresoft.vezha.backup.BackupInfo;
import com.incoresoft.vezha.backup.BackupsManager;
import com.incoresoft.vezha.backup.file.BackupFileRestoreInfo;
import com.incoresoft.vezha.backup.ws.message.create.client.CreateBackupMessage;
import com.incoresoft.vezha.dto.backup.BackupFileRestoreInfoDTO;
import com.incoresoft.vezha.dto.backup.BackupInfoDTO;
import com.incoresoft.vezha.dto.backup.BackupInfosPaginationAnswerDTO;
import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;
import io.javalin.http.Context;
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
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import org.apache.commons.lang3.tuple.Pair;

@OpenAPIDefinition(info=@Info(title="Middleware-Core", description="Middleware-Core HTTP API"))
public class BackupsController
implements Controller {
    private static final String API_PATH = "/api/v1/backup";
    private final BackupsManager backupsManager;
    private final UsersManager usersManager;
    private final BeanValidator validator;
    private final QueryParamsParser queryParamsParser;
    private final Auditor auditor;

    @Override
    @Inject
    public void init(Javalin app) {
        app.routes(() -> ApiBuilder.path(API_PATH, () -> {
            ApiBuilder.get(this::getAllBackupInfos, AppRole.LOGGED_IN, AppRole.API_CALL);
            ApiBuilder.patch("prepare_restore", this::prepareRestoreByFile, AppRole.LOGGED_IN, AppRole.API_CALL);
            ApiBuilder.patch("validate_creation", this::validateCreation, AppRole.LOGGED_IN, AppRole.API_CALL);
            ApiBuilder.path("{id}", () -> {
                ApiBuilder.get("download", this::provideBackupFile, AppRole.LOGGED_IN, AppRole.API_CALL);
                ApiBuilder.patch("prepare_restore", this::prepareRestoreById, AppRole.LOGGED_IN, AppRole.API_CALL);
                ApiBuilder.delete(this::deleteBackup, AppRole.LOGGED_IN, AppRole.API_CALL);
            });
        }));
    }

    @PATCH
    @Path(value="/api/v1/backup/validate_creation")
    @Operation(tags={"Backups"}, summary="Validate backup creation", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, requestBody=@RequestBody(content={@Content(mediaType="application/json", schema=@Schema(implementation=CreateBackupMessage.class))}), responses={@ApiResponse(responseCode="204", description="Successful operation "), @ApiResponse(responseCode="400", description="Bad request", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="500", description="Internal Server Error", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))})})
    public void validateCreation(@Parameter(hidden=true) Context context) {
        CreateBackupMessage createBackupMessage = context.bodyAsClass(CreateBackupMessage.class);
        this.validator.validateObjectOrThrow(createBackupMessage, new Class[0]);
        context.status(204);
    }

    @GET
    @Path(value="/api/v1/backup")
    @Operation(tags={"Backups"}, summary="Get all backup infos", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.QUERY, name="offset", example="0", description="Selected offset"), @Parameter(in=ParameterIn.QUERY, name="limit", example="10", description="Maximum number of elements to return"), @Parameter(in=ParameterIn.QUERY, name="start_date", example="1692710893000", description="Start date and time for searching events in the timestamp format", schema=@Schema(implementation=Long.class)), @Parameter(in=ParameterIn.QUERY, name="end_date", example="1692710894000", description="End date and time for searching events in the timestamp format", schema=@Schema(implementation=Long.class))}, responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", schema=@Schema(implementation=BackupInfosPaginationAnswerDTO.class))}), @ApiResponse(responseCode="400", description="Bad request", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="500", description="Internal server error", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))})})
    public void getAllBackupInfos(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        UserUtils.requireRoot(user);
        StartEndDateParams dateParams = this.queryParamsParser.from(ctx, StartEndDateParams.class);
        PaginationParams paginationParams = this.queryParamsParser.from(ctx, PaginationParams.class);
        Pair<Integer, List<BackupInfo>> result = this.backupsManager.getAllInfos(dateParams, paginationParams);
        List<BackupInfoDTO> data = result.getRight().stream().map(BackupInfoDTO::new).toList();
        ctx.json(new BackupInfosPaginationAnswerDTO(data, (long)result.getLeft().intValue(), (int)paginationParams.getLimit().orElse(Integer.MAX_VALUE)));
    }

    @GET
    @Path(value="/api/v1/backup/{id}/download")
    @Operation(tags={"Backups"}, summary="Provide backup file", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="id", example="2", description="Backup id", required=true)}, responses={@ApiResponse(responseCode="200", description="Successful operation", content={@Content(mediaType="application/octet-stream", schema=@Schema(implementation=FileInputStream.class))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found Sounds Item")})
    public void provideBackupFile(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        UserUtils.requireRoot(user);
        int id = ctx.pathParamAsClass("id", Integer.class).getOrThrow(i -> new ErrorException(BaseError.RequiredParams, "id"));
        File backupFile = this.backupsManager.getBackupFile(id);
        JavalinUtils.provideFile(ctx, backupFile);
        this.auditor.audit(ctx, user, CoreAuditEventAction.BACKUP_DOWNLOADED, "middleware-core", new BackupDownloadedAuditMessage(id, backupFile.getName()));
    }

    @DELETE
    @Path(value="/api/v1/backup/{id}")
    @Operation(tags={"Backups"}, summary="Delete backup", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="id", example="2", description="Backup id", required=true)}, responses={@ApiResponse(responseCode="204", description="Successful operation"), @ApiResponse(responseCode="400", description="Bad request", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="500", description="Internal Server Error", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))})})
    public void deleteBackup(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        UserUtils.requireRoot(user);
        int id = ctx.pathParamAsClass("id", Integer.class).getOrThrow(i -> new ErrorException(BaseError.RequiredParams, "id"));
        BackupInfo backupInfo = this.backupsManager.delete(id);
        ctx.status(204);
        this.auditor.audit(ctx, user, CoreAuditEventAction.BACKUP_DELETED, "middleware-core", new BackupDeletedAuditMessage(backupInfo.getId(), backupInfo.getFileName()));
    }

    @PATCH
    @Path(value="/api/v1/backup/prepare_restore")
    @Operation(tags={"Backups"}, summary="Prepare backup file to restore by file", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, requestBody=@RequestBody(content={@Content(mediaType="multipart/form-data", encoding={@Encoding(name="file", contentType="application/octet-stream")}, schema=@Schema(implementation=UploadPluginFileDTO.class))}), responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", schema=@Schema(implementation=BackupFileRestoreInfoDTO.class))}), @ApiResponse(responseCode="400", description="Bad request", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="500", description="Internal Server Error", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))})})
    public void prepareRestoreByFile(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        UserUtils.requireRoot(user);
        JavalinFile javalinFile = JavalinFile.from(ctx, "file");
        BackupFileRestoreInfo restoreInfo = this.backupsManager.prepareForRestore(javalinFile);
        ctx.json(new BackupFileRestoreInfoDTO(restoreInfo));
    }

    @PATCH
    @Path(value="/api/v1/backup/{id}/prepare_restore")
    @Operation(tags={"Backups"}, summary="Prepare backup file to restore by id", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="id", example="2", description="Backup id", required=true)}, responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", schema=@Schema(implementation=BackupFileRestoreInfoDTO.class))}), @ApiResponse(responseCode="400", description="Bad request", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="500", description="Internal Server Error", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))})})
    public void prepareRestoreById(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        UserUtils.requireRoot(user);
        int id = ctx.pathParamAsClass("id", Integer.class).getOrThrow(i -> new ErrorException(BaseError.RequiredParams, "id"));
        BackupFileRestoreInfo restoreInfo = this.backupsManager.prepareForRestore(id);
        ctx.json(new BackupFileRestoreInfoDTO(restoreInfo));
    }

    @Inject
    public BackupsController(BackupsManager backupsManager, UsersManager usersManager, BeanValidator validator, QueryParamsParser queryParamsParser, Auditor auditor) {
        this.backupsManager = backupsManager;
        this.usersManager = usersManager;
        this.validator = validator;
        this.queryParamsParser = queryParamsParser;
        this.auditor = auditor;
    }
}

