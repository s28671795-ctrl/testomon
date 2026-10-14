/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.http.serialization.instant;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.math.BigInteger;
import java.time.Instant;
import java.util.Optional;

public class MillisecondsInstantDeserializer
extends JsonDeserializer<Instant> {
    @Override
    public Instant deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return Optional.ofNullable(p.getBigIntegerValue()).map(BigInteger::longValue).map(Instant::ofEpochMilli).orElse(null);
    }
}

