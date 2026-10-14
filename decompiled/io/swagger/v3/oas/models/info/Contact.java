/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.models.info;

import io.swagger.v3.oas.models.annotations.OpenAPI31;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class Contact {
    private String name = null;
    private String url = null;
    private String email = null;
    private Map<String, Object> extensions = null;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Contact name(String name) {
        this.name = name;
        return this;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url2) {
        this.url = url2;
    }

    public Contact url(String url2) {
        this.url = url2;
        return this;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Contact email(String email) {
        this.email = email;
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Contact contact = (Contact)o;
        return Objects.equals(this.name, contact.name) && Objects.equals(this.url, contact.url) && Objects.equals(this.email, contact.email) && Objects.equals(this.extensions, contact.extensions);
    }

    public int hashCode() {
        return Objects.hash(this.name, this.url, this.email, this.extensions);
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

    public Contact extensions(Map<String, Object> extensions) {
        this.extensions = extensions;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Contact {\n");
        sb.append("    name: ").append(this.toIndentedString(this.name)).append("\n");
        sb.append("    url: ").append(this.toIndentedString(this.url)).append("\n");
        sb.append("    email: ").append(this.toIndentedString(this.email)).append("\n");
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

