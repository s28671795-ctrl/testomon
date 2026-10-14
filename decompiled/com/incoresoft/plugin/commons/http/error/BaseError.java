/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.http.error;

import com.incoresoft.middleware.http.errors.Error;

public enum BaseError implements Error
{
    UNKNOWN("ERROR_UNKNOWN"),
    Ok("ERROR_OK"),
    Error("ERROR_FAILED_TO_PROCESS_REQUEST"),
    BadRequest("ERROR_BAD_REQUEST"),
    NotAuthorized("ERROR_NOT_AUTHORIZED"),
    WrongLogin("ERROR_WRONG_LOGIN_OR_PASSWORD"),
    WrongPassword("ERROR_WRONG_LOGIN_OR_PASSWORD"),
    WrongSessionId("ERROR_WRONG_SESSION_ID"),
    WrongID("ERROR_WRONG_ID"),
    WrongDate("ERROR_WRONG_DATE_FORMAT"),
    RequiredParams("ERROR_REQUIRED_PARAM"),
    RequiredFields("ERROR_REQUIRED_FIELD"),
    NotFound("ERROR_NOT_FOUND"),
    ValidationError("ERROR_VALIDATION_ERROR"),
    WrongParamsFormat("ERROR_PARAMETER_IS_INVALID"),
    WrongEditPassword("ERROR_WRONG_PASSWORD"),
    ImmutableData("ERROR_THIS_DATA_CANNOT_BE_UPDATED"),
    NotSupported("ERROR_NOT_SUPPORTED"),
    NotAllowed("ERROR_NOT_ALLOWED"),
    FileUploadError("ERROR_FILE_UPLOAD_ERROR"),
    FileFormatError("ERROR_FILE_FORMAT_ERROR"),
    AlreadyContains("ERROR_ALREADY_CONTAINS"),
    FileEncodeError("ERROR_FILE_ENCODE_ERROR"),
    AlreadyExists("ERROR_ALREADY_EXISTS"),
    FileDownloadError("ERROR_FILE_DOWNLOAD_ERROR"),
    AccessDenied("ERROR_ACCESS_DENIED"),
    StreamsInRadiusNotFound("ERROR_NO_CAMERAS_IN_RADIUS");

    private final String localeKey;

    private BaseError(String localeKey) {
        this.localeKey = localeKey;
    }

    @Override
    public String getLocaleKey() {
        return this.localeKey;
    }
}

