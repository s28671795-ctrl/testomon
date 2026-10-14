/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jooq.JSONArrayAggNullStep;
import org.jooq.OrderField;
import org.jooq.SQLDialect;
import org.jooq.Support;

public interface JSONArrayAggOrderByStep<J>
extends JSONArrayAggNullStep<J> {
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.YUGABYTEDB})
    @NotNull
    public JSONArrayAggNullStep<J> orderBy(OrderField<?> ... var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.YUGABYTEDB})
    @NotNull
    public JSONArrayAggNullStep<J> orderBy(Collection<? extends OrderField<?>> var1);
}

