/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.Support;
import org.jooq.TruncateCascadeStep;

public interface TruncateIdentityStep<R extends Record>
extends TruncateCascadeStep<R> {
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
    @NotNull
    @CheckReturnValue
    public TruncateCascadeStep<R> restartIdentity();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
    @NotNull
    @CheckReturnValue
    public TruncateCascadeStep<R> continueIdentity();
}

