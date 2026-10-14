/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.AggregateFilterStep;
import org.jooq.DataType;
import org.jooq.SQLDialect;
import org.jooq.Support;

public interface JSONObjectAggReturningStep<T>
extends AggregateFilterStep<T> {
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB})
    @NotNull
    public AggregateFilterStep<T> returning(DataType<?> var1);
}

