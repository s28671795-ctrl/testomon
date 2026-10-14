/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.SelectForStep;
import org.jooq.Support;

public interface SelectForUpdateWaitStep<R extends Record>
extends SelectForStep<R> {
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES})
    @NotNull
    @CheckReturnValue
    public SelectForStep<R> wait(int var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.MARIADB_10_3, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public SelectForStep<R> noWait();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.MARIADB_10_6, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public SelectForStep<R> skipLocked();
}

