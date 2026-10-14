/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function3;
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

final class BitSet<T extends Number>
extends AbstractField<T>
implements QOM.BitSet<T> {
    final Field<T> value;
    final Field<? extends Number> bit;
    final Field<T> newValue;

    BitSet(Field<T> value, Field<? extends Number> bit) {
        super(Names.N_BIT_SET, Tools.allNotNull(Tools.dataType(SQLDataType.INTEGER, value, false), value, bit));
        this.value = Tools.nullSafeNotNull(value, SQLDataType.INTEGER);
        this.bit = Tools.nullSafeNotNull(bit, SQLDataType.INTEGER);
        this.newValue = null;
    }

    BitSet(Field<T> value, Field<? extends Number> bit, Field<T> newValue) {
        super(Names.N_BIT_SET, Tools.allNotNull(Tools.dataType(SQLDataType.INTEGER, value, false), value, bit, newValue));
        this.value = Tools.nullSafeNotNull(value, SQLDataType.INTEGER);
        this.bit = Tools.nullSafeNotNull(bit, SQLDataType.INTEGER);
        this.newValue = Tools.nullSafeNotNull(newValue, SQLDataType.INTEGER);
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        switch (ctx.family()) {
            case EXASOL: {
                return false;
            }
            case SQLSERVER: {
                return false;
            }
            case TERADATA: {
                return true;
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
            case H2: 
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
                if (this.newValue == null) {
                    ctx.visit(DSL.function(Names.N_BIT_SET, this.getDataType(), this.value, this.bit));
                    break;
                }
                ctx.visit(DSL.case_(this.newValue).when((Field<T>)DSL.zero(), this.value.bitAnd(DSL.one().shl(this.bit).bitNot())).when((Field<Integer>)DSL.one(), this.value.bitOr(DSL.one().shl(this.bit))));
                break;
            }
            case SQLSERVER: {
                if (ctx.dialect().precedesStrictly(SQLDialect.SQLSERVER2022)) {
                    if (this.newValue == null) {
                        ctx.visit(this.value.bitOr(DSL.one().shl(this.bit)));
                        break;
                    }
                    ctx.visit(DSL.case_(this.newValue).when((Field<T>)DSL.zero(), this.value.bitAnd(DSL.one().shl(this.bit).bitNot())).when((Field<Integer>)DSL.one(), this.value.bitOr(DSL.one().shl(this.bit))));
                    break;
                }
                if (this.newValue == null) {
                    ctx.visit(DSL.function(Names.N_SET_BIT, this.getDataType(), this.value, this.bit));
                    break;
                }
                ctx.visit(DSL.function(Names.N_SET_BIT, this.getDataType(), this.value, this.bit, this.newValue));
                break;
            }
            case TERADATA: {
                if (this.newValue != null) {
                    ctx.visit(DSL.function(Names.N_SETBIT, this.getDataType(), this.value, this.bit, this.newValue));
                    break;
                }
                ctx.visit(DSL.function(Names.N_SETBIT, this.getDataType(), this.value, this.bit));
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
            case H2: 
            case HSQLDB: 
            case MARIADB: 
            case MYSQL: 
            case POSTGRES: 
            case SQLITE: 
            case YUGABYTEDB: {
                if (this.newValue == null) {
                    ctx.visit(this.value.bitOr(DSL.one().shl(this.bit)));
                    break;
                }
                ctx.visit(DSL.case_(this.newValue).when((Field<T>)DSL.zero(), this.value.bitAnd(DSL.one().shl(this.bit).bitNot())).when((Field<Integer>)DSL.one(), this.value.bitOr(DSL.one().shl(this.bit))));
                break;
            }
            default: {
                if (this.newValue != null) {
                    ctx.visit(DSL.function(Names.N_BIT_SET, this.getDataType(), this.value, this.bit, this.newValue));
                    break;
                }
                ctx.visit(DSL.function(Names.N_BIT_SET, this.getDataType(), this.value, this.bit));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("bitSet(");
        String s2 = "";
        ctx.sql(s2).visit(this.value);
        s2 = ", ";
        ctx.sql(s2).visit(Val.getJavaValue(this.bit));
        s2 = ", ";
        if (this.newValue != null) {
            ctx.sql(s2).visit(Val.getJavaValue(this.newValue));
            s2 = ", ";
        }
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
    public final Field<T> $arg3() {
        return this.newValue;
    }

    @Override
    public final QOM.BitSet<T> $arg1(Field<T> newValue) {
        return this.$constructor().apply(newValue, (Field<Number>)this.$arg2(), (Field<T>)this.$arg3());
    }

    @Override
    public final QOM.BitSet<T> $arg2(Field<? extends Number> newValue) {
        return this.$constructor().apply((Field<T>)this.$arg1(), (Field<Number>)newValue, (Field<T>)this.$arg3());
    }

    @Override
    public final QOM.BitSet<T> $arg3(Field<T> newValue) {
        return this.$constructor().apply((Field<T>)this.$arg1(), (Field<Number>)this.$arg2(), newValue);
    }

    @Override
    public final Function3<? super Field<T>, ? super Field<? extends Number>, ? super Field<T>, ? extends QOM.BitSet<T>> $constructor() {
        return (a1, a2, a3) -> new BitSet(a1, (Field<? extends Number>)((Field<Number>)a2), a3);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.BitSet) {
            QOM.BitSet o = (QOM.BitSet)that;
            return StringUtils.equals(this.$value(), o.$value()) && StringUtils.equals(this.$bit(), o.$bit()) && StringUtils.equals(this.$newValue(), o.$newValue());
        }
        return super.equals(that);
    }
}

