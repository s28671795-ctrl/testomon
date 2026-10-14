/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.RecordMapper;
import org.jooq.SQLDialect;
import org.jooq.Support;
import org.jooq.Table;
import org.jooq.TableRecord;
import org.jooq.conf.Settings;
import org.jooq.exception.DataAccessException;

@Blocking
public interface DAO<R extends TableRecord<R>, P, T> {
    @NotNull
    public Configuration configuration();

    @NotNull
    public Settings settings();

    @NotNull
    public SQLDialect dialect();

    @NotNull
    public SQLDialect family();

    @NotNull
    public RecordMapper<R, P> mapper();

    @Support
    public void insert(P var1) throws DataAccessException;

    @Support
    public void insert(P ... var1) throws DataAccessException;

    @Support
    public void insert(Collection<P> var1) throws DataAccessException;

    @Support
    public void update(P var1) throws DataAccessException;

    @Support
    public void update(P ... var1) throws DataAccessException;

    @Support
    public void update(Collection<P> var1) throws DataAccessException;

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA})
    public void merge(P var1) throws DataAccessException;

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA})
    public void merge(P ... var1) throws DataAccessException;

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA})
    public void merge(Collection<P> var1) throws DataAccessException;

    @Support
    public void delete(P var1) throws DataAccessException;

    @Support
    public void delete(P ... var1) throws DataAccessException;

    @Support
    public void delete(Collection<P> var1) throws DataAccessException;

    @Support
    public void deleteById(T var1) throws DataAccessException;

    @Support
    public void deleteById(T ... var1) throws DataAccessException;

    @Support
    public void deleteById(Collection<T> var1) throws DataAccessException;

    @Support
    public boolean exists(P var1) throws DataAccessException;

    @Support
    public boolean existsById(T var1) throws DataAccessException;

    @Support
    public long count() throws DataAccessException;

    @Support
    @NotNull
    public @NotNull List<@NotNull P> findAll() throws DataAccessException;

    @Support
    @Nullable
    public P findById(T var1) throws DataAccessException;

    @Support
    @NotNull
    public @NotNull Optional<@NotNull P> findOptionalById(T var1) throws DataAccessException;

    @Support
    @NotNull
    public <Z> List<P> fetch(Field<Z> var1, Z ... var2) throws DataAccessException;

    @Support
    @NotNull
    public <Z> List<P> fetch(Field<Z> var1, Collection<? extends Z> var2) throws DataAccessException;

    @Support
    @NotNull
    public <Z> List<P> fetchRange(Field<Z> var1, Z var2, Z var3) throws DataAccessException;

    @Support
    @Nullable
    public <Z> P fetchOne(Field<Z> var1, Z var2) throws DataAccessException;

    @Support
    @NotNull
    public <Z> @NotNull Optional<@NotNull P> fetchOptional(Field<Z> var1, Z var2) throws DataAccessException;

    @NotNull
    public Table<R> getTable();

    @NotNull
    public Class<P> getType();

    public T getId(P var1);
}

