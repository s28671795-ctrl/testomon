/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.math.BigDecimal;
import java.util.Set;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.Pro;
import org.jooq.SQLDialect;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultDataType;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

final class Digits
extends AbstractField<String>
implements QOM.Digits {
    final Field<? extends Number> value;
    private static final Set<SQLDialect> NO_SUPPORT_DIGITS = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.BIGQUERY, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB);

    Digits(Field<? extends Number> value) {
        super(Names.N_DIGITS, Tools.allNotNull(SQLDataType.VARCHAR, value));
        this.value = Tools.nullSafeNotNull(value, SQLDataType.INTEGER);
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (NO_SUPPORT_DIGITS.contains((Object)ctx.dialect())) {
            DataType t2 = this.value.getDataType();
            if (t2.getType() == Byte.class) {
                ctx.visit(DSL.lpad(DSL.abs(this.value).cast(SQLDataType.VARCHAR(DefaultDataType.BYTE_PRECISION)), DSL.inline(DefaultDataType.BYTE_PRECISION), DSL.inline("0")));
            } else if (t2.getType() == Short.class) {
                ctx.visit(DSL.lpad(DSL.abs(this.value).cast(SQLDataType.VARCHAR(DefaultDataType.SHORT_PRECISION)), DSL.inline(DefaultDataType.SHORT_PRECISION), DSL.inline("0")));
            } else if (t2.getType() == Integer.class) {
                ctx.visit(DSL.lpad(DSL.abs(this.value).cast(SQLDataType.VARCHAR(DefaultDataType.INTEGER_PRECISION)), DSL.inline(DefaultDataType.INTEGER_PRECISION), DSL.inline("0")));
            } else if (t2.getType() == Long.class) {
                ctx.visit(DSL.lpad(DSL.abs(this.value).cast(SQLDataType.VARCHAR(DefaultDataType.LONG_PRECISION)), DSL.inline(DefaultDataType.LONG_PRECISION), DSL.inline("0")));
            } else if (t2.scaleDefined()) {
                ctx.visit(DSL.lpad(DSL.abs(this.value.mul(DSL.inline(BigDecimal.TEN.pow(t2.scale())))).cast(t2.scale(0)).cast(SQLDataType.VARCHAR(t2.precision())), DSL.inline(t2.precision()), DSL.inline("0")));
            } else {
                ctx.visit(DSL.lpad(DSL.abs(this.value).cast(SQLDataType.VARCHAR(t2.precision())), DSL.inline(t2.precision()), DSL.inline("0")));
            }
        } else if (ctx.family() == SQLDialect.JAVA) {
            this.acceptJava(ctx);
        } else {
            ctx.visit(Names.N_DIGITS).sql('(').visit(this.value).sql(')');
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("digits(");
        ctx.visit(Val.getJavaValue(this.value));
        ctx.sql(')');
    }

    @Override
    public final Field<? extends Number> $arg1() {
        return this.value;
    }

    @Override
    public final QOM.Digits $arg1(Field<? extends Number> newValue) {
        return this.$constructor().apply((Field<? extends Number>)newValue);
    }

    @Override
    public final Function1<? super Field<? extends Number>, ? extends QOM.Digits> $constructor() {
        return a1 -> new Digits((Field<? extends Number>)a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Digits) {
            QOM.Digits o = (QOM.Digits)that;
            return StringUtils.equals(this.$value(), o.$value());
        }
        return super.equals(that);
    }
}

