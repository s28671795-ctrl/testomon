/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.models.responses;

import io.swagger.v3.oas.models.annotations.OpenAPI31;
import io.swagger.v3.oas.models.headers.Header;
import io.swagger.v3.oas.models.links.Link;
import io.swagger.v3.oas.models.media.Content;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class ApiResponse {
    private String description = null;
    private Map<String, Header> headers = null;
    private Content content = null;
    private Map<String, Link> links = null;
    private Map<String, Object> extensions = null;
    private String $ref = null;

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ApiResponse description(String description) {
        this.description = description;
        return this;
    }

    public Map<String, Header> getHeaders() {
        return this.headers;
    }

    public void setHeaders(Map<String, Header> headers) {
        this.headers = headers;
    }

    public ApiResponse headers(Map<String, Header> headers) {
        this.headers = headers;
        return this;
    }

    public ApiResponse addHeaderObject(String name, Header header) {
        if (this.headers == null) {
            this.headers = new LinkedHashMap<String, Header>();
        }
        this.headers.put(name, header);
        return this;
    }

    public Content getContent() {
        return this.content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public ApiResponse content(Content content) {
        this.content = content;
        return this;
    }

    public Map<String, Link> getLinks() {
        return this.links;
    }

    public void setLinks(Map<String, Link> links) {
        this.links = links;
    }

    public ApiResponse links(Map<String, Link> links) {
        this.links = links;
        return this;
    }

    public ApiResponse addLink(String name, Link link) {
        if (this.links == null) {
            this.links = new LinkedHashMap<String, Link>();
        }
        this.links.put(name, link);
        return this;
    }

    public ApiResponse link(String name, Link link) {
        return this.addLink(name, link);
    }

    public String get$ref() {
        return this.$ref;
    }

    public void set$ref(String $ref) {
        if ($ref != null && $ref.indexOf(46) == -1 && $ref.indexOf(47) == -1) {
            $ref = "#/components/responses/" + $ref;
        }
        this.$ref = $ref;
    }

    public ApiResponse $ref(String $ref) {
        this.set$ref($ref);
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        ApiResponse apiResponse = (ApiResponse)o;
        return Objects.equals(this.description, apiResponse.description) && Objects.equals(this.headers, apiResponse.headers) && Objects.equals(this.content, apiResponse.content) && Objects.equals(this.links, apiResponse.links) && Objects.equals(this.extensions, apiResponse.extensions) && Objects.equals(this.$ref, apiResponse.$ref);
    }

    public int hashCode() {
        return Objects.hash(this.description, this.headers, this.content, this.links, this.extensions, this.$ref);
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

    public ApiResponse extensions(Map<String, Object> extensions) {
        this.extensions = extensions;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ApiResponse {\n");
        sb.append("    description: ").append(this.toIndentedString(this.description)).append("\n");
        sb.append("    headers: ").append(this.toIndentedString(this.headers)).append("\n");
        sb.append("    content: ").append(this.toIndentedString(this.content)).append("\n");
        sb.append("    links: ").append(this.toIndentedString(this.links)).append("\n");
        sb.append("    extensions: ").append(this.toIndentedString(this.extensions)).append("\n");
        sb.append("    $ref: ").append(this.toIndentedString(this.$ref)).append("\n");
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

