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
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

final class Mod<T extends Number>
extends AbstractField<T>
implements QOM.Mod<T> {
    final Field<T> dividend;
    final Field<? extends Number> divisor;

    Mod(Field<T> dividend, Field<? extends Number> divisor) {
        super(Names.N_MOD, Tools.allNotNull(Tools.dataType(SQLDataType.INTEGER, dividend, false), dividend, divisor));
        this.dividend = Tools.nullSafeNotNull(dividend, SQLDataType.INTEGER);
        this.divisor = Tools.nullSafeNotNull(divisor, SQLDataType.INTEGER);
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        switch (ctx.family()) {
            case ACCESS: 
            case TERADATA: {
                return false;
            }
            case ASE: 
            case REDSHIFT: 
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: 
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
            case ACCESS: 
            case TERADATA: {
                ctx.sql('(').visit(this.dividend).sql(' ').visit(Keywords.K_MOD).sql(' ').visit(this.divisor).sql(')');
                break;
            }
            case ASE: 
            case REDSHIFT: 
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: 
            case SQLITE: {
                ctx.sql('(').visit(this.dividend).sql(" % ").visit(this.divisor).sql(')');
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_MOD, this.getDataType(), this.dividend, this.divisor));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.visit(Val.getJavaValue(this.dividend)).sql(".mod(").visit(Val.getJavaValue(this.divisor)).sql(')');
    }

    @Override
    public final Field<T> $arg1() {
        return this.dividend;
    }

    @Override
    public final Field<? extends Number> $arg2() {
        return this.divisor;
    }

    @Override
    public final QOM.Mod<T> $arg1(Field<T> newValue) {
        return this.$constructor().apply(newValue, (Field<Number>)this.$arg2());
    }

    @Override
    public final QOM.Mod<T> $arg2(Field<? extends Number> newValue) {
        return this.$constructor().apply((Field<T>)this.$arg1(), (Field<Number>)newValue);
    }

    @Override
    public final Function2<? super Field<T>, ? super Field<? extends Number>, ? extends QOM.Mod<T>> $constructor() {
        return (a1, a2) -> new Mod(a1, (Field<? extends Number>)((Field<Number>)a2));
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Mod) {
            QOM.Mod o = (QOM.Mod)that;
            return StringUtils.equals(this.$dividend(), o.$dividend()) && StringUtils.equals(this.$divisor(), o.$divisor());
        }
        return super.equals(that);
    }
}

