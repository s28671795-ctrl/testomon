/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.streams;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.serialization.StreamAuthSerializer;
import com.incoresoft.middleware.analytics.serialization.StreamParamsSerializer;
import com.incoresoft.middleware.model.stream.StreamAuth;
import com.incoresoft.middleware.model.stream.StreamParams;
import com.incoresoft.vezha.dto.streams.validations.StreamNameMustBeUnique;
import com.incoresoft.vezha.dto.streams.validations.StreamPathMustExistForMilestoneRtsp;
import com.incoresoft.vezha.dto.streams.validations.StreamUrlMustBeUnique;
import com.incoresoft.vezha.dto.streams.validations.TimezoneIsNotValid;
import com.incoresoft.vezha.dto.streams.validations.host.NotBlank;
import com.incoresoft.vezha.dto.streams.validations.port.NotNull;
import com.incoresoft.vezha.models.streams.StreamURIParser;
import io.swagger.v3.oas.annotations.media.Schema;
import java.net.URI;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@StreamUrlMustBeUnique
@StreamNameMustBeUnique
@StreamPathMustExistForMilestoneRtsp
@NotBlank
@NotNull
@Schema(name="FullStreamDTO", description="Video stream class structure")
public record StreamValidationGeneralStepDTO(@JSONField(name="id") @Schema(name="id", description="Video stream Id", example="1") @Min(value=0L) @Min(value=0L) Integer id, @JSONField(name="parent_id") @Schema(name="parent_id", description="Parent folder Id", example="1") @javax.validation.constraints.NotNull @Min(value=0L) @javax.validation.constraints.NotNull @Min(value=0L) Integer parentId, @Schema(name="host", description="Stream host", example="demo_host.com", implementation=String.class) String host, @Schema(name="port", description="Stream port", example="554", implementation=Integer.class) @Min(value=0L) @Max(value=65535L) @Min(value=0L) @Max(value=65535L) Integer port, @Schema(name="path", description="Stream path", example="/lpr_path", implementation=String.class) String path, @JSONField(name="full_path") @Schema(name="full_path", description="Stream full path / url", example="rtsp://demo_host.com:554/lpr_path", implementation=String.class) String fullPath, @Schema(name="name", description="Video stream name", example="Main stream", implementation=String.class) @javax.validation.constraints.NotBlank String name, @JSONField(name="auth", deserializeUsing=StreamAuthSerializer.class) @Schema(name="auth", description="Authorization params", example="{\"type\": \"no_auth\"}", implementation=StreamAuth.class) @javax.validation.constraints.NotNull @Valid StreamAuth auth, @JSONField(name="params", deserializeUsing=StreamParamsSerializer.class) @Schema(name="params", description="Additional params", example="{\"type\": \"rtsp\"}", implementation=StreamParams.class) @javax.validation.constraints.NotNull @Valid StreamParams params, @Schema(name="timezone", description="Timezone", implementation=String.class, example="Europe/Kiev") @javax.validation.constraints.NotBlank @TimezoneIsNotValid String timezone) implements StreamUrlMustBeUnique.Validatable,
StreamNameMustBeUnique.Validatable,
NotBlank.Validatable,
NotNull.Validatable,
StreamPathMustExistForMilestoneRtsp.Validatable
{
    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getHost() {
        return this.host;
    }

    @Override
    public Integer getPort() {
        return this.port;
    }

    @Override
    public StreamParams getParams() {
        return this.params();
    }

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public URI getURI() {
        return StreamURIParser.parse(this.host, this.port, this.path, this.auth, this.params);
    }

    @Override
    public int getClientId() {
        return 0;
    }
}

