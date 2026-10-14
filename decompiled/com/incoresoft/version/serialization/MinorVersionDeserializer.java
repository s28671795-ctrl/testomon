/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.version.serialization;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.incoresoft.version.models.MinorVersion;
import java.io.IOException;

public class MinorVersionDeserializer
extends JsonDeserializer<MinorVersion> {
    @Override
    public MinorVersion deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JacksonException {
        String value = jsonParser.getValueAsString();
        return MinorVersion.valueOf(value);
    }
}

