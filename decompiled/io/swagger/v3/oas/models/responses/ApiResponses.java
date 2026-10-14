/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.models.responses;

import io.swagger.v3.oas.models.annotations.OpenAPI31;
import io.swagger.v3.oas.models.responses.ApiResponse;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class ApiResponses
extends LinkedHashMap<String, ApiResponse> {
    public static final String DEFAULT = "default";
    private Map<String, Object> extensions = null;

    public ApiResponses addApiResponse(String name, ApiResponse item) {
        this.put(name, item);
        return this;
    }

    @Deprecated
    public ApiResponse getDefault() {
        return (ApiResponse)this.get(DEFAULT);
    }

    @Deprecated
    public void setDefault(ApiResponse _default) {
        this.addApiResponse(DEFAULT, _default);
    }

    @Deprecated
    public ApiResponses _default(ApiResponse _default) {
        this.setDefault(_default);
        return this;
    }

    public Map<String, Object> getExtensions() {
        return this.extensions;
    }

    public void addExtension(String name, Object value) {
        if (name == null || name.isEmpty() || !name.startsWith("x-")) {
            return;
        }
        if (this.extensions == null) {
            this.extensions = new LinkedHashMap<String, Object>();
        }
        this.extensions.put(name, value);
    }

    @OpenAPI31
    public void addExtension31(String name, Object value) {
        if (name != null && (name.startsWith("x-oas-") || name.startsWith("x-oai-"))) {
            return;
        }
        this.addExtension(name, value);
    }

    public void setExtensions(Map<String, Object> extensions) {
        this.extensions = extensions;
    }

    public ApiResponses extensions(Map<String, Object> extensions) {
        this.extensions = extensions;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        ApiResponses apiResponses = (ApiResponses)o;
        return Objects.equals(this.extensions, apiResponses.extensions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.extensions);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ApiResponses {\n");
        sb.append("    ").append(this.toIndentedString(super.toString())).append("\n");
        sb.append("    extensions: ").append(this.toIndentedString(this.extensions)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

