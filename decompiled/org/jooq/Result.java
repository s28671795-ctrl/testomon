/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.sql.ResultSet;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Attachable;
import org.jooq.Configuration;
import org.jooq.Converter;
import org.jooq.Field;
import org.jooq.Fields;
import org.jooq.ForeignKey;
import org.jooq.Formattable;
import org.jooq.Name;
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
import org.jooq.RecordType;
import org.jooq.Table;
import org.jooq.TableRecord;
import org.jooq.UpdatableRecord;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.DataTypeException;
import org.jooq.exception.InvalidResultException;
import org.jooq.exception.MappingException;

public interface Result<R extends Record>
extends Fields,
List<R>,
Attachable,
Formattable {
    @NotNull
    public RecordType<R> recordType();

    public <T> T getValue(int var1, Field<T> var2) throws IndexOutOfBoundsException, IllegalArgumentException;

    @Nullable
    public Object getValue(int var1, int var2) throws IndexOutOfBoundsException, IllegalArgumentException;

    @Nullable
    public Object getValue(int var1, String var2) throws IndexOutOfBoundsException, IllegalArgumentException;

    @NotNull
    public <T> List<T> getValues(Field<T> var1) throws IllegalArgumentException;

    @NotNull
    public <U> List<U> getValues(Field<?> var1, Class<? extends U> var2) throws IllegalArgumentException;

    @NotNull
    public <T, U> List<U> getValues(Field<T> var1, Converter<? super T, ? extends U> var2) throws IllegalArgumentException;

    @NotNull
    public List<?> getValues(int var1) throws IllegalArgumentException;

    @NotNull
    public <U> List<U> getValues(int var1, Class<? extends U> var2) throws IllegalArgumentException, DataTypeException;

    @NotNull
    public <U> List<U> getValues(int var1, Converter<?, ? extends U> var2) throws IllegalArgumentException, DataTypeException;

    @NotNull
    public List<?> getValues(String var1) throws IllegalArgumentException;

    @NotNull
    public <U> List<U> getValues(String var1, Class<? extends U> var2) throws IllegalArgumentException, DataTypeException;

    @NotNull
    public <U> List<U> getValues(String var1, Converter<?, ? extends U> var2) throws IllegalArgumentException, DataTypeException;

    @NotNull
    public List<?> getValues(Name var1) throws IllegalArgumentException;

    @NotNull
    public <U> List<U> getValues(Name var1, Class<? extends U> var2) throws IllegalArgumentException, DataTypeException;

    @NotNull
    public <U> List<U> getValues(Name var1, Converter<?, ? extends U> var2) throws IllegalArgumentException, DataTypeException;

    @Override
    public boolean isEmpty();

    public boolean isNotEmpty();

    public <X, A> X collect(Collector<? super R, A, X> var1);

    @NotNull
    public List<Map<String, Object>> intoMaps();

    @NotNull
    public <K> Map<K, R> intoMap(Field<K> var1) throws IllegalArgumentException, InvalidResultException;

    @NotNull
    public Map<?, R> intoMap(int var1) throws IllegalArgumentException, InvalidResultException;

    @NotNull
    public Map<?, R> intoMap(String var1) throws IllegalArgumentException, InvalidResultException;

    @NotNull
    public Map<?, R> intoMap(Name var1) throws IllegalArgumentException, InvalidResultException;

    @NotNull
    public <K, V> Map<K, V> intoMap(Field<K> var1, Field<V> var2) throws IllegalArgumentException, InvalidResultException;

    @NotNull
    public Map<?, ?> intoMap(int var1, int var2) throws IllegalArgumentException, InvalidResultException;

    @NotNull
    public Map<?, ?> intoMap(String var1, String var2) throws IllegalArgumentException, InvalidResultException;

    @NotNull
    public Map<?, ?> intoMap(Name var1, Name var2) throws IllegalArgumentException, InvalidResultException;

    @NotNull
    public <K, E> Map<K, E> intoMap(Field<K> var1, Class<? extends E> var2) throws IllegalArgumentException, InvalidResultException, MappingException;

    @NotNull
    public <E> Map<?, E> intoMap(int var1, Class<? extends E> var2) throws IllegalArgumentException, InvalidResultException, MappingException;

    @NotNull
    public <E> Map<?, E> intoMap(String var1, Class<? extends E> var2) throws IllegalArgumentException, InvalidResultException, MappingException;

    @NotNull
    public <E> Map<?, E> intoMap(Name var1, Class<? extends E> var2) throws IllegalArgumentException, InvalidResultException, MappingException;

    @NotNull
    public <K, E> Map<K, E> intoMap(Field<K> var1, RecordMapper<? super R, E> var2) throws IllegalArgumentException, InvalidResultException, MappingException;

    @NotNull
    public <E> Map<?, E> intoMap(int var1, RecordMapper<? super R, E> var2) throws IllegalArgumentException, InvalidResultException, MappingException;

    @NotNull
    public <E> Map<?, E> intoMap(String var1, RecordMapper<? super R, E> var2) throws IllegalArgumentException, InvalidResultException, MappingException;

    @NotNull
    public <E> Map<?, E> intoMap(Name var1, RecordMapper<? super R, E> var2) throws IllegalArgumentException, InvalidResultException, MappingException;

    @NotNull
    public Map<Record, R> intoMap(Field<?>[] var1) throws IllegalArgumentException, InvalidResultException;

    @NotNull
    public Map<Record, R> intoMap(int[] var1) throws IllegalArgumentException, InvalidResultException;

    @NotNull
    public Map<Record, R> intoMap(String[] var1) throws IllegalArgumentException, InvalidResultException;

    @NotNull
    public Map<Record, R> intoMap(Name[] var1) throws IllegalArgumentException, InvalidResultException;

    @NotNull
    public Map<Record, Record> intoMap(Field<?>[] var1, Field<?>[] var2) throws IllegalArgumentException, InvalidResultException;

    @NotNull
    public Map<Record, Record> intoMap(int[] var1, int[] var2) throws IllegalArgumentException, InvalidResultException;

    @NotNull
    public Map<Record, Record> intoMap(String[] var1, String[] var2) throws IllegalArgumentException, InvalidResultException;

    @NotNull
    public Map<Record, Record> intoMap(Name[] var1, Name[] var2) throws IllegalArgumentException, InvalidResultException;

    @NotNull
    public <E> Map<List<?>, E> intoMap(Field<?>[] var1, Class<? extends E> var2) throws IllegalArgumentException, InvalidResultException, MappingException;

    @NotNull
    public <E> Map<List<?>, E> intoMap(int[] var1, Class<? extends E> var2) throws IllegalArgumentException, InvalidResultException, MappingException;

    @NotNull
    public <E> Map<List<?>, E> intoMap(String[] var1, Class<? extends E> var2) throws IllegalArgumentException, InvalidResultException, MappingException;

    @NotNull
    public <E> Map<List<?>, E> intoMap(Name[] var1, Class<? extends E> var2) throws IllegalArgumentException, InvalidResultException, MappingException;

    @NotNull
    public <E> Map<List<?>, E> intoMap(Field<?>[] var1, RecordMapper<? super R, E> var2) throws IllegalArgumentException, InvalidResultException, MappingException;

    @NotNull
    public <E> Map<List<?>, E> intoMap(int[] var1, RecordMapper<? super R, E> var2) throws IllegalArgumentException, InvalidResultException, MappingException;

    @NotNull
    public <E> Map<List<?>, E> intoMap(String[] var1, RecordMapper<? super R, E> var2) throws IllegalArgumentException, InvalidResultException, MappingException;

    @NotNull
    public <E> Map<List<?>, E> intoMap(Name[] var1, RecordMapper<? super R, E> var2) throws IllegalArgumentException, InvalidResultException, MappingException;

    @NotNull
    public <K> Map<K, R> intoMap(Class<? extends K> var1) throws MappingException, InvalidResultException;

    @NotNull
    public <K, V> Map<K, V> intoMap(Class<? extends K> var1, Class<? extends V> var2) throws MappingException, InvalidResultException;

    @NotNull
    public <K, V> Map<K, V> intoMap(Class<? extends K> var1, RecordMapper<? super R, V> var2) throws InvalidResultException, MappingException;

    @NotNull
    public <K> Map<K, R> intoMap(RecordMapper<? super R, K> var1) throws InvalidResultException, MappingException;

    @NotNull
    public <K, V> Map<K, V> intoMap(RecordMapper<? super R, K> var1, Class<V> var2) throws InvalidResultException, MappingException;

    @NotNull
    public <K, V> Map<K, V> intoMap(RecordMapper<? super R, K> var1, RecordMapper<? super R, V> var2) throws InvalidResultException, MappingException;

    @NotNull
    public <S extends Record> Map<S, R> intoMap(Table<S> var1) throws IllegalArgumentException, InvalidResultException;

    @NotNull
    public <S extends Record, T extends Record> Map<S, T> intoMap(Table<S> var1, Table<T> var2) throws IllegalArgumentException, InvalidResultException;

    @NotNull
    public <E, S extends Record> Map<S, E> intoMap(Table<S> var1, Class<? extends E> var2) throws IllegalArgumentException, InvalidResultException, MappingException;

    @NotNull
    public <E, S extends Record> Map<S, E> intoMap(Table<S> var1, RecordMapper<? super R, E> var2) throws IllegalArgumentException, InvalidResultException, MappingException;

    @NotNull
    public <K> Map<K, Result<R>> intoGroups(Field<K> var1) throws IllegalArgumentException;

    @NotNull
    public Map<?, Result<R>> intoGroups(int var1) throws IllegalArgumentException;

    @NotNull
    public Map<?, Result<R>> intoGroups(String var1) throws IllegalArgumentException;

    @NotNull
    public Map<?, Result<R>> intoGroups(Name var1) throws IllegalArgumentException;

    @NotNull
    public <K, V> Map<K, List<V>> intoGroups(Field<K> var1, Field<V> var2) throws IllegalArgumentException;

    @NotNull
    public Map<?, List<?>> intoGroups(int var1, int var2) throws IllegalArgumentException;

    @NotNull
    public Map<?, List<?>> intoGroups(String var1, String var2) throws IllegalArgumentException;

    @NotNull
    public Map<?, List<?>> intoGroups(Name var1, Name var2) throws IllegalArgumentException;

    @NotNull
    public <K, E> Map<K, List<E>> intoGroups(Field<K> var1, Class<? extends E> var2) throws IllegalArgumentException, MappingException;

    @NotNull
    public <E> Map<?, List<E>> intoGroups(int var1, Class<? extends E> var2) throws IllegalArgumentException, MappingException;

    @NotNull
    public <E> Map<?, List<E>> intoGroups(String var1, Class<? extends E> var2) throws IllegalArgumentException, MappingException;

    @NotNull
    public <E> Map<?, List<E>> intoGroups(Name var1, Class<? extends E> var2) throws IllegalArgumentException, MappingException;

    @NotNull
    public <K, E> Map<K, List<E>> intoGroups(Field<K> var1, RecordMapper<? super R, E> var2) throws IllegalArgumentException, MappingException;

    @NotNull
    public <E> Map<?, List<E>> intoGroups(int var1, RecordMapper<? super R, E> var2) throws IllegalArgumentException, MappingException;

    @NotNull
    public <E> Map<?, List<E>> intoGroups(String var1, RecordMapper<? super R, E> var2) throws IllegalArgumentException, MappingException;

    @NotNull
    public <E> Map<?, List<E>> intoGroups(Name var1, RecordMapper<? super R, E> var2) throws IllegalArgumentException, MappingException;

    @NotNull
    public Map<Record, Result<R>> intoGroups(Field<?>[] var1) throws IllegalArgumentException;

    @NotNull
    public Map<Record, Result<R>> intoGroups(int[] var1) throws IllegalArgumentException;

    @NotNull
    public Map<Record, Result<R>> intoGroups(String[] var1) throws IllegalArgumentException;

    @NotNull
    public Map<Record, Result<R>> intoGroups(Name[] var1) throws IllegalArgumentException;

    @NotNull
    public Map<Record, Result<Record>> intoGroups(Field<?>[] var1, Field<?>[] var2) throws IllegalArgumentException;

    @NotNull
    public Map<Record, Result<Record>> intoGroups(int[] var1, int[] var2) throws IllegalArgumentException;

    @NotNull
    public Map<Record, Result<Record>> intoGroups(String[] var1, String[] var2) throws IllegalArgumentException;

    @NotNull
    public Map<Record, Result<Record>> intoGroups(Name[] var1, Name[] var2) throws IllegalArgumentException;

    @NotNull
    public <E> Map<Record, List<E>> intoGroups(Field<?>[] var1, Class<? extends E> var2) throws IllegalArgumentException, MappingException;

    @NotNull
    public <E> Map<Record, List<E>> intoGroups(int[] var1, Class<? extends E> var2) throws IllegalArgumentException, MappingException;

    @NotNull
    public <E> Map<Record, List<E>> intoGroups(String[] var1, Class<? extends E> var2) throws IllegalArgumentException, MappingException;

    @NotNull
    public <E> Map<Record, List<E>> intoGroups(Name[] var1, Class<? extends E> var2) throws IllegalArgumentException, MappingException;

    @NotNull
    public <E> Map<Record, List<E>> intoGroups(Field<?>[] var1, RecordMapper<? super R, E> var2) throws IllegalArgumentException, MappingException;

    @NotNull
    public <E> Map<Record, List<E>> intoGroups(int[] var1, RecordMapper<? super R, E> var2) throws IllegalArgumentException, MappingException;

    @NotNull
    public <E> Map<Record, List<E>> intoGroups(String[] var1, RecordMapper<? super R, E> var2) throws IllegalArgumentException, MappingException;

    @NotNull
    public <E> Map<Record, List<E>> intoGroups(Name[] var1, RecordMapper<? super R, E> var2) throws IllegalArgumentException, MappingException;

    @NotNull
    public <K> Map<K, Result<R>> intoGroups(Class<? extends K> var1) throws MappingException;

    @NotNull
    public <K, V> Map<K, List<V>> intoGroups(Class<? extends K> var1, Class<? extends V> var2) throws MappingException;

    @NotNull
    public <K, V> Map<K, List<V>> intoGroups(Class<? extends K> var1, RecordMapper<? super R, V> var2) throws MappingException;

    @NotNull
    public <K> Map<K, Result<R>> intoGroups(RecordMapper<? super R, K> var1) throws MappingException;

    @NotNull
    public <K, V> Map<K, List<V>> intoGroups(RecordMapper<? super R, K> var1, Class<V> var2) throws MappingException;

    @NotNull
    public <K, V> Map<K, List<V>> intoGroups(RecordMapper<? super R, K> var1, RecordMapper<? super R, V> var2) throws MappingException;

    @NotNull
    public <S extends Record> Map<S, Result<R>> intoGroups(Table<S> var1) throws IllegalArgumentException;

    @NotNull
    public <S extends Record, T extends Record> Map<S, Result<T>> intoGroups(Table<S> var1, Table<T> var2) throws IllegalArgumentException;

    @NotNull
    public <E, S extends Record> Map<S, List<E>> intoGroups(Table<S> var1, Class<? extends E> var2) throws IllegalArgumentException, MappingException;

    @NotNull
    public <E, S extends Record> Map<S, List<E>> intoGroups(Table<S> var1, RecordMapper<? super R, E> var2) throws IllegalArgumentException, MappingException;

    public @Nullable Object @NotNull [] @NotNull [] intoArrays();

    public @Nullable Object @NotNull [] intoArray(int var1) throws IllegalArgumentException;

    public <U> U @NotNull [] intoArray(int var1, Class<? extends U> var2) throws IllegalArgumentException, DataTypeException;

    public <U> U @NotNull [] intoArray(int var1, Converter<?, ? extends U> var2) throws IllegalArgumentException, DataTypeException;

    public @Nullable Object @NotNull [] intoArray(String var1) throws IllegalArgumentException;

    public <U> U @NotNull [] intoArray(String var1, Class<? extends U> var2) throws IllegalArgumentException, DataTypeException;

    public <U> U @NotNull [] intoArray(String var1, Converter<?, ? extends U> var2) throws IllegalArgumentException, DataTypeException;

    public @Nullable Object @NotNull [] intoArray(Name var1) throws IllegalArgumentException;

    public <U> U @NotNull [] intoArray(Name var1, Class<? extends U> var2) throws IllegalArgumentException, DataTypeException;

    public <U> U @NotNull [] intoArray(Name var1, Converter<?, ? extends U> var2) throws IllegalArgumentException, DataTypeException;

    public <T> T @NotNull [] intoArray(Field<T> var1) throws IllegalArgumentException;

    public <U> U @NotNull [] intoArray(Field<?> var1, Class<? extends U> var2) throws IllegalArgumentException, DataTypeException;

    public <T, U> U @NotNull [] intoArray(Field<T> var1, Converter<? super T, ? extends U> var2) throws IllegalArgumentException, DataTypeException;

    @NotNull
    public <E> Set<E> intoSet(RecordMapper<? super R, E> var1);

    @NotNull
    public Set<?> intoSet(int var1) throws IllegalArgumentException;

    @NotNull
    public <U> Set<U> intoSet(int var1, Class<? extends U> var2) throws IllegalArgumentException, DataTypeException;

    @NotNull
    public <U> Set<U> intoSet(int var1, Converter<?, ? extends U> var2) throws IllegalArgumentException, DataTypeException;

    @NotNull
    public Set<?> intoSet(String var1) throws IllegalArgumentException;

    @NotNull
    public <U> Set<U> intoSet(String var1, Class<? extends U> var2) throws IllegalArgumentException, DataTypeException;

    @NotNull
    public <U> Set<U> intoSet(String var1, Converter<?, ? extends U> var2) throws IllegalArgumentException, DataTypeException;

    @NotNull
    public Set<?> intoSet(Name var1) throws IllegalArgumentException;

    @NotNull
    public <U> Set<U> intoSet(Name var1, Class<? extends U> var2) throws IllegalArgumentException, DataTypeException;

    @NotNull
    public <U> Set<U> intoSet(Name var1, Converter<?, ? extends U> var2) throws IllegalArgumentException, DataTypeException;

    @NotNull
    public <T> Set<T> intoSet(Field<T> var1) throws IllegalArgumentException;

    @NotNull
    public <U> Set<U> intoSet(Field<?> var1, Class<? extends U> var2) throws IllegalArgumentException, DataTypeException;

    @NotNull
    public <T, U> Set<U> intoSet(Field<T> var1, Converter<? super T, ? extends U> var2) throws IllegalArgumentException, DataTypeException;

    @NotNull
    public Result<Record> into(Field<?> ... var1);

    @NotNull
    public <T1> Result<Record1<T1>> into(Field<T1> var1);

    @NotNull
    public <T1, T2> Result<Record2<T1, T2>> into(Field<T1> var1, Field<T2> var2);

    @NotNull
    public <T1, T2, T3> Result<Record3<T1, T2, T3>> into(Field<T1> var1, Field<T2> var2, Field<T3> var3);

    @NotNull
    public <T1, T2, T3, T4> Result<Record4<T1, T2, T3, T4>> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4);

    @NotNull
    public <T1, T2, T3, T4, T5> Result<Record5<T1, T2, T3, T4, T5>> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5);

    @NotNull
    public <T1, T2, T3, T4, T5, T6> Result<Record6<T1, T2, T3, T4, T5, T6>> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7> Result<Record7<T1, T2, T3, T4, T5, T6, T7>> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8> Result<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> Result<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Result<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Result<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Result<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Result<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Result<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Result<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Result<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> Result<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> Result<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> Result<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18, Field<T19> var19);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> Result<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18, Field<T19> var19, Field<T20> var20);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> Result<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18, Field<T19> var19, Field<T20> var20, Field<T21> var21);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> Result<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18, Field<T19> var19, Field<T20> var20, Field<T21> var21, Field<T22> var22);

    @NotNull
    public <E> List<E> into(Class<? extends E> var1) throws MappingException;

    @NotNull
    public <Z extends Record> Result<Z> into(Table<Z> var1) throws MappingException;

    @Deprecated(forRemoval=true, since="3.15")
    @NotNull
    public <H extends RecordHandler<? super R>> H into(H var1);

    @NotNull
    public ResultSet intoResultSet();

    @NotNull
    public <E> List<E> map(RecordMapper<? super R, E> var1);

    @NotNull
    public <T extends Comparable<? super T>> Result<R> sortAsc(Field<T> var1) throws IllegalArgumentException;

    @NotNull
    public <T extends Comparable<? super T>> Result<R> sortDesc(Field<T> var1) throws IllegalArgumentException;

    @NotNull
    public Result<R> sortAsc(int var1) throws IllegalArgumentException;

    @NotNull
    public Result<R> sortDesc(int var1) throws IllegalArgumentException;

    @NotNull
    public Result<R> sortAsc(String var1) throws IllegalArgumentException;

    @NotNull
    public Result<R> sortDesc(String var1) throws IllegalArgumentException;

    @NotNull
    public Result<R> sortAsc(Name var1) throws IllegalArgumentException;

    @NotNull
    public Result<R> sortDesc(Name var1) throws IllegalArgumentException;

    @NotNull
    public <T> Result<R> sortAsc(Field<T> var1, Comparator<? super T> var2) throws IllegalArgumentException;

    @NotNull
    public <T> Result<R> sortDesc(Field<T> var1, Comparator<? super T> var2) throws IllegalArgumentException;

    @NotNull
    public Result<R> sortAsc(int var1, Comparator<?> var2) throws IllegalArgumentException;

    @NotNull
    public Result<R> sortDesc(int var1, Comparator<?> var2) throws IllegalArgumentException;

    @NotNull
    public Result<R> sortAsc(String var1, Comparator<?> var2) throws IllegalArgumentException;

    @NotNull
    public Result<R> sortDesc(String var1, Comparator<?> var2) throws IllegalArgumentException;

    @NotNull
    public Result<R> sortAsc(Name var1, Comparator<?> var2) throws IllegalArgumentException;

    @NotNull
    public Result<R> sortDesc(Name var1, Comparator<?> var2) throws IllegalArgumentException;

    @NotNull
    public Result<R> sortAsc(Comparator<? super R> var1);

    @NotNull
    public Result<R> sortDesc(Comparator<? super R> var1);

    @Deprecated(forRemoval=true, since="3.10")
    @NotNull
    public Result<R> intern(Field<?> ... var1);

    @Deprecated(forRemoval=true, since="3.10")
    @NotNull
    public Result<R> intern(int ... var1);

    @Deprecated(forRemoval=true, since="3.10")
    @NotNull
    public Result<R> intern(String ... var1);

    @Deprecated(forRemoval=true, since="3.10")
    @NotNull
    public Result<R> intern(Name ... var1);

    @NotNull
    @Blocking
    public <O extends UpdatableRecord<O>> Result<O> fetchParents(ForeignKey<R, O> var1) throws DataAccessException;

    @NotNull
    @Blocking
    public <O extends TableRecord<O>> Result<O> fetchChildren(ForeignKey<O, R> var1) throws DataAccessException;

    @NotNull
    public <O extends UpdatableRecord<O>> Table<O> parents(ForeignKey<R, O> var1);

    @NotNull
    public <O extends TableRecord<O>> Table<O> children(ForeignKey<O, R> var1);

    @Override
    public void attach(Configuration var1);

    @Override
    public void detach();
}

