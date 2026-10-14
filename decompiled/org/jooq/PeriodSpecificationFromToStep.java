/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.Field;
import org.jooq.PeriodSpecification;
import org.jooq.Pro;
import org.jooq.SQLDialect;
import org.jooq.Support;

@Pro
public interface PeriodSpecificationFromToStep<T> {
    @Support(value={SQLDialect.DB2, SQLDialect.HSQLDB, SQLDialect.MARIADB_10_3, SQLDialect.SQLSERVER2016})
    @NotNull
    public PeriodSpecification to(T var1);

    @Support(value={SQLDialect.DB2, SQLDialect.HSQLDB, SQLDialect.MARIADB_10_3, SQLDialect.SQLSERVER2016})
    @NotNull
    public PeriodSpecification to(Field<T> var1);
}

