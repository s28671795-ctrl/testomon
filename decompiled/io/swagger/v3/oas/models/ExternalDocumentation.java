/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.models;

import io.swagger.v3.oas.models.annotations.OpenAPI31;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class ExternalDocumentation {
    private String description = null;
    private String url = null;
    private Map<String, Object> extensions = null;

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ExternalDocumentation description(String description) {
        this.description = description;
        return this;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url2) {
        this.url = url2;
    }

    public ExternalDocumentation url(String url2) {
        this.url = url2;
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        ExternalDocumentation externalDocumentation = (ExternalDocumentation)o;
        return Objects.equals(this.description, externalDocumentation.description) && Objects.equals(this.url, externalDocumentation.url) && Objects.equals(this.extensions, externalDocumentation.extensions);
    }

    public int hashCode() {
        return Objects.hash(this.description, this.url, this.extensions);
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

    public ExternalDocumentation extensions(Map<String, Object> extensions) {
        this.extensions = extensions;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ExternalDocumentation {\n");
        sb.append("    description: ").append(this.toIndentedString(this.description)).append("\n");
        sb.append("    url: ").append(this.toIndentedString(this.url)).append("\n");
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

