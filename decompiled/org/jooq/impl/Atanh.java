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

final class Atanh
extends AbstractField<BigDecimal>
implements QOM.Atanh {
    final Field<? extends Number> value;

    Atanh(Field<? extends Number> value) {
        super(Names.N_ATANH, Tools.allNotNull(SQLDataType.NUMERIC, value));
        this.value = Tools.nullSafeNotNull(value, SQLDataType.INTEGER);
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        switch (ctx.family()) {
            case ACCESS: 
            case ASE: 
            case AURORA_MYSQL: 
            case EXASOL: 
            case HANA: 
            case INGRES: 
            case MEMSQL: 
            case ORACLE: 
            case REDSHIFT: 
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: 
            case SYBASE: 
            case VERTICA: 
            case CUBRID: 
            case DERBY: 
            case H2: 
            case HSQLDB: 
            case IGNITE: 
            case MARIADB: 
            case MYSQL: 
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
            case EXASOL: 
            case HANA: 
            case INGRES: 
            case MEMSQL: 
            case ORACLE: 
            case REDSHIFT: 
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: 
            case SYBASE: 
            case VERTICA: 
            case CUBRID: 
            case DERBY: 
            case H2: 
            case HSQLDB: 
            case IGNITE: 
            case MARIADB: 
            case MYSQL: 
            case YUGABYTEDB: {
                ctx.visit(Internal.idiv(DSL.ln(Internal.idiv(Internal.iadd(DSL.one(), this.value), Internal.isub(DSL.one(), this.value))), DSL.two()));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_ATANH, this.getDataType(), this.value));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("atanh(");
        ctx.visit(Val.getJavaValue(this.value));
        ctx.sql(')');
    }

    @Override
    public final Field<? extends Number> $arg1() {
        return this.value;
    }

    @Override
    public final QOM.Atanh $arg1(Field<? extends Number> newValue) {
        return this.$constructor().apply((Field<? extends Number>)newValue);
    }

    @Override
    public final Function1<? super Field<? extends Number>, ? extends QOM.Atanh> $constructor() {
        return a1 -> new Atanh((Field<? extends Number>)a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Atanh) {
            QOM.Atanh o = (QOM.Atanh)that;
            return StringUtils.equals(this.$value(), o.$value());
        }
        return super.equals(that);
    }
}

