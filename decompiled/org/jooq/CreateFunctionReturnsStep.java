/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.CreateFunctionCharacteristicsStep;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Pro;
import org.jooq.SQLDialect;
import org.jooq.Support;

@Pro
public interface CreateFunctionReturnsStep {
    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateFunctionCharacteristicsStep returns(DataType<?> var1);

    @Support(value={SQLDialect.HSQLDB})
    @NotNull
    @CheckReturnValue
    public CreateFunctionCharacteristicsStep returnsTable(Field<?> ... var1);

    @Support(value={SQLDialect.HSQLDB})
    @NotNull
    @CheckReturnValue
    public CreateFunctionCharacteristicsStep returnsTable(Collection<? extends Field<?>> var1);
}

