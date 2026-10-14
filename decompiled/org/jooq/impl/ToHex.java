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

final class ToHex
extends AbstractField<String>
implements QOM.ToHex {
    final Field<? extends Number> value;

    ToHex(Field<? extends Number> value) {
        super(Names.N_TO_HEX, Tools.allNotNull(SQLDataType.VARCHAR, value));
        this.value = Tools.nullSafeNotNull(value, SQLDataType.INTEGER);
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        switch (ctx.family()) {
            case MARIADB: 
            case MYSQL: {
                return true;
            }
            case ORACLE: 
            case H2: {
                return false;
            }
            case SQLITE: {
                return false;
            }
            case SQLSERVER: {
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
            case MARIADB: 
            case MYSQL: {
                ctx.visit(DSL.function(Names.N_HEX, this.getDataType(), this.value));
                break;
            }
            case ORACLE: 
            case H2: {
                ctx.visit(DSL.trim(DSL.toChar(this.value, DSL.inline("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"))));
                break;
            }
            case SQLITE: {
                ctx.visit(DSL.function(Names.N_PRINTF, this.getDataType(), DSL.inline("%X"), this.value));
                break;
            }
            case SQLSERVER: {
                ctx.visit(DSL.function(Names.N_FORMAT, this.getDataType(), this.value, DSL.inline("X")));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_TO_HEX, this.getDataType(), this.value));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("toHex(");
        ctx.visit(Val.getJavaValue(this.value));
        ctx.sql(')');
    }

    @Override
    public final Field<? extends Number> $arg1() {
        return this.value;
    }

    @Override
    public final QOM.ToHex $arg1(Field<? extends Number> newValue) {
        return this.$constructor().apply((Field<? extends Number>)newValue);
    }

    @Override
    public final Function1<? super Field<? extends Number>, ? extends QOM.ToHex> $constructor() {
        return a1 -> new ToHex((Field<? extends Number>)a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.ToHex) {
            QOM.ToHex o = (QOM.ToHex)that;
            return StringUtils.equals(this.$value(), o.$value());
        }
        return super.equals(that);
    }
}

