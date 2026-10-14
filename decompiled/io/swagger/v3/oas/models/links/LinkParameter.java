/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.models.links;

import io.swagger.v3.oas.models.annotations.OpenAPI31;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class LinkParameter {
    private String value;
    private Map<String, Object> extensions = null;

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LinkParameter value(String value) {
        this.value = value;
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        LinkParameter linkParameters = (LinkParameter)o;
        return Objects.equals(this.value, linkParameters.value) && Objects.equals(this.extensions, linkParameters.extensions);
    }

    public int hashCode() {
        return Objects.hash(this.value, this.extensions);
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

    public LinkParameter extensions(Map<String, Object> extensions) {
        this.extensions = extensions;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class LinkParameter {\n");
        sb.append("}");
        return sb.toString();
    }
}

