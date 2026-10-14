/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function2;
import org.jooq.Pro;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

final class Nvl<T>
extends AbstractField<T>
implements QOM.Nvl<T> {
    final Field<T> value;
    final Field<T> defaultValue;

    Nvl(Field<T> value, Field<T> defaultValue) {
        super(Names.N_NVL, Tools.anyNotNull(Tools.dataType(value), value, defaultValue));
        this.value = Tools.nullSafeNotNull(value, SQLDataType.OTHER);
        this.defaultValue = Tools.nullSafeNotNull(defaultValue, SQLDataType.OTHER);
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        switch (ctx.family()) {
            case ACCESS: {
                return false;
            }
            case INFORMIX: {
                return false;
            }
            case DERBY: {
                return false;
            }
            case ASE: 
            case AURORA_POSTGRES: 
            case COCKROACHDB: 
            case EXASOL: 
            case HANA: 
            case REDSHIFT: 
            case SNOWFLAKE: 
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: 
            case SYBASE: 
            case TERADATA: 
            case VERTICA: 
            case CUBRID: 
            case FIREBIRD: 
            case IGNITE: 
            case POSTGRES: 
            case YUGABYTEDB: {
                return true;
            }
            case AURORA_MYSQL: 
            case BIGQUERY: 
            case MEMSQL: 
            case MARIADB: 
            case MYSQL: 
            case SQLITE: {
                return true;
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
            case ACCESS: {
                ctx.visit(Names.N_IIF).sql('(').visit(this.value).sql(' ').visit(Keywords.K_IS_NULL).sql(", ").visit(this.defaultValue).sql(", ").visit(this.value).sql(')');
                break;
            }
            case INFORMIX: {
                ctx.visit(DSL.function(Names.N_NVL, this.getDataType(), this.value, this.defaultValue instanceof Val && !((Val)this.defaultValue).isInline(ctx) ? DSL.field(DSL.select(this.defaultValue)) : this.defaultValue));
                break;
            }
            case DERBY: {
                ctx.visit(DSL.coalesce(this.value, this.defaultValue));
                break;
            }
            case ASE: 
            case AURORA_POSTGRES: 
            case COCKROACHDB: 
            case EXASOL: 
            case HANA: 
            case REDSHIFT: 
            case SNOWFLAKE: 
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: 
            case SYBASE: 
            case TERADATA: 
            case VERTICA: 
            case CUBRID: 
            case FIREBIRD: 
            case IGNITE: 
            case POSTGRES: 
            case YUGABYTEDB: {
                ctx.visit(DSL.function(Names.N_COALESCE, this.getDataType(), this.value, this.defaultValue));
                break;
            }
            case AURORA_MYSQL: 
            case BIGQUERY: 
            case MEMSQL: 
            case MARIADB: 
            case MYSQL: 
            case SQLITE: {
                ctx.visit(DSL.function(Names.N_IFNULL, this.getDataType(), this.value, this.defaultValue));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_NVL, this.getDataType(), this.value, this.defaultValue));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("nvl(");
        String s2 = "";
        ctx.sql(s2).visit(Val.getJavaValue(this.value));
        s2 = ", ";
        ctx.sql(s2).visit(Val.getJavaValue(this.defaultValue));
        s2 = ", ";
        ctx.sql(')');
    }

    @Override
    public final Field<T> $arg1() {
        return this.value;
    }

    @Override
    public final Field<T> $arg2() {
        return this.defaultValue;
    }

    @Override
    public final QOM.Nvl<T> $arg1(Field<T> newValue) {
        return this.$constructor().apply(newValue, (Field<T>)this.$arg2());
    }

    @Override
    public final QOM.Nvl<T> $arg2(Field<T> newValue) {
        return this.$constructor().apply((Field<T>)this.$arg1(), newValue);
    }

    @Override
    public final Function2<? super Field<T>, ? super Field<T>, ? extends QOM.Nvl<T>> $constructor() {
        return (a1, a2) -> new Nvl(a1, a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Nvl) {
            QOM.Nvl o = (QOM.Nvl)that;
            return StringUtils.equals(this.$value(), o.$value()) && StringUtils.equals(this.$defaultValue(), o.$defaultValue());
        }
        return super.equals(that);
    }
}

