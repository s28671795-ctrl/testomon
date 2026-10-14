/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.AlterSequenceFinalStep;
import org.jooq.AlterSequenceFlagsStep;
import org.jooq.CheckReturnValue;
import org.jooq.Name;
import org.jooq.SQLDialect;
import org.jooq.Sequence;
import org.jooq.Support;

public interface AlterSequenceStep<T extends Number>
extends AlterSequenceFlagsStep<T> {
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.HSQLDB, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.VERTICA})
    @NotNull
    @CheckReturnValue
    public AlterSequenceFinalStep renameTo(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.HSQLDB, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.VERTICA})
    @NotNull
    @CheckReturnValue
    public AlterSequenceFinalStep renameTo(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.HSQLDB, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.VERTICA})
    @NotNull
    @CheckReturnValue
    public AlterSequenceFinalStep renameTo(Sequence<?> var1);
}

