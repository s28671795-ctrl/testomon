/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.AlterDomainFinalStep;
import org.jooq.CheckReturnValue;
import org.jooq.Constraint;
import org.jooq.Name;
import org.jooq.SQLDialect;
import org.jooq.Support;

public interface AlterDomainRenameConstraintStep {
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES})
    @NotNull
    @CheckReturnValue
    public AlterDomainFinalStep to(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES})
    @NotNull
    @CheckReturnValue
    public AlterDomainFinalStep to(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES})
    @NotNull
    @CheckReturnValue
    public AlterDomainFinalStep to(Constraint var1);
}

