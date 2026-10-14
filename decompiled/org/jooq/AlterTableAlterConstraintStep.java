/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.AlterTableFinalStep;
import org.jooq.CheckReturnValue;
import org.jooq.SQLDialect;
import org.jooq.Support;

public interface AlterTableAlterConstraintStep {
    @Support(value={SQLDialect.DB2, SQLDialect.MYSQL_8_0_19, SQLDialect.ORACLE})
    @NotNull
    @CheckReturnValue
    public AlterTableFinalStep enforced();

    @Support(value={SQLDialect.DB2, SQLDialect.MYSQL_8_0_19, SQLDialect.ORACLE})
    @NotNull
    @CheckReturnValue
    public AlterTableFinalStep notEnforced();
}

