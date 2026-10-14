/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.Constraint;
import org.jooq.CreateDomainFinalStep;
import org.jooq.SQLDialect;
import org.jooq.Support;

public interface CreateDomainConstraintStep
extends CreateDomainFinalStep {
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateDomainConstraintStep constraints(Constraint ... var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateDomainConstraintStep constraints(Collection<? extends Constraint> var1);
}

