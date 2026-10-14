/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.version.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.github.zafarkhaja.semver.Version;
import java.io.IOException;

public class VersionDeserializer
extends JsonDeserializer<Version> {
    @Override
    public Version deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
        String value = jsonParser.getValueAsString();
        return Version.valueOf(value);
    }
}

