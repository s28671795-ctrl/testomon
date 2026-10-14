/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.PlainSQL;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.SQL;
import org.jooq.SelectJoinStep;
import org.jooq.SelectOnConditionStep;
import org.jooq.Support;
import org.jooq.TableField;
import org.jooq.exception.DataAccessException;

public interface SelectOnStep<R extends Record> {
    @Support
    @NotNull
    @CheckReturnValue
    public SelectOnConditionStep<R> on(Condition var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectOnConditionStep<R> on(Condition ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectOnConditionStep<R> on(Field<Boolean> var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectOnConditionStep<R> on(SQL var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectOnConditionStep<R> on(String var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectOnConditionStep<R> on(String var1, Object ... var2);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectOnConditionStep<R> on(String var1, QueryPart ... var2);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectOnConditionStep<R> onKey() throws DataAccessException;

    @Support
    @NotNull
    @CheckReturnValue
    public SelectOnConditionStep<R> onKey(TableField<?, ?> ... var1) throws DataAccessException;

    @Support
    @NotNull
    @CheckReturnValue
    public SelectOnConditionStep<R> onKey(ForeignKey<?, ?> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> using(Field<?> ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> using(Collection<? extends Field<?>> var1);
}

