/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.Timestamp;
import java.util.Date;
import org.jooq.ConverterContext;
import org.jooq.impl.AbstractContextConverter;

final class TimestampToJavaUtilDateConverter
extends AbstractContextConverter<Timestamp, Date> {
    static final TimestampToJavaUtilDateConverter INSTANCE = new TimestampToJavaUtilDateConverter();

    private TimestampToJavaUtilDateConverter() {
        super(Timestamp.class, Date.class);
    }

    @Override
    public final Date from(Timestamp t2, ConverterContext scope) {
        return t2 == null ? null : new Date(t2.getTime());
    }

    @Override
    public final Timestamp to(Date u, ConverterContext scope) {
        return u == null ? null : new Timestamp(u.getTime());
    }
}

