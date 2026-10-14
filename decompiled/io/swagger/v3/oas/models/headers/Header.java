/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.models.headers;

import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.Schema;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class Header {
    private String description = null;
    private String $ref = null;
    private Boolean required = null;
    private Boolean deprecated = null;
    private StyleEnum style = null;
    private Boolean explode = null;
    private Schema schema = null;
    private Map<String, Example> examples = null;
    private Object example = null;
    private Content content = null;
    private Map<String, Object> extensions = null;

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Header description(String description) {
        this.description = description;
        return this;
    }

    public Boolean getRequired() {
        return this.required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Header required(Boolean required) {
        this.required = required;
        return this;
    }

    public Boolean getDeprecated() {
        return this.deprecated;
    }

    public void setDeprecated(Boolean deprecated2) {
        this.deprecated = deprecated2;
    }

    public Header deprecated(Boolean deprecated2) {
        this.deprecated = deprecated2;
        return this;
    }

    public StyleEnum getStyle() {
        return this.style;
    }

    public void setStyle(StyleEnum style) {
        this.style = style;
    }

    public Header style(StyleEnum style) {
        this.style = style;
        return this;
    }

    public Boolean getExplode() {
        return this.explode;
    }

    public void setExplode(Boolean explode) {
        this.explode = explode;
    }

    public Header explode(Boolean explode) {
        this.explode = explode;
        return this;
    }

    public Schema getSchema() {
        return this.schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    public Header schema(Schema schema) {
        this.schema = schema;
        return this;
    }

    public Map<String, Example> getExamples() {
        return this.examples;
    }

    public void setExamples(Map<String, Example> examples) {
        this.examples = examples;
    }

    public Header examples(Map<String, Example> examples) {
        this.examples = examples;
        return this;
    }

    public Header addExample(String key, Example examplesItem) {
        if (this.examples == null) {
            this.examples = new LinkedHashMap<String, Example>();
        }
        this.examples.put(key, examplesItem);
        return this;
    }

    public Object getExample() {
        return this.example;
    }

    public void setExample(Object example) {
        this.example = example;
    }

    public Header example(Object example) {
        this.example = example;
        return this;
    }

    public Content getContent() {
        return this.content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Header content(Content content) {
        this.content = content;
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Header header = (Header)o;
        return Objects.equals(this.description, header.description) && Objects.equals(this.required, header.required) && Objects.equals(this.deprecated, header.deprecated) && Objects.equals((Object)this.style, (Object)header.style) && Objects.equals(this.explode, header.explode) && Objects.equals(this.schema, header.schema) && Objects.equals(this.examples, header.examples) && Objects.equals(this.example, header.example) && Objects.equals(this.content, header.content) && Objects.equals(this.extensions, header.extensions) && Objects.equals(this.$ref, header.$ref);
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.description, this.required, this.deprecated, this.style, this.explode, this.schema, this.examples, this.example, this.content, this.extensions, this.$ref});
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

    public void setExtensions(Map<String, Object> extensions) {
        this.extensions = extensions;
    }

    public Header extensions(Map<String, Object> extensions) {
        this.extensions = extensions;
        return this;
    }

    public String get$ref() {
        return this.$ref;
    }

    public void set$ref(String $ref) {
        if ($ref != null && $ref.indexOf(46) == -1 && $ref.indexOf(47) == -1) {
            $ref = "#/components/headers/" + $ref;
        }
        this.$ref = $ref;
    }

    public Header $ref(String $ref) {
        this.set$ref($ref);
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Header {\n");
        sb.append("    description: ").append(this.toIndentedString(this.description)).append("\n");
        sb.append("    required: ").append(this.toIndentedString(this.required)).append("\n");
        sb.append("    deprecated: ").append(this.toIndentedString(this.deprecated)).append("\n");
        sb.append("    style: ").append(this.toIndentedString((Object)this.style)).append("\n");
        sb.append("    explode: ").append(this.toIndentedString(this.explode)).append("\n");
        sb.append("    schema: ").append(this.toIndentedString(this.schema)).append("\n");
        sb.append("    examples: ").append(this.toIndentedString(this.examples)).append("\n");
        sb.append("    example: ").append(this.toIndentedString(this.example)).append("\n");
        sb.append("    content: ").append(this.toIndentedString(this.content)).append("\n");
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

    public static enum StyleEnum {
        SIMPLE("simple");

        private String value;

        private StyleEnum(String value) {
            this.value = value;
        }

        public String toString() {
            return String.valueOf(this.value);
        }
    }
}

