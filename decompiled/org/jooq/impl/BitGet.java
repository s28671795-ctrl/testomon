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

final class BitGet<T extends Number>
extends AbstractField<T>
implements QOM.BitGet<T> {
    final Field<T> value;
    final Field<? extends Number> bit;

    BitGet(Field<T> value, Field<? extends Number> bit) {
        super(Names.N_BIT_GET, Tools.allNotNull(Tools.dataType(SQLDataType.INTEGER, value, false), value, bit));
        this.value = Tools.nullSafeNotNull(value, SQLDataType.INTEGER);
        this.bit = Tools.nullSafeNotNull(bit, SQLDataType.INTEGER);
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        switch (ctx.family()) {
            case EXASOL: {
                return false;
            }
            case TERADATA: {
                return true;
            }
            case SQLSERVER: {
                return false;
            }
            case H2: {
                return false;
            }
            case ASE: 
            case AURORA_MYSQL: 
            case AURORA_POSTGRES: 
            case BIGQUERY: 
            case COCKROACHDB: 
            case DB2: 
            case HANA: 
            case INFORMIX: 
            case MEMSQL: 
            case ORACLE: 
            case REDSHIFT: 
            case SNOWFLAKE: 
            case SQLDATAWAREHOUSE: 
            case SYBASE: 
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
            case EXASOL: {
                ctx.visit(DSL.case_(DSL.function(Names.N_BIT_CHECK, SQLDataType.BOOLEAN, this.value, this.bit)).when((Field<Boolean>)DSL.trueCondition(), DSL.inline(1)).when((Field<Boolean>)DSL.falseCondition(), DSL.inline(0)));
                break;
            }
            case TERADATA: {
                ctx.visit(DSL.function(Names.N_GETBIT, this.getDataType(), this.value, this.bit));
                break;
            }
            case SQLSERVER: {
                if (ctx.dialect().precedesStrictly(SQLDialect.SQLSERVER2022)) {
                    ctx.visit(this.value.bitAnd(DSL.one().shl(this.bit)).shr(this.bit));
                    break;
                }
                ctx.visit(DSL.function(Names.N_GET_BIT, this.getDataType(), this.value, this.bit));
                break;
            }
            case H2: {
                ctx.visit(DSL.case_(DSL.function(Names.N_BITGET, SQLDataType.BOOLEAN, this.value, this.bit)).when((Field<Boolean>)DSL.trueCondition(), DSL.inline(1)).when((Field<Boolean>)DSL.falseCondition(), DSL.inline(0)));
                break;
            }
            case ASE: 
            case AURORA_MYSQL: 
            case AURORA_POSTGRES: 
            case BIGQUERY: 
            case COCKROACHDB: 
            case DB2: 
            case HANA: 
            case INFORMIX: 
            case MEMSQL: 
            case ORACLE: 
            case REDSHIFT: 
            case SNOWFLAKE: 
            case SQLDATAWAREHOUSE: 
            case SYBASE: 
            case VERTICA: 
            case CUBRID: 
            case FIREBIRD: 
            case HSQLDB: 
            case MARIADB: 
            case MYSQL: 
            case POSTGRES: 
            case SQLITE: 
            case YUGABYTEDB: {
                ctx.visit(this.value.bitAnd(DSL.one().shl(this.bit)).shr(this.bit));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_BIT_GET, this.getDataType(), this.value, this.bit));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("bitGet(");
        String s2 = "";
        ctx.sql(s2).visit(this.value);
        s2 = ", ";
        ctx.sql(s2).visit(Val.getJavaValue(this.bit));
        s2 = ", ";
        ctx.sql(')');
    }

    @Override
    public final Field<T> $arg1() {
        return this.value;
    }

    @Override
    public final Field<? extends Number> $arg2() {
        return this.bit;
    }

    @Override
    public final QOM.BitGet<T> $arg1(Field<T> newValue) {
        return this.$constructor().apply(newValue, (Field<Number>)this.$arg2());
    }

    @Override
    public final QOM.BitGet<T> $arg2(Field<? extends Number> newValue) {
        return this.$constructor().apply((Field<T>)this.$arg1(), (Field<Number>)newValue);
    }

    @Override
    public final Function2<? super Field<T>, ? super Field<? extends Number>, ? extends QOM.BitGet<T>> $constructor() {
        return (a1, a2) -> new BitGet(a1, (Field<? extends Number>)((Field<Number>)a2));
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.BitGet) {
            QOM.BitGet o = (QOM.BitGet)that;
            return StringUtils.equals(this.$value(), o.$value()) && StringUtils.equals(this.$bit(), o.$bit());
        }
        return super.equals(that);
    }
}

