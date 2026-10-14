/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jetbrains.annotations.NotNull;
import org.jooq.Configuration;
import org.jooq.Converter;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.Table;

interface Mappable<R extends Record> {
    @NotNull
    public RecordMapper<R, ?> mapper(int var1);

    @NotNull
    public <U> RecordMapper<R, U> mapper(int var1, Configuration var2, Class<? extends U> var3);

    @NotNull
    public <U> RecordMapper<R, U> mapper(int var1, Converter<?, ? extends U> var2);

    @NotNull
    public RecordMapper<R, Record> mapper(int[] var1);

    @NotNull
    public RecordMapper<R, ?> mapper(String var1);

    @NotNull
    public <U> RecordMapper<R, U> mapper(String var1, Configuration var2, Class<? extends U> var3);

    @NotNull
    public <U> RecordMapper<R, U> mapper(String var1, Converter<?, ? extends U> var2);

    @NotNull
    public RecordMapper<R, Record> mapper(String[] var1);

    @NotNull
    public RecordMapper<R, ?> mapper(Name var1);

    @NotNull
    public <U> RecordMapper<R, U> mapper(Name var1, Configuration var2, Class<? extends U> var3);

    @NotNull
    public <U> RecordMapper<R, U> mapper(Name var1, Converter<?, ? extends U> var2);

    @NotNull
    public RecordMapper<R, Record> mapper(Name[] var1);

    @NotNull
    public <T> RecordMapper<R, T> mapper(Field<T> var1);

    @NotNull
    public <U> RecordMapper<R, U> mapper(Field<?> var1, Configuration var2, Class<? extends U> var3);

    @NotNull
    public <T, U> RecordMapper<R, U> mapper(Field<T> var1, Converter<? super T, ? extends U> var2);

    @NotNull
    public RecordMapper<R, Record> mapper(Field<?>[] var1);

    @NotNull
    public <S extends Record> RecordMapper<R, S> mapper(Table<S> var1);

    @NotNull
    public <E> RecordMapper<R, E> mapper(Configuration var1, Class<? extends E> var2);
}

