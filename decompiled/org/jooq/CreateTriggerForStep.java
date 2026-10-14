/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.CreateTriggerWhenStep;
import org.jooq.Pro;
import org.jooq.SQLDialect;
import org.jooq.Support;

@Pro
public interface CreateTriggerForStep
extends CreateTriggerWhenStep {
    @Support(value={SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE})
    @NotNull
    @CheckReturnValue
    public CreateTriggerWhenStep forEachRow();

    @Support(value={SQLDialect.DB2, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    public CreateTriggerWhenStep forEachStatement();
}

