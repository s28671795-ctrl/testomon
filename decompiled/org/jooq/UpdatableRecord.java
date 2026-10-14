/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.Support;
import org.jooq.Table;
import org.jooq.TableRecord;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.DataChangedException;
import org.jooq.exception.NoDataFoundException;
import org.jooq.exception.TooManyRowsException;

public interface UpdatableRecord<R extends UpdatableRecord<R>>
extends TableRecord<R> {
    @NotNull
    public Record key();

    @Support
    public int store() throws DataAccessException, DataChangedException;

    @Support
    public int store(Field<?> ... var1) throws DataAccessException, DataChangedException;

    @Support
    public int store(Collection<? extends Field<?>> var1) throws DataAccessException, DataChangedException;

    @Override
    @Support
    public int insert() throws DataAccessException;

    @Override
    @Support
    public int insert(Field<?> ... var1) throws DataAccessException;

    @Override
    @Support
    public int insert(Collection<? extends Field<?>> var1) throws DataAccessException;

    @Support
    public int update() throws DataAccessException, DataChangedException;

    @Support
    public int update(Field<?> ... var1) throws DataAccessException, DataChangedException;

    @Support
    public int update(Collection<? extends Field<?>> var1) throws DataAccessException, DataChangedException;

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    public int merge() throws DataAccessException;

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    public int merge(Field<?> ... var1) throws DataAccessException;

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    public int merge(Collection<? extends Field<?>> var1) throws DataAccessException;

    @Support
    public int delete() throws DataAccessException, DataChangedException;

    @Support
    public void refresh() throws DataAccessException;

    @Support
    public void refresh(Field<?> ... var1) throws DataAccessException, NoDataFoundException;

    @Support
    public void refresh(Collection<? extends Field<?>> var1) throws DataAccessException, NoDataFoundException;

    @NotNull
    public R copy();

    @Support
    @Nullable
    @Blocking
    public <O extends TableRecord<O>> O fetchChild(ForeignKey<O, R> var1) throws TooManyRowsException, DataAccessException;

    @Support
    @NotNull
    @Blocking
    public <O extends TableRecord<O>> Result<O> fetchChildren(ForeignKey<O, R> var1) throws DataAccessException;

    @Support
    @NotNull
    public <O extends TableRecord<O>> Table<O> children(ForeignKey<O, R> var1);
}

