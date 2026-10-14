/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.CreateProcedureCharacteristicsStep;
import org.jooq.Parameter;
import org.jooq.Pro;
import org.jooq.SQLDialect;
import org.jooq.Support;

@Pro
public interface CreateProcedureParametersStep
extends CreateProcedureCharacteristicsStep {
    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_11, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateProcedureCharacteristicsStep parameters(Parameter<?> ... var1);

    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_11, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateProcedureCharacteristicsStep parameters(Collection<? extends Parameter<?>> var1);
}

