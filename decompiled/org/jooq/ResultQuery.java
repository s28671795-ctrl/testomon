/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Spliterator;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.stream.Collector;
import java.util.stream.Stream;
import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.CloseableResultQuery;
import org.jooq.Converter;
import org.jooq.Cursor;
import org.jooq.Field;
import org.jooq.Fields;
import org.jooq.Name;
import org.jooq.Publisher;
import org.jooq.Query;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Record10;
import org.jooq.Record11;
import org.jooq.Record12;
import org.jooq.Record13;
import org.jooq.Record14;
import org.jooq.Record15;
import org.jooq.Record16;
import org.jooq.Record17;
import org.jooq.Record18;
import org.jooq.Record19;
import org.jooq.Record2;
import org.jooq.Record20;
import org.jooq.Record21;
import org.jooq.Record22;
import org.jooq.Record3;
import org.jooq.Record4;
import org.jooq.Record5;
import org.jooq.Record6;
import org.jooq.Record7;
import org.jooq.Record8;
import org.jooq.Record9;
import org.jooq.RecordHandler;
import org.jooq.RecordMapper;
import org.jooq.Result;
import org.jooq.Results;
import org.jooq.Table;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.DataTypeException;
import org.jooq.exception.InvalidResultException;
import org.jooq.exception.MappingException;
import org.jooq.exception.NoDataFoundException;
import org.jooq.exception.TooManyRowsException;

public interface ResultQuery<R extends Record>
extends Fields,
Query,
Iterable<R>,
Publisher<R> {
    @NotNull
    @Blocking
    public Result<R> fetch() throws DataAccessException;

    @NotNull
    @Blocking
    public ResultSet fetchResultSet() throws DataAccessException;

    @Override
    @NotNull
    @Blocking
    public Iterator<R> iterator() throws DataAccessException;

    @Override
    @NotNull
    @Blocking
    default public Spliterator<R> spliterator() {
        return Iterable.super.spliterator();
    }

    @Override
    @Blocking
    default public void forEach(Consumer<? super R> action) {
        Iterable.super.forEach(action);
    }

    @NotNull
    @Blocking
    public Stream<R> fetchStream() throws DataAccessException;

    @NotNull
    @Blocking
    public <E> Stream<E> fetchStreamInto(Class<? extends E> var1) throws DataAccessException, MappingException;

    @NotNull
    @Blocking
    public <Z extends Record> Stream<Z> fetchStreamInto(Table<Z> var1) throws DataAccessException;

    @NotNull
    @Blocking
    public Stream<R> stream() throws DataAccessException;

    @Blocking
    public <X, A> X collect(Collector<? super R, A, X> var1) throws DataAccessException;

    @NotNull
    @Blocking
    public Cursor<R> fetchLazy() throws DataAccessException;

    @NotNull
    @Blocking
    public Results fetchMany() throws DataAccessException;

    @NotNull
    @Blocking
    public <T> List<T> fetch(Field<T> var1) throws DataAccessException;

    @NotNull
    @Blocking
    public <U> List<U> fetch(Field<?> var1, Class<? extends U> var2) throws DataAccessException;

    @NotNull
    @Blocking
    public <T, U> List<U> fetch(Field<T> var1, Converter<? super T, ? extends U> var2) throws DataAccessException;

    @NotNull
    @Blocking
    public List<?> fetch(int var1) throws DataAccessException;

    @NotNull
    @Blocking
    public <U> List<U> fetch(int var1, Class<? extends U> var2) throws DataAccessException;

    @NotNull
    @Blocking
    public <U> List<U> fetch(int var1, Converter<?, ? extends U> var2) throws DataAccessException;

    @NotNull
    @Blocking
    public List<?> fetch(String var1) throws DataAccessException;

    @NotNull
    @Blocking
    public <U> List<U> fetch(String var1, Class<? extends U> var2) throws DataAccessException;

    @NotNull
    @Blocking
    public <U> List<U> fetch(String var1, Converter<?, ? extends U> var2) throws DataAccessException;

    @NotNull
    @Blocking
    public List<?> fetch(Name var1) throws DataAccessException;

    @NotNull
    @Blocking
    public <U> List<U> fetch(Name var1, Class<? extends U> var2) throws DataAccessException;

    @NotNull
    @Blocking
    public <U> List<U> fetch(Name var1, Converter<?, ? extends U> var2) throws DataAccessException;

    @Nullable
    @Blocking
    public <T> T fetchOne(Field<T> var1) throws DataAccessException, TooManyRowsException;

    @Nullable
    @Blocking
    public <U> U fetchOne(Field<?> var1, Class<? extends U> var2) throws DataAccessException, TooManyRowsException;

    @Nullable
    @Blocking
    public <T, U> U fetchOne(Field<T> var1, Converter<? super T, ? extends U> var2) throws DataAccessException, TooManyRowsException;

    @Nullable
    @Blocking
    public Object fetchOne(int var1) throws DataAccessException, TooManyRowsException;

    @Nullable
    @Blocking
    public <U> U fetchOne(int var1, Class<? extends U> var2) throws DataAccessException, TooManyRowsException;

    @Nullable
    @Blocking
    public <U> U fetchOne(int var1, Converter<?, ? extends U> var2) throws DataAccessException, TooManyRowsException;

    @Nullable
    @Blocking
    public Object fetchOne(String var1) throws DataAccessException, TooManyRowsException;

    @Nullable
    @Blocking
    public <U> U fetchOne(String var1, Class<? extends U> var2) throws DataAccessException, TooManyRowsException;

    @Nullable
    @Blocking
    public <U> U fetchOne(String var1, Converter<?, ? extends U> var2) throws DataAccessException, TooManyRowsException;

    @Nullable
    @Blocking
    public Object fetchOne(Name var1) throws DataAccessException, TooManyRowsException;

    @Nullable
    @Blocking
    public <U> U fetchOne(Name var1, Class<? extends U> var2) throws DataAccessException, TooManyRowsException;

    @Nullable
    @Blocking
    public <U> U fetchOne(Name var1, Converter<?, ? extends U> var2) throws DataAccessException, TooManyRowsException;

    @Nullable
    @Blocking
    public R fetchOne() throws DataAccessException, TooManyRowsException;

    @Nullable
    @Blocking
    public <E> E fetchOne(RecordMapper<? super R, E> var1) throws DataAccessException, TooManyRowsException;

    @Nullable
    @Blocking
    public Map<String, Object> fetchOneMap() throws DataAccessException, TooManyRowsException;

    @Nullable
    @Blocking
    public @Nullable Object @Nullable [] fetchOneArray() throws DataAccessException, TooManyRowsException;

    @Nullable
    @Blocking
    public <E> E fetchOneInto(Class<? extends E> var1) throws DataAccessException, MappingException, TooManyRowsException;

    @Nullable
    @Blocking
    public <Z extends Record> Z fetchOneInto(Table<Z> var1) throws DataAccessException, TooManyRowsException;

    @Nullable
    @Blocking
    public <T> T fetchSingle(Field<T> var1) throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @Nullable
    @Blocking
    public <U> U fetchSingle(Field<?> var1, Class<? extends U> var2) throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @Nullable
    @Blocking
    public <T, U> U fetchSingle(Field<T> var1, Converter<? super T, ? extends U> var2) throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @Nullable
    @Blocking
    public Object fetchSingle(int var1) throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @Nullable
    @Blocking
    public <U> U fetchSingle(int var1, Class<? extends U> var2) throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @Nullable
    @Blocking
    public <U> U fetchSingle(int var1, Converter<?, ? extends U> var2) throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @Nullable
    @Blocking
    public Object fetchSingle(String var1) throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @Nullable
    @Blocking
    public <U> U fetchSingle(String var1, Class<? extends U> var2) throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @Nullable
    @Blocking
    public <U> U fetchSingle(String var1, Converter<?, ? extends U> var2) throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @Nullable
    @Blocking
    public Object fetchSingle(Name var1) throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @Nullable
    @Blocking
    public <U> U fetchSingle(Name var1, Class<? extends U> var2) throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @Nullable
    @Blocking
    public <U> U fetchSingle(Name var1, Converter<?, ? extends U> var2) throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @NotNull
    @Blocking
    public R fetchSingle() throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @NotNull
    @Blocking
    public <E> E fetchSingle(RecordMapper<? super R, E> var1) throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @NotNull
    @Blocking
    public Map<String, Object> fetchSingleMap() throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @Nullable
    @Blocking
    public @Nullable Object @NotNull [] fetchSingleArray() throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @Blocking
    public <E> E fetchSingleInto(Class<? extends E> var1) throws DataAccessException, MappingException, NoDataFoundException, TooManyRowsException;

    @NotNull
    @Blocking
    public <Z extends Record> Z fetchSingleInto(Table<Z> var1) throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @NotNull
    @Blocking
    public <T> Optional<T> fetchOptional(Field<T> var1) throws DataAccessException, TooManyRowsException;

    @NotNull
    @Blocking
    public <U> Optional<U> fetchOptional(Field<?> var1, Class<? extends U> var2) throws DataAccessException, TooManyRowsException;

    @NotNull
    @Blocking
    public <T, U> Optional<U> fetchOptional(Field<T> var1, Converter<? super T, ? extends U> var2) throws DataAccessException, TooManyRowsException;

    @NotNull
    @Blocking
    public Optional<?> fetchOptional(int var1) throws DataAccessException, TooManyRowsException;

    @NotNull
    @Blocking
    public <U> Optional<U> fetchOptional(int var1, Class<? extends U> var2) throws DataAccessException, TooManyRowsException;

    @NotNull
    @Blocking
    public <U> Optional<U> fetchOptional(int var1, Converter<?, ? extends U> var2) throws DataAccessException, TooManyRowsException;

    @NotNull
    @Blocking
    public Optional<?> fetchOptional(String var1) throws DataAccessException, TooManyRowsException;

    @NotNull
    @Blocking
    public <U> Optional<U> fetchOptional(String var1, Class<? extends U> var2) throws DataAccessException, TooManyRowsException;

    @NotNull
    @Blocking
    public <U> Optional<U> fetchOptional(String var1, Converter<?, ? extends U> var2) throws DataAccessException, TooManyRowsException;

    @NotNull
    @Blocking
    public Optional<?> fetchOptional(Name var1) throws DataAccessException, TooManyRowsException;

    @NotNull
    @Blocking
    public <U> Optional<U> fetchOptional(Name var1, Class<? extends U> var2) throws DataAccessException, TooManyRowsException;

    @NotNull
    @Blocking
    public <U> Optional<U> fetchOptional(Name var1, Converter<?, ? extends U> var2) throws DataAccessException, TooManyRowsException;

    @NotNull
    @Blocking
    public Optional<R> fetchOptional() throws DataAccessException, TooManyRowsException;

    @NotNull
    @Blocking
    public <E> Optional<E> fetchOptional(RecordMapper<? super R, E> var1) throws DataAccessException, TooManyRowsException;

    @NotNull
    @Blocking
    public Optional<Map<String, Object>> fetchOptionalMap() throws DataAccessException, TooManyRowsException;

    @NotNull
    @Blocking
    public Optional<Object[]> fetchOptionalArray() throws DataAccessException, TooManyRowsException;

    @NotNull
    @Blocking
    public <E> Optional<E> fetchOptionalInto(Class<? extends E> var1) throws DataAccessException, MappingException, TooManyRowsException;

    @NotNull
    @Blocking
    public <Z extends Record> Optional<Z> fetchOptionalInto(Table<Z> var1) throws DataAccessException, TooManyRowsException;

    @Nullable
    @Blocking
    public <T> T fetchAny(Field<T> var1) throws DataAccessException;

    @Nullable
    @Blocking
    public <U> U fetchAny(Field<?> var1, Class<? extends U> var2) throws DataAccessException;

    @Nullable
    @Blocking
    public <T, U> U fetchAny(Field<T> var1, Converter<? super T, ? extends U> var2) throws DataAccessException;

    @Nullable
    @Blocking
    public Object fetchAny(int var1) throws DataAccessException;

    @Nullable
    @Blocking
    public <U> U fetchAny(int var1, Class<? extends U> var2) throws DataAccessException;

    @Nullable
    @Blocking
    public <U> U fetchAny(int var1, Converter<?, ? extends U> var2) throws DataAccessException;

    @Nullable
    @Blocking
    public Object fetchAny(String var1) throws DataAccessException;

    @Nullable
    @Blocking
    public <U> U fetchAny(String var1, Class<? extends U> var2) throws DataAccessException;

    @Nullable
    @Blocking
    public <U> U fetchAny(String var1, Converter<?, ? extends U> var2) throws DataAccessException;

    @Nullable
    @Blocking
    public Object fetchAny(Name var1) throws DataAccessException;

    @Nullable
    @Blocking
    public <U> U fetchAny(Name var1, Class<? extends U> var2) throws DataAccessException;

    @Nullable
    @Blocking
    public <U> U fetchAny(Name var1, Converter<?, ? extends U> var2) throws DataAccessException;

    @Nullable
    @Blocking
    public R fetchAny() throws DataAccessException;

    @Nullable
    @Blocking
    public <E> E fetchAny(RecordMapper<? super R, E> var1) throws DataAccessException;

    @Nullable
    @Blocking
    public Map<String, Object> fetchAnyMap() throws DataAccessException;

    @Nullable
    @Blocking
    public @Nullable Object @Nullable [] fetchAnyArray() throws DataAccessException;

    @Nullable
    @Blocking
    public <E> E fetchAnyInto(Class<? extends E> var1) throws DataAccessException, MappingException;

    @Nullable
    @Blocking
    public <Z extends Record> Z fetchAnyInto(Table<Z> var1) throws DataAccessException;

    @NotNull
    @Blocking
    public List<Map<String, Object>> fetchMaps() throws DataAccessException;

    @NotNull
    @Blocking
    public <K> Map<K, R> fetchMap(Field<K> var1) throws DataAccessException;

    @NotNull
    @Blocking
    public Map<?, R> fetchMap(int var1) throws DataAccessException;

    @NotNull
    @Blocking
    public Map<?, R> fetchMap(String var1) throws DataAccessException;

    @NotNull
    @Blocking
    public Map<?, R> fetchMap(Name var1) throws DataAccessException;

    @NotNull
    @Blocking
    public <K, V> Map<K, V> fetchMap(Field<K> var1, Field<V> var2) throws DataAccessException;

    @NotNull
    @Blocking
    public Map<?, ?> fetchMap(int var1, int var2) throws DataAccessException;

    @NotNull
    @Blocking
    public Map<?, ?> fetchMap(String var1, String var2) throws DataAccessException;

    @NotNull
    @Blocking
    public Map<?, ?> fetchMap(Name var1, Name var2) throws DataAccessException;

    @NotNull
    @Blocking
    public Map<Record, R> fetchMap(Field<?>[] var1) throws DataAccessException;

    @NotNull
    @Blocking
    public Map<Record, R> fetchMap(int[] var1) throws DataAccessException;

    @NotNull
    @Blocking
    public Map<Record, R> fetchMap(String[] var1) throws DataAccessException;

    @NotNull
    @Blocking
    public Map<Record, R> fetchMap(Name[] var1) throws DataAccessException;

    @NotNull
    @Blocking
    public Map<Record, Record> fetchMap(Field<?>[] var1, Field<?>[] var2) throws DataAccessException;

    @NotNull
    @Blocking
    public Map<Record, Record> fetchMap(int[] var1, int[] var2) throws DataAccessException;

    @NotNull
    @Blocking
    public Map<Record, Record> fetchMap(String[] var1, String[] var2) throws DataAccessException;

    @NotNull
    @Blocking
    public Map<Record, Record> fetchMap(Name[] var1, Name[] var2) throws DataAccessException;

    @NotNull
    @Blocking
    public <E> Map<List<?>, E> fetchMap(Field<?>[] var1, Class<? extends E> var2) throws DataAccessException, MappingException;

    @NotNull
    @Blocking
    public <E> Map<List<?>, E> fetchMap(int[] var1, Class<? extends E> var2) throws DataAccessException, MappingException;

    @NotNull
    @Blocking
    public <E> Map<List<?>, E> fetchMap(String[] var1, Class<? extends E> var2) throws DataAccessException, MappingException;

    @NotNull
    @Blocking
    public <E> Map<List<?>, E> fetchMap(Name[] var1, Class<? extends E> var2) throws DataAccessException, MappingException;

    @NotNull
    @Blocking
    public <E> Map<List<?>, E> fetchMap(Field<?>[] var1, RecordMapper<? super R, E> var2) throws DataAccessException, MappingException;

    @NotNull
    @Blocking
    public <E> Map<List<?>, E> fetchMap(int[] var1, RecordMapper<? super R, E> var2) throws DataAccessException, MappingException;

    @NotNull
    @Blocking
    public <E> Map<List<?>, E> fetchMap(String[] var1, RecordMapper<? super R, E> var2) throws DataAccessException, MappingException;

    @NotNull
    @Blocking
    public <E> Map<List<?>, E> fetchMap(Name[] var1, RecordMapper<? super R, E> var2) throws DataAccessException, MappingException;

    @NotNull
    @Blocking
    public <K> Map<K, R> fetchMap(Class<? extends K> var1) throws DataAccessException, MappingException, InvalidResultException;

    @NotNull
    @Blocking
    public <K, V> Map<K, V> fetchMap(Class<? extends K> var1, Class<? extends V> var2) throws DataAccessException, MappingException, InvalidResultException;

    @NotNull
    @Blocking
    public <K, V> Map<K, V> fetchMap(Class<? extends K> var1, RecordMapper<? super R, V> var2) throws DataAccessException, InvalidResultException, MappingException;

    @NotNull
    @Blocking
    public <K> Map<K, R> fetchMap(RecordMapper<? super R, K> var1) throws DataAccessException, InvalidResultException, MappingException;

    @NotNull
    @Blocking
    public <K, V> Map<K, V> fetchMap(RecordMapper<? super R, K> var1, Class<V> var2) throws DataAccessException, InvalidResultException, MappingException;

    @NotNull
    @Blocking
    public <K, V> Map<K, V> fetchMap(RecordMapper<? super R, K> var1, RecordMapper<? super R, V> var2) throws DataAccessException, InvalidResultException, MappingException;

    @NotNull
    @Blocking
    public <S extends Record> Map<S, R> fetchMap(Table<S> var1) throws DataAccessException;

    @NotNull
    @Blocking
    public <S extends Record, T extends Record> Map<S, T> fetchMap(Table<S> var1, Table<T> var2) throws DataAccessException;

    @NotNull
    @Blocking
    public <E, S extends Record> Map<S, E> fetchMap(Table<S> var1, Class<? extends E> var2) throws DataAccessException, MappingException;

    @NotNull
    @Blocking
    public <E, S extends Record> Map<S, E> fetchMap(Table<S> var1, RecordMapper<? super R, E> var2) throws DataAccessException, MappingException;

    @NotNull
    @Blocking
    public <K, E> Map<K, E> fetchMap(Field<K> var1, Class<? extends E> var2) throws DataAccessException;

    @NotNull
    @Blocking
    public <E> Map<?, E> fetchMap(int var1, Class<? extends E> var2) throws DataAccessException;

    @NotNull
    @Blocking
    public <E> Map<?, E> fetchMap(String var1, Class<? extends E> var2) throws DataAccessException;

    @NotNull
    @Blocking
    public <E> Map<?, E> fetchMap(Name var1, Class<? extends E> var2) throws DataAccessException;

    @NotNull
    @Blocking
    public <K, E> Map<K, E> fetchMap(Field<K> var1, RecordMapper<? super R, E> var2) throws DataAccessException;

    @NotNull
    @Blocking
    public <E> Map<?, E> fetchMap(int var1, RecordMapper<? super R, E> var2) throws DataAccessException;

    @NotNull
    @Blocking
    public <E> Map<?, E> fetchMap(String var1, RecordMapper<? super R, E> var2) throws DataAccessException;

    @NotNull
    @Blocking
    public <E> Map<?, E> fetchMap(Name var1, RecordMapper<? super R, E> var2) throws DataAccessException;

    @NotNull
    @Blocking
    public <K> Map<K, Result<R>> fetchGroups(Field<K> var1) throws DataAccessException;

    @NotNull
    @Blocking
    public Map<?, Result<R>> fetchGroups(int var1) throws DataAccessException;

    @NotNull
    @Blocking
    public Map<?, Result<R>> fetchGroups(String var1) throws DataAccessException;

    @NotNull
    @Blocking
    public Map<?, Result<R>> fetchGroups(Name var1) throws DataAccessException;

    @NotNull
    @Blocking
    public <K, V> Map<K, List<V>> fetchGroups(Field<K> var1, Field<V> var2) throws DataAccessException;

    @NotNull
    @Blocking
    public Map<?, List<?>> fetchGroups(int var1, int var2) throws DataAccessException;

    @NotNull
    @Blocking
    public Map<?, List<?>> fetchGroups(String var1, String var2) throws DataAccessException;

    @NotNull
    @Blocking
    public Map<?, List<?>> fetchGroups(Name var1, Name var2) throws DataAccessException;

    @NotNull
    @Blocking
    public Map<Record, Result<R>> fetchGroups(Field<?>[] var1) throws DataAccessException;

    @NotNull
    @Blocking
    public Map<Record, Result<R>> fetchGroups(int[] var1) throws DataAccessException;

    @NotNull
    @Blocking
    public Map<Record, Result<R>> fetchGroups(String[] var1) throws DataAccessException;

    @NotNull
    @Blocking
    public Map<Record, Result<R>> fetchGroups(Name[] var1) throws DataAccessException;

    @NotNull
    @Blocking
    public Map<Record, Result<Record>> fetchGroups(Field<?>[] var1, Field<?>[] var2) throws DataAccessException;

    @NotNull
    @Blocking
    public Map<Record, Result<Record>> fetchGroups(int[] var1, int[] var2) throws DataAccessException;

    @NotNull
    @Blocking
    public Map<Record, Result<Record>> fetchGroups(String[] var1, String[] var2) throws DataAccessException;

    @NotNull
    @Blocking
    public Map<Record, Result<Record>> fetchGroups(Name[] var1, Name[] var2) throws DataAccessException;

    @NotNull
    @Blocking
    public <E> Map<Record, List<E>> fetchGroups(Field<?>[] var1, Class<? extends E> var2) throws MappingException;

    @NotNull
    @Blocking
    public <E> Map<Record, List<E>> fetchGroups(int[] var1, Class<? extends E> var2) throws MappingException;

    @NotNull
    @Blocking
    public <E> Map<Record, List<E>> fetchGroups(String[] var1, Class<? extends E> var2) throws MappingException;

    @NotNull
    @Blocking
    public <E> Map<Record, List<E>> fetchGroups(Name[] var1, Class<? extends E> var2) throws MappingException;

    @NotNull
    @Blocking
    public <E> Map<Record, List<E>> fetchGroups(Field<?>[] var1, RecordMapper<? super R, E> var2) throws MappingException;

    @NotNull
    @Blocking
    public <E> Map<Record, List<E>> fetchGroups(int[] var1, RecordMapper<? super R, E> var2) throws MappingException;

    @NotNull
    @Blocking
    public <E> Map<Record, List<E>> fetchGroups(String[] var1, RecordMapper<? super R, E> var2) throws MappingException;

    @NotNull
    @Blocking
    public <E> Map<Record, List<E>> fetchGroups(Name[] var1, RecordMapper<? super R, E> var2) throws MappingException;

    @NotNull
    @Blocking
    public <K> Map<K, Result<R>> fetchGroups(Class<? extends K> var1) throws MappingException;

    @NotNull
    @Blocking
    public <K, V> Map<K, List<V>> fetchGroups(Class<? extends K> var1, Class<? extends V> var2) throws MappingException;

    @NotNull
    @Blocking
    public <K, V> Map<K, List<V>> fetchGroups(Class<? extends K> var1, RecordMapper<? super R, V> var2) throws MappingException;

    @NotNull
    @Blocking
    public <K> Map<K, Result<R>> fetchGroups(RecordMapper<? super R, K> var1) throws MappingException;

    @NotNull
    @Blocking
    public <K, V> Map<K, List<V>> fetchGroups(RecordMapper<? super R, K> var1, Class<V> var2) throws MappingException;

    @NotNull
    @Blocking
    public <K, V> Map<K, List<V>> fetchGroups(RecordMapper<? super R, K> var1, RecordMapper<? super R, V> var2) throws MappingException;

    @NotNull
    @Blocking
    public <S extends Record> Map<S, Result<R>> fetchGroups(Table<S> var1) throws DataAccessException;

    @NotNull
    @Blocking
    public <S extends Record, T extends Record> Map<S, Result<T>> fetchGroups(Table<S> var1, Table<T> var2) throws DataAccessException;

    @NotNull
    @Blocking
    public <E, S extends Record> Map<S, List<E>> fetchGroups(Table<S> var1, Class<? extends E> var2) throws DataAccessException, MappingException;

    @NotNull
    @Blocking
    public <E, S extends Record> Map<S, List<E>> fetchGroups(Table<S> var1, RecordMapper<? super R, E> var2) throws DataAccessException, MappingException;

    @NotNull
    @Blocking
    public <K, E> Map<K, List<E>> fetchGroups(Field<K> var1, Class<? extends E> var2) throws DataAccessException, MappingException;

    @NotNull
    @Blocking
    public <E> Map<?, List<E>> fetchGroups(int var1, Class<? extends E> var2) throws DataAccessException, MappingException;

    @NotNull
    @Blocking
    public <E> Map<?, List<E>> fetchGroups(String var1, Class<? extends E> var2) throws DataAccessException, MappingException;

    @NotNull
    @Blocking
    public <E> Map<?, List<E>> fetchGroups(Name var1, Class<? extends E> var2) throws DataAccessException, MappingException;

    @NotNull
    @Blocking
    public <K, E> Map<K, List<E>> fetchGroups(Field<K> var1, RecordMapper<? super R, E> var2) throws DataAccessException, MappingException;

    @NotNull
    @Blocking
    public <E> Map<?, List<E>> fetchGroups(int var1, RecordMapper<? super R, E> var2) throws DataAccessException, MappingException;

    @NotNull
    @Blocking
    public <E> Map<?, List<E>> fetchGroups(String var1, RecordMapper<? super R, E> var2) throws DataAccessException, MappingException;

    @NotNull
    @Blocking
    public <E> Map<?, List<E>> fetchGroups(Name var1, RecordMapper<? super R, E> var2) throws DataAccessException, MappingException;

    @Nullable
    @Blocking
    public @Nullable Object @NotNull [] @NotNull [] fetchArrays() throws DataAccessException;

    @NotNull
    @Blocking
    public @NotNull R @NotNull [] fetchArray() throws DataAccessException;

    @Nullable
    @Blocking
    public @Nullable Object @NotNull [] fetchArray(int var1) throws DataAccessException;

    @Blocking
    public <U> U @NotNull [] fetchArray(int var1, Class<? extends U> var2) throws DataAccessException;

    @Blocking
    public <U> U @NotNull [] fetchArray(int var1, Converter<?, ? extends U> var2) throws DataAccessException;

    @Nullable
    @Blocking
    public @Nullable Object @NotNull [] fetchArray(String var1) throws DataAccessException;

    @Blocking
    public <U> U @NotNull [] fetchArray(String var1, Class<? extends U> var2) throws DataAccessException;

    @Blocking
    public <U> U @NotNull [] fetchArray(String var1, Converter<?, ? extends U> var2) throws DataAccessException;

    @Nullable
    @Blocking
    public @Nullable Object @NotNull [] fetchArray(Name var1) throws DataAccessException;

    @Blocking
    public <U> U @NotNull [] fetchArray(Name var1, Class<? extends U> var2) throws DataAccessException;

    @Blocking
    public <U> U @NotNull [] fetchArray(Name var1, Converter<?, ? extends U> var2) throws DataAccessException;

    @Blocking
    public <T> T @NotNull [] fetchArray(Field<T> var1) throws DataAccessException;

    @Blocking
    public <U> U @NotNull [] fetchArray(Field<?> var1, Class<? extends U> var2) throws DataAccessException;

    @Blocking
    public <T, U> U @NotNull [] fetchArray(Field<T> var1, Converter<? super T, ? extends U> var2) throws DataAccessException;

    @NotNull
    @Blocking
    public <E> Set<E> fetchSet(RecordMapper<? super R, E> var1) throws DataAccessException;

    @NotNull
    @Blocking
    public Set<?> fetchSet(int var1) throws DataAccessException;

    @NotNull
    @Blocking
    public <U> Set<U> fetchSet(int var1, Class<? extends U> var2) throws DataAccessException;

    @NotNull
    @Blocking
    public <U> Set<U> fetchSet(int var1, Converter<?, ? extends U> var2) throws DataAccessException;

    @NotNull
    @Blocking
    public Set<?> fetchSet(String var1) throws DataAccessException;

    @NotNull
    @Blocking
    public <U> Set<U> fetchSet(String var1, Class<? extends U> var2) throws DataAccessException;

    @NotNull
    @Blocking
    public <U> Set<U> fetchSet(String var1, Converter<?, ? extends U> var2) throws DataAccessException;

    @NotNull
    @Blocking
    public Set<?> fetchSet(Name var1) throws DataAccessException;

    @NotNull
    @Blocking
    public <U> Set<U> fetchSet(Name var1, Class<? extends U> var2) throws DataAccessException;

    @NotNull
    @Blocking
    public <U> Set<U> fetchSet(Name var1, Converter<?, ? extends U> var2) throws DataAccessException;

    @NotNull
    @Blocking
    public <T> Set<T> fetchSet(Field<T> var1) throws DataAccessException;

    @NotNull
    @Blocking
    public <U> Set<U> fetchSet(Field<?> var1, Class<? extends U> var2) throws DataAccessException;

    @NotNull
    @Blocking
    public <T, U> Set<U> fetchSet(Field<T> var1, Converter<? super T, ? extends U> var2) throws DataAccessException;

    @NotNull
    @Blocking
    public <E> List<E> fetchInto(Class<? extends E> var1) throws DataAccessException, MappingException;

    @NotNull
    @Blocking
    public <Z extends Record> Result<Z> fetchInto(Table<Z> var1) throws DataAccessException;

    @Deprecated(forRemoval=true, since="3.15")
    @NotNull
    @Blocking
    public <H extends RecordHandler<? super R>> H fetchInto(H var1) throws DataAccessException;

    @NotNull
    @Blocking
    public <E> List<E> fetch(RecordMapper<? super R, E> var1) throws DataAccessException;

    @NotNull
    public CompletionStage<Result<R>> fetchAsync();

    @NotNull
    public CompletionStage<Result<R>> fetchAsync(Executor var1);

    @Nullable
    public Result<R> getResult();

    @NotNull
    public Class<? extends R> getRecordType();

    @Override
    @NotNull
    public ResultQuery<R> bind(String var1, Object var2) throws IllegalArgumentException, DataTypeException;

    @Override
    @NotNull
    public ResultQuery<R> bind(int var1, Object var2) throws IllegalArgumentException, DataTypeException;

    @Override
    @NotNull
    public ResultQuery<R> poolable(boolean var1);

    @Override
    @NotNull
    public ResultQuery<R> queryTimeout(int var1);

    @Override
    @NotNull
    public CloseableResultQuery<R> keepStatement(boolean var1);

    @NotNull
    public ResultQuery<R> maxRows(int var1);

    @NotNull
    public ResultQuery<R> fetchSize(int var1);

    @NotNull
    public ResultQuery<R> resultSetConcurrency(int var1);

    @NotNull
    public ResultQuery<R> resultSetType(int var1);

    @NotNull
    public ResultQuery<R> resultSetHoldability(int var1);

    @Deprecated(forRemoval=true, since="3.10")
    @NotNull
    public ResultQuery<R> intern(Field<?> ... var1);

    @Deprecated(forRemoval=true, since="3.10")
    @NotNull
    public ResultQuery<R> intern(int ... var1);

    @Deprecated(forRemoval=true, since="3.10")
    @NotNull
    public ResultQuery<R> intern(String ... var1);

    @Deprecated(forRemoval=true, since="3.10")
    @NotNull
    public ResultQuery<R> intern(Name ... var1);

    @NotNull
    public <X extends Record> ResultQuery<X> coerce(Table<X> var1);

    @NotNull
    public ResultQuery<Record> coerce(Field<?> ... var1);

    @NotNull
    public ResultQuery<Record> coerce(Collection<? extends Field<?>> var1);

    @NotNull
    public <T1> ResultQuery<Record1<T1>> coerce(Field<T1> var1);

    @NotNull
    public <T1, T2> ResultQuery<Record2<T1, T2>> coerce(Field<T1> var1, Field<T2> var2);

    @NotNull
    public <T1, T2, T3> ResultQuery<Record3<T1, T2, T3>> coerce(Field<T1> var1, Field<T2> var2, Field<T3> var3);

    @NotNull
    public <T1, T2, T3, T4> ResultQuery<Record4<T1, T2, T3, T4>> coerce(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4);

    @NotNull
    public <T1, T2, T3, T4, T5> ResultQuery<Record5<T1, T2, T3, T4, T5>> coerce(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5);

    @NotNull
    public <T1, T2, T3, T4, T5, T6> ResultQuery<Record6<T1, T2, T3, T4, T5, T6>> coerce(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7> ResultQuery<Record7<T1, T2, T3, T4, T5, T6, T7>> coerce(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8> ResultQuery<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> coerce(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> ResultQuery<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> coerce(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> ResultQuery<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> coerce(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> ResultQuery<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> coerce(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> ResultQuery<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> coerce(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> ResultQuery<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> coerce(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> ResultQuery<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> coerce(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> ResultQuery<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> coerce(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> ResultQuery<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> coerce(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> ResultQuery<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> coerce(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> ResultQuery<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> coerce(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> ResultQuery<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> coerce(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18, Field<T19> var19);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> ResultQuery<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> coerce(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18, Field<T19> var19, Field<T20> var20);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> ResultQuery<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> coerce(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18, Field<T19> var19, Field<T20> var20, Field<T21> var21);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> ResultQuery<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> coerce(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18, Field<T19> var19, Field<T20> var20, Field<T21> var21, Field<T22> var22);
}

