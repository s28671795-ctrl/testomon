/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.license;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.vezha.license.LicenseUsage;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="LicenseUsageDTO", description="License usage class structure")
public record LicenseUsageDTO(@Schema(name="name", description="License name", example="License name") @JSONField(name="name") String name, @Schema(name="embedded", description="Boolean value that shows if license is embedded", example="true") @JSONField(name="embedded") boolean embedded, @Schema(name="count", description="License count", example="1") @JSONField(name="count") int count) {
    public LicenseUsageDTO(LicenseUsage licenseUsage) {
        this(licenseUsage.name(), licenseUsage.embedded(), licenseUsage.count());
    }
}

