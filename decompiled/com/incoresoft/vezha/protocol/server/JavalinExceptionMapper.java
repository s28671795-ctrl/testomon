/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.protocol.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.google.inject.Inject;
import com.incoresoft.cleaner.exceptions.CleanerIsAlreadyRunning;
import com.incoresoft.config.exceptions.StorageConnectionException;
import com.incoresoft.dependencyinjection.exceptions.SystemIsUnavailableException;
import com.incoresoft.errors.CoreErrors;
import com.incoresoft.marketplace.exceptions.MarketplaceServerUnavailableException;
import com.incoresoft.middleware.http.errors.Error;
import com.incoresoft.middleware.http.exceptions.ErrorException;
import com.incoresoft.middleware.http.exceptions.ValidationException;
import com.incoresoft.plugin.commons.dto.ErrorDTO;
import com.incoresoft.plugin.commons.exceptions.file.UploadedFileMustHaveJarExtension;
import com.incoresoft.plugin.commons.exceptions.file.UploadedFileNotFoundException;
import com.incoresoft.plugin.commons.http.answers.ErrorAnswer;
import com.incoresoft.plugin.commons.http.error.BaseError;
import com.incoresoft.plugins.exceptions.PluginAlreadyInstalledException;
import com.incoresoft.plugins.exceptions.PluginAlreadyUpdatedException;
import com.incoresoft.plugins.exceptions.PluginInvalidException;
import com.incoresoft.plugins.exceptions.PluginIsBeingInstalledException;
import com.incoresoft.plugins.exceptions.PluginLoadingException;
import com.incoresoft.plugins.exceptions.PluginNotCompatibleException;
import com.incoresoft.plugins.exceptions.PluginNotFoundException;
import com.incoresoft.plugins.exceptions.PluginStartingException;
import com.incoresoft.plugins.exceptions.PluginStoppingException;
import com.incoresoft.plugins.exceptions.PluginUninstallingException;
import com.incoresoft.plugins.exceptions.PluginsStartingException;
import com.incoresoft.plugins.exceptions.PluginsStoppingException;
import com.incoresoft.services.exceptions.ServiceFileIsInvalidException;
import com.incoresoft.services.exceptions.ServiceIsBeingUpdatedException;
import com.incoresoft.services.exceptions.ServiceIsNotBeingUpdatedException;
import com.incoresoft.services.exceptions.ServiceVersionNotFoundException;
import com.incoresoft.vezha.analytics.cameraanalytics.StoredImageRetrievalException;
import com.incoresoft.vezha.analytics.exceptions.AnalyticsIsNotStoppedException;
import com.incoresoft.vezha.analytics.exceptions.CannotStartCameraAnalyticsOnLocalS3Exception;
import com.incoresoft.vezha.analytics.exceptions.CannotStartCameraAnalyticsOnLocalStorageException;
import com.incoresoft.vezha.analytics.exceptions.NoAccessToModuleException;
import com.incoresoft.vezha.analytics.exceptions.NoAvailableLicenseException;
import com.incoresoft.vezha.analytics.exceptions.WrongSystemVersionException;
import com.incoresoft.vezha.auth.exceptions.LoginIsDisabledException;
import com.incoresoft.vezha.backup.exceptions.BackupFileCorruptedException;
import com.incoresoft.vezha.backup.exceptions.BackupFileInvalidException;
import com.incoresoft.vezha.backup.exceptions.BackupFileNotCompatibleException;
import com.incoresoft.vezha.branding.exceptions.FileExtensionIsWrong;
import com.incoresoft.vezha.branding.exceptions.FileExtensionRequired;
import com.incoresoft.vezha.dto.plugins.PluginIdsDTO;
import com.incoresoft.vezha.dto.streams.StreamNamesDTO;
import com.incoresoft.vezha.errors.VezhaErrors;
import com.incoresoft.vezha.eventmanager.EventInUseException;
import com.incoresoft.vezha.models.streams.exceptions.OnvifLoginFailedException;
import com.incoresoft.vezha.models.streams.exceptions.StreamFrameGrabException;
import com.incoresoft.vezha.models.streams.exceptions.StreamGroupIsNotEmptyException;
import com.incoresoft.vezha.models.streams.exceptions.StreamHasAnalyticsException;
import com.incoresoft.vezha.models.streams.exceptions.StreamWithNameExistsException;
import com.incoresoft.vezha.models.streams.exceptions.StreamWithThisUriExistsException;
import com.incoresoft.vezha.models.streams.exceptions.UnsupportedCodecException;
import com.incoresoft.vezha.models.users.exceptions.CannotCreateUserWithRootRoleException;
import com.incoresoft.vezha.models.users.exceptions.CannotDeleteRootUserException;
import com.incoresoft.vezha.models.users.exceptions.CannotEditRootUserException;
import com.incoresoft.vezha.models.users.exceptions.UserCannotDeleteItselfException;
import com.incoresoft.vezha.models.users.exceptions.UserWithThisEmailAlreadyExistsException;
import com.incoresoft.vezha.models.users.exceptions.WrongLoginOrPasswordException;
import io.javalin.Javalin;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.ForbiddenResponse;
import io.javalin.http.HttpStatus;
import io.javalin.http.NotFoundResponse;
import io.javalin.http.UnauthorizedResponse;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavalinExceptionMapper {
    private static final Logger log = LoggerFactory.getLogger(JavalinExceptionMapper.class);
    private static final int DESERIALIZATION_ERROR_CODE = 498;
    private final Javalin app;

    @Inject
    public void map() {
        this.app.exception(Exception.class, (e, ctx) -> {
            log.error("Exception has occurred during http request.", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR);
            ctx.json(new ErrorAnswer((Error)BaseError.Error, null, e.getMessage()));
        }).exception(JSONException.class, (e, ctx) -> ctx.status(498).json(new ErrorAnswer((Error)CoreErrors.DESERIALIZATION_FAILED, null, e.getMessage()))).exception(ErrorException.class, (e, ctx) -> ctx.json(new ErrorAnswer((ErrorException)e)).status(400)).exception(ValidationException.class, (e, ctx) -> {
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.json(new ErrorAnswer((ValidationException)e));
        }).exception(SystemIsUnavailableException.class, (e, ctx) -> ctx.status(HttpStatus.CONFLICT).json(new ErrorAnswer(e.getError(), new Object[0]))).exception(UserWithThisEmailAlreadyExistsException.class, (e, ctx) -> {
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.json(new ErrorAnswer((Error)CoreErrors.USER_WITH_EMAIL_EXISTS, new Object[0]));
        }).exception(LoginIsDisabledException.class, (e, ctx) -> {
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.json(new ErrorAnswer((Error)VezhaErrors.LOGIN_IS_DISABLED, new Object[0]));
        }).exception(UserCannotDeleteItselfException.class, (e, ctx) -> {
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.json(new ErrorAnswer((Error)CoreErrors.SELF_USER_DELETE, new Object[0]));
        }).exception(CannotCreateUserWithRootRoleException.class, (e, ctx) -> {
            ctx.status(400);
            ctx.json(new ErrorAnswer((Error)VezhaErrors.CANNOT_CREATE_ROOT_USER, new Object[0]));
        }).exception(CannotEditRootUserException.class, (e, ctx) -> {
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.json(new ErrorAnswer((Error)CoreErrors.CANNOT_EDIT_ROOT_USER, new Object[0]));
        }).exception(CannotDeleteRootUserException.class, (e, ctx) -> {
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.json(new ErrorAnswer((Error)CoreErrors.CANNOT_DELETE_ROOT_USER, new Object[0]));
        }).exception(EventInUseException.class, (e, ctx) -> {
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.json(new ErrorAnswer((Error)VezhaErrors.DELETE_EVENT_IN_USE, JSON.toJSONString(e.getIdOfAnalyticsUsingEvent()), new Object[0]));
        }).exception(OnvifLoginFailedException.class, (e, ctx) -> {
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.json(new ErrorAnswer((Error)BaseError.WrongLogin, new Object[0]));
        }).exception(StreamGroupIsNotEmptyException.class, (e, ctx) -> {
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.json(new ErrorAnswer((Error)VezhaErrors.GROUP_IS_NOT_EMPTY, new Object[0]));
        }).exception(ServiceIsBeingUpdatedException.class, (e, ctx) -> {
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.json(new ErrorAnswer((Error)CoreErrors.SERVICE_IS_BEING_UPDATED, null, e.getServiceId()));
        }).exception(ServiceIsNotBeingUpdatedException.class, (e, ctx) -> {
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.json(new ErrorAnswer((Error)CoreErrors.SERVICE_IS_NOT_BEING_UPDATED, null, e.getServiceId()));
        }).exception(ServiceFileIsInvalidException.class, (e, ctx) -> {
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.json(new ErrorAnswer((Error)CoreErrors.SERVICE_FILE_IS_INVALID, null, e.getServiceId()));
        }).exception(ServiceVersionNotFoundException.class, (e, ctx) -> {
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.json(new ErrorAnswer((Error)CoreErrors.SERVICE_VERSION_NOT_FOUND, null, e.getServiceId()));
        }).exception(AnalyticsIsNotStoppedException.class, (e, ctx) -> {
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.json(new ErrorAnswer((Error)VezhaErrors.ANALYTICS_IS_NOT_STOPPED, e.getId()));
        }).exception(NoAccessToModuleException.class, (e, ctx) -> ctx.json(new ErrorAnswer((Error)VezhaErrors.NO_ACCESS_TO_MODULE, e.getType(), new Object[0])).status(HttpStatus.BAD_REQUEST)).exception(NoAvailableLicenseException.class, (e, ctx) -> ctx.json(new ErrorAnswer((Error)VezhaErrors.NO_AVAILABLE_LICENSE, null, e.getType())).status(HttpStatus.BAD_REQUEST)).exception(WrongSystemVersionException.class, (e, ctx) -> ctx.json(new ErrorAnswer((Error)VezhaErrors.LICENSE_IS_NOT_VALID_FOR_THIS_VERSION, new Object[0])).status(HttpStatus.BAD_REQUEST)).exception(NotFoundResponse.class, (e, ctx) -> ctx.json(new ErrorAnswer((Error)BaseError.NotFound, new Object[0])).status(HttpStatus.NOT_FOUND)).exception(BadRequestResponse.class, (e, ctx) -> ctx.json(new ErrorAnswer((Error)BaseError.BadRequest, null, e.getMessage())).status(HttpStatus.BAD_REQUEST)).exception(UnauthorizedResponse.class, (e, ctx) -> ctx.json(new ErrorAnswer((Error)CoreErrors.UNAUTHORIZED, new Object[0])).status(401)).exception(ForbiddenResponse.class, (e, ctx) -> ctx.json(new ErrorAnswer((Error)CoreErrors.FORBIDDEN, new Object[0])).status(403)).exception(UnsupportedCodecException.class, (e, ctx) -> ctx.json(new ErrorAnswer((Error)VezhaErrors.UNSUPPORTED_CODEC, new Object[0])).status(HttpStatus.BAD_REQUEST)).exception(StorageConnectionException.class, (e, ctx) -> ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorAnswer((Error)CoreErrors.STORAGE_CONNECTION_FAILED, new Object[0]))).exception(BackupFileNotCompatibleException.class, (e, ctx) -> ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorAnswer((Error)CoreErrors.BACKUP_FILE_NOT_COMPATIBLE, null, e.getResponseBody()))).exception(BackupFileCorruptedException.class, (e, ctx) -> ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorAnswer((Error)CoreErrors.BACKUP_FILE_CORRUPTED, null, e.getMessage()))).exception(BackupFileInvalidException.class, (e, ctx) -> ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorAnswer((Error)CoreErrors.BACKUP_RESTORE_VALIDATION_FAILED, null, e.getMessage()))).exception(MarketplaceServerUnavailableException.class, (e, ctx) -> ctx.status(HttpStatus.SERVICE_UNAVAILABLE).json(new ErrorAnswer((Error)CoreErrors.MARKETPLACE_SERVER_UNAVAILABLE, new Object[0]))).exception(PluginAlreadyInstalledException.class, (e, ctx) -> ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorAnswer((Error)CoreErrors.PLUGIN_IS_ALREADY_INSTALLED, null, e.getPluginId()))).exception(PluginAlreadyUpdatedException.class, (e, ctx) -> ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorAnswer((Error)CoreErrors.PLUGIN_IS_ALREADY_UPDATED, new Object[0]))).exception(PluginInvalidException.class, (e, ctx) -> ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorAnswer((Error)CoreErrors.PLUGIN_IS_INVALID, new Object[0]))).exception(PluginIsBeingInstalledException.class, (e, ctx) -> ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorAnswer((Error)CoreErrors.PLUGIN_IS_BEING_INSTALLED, new Object[0]))).exception(PluginLoadingException.class, (e, ctx) -> ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorAnswer((Error)CoreErrors.PLUGIN_LOADING_FAILED, new Object[0]))).exception(PluginNotCompatibleException.class, (e, ctx) -> ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorAnswer((Error)CoreErrors.PLUGIN_NOT_COMPATIBLE, null, e.getPluginVersion()))).exception(PluginNotFoundException.class, (e, ctx) -> ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorAnswer((Error)CoreErrors.PLUGIN_NOT_FOUND, new Object[0]))).exception(PluginsStartingException.class, (e, ctx) -> ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorAnswer((Error)CoreErrors.PLUGINS_STARTING_FAILED, new PluginIdsDTO(e.getPluginIds())))).exception(PluginsStoppingException.class, (e, ctx) -> ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorAnswer((Error)CoreErrors.PLUGINS_STOPPING_FAILED, new PluginIdsDTO(e.getPluginIds())))).exception(PluginStartingException.class, (e, ctx) -> ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorAnswer((Error)CoreErrors.PLUGIN_STARTING_FAILED, new Object[0]))).exception(PluginStoppingException.class, (e, ctx) -> ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorAnswer((Error)CoreErrors.PLUGIN_STOPPING_FAILED, new Object[0]))).exception(PluginUninstallingException.class, (e, ctx) -> ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorAnswer((Error)CoreErrors.PLUGIN_UNINSTALLING_FAILED, new Object[0]))).exception(BackupFileNotCompatibleException.class, (e, ctx) -> ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorAnswer((Error)CoreErrors.BACKUP_FILE_NOT_COMPATIBLE, null, e.getResponseBody()))).exception(BackupFileCorruptedException.class, (e, ctx) -> ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorAnswer((Error)CoreErrors.BACKUP_FILE_CORRUPTED, null, e.getMessage()))).exception(BackupFileInvalidException.class, (e, ctx) -> ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorAnswer((Error)CoreErrors.BACKUP_RESTORE_VALIDATION_FAILED, null, e.getMessage()))).exception(StreamHasAnalyticsException.class, (e, ctx) -> ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorAnswer((Error)VezhaErrors.STREAM_HAS_ANALYTICS, null, e.getAnalyticsIds()))).exception(StreamWithNameExistsException.class, (e, ctx) -> ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorAnswer((Error)VezhaErrors.STREAM_WITH_NAME_EXISTS, null, new StreamNamesDTO(e.getNames())))).exception(UploadedFileNotFoundException.class, (e, ctx) -> ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorAnswer((Error)BaseError.RequiredParams, e.getFileName(), new Object[0]))).exception(UploadedFileMustHaveJarExtension.class, (e, ctx) -> ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorAnswer((Error)CoreErrors.FILE_MUST_HAVE_JAR_EXTENSION, e.getFileName(), new Object[0]))).exception(StreamWithThisUriExistsException.class, (e, ctx) -> ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorAnswer((Error)VezhaErrors.STREAM_WITH_URL_EXISTS, e.getUri()))).exception(StreamFrameGrabException.class, (e, ctx) -> ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorAnswer((Error)VezhaErrors.STREAM_FRAME_GRAB_ERROR, new Object[0]))).exception(FileExtensionIsWrong.class, (e, ctx) -> ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorAnswer((Error)VezhaErrors.LOGO_IMAGE_EXTENSION_IS_WRONG, new Object[0]))).exception(FileExtensionRequired.class, (e, ctx) -> ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorAnswer((Error)VezhaErrors.LOGO_IMAGE_EXTENSION_REQUIRED, new Object[0]))).exception(CleanerIsAlreadyRunning.class, (e, ctx) -> ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorAnswer((Error)CoreErrors.CLEANER_IS_ALREADY_RUNNING, new Object[0]))).exception(WrongLoginOrPasswordException.class, (e, ctx) -> ctx.status(401).json(new ErrorAnswer((Error)CoreErrors.WRONG_LOGIN_OR_PASSWORD, new Object[0]))).exception(CannotStartCameraAnalyticsOnLocalStorageException.class, (e, ctx) -> ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorAnswer((Error)VezhaErrors.CANNOT_START_CAMERA_ANALYTICS_ON_LOCAL_STORAGE, new Object[0]))).exception(CannotStartCameraAnalyticsOnLocalS3Exception.class, (e, ctx) -> ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorAnswer((Error)VezhaErrors.CANNOT_START_CAMERA_ANALYTICS_ON_LOCAL_S3_STORAGE, new Object[0]))).exception(StoredImageRetrievalException.class, (e, ctx) -> ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorAnswer(List.of(new ErrorDTO(e.getErrorMessage(), new Object[0])))));
    }

    @Inject
    public JavalinExceptionMapper(Javalin app) {
        this.app = app;
    }
}

