/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.JSONArrayAggReturningStep;
import org.jooq.SQLDialect;
import org.jooq.Support;

public interface JSONArrayAggNullStep<T>
extends JSONArrayAggReturningStep<T> {
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB})
    @NotNull
    public JSONArrayAggReturningStep<T> nullOnNull();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB})
    @NotNull
    public JSONArrayAggReturningStep<T> absentOnNull();
}

