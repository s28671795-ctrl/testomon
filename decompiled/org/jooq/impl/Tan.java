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

final class Tan
extends AbstractField<BigDecimal>
implements QOM.Tan {
    final Field<? extends Number> value;

    Tan(Field<? extends Number> value) {
        super(Names.N_TAN, Tools.allNotNull(SQLDataType.NUMERIC, value));
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
                ctx.visit(DSL.function(Names.N_TAN, this.getDataType(), this.value));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("tan(");
        ctx.visit(Val.getJavaValue(this.value));
        ctx.sql(')');
    }

    @Override
    public final Field<? extends Number> $arg1() {
        return this.value;
    }

    @Override
    public final QOM.Tan $arg1(Field<? extends Number> newValue) {
        return this.$constructor().apply((Field<? extends Number>)newValue);
    }

    @Override
    public final Function1<? super Field<? extends Number>, ? extends QOM.Tan> $constructor() {
        return a1 -> new Tan((Field<? extends Number>)a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Tan) {
            QOM.Tan o = (QOM.Tan)that;
            return StringUtils.equals(this.$value(), o.$value());
        }
        return super.equals(that);
    }
}

