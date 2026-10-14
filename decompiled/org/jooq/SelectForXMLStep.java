/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.Pro;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.SelectForXMLPathDirectivesStep;
import org.jooq.SelectForXMLRawDirectivesStep;
import org.jooq.Support;

@Pro
public interface SelectForXMLStep<R extends Record> {
    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectForXMLRawDirectivesStep<R> raw();

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectForXMLRawDirectivesStep<R> raw(String var1);

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectForXMLRawDirectivesStep<R> auto();

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectForXMLPathDirectivesStep<R> path();

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectForXMLPathDirectivesStep<R> path(String var1);

    @Support(value={SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectForXMLPathDirectivesStep<R> explicit();
}

