/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function2;
import org.jooq.Pro;
import org.jooq.impl.AbstractField;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;

final class ArrayConcat<T>
extends AbstractField<T[]>
implements QOM.ArrayConcat<T> {
    final Field<T[]> arg1;
    final Field<T[]> arg2;

    ArrayConcat(Field<T[]> arg1, Field<T[]> arg2) {
        super(Names.N_ARRAY_CONCAT, Tools.allNotNull(Tools.dataType(SQLDataType.OTHER.array(), arg1, false), arg1, arg2));
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
            default: {
                ctx.sql('(').visit(this.arg1).sql(" || ").visit(this.arg2).sql(')');
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("arrayConcat(");
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
    public final QOM.ArrayConcat<T> $arg1(Field<T[]> newValue) {
        return this.$constructor().apply(newValue, (Field<T[]>)this.$arg2());
    }

    @Override
    public final QOM.ArrayConcat<T> $arg2(Field<T[]> newValue) {
        return this.$constructor().apply((Field<T[]>)this.$arg1(), newValue);
    }

    @Override
    public final Function2<? super Field<T[]>, ? super Field<T[]>, ? extends QOM.ArrayConcat<T>> $constructor() {
        return (a1, a2) -> new ArrayConcat((Field<T[]>)a1, (Field<T[]>)a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.ArrayConcat) {
            QOM.ArrayConcat o = (QOM.ArrayConcat)that;
            return StringUtils.equals(this.$arg1(), o.$arg1()) && StringUtils.equals(this.$arg2(), o.$arg2());
        }
        return super.equals(that);
    }
}

