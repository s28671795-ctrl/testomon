/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.JSON;
import org.jooq.JSONB;
import org.jooq.Pro;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.SQLDialect;
import org.jooq.SelectForJSONStep;
import org.jooq.SelectForXMLStep;
import org.jooq.SelectOptionStep;
import org.jooq.Support;
import org.jooq.XML;

public interface SelectForStep<R extends Record>
extends SelectOptionStep<R> {
    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectForXMLStep<Record1<XML>> forXML();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_5, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectForJSONStep<Record1<JSON>> forJSON();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_5, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectForJSONStep<Record1<JSONB>> forJSONB();
}

