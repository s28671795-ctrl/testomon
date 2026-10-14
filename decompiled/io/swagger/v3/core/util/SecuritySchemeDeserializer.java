/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.core.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.core.util.Json31;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityScheme;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

public class SecuritySchemeDeserializer
extends JsonDeserializer<SecurityScheme> {
    protected boolean openapi31;

    @Override
    public SecurityScheme deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        ObjectMapper mapper2 = null;
        mapper2 = this.openapi31 ? Json31.mapper() : Json.mapper();
        SecurityScheme result = null;
        JsonNode node = (JsonNode)jp.getCodec().readTree(jp);
        JsonNode inNode = node.get("type");
        if (inNode != null) {
            String type = inNode.asText();
            if (Arrays.stream(SecurityScheme.Type.values()).noneMatch(t2 -> t2.toString().equals(type))) {
                throw new JsonParseException(jp, String.format("SecurityScheme type %s not allowed", type));
            }
            result = new SecurityScheme().description(this.getFieldText("description", node));
            if ("http".equals(type)) {
                result.type(SecurityScheme.Type.HTTP).scheme(this.getFieldText("scheme", node)).bearerFormat(this.getFieldText("bearerFormat", node));
            } else if ("apiKey".equals(type)) {
                result.type(SecurityScheme.Type.APIKEY).name(this.getFieldText("name", node)).in(this.getIn(this.getFieldText("in", node)));
            } else if ("openIdConnect".equals(type)) {
                result.type(SecurityScheme.Type.OPENIDCONNECT).openIdConnectUrl(this.getFieldText("openIdConnectUrl", node));
            } else if ("oauth2".equals(type)) {
                result.type(SecurityScheme.Type.OAUTH2).flows(mapper2.convertValue((Object)node.get("flows"), OAuthFlows.class));
            } else if ("mutualTLS".equals(type)) {
                result.type(SecurityScheme.Type.MUTUALTLS);
            }
            Iterator<String> fieldNames = node.fieldNames();
            while (fieldNames.hasNext()) {
                String fieldName = fieldNames.next();
                if (!fieldName.startsWith("x-")) continue;
                JsonNode fieldValue = node.get(fieldName);
                Object value = Json.mapper().treeToValue((TreeNode)fieldValue, Object.class);
                result.addExtension(fieldName, value);
            }
        }
        return result;
    }

    private SecurityScheme.In getIn(String value) {
        return Arrays.stream(SecurityScheme.In.values()).filter(i -> i.toString().equals(value)).findFirst().orElse(null);
    }

    private String getFieldText(String fieldName, JsonNode node) {
        JsonNode inNode = node.get(fieldName);
        if (inNode != null) {
            return inNode.asText();
        }
        return null;
    }
}

