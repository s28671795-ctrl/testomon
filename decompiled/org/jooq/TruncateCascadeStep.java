/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.Support;
import org.jooq.TruncateFinalStep;

public interface TruncateCascadeStep<R extends Record>
extends TruncateFinalStep<R> {
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public TruncateFinalStep<R> cascade();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public TruncateFinalStep<R> restrict();
}

