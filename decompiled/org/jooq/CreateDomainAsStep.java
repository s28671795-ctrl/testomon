/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.CreateDomainDefaultStep;
import org.jooq.DataType;
import org.jooq.SQLDialect;
import org.jooq.Support;

public interface CreateDomainAsStep {
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <T> CreateDomainDefaultStep<T> as(Class<T> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <T> CreateDomainDefaultStep<T> as(DataType<T> var1);
}

