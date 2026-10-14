/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.models;

import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.annotations.OpenAPI31;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class Paths
extends LinkedHashMap<String, PathItem> {
    private Map<String, Object> extensions = null;

    public Paths addPathItem(String name, PathItem item) {
        this.put(name, item);
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
        Paths paths = (Paths)o;
        return Objects.equals(this.extensions, paths.extensions) && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.extensions, super.hashCode());
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

    public Paths extensions(Map<String, Object> extensions) {
        this.extensions = extensions;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Paths {\n");
        sb.append("    ").append(this.toIndentedString(super.toString())).append("\n");
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

