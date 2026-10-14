/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function3;
import org.jooq.Pro;
import org.jooq.TableLike;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

final class ArrayReplace<T>
extends AbstractField<T[]>
implements QOM.ArrayReplace<T> {
    final Field<T[]> arg1;
    final Field<T> arg2;
    final Field<T> arg3;

    ArrayReplace(Field<T[]> arg1, Field<T> arg2, Field<T> arg3) {
        super(Names.N_ARRAY_REPLACE, Tools.allNotNull(Tools.dataType(SQLDataType.OTHER.array(), arg1, false), arg1, arg2, arg3));
        this.arg1 = Tools.nullSafeNotNull(arg1, SQLDataType.OTHER.array());
        this.arg2 = Tools.nullSafeNotNull(arg2, SQLDataType.OTHER);
        this.arg3 = Tools.nullSafeNotNull(arg3, SQLDataType.OTHER);
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        switch (ctx.family()) {
            case H2: 
            case HSQLDB: {
                return false;
            }
        }
        return true;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                this.acceptJava(ctx);
                break;
            }
            case H2: 
            case HSQLDB: {
                Field<T> x = DSL.field(DSL.name("x"), this.arg2.getDataType());
                Field<Long> o = DSL.field(DSL.name("o"), SQLDataType.BIGINT);
                ctx.visit(DSL.field(DSL.select(DSL.arrayAgg(DSL.when(x.isNotDistinctFrom(this.arg2), this.arg3).else_(x)).orderBy(o)).from((TableLike<?>)DSL.unnest(this.arg1).withOrdinality().as("t", "x", "o"))));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_ARRAY_REPLACE, this.getDataType(), this.arg1, this.arg2, this.arg3));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("arrayReplace(");
        String s2 = "";
        ctx.sql(s2).visit(this.arg1);
        s2 = ", ";
        ctx.sql(s2).visit(Val.getJavaValue(this.arg2));
        s2 = ", ";
        ctx.sql(s2).visit(Val.getJavaValue(this.arg3));
        s2 = ", ";
        ctx.sql(')');
    }

    @Override
    public final Field<T[]> $arg1() {
        return this.arg1;
    }

    @Override
    public final Field<T> $arg2() {
        return this.arg2;
    }

    @Override
    public final Field<T> $arg3() {
        return this.arg3;
    }

    @Override
    public final QOM.ArrayReplace<T> $arg1(Field<T[]> newValue) {
        return this.$constructor().apply(newValue, (Field<T>)this.$arg2(), (Field<T>)this.$arg3());
    }

    @Override
    public final QOM.ArrayReplace<T> $arg2(Field<T> newValue) {
        return this.$constructor().apply((Field<T[]>)this.$arg1(), newValue, (Field<T>)this.$arg3());
    }

    @Override
    public final QOM.ArrayReplace<T> $arg3(Field<T> newValue) {
        return this.$constructor().apply((Field<T[]>)this.$arg1(), (Field<T>)this.$arg2(), newValue);
    }

    @Override
    public final Function3<? super Field<T[]>, ? super Field<T>, ? super Field<T>, ? extends QOM.ArrayReplace<T>> $constructor() {
        return (a1, a2, a3) -> new ArrayReplace((Field<T[]>)a1, a2, a3);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.ArrayReplace) {
            QOM.ArrayReplace o = (QOM.ArrayReplace)that;
            return StringUtils.equals(this.$arg1(), o.$arg1()) && StringUtils.equals(this.$arg2(), o.$arg2()) && StringUtils.equals(this.$arg3(), o.$arg3());
        }
        return super.equals(that);
    }
}

