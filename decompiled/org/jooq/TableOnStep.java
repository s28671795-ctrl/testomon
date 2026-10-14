/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.PlainSQL;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.SQL;
import org.jooq.Support;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOnConditionStep;
import org.jooq.exception.DataAccessException;

public interface TableOnStep<R extends Record> {
    @Support
    @NotNull
    public TableOnConditionStep<R> on(Condition var1);

    @Support
    @NotNull
    public TableOnConditionStep<R> on(Condition ... var1);

    @Support
    @NotNull
    public TableOnConditionStep<R> on(Field<Boolean> var1);

    @Support
    @PlainSQL
    @NotNull
    public TableOnConditionStep<R> on(SQL var1);

    @Support
    @PlainSQL
    @NotNull
    public TableOnConditionStep<R> on(String var1);

    @Support
    @PlainSQL
    @NotNull
    public TableOnConditionStep<R> on(String var1, Object ... var2);

    @Support
    @PlainSQL
    @NotNull
    public TableOnConditionStep<R> on(String var1, QueryPart ... var2);

    @Support
    @NotNull
    public Table<Record> using(Field<?> ... var1);

    @Support
    @NotNull
    public Table<Record> using(Collection<? extends Field<?>> var1);

    @Support
    @NotNull
    public TableOnConditionStep<R> onKey() throws DataAccessException;

    @Support
    @NotNull
    public TableOnConditionStep<R> onKey(TableField<?, ?> ... var1) throws DataAccessException;

    @Support
    @NotNull
    public TableOnConditionStep<R> onKey(ForeignKey<?, ?> var1);
}

