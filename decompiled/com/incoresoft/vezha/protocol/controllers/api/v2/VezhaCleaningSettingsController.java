/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.protocol.controllers.api.v2;

import com.google.inject.Inject;
import com.incoresoft.cleaner.CleaningSettingsController;
import com.incoresoft.database.CleaningSettingsRepository;
import com.incoresoft.middleware.audit.Auditor;
import com.incoresoft.middleware.cleaner.CleaningCategory;
import com.incoresoft.middleware.model.user.UsersManager;
import com.incoresoft.plugin.commons.cleaner.VezhaCleaningCategory;
import com.incoresoft.plugin.commons.validation.BeanValidator;
import com.incoresoft.vezha.dto.cleaner.VezhaCleaningSettingsDTO;
import com.incoresoft.vezha.storage.S3StorageCleaner;
import io.javalin.http.Context;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

@OpenAPIDefinition(info=@Info(title="Middleware-Core", description="Middleware-Core HTTP API"))
public class VezhaCleaningSettingsController
extends CleaningSettingsController<VezhaCleaningSettingsDTO> {
    private final S3StorageCleaner s3StorageCleaner;

    @Inject
    public VezhaCleaningSettingsController(UsersManager usersManager, CleaningSettingsRepository cleaningSettingsRepository, Auditor auditor, BeanValidator beanValidator, S3StorageCleaner s3StorageCleaner) {
        super(usersManager, cleaningSettingsRepository, auditor, beanValidator, "middleware-core");
        this.s3StorageCleaner = s3StorageCleaner;
    }

    @Override
    public Collection<CleaningCategory> allCategories() {
        return Arrays.asList(VezhaCleaningCategory.values());
    }

    @Override
    public Class<VezhaCleaningSettingsDTO> getSettingsDTOClass() {
        return VezhaCleaningSettingsDTO.class;
    }

    @Override
    public VezhaCleaningSettingsDTO createSettingsDTO(Map<CleaningCategory, Duration> map) {
        return new VezhaCleaningSettingsDTO(map);
    }

    @Override
    @ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", schema=@Schema(implementation=VezhaCleaningSettingsDTO.class))})
    public void getCleaningSettings(Context ctx) {
        super.getCleaningSettings(ctx);
    }

    @Override
    @RequestBody(required=true, content={@Content(mediaType="application/json", schema=@Schema(implementation=VezhaCleaningSettingsDTO.class))})
    @ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", schema=@Schema(implementation=VezhaCleaningSettingsDTO.class))})
    public void updateCleaningSettings(Context ctx) {
        super.updateCleaningSettings(ctx);
        this.s3StorageCleaner.updateLifecycleRule();
    }
}

