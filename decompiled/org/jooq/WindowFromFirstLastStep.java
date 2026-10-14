/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.SQLDialect;
import org.jooq.Support;
import org.jooq.WindowIgnoreNullsStep;

public interface WindowFromFirstLastStep<T>
extends WindowIgnoreNullsStep<T> {
    @Support(value={SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.ORACLE, SQLDialect.SNOWFLAKE})
    @NotNull
    public WindowIgnoreNullsStep<T> fromFirst();

    @Support(value={SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.ORACLE, SQLDialect.SNOWFLAKE})
    @NotNull
    public WindowIgnoreNullsStep<T> fromLast();
}

