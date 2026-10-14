/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.math.BigDecimal;
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

final class Atan
extends AbstractField<BigDecimal>
implements QOM.Atan {
    final Field<? extends Number> value;

    Atan(Field<? extends Number> value) {
        super(Names.N_ATAN, Tools.allNotNull(SQLDataType.NUMERIC, value));
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
            case ACCESS: {
                ctx.visit(DSL.function(Names.N_ATN, this.getDataType(), this.value));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_ATAN, this.getDataType(), this.value));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("atan(");
        ctx.visit(Val.getJavaValue(this.value));
        ctx.sql(')');
    }

    @Override
    public final Field<? extends Number> $arg1() {
        return this.value;
    }

    @Override
    public final QOM.Atan $arg1(Field<? extends Number> newValue) {
        return this.$constructor().apply((Field<? extends Number>)newValue);
    }

    @Override
    public final Function1<? super Field<? extends Number>, ? extends QOM.Atan> $constructor() {
        return a1 -> new Atan((Field<? extends Number>)a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Atan) {
            QOM.Atan o = (QOM.Atan)that;
            return StringUtils.equals(this.$value(), o.$value());
        }
        return super.equals(that);
    }
}

