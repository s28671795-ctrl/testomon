/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.streams;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.vezha.dto.streams.validations.StreamWithUuidMustExist;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import javax.validation.constraints.NotEmpty;
import org.jetbrains.annotations.NotNull;

@StreamWithUuidMustExist
@Schema(name="StreamRenameDTO", description="Stream rename class structure")
public record StreamRenameDTO(@JSONField(name="uuid") @JsonProperty(value="uuid") @Schema(name="uuid", description="Stream uuid", implementation=UUID.class) @NotNull UUID uuid, @JSONField(name="new_name") @JsonProperty(value="new_name") @Schema(name="new_name", description="New name of stream", implementation=String.class) @NotEmpty String newName, @JsonIgnore @JSONField(name="client_id", serialize=false, deserialize=false) @Schema(hidden=true) Integer clientId) {

    public static final class Fields {
        public static final String uuid = "uuid";
        public static final String newName = "newName";
        public static final String clientId = "clientId";
    }
}

