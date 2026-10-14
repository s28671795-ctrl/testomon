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

final class Asinh
extends AbstractField<BigDecimal>
implements QOM.Asinh {
    final Field<? extends Number> value;

    Asinh(Field<? extends Number> value) {
        super(Names.N_ASINH, Tools.allNotNull(SQLDataType.NUMERIC, value));
        this.value = Tools.nullSafeNotNull(value, SQLDataType.INTEGER);
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        switch (ctx.family()) {
            case ACCESS: 
            case ASE: 
            case AURORA_MYSQL: 
            case DB2: 
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
            case DB2: 
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
                ctx.visit(DSL.ln(Internal.iadd(this.value, DSL.sqrt(Internal.iadd(DSL.square(this.value), DSL.one())))));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_ASINH, this.getDataType(), this.value));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("asinh(");
        ctx.visit(Val.getJavaValue(this.value));
        ctx.sql(')');
    }

    @Override
    public final Field<? extends Number> $arg1() {
        return this.value;
    }

    @Override
    public final QOM.Asinh $arg1(Field<? extends Number> newValue) {
        return this.$constructor().apply((Field<? extends Number>)newValue);
    }

    @Override
    public final Function1<? super Field<? extends Number>, ? extends QOM.Asinh> $constructor() {
        return a1 -> new Asinh((Field<? extends Number>)a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Asinh) {
            QOM.Asinh o = (QOM.Asinh)that;
            return StringUtils.equals(this.$value(), o.$value());
        }
        return super.equals(that);
    }
}

