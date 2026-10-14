/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.core.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.core.util.Json31;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class ApiResponsesDeserializer
extends JsonDeserializer<ApiResponses> {
    protected boolean openapi31;

    @Override
    public ApiResponses deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        ObjectMapper mapper2 = this.openapi31 ? Json31.mapper() : Json.mapper();
        ApiResponses result = new ApiResponses();
        JsonNode node = (JsonNode)jp.getCodec().readTree(jp);
        ObjectNode objectNode = (ObjectNode)node;
        LinkedHashMap<String, Object> extensions = new LinkedHashMap<String, Object>();
        Iterator<String> it = objectNode.fieldNames();
        while (it.hasNext()) {
            String childName = it.next();
            JsonNode child = objectNode.get(childName);
            if (childName.startsWith("x-")) {
                extensions.put(childName, mapper2.convertValue((Object)child, Object.class));
                continue;
            }
            result.put(childName, mapper2.convertValue((Object)child, ApiResponse.class));
        }
        if (!extensions.isEmpty()) {
            result.setExtensions(extensions);
        }
        return result;
    }
}

