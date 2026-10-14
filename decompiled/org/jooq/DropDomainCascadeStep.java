/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.DropDomainFinalStep;
import org.jooq.SQLDialect;
import org.jooq.Support;

public interface DropDomainCascadeStep
extends DropDomainFinalStep {
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropDomainFinalStep cascade();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropDomainFinalStep restrict();
}

