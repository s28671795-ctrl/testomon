/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jooq.GroupConcatSeparatorStep;
import org.jooq.OrderField;
import org.jooq.SQLDialect;
import org.jooq.Support;

public interface GroupConcatOrderByStep
extends GroupConcatSeparatorStep {
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE11G, SQLDialect.POSTGRES, SQLDialect.SYBASE, SQLDialect.TERADATA})
    @NotNull
    public GroupConcatSeparatorStep orderBy(OrderField<?> ... var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE11G, SQLDialect.POSTGRES, SQLDialect.SYBASE, SQLDialect.TERADATA})
    @NotNull
    public GroupConcatSeparatorStep orderBy(Collection<? extends OrderField<?>> var1);
}

