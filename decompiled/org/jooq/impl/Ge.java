/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Comparator;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function2;
import org.jooq.Pro;
import org.jooq.RowN;
import org.jooq.SQLDialect;
import org.jooq.impl.AbstractCondition;
import org.jooq.impl.Eq;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;

final class Ge<T>
extends AbstractCondition
implements QOM.Ge<T> {
    final Field<T> arg1;
    final Field<T> arg2;

    Ge(Field<T> arg1, Field<T> arg2) {
        this.arg1 = Tools.nullableIf(false, Tools.nullSafe(arg1, arg2.getDataType()));
        this.arg2 = Tools.nullableIf(false, Tools.nullSafe(arg2, arg1.getDataType()));
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (ctx.family() == SQLDialect.JAVA) {
            this.acceptJava(ctx);
            return;
        }
        Eq.acceptCompareCondition(ctx, this, this.arg1, Comparator.GREATER_OR_EQUAL, this.arg2, RowN::ge, RowN::ge, (c, a1, a2) -> c.visit((Field<?>)a1).sql(" >= ").visit((Field<?>)a2));
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return Eq.CLAUSES;
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
    public final Field<T> $arg2() {
        return this.arg2;
    }

    @Override
    public final QOM.Ge<T> $arg1(Field<T> newValue) {
        return this.$constructor().apply(newValue, (Field<T>)this.$arg2());
    }

    @Override
    public final QOM.Ge<T> $arg2(Field<T> newValue) {
        return this.$constructor().apply((Field<T>)this.$arg1(), newValue);
    }

    @Override
    public final Function2<? super Field<T>, ? super Field<T>, ? extends QOM.Ge<T>> $constructor() {
        return (a1, a2) -> new Ge(a1, a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Ge) {
            QOM.Ge o = (QOM.Ge)that;
            return StringUtils.equals(this.$arg1(), o.$arg1()) && StringUtils.equals(this.$arg2(), o.$arg2());
        }
        return super.equals(that);
    }
}

