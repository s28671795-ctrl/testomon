/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.alpr.makemodel;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.middleware.analytics.models.alpr.makemodel.MakeModel;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="MakeModelDTO", description="Make and model class structure")
public final class MakeModelDTO {
    @JSONField(name="id")
    @JsonProperty(value="id")
    @Schema(name="id", description="Make model ID", implementation=Integer.class, example="1")
    private final int id;
    @JSONField(name="make")
    @JsonProperty(value="make")
    @Schema(name="make", description="Vehicle make name", implementation=String.class, example="AUDI")
    private final String make;
    @JSONField(name="model")
    @JsonProperty(value="model")
    @Schema(name="model", description="Vehicle model name", implementation=String.class, example="Q7")
    private final String model;

    public MakeModelDTO(MakeModel entity) {
        this.id = entity.getId();
        this.make = entity.getMake();
        this.model = entity.getModel();
    }

    @JsonCreator
    public MakeModelDTO(@JsonProperty(value="id") int id, @JsonProperty(value="make") String make, @JsonProperty(value="model") String model) {
        this.id = id;
        this.make = make;
        this.model = model;
    }

    public int getId() {
        return this.id;
    }

    public String getMake() {
        return this.make;
    }

    public String getModel() {
        return this.model;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof MakeModelDTO)) {
            return false;
        }
        MakeModelDTO other = (MakeModelDTO)o;
        if (this.getId() != other.getId()) {
            return false;
        }
        String this$make = this.getMake();
        String other$make = other.getMake();
        if (this$make == null ? other$make != null : !this$make.equals(other$make)) {
            return false;
        }
        String this$model = this.getModel();
        String other$model = other.getModel();
        return !(this$model == null ? other$model != null : !this$model.equals(other$model));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getId();
        String $make = this.getMake();
        result = result * 59 + ($make == null ? 43 : $make.hashCode());
        String $model = this.getModel();
        result = result * 59 + ($model == null ? 43 : $model.hashCode());
        return result;
    }

    public String toString() {
        return "MakeModelDTO(id=" + this.getId() + ", make=" + this.getMake() + ", model=" + this.getModel() + ")";
    }
}

