/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.util.serializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.pac4j.core.util.CommonHelper;
import org.pac4j.core.util.serializer.AbstractSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonSerializer
extends AbstractSerializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonSerializer.class);
    private ObjectMapper objectMapper;
    private Class<? extends Object> clazz;

    public JsonSerializer(Class<? extends Object> clazz) {
        CommonHelper.assertNotNull("clazz", clazz);
        this.clazz = clazz;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        this.objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    @Override
    protected String internalSerializeToString(Object obj) {
        try {
            return this.objectMapper.writeValueAsString(obj);
        }
        catch (JsonProcessingException e) {
            LOGGER.error("Cannot encode object", e);
            return null;
        }
    }

    @Override
    protected Object internalDeserializeFromString(String encoded) {
        try {
            return this.objectMapper.readValue(encoded, this.clazz);
        }
        catch (JsonProcessingException e) {
            LOGGER.error("Cannot decode string", e);
            return null;
        }
    }

    public ObjectMapper getObjectMapper() {
        return this.objectMapper;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        CommonHelper.assertNotNull("objectMapper", objectMapper);
        this.objectMapper = objectMapper;
    }
}

