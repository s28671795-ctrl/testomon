/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.models.examples;

import io.swagger.v3.oas.models.annotations.OpenAPI31;
import java.util.LinkedHashMap;
import java.util.Map;

public class Example {
    private String summary = null;
    private String description = null;
    private Object value = null;
    private String externalValue = null;
    private String $ref = null;
    private Map<String, Object> extensions = null;
    private boolean valueSetFlag;

    public String getSummary() {
        return this.summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Example summary(String summary) {
        this.summary = summary;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Example description(String description) {
        this.description = description;
        return this;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
        this.valueSetFlag = true;
    }

    public Example value(Object value) {
        this.setValue(value);
        return this;
    }

    public String getExternalValue() {
        return this.externalValue;
    }

    public void setExternalValue(String externalValue) {
        this.externalValue = externalValue;
    }

    public Example externalValue(String externalValue) {
        this.externalValue = externalValue;
        return this;
    }

    public String get$ref() {
        return this.$ref;
    }

    public void set$ref(String $ref) {
        if ($ref != null && $ref.indexOf(46) == -1 && $ref.indexOf(47) == -1) {
            $ref = "#/components/examples/" + $ref;
        }
        this.$ref = $ref;
    }

    public Example $ref(String $ref) {
        this.set$ref($ref);
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

    public Example extensions(Map<String, Object> extensions) {
        this.extensions = extensions;
        return this;
    }

    public boolean getValueSetFlag() {
        return this.valueSetFlag;
    }

    public void setValueSetFlag(boolean valueSetFlag) {
        this.valueSetFlag = valueSetFlag;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Example)) {
            return false;
        }
        Example example = (Example)o;
        if (this.summary != null ? !this.summary.equals(example.summary) : example.summary != null) {
            return false;
        }
        if (this.description != null ? !this.description.equals(example.description) : example.description != null) {
            return false;
        }
        if (this.value != null ? !this.value.equals(example.value) : example.value != null) {
            return false;
        }
        if (this.externalValue != null ? !this.externalValue.equals(example.externalValue) : example.externalValue != null) {
            return false;
        }
        if (this.$ref != null ? !this.$ref.equals(example.$ref) : example.$ref != null) {
            return false;
        }
        return this.extensions != null ? this.extensions.equals(example.extensions) : example.extensions == null;
    }

    public int hashCode() {
        int result = this.summary != null ? this.summary.hashCode() : 0;
        result = 31 * result + (this.description != null ? this.description.hashCode() : 0);
        result = 31 * result + (this.value != null ? this.value.hashCode() : 0);
        result = 31 * result + (this.externalValue != null ? this.externalValue.hashCode() : 0);
        result = 31 * result + (this.$ref != null ? this.$ref.hashCode() : 0);
        result = 31 * result + (this.extensions != null ? this.extensions.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Example {\n");
        sb.append("    summary: ").append(this.toIndentedString(this.summary)).append("\n");
        sb.append("    description: ").append(this.toIndentedString(this.description)).append("\n");
        sb.append("    value: ").append(this.toIndentedString(this.value)).append("\n");
        sb.append("    externalValue: ").append(this.toIndentedString(this.externalValue)).append("\n");
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

