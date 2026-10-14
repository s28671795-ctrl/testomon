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

final class CharLength
extends AbstractField<Integer>
implements QOM.CharLength {
    final Field<String> string;

    CharLength(Field<String> string) {
        super(Names.N_CHAR_LENGTH, Tools.allNotNull(SQLDataType.INTEGER, string));
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
            case ACCESS: 
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: {
                ctx.visit(DSL.function(Names.N_LEN, this.getDataType(), this.string));
                break;
            }
            case DB2: 
            case HANA: 
            case INGRES: 
            case ORACLE: 
            case SNOWFLAKE: 
            case SYBASE: 
            case TERADATA: 
            case DERBY: 
            case SQLITE: {
                ctx.visit(DSL.function(Names.N_LENGTH, this.getDataType(), this.string));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_CHAR_LENGTH, this.getDataType(), this.string));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("charLength(");
        ctx.visit(Val.getJavaValue(this.string));
        ctx.sql(')');
    }

    @Override
    public final Field<String> $arg1() {
        return this.string;
    }

    @Override
    public final QOM.CharLength $arg1(Field<String> newValue) {
        return this.$constructor().apply((Field<String>)newValue);
    }

    @Override
    public final Function1<? super Field<String>, ? extends QOM.CharLength> $constructor() {
        return a1 -> new CharLength((Field<String>)a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.CharLength) {
            QOM.CharLength o = (QOM.CharLength)that;
            return StringUtils.equals(this.$string(), o.$string());
        }
        return super.equals(that);
    }
}

