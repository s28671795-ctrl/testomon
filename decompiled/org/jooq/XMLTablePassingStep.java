/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.Support;
import org.jooq.XML;
import org.jooq.XMLTableColumnsFirstStep;

public interface XMLTablePassingStep
extends XMLTableColumnsFirstStep {
    @Support(value={SQLDialect.DB2, SQLDialect.HANA, SQLDialect.ORACLE, SQLDialect.POSTGRES_10, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @NotNull
    public XMLTableColumnsFirstStep passing(XML var1);

    @Support(value={SQLDialect.DB2, SQLDialect.HANA, SQLDialect.ORACLE, SQLDialect.POSTGRES_10, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @NotNull
    public XMLTableColumnsFirstStep passing(Field<XML> var1);

    @Support(value={SQLDialect.DB2, SQLDialect.POSTGRES_10})
    @NotNull
    public XMLTableColumnsFirstStep passingByRef(XML var1);

    @Support(value={SQLDialect.DB2, SQLDialect.POSTGRES_10})
    @NotNull
    public XMLTableColumnsFirstStep passingByRef(Field<XML> var1);

    @Support(value={SQLDialect.ORACLE, SQLDialect.POSTGRES_10, SQLDialect.TERADATA})
    @NotNull
    public XMLTableColumnsFirstStep passingByValue(XML var1);

    @Support(value={SQLDialect.ORACLE, SQLDialect.POSTGRES_10, SQLDialect.TERADATA})
    @NotNull
    public XMLTableColumnsFirstStep passingByValue(Field<XML> var1);
}

