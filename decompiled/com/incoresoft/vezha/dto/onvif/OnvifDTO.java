/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.onvif;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.vezha.dto.onvif.OnvifObjectDTO;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Schema(name="OnvifDTO", description="Onvif streams import class structure")
public record OnvifDTO(@JSONField(name="host") @Schema(name="host", description="ONVIF host", implementation=String.class, example="localhost") @NotBlank String host, @JSONField(name="user") @Schema(name="user", description="ONVIF server user name", implementation=String.class, example="user") @NotBlank String user, @JSONField(name="password") @Schema(name="password", description="ONVIF server password", implementation=String.class, example="password") @NotBlank String password, @ArraySchema(arraySchema=@Schema(name="streams", description="ONVIF streams list"), schema=@Schema(implementation=OnvifObjectDTO.class)) @NotEmpty @Valid List<OnvifObjectDTO> streams) {
}

