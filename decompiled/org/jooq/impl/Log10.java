/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.math.BigDecimal;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.Pro;
import org.jooq.SQLDialect;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

final class Log10
extends AbstractField<BigDecimal>
implements QOM.Log10 {
    final Field<? extends Number> value;

    Log10(Field<? extends Number> value) {
        super(Names.N_LOG10, Tools.allNotNull(SQLDataType.NUMERIC, value));
        this.value = Tools.nullSafeNotNull(value, SQLDataType.INTEGER);
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        switch (ctx.family()) {
            case COCKROACHDB: 
            case HANA: 
            case INGRES: 
            case ORACLE: 
            case SNOWFLAKE: 
            case YUGABYTEDB: {
                return false;
            }
            case REDSHIFT: 
            case TERADATA: {
                return true;
            }
            case POSTGRES: 
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
            case COCKROACHDB: 
            case HANA: 
            case INGRES: 
            case ORACLE: 
            case SNOWFLAKE: 
            case YUGABYTEDB: {
                ctx.visit(DSL.log(this.value, DSL.inline(10)));
                break;
            }
            case REDSHIFT: 
            case TERADATA: {
                ctx.visit(DSL.function(Names.N_LOG, this.getDataType(), this.value));
                break;
            }
            case POSTGRES: 
            case SQLITE: {
                if (ctx.dialect().precedes(SQLDialect.POSTGRES_11)) {
                    ctx.visit(DSL.log(this.value, DSL.inline(10)));
                    break;
                }
                ctx.visit(Names.N_LOG10).sql('(').visit(this.value).sql(')');
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_LOG10, this.getDataType(), this.value));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("log10(");
        ctx.visit(Val.getJavaValue(this.value));
        ctx.sql(')');
    }

    @Override
    public final Field<? extends Number> $arg1() {
        return this.value;
    }

    @Override
    public final QOM.Log10 $arg1(Field<? extends Number> newValue) {
        return this.$constructor().apply((Field<? extends Number>)newValue);
    }

    @Override
    public final Function1<? super Field<? extends Number>, ? extends QOM.Log10> $constructor() {
        return a1 -> new Log10((Field<? extends Number>)a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Log10) {
            QOM.Log10 o = (QOM.Log10)that;
            return StringUtils.equals(this.$value(), o.$value());
        }
        return super.equals(that);
    }
}

