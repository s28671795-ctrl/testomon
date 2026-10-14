/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Comparator;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function2;
import org.jooq.Pro;
import org.jooq.Record1;
import org.jooq.RowN;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.impl.AbstractCondition;
import org.jooq.impl.Eq;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.ScalarSubquery;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;

final class NotIn<T>
extends AbstractCondition
implements QOM.NotIn<T> {
    final Field<T> arg1;
    final Select<? extends Record1<T>> arg2;

    NotIn(Field<T> arg1, Select<? extends Record1<T>> arg2) {
        this.arg1 = Tools.nullSafeNotNull(arg1, SQLDataType.OTHER);
        this.arg2 = arg2;
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (ctx.family() == SQLDialect.JAVA) {
            this.acceptJava(ctx);
            return;
        }
        Eq.acceptCompareCondition(ctx, this, this.arg1, Comparator.NOT_IN, new ScalarSubquery(this.arg2, this.arg1.getDataType(), true), RowN::notIn, (rec$, xva$0) -> ((RowN)rec$).notIn((RowN)xva$0), (c, a1, a2) -> c.visit((Field<?>)a1).sql(' ').visit(Keywords.K_NOT_IN).sql(' ').visit((Field<?>)a2));
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.visit(this.arg1).sql(".notIn(").visitSubquery(this.arg2).sql(')');
    }

    @Override
    public final Field<T> $arg1() {
        return this.arg1;
    }

    @Override
    public final Select<? extends Record1<T>> $arg2() {
        return this.arg2;
    }

    @Override
    public final QOM.NotIn<T> $arg1(Field<T> newValue) {
        return this.$constructor().apply(newValue, (Select<Record1<T>>)this.$arg2());
    }

    @Override
    public final QOM.NotIn<T> $arg2(Select<? extends Record1<T>> newValue) {
        return this.$constructor().apply((Field<T>)this.$arg1(), (Select<Record1<T>>)newValue);
    }

    @Override
    public final Function2<? super Field<T>, ? super Select<? extends Record1<T>>, ? extends QOM.NotIn<T>> $constructor() {
        return (a1, a2) -> new NotIn(a1, a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.NotIn) {
            QOM.NotIn o = (QOM.NotIn)that;
            return StringUtils.equals(this.$arg1(), o.$arg1()) && StringUtils.equals(this.$arg2(), o.$arg2());
        }
        return super.equals(that);
    }
}

