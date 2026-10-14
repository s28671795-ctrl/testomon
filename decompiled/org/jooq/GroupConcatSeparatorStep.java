/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.AggregateFunction;
import org.jooq.SQLDialect;
import org.jooq.Support;

public interface GroupConcatSeparatorStep
extends AggregateFunction<String> {
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE11G, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER2017, SQLDialect.SYBASE, SQLDialect.TERADATA})
    @NotNull
    public AggregateFunction<String> separator(String var1);
}

