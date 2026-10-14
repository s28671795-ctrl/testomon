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

final class Ascii
extends AbstractField<Integer>
implements QOM.Ascii {
    final Field<String> string;

    Ascii(Field<String> string) {
        super(Names.N_ASCII, Tools.allNotNull(SQLDataType.INTEGER, string));
        this.string = Tools.nullSafeNotNull(string, SQLDataType.VARCHAR);
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
                ctx.visit(DSL.function(Names.N_ASC, this.getDataType(), this.string));
                break;
            }
            case FIREBIRD: {
                ctx.visit(DSL.function(Names.N_ASCII_VAL, this.getDataType(), this.string));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_ASCII, this.getDataType(), this.string));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("ascii(");
        ctx.visit(Val.getJavaValue(this.string));
        ctx.sql(')');
    }

    @Override
    public final Field<String> $arg1() {
        return this.string;
    }

    @Override
    public final QOM.Ascii $arg1(Field<String> newValue) {
        return this.$constructor().apply((Field<String>)newValue);
    }

    @Override
    public final Function1<? super Field<String>, ? extends QOM.Ascii> $constructor() {
        return a1 -> new Ascii((Field<String>)a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Ascii) {
            QOM.Ascii o = (QOM.Ascii)that;
            return StringUtils.equals(this.$string(), o.$string());
        }
        return super.equals(that);
    }
}

