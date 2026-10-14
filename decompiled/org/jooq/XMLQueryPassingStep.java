/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.Support;
import org.jooq.XML;

public interface XMLQueryPassingStep {
    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @NotNull
    public Field<XML> passing(XML var1);

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @NotNull
    public Field<XML> passing(Field<XML> var1);

    @Support(value={SQLDialect.DB2, SQLDialect.POSTGRES})
    @NotNull
    public Field<XML> passingByRef(XML var1);

    @Support(value={SQLDialect.DB2, SQLDialect.POSTGRES})
    @NotNull
    public Field<XML> passingByRef(Field<XML> var1);

    @Support(value={SQLDialect.ORACLE, SQLDialect.TERADATA})
    @NotNull
    public Field<XML> passingByValue(XML var1);

    @Support(value={SQLDialect.ORACLE, SQLDialect.TERADATA})
    @NotNull
    public Field<XML> passingByValue(Field<XML> var1);
}

