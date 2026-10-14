/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.config;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name="IpAddressesDTO")
public record IpAddressesDTO(@JsonProperty(value="values") @JSONField(name="values") @ArraySchema(schema=@Schema(name="values", description="Ip addresses", implementation=String.class, example="127.0.0.1")) List<String> values) {
}

