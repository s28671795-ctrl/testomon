/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Comparator;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function2;
import org.jooq.Pro;
import org.jooq.QuantifiedSelect;
import org.jooq.Record1;
import org.jooq.SQLDialect;
import org.jooq.impl.AbstractCondition;
import org.jooq.impl.EqQuantified;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;

final class GeQuantified<T>
extends AbstractCondition
implements QOM.GeQuantified<T> {
    final Field<T> arg1;
    final QuantifiedSelect<? extends Record1<T>> arg2;

    GeQuantified(Field<T> arg1, QuantifiedSelect<? extends Record1<T>> arg2) {
        this.arg1 = Tools.nullSafeNotNull(arg1, SQLDataType.OTHER);
        this.arg2 = arg2;
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (ctx.family() == SQLDialect.JAVA) {
            this.acceptJava(ctx);
            return;
        }
        EqQuantified.acceptCompareCondition(ctx, this, this.arg1, Comparator.GREATER_OR_EQUAL, this.arg2);
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.visit(this.arg1).sql(".ge(").visit(this.arg2).sql(')');
    }

    @Override
    public final Field<T> $arg1() {
        return this.arg1;
    }

    @Override
    public final QuantifiedSelect<? extends Record1<T>> $arg2() {
        return this.arg2;
    }

    @Override
    public final QOM.GeQuantified<T> $arg1(Field<T> newValue) {
        return this.$constructor().apply(newValue, (QuantifiedSelect<Record1<T>>)this.$arg2());
    }

    @Override
    public final QOM.GeQuantified<T> $arg2(QuantifiedSelect<? extends Record1<T>> newValue) {
        return this.$constructor().apply((Field<T>)this.$arg1(), (QuantifiedSelect<Record1<T>>)newValue);
    }

    @Override
    public final Function2<? super Field<T>, ? super QuantifiedSelect<? extends Record1<T>>, ? extends QOM.GeQuantified<T>> $constructor() {
        return (a1, a2) -> new GeQuantified(a1, a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.GeQuantified) {
            QOM.GeQuantified o = (QOM.GeQuantified)that;
            return StringUtils.equals(this.$arg1(), o.$arg1()) && StringUtils.equals(this.$arg2(), o.$arg2());
        }
        return super.equals(that);
    }
}

