/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.plugins.dto.face;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="FaceListDTO", description="Face list class structure")
public record IntegrationShortListDTO(@Schema(name="id", description="List ID", implementation=Integer.class, example="1") @JSONField(name="id") @JsonProperty(value="id") Integer id, @Schema(name="name", description="List name", implementation=String.class, example="White list") @JSONField(name="name") @JsonProperty(value="name") String name, @Schema(name="comment", description="List comment", implementation=String.class, example="Some comment") @JSONField(name="comment") @JsonProperty(value="comment") String comment) {
}

