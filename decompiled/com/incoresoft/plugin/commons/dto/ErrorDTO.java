/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;

public final class ErrorDTO {
    @JSONField(name="type")
    @JsonProperty(value="type")
    @Schema(name="type", description="Error type", implementation=String.class, example="ERROR")
    private final String type;
    @JSONField(name="field")
    @JsonProperty(value="field")
    @Schema(name="field", description="Error field", implementation=String.class, example="some_field")
    private final String field;
    @JSONField(name="args")
    @JsonProperty(value="args")
    @ArraySchema(schema=@Schema(name="args", description="Error args", implementation=String.class, example="some arg"))
    private final Object[] args;

    public ErrorDTO(String type, Object ... args2) {
        this(type, null, args2);
    }

    @JsonCreator
    public ErrorDTO(@JsonProperty(value="type") String type, @JsonProperty(value="field") String field2, @JsonProperty(value="args") Object[] args2) {
        this.type = type;
        this.field = field2;
        this.args = args2;
    }

    public String getType() {
        return this.type;
    }

    public String getField() {
        return this.field;
    }

    public Object[] getArgs() {
        return this.args;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ErrorDTO)) {
            return false;
        }
        ErrorDTO other = (ErrorDTO)o;
        String this$type = this.getType();
        String other$type = other.getType();
        if (this$type == null ? other$type != null : !this$type.equals(other$type)) {
            return false;
        }
        String this$field = this.getField();
        String other$field = other.getField();
        if (this$field == null ? other$field != null : !this$field.equals(other$field)) {
            return false;
        }
        return Arrays.deepEquals(this.getArgs(), other.getArgs());
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $type = this.getType();
        result = result * 59 + ($type == null ? 43 : $type.hashCode());
        String $field = this.getField();
        result = result * 59 + ($field == null ? 43 : $field.hashCode());
        result = result * 59 + Arrays.deepHashCode(this.getArgs());
        return result;
    }

    public String toString() {
        return "ErrorDTO(type=" + this.getType() + ", field=" + this.getField() + ", args=" + Arrays.deepToString(this.getArgs()) + ")";
    }
}

