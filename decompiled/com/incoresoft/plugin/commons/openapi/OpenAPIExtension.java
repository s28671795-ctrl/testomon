/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.openapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.util.ObjectMapperFactory;
import io.swagger.v3.oas.models.OpenAPI;

public class OpenAPIExtension {
    public static String toJSON(OpenAPI openAPI) {
        return OpenAPIExtension.toJson(openAPI, ObjectMapperFactoryExtended.createJson());
    }

    public static String toJson(OpenAPI openAPI, ObjectMapper objectMapper) {
        try {
            return objectMapper.writeValueAsString(openAPI);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static class ObjectMapperFactoryExtended
    extends ObjectMapperFactory {
        private ObjectMapperFactoryExtended() {
        }

        protected static ObjectMapper createJson() {
            return ObjectMapperFactory.createJson();
        }
    }
}

