/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.JSONTableColumnForOrdinalityStep;
import org.jooq.JSONTableColumnPathStep;
import org.jooq.Name;
import org.jooq.SQLDialect;
import org.jooq.Support;

public interface JSONTableColumnsFirstStep {
    @Support(value={SQLDialect.DB2_11, SQLDialect.HANA, SQLDialect.MARIADB_10_6, SQLDialect.MYSQL_8_0_19, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_12, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    public JSONTableColumnForOrdinalityStep column(String var1);

    @Support(value={SQLDialect.DB2_11, SQLDialect.HANA, SQLDialect.MARIADB_10_6, SQLDialect.MYSQL_8_0_19, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_12, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    public JSONTableColumnForOrdinalityStep column(Name var1);

    @Support(value={SQLDialect.DB2_11, SQLDialect.HANA, SQLDialect.MARIADB_10_6, SQLDialect.MYSQL_8_0_19, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_12, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    public JSONTableColumnPathStep column(Field<?> var1);

    @Support(value={SQLDialect.DB2_11, SQLDialect.HANA, SQLDialect.MARIADB_10_6, SQLDialect.MYSQL_8_0_19, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_12, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    public JSONTableColumnPathStep column(String var1, DataType<?> var2);

    @Support(value={SQLDialect.DB2_11, SQLDialect.HANA, SQLDialect.MARIADB_10_6, SQLDialect.MYSQL_8_0_19, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_12, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    public JSONTableColumnPathStep column(Name var1, DataType<?> var2);

    @Support(value={SQLDialect.DB2_11, SQLDialect.HANA, SQLDialect.MARIADB_10_6, SQLDialect.MYSQL_8_0_19, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_12, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    public JSONTableColumnPathStep column(Field<?> var1, DataType<?> var2);
}

