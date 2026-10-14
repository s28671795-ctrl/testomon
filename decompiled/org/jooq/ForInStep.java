/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.Field;
import org.jooq.ForByStep;
import org.jooq.Pro;
import org.jooq.SQLDialect;
import org.jooq.Support;

@Pro
public interface ForInStep<T> {
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB_10_3, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public ForByStep<T> in(T var1, T var2);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB_10_3, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public ForByStep<T> in(Field<T> var1, Field<T> var2);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB_10_3, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public ForByStep<T> inReverse(T var1, T var2);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB_10_3, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public ForByStep<T> inReverse(Field<T> var1, Field<T> var2);
}

