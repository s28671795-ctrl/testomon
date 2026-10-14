/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.MergeMatchedStep;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.Support;

public interface MergeMatchedDeleteStep<R extends Record>
extends MergeMatchedStep<R> {
    @Support(value={SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.ORACLE, SQLDialect.POSTGRES_15, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA})
    @NotNull
    @CheckReturnValue
    public MergeMatchedStep<R> deleteWhere(Condition var1);

    @Support(value={SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.ORACLE, SQLDialect.POSTGRES_15, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA})
    @NotNull
    @CheckReturnValue
    public MergeMatchedStep<R> deleteWhere(Field<Boolean> var1);
}

