/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.plugins;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="UploadPluginFileDTO", description="Upload plugin file class structure")
public record UploadPluginFileDTO(@JsonProperty(value="file") @JSONField(name="file") @Schema(name="file", description="Uploaded file", type="string", format="binary") String uploadedFile) {
}

