/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Comparator;
import org.jooq.Context;
import org.jooq.Function2;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Row;
import org.jooq.SQLDialect;
import org.jooq.impl.AbstractCondition;
import org.jooq.impl.AbstractRow;
import org.jooq.impl.QOM;
import org.jooq.impl.RowEq;
import org.jooq.tools.StringUtils;

final class RowGe<T extends Row>
extends AbstractCondition
implements QOM.RowGe<T> {
    final T arg1;
    final T arg2;

    RowGe(T arg1, T arg2) {
        this.arg1 = ((AbstractRow)arg1).convertTo((Row)arg2);
        this.arg2 = ((AbstractRow)arg2).convertTo((Row)arg1);
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (ctx.family() == SQLDialect.JAVA) {
            this.acceptJava(ctx);
            return;
        }
        RowEq.acceptCompareCondition(ctx, this, this.arg1, Comparator.GREATER_OR_EQUAL, this.arg2);
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.visit((QueryPart)this.arg1).sql(".ge(").visit((QueryPart)this.arg2).sql(')');
    }

    @Override
    public final T $arg1() {
        return this.arg1;
    }

    @Override
    public final T $arg2() {
        return this.arg2;
    }

    @Override
    public final QOM.RowGe<T> $arg1(T newValue) {
        return this.$constructor().apply(newValue, (T)this.$arg2());
    }

    @Override
    public final QOM.RowGe<T> $arg2(T newValue) {
        return this.$constructor().apply((T)this.$arg1(), newValue);
    }

    @Override
    public final Function2<? super T, ? super T, ? extends QOM.RowGe<T>> $constructor() {
        return (a1, a2) -> new RowGe<Row>((Row)a1, (Row)a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.RowGe) {
            QOM.RowGe o = (QOM.RowGe)that;
            return StringUtils.equals(this.$arg1(), o.$arg1()) && StringUtils.equals(this.$arg2(), o.$arg2());
        }
        return super.equals(that);
    }
}

