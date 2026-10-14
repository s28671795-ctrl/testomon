/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.AlterSchemaFinalStep;
import org.jooq.CheckReturnValue;
import org.jooq.Name;
import org.jooq.SQLDialect;
import org.jooq.Schema;
import org.jooq.Support;

public interface AlterSchemaStep {
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.VERTICA})
    @NotNull
    @CheckReturnValue
    public AlterSchemaFinalStep renameTo(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.VERTICA})
    @NotNull
    @CheckReturnValue
    public AlterSchemaFinalStep renameTo(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.VERTICA})
    @NotNull
    @CheckReturnValue
    public AlterSchemaFinalStep renameTo(Schema var1);
}

