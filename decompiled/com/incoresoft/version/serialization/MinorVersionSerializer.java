/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.version.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.incoresoft.version.models.MinorVersion;
import java.io.IOException;

public class MinorVersionSerializer
extends JsonSerializer<MinorVersion> {
    @Override
    public void serialize(MinorVersion value, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
        jsonGenerator.writeString(value.toString());
    }
}

