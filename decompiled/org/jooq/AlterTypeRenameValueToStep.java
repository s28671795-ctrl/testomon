/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.AlterTypeFinalStep;
import org.jooq.CheckReturnValue;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.Support;

public interface AlterTypeRenameValueToStep {
    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.POSTGRES_10})
    @NotNull
    @CheckReturnValue
    public AlterTypeFinalStep to(String var1);

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.POSTGRES_10})
    @NotNull
    @CheckReturnValue
    public AlterTypeFinalStep to(Field<String> var1);
}

