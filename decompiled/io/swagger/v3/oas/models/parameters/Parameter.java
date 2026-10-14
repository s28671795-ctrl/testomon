/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.models.parameters;

import io.swagger.v3.oas.models.annotations.OpenAPI31;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.Schema;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class Parameter {
    private String name = null;
    private String in = null;
    private String description = null;
    private Boolean required = null;
    private Boolean deprecated = null;
    private Boolean allowEmptyValue = null;
    private String $ref = null;
    private StyleEnum style = null;
    private Boolean explode = null;
    private Boolean allowReserved = null;
    private Schema schema = null;
    private Map<String, Example> examples = null;
    private Object example = null;
    private Content content = null;
    private Map<String, Object> extensions = null;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Parameter name(String name) {
        this.name = name;
        return this;
    }

    public String getIn() {
        return this.in;
    }

    public void setIn(String in) {
        if ("path".equals(in)) {
            this.required = true;
        }
        this.in = in;
    }

    public Parameter in(String in) {
        this.setIn(in);
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Parameter description(String description) {
        this.description = description;
        return this;
    }

    public Boolean getRequired() {
        return this.required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Parameter required(Boolean required) {
        this.required = required;
        return this;
    }

    public Boolean getDeprecated() {
        return this.deprecated;
    }

    public void setDeprecated(Boolean deprecated2) {
        this.deprecated = deprecated2;
    }

    public Parameter deprecated(Boolean deprecated2) {
        this.deprecated = deprecated2;
        return this;
    }

    public Boolean getAllowEmptyValue() {
        return this.allowEmptyValue;
    }

    public void setAllowEmptyValue(Boolean allowEmptyValue) {
        this.allowEmptyValue = allowEmptyValue;
    }

    public Parameter allowEmptyValue(Boolean allowEmptyValue) {
        this.allowEmptyValue = allowEmptyValue;
        return this;
    }

    public StyleEnum getStyle() {
        return this.style;
    }

    public void setStyle(StyleEnum style) {
        this.style = style;
    }

    public Parameter style(StyleEnum style) {
        this.style = style;
        return this;
    }

    public Boolean getExplode() {
        return this.explode;
    }

    public void setExplode(Boolean explode) {
        this.explode = explode;
    }

    public Parameter explode(Boolean explode) {
        this.explode = explode;
        return this;
    }

    public Boolean getAllowReserved() {
        return this.allowReserved;
    }

    public void setAllowReserved(Boolean allowReserved) {
        this.allowReserved = allowReserved;
    }

    public Parameter allowReserved(Boolean allowReserved) {
        this.allowReserved = allowReserved;
        return this;
    }

    public Schema getSchema() {
        return this.schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    public Parameter schema(Schema schema) {
        this.schema = schema;
        return this;
    }

    public Map<String, Example> getExamples() {
        return this.examples;
    }

    public void setExamples(Map<String, Example> examples) {
        this.examples = examples;
    }

    public Parameter examples(Map<String, Example> examples) {
        this.examples = examples;
        return this;
    }

    public Parameter addExample(String key, Example examplesItem) {
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

    public Parameter example(Object example) {
        this.example = example;
        return this;
    }

    public Content getContent() {
        return this.content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Parameter content(Content content) {
        this.content = content;
        return this;
    }

    public String get$ref() {
        return this.$ref;
    }

    public void set$ref(String $ref) {
        if ($ref != null && $ref.indexOf(46) == -1 && $ref.indexOf(47) == -1) {
            $ref = "#/components/parameters/" + $ref;
        }
        this.$ref = $ref;
    }

    public Parameter $ref(String $ref) {
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
        Parameter parameter = (Parameter)o;
        return Objects.equals(this.name, parameter.name) && Objects.equals(this.in, parameter.in) && Objects.equals(this.description, parameter.description) && Objects.equals(this.required, parameter.required) && Objects.equals(this.deprecated, parameter.deprecated) && Objects.equals(this.allowEmptyValue, parameter.allowEmptyValue) && Objects.equals((Object)this.style, (Object)parameter.style) && Objects.equals(this.explode, parameter.explode) && Objects.equals(this.allowReserved, parameter.allowReserved) && Objects.equals(this.schema, parameter.schema) && Objects.equals(this.examples, parameter.examples) && Objects.equals(this.example, parameter.example) && Objects.equals(this.content, parameter.content) && Objects.equals(this.$ref, parameter.$ref) && Objects.equals(this.extensions, parameter.extensions);
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.name, this.in, this.description, this.required, this.deprecated, this.allowEmptyValue, this.style, this.explode, this.allowReserved, this.schema, this.examples, this.example, this.content, this.$ref, this.extensions});
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

    public Parameter extensions(Map<String, Object> extensions) {
        this.extensions = extensions;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Parameter {\n");
        sb.append("    name: ").append(Parameter.toIndentedString(this.name)).append("\n");
        sb.append("    in: ").append(Parameter.toIndentedString(this.in)).append("\n");
        sb.append("    description: ").append(Parameter.toIndentedString(this.description)).append("\n");
        sb.append("    required: ").append(Parameter.toIndentedString(this.required)).append("\n");
        sb.append("    deprecated: ").append(Parameter.toIndentedString(this.deprecated)).append("\n");
        sb.append("    allowEmptyValue: ").append(Parameter.toIndentedString(this.allowEmptyValue)).append("\n");
        sb.append("    style: ").append(Parameter.toIndentedString((Object)this.style)).append("\n");
        sb.append("    explode: ").append(Parameter.toIndentedString(this.explode)).append("\n");
        sb.append("    allowReserved: ").append(Parameter.toIndentedString(this.allowReserved)).append("\n");
        sb.append("    schema: ").append(Parameter.toIndentedString(this.schema)).append("\n");
        sb.append("    examples: ").append(Parameter.toIndentedString(this.examples)).append("\n");
        sb.append("    example: ").append(Parameter.toIndentedString(this.example)).append("\n");
        sb.append("    content: ").append(Parameter.toIndentedString(this.content)).append("\n");
        sb.append("    $ref: ").append(Parameter.toIndentedString(this.$ref)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    static String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

    public static enum StyleEnum {
        MATRIX("matrix"),
        LABEL("label"),
        FORM("form"),
        SIMPLE("simple"),
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

