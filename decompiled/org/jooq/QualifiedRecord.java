/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.sql.SQLData;
import org.jetbrains.annotations.NotNull;
import org.jooq.Converter;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.RecordQualifier;

public interface QualifiedRecord<R extends QualifiedRecord<R>>
extends Record,
SQLData {
    @NotNull
    public RecordQualifier<R> getQualifier();

    @NotNull
    public <T> R with(Field<T> var1, T var2);

    @NotNull
    public <T, U> R with(Field<T> var1, U var2, Converter<? extends T, ? super U> var3);
}

