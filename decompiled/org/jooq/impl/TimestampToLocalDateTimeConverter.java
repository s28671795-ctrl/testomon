/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.jooq.ConverterContext;
import org.jooq.impl.AbstractContextConverter;

@Deprecated
public final class TimestampToLocalDateTimeConverter
extends AbstractContextConverter<Timestamp, LocalDateTime> {
    public TimestampToLocalDateTimeConverter() {
        super(Timestamp.class, LocalDateTime.class);
    }

    @Override
    public final LocalDateTime from(Timestamp t2, ConverterContext scope) {
        return t2 == null ? null : t2.toLocalDateTime();
    }

    @Override
    public final Timestamp to(LocalDateTime u, ConverterContext scope) {
        return u == null ? null : Timestamp.valueOf(u);
    }
}

