/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function2;
import org.jooq.Pro;
import org.jooq.Select;
import org.jooq.TableLike;
import org.jooq.impl.AbstractCondition;
import org.jooq.impl.DSL;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;

final class ArrayOverlap<T>
extends AbstractCondition
implements QOM.ArrayOverlap<T> {
    final Field<T[]> arg1;
    final Field<T[]> arg2;

    ArrayOverlap(Field<T[]> arg1, Field<T[]> arg2) {
        this.arg1 = Tools.nullSafeNotNull(arg1, SQLDataType.OTHER.array());
        this.arg2 = Tools.nullSafeNotNull(arg2, SQLDataType.OTHER.array());
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                this.acceptJava(ctx);
                break;
            }
            case H2: {
                ctx.visit(DSL.exists(DSL.select(DSL.asterisk()).from((TableLike<?>)DSL.unnest(this.arg1)).intersect((Select)DSL.select(DSL.asterisk()).from((TableLike<?>)DSL.unnest(this.arg2)))));
                break;
            }
            case HSQLDB: {
                ctx.visit(DSL.exists(DSL.select(DSL.asterisk()).from((TableLike<?>)DSL.unnest(this.arg1)).intersectAll((Select)DSL.select(DSL.asterisk()).from((TableLike<?>)DSL.unnest(this.arg2)))));
                break;
            }
            default: {
                ctx.sql('(').visit(this.arg1).sql(" && ").visit(this.arg2).sql(')');
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("arrayOverlap(");
        String s2 = "";
        ctx.sql(s2).visit(this.arg1);
        s2 = ", ";
        ctx.sql(s2).visit(this.arg2);
        s2 = ", ";
        ctx.sql(')');
    }

    @Override
    public final Field<T[]> $arg1() {
        return this.arg1;
    }

    @Override
    public final Field<T[]> $arg2() {
        return this.arg2;
    }

    @Override
    public final QOM.ArrayOverlap<T> $arg1(Field<T[]> newValue) {
        return this.$constructor().apply(newValue, (Field<T[]>)this.$arg2());
    }

    @Override
    public final QOM.ArrayOverlap<T> $arg2(Field<T[]> newValue) {
        return this.$constructor().apply((Field<T[]>)this.$arg1(), newValue);
    }

    @Override
    public final Function2<? super Field<T[]>, ? super Field<T[]>, ? extends QOM.ArrayOverlap<T>> $constructor() {
        return (a1, a2) -> new ArrayOverlap((Field<T[]>)a1, (Field<T[]>)a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.ArrayOverlap) {
            QOM.ArrayOverlap o = (QOM.ArrayOverlap)that;
            return StringUtils.equals(this.$arg1(), o.$arg1()) && StringUtils.equals(this.$arg2(), o.$arg2());
        }
        return super.equals(that);
    }
}

