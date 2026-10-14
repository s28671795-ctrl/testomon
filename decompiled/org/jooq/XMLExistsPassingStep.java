/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.Support;
import org.jooq.XML;

public interface XMLExistsPassingStep {
    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @NotNull
    public Condition passing(XML var1);

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @NotNull
    public Condition passing(Field<XML> var1);

    @Support(value={SQLDialect.DB2, SQLDialect.POSTGRES})
    @NotNull
    public Condition passingByRef(XML var1);

    @Support(value={SQLDialect.DB2, SQLDialect.POSTGRES})
    @NotNull
    public Condition passingByRef(Field<XML> var1);

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.TERADATA})
    @NotNull
    public Condition passingByValue(XML var1);

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.TERADATA})
    @NotNull
    public Condition passingByValue(Field<XML> var1);
}

