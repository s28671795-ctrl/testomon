/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.models.media;

import io.swagger.v3.oas.models.annotations.OpenAPI31;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Encoding;
import io.swagger.v3.oas.models.media.Schema;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class MediaType {
    private Schema schema = null;
    private Map<String, Example> examples = null;
    private Object example = null;
    private Map<String, Encoding> encoding = null;
    private Map<String, Object> extensions = null;
    private boolean exampleSetFlag;

    public Schema getSchema() {
        return this.schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    public MediaType schema(Schema schema) {
        this.schema = schema;
        return this;
    }

    public Map<String, Example> getExamples() {
        return this.examples;
    }

    public void setExamples(Map<String, Example> examples) {
        this.examples = examples;
    }

    public MediaType examples(Map<String, Example> examples) {
        this.examples = examples;
        return this;
    }

    public MediaType addExamples(String key, Example examplesItem) {
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
        if (this.schema == null) {
            this.example = example;
            this.exampleSetFlag = true;
            return;
        }
        this.example = this.schema.cast(example);
        if (example == null || this.example != null) {
            this.exampleSetFlag = true;
        }
    }

    public MediaType example(Object example) {
        this.setExample(example);
        return this;
    }

    public Map<String, Encoding> getEncoding() {
        return this.encoding;
    }

    public void setEncoding(Map<String, Encoding> encoding) {
        this.encoding = encoding;
    }

    public MediaType encoding(Map<String, Encoding> encoding) {
        this.encoding = encoding;
        return this;
    }

    public MediaType addEncoding(String key, Encoding encodingItem) {
        if (this.encoding == null) {
            this.encoding = new LinkedHashMap<String, Encoding>();
        }
        this.encoding.put(key, encodingItem);
        return this;
    }

    public boolean getExampleSetFlag() {
        return this.exampleSetFlag;
    }

    public void setExampleSetFlag(boolean exampleSetFlag) {
        this.exampleSetFlag = exampleSetFlag;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        MediaType mediaType = (MediaType)o;
        return Objects.equals(this.schema, mediaType.schema) && Objects.equals(this.examples, mediaType.examples) && Objects.equals(this.example, mediaType.example) && Objects.equals(this.encoding, mediaType.encoding) && Objects.equals(this.extensions, mediaType.extensions);
    }

    public int hashCode() {
        return Objects.hash(this.schema, this.examples, this.example, this.encoding, this.extensions);
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

    public MediaType extensions(Map<String, Object> extensions) {
        this.extensions = extensions;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class MediaType {\n");
        sb.append("    schema: ").append(this.toIndentedString(this.schema)).append("\n");
        sb.append("    examples: ").append(this.toIndentedString(this.examples)).append("\n");
        sb.append("    example: ").append(this.toIndentedString(this.example)).append("\n");
        sb.append("    encoding: ").append(this.toIndentedString(this.encoding)).append("\n");
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

