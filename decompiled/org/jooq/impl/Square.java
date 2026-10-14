/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

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

final class Square<T extends Number>
extends AbstractField<T>
implements QOM.Square<T> {
    final Field<T> value;

    Square(Field<T> value) {
        super(Names.N_SQUARE, Tools.allNotNull(Tools.dataType(SQLDataType.INTEGER, value, false), value));
        this.value = Tools.nullSafeNotNull(value, SQLDataType.INTEGER);
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        switch (ctx.family()) {
            case SQLITE: {
                return false;
            }
            case ACCESS: 
            case AURORA_MYSQL: 
            case AURORA_POSTGRES: 
            case BIGQUERY: 
            case COCKROACHDB: 
            case DB2: 
            case EXASOL: 
            case HANA: 
            case INFORMIX: 
            case INGRES: 
            case MEMSQL: 
            case ORACLE: 
            case REDSHIFT: 
            case SNOWFLAKE: 
            case SYBASE: 
            case TERADATA: 
            case VERTICA: 
            case CUBRID: 
            case DERBY: 
            case FIREBIRD: 
            case H2: 
            case HSQLDB: 
            case IGNITE: 
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
            case SQLITE: {
                ctx.visit(Internal.imul(this.value, this.value));
                break;
            }
            case ACCESS: 
            case AURORA_MYSQL: 
            case AURORA_POSTGRES: 
            case BIGQUERY: 
            case COCKROACHDB: 
            case DB2: 
            case EXASOL: 
            case HANA: 
            case INFORMIX: 
            case INGRES: 
            case MEMSQL: 
            case ORACLE: 
            case REDSHIFT: 
            case SNOWFLAKE: 
            case SYBASE: 
            case TERADATA: 
            case VERTICA: 
            case CUBRID: 
            case DERBY: 
            case FIREBIRD: 
            case H2: 
            case HSQLDB: 
            case IGNITE: 
            case MARIADB: 
            case MYSQL: 
            case POSTGRES: 
            case YUGABYTEDB: {
                if (Tools.isSimple(ctx, this.value)) {
                    ctx.visit(Internal.imul(this.value, this.value));
                    break;
                }
                ctx.visit(DSL.power(this.value, DSL.inline(2)));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_SQUARE, this.getDataType(), this.value));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("square(");
        ctx.visit(Val.getJavaValue(this.value));
        ctx.sql(')');
    }

    @Override
    public final Field<T> $arg1() {
        return this.value;
    }

    @Override
    public final QOM.Square<T> $arg1(Field<T> newValue) {
        return this.$constructor().apply(newValue);
    }

    @Override
    public final Function1<? super Field<T>, ? extends QOM.Square<T>> $constructor() {
        return a1 -> new Square(a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Square) {
            QOM.Square o = (QOM.Square)that;
            return StringUtils.equals(this.$value(), o.$value());
        }
        return super.equals(that);
    }
}

