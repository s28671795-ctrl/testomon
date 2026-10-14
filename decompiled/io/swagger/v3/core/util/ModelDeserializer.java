/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.core.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.core.util.Json31;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.BooleanSchema;
import io.swagger.v3.oas.models.media.ComposedSchema;
import io.swagger.v3.oas.models.media.DateSchema;
import io.swagger.v3.oas.models.media.DateTimeSchema;
import io.swagger.v3.oas.models.media.EmailSchema;
import io.swagger.v3.oas.models.media.IntegerSchema;
import io.swagger.v3.oas.models.media.JsonSchema;
import io.swagger.v3.oas.models.media.MapSchema;
import io.swagger.v3.oas.models.media.NumberSchema;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.PasswordSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.media.UUIDSchema;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class ModelDeserializer
extends JsonDeserializer<Schema> {
    protected boolean openapi31 = false;

    @Override
    public Schema deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        String format;
        JsonNode node = (JsonNode)jp.getCodec().readTree(jp);
        Schema schema = null;
        if (this.openapi31) {
            schema = this.deserializeJsonSchema(node);
            return schema;
        }
        if (node.isBoolean()) {
            return new Schema().booleanSchemaValue(node.booleanValue());
        }
        List<String> composed = Arrays.asList("allOf", "anyOf", "oneOf");
        for (String field2 : composed) {
            if (node.get(field2) == null) continue;
            return Json.mapper().convertValue((Object)node, ComposedSchema.class);
        }
        JsonNode type = node.get("type");
        String string = format = node.get("format") == null ? "" : node.get("format").textValue();
        if (type != null && "array".equals(((TextNode)type).textValue())) {
            schema = Json.mapper().convertValue((Object)node, ArraySchema.class);
        } else if (type != null) {
            if (type.textValue().equals("integer")) {
                schema = Json.mapper().convertValue((Object)node, IntegerSchema.class);
                if (StringUtils.isBlank(format)) {
                    schema.setFormat(null);
                }
            } else if (type.textValue().equals("number")) {
                schema = Json.mapper().convertValue((Object)node, NumberSchema.class);
            } else if (type.textValue().equals("boolean")) {
                schema = Json.mapper().convertValue((Object)node, BooleanSchema.class);
            } else if (type.textValue().equals("string")) {
                schema = "date".equals(format) ? (Schema)Json.mapper().convertValue((Object)node, DateSchema.class) : ("date-time".equals(format) ? (Schema)Json.mapper().convertValue((Object)node, DateTimeSchema.class) : ("email".equals(format) ? (Schema)Json.mapper().convertValue((Object)node, EmailSchema.class) : ("password".equals(format) ? (Schema)Json.mapper().convertValue((Object)node, PasswordSchema.class) : ("uuid".equals(format) ? (Schema)Json.mapper().convertValue((Object)node, UUIDSchema.class) : (Schema)Json.mapper().convertValue((Object)node, StringSchema.class)))));
            } else if (type.textValue().equals("object")) {
                schema = this.deserializeObjectSchema(node);
            }
        } else {
            schema = node.get("$ref") != null ? new Schema().$ref(node.get("$ref").asText()) : this.deserializeObjectSchema(node);
        }
        return schema;
    }

    private Schema deserializeObjectSchema(JsonNode node) {
        JsonNode additionalProperties = node.get("additionalProperties");
        Schema schema = null;
        if (additionalProperties != null) {
            if (additionalProperties.isBoolean()) {
                Boolean additionalPropsBoolean = Json.mapper().convertValue((Object)additionalProperties, Boolean.class);
                ((ObjectNode)node).remove("additionalProperties");
                schema = additionalPropsBoolean != false ? (Schema)Json.mapper().convertValue((Object)node, MapSchema.class) : (Schema)Json.mapper().convertValue((Object)node, ObjectSchema.class);
                schema.setAdditionalProperties(additionalPropsBoolean);
            } else {
                Schema innerSchema = Json.mapper().convertValue((Object)additionalProperties, Schema.class);
                ((ObjectNode)node).remove("additionalProperties");
                MapSchema ms = Json.mapper().convertValue((Object)node, MapSchema.class);
                ms.setAdditionalProperties(innerSchema);
                schema = ms;
            }
        } else {
            schema = Json.mapper().convertValue((Object)node, ObjectSchema.class);
        }
        if (schema != null) {
            schema.jsonSchema(Json31.jsonSchemaAsMap(node));
        }
        return schema;
    }

    private Schema deserializeJsonSchema(JsonNode node) {
        Schema schema;
        block12: {
            if (node.isBoolean()) {
                return new Schema().booleanSchemaValue(node.booleanValue());
            }
            JsonNode additionalProperties = node.get("additionalProperties");
            JsonNode type = node.get("type");
            schema = null;
            if (type != null || additionalProperties != null) {
                if (type != null) {
                    ((ObjectNode)node).remove("type");
                }
                if (additionalProperties != null) {
                    ((ObjectNode)node).remove("additionalProperties");
                }
                schema = Json31.mapper().convertValue((Object)node, JsonSchema.class);
                if (type instanceof TextNode) {
                    schema.types(new LinkedHashSet<String>(Arrays.asList(type.textValue())));
                } else if (type instanceof ArrayNode) {
                    LinkedHashSet<String> types = new LinkedHashSet<String>();
                    ((ArrayNode)type).elements().forEachRemaining(n -> types.add(n.textValue()));
                    schema.types(types);
                }
                if (additionalProperties != null) {
                    try {
                        if (additionalProperties.isBoolean()) {
                            schema.setAdditionalProperties(additionalProperties.booleanValue());
                            break block12;
                        }
                        Schema innerSchema = this.deserializeJsonSchema(additionalProperties);
                        schema.setAdditionalProperties(innerSchema);
                    }
                    catch (Exception e) {
                        Boolean additionalPropsBoolean = Json31.mapper().convertValue((Object)additionalProperties, Boolean.class);
                        schema.setAdditionalProperties(additionalPropsBoolean);
                    }
                }
            } else {
                schema = Json31.mapper().convertValue((Object)node, JsonSchema.class);
            }
        }
        return schema;
    }
}

