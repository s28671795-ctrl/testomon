/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import org.jooq.AuditProvider;
import org.jooq.Field;
import org.jooq.GeneratorContext;
import org.jooq.Pro;
import org.jooq.impl.DSL;

@Pro
public class DefaultAuditProvider
implements AuditProvider {
    @Override
    public <T> Field<T> provideTimestamp(GeneratorContext<?, ?, T> ctx) {
        Class<Timestamp> t2;
        Field<T> f = ctx.field();
        Class clazz = t2 = f == null ? Timestamp.class : f.getDataType().getType();
        if (t2 == Timestamp.class) {
            return DSL.currentTimestamp();
        }
        if (t2 == Time.class) {
            return DSL.currentTime();
        }
        if (t2 == Date.class) {
            return DSL.currentDate();
        }
        if (t2 == LocalDate.class) {
            return DSL.currentLocalDate();
        }
        if (t2 == LocalTime.class) {
            return DSL.currentLocalTime();
        }
        if (t2 == LocalDateTime.class) {
            return DSL.currentLocalDateTime();
        }
        if (t2 == OffsetDateTime.class) {
            return DSL.currentOffsetDateTime();
        }
        if (t2 == OffsetTime.class) {
            return DSL.currentOffsetTime();
        }
        if (t2 == Instant.class) {
            return DSL.currentInstant();
        }
        return null;
    }

    @Override
    public <T> Field<T> provideUser(GeneratorContext<?, ?, T> ctx) {
        Class<String> t2;
        Field<T> f = ctx.field();
        Class clazz = t2 = f == null ? String.class : f.getDataType().getType();
        if (t2 == String.class) {
            return DSL.currentUser();
        }
        return null;
    }
}

