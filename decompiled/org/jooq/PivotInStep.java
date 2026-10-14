/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jooq.Field;
import org.jooq.Pro;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.Support;
import org.jooq.Table;

@Pro
public interface PivotInStep<T> {
    @Support(value={SQLDialect.ORACLE11G, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    public Table<Record> in(T ... var1);

    @Support(value={SQLDialect.ORACLE11G, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    public Table<Record> in(Field<?> ... var1);

    @Support(value={SQLDialect.ORACLE11G, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    public Table<Record> in(Collection<? extends Field<T>> var1);
}

