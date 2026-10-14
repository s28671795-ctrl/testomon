/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.db;

import java.sql.Timestamp;
import java.time.Instant;
import org.jetbrains.annotations.NotNull;
import org.jooq.Converter;

public class InstantConverter
implements Converter<Timestamp, Instant> {
    @Override
    public Instant from(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toInstant();
    }

    @Override
    public Timestamp to(Instant instant) {
        return instant == null ? null : Timestamp.from(instant);
    }

    @Override
    @NotNull
    public Class<Timestamp> fromType() {
        return Timestamp.class;
    }

    @Override
    @NotNull
    public Class<Instant> toType() {
        return Instant.class;
    }
}

