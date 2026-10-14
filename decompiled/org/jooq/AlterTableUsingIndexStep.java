/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.AlterTableFinalStep;
import org.jooq.CheckReturnValue;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Pro;
import org.jooq.SQLDialect;
import org.jooq.Support;

public interface AlterTableUsingIndexStep
extends AlterTableFinalStep {
    @Support(value={SQLDialect.ORACLE})
    @NotNull
    @CheckReturnValue
    @Pro
    public AlterTableFinalStep usingIndex(String var1);

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    @CheckReturnValue
    @Pro
    public AlterTableFinalStep usingIndex(Name var1);

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    @CheckReturnValue
    @Pro
    public AlterTableFinalStep usingIndex(Index var1);
}

