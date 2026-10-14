/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.plugins.dto.face;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.plugin.commons.plugins.dto.face.IntegrationShortListDTO;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="ListItemDTO", description="List item class structure")
public record IntegrationShortListItemDTO(@Schema(name="id", description="List item ID", implementation=Integer.class, example="1") @JSONField(name="id") @JsonProperty(value="id") Integer id, @Schema(name="name", description="List item name", implementation=String.class, example="John Deer") @JSONField(name="name") @JsonProperty(value="name") String name, @Schema(name="comment", description="List item comment", implementation=String.class, example="Some comment") @JSONField(name="comment") @JsonProperty(value="comment") String comment, @ArraySchema(schema=@Schema(name="image", description="Image", implementation=String.class)) @JSONField(name="image") @JsonProperty(value="image") String image, @Schema(name="list", description="List ID", implementation=Integer.class, example="1") @JSONField(name="list") @JsonProperty(value="list") IntegrationShortListDTO list) {
}

