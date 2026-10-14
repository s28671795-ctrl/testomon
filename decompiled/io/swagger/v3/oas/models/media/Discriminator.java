/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.models.media;

import io.swagger.v3.oas.models.annotations.OpenAPI31;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class Discriminator {
    private String propertyName;
    private Map<String, String> mapping;
    @OpenAPI31
    private Map<String, Object> extensions;

    public Discriminator propertyName(String propertyName) {
        this.propertyName = propertyName;
        return this;
    }

    public String getPropertyName() {
        return this.propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public Discriminator mapping(String name, String value) {
        if (this.mapping == null) {
            this.mapping = new LinkedHashMap<String, String>();
        }
        this.mapping.put(name, value);
        return this;
    }

    public Discriminator mapping(Map<String, String> mapping) {
        this.mapping = mapping;
        return this;
    }

    public Map<String, String> getMapping() {
        return this.mapping;
    }

    public void setMapping(Map<String, String> mapping) {
        this.mapping = mapping;
    }

    @OpenAPI31
    public Map<String, Object> getExtensions() {
        return this.extensions;
    }

    @OpenAPI31
    public void setExtensions(Map<String, Object> extensions) {
        this.extensions = extensions;
    }

    @OpenAPI31
    public void addExtension(String name, Object value) {
        if (name == null || name.isEmpty() || !name.startsWith("x-")) {
            return;
        }
        if (name.startsWith("x-oas-") || name.startsWith("x-oai-")) {
            return;
        }
        if (this.extensions == null) {
            this.extensions = new LinkedHashMap<String, Object>();
        }
        this.extensions.put(name, value);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Discriminator)) {
            return false;
        }
        Discriminator that = (Discriminator)o;
        if (this.propertyName != null ? !this.propertyName.equals(that.propertyName) : that.propertyName != null) {
            return false;
        }
        if (this.extensions != null ? !this.extensions.equals(that.extensions) : that.extensions != null) {
            return false;
        }
        return this.mapping != null ? this.mapping.equals(that.mapping) : that.mapping == null;
    }

    public int hashCode() {
        return Objects.hash(this.propertyName, this.mapping, this.extensions);
    }

    public String toString() {
        return "Discriminator{propertyName='" + this.propertyName + '\'' + ", mapping=" + this.mapping + ", extensions=" + this.extensions + '}';
    }
}

