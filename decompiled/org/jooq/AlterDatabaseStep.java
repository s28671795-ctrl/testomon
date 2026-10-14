/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.AlterDatabaseFinalStep;
import org.jooq.Catalog;
import org.jooq.CheckReturnValue;
import org.jooq.Name;
import org.jooq.SQLDialect;
import org.jooq.Support;

public interface AlterDatabaseStep {
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterDatabaseFinalStep renameTo(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterDatabaseFinalStep renameTo(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterDatabaseFinalStep renameTo(Catalog var1);
}

