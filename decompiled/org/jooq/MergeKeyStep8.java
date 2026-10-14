/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.Field;
import org.jooq.MergeValuesStep8;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.Support;

@Deprecated(forRemoval=true, since="3.14")
public interface MergeKeyStep8<R extends Record, T1, T2, T3, T4, T5, T6, T7, T8>
extends MergeValuesStep8<R, T1, T2, T3, T4, T5, T6, T7, T8> {
    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public MergeValuesStep8<R, T1, T2, T3, T4, T5, T6, T7, T8> key(Field<?> ... var1);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public MergeValuesStep8<R, T1, T2, T3, T4, T5, T6, T7, T8> key(Collection<? extends Field<?>> var1);
}

