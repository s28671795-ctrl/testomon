/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.jooq.Condition;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Pro;
import org.jooq.Row1;
import org.jooq.SelectField;
import org.jooq.TableLike;
import org.jooq.impl.DSL;
import org.jooq.impl.QueryPartListView;
import org.jooq.impl.Tools;

final class GreatestLeast<T> {
    GreatestLeast() {
    }

    @Pro
    static final <T> void acceptValuesEmulation(Context<?> ctx, DataType<T> type, QueryPartListView<Field<T>> args2, Function<? super Field<T>, ? extends Field<T>> minMax) {
        ctx.visitSubquery(DSL.select((SelectField)minMax.apply(DSL.field(DSL.name("c"), type))).from((TableLike<?>)DSL.values(Tools.map(args2, f -> DSL.row(f), Row1[]::new)).as("t", "c")));
    }

    static final <T> void acceptCaseEmulation(Context<?> ctx, QueryPartListView<Field<T>> args2, BiFunction<? super Field<T>, ? super Field<?>[], ? extends Field<T>> greatestLeast, BiFunction<? super Field<T>, ? super Field<T>, ? extends Condition> gtLt) {
        Field first = (Field)args2.get(0);
        Field other = (Field)args2.get(1);
        if (args2.size() > 2) {
            Field<?>[] remaining = args2.subList(2, args2.size()).toArray(Tools.EMPTY_FIELD);
            ctx.visit(DSL.when(gtLt.apply(first, other), greatestLeast.apply(first, remaining)).otherwise(greatestLeast.apply(other, remaining)));
        } else {
            ctx.visit(DSL.when(gtLt.apply(first, other), first).otherwise(other));
        }
    }
}

