/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.AlterTableFinalStep;
import org.jooq.CheckReturnValue;
import org.jooq.SQLDialect;
import org.jooq.Support;

public interface AlterTableDropStep
extends AlterTableFinalStep {
    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterTableFinalStep cascade();

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterTableFinalStep restrict();
}

