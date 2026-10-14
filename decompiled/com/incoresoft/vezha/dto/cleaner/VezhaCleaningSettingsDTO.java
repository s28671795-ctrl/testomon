/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.cleaner;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.dto.cleaner.CleaningSettingsDTO;
import com.incoresoft.middleware.cleaner.CleaningCategory;
import com.incoresoft.plugin.commons.cleaner.VezhaCleaningCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Schema(description="Cleaner retention time settings for different categories of data")
public record VezhaCleaningSettingsDTO(@NotNull @Min(value=1L) @JSONField(name="metadata") @Schema(name="metadata", description="Metadata retention time in days", example="30") @NotNull @Min(value=1L) Integer metadataRetentionDays, @NotNull @Min(value=1L) @JSONField(name="alerts") @Schema(name="alerts", description="Alerts retention time in days", example="30") @NotNull @Min(value=1L) Integer alertsRetentionDays, @NotNull @Min(value=1L) @JSONField(name="storage_data") @Schema(name="storage_data", description="Storage data retention time in days", example="30") @NotNull @Min(value=1L) Integer storageRetentionDays) implements CleaningSettingsDTO
{
    public VezhaCleaningSettingsDTO(Map<CleaningCategory, Duration> retentionTimes) {
        this((int)Objects.requireNonNull(retentionTimes.get(VezhaCleaningCategory.METADATA)).toDays(), (int)Objects.requireNonNull(retentionTimes.get(VezhaCleaningCategory.ALERTS)).toDays(), (int)Objects.requireNonNull(retentionTimes.get(VezhaCleaningCategory.STORAGE_DATA)).toDays());
    }

    @Override
    public Map<CleaningCategory, Duration> asMap() {
        return Map.of(VezhaCleaningCategory.METADATA, Duration.ofDays(this.metadataRetentionDays.intValue()), VezhaCleaningCategory.ALERTS, Duration.ofDays(this.alertsRetentionDays.intValue()), VezhaCleaningCategory.STORAGE_DATA, Duration.ofDays(this.storageRetentionDays.intValue()));
    }
}

