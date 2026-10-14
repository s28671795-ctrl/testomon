/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.AlterTableFinalStep;
import org.jooq.CheckReturnValue;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.SQLDialect;
import org.jooq.Support;

public interface AlterTableAddStep
extends AlterTableFinalStep {
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL})
    @NotNull
    @CheckReturnValue
    public AlterTableFinalStep first();

    @Support(value={SQLDialect.H2, SQLDialect.HSQLDB})
    @NotNull
    @CheckReturnValue
    public AlterTableFinalStep before(String var1);

    @Support(value={SQLDialect.H2, SQLDialect.HSQLDB})
    @NotNull
    @CheckReturnValue
    public AlterTableFinalStep before(Name var1);

    @Support(value={SQLDialect.H2, SQLDialect.HSQLDB})
    @NotNull
    @CheckReturnValue
    public AlterTableFinalStep before(Field<?> var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.H2, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL})
    @NotNull
    @CheckReturnValue
    public AlterTableFinalStep after(String var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.H2, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL})
    @NotNull
    @CheckReturnValue
    public AlterTableFinalStep after(Name var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.H2, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL})
    @NotNull
    @CheckReturnValue
    public AlterTableFinalStep after(Field<?> var1);
}

