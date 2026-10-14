/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.protocol.controllers.api.v2;

import com.google.inject.Inject;
import com.incoresoft.middleware.audit.Auditor;
import com.incoresoft.middleware.http.AppRole;
import com.incoresoft.middleware.http.controllers.Controller;
import com.incoresoft.middleware.http.exceptions.ErrorException;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.plugin.commons.http.answers.ErrorAnswer;
import com.incoresoft.plugin.commons.http.error.BaseError;
import com.incoresoft.plugin.commons.utils.ContentTypeUtils;
import com.incoresoft.vezha.audit.VezhaAuditEventAction;
import com.incoresoft.vezha.branding.exceptions.FileExtensionIsWrong;
import com.incoresoft.vezha.branding.exceptions.FileExtensionRequired;
import com.incoresoft.vezha.config.VezhaConfigManager;
import com.incoresoft.vezha.dto.branding.BrandingSettingsDTO;
import com.incoresoft.vezha.dto.settings.UploadLogoFileDTO;
import com.incoresoft.vezha.models.settings.CustomSettingsManager;
import com.incoresoft.vezha.models.users.UserImpl;
import com.incoresoft.vezha.models.users.UsersManagerImpl;
import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@OpenAPIDefinition(info=@Info(title="Middleware-Core", description="Middleware-Core HTTP API"))
public class ApiBrandingControllerV2
implements Controller {
    private static final Logger log = LoggerFactory.getLogger(ApiBrandingControllerV2.class);
    private final String API_PATH = "/api/v2/branding";
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("png", "jpg", "jpeg");
    private final UsersManagerImpl usersManager;
    private final Auditor auditor;
    private final VezhaConfigManager configManager;
    private final CustomSettingsManager customSettingsManager;

    @Override
    @Inject
    public void init(Javalin app) {
        app.routes(() -> ApiBuilder.path("/api/v2/branding", () -> {
            ApiBuilder.get("settings", this::getSettings, AppRole.ANYONE);
            ApiBuilder.path("logo", () -> {
                ApiBuilder.get(this::getLogo, AppRole.ANYONE);
                ApiBuilder.post(this::setNewLogo, AppRole.LOGGED_IN);
                ApiBuilder.delete(this::defaultLogo, AppRole.LOGGED_IN);
            });
            ApiBuilder.path("favicon", () -> ApiBuilder.get(this::getFavicon, AppRole.ANYONE));
        }));
    }

    @GET
    @Path(value="/api/v2/branding/settings")
    @Operation(tags={"Branding"}, summary="Get current branding settings", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, responses={@ApiResponse(responseCode="200", description="Successful operation", content={@Content(mediaType="application/json", schema=@Schema(implementation=BrandingSettingsDTO.class))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found Sounds Item")})
    public void getSettings(@Parameter(hidden=true) Context ctx) {
        ctx.json(new BrandingSettingsDTO(this.customSettingsManager));
    }

    @GET
    @Path(value="/api/v2/branding/logo")
    @Operation(tags={"Branding"}, summary="Get current logo", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, responses={@ApiResponse(responseCode="200", description="Successful operation", content={@Content(mediaType="image/png")}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found Sounds Item")})
    public void getLogo(@Parameter(hidden=true) Context ctx) {
        try {
            if (Files.exists(this.configManager.getCustomLogoPath(), new LinkOption[0])) {
                ctx.contentType(ContentTypeUtils.guessContentType(this.configManager.getCustomLogoPath().toString())).result(Files.newInputStream(this.configManager.getCustomLogoPath(), new OpenOption[0]));
            } else {
                ctx.contentType("image/png").result(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("front/host/images/logo_frame.png")));
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @POST
    @Path(value="/api/v2/branding/logo")
    @Operation(tags={"Branding"}, summary="Set new logo", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, requestBody=@RequestBody(content={@Content(mediaType="multipart/form-data", encoding={@Encoding(name="logo", contentType="application/octet-stream")}, schema=@Schema(implementation=UploadLogoFileDTO.class))}), responses={@ApiResponse(responseCode="204", description="Successful operation"), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found Sounds Item")})
    public void setNewLogo(@Parameter(hidden=true) Context ctx) throws IOException {
        UserImpl user = (UserImpl)this.usersManager.getUser(ctx);
        UploadedFile file = Optional.ofNullable(ctx.uploadedFile("logo")).orElseThrow(() -> new ErrorException(BaseError.RequiredParams, "logo"));
        if (ApiBrandingControllerV2.fileHasNoExtension(file)) {
            throw new FileExtensionRequired();
        }
        if (!ALLOWED_EXTENSIONS.contains(file.extension().replace(".", "").toLowerCase())) {
            throw new FileExtensionIsWrong();
        }
        Files.copy(file.content(), this.configManager.getCustomLogoPath(), StandardCopyOption.REPLACE_EXISTING);
        ctx.status(204);
        this.auditor.audit(ctx, user, VezhaAuditEventAction.LOGO_UPDATED, "middleware-core");
    }

    private static boolean fileHasNoExtension(UploadedFile file) {
        return !file.filename().contains(".");
    }

    @DELETE
    @Path(value="/api/v2/branding/logo")
    @Operation(tags={"Branding"}, summary="Delete logo / Set default logo", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, responses={@ApiResponse(responseCode="204", description="Successful operation "), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void defaultLogo(@Parameter(hidden=true) Context ctx) throws IOException {
        User user = this.usersManager.getUser(ctx);
        if (Files.exists(this.configManager.getCustomLogoPath(), new LinkOption[0])) {
            Files.delete(this.configManager.getCustomLogoPath());
        }
        this.auditor.audit(ctx, user, VezhaAuditEventAction.LOGO_UPDATED, "middleware-core");
    }

    @GET
    @Path(value="/api/v2/branding/favicon")
    @Operation(tags={"Branding"}, summary="Get current favicon", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, responses={@ApiResponse(responseCode="200", description="Successful operation", content={@Content(mediaType="application/json", schema=@Schema(implementation=BrandingSettingsDTO.class))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found Sounds Item")})
    public void getFavicon(@Parameter(hidden=true) Context ctx) {
        try {
            InputStream inputStream2 = this.getFaviconInputStream();
            ctx.contentType("image/x-icon").result(Objects.requireNonNull(inputStream2));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private InputStream getFaviconInputStream() throws IOException {
        return Files.exists(this.configManager.getCustomFaviconPath(), new LinkOption[0]) ? Files.newInputStream(this.configManager.getCustomFaviconPath(), new OpenOption[0]) : this.getClass().getClassLoader().getResourceAsStream("front/host/favicon-vezha.ico");
    }

    @Inject
    public ApiBrandingControllerV2(UsersManagerImpl usersManager, Auditor auditor, VezhaConfigManager configManager, CustomSettingsManager customSettingsManager) {
        this.usersManager = usersManager;
        this.auditor = auditor;
        this.configManager = configManager;
        this.customSettingsManager = customSettingsManager;
    }
}

