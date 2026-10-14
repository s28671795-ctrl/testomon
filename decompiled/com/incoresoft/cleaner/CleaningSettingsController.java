/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.cleaner;

import com.google.inject.Inject;
import com.incoresoft.audit.CoreAuditEventAction;
import com.incoresoft.database.CleaningSettingsRepository;
import com.incoresoft.dto.audit.messages.cleaner.CleaningSettingsUpdatedAuditMessage;
import com.incoresoft.dto.cleaner.CleaningSettingsDTO;
import com.incoresoft.middleware.audit.Auditor;
import com.incoresoft.middleware.cleaner.CleaningCategory;
import com.incoresoft.middleware.http.AppRole;
import com.incoresoft.middleware.http.controllers.Controller;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.model.user.UsersManager;
import com.incoresoft.plugin.commons.dto.ErrorDTO;
import com.incoresoft.plugin.commons.validation.BeanValidator;
import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;
import io.javalin.http.Context;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.time.Duration;
import java.util.Collection;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

public abstract class CleaningSettingsController<T extends CleaningSettingsDTO>
implements Controller {
    private static final String API_PATH = "/api/v1/cleaning_settings";
    private final UsersManager usersManager;
    private final CleaningSettingsRepository cleaningSettingsRepository;
    private final Auditor auditor;
    private final BeanValidator beanValidator;
    private final String source;

    @Override
    @Inject
    public void init(Javalin app) {
        app.routes(() -> ApiBuilder.path(API_PATH, () -> {
            ApiBuilder.get(this::getCleaningSettings, AppRole.LOGGED_IN, AppRole.API_CALL);
            ApiBuilder.put(this::updateCleaningSettings, AppRole.LOGGED_IN, AppRole.API_CALL);
        }));
    }

    public abstract Collection<CleaningCategory> allCategories();

    public abstract Class<T> getSettingsDTOClass();

    public abstract T createSettingsDTO(Map<CleaningCategory, Duration> var1);

    @GET
    @Path(value="/api/v1/cleaning_settings")
    @Operation(tags={"Cleaning settings"}, summary="Get cleaning settings", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, responses={@ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="500", description="Internal server error", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))})})
    public void getCleaningSettings(@Parameter(hidden=true) Context ctx) {
        ctx.json(this.createSettingsDTO(this.cleaningSettingsRepository.getMappings(this.allCategories())));
    }

    @PUT
    @Path(value="/api/v1/cleaning_settings")
    @Operation(tags={"Cleaning settings"}, summary="Update cleaning settings", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, responses={@ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="500", description="Internal server error", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))})})
    public void updateCleaningSettings(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        CleaningSettingsDTO cleaningSettingsDTO = (CleaningSettingsDTO)ctx.bodyAsClass(this.getSettingsDTOClass());
        this.beanValidator.validateObjectOrThrow(cleaningSettingsDTO, new Class[0]);
        Map<CleaningCategory, Duration> old = this.cleaningSettingsRepository.getMappings(this.allCategories());
        for (Map.Entry<CleaningCategory, Duration> entry : cleaningSettingsDTO.asMap().entrySet()) {
            this.cleaningSettingsRepository.changeRetentionTime(entry.getKey(), entry.getValue());
        }
        Map<CleaningCategory, Duration> updated = this.cleaningSettingsRepository.getMappings(this.allCategories());
        ctx.json(this.createSettingsDTO(updated));
        CleaningSettingsUpdatedAuditMessage message = new CleaningSettingsUpdatedAuditMessage(old, updated);
        this.auditor.audit(ctx, user, CoreAuditEventAction.CLEANING_SETTINGS_CHANGED, this.source, message);
    }

    public CleaningSettingsController(UsersManager usersManager, CleaningSettingsRepository cleaningSettingsRepository, Auditor auditor, BeanValidator beanValidator, String source2) {
        this.usersManager = usersManager;
        this.cleaningSettingsRepository = cleaningSettingsRepository;
        this.auditor = auditor;
        this.beanValidator = beanValidator;
        this.source = source2;
    }
}

