/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.models.media;

import io.swagger.v3.oas.models.annotations.OpenAPI31;
import io.swagger.v3.oas.models.headers.Header;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class EncodingProperty {
    private String contentType = null;
    private Map<String, Header> headers = null;
    private StyleEnum style = null;
    private Boolean explode = null;
    private Boolean allowReserved = null;
    private Map<String, Object> extensions = null;

    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public EncodingProperty contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public Map<String, Header> getHeaders() {
        return this.headers;
    }

    public void setHeaders(Map<String, Header> headers) {
        this.headers = headers;
    }

    public EncodingProperty headers(Map<String, Header> headers) {
        this.headers = headers;
        return this;
    }

    public EncodingProperty addHeaderObject(String name, Header header) {
        if (this.headers == null) {
            this.headers = new LinkedHashMap<String, Header>();
        }
        this.headers.put(name, header);
        return this;
    }

    public StyleEnum getStyle() {
        return this.style;
    }

    public void setStyle(StyleEnum style) {
        this.style = style;
    }

    public EncodingProperty style(StyleEnum style) {
        this.style = style;
        return this;
    }

    public Boolean getExplode() {
        return this.explode;
    }

    public void setExplode(Boolean explode) {
        this.explode = explode;
    }

    public EncodingProperty explode(Boolean explode) {
        this.explode = explode;
        return this;
    }

    public Boolean getAllowReserved() {
        return this.allowReserved;
    }

    public void setAllowReserved(Boolean allowReserved) {
        this.allowReserved = allowReserved;
    }

    public EncodingProperty allowReserved(Boolean allowReserved) {
        this.allowReserved = allowReserved;
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        EncodingProperty encodingProperty = (EncodingProperty)o;
        return Objects.equals(this.contentType, encodingProperty.contentType) && Objects.equals(this.headers, encodingProperty.headers) && Objects.equals((Object)this.style, (Object)encodingProperty.style) && Objects.equals(this.explode, encodingProperty.explode) && Objects.equals(this.allowReserved, encodingProperty.allowReserved) && Objects.equals(this.extensions, encodingProperty.extensions);
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.contentType, this.headers, this.style, this.explode, this.allowReserved, this.extensions});
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

    public EncodingProperty extensions(Map<String, Object> extensions) {
        this.extensions = extensions;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class EncodingProperty {\n");
        sb.append("    contentType: ").append(this.toIndentedString(this.contentType)).append("\n");
        sb.append("    headers: ").append(this.toIndentedString(this.headers)).append("\n");
        sb.append("    style: ").append(this.toIndentedString((Object)this.style)).append("\n");
        sb.append("    explode: ").append(this.toIndentedString(this.explode)).append("\n");
        sb.append("    allowReserved: ").append(this.toIndentedString(this.allowReserved)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

    public static enum StyleEnum {
        FORM("form"),
        SPACEDELIMITED("spaceDelimited"),
        PIPEDELIMITED("pipeDelimited"),
        DEEPOBJECT("deepObject");

        private String value;

        private StyleEnum(String value) {
            this.value = value;
        }

        public String toString() {
            return String.valueOf(this.value);
        }
    }
}

