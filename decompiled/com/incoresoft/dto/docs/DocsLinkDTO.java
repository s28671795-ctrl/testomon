/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.docs;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="DocsLinkDTO", description="Plugin API documentation link class structure")
public final class DocsLinkDTO {
    @JsonProperty(value="name")
    @JSONField(name="name")
    @Schema(name="name", description="Module name", example="Middleware-Core")
    private final String name;
    @JsonProperty(value="swagger")
    @JSONField(name="swagger")
    @Schema(name="swagger", description="Swagger documentation link", example="swagger/core")
    private final String swaggerLink;

    public DocsLinkDTO(String name, String swaggerLink) {
        this.name = name;
        this.swaggerLink = swaggerLink;
    }

    public String getName() {
        return this.name;
    }

    public String getSwaggerLink() {
        return this.swaggerLink;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DocsLinkDTO)) {
            return false;
        }
        DocsLinkDTO other = (DocsLinkDTO)o;
        String this$name = this.getName();
        String other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        String this$swaggerLink = this.getSwaggerLink();
        String other$swaggerLink = other.getSwaggerLink();
        return !(this$swaggerLink == null ? other$swaggerLink != null : !this$swaggerLink.equals(other$swaggerLink));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        String $swaggerLink = this.getSwaggerLink();
        result = result * 59 + ($swaggerLink == null ? 43 : $swaggerLink.hashCode());
        return result;
    }

    public String toString() {
        return "DocsLinkDTO(name=" + this.getName() + ", swaggerLink=" + this.getSwaggerLink() + ")";
    }
}

