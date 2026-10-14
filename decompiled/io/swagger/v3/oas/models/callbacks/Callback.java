/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.models.callbacks;

import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.annotations.OpenAPI31;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class Callback
extends LinkedHashMap<String, PathItem> {
    private Map<String, Object> extensions = null;
    private String $ref = null;

    public String get$ref() {
        return this.$ref;
    }

    public void set$ref(String $ref) {
        if ($ref != null && $ref.indexOf(46) == -1 && $ref.indexOf(47) == -1) {
            $ref = "#/components/callbacks/" + $ref;
        }
        this.$ref = $ref;
    }

    public Callback $ref(String $ref) {
        this.set$ref($ref);
        return this;
    }

    public Callback addPathItem(String name, PathItem item) {
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
        Callback callback = (Callback)o;
        if (this.$ref != null ? !this.$ref.equals(callback.$ref) : callback.$ref != null) {
            return false;
        }
        return Objects.equals(this.extensions, callback.extensions) && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.extensions, this.$ref, super.hashCode());
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

    public Callback extensions(Map<String, Object> extensions) {
        this.extensions = extensions;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Callback {\n");
        sb.append("    $ref: ").append(this.toIndentedString(this.$ref)).append("\n");
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

