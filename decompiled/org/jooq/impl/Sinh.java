/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.math.BigDecimal;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function1;
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

final class Sinh
extends AbstractField<BigDecimal>
implements QOM.Sinh {
    final Field<? extends Number> value;

    Sinh(Field<? extends Number> value) {
        super(Names.N_SINH, Tools.allNotNull(SQLDataType.NUMERIC, value));
        this.value = Tools.nullSafeNotNull(value, SQLDataType.INTEGER);
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        switch (ctx.family()) {
            case ACCESS: 
            case ASE: 
            case AURORA_MYSQL: 
            case AURORA_POSTGRES: 
            case COCKROACHDB: 
            case INGRES: 
            case MEMSQL: 
            case REDSHIFT: 
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: 
            case SYBASE: 
            case VERTICA: 
            case CUBRID: 
            case HSQLDB: 
            case MARIADB: 
            case MYSQL: 
            case POSTGRES: 
            case YUGABYTEDB: {
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
            case ASE: 
            case AURORA_MYSQL: 
            case AURORA_POSTGRES: 
            case COCKROACHDB: 
            case INGRES: 
            case MEMSQL: 
            case REDSHIFT: 
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: 
            case SYBASE: 
            case VERTICA: 
            case CUBRID: 
            case HSQLDB: 
            case MARIADB: 
            case MYSQL: 
            case POSTGRES: 
            case YUGABYTEDB: {
                ctx.visit(Internal.idiv(Internal.isub(DSL.exp(Internal.imul(this.value, DSL.two())), DSL.one()), Internal.imul(DSL.exp(this.value), DSL.two())));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_SINH, this.getDataType(), this.value));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("sinh(");
        ctx.visit(Val.getJavaValue(this.value));
        ctx.sql(')');
    }

    @Override
    public final Field<? extends Number> $arg1() {
        return this.value;
    }

    @Override
    public final QOM.Sinh $arg1(Field<? extends Number> newValue) {
        return this.$constructor().apply((Field<? extends Number>)newValue);
    }

    @Override
    public final Function1<? super Field<? extends Number>, ? extends QOM.Sinh> $constructor() {
        return a1 -> new Sinh((Field<? extends Number>)a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Sinh) {
            QOM.Sinh o = (QOM.Sinh)that;
            return StringUtils.equals(this.$value(), o.$value());
        }
        return super.equals(that);
    }
}

