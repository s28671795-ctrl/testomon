/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.CreateTriggerEventOfStep;
import org.jooq.CreateTriggerEventOrStep;
import org.jooq.Pro;
import org.jooq.SQLDialect;
import org.jooq.Support;

@Pro
public interface CreateTriggerEventStep {
    @Support(value={SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    public CreateTriggerEventOrStep beforeInsert();

    @Support(value={SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    public CreateTriggerEventOfStep beforeUpdate();

    @Support(value={SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    public CreateTriggerEventOrStep beforeDelete();

    @Support(value={SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    public CreateTriggerEventOrStep afterInsert();

    @Support(value={SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    public CreateTriggerEventOfStep afterUpdate();

    @Support(value={SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    public CreateTriggerEventOrStep afterDelete();

    @Support(value={SQLDialect.DB2, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    public CreateTriggerEventOfStep insteadOfUpdate();

    @Support(value={SQLDialect.DB2, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    public CreateTriggerEventOrStep insteadOfInsert();

    @Support(value={SQLDialect.DB2, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    public CreateTriggerEventOrStep insteadOfDelete();
}

