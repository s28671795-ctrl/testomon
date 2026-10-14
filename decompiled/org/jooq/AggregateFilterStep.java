/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.PlainSQL;
import org.jooq.QueryPart;
import org.jooq.SQL;
import org.jooq.Support;
import org.jooq.WindowBeforeOverStep;

public interface AggregateFilterStep<T>
extends WindowBeforeOverStep<T> {
    @Support
    @NotNull
    public WindowBeforeOverStep<T> filterWhere(Condition var1);

    @Support
    @NotNull
    public WindowBeforeOverStep<T> filterWhere(Condition ... var1);

    @Support
    @NotNull
    public WindowBeforeOverStep<T> filterWhere(Collection<? extends Condition> var1);

    @Support
    @NotNull
    public WindowBeforeOverStep<T> filterWhere(Field<Boolean> var1);

    @Support
    @PlainSQL
    @NotNull
    public WindowBeforeOverStep<T> filterWhere(SQL var1);

    @Support
    @PlainSQL
    @NotNull
    public WindowBeforeOverStep<T> filterWhere(String var1);

    @Support
    @PlainSQL
    @NotNull
    public WindowBeforeOverStep<T> filterWhere(String var1, Object ... var2);

    @Support
    @PlainSQL
    @NotNull
    public WindowBeforeOverStep<T> filterWhere(String var1, QueryPart ... var2);
}

