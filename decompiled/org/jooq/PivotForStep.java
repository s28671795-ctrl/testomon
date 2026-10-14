/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.Field;
import org.jooq.PivotInStep;
import org.jooq.Pro;
import org.jooq.SQLDialect;
import org.jooq.Support;

@Pro
public interface PivotForStep {
    @Deprecated(forRemoval=true, since="3.12")
    @Support(value={SQLDialect.ORACLE11G, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    public <T> PivotInStep<T> on(Field<T> var1);

    @Support(value={SQLDialect.ORACLE11G, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    public <T> PivotInStep<T> for_(Field<T> var1);
}

