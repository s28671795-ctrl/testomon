/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.math.BigDecimal;
import java.math.MathContext;
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

final class Trunc<T extends Number>
extends AbstractField<T>
implements QOM.Trunc<T> {
    final Field<T> value;
    final Field<Integer> decimals;

    Trunc(Field<T> value, Field<Integer> decimals) {
        super(Names.N_TRUNC, Tools.allNotNull(Tools.dataType(SQLDataType.INTEGER, value, false), value, decimals));
        this.value = Tools.nullSafeNotNull(value, SQLDataType.INTEGER);
        this.decimals = Tools.nullSafeNotNull(decimals, SQLDataType.INTEGER);
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case ACCESS: 
            case ASE: 
            case DERBY: {
                Integer decimalsVal = Tools.extractParamValue(this.decimals);
                Field<BigDecimal> power = decimalsVal != null ? DSL.inline(BigDecimal.TEN.pow(decimalsVal, MathContext.DECIMAL128)) : DSL.power(DSL.inline(BigDecimal.TEN), this.decimals);
                ctx.visit(DSL.decode().when(this.value.sign().greaterOrEqual(DSL.zero()), Internal.idiv(Internal.imul(this.value, power).floor(), power)).otherwise(Internal.idiv(Internal.imul(this.value, power).ceil(), power)));
                break;
            }
            case AURORA_MYSQL: 
            case MEMSQL: 
            case H2: 
            case MARIADB: 
            case MYSQL: {
                ctx.visit(Names.N_TRUNCATE).sql('(').visit(this.value).sql(", ").visit(this.decimals).sql(')');
                break;
            }
            case AURORA_POSTGRES: 
            case COCKROACHDB: 
            case POSTGRES: 
            case YUGABYTEDB: {
                ctx.visit(Tools.castIfNeeded(DSL.function(Names.N_TRUNC, SQLDataType.NUMERIC, Tools.castIfNeeded(this.value, SQLDataType.NUMERIC), this.decimals), this.value.getDataType()));
                break;
            }
            case JAVA: {
                this.acceptJava(ctx);
                break;
            }
            case HANA: {
                ctx.visit(Names.N_ROUND).sql('(').visit(this.value).sql(", ").visit(this.decimals).sql(", ").visit(Names.N_ROUND_DOWN).sql(')');
                break;
            }
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: {
                ctx.visit(Names.N_ROUND).sql('(').visit(this.value).sql(", ").visit(this.decimals).sql(", 1)");
                break;
            }
            case SYBASE: {
                ctx.visit(Names.N_TRUNCNUM).sql('(').visit(this.value).sql(", ").visit(this.decimals).sql(')');
                break;
            }
            default: {
                ctx.visit(Names.N_TRUNC).sql('(').visit(this.value).sql(", ").visit(this.decimals).sql(')');
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("trunc(");
        String s2 = "";
        ctx.sql(s2).visit(Val.getJavaValue(this.value));
        s2 = ", ";
        ctx.sql(s2).visit(Val.getJavaValue(this.decimals));
        s2 = ", ";
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
    public final QOM.Trunc<T> $arg1(Field<T> newValue) {
        return this.$constructor().apply(newValue, (Field<Integer>)this.$arg2());
    }

    @Override
    public final QOM.Trunc<T> $arg2(Field<Integer> newValue) {
        return this.$constructor().apply((Field<T>)this.$arg1(), newValue);
    }

    @Override
    public final Function2<? super Field<T>, ? super Field<Integer>, ? extends QOM.Trunc<T>> $constructor() {
        return (a1, a2) -> new Trunc(a1, (Field<Integer>)a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Trunc) {
            QOM.Trunc o = (QOM.Trunc)that;
            return StringUtils.equals(this.$value(), o.$value()) && StringUtils.equals(this.$decimals(), o.$decimals());
        }
        return super.equals(that);
    }
}

