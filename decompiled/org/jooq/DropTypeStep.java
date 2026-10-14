/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.DropTypeFinalStep;
import org.jooq.SQLDialect;
import org.jooq.Support;

public interface DropTypeStep
extends DropTypeFinalStep {
    @Support(value={SQLDialect.H2, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropTypeFinalStep cascade();

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropTypeFinalStep restrict();
}

