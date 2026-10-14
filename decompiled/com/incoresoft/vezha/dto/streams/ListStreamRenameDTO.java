/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.streams;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.vezha.dto.streams.StreamRenameDTO;
import com.incoresoft.vezha.dto.streams.validations.StreamNamesMustBeUnique;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Schema(name="ListStreamRenameDTO", description="Stream list rename class structure")
@StreamNamesMustBeUnique
public record ListStreamRenameDTO(@JSONField(name="streams") @JsonProperty(value="streams") @ArraySchema(schema=@Schema(name="streams", description="List of streams for rename", implementation=StreamRenameDTO.class)) @NotEmpty @Valid List<StreamRenameDTO> streams) {

    public static final class Fields {
        public static final String streams = "streams";
    }
}

