/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.Condition;
import org.jooq.CreateIndexFinalStep;
import org.jooq.Field;
import org.jooq.PlainSQL;
import org.jooq.QueryPart;
import org.jooq.SQL;
import org.jooq.SQLDialect;
import org.jooq.Support;

public interface CreateIndexWhereStep
extends CreateIndexFinalStep {
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateIndexFinalStep where(Field<Boolean> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateIndexFinalStep where(Condition ... var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateIndexFinalStep where(Collection<? extends Condition> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateIndexFinalStep where(Condition var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public CreateIndexFinalStep where(String var1, QueryPart ... var2);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public CreateIndexFinalStep where(String var1, Object ... var2);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public CreateIndexFinalStep where(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public CreateIndexFinalStep where(SQL var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateIndexFinalStep excludeNullKeys();
}

