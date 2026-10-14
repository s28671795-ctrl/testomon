/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.alpr.country;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.middleware.analytics.models.alpr.countrypatterns.Country;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="CountryDTO", description="Country class structure")
public final class CountryDTO {
    @JSONField(name="code")
    @JsonProperty(value="code")
    @Schema(name="code", description="Country code", example="UA")
    private final String code;
    @JSONField(name="name")
    @JsonProperty(value="name")
    @Schema(name="name", description="Country name", example="Ukraine")
    private final String name;

    public CountryDTO(Country entity) {
        this.code = entity.getCode();
        this.name = entity.getName();
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CountryDTO)) {
            return false;
        }
        CountryDTO other = (CountryDTO)o;
        String this$code = this.getCode();
        String other$code = other.getCode();
        if (this$code == null ? other$code != null : !this$code.equals(other$code)) {
            return false;
        }
        String this$name = this.getName();
        String other$name = other.getName();
        return !(this$name == null ? other$name != null : !this$name.equals(other$name));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $code = this.getCode();
        result = result * 59 + ($code == null ? 43 : $code.hashCode());
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        return result;
    }

    public String toString() {
        return "CountryDTO(code=" + this.getCode() + ", name=" + this.getName() + ")";
    }

    public CountryDTO(String code, String name) {
        this.code = code;
        this.name = name;
    }
}

