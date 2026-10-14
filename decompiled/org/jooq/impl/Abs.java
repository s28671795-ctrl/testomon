/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.Pro;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

final class Abs<T extends Number>
extends AbstractField<T>
implements QOM.Abs<T> {
    final Field<T> value;

    Abs(Field<T> value) {
        super(Names.N_ABS, Tools.allNotNull(Tools.dataType(SQLDataType.INTEGER, value, false), value));
        this.value = Tools.nullSafeNotNull(value, SQLDataType.INTEGER);
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        return true;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                this.acceptJava(ctx);
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_ABS, this.getDataType(), this.value));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("abs(");
        ctx.visit(Val.getJavaValue(this.value));
        ctx.sql(')');
    }

    @Override
    public final Field<T> $arg1() {
        return this.value;
    }

    @Override
    public final QOM.Abs<T> $arg1(Field<T> newValue) {
        return this.$constructor().apply(newValue);
    }

    @Override
    public final Function1<? super Field<T>, ? extends QOM.Abs<T>> $constructor() {
        return a1 -> new Abs(a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Abs) {
            QOM.Abs o = (QOM.Abs)that;
            return StringUtils.equals(this.$value(), o.$value());
        }
        return super.equals(that);
    }
}

