/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.Support;

public interface JSONValueReturningStep<T>
extends Field<T> {
    @Support(value={SQLDialect.DB2_11, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_12, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    public Field<T> returning(DataType<?> var1);
}

