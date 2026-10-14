/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.models.media;

import io.swagger.v3.oas.models.annotations.OpenAPI31;
import io.swagger.v3.oas.models.headers.Header;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class Encoding {
    private String contentType;
    private Map<String, Header> headers;
    private StyleEnum style;
    private Boolean explode;
    private Boolean allowReserved;
    private Map<String, Object> extensions = null;

    public Encoding contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Encoding headers(Map<String, Header> headers) {
        this.headers = headers;
        return this;
    }

    public Map<String, Header> getHeaders() {
        return this.headers;
    }

    public void setHeaders(Map<String, Header> headers) {
        this.headers = headers;
    }

    public Encoding addHeader(String name, Header header) {
        if (this.headers == null) {
            this.headers = new LinkedHashMap<String, Header>();
        }
        this.headers.put(name, header);
        return this;
    }

    public Encoding style(StyleEnum style) {
        this.style = style;
        return this;
    }

    public StyleEnum getStyle() {
        return this.style;
    }

    public void setStyle(StyleEnum style) {
        this.style = style;
    }

    public Encoding explode(Boolean explode) {
        this.explode = explode;
        return this;
    }

    public Boolean getExplode() {
        return this.explode;
    }

    public void setExplode(Boolean explode) {
        this.explode = explode;
    }

    public Encoding allowReserved(Boolean allowReserved) {
        this.allowReserved = allowReserved;
        return this;
    }

    public Boolean getAllowReserved() {
        return this.allowReserved;
    }

    public void setAllowReserved(Boolean allowReserved) {
        this.allowReserved = allowReserved;
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

    public Encoding extensions(Map<String, Object> extensions) {
        this.extensions = extensions;
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Encoding encoding = (Encoding)o;
        return Objects.equals(this.contentType, encoding.contentType) && Objects.equals(this.headers, encoding.headers) && Objects.equals((Object)this.style, (Object)encoding.style) && Objects.equals(this.explode, encoding.explode) && Objects.equals(this.extensions, encoding.extensions) && Objects.equals(this.allowReserved, encoding.allowReserved);
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.contentType, this.headers, this.style, this.explode, this.allowReserved, this.extensions});
    }

    public String toString() {
        return "Encoding{contentType='" + this.contentType + '\'' + ", headers=" + this.headers + ", style='" + (Object)((Object)this.style) + '\'' + ", explode=" + this.explode + ", allowReserved=" + this.allowReserved + ", extensions=" + this.extensions + '}';
    }

    public static enum StyleEnum {
        FORM("form"),
        SPACE_DELIMITED("spaceDelimited"),
        PIPE_DELIMITED("pipeDelimited"),
        DEEP_OBJECT("deepObject");

        private String value;

        private StyleEnum(String value) {
            this.value = value;
        }

        public String toString() {
            return String.valueOf(this.value);
        }

        public static StyleEnum fromString(String value) {
            for (StyleEnum e : StyleEnum.values()) {
                if (!e.value.equals(value)) continue;
                return e;
            }
            return null;
        }
    }
}

