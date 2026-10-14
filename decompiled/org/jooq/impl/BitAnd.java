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
import org.jooq.impl.Expression;
import org.jooq.impl.Names;
import org.jooq.impl.Operators;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

final class BitAnd<T extends Number>
extends AbstractField<T>
implements QOM.BitAnd<T> {
    final Field<T> arg1;
    final Field<T> arg2;

    BitAnd(Field<T> arg1, Field<T> arg2) {
        super(Names.N_BIT_AND, Tools.allNotNull(Tools.dataType(SQLDataType.INTEGER, arg1, false), arg1, arg2));
        this.arg1 = Tools.nullSafeNotNull(arg1, SQLDataType.INTEGER);
        this.arg2 = Tools.nullSafeNotNull(arg2, SQLDataType.INTEGER);
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        switch (ctx.family()) {
            case DB2: 
            case HANA: 
            case INFORMIX: 
            case ORACLE: 
            case SNOWFLAKE: 
            case TERADATA: 
            case H2: 
            case HSQLDB: {
                return true;
            }
            case EXASOL: {
                return true;
            }
            case FIREBIRD: {
                return true;
            }
        }
        return false;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                this.acceptJava(ctx);
                break;
            }
            case DB2: 
            case HANA: 
            case INFORMIX: 
            case ORACLE: 
            case SNOWFLAKE: 
            case TERADATA: 
            case H2: 
            case HSQLDB: {
                ctx.visit(DSL.function(Names.N_BITAND, this.getDataType(), this.arg1, this.arg2));
                break;
            }
            case EXASOL: {
                ctx.visit(DSL.function(Names.N_BIT_AND, this.getDataType(), this.arg1, this.arg2));
                break;
            }
            case FIREBIRD: {
                ctx.visit(DSL.function(Names.N_BIN_AND, this.getDataType(), this.arg1, this.arg2));
                break;
            }
            default: {
                ctx.sql('(');
                Expression.acceptAssociative(ctx, this, q -> new Expression.Expr(q.arg1, Operators.OP_AMP, q.arg2), c -> c.sql(' '));
                ctx.sql(')');
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.visit(Val.getJavaValue(this.arg1)).sql(".bitAnd(").visit(Val.getJavaValue(this.arg2)).sql(')');
    }

    @Override
    public final Field<T> $arg1() {
        return this.arg1;
    }

    @Override
    public final Field<T> $arg2() {
        return this.arg2;
    }

    @Override
    public final QOM.BitAnd<T> $arg1(Field<T> newValue) {
        return this.$constructor().apply(newValue, (Field<T>)this.$arg2());
    }

    @Override
    public final QOM.BitAnd<T> $arg2(Field<T> newValue) {
        return this.$constructor().apply((Field<T>)this.$arg1(), newValue);
    }

    @Override
    public final Function2<? super Field<T>, ? super Field<T>, ? extends QOM.BitAnd<T>> $constructor() {
        return (a1, a2) -> new BitAnd(a1, a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.BitAnd) {
            QOM.BitAnd o = (QOM.BitAnd)that;
            return StringUtils.equals(this.$arg1(), o.$arg1()) && StringUtils.equals(this.$arg2(), o.$arg2());
        }
        return super.equals(that);
    }
}

