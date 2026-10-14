/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.http.serialization.instant;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.Instant;
import java.util.Optional;

public class MillisecondsInstantSerializer
extends JsonSerializer<Instant> {
    @Override
    public void serialize(Instant value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeNumber(Optional.ofNullable(value).map(Instant::toEpochMilli).orElse(0L));
    }
}

