/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
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

final class ToChar
extends AbstractField<String>
implements QOM.ToChar {
    final Field<?> value;
    final Field<String> formatMask;
    private static final Set<SQLDialect> NO_SUPPORT_NATIVE_WITHOUT_MASK = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB);
    private static final Set<SQLDialect> NO_SUPPORT_NATIVE_WITH_MASK = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA);

    ToChar(Field<?> value) {
        super(Names.N_TO_CHAR, Tools.allNotNull(SQLDataType.VARCHAR, value));
        this.value = Tools.nullSafeNotNull(value, SQLDataType.OTHER);
        this.formatMask = null;
    }

    ToChar(Field<?> value, Field<String> formatMask) {
        super(Names.N_TO_CHAR, Tools.allNotNull(SQLDataType.VARCHAR, value, formatMask));
        this.value = Tools.nullSafeNotNull(value, SQLDataType.OTHER);
        this.formatMask = Tools.nullSafeNotNull(formatMask, SQLDataType.VARCHAR);
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (this.formatMask == null && NO_SUPPORT_NATIVE_WITHOUT_MASK.contains((Object)ctx.dialect())) {
            this.acceptCast(ctx);
        } else if (this.formatMask != null && NO_SUPPORT_NATIVE_WITH_MASK.contains((Object)ctx.dialect())) {
            this.acceptCast(ctx);
        } else if (ctx.family() == SQLDialect.JAVA) {
            this.acceptJava(ctx);
        } else {
            this.acceptNative(ctx);
        }
    }

    private final void acceptNative(Context<?> ctx) {
        ctx.visit(Names.N_TO_CHAR).sql('(').visit(this.value);
        if (this.formatMask != null) {
            ctx.sql(", ").visit(this.formatMask);
        }
        ctx.sql(')');
    }

    private final void acceptCast(Context<?> ctx) {
        ctx.visit(DSL.cast(this.value, SQLDataType.VARCHAR));
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("toChar(");
        String s2 = "";
        ctx.sql(s2).visit(Val.getJavaValue(this.value));
        s2 = ", ";
        if (this.formatMask != null) {
            ctx.sql(s2).visit(Val.getJavaValue(this.formatMask));
            s2 = ", ";
        }
        ctx.sql(')');
    }

    @Override
    public final Field<?> $arg1() {
        return this.value;
    }

    @Override
    public final Field<String> $arg2() {
        return this.formatMask;
    }

    @Override
    public final QOM.ToChar $arg1(Field<?> newValue) {
        return this.$constructor().apply(newValue, (Field<String>)this.$arg2());
    }

    @Override
    public final QOM.ToChar $arg2(Field<String> newValue) {
        return this.$constructor().apply((Field<?>)this.$arg1(), newValue);
    }

    @Override
    public final Function2<? super Field<?>, ? super Field<String>, ? extends QOM.ToChar> $constructor() {
        return (a1, a2) -> new ToChar((Field<?>)a1, (Field<String>)a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.ToChar) {
            QOM.ToChar o = (QOM.ToChar)that;
            return StringUtils.equals(this.$value(), o.$value()) && StringUtils.equals(this.$formatMask(), o.$formatMask());
        }
        return super.equals(that);
    }
}

