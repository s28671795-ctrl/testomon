/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.RowCountQuery;
import org.jooq.SelectFieldOrAsterisk;
import org.jooq.Support;

public interface StoreQuery<R extends Record>
extends RowCountQuery {
    @Support
    public void setRecord(R var1);

    @Support
    public <T> void addValue(Field<T> var1, T var2);

    @Support
    public <T> void addValue(Field<T> var1, Field<T> var2);

    @Support
    public void addValues(Map<?, ?> var1);

    @Support
    public void setReturning();

    @Support
    public void setReturning(Identity<R, ?> var1);

    @Support
    public void setReturning(SelectFieldOrAsterisk ... var1);

    @Support
    public void setReturning(Collection<? extends SelectFieldOrAsterisk> var1);

    @Support
    @Nullable
    public R getReturnedRecord();

    @Support
    @NotNull
    public Result<R> getReturnedRecords();

    @Support
    @NotNull
    public Result<?> getResult();
}

