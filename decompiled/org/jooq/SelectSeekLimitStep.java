/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.SelectForUpdateStep;
import org.jooq.Support;

public interface SelectSeekLimitStep<R extends Record>
extends SelectForUpdateStep<R> {
    @Support
    @NotNull
    @CheckReturnValue
    public SelectForUpdateStep<R> limit(Number var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public SelectForUpdateStep<R> limit(Field<? extends Number> var1);
}

