/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function2;
import org.jooq.Pro;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.impl.AbstractCondition;
import org.jooq.impl.DSL;
import org.jooq.impl.IsDistinctFrom;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;

final class IsNotDistinctFrom<T>
extends AbstractCondition
implements QOM.IsNotDistinctFrom<T> {
    final Field<T> arg1;
    final Field<T> arg2;

    IsNotDistinctFrom(Field<T> arg1, Field<T> arg2) {
        this.arg1 = Tools.nullableIf(true, Tools.nullSafe(arg1, arg2.getDataType()));
        this.arg2 = Tools.nullableIf(true, Tools.nullSafe(arg2, arg1.getDataType()));
    }

    @Override
    final boolean isNullable() {
        return false;
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (ctx.family() == SQLDialect.JAVA) {
            this.acceptJava(ctx);
        } else if (this.arg1.getDataType().isEmbeddable() && this.arg2.getDataType().isEmbeddable()) {
            ctx.visit(DSL.row(Tools.embeddedFields(this.arg1)).isNotDistinctFrom(DSL.row(Tools.embeddedFields(this.arg2))));
        } else if (IsDistinctFrom.SUPPORT_DECODE.contains((Object)ctx.dialect())) {
            ctx.visit(DSL.decode(this.arg1, this.arg2, DSL.inline(1), DSL.inline(0)).eq(DSL.inline(1)));
        } else if (IsDistinctFrom.EMULATE_DISTINCT_PREDICATE.contains((Object)ctx.dialect())) {
            ctx.visit(DSL.exists(DSL.select(this.arg1.as("x")).intersect((Select)DSL.select(this.arg2.as("x")))));
        } else if (IsDistinctFrom.SUPPORT_DISTINCT_WITH_ARROW.contains((Object)ctx.dialect())) {
            ctx.visit(DSL.condition("{0} <=> {1}", this.arg1, this.arg2));
        } else if (SQLDialect.SQLITE == ctx.family()) {
            ctx.visit(DSL.condition("{0} {is} {1}", this.arg1, this.arg2));
        } else if (SQLDialect.REDSHIFT == ctx.family()) {
            ctx.visit(new IsDistinctFrom<T>(this.arg1, this.arg2).not());
        } else {
            ctx.visit(this.arg1).sql(' ').visit(Keywords.K_IS).sql(' ').visit(Keywords.K_NOT).sql(' ').visit(Keywords.K_DISTINCT).sql(' ').visit(Keywords.K_FROM).sql(' ').visit(this.arg2);
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.visit(this.arg1).sql(".isNotDistinctFrom(").visit(this.arg2).sql(')');
    }

    @Override
    public final Field<T> $arg1() {
        return this.arg1;
    }

    @Override
    public final Field<T> $arg2() {
        return this.arg2;
    }

    @Override
    public final QOM.IsNotDistinctFrom<T> $arg1(Field<T> newValue) {
        return this.$constructor().apply(newValue, (Field<T>)this.$arg2());
    }

    @Override
    public final QOM.IsNotDistinctFrom<T> $arg2(Field<T> newValue) {
        return this.$constructor().apply((Field<T>)this.$arg1(), newValue);
    }

    @Override
    public final Function2<? super Field<T>, ? super Field<T>, ? extends QOM.IsNotDistinctFrom<T>> $constructor() {
        return (a1, a2) -> new IsNotDistinctFrom(a1, a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.IsNotDistinctFrom) {
            QOM.IsNotDistinctFrom o = (QOM.IsNotDistinctFrom)that;
            return StringUtils.equals(this.$arg1(), o.$arg1()) && StringUtils.equals(this.$arg2(), o.$arg2());
        }
        return super.equals(that);
    }
}

