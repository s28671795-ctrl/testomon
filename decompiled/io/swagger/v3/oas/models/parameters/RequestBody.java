/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.models.parameters;

import io.swagger.v3.oas.models.annotations.OpenAPI31;
import io.swagger.v3.oas.models.media.Content;
import java.util.LinkedHashMap;
import java.util.Map;

public class RequestBody {
    private String description = null;
    private Content content = null;
    private Boolean required = null;
    private Map<String, Object> extensions = null;
    private String $ref = null;

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RequestBody description(String description) {
        this.description = description;
        return this;
    }

    public Content getContent() {
        return this.content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public RequestBody content(Content content) {
        this.content = content;
        return this;
    }

    public Boolean getRequired() {
        return this.required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public RequestBody required(Boolean required) {
        this.required = required;
        return this;
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

    public RequestBody extensions(Map<String, Object> extensions) {
        this.extensions = extensions;
        return this;
    }

    public String get$ref() {
        return this.$ref;
    }

    public void set$ref(String $ref) {
        if ($ref != null && $ref.indexOf(46) == -1 && $ref.indexOf(47) == -1) {
            $ref = "#/components/requestBodies/" + $ref;
        }
        this.$ref = $ref;
    }

    public RequestBody $ref(String $ref) {
        this.set$ref($ref);
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequestBody)) {
            return false;
        }
        RequestBody that = (RequestBody)o;
        if (this.description != null ? !this.description.equals(that.description) : that.description != null) {
            return false;
        }
        if (this.content != null ? !this.content.equals(that.content) : that.content != null) {
            return false;
        }
        if (this.required != null ? !this.required.equals(that.required) : that.required != null) {
            return false;
        }
        if (this.extensions != null ? !this.extensions.equals(that.extensions) : that.extensions != null) {
            return false;
        }
        return this.$ref != null ? this.$ref.equals(that.$ref) : that.$ref == null;
    }

    public int hashCode() {
        int result = this.description != null ? this.description.hashCode() : 0;
        result = 31 * result + (this.content != null ? this.content.hashCode() : 0);
        result = 31 * result + (this.required != null ? this.required.hashCode() : 0);
        result = 31 * result + (this.extensions != null ? this.extensions.hashCode() : 0);
        result = 31 * result + (this.$ref != null ? this.$ref.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class RequestBody {\n");
        sb.append("    description: ").append(this.toIndentedString(this.description)).append("\n");
        sb.append("    content: ").append(this.toIndentedString(this.content)).append("\n");
        sb.append("    required: ").append(this.toIndentedString(this.required)).append("\n");
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

