/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.CreateTriggerEventOfStep;
import org.jooq.CreateTriggerEventOnStep;
import org.jooq.Pro;
import org.jooq.SQLDialect;
import org.jooq.Support;

@Pro
public interface CreateTriggerEventOrStep
extends CreateTriggerEventOnStep {
    @Support(value={SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.ORACLE, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    public CreateTriggerEventOrStep orInsert();

    @Support(value={SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.ORACLE, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    public CreateTriggerEventOfStep orUpdate();

    @Support(value={SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.ORACLE, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    public CreateTriggerEventOrStep orDelete();
}

