/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.models.media;

import io.swagger.v3.oas.models.annotations.OpenAPI31;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class XML {
    private String name = null;
    private String namespace = null;
    private String prefix = null;
    private Boolean attribute = null;
    private Boolean wrapped = null;
    private Map<String, Object> extensions = null;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public XML name(String name) {
        this.name = name;
        return this;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public XML namespace(String namespace) {
        this.namespace = namespace;
        return this;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public XML prefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public Boolean getAttribute() {
        return this.attribute;
    }

    public void setAttribute(Boolean attribute) {
        this.attribute = attribute;
    }

    public XML attribute(Boolean attribute) {
        this.attribute = attribute;
        return this;
    }

    public Boolean getWrapped() {
        return this.wrapped;
    }

    public void setWrapped(Boolean wrapped) {
        this.wrapped = wrapped;
    }

    public XML wrapped(Boolean wrapped) {
        this.wrapped = wrapped;
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        XML XML2 = (XML)o;
        return Objects.equals(this.name, XML2.name) && Objects.equals(this.namespace, XML2.namespace) && Objects.equals(this.prefix, XML2.prefix) && Objects.equals(this.attribute, XML2.attribute) && Objects.equals(this.wrapped, XML2.wrapped) && Objects.equals(this.extensions, XML2.extensions);
    }

    public int hashCode() {
        return Objects.hash(this.name, this.namespace, this.prefix, this.attribute, this.wrapped, this.extensions);
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

    public XML extensions(Map<String, Object> extensions) {
        this.extensions = extensions;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class XML {\n");
        sb.append("    name: ").append(this.toIndentedString(this.name)).append("\n");
        sb.append("    namespace: ").append(this.toIndentedString(this.namespace)).append("\n");
        sb.append("    prefix: ").append(this.toIndentedString(this.prefix)).append("\n");
        sb.append("    attribute: ").append(this.toIndentedString(this.attribute)).append("\n");
        sb.append("    wrapped: ").append(this.toIndentedString(this.wrapped)).append("\n");
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

