/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.CreateProcedureAsStep;
import org.jooq.Pro;
import org.jooq.SQLDialect;
import org.jooq.Support;

@Pro
public interface CreateProcedureCharacteristicsStep
extends CreateProcedureAsStep {
    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_11, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateProcedureCharacteristicsStep noSQL();

    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_11, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateProcedureCharacteristicsStep containsSQL();

    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_11, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateProcedureCharacteristicsStep readsSQLData();

    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_11, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateProcedureCharacteristicsStep modifiesSQLData();
}

