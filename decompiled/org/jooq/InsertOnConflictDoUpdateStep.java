/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.InsertOnDuplicateSetStep;
import org.jooq.InsertReturningStep;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.Support;

public interface InsertOnConflictDoUpdateStep<R extends Record> {
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public InsertOnDuplicateSetStep<R> doUpdate();

    @Support
    @NotNull
    @CheckReturnValue
    public InsertReturningStep<R> doNothing();
}

