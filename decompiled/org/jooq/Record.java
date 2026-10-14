/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Attachable;
import org.jooq.Converter;
import org.jooq.Field;
import org.jooq.Fields;
import org.jooq.Formattable;
import org.jooq.Name;
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
import org.jooq.RecordMapper;
import org.jooq.Row;
import org.jooq.Table;
import org.jooq.exception.DataTypeException;
import org.jooq.exception.MappingException;

public interface Record
extends Fields,
Attachable,
Comparable<Record>,
Formattable {
    @NotNull
    public Row valuesRow();

    public <T> T get(Field<T> var1) throws IllegalArgumentException;

    public <U> U get(Field<?> var1, Class<? extends U> var2) throws IllegalArgumentException, DataTypeException;

    public <T, U> U get(Field<T> var1, Converter<? super T, ? extends U> var2) throws IllegalArgumentException, DataTypeException;

    @Nullable
    public Object get(String var1) throws IllegalArgumentException;

    public <U> U get(String var1, Class<? extends U> var2) throws IllegalArgumentException, DataTypeException;

    public <U> U get(String var1, Converter<?, ? extends U> var2) throws IllegalArgumentException, DataTypeException;

    @Nullable
    public Object get(Name var1) throws IllegalArgumentException;

    public <U> U get(Name var1, Class<? extends U> var2) throws IllegalArgumentException, DataTypeException;

    public <U> U get(Name var1, Converter<?, ? extends U> var2) throws IllegalArgumentException, DataTypeException;

    @Nullable
    public Object get(int var1) throws IllegalArgumentException;

    public <U> U get(int var1, Class<? extends U> var2) throws IllegalArgumentException, DataTypeException;

    public <U> U get(int var1, Converter<?, ? extends U> var2) throws IllegalArgumentException, DataTypeException;

    public <T> void set(Field<T> var1, T var2);

    public <T, U> void set(Field<T> var1, U var2, Converter<? extends T, ? super U> var3);

    @NotNull
    public <T> Record with(Field<T> var1, T var2);

    @NotNull
    public <T, U> Record with(Field<T> var1, U var2, Converter<? extends T, ? super U> var3);

    public int size();

    @NotNull
    public Record original();

    public <T> T original(Field<T> var1);

    @Nullable
    public Object original(int var1);

    @Nullable
    public Object original(String var1);

    @Nullable
    public Object original(Name var1);

    public boolean changed();

    public boolean changed(Field<?> var1);

    public boolean changed(int var1);

    public boolean changed(String var1);

    public boolean changed(Name var1);

    public void changed(boolean var1);

    public void changed(Field<?> var1, boolean var2);

    public void changed(int var1, boolean var2);

    public void changed(String var1, boolean var2);

    public void changed(Name var1, boolean var2);

    public void reset();

    public void reset(Field<?> var1);

    public void reset(int var1);

    public void reset(String var1);

    public void reset(Name var1);

    public @Nullable Object @NotNull [] intoArray();

    @NotNull
    public List<Object> intoList();

    @NotNull
    public Stream<Object> intoStream();

    @NotNull
    public Map<String, Object> intoMap();

    @NotNull
    public Record into(Field<?> ... var1);

    @NotNull
    public <T1> Record1<T1> into(Field<T1> var1);

    @NotNull
    public <T1, T2> Record2<T1, T2> into(Field<T1> var1, Field<T2> var2);

    @NotNull
    public <T1, T2, T3> Record3<T1, T2, T3> into(Field<T1> var1, Field<T2> var2, Field<T3> var3);

    @NotNull
    public <T1, T2, T3, T4> Record4<T1, T2, T3, T4> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4);

    @NotNull
    public <T1, T2, T3, T4, T5> Record5<T1, T2, T3, T4, T5> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5);

    @NotNull
    public <T1, T2, T3, T4, T5, T6> Record6<T1, T2, T3, T4, T5, T6> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7> Record7<T1, T2, T3, T4, T5, T6, T7> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8> Record8<T1, T2, T3, T4, T5, T6, T7, T8> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18, Field<T19> var19);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18, Field<T19> var19, Field<T20> var20);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18, Field<T19> var19, Field<T20> var20, Field<T21> var21);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18, Field<T19> var19, Field<T20> var20, Field<T21> var21, Field<T22> var22);

    public <E> E into(Class<? extends E> var1) throws MappingException;

    @NotNull
    public <E> E into(E var1) throws MappingException;

    @NotNull
    public <R extends Record> R into(Table<R> var1);

    @NotNull
    public ResultSet intoResultSet();

    @NotNull
    public <E> E map(RecordMapper<Record, E> var1);

    public void from(Object var1) throws MappingException;

    public void from(Object var1, Field<?> ... var2) throws MappingException;

    public void from(Object var1, String ... var2) throws MappingException;

    public void from(Object var1, Name ... var2) throws MappingException;

    public void from(Object var1, int ... var2) throws MappingException;

    public void fromMap(Map<String, ?> var1);

    public void fromMap(Map<String, ?> var1, Field<?> ... var2);

    public void fromMap(Map<String, ?> var1, String ... var2);

    public void fromMap(Map<String, ?> var1, Name ... var2);

    public void fromMap(Map<String, ?> var1, int ... var2);

    public void fromArray(Object ... var1);

    public void fromArray(Object[] var1, Field<?> ... var2);

    public void fromArray(Object[] var1, String ... var2);

    public void fromArray(Object[] var1, Name ... var2);

    public void fromArray(Object[] var1, int ... var2);

    public int hashCode();

    public boolean equals(Object var1);

    @Override
    public int compareTo(Record var1);

    public <T> T getValue(Field<T> var1) throws IllegalArgumentException;

    public <T> T getValue(Field<?> var1, Class<? extends T> var2) throws IllegalArgumentException, DataTypeException;

    public <T, U> U getValue(Field<T> var1, Converter<? super T, ? extends U> var2) throws IllegalArgumentException, DataTypeException;

    public Object getValue(String var1) throws IllegalArgumentException;

    public <T> T getValue(String var1, Class<? extends T> var2) throws IllegalArgumentException, DataTypeException;

    public <U> U getValue(String var1, Converter<?, ? extends U> var2) throws IllegalArgumentException, DataTypeException;

    public Object getValue(Name var1) throws IllegalArgumentException;

    public <T> T getValue(Name var1, Class<? extends T> var2) throws IllegalArgumentException, DataTypeException;

    public <U> U getValue(Name var1, Converter<?, ? extends U> var2) throws IllegalArgumentException, DataTypeException;

    public Object getValue(int var1) throws IllegalArgumentException;

    public <T> T getValue(int var1, Class<? extends T> var2) throws IllegalArgumentException, DataTypeException;

    public <U> U getValue(int var1, Converter<?, ? extends U> var2) throws IllegalArgumentException, DataTypeException;

    public <T> void setValue(Field<T> var1, T var2);

    public <T, U> void setValue(Field<T> var1, U var2, Converter<? extends T, ? super U> var3);
}

