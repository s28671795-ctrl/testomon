/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.SQLDialect;
import org.jooq.Support;
import org.jooq.XMLTableColumnForOrdinalityStep;
import org.jooq.XMLTableColumnPathStep;

public interface XMLTableColumnsFirstStep {
    @Support(value={SQLDialect.DB2, SQLDialect.HANA, SQLDialect.ORACLE, SQLDialect.POSTGRES_10, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @NotNull
    public XMLTableColumnForOrdinalityStep column(String var1);

    @Support(value={SQLDialect.DB2, SQLDialect.HANA, SQLDialect.ORACLE, SQLDialect.POSTGRES_10, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @NotNull
    public XMLTableColumnForOrdinalityStep column(Name var1);

    @Support(value={SQLDialect.DB2, SQLDialect.HANA, SQLDialect.ORACLE, SQLDialect.POSTGRES_10, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @NotNull
    public XMLTableColumnPathStep column(Field<?> var1);

    @Support(value={SQLDialect.DB2, SQLDialect.HANA, SQLDialect.ORACLE, SQLDialect.POSTGRES_10, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @NotNull
    public XMLTableColumnPathStep column(String var1, DataType<?> var2);

    @Support(value={SQLDialect.DB2, SQLDialect.HANA, SQLDialect.ORACLE, SQLDialect.POSTGRES_10, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @NotNull
    public XMLTableColumnPathStep column(Name var1, DataType<?> var2);

    @Support(value={SQLDialect.DB2, SQLDialect.HANA, SQLDialect.ORACLE, SQLDialect.POSTGRES_10, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @NotNull
    public XMLTableColumnPathStep column(Field<?> var1, DataType<?> var2);
}

