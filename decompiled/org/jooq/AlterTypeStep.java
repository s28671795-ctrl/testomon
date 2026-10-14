/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.AlterTypeFinalStep;
import org.jooq.AlterTypeRenameValueToStep;
import org.jooq.CheckReturnValue;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.SQLDialect;
import org.jooq.Schema;
import org.jooq.Support;

public interface AlterTypeStep {
    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.POSTGRES})
    @NotNull
    @CheckReturnValue
    public AlterTypeFinalStep renameTo(String var1);

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.POSTGRES})
    @NotNull
    @CheckReturnValue
    public AlterTypeFinalStep renameTo(Name var1);

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.POSTGRES})
    @NotNull
    @CheckReturnValue
    public AlterTypeFinalStep setSchema(String var1);

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.POSTGRES})
    @NotNull
    @CheckReturnValue
    public AlterTypeFinalStep setSchema(Name var1);

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.POSTGRES})
    @NotNull
    @CheckReturnValue
    public AlterTypeFinalStep setSchema(Schema var1);

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.POSTGRES})
    @NotNull
    @CheckReturnValue
    public AlterTypeFinalStep addValue(String var1);

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.POSTGRES})
    @NotNull
    @CheckReturnValue
    public AlterTypeFinalStep addValue(Field<String> var1);

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.POSTGRES_10})
    @NotNull
    @CheckReturnValue
    public AlterTypeRenameValueToStep renameValue(String var1);

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.POSTGRES_10})
    @NotNull
    @CheckReturnValue
    public AlterTypeRenameValueToStep renameValue(Field<String> var1);
}

