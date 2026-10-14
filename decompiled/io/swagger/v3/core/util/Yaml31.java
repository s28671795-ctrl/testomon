/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.swagger.v3.core.util.ObjectMapperFactory;
import io.swagger.v3.oas.models.media.Schema;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Yaml31 {
    static ObjectMapper mapper;
    static Logger LOGGER;

    public static ObjectMapper mapper() {
        if (mapper == null) {
            mapper = ObjectMapperFactory.createYaml31();
        }
        return mapper;
    }

    public static ObjectWriter pretty() {
        return Yaml31.mapper().writer(new DefaultPrettyPrinter());
    }

    public static String pretty(Object o) {
        try {
            return Yaml31.pretty().writeValueAsString(o);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void prettyPrint(Object o) {
        try {
            System.out.println(Yaml31.pretty().writeValueAsString(o));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Object> jsonSchemaAsMap(String jsonSchema) {
        try {
            return Yaml31.mapper().readValue(jsonSchema, Map.class);
        }
        catch (JsonProcessingException e) {
            LOGGER.error("Exception converting jsonSchema to Map", e);
            return null;
        }
    }

    public static Map<String, Object> jsonSchemaAsMap(Schema schema) {
        try {
            return Yaml31.mapper().readValue(Yaml31.mapper().writeValueAsString(schema), Map.class);
        }
        catch (JsonProcessingException e) {
            LOGGER.error("Exception converting jsonSchema to Map", e);
            return null;
        }
    }

    static {
        LOGGER = LoggerFactory.getLogger(Yaml31.class);
    }
}

