/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.AlterDomainFinalStep;
import org.jooq.CheckReturnValue;
import org.jooq.SQLDialect;
import org.jooq.Support;

public interface AlterDomainDropConstraintCascadeStep
extends AlterDomainFinalStep {
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES})
    @NotNull
    @CheckReturnValue
    public AlterDomainFinalStep cascade();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.POSTGRES})
    @NotNull
    @CheckReturnValue
    public AlterDomainFinalStep restrict();
}

