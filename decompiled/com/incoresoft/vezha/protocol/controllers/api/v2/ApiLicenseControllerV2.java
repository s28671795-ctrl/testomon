/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.protocol.controllers.api.v2;

import com.google.inject.Inject;
import com.incoresoft.Utils.ApiParams;
import com.incoresoft.audit.CoreAuditEventAction;
import com.incoresoft.dto.audit.messages.license.LicenseActivatedWithFileAuditMessage;
import com.incoresoft.dto.audit.messages.license.LicenseActivatedWithKeyAuditMessage;
import com.incoresoft.dto.audit.messages.license.LicenseDeactivatedAuditMessage;
import com.incoresoft.dto.license.ActivateWithKeyDTO;
import com.incoresoft.dto.license.LicenseDTO;
import com.incoresoft.dto.license.LicenseFileActivationDTO;
import com.incoresoft.errors.CoreErrors;
import com.incoresoft.errors.LicenseError;
import com.incoresoft.license.License;
import com.incoresoft.license.LicenseDailyChecker;
import com.incoresoft.license.LicenseManager;
import com.incoresoft.license.exceptions.LicenseActivationException;
import com.incoresoft.license.exceptions.LicenseDeactivationException;
import com.incoresoft.license.model.LicenseData;
import com.incoresoft.license.model.LicenseStatus;
import com.incoresoft.middleware.audit.Auditor;
import com.incoresoft.middleware.http.AppRole;
import com.incoresoft.middleware.http.controllers.Controller;
import com.incoresoft.middleware.http.exceptions.ErrorException;
import com.incoresoft.middleware.http.exceptions.LicenseException;
import com.incoresoft.middleware.http.exceptions.ValidationException;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.model.user.UsersManager;
import com.incoresoft.plugin.commons.dto.ErrorDTO;
import com.incoresoft.plugin.commons.http.answers.ErrorAnswer;
import com.incoresoft.plugin.commons.http.error.BaseError;
import com.incoresoft.plugin.commons.validation.BeanValidator;
import com.incoresoft.vezha.dto.license.LicenseUsageDTO;
import com.incoresoft.vezha.license.LicenseUsageManager;
import com.incoresoft.vezha.license.VezhaLicenseProperties;
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
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@OpenAPIDefinition(info=@Info(title="Middleware-Core", description="Middleware-Core HTTP API", version="v2"))
public class ApiLicenseControllerV2
implements Controller {
    private static final Logger log = LoggerFactory.getLogger(ApiLicenseControllerV2.class);
    private static final String API_PATH = "/api/v2/license";
    private static final String FILE_KEY = "file_key";
    private final LicenseManager licenseManager;
    private final LicenseUsageManager licenseUsageManager;
    private final LicenseDailyChecker licenseDailyChecker;
    private final UsersManager usersManager;
    private final Auditor auditor;
    private final BeanValidator validator;
    private final VezhaLicenseProperties licenseProperties;

    @Override
    @Inject
    public void init(Javalin app) {
        app.routes(() -> ApiBuilder.path(API_PATH, () -> {
            ApiBuilder.get(this::getLicenseData, AppRole.LOGGED_IN, AppRole.API_CALL);
            ApiBuilder.patch("trigger_license_check", this::triggerLicenseCheck, AppRole.LOGGED_IN, AppRole.API_CALL);
            ApiBuilder.post(this::activateWithKey, AppRole.LOGGED_IN, AppRole.API_CALL);
            ApiBuilder.post("file", this::activateWithFile, AppRole.LOGGED_IN, AppRole.API_CALL);
            ApiBuilder.put(this::updateLicense, AppRole.LOGGED_IN, AppRole.API_CALL);
            ApiBuilder.delete("{id}", this::deactivateLicense, AppRole.LOGGED_IN, AppRole.API_CALL);
            ApiBuilder.post("hardware_file", this::getHardwareFile, AppRole.LOGGED_IN, AppRole.API_CALL);
            ApiBuilder.get("license_usage", this::getLicenseUsage, AppRole.LOGGED_IN, AppRole.API_CALL);
        }));
    }

    @PATCH
    @Path(value="/api/v2/license/trigger_license_check")
    @Operation(tags={"Licenses"}, summary="Trigger daily license check immediately. This request has no effect on next scheduled check", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, responses={@ApiResponse(responseCode="204", description="Successful operation"), @ApiResponse(responseCode="400", description="Bad request", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))}), @ApiResponse(responseCode="500", description="Internal server error", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))})})
    public void triggerLicenseCheck(@Parameter(hidden=true) Context context) {
        User user = this.usersManager.getUser(context);
        if (!user.isRoot()) {
            throw new NotFoundResponse();
        }
        this.licenseDailyChecker.checkLicense();
        context.status(204);
    }

    @GET
    @Path(value="/api/v2/license")
    @Operation(tags={"Licenses"}, summary="Get license data", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=LicenseDTO.class)))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void getLicenseData(@Parameter(hidden=true) Context ctx) {
        List<LicenseDTO> licenseData = this.licenseManager.getLicenses().stream().sorted(Comparator.comparing(License::activationDate).reversed()).map(License::getLicenseData).map(it -> new LicenseDTO((LicenseData)it, this.licenseProperties)).toList();
        ctx.json(licenseData);
    }

    @POST
    @Path(value="/api/v2/license")
    @Operation(tags={"Licenses"}, summary="Activate license with key", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, requestBody=@RequestBody(required=true, content={@Content(mediaType="application/json", schema=@Schema(implementation=ActivateWithKeyDTO.class))}), responses={@ApiResponse(responseCode="204", description="Successful operation "), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void activateWithKey(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        ActivateWithKeyDTO dto = new ApiParams(ctx).getBodyAsClass(ActivateWithKeyDTO.class);
        this.validator.validateObjectOrThrow(dto, new Class[0]);
        if (this.licenseManager.licenseKeyAlreadyExists(dto.getKey())) {
            throw new ValidationException(CoreErrors.LICENSE_KEY_ALREADY_EXISTS, "key", new Object[0]);
        }
        try {
            this.licenseManager.activateWithKey(dto.getKey());
        }
        catch (LicenseException | IllegalArgumentException e) {
            throw new ErrorException(new LicenseError(LicenseStatus.LICENSE_INVALID), new Object[0]);
        }
        catch (LicenseActivationException e) {
            throw new ErrorException(new LicenseError(e.getLicenseStatus()), new Object[]{e.getValidationStatus()});
        }
        ctx.status(204);
        this.auditor.audit(ctx, user, CoreAuditEventAction.LICENSE_ACTIVATED, "middleware-core", new LicenseActivatedWithKeyAuditMessage(dto.getKey()));
    }

    @POST
    @Path(value="/api/v2/license/file")
    @Operation(tags={"Licenses"}, summary="Activate license with file", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, requestBody=@RequestBody(content={@Content(mediaType="multipart/form-data", encoding={@Encoding(name="file", contentType="application/octet-stream")}, schema=@Schema(implementation=LicenseFileActivationDTO.class))}), responses={@ApiResponse(responseCode="204", description="Successful operation "), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void activateWithFile(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        UploadedFile uploadedFile = ctx.uploadedFile("file");
        if (uploadedFile == null) {
            throw new ValidationException(BaseError.FileUploadError, FILE_KEY, new Object[0]);
        }
        try (InputStream inputStream2 = uploadedFile.content();){
            this.licenseManager.activateWithFile(new String(inputStream2.readAllBytes(), StandardCharsets.UTF_8));
        }
        catch (IOException e) {
            throw new ValidationException(BaseError.FileFormatError, FILE_KEY, new Object[0]);
        }
        catch (LicenseActivationException | LicenseException | IllegalArgumentException e) {
            throw new ValidationException(CoreErrors.INVALID_ACTIVATION_KEY_FILE, FILE_KEY, new Object[0]);
        }
        ctx.status(204);
        this.auditor.audit(ctx, user, CoreAuditEventAction.LICENSE_ACTIVATED, "middleware-core", new LicenseActivatedWithFileAuditMessage(uploadedFile.filename()));
    }

    @PUT
    @Path(value="/api/v2/license")
    @Operation(tags={"Licenses"}, summary="Update licenses", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, responses={@ApiResponse(responseCode="204", description="Successful operation "), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void updateLicense(@Parameter(hidden=true) Context ctx) {
        this.licenseManager.updateLicense();
        ctx.status(204);
    }

    @POST
    @Path(value="/api/v2/license/hardware_file")
    @Operation(tags={"Licenses"}, summary="Get activation string", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, requestBody=@RequestBody(content={@Content(mediaType="application/json", schema=@Schema(implementation=ActivateWithKeyDTO.class))}), responses={@ApiResponse(responseCode="200", description="Successful operation", content={@Content(mediaType="application/octet-stream")}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden")})
    public void getHardwareFile(@Parameter(hidden=true) Context ctx) {
        String activationString;
        ActivateWithKeyDTO dto = new ApiParams(ctx).getBodyAsClass(ActivateWithKeyDTO.class);
        this.validator.validateObjectOrThrow(dto, new Class[0]);
        try {
            activationString = this.licenseManager.getActivationString(dto.getKey());
        }
        catch (LicenseActivationException e) {
            throw new ValidationException(new LicenseError(e.getLicenseStatus()), "key", new Object[0]);
        }
        try {
            ctx.result(new ByteArrayInputStream(activationString.getBytes(StandardCharsets.UTF_8))).header("Content-Disposition", "attachment; filename=activation.txt").header("Content-Type", "application/octet-stream");
        }
        catch (Exception e) {
            log.error("Exception downloading license activation string file", e);
            throw new ErrorException(BaseError.FileDownloadError, new Object[0]);
        }
    }

    @DELETE
    @Path(value="/api/v2/license/{id}")
    @Operation(tags={"Licenses"}, summary="Delete license by ID", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="id", example="123", description="License ID", required=true)}, responses={@ApiResponse(responseCode="204", description="Successful operation"), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void deactivateLicense(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        int id = ctx.pathParamAsClass("id", Integer.class).getOrThrow(e -> new ErrorException(BaseError.RequiredParams, "id"));
        License license = this.licenseManager.get(id).orElseThrow(NotFoundResponse::new);
        String licenseKey = this.licenseManager.getLicenseKey(license);
        try {
            this.licenseManager.deactivateLicense(id);
        }
        catch (LicenseDeactivationException e2) {
            throw new ErrorException(new LicenseError(e2.getLicenseStatus()), new Object[0]);
        }
        ctx.status(204);
        this.auditor.audit(ctx, user, CoreAuditEventAction.LICENSE_DEACTIVATED, "middleware-core", new LicenseDeactivatedAuditMessage(licenseKey));
    }

    @GET
    @Path(value="/api/v2/license/license_usage")
    @Operation(tags={"Licenses"}, summary="Get license usage", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=String.class)))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void getLicenseUsage(@Parameter(hidden=true) Context ctx) {
        ctx.json(this.licenseUsageManager.getLicenseUsage().stream().map(LicenseUsageDTO::new).toList());
    }

    @Inject
    public ApiLicenseControllerV2(LicenseManager licenseManager, LicenseUsageManager licenseUsageManager, LicenseDailyChecker licenseDailyChecker, UsersManager usersManager, Auditor auditor, BeanValidator validator, VezhaLicenseProperties licenseProperties) {
        this.licenseManager = licenseManager;
        this.licenseUsageManager = licenseUsageManager;
        this.licenseDailyChecker = licenseDailyChecker;
        this.usersManager = usersManager;
        this.auditor = auditor;
        this.validator = validator;
        this.licenseProperties = licenseProperties;
    }
}

