/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.LoaderSourceStep;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.Support;

public interface LoaderOptionsStep<R extends Record>
extends LoaderSourceStep<R> {
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA})
    @NotNull
    @CheckReturnValue
    public LoaderOptionsStep<R> onDuplicateKeyUpdate();

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderOptionsStep<R> onDuplicateKeyIgnore();

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderOptionsStep<R> onDuplicateKeyError();

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderOptionsStep<R> onErrorIgnore();

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderOptionsStep<R> onErrorAbort();

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderOptionsStep<R> commitEach();

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderOptionsStep<R> commitAfter(int var1);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderOptionsStep<R> commitAll();

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderOptionsStep<R> commitNone();

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderOptionsStep<R> batchAll();

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderOptionsStep<R> batchNone();

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderOptionsStep<R> batchAfter(int var1);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderOptionsStep<R> bulkAll();

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderOptionsStep<R> bulkNone();

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderOptionsStep<R> bulkAfter(int var1);
}

