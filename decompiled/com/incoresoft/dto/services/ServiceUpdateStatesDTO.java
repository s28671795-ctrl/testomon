/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.services;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.dto.services.ServiceUpdateStateDTO;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record ServiceUpdateStatesDTO(@JsonProperty(value="services") @JSONField(name="services") @ArraySchema(schema=@Schema(name="services", description="List of services", implementation=ServiceUpdateStateDTO.class)) List<ServiceUpdateStateDTO> services) {
}

