/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Stream;
import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Fields;
import org.jooq.Formattable;
import org.jooq.Record;
import org.jooq.RecordHandler;
import org.jooq.RecordMapper;
import org.jooq.RecordType;
import org.jooq.Result;
import org.jooq.Table;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.MappingException;

@Blocking
public interface Cursor<R extends Record>
extends Fields,
Iterable<R>,
Formattable,
AutoCloseable {
    @NotNull
    public RecordType<R> recordType();

    public boolean hasNext() throws DataAccessException;

    @NotNull
    public Result<R> fetch() throws DataAccessException;

    @Deprecated(forRemoval=true, since="3.10")
    @NotNull
    public Result<R> fetch(int var1) throws DataAccessException;

    @NotNull
    public Result<R> fetchNext(int var1) throws DataAccessException;

    @Deprecated(forRemoval=true, since="3.15")
    @NotNull
    public <H extends RecordHandler<? super R>> H fetchInto(H var1) throws DataAccessException;

    @NotNull
    public <E> List<E> fetch(RecordMapper<? super R, E> var1) throws DataAccessException;

    @NotNull
    public <E> List<E> fetchInto(Class<? extends E> var1) throws DataAccessException, MappingException;

    @NotNull
    public <Z extends Record> Result<Z> fetchInto(Table<Z> var1) throws DataAccessException, MappingException;

    @Deprecated(forRemoval=true, since="3.10")
    @Nullable
    public R fetchOne() throws DataAccessException;

    @Deprecated(forRemoval=true, since="3.10")
    @NotNull
    public <H extends RecordHandler<? super R>> H fetchOneInto(H var1) throws DataAccessException;

    @Deprecated(forRemoval=true, since="3.10")
    @Nullable
    public <E> E fetchOne(RecordMapper<? super R, E> var1) throws DataAccessException;

    @Deprecated(forRemoval=true, since="3.10")
    @Nullable
    public <Z extends Record> Z fetchOneInto(Table<Z> var1) throws DataAccessException, MappingException;

    @Nullable
    public R fetchNext() throws DataAccessException;

    @Deprecated(forRemoval=true, since="3.15")
    @NotNull
    public <H extends RecordHandler<? super R>> H fetchNextInto(H var1) throws DataAccessException;

    @Deprecated(forRemoval=true, since="3.10")
    @Nullable
    public <E> E fetchOneInto(Class<? extends E> var1) throws DataAccessException, MappingException;

    @Nullable
    public <E> E fetchNextInto(Class<? extends E> var1) throws DataAccessException, MappingException;

    @Nullable
    public <E> E fetchNext(RecordMapper<? super R, E> var1) throws DataAccessException;

    @Nullable
    public <Z extends Record> Z fetchNextInto(Table<Z> var1) throws DataAccessException, MappingException;

    @Deprecated(forRemoval=true, since="3.10")
    @NotNull
    public Optional<R> fetchOptional() throws DataAccessException;

    @Deprecated(forRemoval=true, since="3.10")
    @NotNull
    public <E> Optional<E> fetchOptionalInto(Class<? extends E> var1) throws DataAccessException, MappingException;

    @Deprecated(forRemoval=true, since="3.10")
    @NotNull
    public <E> Optional<E> fetchOptional(RecordMapper<? super R, E> var1) throws DataAccessException;

    @Deprecated(forRemoval=true, since="3.10")
    @NotNull
    public <Z extends Record> Optional<Z> fetchOptionalInto(Table<Z> var1) throws DataAccessException, MappingException;

    @NotNull
    public Optional<R> fetchNextOptional() throws DataAccessException;

    @NotNull
    public <E> Optional<E> fetchNextOptionalInto(Class<? extends E> var1) throws DataAccessException, MappingException;

    @NotNull
    public <E> Optional<E> fetchNextOptional(RecordMapper<? super R, E> var1) throws DataAccessException;

    @NotNull
    public <Z extends Record> Optional<Z> fetchNextOptionalInto(Table<Z> var1) throws DataAccessException, MappingException;

    @NotNull
    public Stream<R> stream() throws DataAccessException;

    public <X, A> X collect(Collector<? super R, A, X> var1) throws DataAccessException;

    @Override
    public void close() throws DataAccessException;

    public boolean isClosed();

    @Nullable
    public ResultSet resultSet();
}

