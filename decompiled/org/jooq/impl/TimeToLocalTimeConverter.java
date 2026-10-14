/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.Time;
import java.time.LocalTime;
import org.jooq.ConverterContext;
import org.jooq.impl.AbstractContextConverter;

@Deprecated
public final class TimeToLocalTimeConverter
extends AbstractContextConverter<Time, LocalTime> {
    public TimeToLocalTimeConverter() {
        super(Time.class, LocalTime.class);
    }

    @Override
    public final LocalTime from(Time t2, ConverterContext scope) {
        return t2 == null ? null : t2.toLocalTime();
    }

    @Override
    public final Time to(LocalTime u, ConverterContext scope) {
        return u == null ? null : Time.valueOf(u);
    }
}

