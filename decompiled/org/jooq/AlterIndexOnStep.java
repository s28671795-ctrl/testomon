/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.AlterIndexStep;
import org.jooq.CheckReturnValue;
import org.jooq.Name;
import org.jooq.SQLDialect;
import org.jooq.Support;
import org.jooq.Table;

public interface AlterIndexOnStep
extends AlterIndexStep {
    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER2016})
    @NotNull
    @CheckReturnValue
    public AlterIndexStep on(String var1);

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER2016})
    @NotNull
    @CheckReturnValue
    public AlterIndexStep on(Name var1);

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER2016})
    @NotNull
    @CheckReturnValue
    public AlterIndexStep on(Table<?> var1);
}

