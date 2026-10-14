/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.swagger.v3.core.util.ObjectMapperFactory;
import io.swagger.v3.oas.models.media.Schema;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Json31 {
    private static ObjectMapper mapper;
    private static ObjectMapper converterMapper;
    static Logger LOGGER;

    public static ObjectMapper mapper() {
        if (mapper == null) {
            mapper = ObjectMapperFactory.createJson31();
        }
        return mapper;
    }

    public static ObjectMapper converterMapper() {
        if (converterMapper == null) {
            converterMapper = ObjectMapperFactory.createJsonConverter();
        }
        return converterMapper;
    }

    public static ObjectWriter pretty() {
        return Json31.mapper().writer(new DefaultPrettyPrinter());
    }

    public static String pretty(Object o) {
        try {
            return Json31.pretty().writeValueAsString(o);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void prettyPrint(Object o) {
        try {
            System.out.println(Json31.pretty().writeValueAsString(o).replace("\r", ""));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Object> jsonSchemaAsMap(String jsonSchema) {
        try {
            return Json31.mapper().readValue(jsonSchema, Map.class);
        }
        catch (JsonProcessingException e) {
            LOGGER.error("Exception converting jsonSchema to Map", e);
            return null;
        }
    }

    public static Map<String, Object> jsonSchemaAsMap(Schema schema) {
        try {
            return Json31.mapper().readValue(Json31.mapper().writeValueAsString(schema), Map.class);
        }
        catch (JsonProcessingException e) {
            LOGGER.error("Exception converting jsonSchema to Map", e);
            return null;
        }
    }

    public static Map<String, Object> jsonSchemaAsMap(JsonNode schema) {
        try {
            return Json31.mapper().readValue(Json31.mapper().writeValueAsString(schema), Map.class);
        }
        catch (JsonProcessingException e) {
            LOGGER.error("Exception converting jsonSchema to Map", e);
            return null;
        }
    }

    static {
        LOGGER = LoggerFactory.getLogger(Json31.class);
    }
}

