/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.license;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.javalin.http.UploadedFile;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="LicenseFileActivationDTO", description="License file activation class structure")
public record LicenseFileActivationDTO(@Schema(name="file", description="License file", type="file", format="binary") @JsonProperty(value="file") @JSONField(name="file") UploadedFile image) {
}

