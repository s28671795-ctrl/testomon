/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.license;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.dto.license.validations.LicenseKeyIsWrong;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;

@LicenseKeyIsWrong
@Schema(name="ActivateWithKeyDTO", description="Activate with key class structure")
public class ActivateWithKeyDTO {
    @JsonProperty(value="key")
    @JSONField(name="key")
    @Schema(name="key", description="License key", implementation=String.class, example="IP9N5-WKFP1-J124I-JW5JX-P4YBP")
    @NotBlank
    private String key;

    public String getKey() {
        return this.key;
    }

    @JsonProperty(value="key")
    public void setKey(String key) {
        this.key = key;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ActivateWithKeyDTO)) {
            return false;
        }
        ActivateWithKeyDTO other = (ActivateWithKeyDTO)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$key = this.getKey();
        String other$key = other.getKey();
        return !(this$key == null ? other$key != null : !this$key.equals(other$key));
    }

    protected boolean canEqual(Object other) {
        return other instanceof ActivateWithKeyDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $key = this.getKey();
        result = result * 59 + ($key == null ? 43 : $key.hashCode());
        return result;
    }

    public String toString() {
        return "ActivateWithKeyDTO(key=" + this.getKey() + ")";
    }

    public ActivateWithKeyDTO(String key) {
        this.key = key;
    }

    public ActivateWithKeyDTO() {
    }

    public static final class Fields {
        public static final String key = "key";
    }
}

