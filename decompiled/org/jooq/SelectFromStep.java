/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.Name;
import org.jooq.PlainSQL;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.SQL;
import org.jooq.SelectJoinStep;
import org.jooq.SelectWhereStep;
import org.jooq.Support;
import org.jooq.TableLike;

public interface SelectFromStep<R extends Record>
extends SelectWhereStep<R> {
    @Support
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> from(TableLike<?> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> from(TableLike<?> ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> from(Collection<? extends TableLike<?>> var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> from(SQL var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> from(String var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> from(String var1, Object ... var2);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> from(String var1, QueryPart ... var2);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> from(Name var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectFromStep<R> hint(String var1);
}

