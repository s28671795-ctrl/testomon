/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.version.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.zafarkhaja.semver.Version;
import java.io.IOException;

public class VersionSerializer
extends JsonSerializer<Version> {
    @Override
    public void serialize(Version version, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(version.toString());
    }
}

