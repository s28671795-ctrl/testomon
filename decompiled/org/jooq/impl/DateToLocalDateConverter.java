/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.Date;
import java.time.LocalDate;
import org.jooq.ConverterContext;
import org.jooq.impl.AbstractContextConverter;

@Deprecated
public final class DateToLocalDateConverter
extends AbstractContextConverter<Date, LocalDate> {
    public DateToLocalDateConverter() {
        super(Date.class, LocalDate.class);
    }

    @Override
    public final LocalDate from(Date t2, ConverterContext scope) {
        return t2 == null ? null : t2.toLocalDate();
    }

    @Override
    public final Date to(LocalDate u, ConverterContext scope) {
        return u == null ? null : Date.valueOf(u);
    }
}

