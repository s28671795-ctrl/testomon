/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function2;
import org.jooq.Pro;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

final class Right
extends AbstractField<String>
implements QOM.Right {
    final Field<String> string;
    final Field<? extends Number> length;

    Right(Field<String> string, Field<? extends Number> length) {
        super(Names.N_RIGHT, Tools.allNotNull(SQLDataType.VARCHAR, string, length));
        this.string = Tools.nullSafeNotNull(string, SQLDataType.VARCHAR);
        this.length = Tools.nullSafeNotNull(length, SQLDataType.INTEGER);
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        switch (ctx.family()) {
            case DERBY: {
                return false;
            }
            case ORACLE: 
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
            case DERBY: {
                ctx.visit(DSL.substring(this.string, Internal.iadd(DSL.length(this.string), Internal.isub(DSL.one(), this.length))));
                break;
            }
            case ORACLE: 
            case SQLITE: {
                ctx.visit(DSL.substring(this.string, Internal.ineg(this.length)));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_RIGHT, this.getDataType(), this.string, this.length));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("right(");
        String s2 = "";
        ctx.sql(s2).visit(Val.getJavaValue(this.string));
        s2 = ", ";
        ctx.sql(s2).visit(Val.getJavaValue(this.length));
        s2 = ", ";
        ctx.sql(')');
    }

    @Override
    public final Field<String> $arg1() {
        return this.string;
    }

    @Override
    public final Field<? extends Number> $arg2() {
        return this.length;
    }

    @Override
    public final QOM.Right $arg1(Field<String> newValue) {
        return this.$constructor().apply((Field<String>)newValue, (Field<? extends Number>)this.$arg2());
    }

    @Override
    public final QOM.Right $arg2(Field<? extends Number> newValue) {
        return this.$constructor().apply((Field<String>)this.$arg1(), (Field<? extends Number>)newValue);
    }

    @Override
    public final Function2<? super Field<String>, ? super Field<? extends Number>, ? extends QOM.Right> $constructor() {
        return (a1, a2) -> new Right((Field<String>)a1, (Field<? extends Number>)a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Right) {
            QOM.Right o = (QOM.Right)that;
            return StringUtils.equals(this.$string(), o.$string()) && StringUtils.equals(this.$length(), o.$length());
        }
        return super.equals(that);
    }
}

