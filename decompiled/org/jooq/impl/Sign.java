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

final class Sign
extends AbstractField<Integer>
implements QOM.Sign {
    final Field<? extends Number> value;

    Sign(Field<? extends Number> value) {
        super(Names.N_SIGN, Tools.allNotNull(SQLDataType.INTEGER, value));
        this.value = Tools.nullSafeNotNull(value, SQLDataType.INTEGER);
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        switch (ctx.family()) {
            case ACCESS: {
                return true;
            }
            case SQLITE: {
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
            case ACCESS: {
                ctx.visit(DSL.function(Names.N_SGN, this.getDataType(), this.value));
                break;
            }
            case SQLITE: {
                ctx.visit(DSL.when(this.value.gt(DSL.zero()), DSL.inline(1)).when(this.value.lt(DSL.zero()), (Field<Integer>)DSL.inline(-1)).when(this.value.eq(DSL.zero()), (Field<Integer>)DSL.inline(0)));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_SIGN, this.getDataType(), this.value));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("sign(");
        ctx.visit(Val.getJavaValue(this.value));
        ctx.sql(')');
    }

    @Override
    public final Field<? extends Number> $arg1() {
        return this.value;
    }

    @Override
    public final QOM.Sign $arg1(Field<? extends Number> newValue) {
        return this.$constructor().apply((Field<? extends Number>)newValue);
    }

    @Override
    public final Function1<? super Field<? extends Number>, ? extends QOM.Sign> $constructor() {
        return a1 -> new Sign((Field<? extends Number>)a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Sign) {
            QOM.Sign o = (QOM.Sign)that;
            return StringUtils.equals(this.$value(), o.$value());
        }
        return super.equals(that);
    }
}

