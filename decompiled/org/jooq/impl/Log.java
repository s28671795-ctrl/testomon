/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.math.BigDecimal;
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

final class Log
extends AbstractField<BigDecimal>
implements QOM.Log {
    final Field<? extends Number> value;
    final Field<? extends Number> base;

    Log(Field<? extends Number> value, Field<? extends Number> base) {
        super(Names.N_LOG, Tools.allNotNull(SQLDataType.NUMERIC, value, base));
        this.value = Tools.nullSafeNotNull(value, SQLDataType.INTEGER);
        this.base = Tools.nullSafeNotNull(base, SQLDataType.INTEGER);
    }

    @Override
    public final void accept(Context<?> ctx) {
        block0 : switch (ctx.family()) {
            case JAVA: {
                this.acceptJava(ctx);
                break;
            }
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: {
                switch (ctx.dialect()) {
                    case SQLSERVER2008: {
                        ctx.visit(Internal.idiv(DSL.ln(this.value), DSL.ln(this.base)));
                        break block0;
                    }
                }
                ctx.visit(DSL.function(Names.N_LOG, SQLDataType.NUMERIC, this.value, this.base));
                break;
            }
            case ACCESS: 
            case ASE: 
            case COCKROACHDB: 
            case DB2: 
            case INFORMIX: 
            case INGRES: 
            case SYBASE: 
            case TERADATA: 
            case DERBY: 
            case HSQLDB: 
            case IGNITE: 
            case SQLITE: {
                ctx.visit(Internal.idiv(DSL.ln(this.value), DSL.ln(this.base)));
                break;
            }
            case BIGQUERY: {
                ctx.visit(DSL.function(Names.N_LOG, SQLDataType.NUMERIC, this.value, this.base));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_LOG, SQLDataType.NUMERIC, this.base, this.value));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("log(");
        String s2 = "";
        ctx.sql(s2).visit(Val.getJavaValue(this.value));
        s2 = ", ";
        ctx.sql(s2).visit(Val.getJavaValue(this.base));
        s2 = ", ";
        ctx.sql(')');
    }

    @Override
    public final Field<? extends Number> $arg1() {
        return this.value;
    }

    @Override
    public final Field<? extends Number> $arg2() {
        return this.base;
    }

    @Override
    public final QOM.Log $arg1(Field<? extends Number> newValue) {
        return this.$constructor().apply((Field<? extends Number>)newValue, (Field<? extends Number>)this.$arg2());
    }

    @Override
    public final QOM.Log $arg2(Field<? extends Number> newValue) {
        return this.$constructor().apply((Field<? extends Number>)this.$arg1(), (Field<? extends Number>)newValue);
    }

    @Override
    public final Function2<? super Field<? extends Number>, ? super Field<? extends Number>, ? extends QOM.Log> $constructor() {
        return (a1, a2) -> new Log((Field<? extends Number>)a1, (Field<? extends Number>)a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Log) {
            QOM.Log o = (QOM.Log)that;
            return StringUtils.equals(this.$value(), o.$value()) && StringUtils.equals(this.$base(), o.$base());
        }
        return super.equals(that);
    }
}

