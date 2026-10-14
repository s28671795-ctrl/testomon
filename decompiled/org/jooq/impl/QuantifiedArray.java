/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function2;
import org.jooq.Param;
import org.jooq.QuantifiedSelect;
import org.jooq.QueryPart;
import org.jooq.Record1;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.SelectFieldOrAsterisk;
import org.jooq.TableLike;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.DSL;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;

final class QuantifiedArray<T>
extends AbstractQueryPart
implements QuantifiedSelect<Record1<T>>,
QOM.QuantifiedArray<T> {
    final QOM.Quantifier quantifier;
    final Field<T[]> array;

    QuantifiedArray(QOM.Quantifier quantifier, Field<T[]> array) {
        this.quantifier = quantifier;
        this.array = array;
    }

    @Override
    public final void accept(Context<?> ctx) {
        boolean extraParentheses = SQLDialect.ORACLE == ctx.family() && Boolean.TRUE.equals(ctx.data(Tools.BooleanDataKey.DATA_ROW_VALUE_EXPRESSION_PREDICATE_SUBQUERY));
        switch (ctx.family()) {
            case JAVA: {
                ctx.sql(this.quantifier == QOM.Quantifier.ANY ? "any(" : "all(");
                ctx.visit(this.array);
                ctx.sql(')');
                break;
            }
            default: {
                ctx.visit(this.quantifier.keyword);
                ctx.sql(extraParentheses ? " ((" : " (");
                Tools.visitSubquery(ctx, this.delegate(ctx), 256, false);
                ctx.sql(extraParentheses ? "))" : ")");
            }
        }
    }

    private final QueryPart delegate(Context<?> ctx) {
        Field<T[]> field2 = this.array;
        if (field2 instanceof QOM.Array) {
            QOM.Array a = (QOM.Array)field2;
            switch (ctx.family()) {
                case AURORA_POSTGRES: 
                case COCKROACHDB: 
                case POSTGRES: 
                case YUGABYTEDB: {
                    return this.array;
                }
            }
            Select select = null;
            for (Field field3 : a.$elements()) {
                if (select == null) {
                    select = DSL.select(field3);
                    continue;
                }
                select = select.unionAll(DSL.select(field3));
            }
            return select;
        }
        switch (ctx.family()) {
            case AURORA_POSTGRES: 
            case COCKROACHDB: 
            case POSTGRES: 
            case YUGABYTEDB: {
                return this.array;
            }
            case H2: 
            case HSQLDB: {
                return DSL.select(new SelectFieldOrAsterisk[0]).from((TableLike<?>)DSL.table(this.array));
            }
        }
        if (this.array instanceof Param) {
            Object[] values0 = (Object[])((Param)this.array).getValue();
            Select<Record1<Object>> select = null;
            for (Object value : values0) {
                select = select == null ? DSL.select(DSL.val(value)) : select.unionAll(DSL.select(DSL.val(value)));
            }
            return select;
        }
        return DSL.select(new SelectFieldOrAsterisk[0]).from((TableLike<?>)DSL.table(this.array));
    }

    @Override
    public final Function2<? super QOM.Quantifier, ? super Field<T[]>, ? extends QuantifiedArray<T>> $constructor() {
        return (q, a) -> new QuantifiedArray((QOM.Quantifier)((Object)q), (Field<T[]>)a);
    }

    @Override
    public final QOM.Quantifier $arg1() {
        return this.quantifier;
    }

    @Override
    public final Field<T[]> $arg2() {
        return this.array;
    }
}

