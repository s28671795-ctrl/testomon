/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.SQLDialect;
import org.jooq.Support;
import org.jooq.XMLTableColumnsStep;

public interface XMLTableColumnForOrdinalityStep {
    @Support(value={SQLDialect.DB2, SQLDialect.HANA, SQLDialect.ORACLE, SQLDialect.POSTGRES_10, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @NotNull
    public XMLTableColumnsStep forOrdinality();
}

