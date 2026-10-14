/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.CreateDomainConstraintStep;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.Support;

public interface CreateDomainDefaultStep<T>
extends CreateDomainConstraintStep {
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateDomainConstraintStep default_(T var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateDomainConstraintStep default_(Field<T> var1);
}

