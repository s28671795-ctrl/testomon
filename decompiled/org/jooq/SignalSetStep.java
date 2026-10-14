/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.Field;
import org.jooq.Pro;
import org.jooq.SQLDialect;
import org.jooq.Statement;
import org.jooq.Support;

@Pro
public interface SignalSetStep
extends Statement {
    @Support(value={SQLDialect.DB2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public Statement setMessageText(String var1);

    @Support(value={SQLDialect.DB2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public Statement setMessageText(Field<String> var1);
}

