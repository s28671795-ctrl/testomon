/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.models.tags;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.annotations.OpenAPI31;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class Tag {
    private String name = null;
    private String description = null;
    private ExternalDocumentation externalDocs = null;
    private Map<String, Object> extensions = null;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Tag name(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Tag description(String description) {
        this.description = description;
        return this;
    }

    public ExternalDocumentation getExternalDocs() {
        return this.externalDocs;
    }

    public void setExternalDocs(ExternalDocumentation externalDocs) {
        this.externalDocs = externalDocs;
    }

    public Tag externalDocs(ExternalDocumentation externalDocs) {
        this.externalDocs = externalDocs;
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Tag tag = (Tag)o;
        return Objects.equals(this.name, tag.name) && Objects.equals(this.description, tag.description) && Objects.equals(this.externalDocs, tag.externalDocs) && Objects.equals(this.extensions, tag.extensions);
    }

    public int hashCode() {
        return Objects.hash(this.name, this.description, this.externalDocs, this.extensions);
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

    public Tag extensions(Map<String, Object> extensions) {
        this.extensions = extensions;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Tag {\n");
        sb.append("    name: ").append(this.toIndentedString(this.name)).append("\n");
        sb.append("    description: ").append(this.toIndentedString(this.description)).append("\n");
        sb.append("    externalDocs: ").append(this.toIndentedString(this.externalDocs)).append("\n");
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

