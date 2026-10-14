/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.Condition;
import org.jooq.CreateTriggerActionStep;
import org.jooq.Field;
import org.jooq.PlainSQL;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.SQL;
import org.jooq.SQLDialect;
import org.jooq.Support;

@Pro
public interface CreateTriggerWhenStep
extends CreateTriggerActionStep {
    @Support(value={SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    public CreateTriggerActionStep when(Field<Boolean> var1);

    @Support(value={SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    public CreateTriggerActionStep when(Condition ... var1);

    @Support(value={SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    public CreateTriggerActionStep when(Collection<? extends Condition> var1);

    @Support(value={SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    public CreateTriggerActionStep when(Condition var1);

    @Support(value={SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.SQLSERVER})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public CreateTriggerActionStep when(String var1, QueryPart ... var2);

    @Support(value={SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.SQLSERVER})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public CreateTriggerActionStep when(String var1, Object ... var2);

    @Support(value={SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.SQLSERVER})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public CreateTriggerActionStep when(String var1);

    @Support(value={SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.SQLSERVER})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public CreateTriggerActionStep when(SQL var1);
}

