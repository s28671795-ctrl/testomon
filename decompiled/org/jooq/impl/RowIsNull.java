/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.Function1;
import org.jooq.Row;
import org.jooq.SQLDialect;
import org.jooq.impl.AbstractCondition;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;

final class RowIsNull
extends AbstractCondition
implements QOM.RowIsNull {
    static final Set<SQLDialect> EMULATE_NULL_ROW = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA);
    private final Row row;

    RowIsNull(Row row) {
        this.row = row;
    }

    @Override
    final boolean isNullable() {
        return false;
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (EMULATE_NULL_ROW.contains((Object)ctx.dialect())) {
            ctx.visit(Tools.allNull(this.row.fields()));
        } else {
            this.acceptStandard(ctx);
        }
    }

    private final void acceptStandard(Context<?> ctx) {
        ctx.visit(this.row);
        switch (ctx.family()) {
            case JAVA: {
                ctx.sql(".isNull()");
                break;
            }
            default: {
                ctx.sql(' ').visit(Keywords.K_IS_NULL);
            }
        }
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return null;
    }

    @Override
    public final Row $arg1() {
        return this.row;
    }

    @Override
    public final Function1<? super Row, ? extends QOM.RowIsNull> $constructor() {
        return r -> new RowIsNull((Row)r);
    }
}

