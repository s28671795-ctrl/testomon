/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function2;
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

final class BitXNor<T extends Number>
extends AbstractField<T>
implements QOM.BitXNor<T> {
    final Field<T> arg1;
    final Field<T> arg2;

    BitXNor(Field<T> arg1, Field<T> arg2) {
        super(Names.N_BIT_XNOR, Tools.allNotNull(Tools.dataType(SQLDataType.INTEGER, arg1, false), arg1, arg2));
        this.arg1 = Tools.nullSafeNotNull(arg1, SQLDataType.INTEGER);
        this.arg2 = Tools.nullSafeNotNull(arg2, SQLDataType.INTEGER);
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        switch (ctx.family()) {
            case ASE: 
            case AURORA_MYSQL: 
            case AURORA_POSTGRES: 
            case BIGQUERY: 
            case COCKROACHDB: 
            case DB2: 
            case EXASOL: 
            case HANA: 
            case INFORMIX: 
            case MEMSQL: 
            case ORACLE: 
            case REDSHIFT: 
            case SNOWFLAKE: 
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: 
            case SYBASE: 
            case TERADATA: 
            case VERTICA: 
            case CUBRID: 
            case FIREBIRD: 
            case HSQLDB: 
            case MARIADB: 
            case MYSQL: 
            case POSTGRES: 
            case SQLITE: 
            case YUGABYTEDB: {
                return false;
            }
            case H2: {
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
            case ASE: 
            case AURORA_MYSQL: 
            case AURORA_POSTGRES: 
            case BIGQUERY: 
            case COCKROACHDB: 
            case DB2: 
            case EXASOL: 
            case HANA: 
            case INFORMIX: 
            case MEMSQL: 
            case ORACLE: 
            case REDSHIFT: 
            case SNOWFLAKE: 
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: 
            case SYBASE: 
            case TERADATA: 
            case VERTICA: 
            case CUBRID: 
            case FIREBIRD: 
            case HSQLDB: 
            case MARIADB: 
            case MYSQL: 
            case POSTGRES: 
            case SQLITE: 
            case YUGABYTEDB: {
                ctx.visit(DSL.bitNot(DSL.bitXor(this.arg1, this.arg2)));
                break;
            }
            case H2: {
                if (ctx.dialect().precedes(SQLDialect.H2_1_4_200)) {
                    ctx.visit(DSL.bitNot(DSL.bitXor(this.arg1, this.arg2)));
                    break;
                }
                ctx.visit(DSL.function(Names.N_BITXNOR, this.getDataType(), this.arg1, this.arg2));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_BIT_XNOR, this.getDataType(), this.arg1, this.arg2));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.visit(Val.getJavaValue(this.arg1)).sql(".bitXNor(").visit(Val.getJavaValue(this.arg2)).sql(')');
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
    public final QOM.BitXNor<T> $arg1(Field<T> newValue) {
        return this.$constructor().apply(newValue, (Field<T>)this.$arg2());
    }

    @Override
    public final QOM.BitXNor<T> $arg2(Field<T> newValue) {
        return this.$constructor().apply((Field<T>)this.$arg1(), newValue);
    }

    @Override
    public final Function2<? super Field<T>, ? super Field<T>, ? extends QOM.BitXNor<T>> $constructor() {
        return (a1, a2) -> new BitXNor(a1, a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.BitXNor) {
            QOM.BitXNor o = (QOM.BitXNor)that;
            return StringUtils.equals(this.$arg1(), o.$arg1()) && StringUtils.equals(this.$arg2(), o.$arg2());
        }
        return super.equals(that);
    }
}

