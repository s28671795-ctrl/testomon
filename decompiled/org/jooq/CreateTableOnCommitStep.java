/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.CreateTableCommentStep;
import org.jooq.SQLDialect;
import org.jooq.Support;

public interface CreateTableOnCommitStep
extends CreateTableCommentStep {
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateTableCommentStep onCommitDeleteRows();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateTableCommentStep onCommitPreserveRows();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateTableCommentStep onCommitDrop();
}

