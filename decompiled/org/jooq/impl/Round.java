/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.math.BigDecimal;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function2;
import org.jooq.Param;
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

final class Round<T extends Number>
extends AbstractField<T>
implements QOM.Round<T> {
    final Field<T> value;
    final Field<Integer> decimals;

    Round(Field<T> value) {
        super(Names.N_ROUND, Tools.allNotNull(Tools.dataType(SQLDataType.INTEGER, value, false), value));
        this.value = Tools.nullSafeNotNull(value, SQLDataType.INTEGER);
        this.decimals = null;
    }

    Round(Field<T> value, Field<Integer> decimals) {
        super(Names.N_ROUND, Tools.allNotNull(Tools.dataType(SQLDataType.INTEGER, value, false), value, decimals));
        this.value = Tools.nullSafeNotNull(value, SQLDataType.INTEGER);
        this.decimals = Tools.nullSafeNotNull(decimals, SQLDataType.INTEGER);
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case DERBY: {
                if (this.decimals == null) {
                    ctx.visit(DSL.when(Internal.isub(this.value, DSL.floor(this.value)).lessThan(DSL.inline(Double.valueOf(0.5))), DSL.floor(this.value)).otherwise(DSL.ceil(this.value)));
                    return;
                }
                Field<Integer> field2 = this.decimals;
                if (field2 instanceof Param) {
                    Param p = (Param)field2;
                    Integer decimalsValue = (Integer)p.getValue();
                    Param<BigDecimal> factor = DSL.val(BigDecimal.ONE.movePointRight(decimalsValue));
                    Field<T> mul = Internal.imul(this.value, factor);
                    ctx.visit(DSL.when(Internal.isub(mul, DSL.floor(mul)).lessThan(DSL.inline(Double.valueOf(0.5))), Internal.idiv(DSL.floor(mul), factor)).otherwise(Internal.idiv(DSL.ceil(mul), factor)));
                    return;
                }
            }
            case ASE: 
            case INGRES: 
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: 
            case SYBASE: {
                if (this.decimals == null) {
                    ctx.visit(DSL.function(Names.N_ROUND, this.getDataType(), this.value, DSL.inline(0)));
                } else {
                    ctx.visit(DSL.function(Names.N_ROUND, this.getDataType(), this.value, this.decimals));
                }
                return;
            }
            case COCKROACHDB: {
                if (this.decimals == null) {
                    ctx.visit(DSL.function(Names.N_ROUND, this.getDataType(), DSL.cast(this.value, SQLDataType.NUMERIC)));
                } else {
                    ctx.visit(DSL.function(Names.N_ROUND, this.getDataType(), DSL.cast(this.value, SQLDataType.NUMERIC), this.decimals));
                }
                return;
            }
            case AURORA_POSTGRES: 
            case POSTGRES: 
            case YUGABYTEDB: {
                if (this.decimals == null) {
                    ctx.visit(DSL.function(Names.N_ROUND, this.getDataType(), this.value));
                } else {
                    ctx.visit(DSL.function(Names.N_ROUND, this.getDataType(), Tools.castIfNeeded(this.value, SQLDataType.NUMERIC), this.decimals));
                }
                return;
            }
        }
        if (this.decimals == null) {
            ctx.visit(DSL.function(Names.N_ROUND, this.getDataType(), this.value));
        } else {
            ctx.visit(DSL.function(Names.N_ROUND, this.getDataType(), this.value, this.decimals));
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("round(");
        String s2 = "";
        ctx.sql(s2).visit(Val.getJavaValue(this.value));
        s2 = ", ";
        if (this.decimals != null) {
            ctx.sql(s2).visit(Val.getJavaValue(this.decimals));
            s2 = ", ";
        }
        ctx.sql(')');
    }

    @Override
    public final Field<T> $arg1() {
        return this.value;
    }

    @Override
    public final Field<Integer> $arg2() {
        return this.decimals;
    }

    @Override
    public final QOM.Round<T> $arg1(Field<T> newValue) {
        return this.$constructor().apply(newValue, (Field<Integer>)this.$arg2());
    }

    @Override
    public final QOM.Round<T> $arg2(Field<Integer> newValue) {
        return this.$constructor().apply((Field<T>)this.$arg1(), newValue);
    }

    @Override
    public final Function2<? super Field<T>, ? super Field<Integer>, ? extends QOM.Round<T>> $constructor() {
        return (a1, a2) -> new Round(a1, (Field<Integer>)a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Round) {
            QOM.Round o = (QOM.Round)that;
            return StringUtils.equals(this.$value(), o.$value()) && StringUtils.equals(this.$decimals(), o.$decimals());
        }
        return super.equals(that);
    }
}

