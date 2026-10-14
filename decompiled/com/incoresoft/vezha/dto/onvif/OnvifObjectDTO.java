/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.onvif;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.vezha.dto.onvif.validations.GroupMustExist;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@GroupMustExist
public record OnvifObjectDTO(@NotNull @JSONField(name="parent_id", defaultValue="0") @Schema(name="parent_id", description="ID of folder to add stream in", implementation=Integer.class, example="1") Integer parentId, @NotBlank @JSONField(name="token") @Schema(name="token", description="ONVIF stream token", implementation=String.class, example="some onvif stream token") String token) {

    public static final class Fields {
        public static final String parentId = "parentId";
        public static final String token = "token";
    }
}

