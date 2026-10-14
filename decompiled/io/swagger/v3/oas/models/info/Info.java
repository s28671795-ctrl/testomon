/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.models.info;

import io.swagger.v3.oas.models.annotations.OpenAPI31;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class Info {
    private String title = null;
    private String description = null;
    private String termsOfService = null;
    private Contact contact = null;
    private License license = null;
    private String version = null;
    private Map<String, Object> extensions = null;
    @OpenAPI31
    private String summary = null;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Info title(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Info description(String description) {
        this.description = description;
        return this;
    }

    public String getTermsOfService() {
        return this.termsOfService;
    }

    public void setTermsOfService(String termsOfService) {
        this.termsOfService = termsOfService;
    }

    public Info termsOfService(String termsOfService) {
        this.termsOfService = termsOfService;
        return this;
    }

    public Contact getContact() {
        return this.contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Info contact(Contact contact) {
        this.contact = contact;
        return this;
    }

    public License getLicense() {
        return this.license;
    }

    public void setLicense(License license) {
        this.license = license;
    }

    public Info license(License license) {
        this.license = license;
        return this;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Info version(String version) {
        this.version = version;
        return this;
    }

    @OpenAPI31
    public String getSummary() {
        return this.summary;
    }

    @OpenAPI31
    public void setSummary(String summary) {
        this.summary = summary;
    }

    @OpenAPI31
    public Info summary(String summary) {
        this.summary = summary;
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Info info = (Info)o;
        return Objects.equals(this.title, info.title) && Objects.equals(this.description, info.description) && Objects.equals(this.termsOfService, info.termsOfService) && Objects.equals(this.contact, info.contact) && Objects.equals(this.license, info.license) && Objects.equals(this.version, info.version) && Objects.equals(this.extensions, info.extensions) && Objects.equals(this.summary, info.summary);
    }

    public int hashCode() {
        return Objects.hash(this.title, this.description, this.termsOfService, this.contact, this.license, this.version, this.extensions, this.summary);
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

    public Info extensions(Map<String, Object> extensions) {
        this.extensions = extensions;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Info {\n");
        sb.append("    title: ").append(this.toIndentedString(this.title)).append("\n");
        sb.append("    description: ").append(this.toIndentedString(this.description)).append("\n");
        sb.append("    summary: ").append(this.toIndentedString(this.summary)).append("\n");
        sb.append("    termsOfService: ").append(this.toIndentedString(this.termsOfService)).append("\n");
        sb.append("    contact: ").append(this.toIndentedString(this.contact)).append("\n");
        sb.append("    license: ").append(this.toIndentedString(this.license)).append("\n");
        sb.append("    version: ").append(this.toIndentedString(this.version)).append("\n");
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

