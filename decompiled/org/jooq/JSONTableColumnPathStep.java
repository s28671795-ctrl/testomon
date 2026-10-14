/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.JSONTableColumnForOrdinalityStep;
import org.jooq.JSONTableColumnsStep;
import org.jooq.SQLDialect;
import org.jooq.Support;

public interface JSONTableColumnPathStep
extends JSONTableColumnForOrdinalityStep,
JSONTableColumnsStep {
    @Support(value={SQLDialect.DB2_11, SQLDialect.HANA, SQLDialect.MARIADB_10_6, SQLDialect.MYSQL_8_0_19, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_12, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    public JSONTableColumnsStep path(String var1);
}

