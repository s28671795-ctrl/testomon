/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.http.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.incoresoft.middleware.http.serialization.instant.MillisecondsInstantDeserializer;
import com.incoresoft.middleware.http.serialization.instant.MillisecondsInstantSerializer;
import java.time.Instant;
import java.util.List;

public class Serialization {
    private static final ObjectMapper defaultMapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE).disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES).enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING).enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING).enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL).disable(SerializationFeature.FAIL_ON_EMPTY_BEANS).registerModule(new JavaTimeModule()).registerModule(Serialization.millisecondsInstantModule());

    private Serialization() {
    }

    public static ObjectMapper objectMapper() {
        return defaultMapper;
    }

    public static ObjectWriter writer() {
        return Serialization.objectMapper().writer();
    }

    public static String writeValueAsString(Object value) {
        try {
            return Serialization.objectMapper().writeValueAsString(value);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String writeValueAsPrettyString(Object value) {
        try {
            return Serialization.objectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(value);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T readValue(String content, Class<T> clazz) {
        try {
            return Serialization.objectMapper().readerFor(clazz).readValue(content);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> readAsList(String content, Class<T> valueType) {
        try {
            return (List)Serialization.objectMapper().readerForListOf(valueType).readValue(content);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static Module millisecondsInstantModule() {
        return new SimpleModule().addSerializer(Instant.class, new MillisecondsInstantSerializer()).addDeserializer(Instant.class, new MillisecondsInstantDeserializer());
    }
}

