/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.Constraint;
import org.jooq.Field;
import org.jooq.InsertOnConflictDoUpdateStep;
import org.jooq.InsertOnConflictWhereIndexPredicateStep;
import org.jooq.InsertOnDuplicateSetStep;
import org.jooq.InsertReturningStep;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.Support;
import org.jooq.UniqueKey;

public interface InsertOnDuplicateStep<R extends Record>
extends InsertReturningStep<R> {
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public InsertOnConflictDoUpdateStep<R> onConflictOnConstraint(Constraint var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public InsertOnConflictDoUpdateStep<R> onConflictOnConstraint(Name var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertOnConflictDoUpdateStep<R> onConflictOnConstraint(UniqueKey<R> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertOnConflictWhereIndexPredicateStep<R> onConflict(Field<?> ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertOnConflictWhereIndexPredicateStep<R> onConflict(Collection<? extends Field<?>> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertReturningStep<R> onConflictDoNothing();

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public InsertOnDuplicateSetStep<R> onDuplicateKeyUpdate();

    @Support
    @NotNull
    @CheckReturnValue
    public InsertReturningStep<R> onDuplicateKeyIgnore();
}

