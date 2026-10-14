/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.onvif;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Schema(description="Onvif import first step parameters")
public record OnvifConnectionDTO(@JSONField(name="host") @Schema(name="host", description="ONVIF host", implementation=String.class, example="localhost") @NotBlank String host, @JSONField(name="onvif_port") @Schema(name="onvif_port", description="ONVIF port", implementation=Integer.class, example="580") @NotNull @Min(value=0L) @Max(value=65535L) @NotNull @Min(value=0L) @Max(value=65535L) Integer onvifPort, @JSONField(name="rtsp_port") @Schema(name="rtsp_port", description="RTSP port", implementation=Integer.class, example="554") @NotNull @Min(value=0L) @Max(value=65535L) @NotNull @Min(value=0L) @Max(value=65535L) Integer rtspPort, @JSONField(name="user") @Schema(name="user", description="ONVIF server user name", implementation=String.class, example="user") @NotBlank String user, @JSONField(name="password") @Schema(name="password", description="ONVIF server password", implementation=String.class, example="password") @NotBlank String password) {
}

