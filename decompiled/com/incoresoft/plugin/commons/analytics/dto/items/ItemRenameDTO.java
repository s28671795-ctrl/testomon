/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.analytics.dto.items;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotEmpty;

@Schema(name="ItemRenameDTO", description="Item rename class structure")
public record ItemRenameDTO(@Schema(name="name", description="New item name", example="Item 1", implementation=String.class) @JsonProperty(value="name") @NotEmpty String name) {
}

