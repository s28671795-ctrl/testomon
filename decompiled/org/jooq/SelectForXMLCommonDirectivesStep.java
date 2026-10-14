/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.Pro;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.SelectOptionStep;
import org.jooq.Support;

@Pro
public interface SelectForXMLCommonDirectivesStep<R extends Record>
extends SelectOptionStep<R> {
    @Support(value={SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectForXMLCommonDirectivesStep<R> binaryBase64();

    @Support(value={SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectForXMLCommonDirectivesStep<R> type();

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectForXMLCommonDirectivesStep<R> root();

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectForXMLCommonDirectivesStep<R> root(String var1);
}

