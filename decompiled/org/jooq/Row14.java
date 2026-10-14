/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jooq.BetweenAndStep14;
import org.jooq.Comparator;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Function14;
import org.jooq.QuantifiedSelect;
import org.jooq.Record14;
import org.jooq.Result;
import org.jooq.Row;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.SelectField;
import org.jooq.Statement;
import org.jooq.Support;

public interface Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>
extends Row,
SelectField<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> {
    @NotNull
    public <U> SelectField<U> mapping(Function14<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? extends U> var1);

    @NotNull
    public <U> SelectField<U> mapping(Class<U> var1, Function14<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? extends U> var2);

    @NotNull
    public Field<T1> field1();

    @NotNull
    public Field<T2> field2();

    @NotNull
    public Field<T3> field3();

    @NotNull
    public Field<T4> field4();

    @NotNull
    public Field<T5> field5();

    @NotNull
    public Field<T6> field6();

    @NotNull
    public Field<T7> field7();

    @NotNull
    public Field<T8> field8();

    @NotNull
    public Field<T9> field9();

    @NotNull
    public Field<T10> field10();

    @NotNull
    public Field<T11> field11();

    @NotNull
    public Field<T12> field12();

    @NotNull
    public Field<T13> field13();

    @NotNull
    public Field<T14> field14();

    @Support
    @NotNull
    public Condition compare(Comparator var1, Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var2);

    @Support
    @NotNull
    public Condition compare(Comparator var1, Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var2);

    @Support
    @NotNull
    public Condition compare(Comparator var1, T1 var2, T2 var3, T3 var4, T4 var5, T5 var6, T6 var7, T7 var8, T8 var9, T9 var10, T10 var11, T11 var12, T12 var13, T13 var14, T14 var15);

    @Support
    @NotNull
    public Condition compare(Comparator var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15);

    @Support
    @NotNull
    public Condition compare(Comparator var1, Select<? extends Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> var2);

    @Support
    @NotNull
    public Condition compare(Comparator var1, QuantifiedSelect<? extends Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> var2);

    @Support
    @NotNull
    public Condition equal(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1);

    @Support
    @NotNull
    public Condition equal(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1);

    @Support
    @NotNull
    public Condition equal(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10, T11 var11, T12 var12, T13 var13, T14 var14);

    @Support
    @NotNull
    public Condition equal(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14);

    @Support
    @NotNull
    public Condition equal(Select<? extends Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> var1);

    @Support
    @NotNull
    public Condition equal(QuantifiedSelect<? extends Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> var1);

    @Support
    @NotNull
    public Condition eq(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1);

    @Support
    @NotNull
    public Condition eq(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1);

    @Support
    @NotNull
    public Condition eq(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10, T11 var11, T12 var12, T13 var13, T14 var14);

    @Support
    @NotNull
    public Condition eq(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14);

    @Support
    @NotNull
    public Condition eq(Select<? extends Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> var1);

    @Support
    @NotNull
    public Condition eq(QuantifiedSelect<? extends Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> var1);

    @Support
    @NotNull
    public Condition notEqual(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1);

    @Support
    @NotNull
    public Condition notEqual(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1);

    @Support
    @NotNull
    public Condition notEqual(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10, T11 var11, T12 var12, T13 var13, T14 var14);

    @Support
    @NotNull
    public Condition notEqual(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14);

    @Support
    @NotNull
    public Condition notEqual(Select<? extends Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> var1);

    @Support
    @NotNull
    public Condition notEqual(QuantifiedSelect<? extends Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> var1);

    @Support
    @NotNull
    public Condition ne(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1);

    @Support
    @NotNull
    public Condition ne(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1);

    @Support
    @NotNull
    public Condition ne(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10, T11 var11, T12 var12, T13 var13, T14 var14);

    @Support
    @NotNull
    public Condition ne(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14);

    @Support
    @NotNull
    public Condition ne(Select<? extends Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> var1);

    @Support
    @NotNull
    public Condition ne(QuantifiedSelect<? extends Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> var1);

    @Support
    @NotNull
    public Condition isDistinctFrom(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1);

    @Support
    @NotNull
    public Condition isDistinctFrom(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1);

    @Support
    @NotNull
    public Condition isDistinctFrom(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10, T11 var11, T12 var12, T13 var13, T14 var14);

    @Support
    @NotNull
    public Condition isDistinctFrom(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14);

    @Support
    @NotNull
    public Condition isDistinctFrom(Select<? extends Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> var1);

    @Support
    @NotNull
    public Condition isNotDistinctFrom(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1);

    @Support
    @NotNull
    public Condition isNotDistinctFrom(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1);

    @Support
    @NotNull
    public Condition isNotDistinctFrom(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10, T11 var11, T12 var12, T13 var13, T14 var14);

    @Support
    @NotNull
    public Condition isNotDistinctFrom(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14);

    @Support
    @NotNull
    public Condition isNotDistinctFrom(Select<? extends Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> var1);

    @Support
    @NotNull
    public Condition lessThan(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1);

    @Support
    @NotNull
    public Condition lessThan(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1);

    @Support
    @NotNull
    public Condition lessThan(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10, T11 var11, T12 var12, T13 var13, T14 var14);

    @Support
    @NotNull
    public Condition lessThan(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14);

    @Support
    @NotNull
    public Condition lessThan(Select<? extends Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> var1);

    @Support
    @NotNull
    public Condition lessThan(QuantifiedSelect<? extends Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> var1);

    @Support
    @NotNull
    public Condition lt(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1);

    @Support
    @NotNull
    public Condition lt(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1);

    @Support
    @NotNull
    public Condition lt(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10, T11 var11, T12 var12, T13 var13, T14 var14);

    @Support
    @NotNull
    public Condition lt(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14);

    @Support
    @NotNull
    public Condition lt(Select<? extends Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> var1);

    @Support
    @NotNull
    public Condition lt(QuantifiedSelect<? extends Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> var1);

    @Support
    @NotNull
    public Condition lessOrEqual(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1);

    @Support
    @NotNull
    public Condition lessOrEqual(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1);

    @Support
    @NotNull
    public Condition lessOrEqual(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10, T11 var11, T12 var12, T13 var13, T14 var14);

    @Support
    @NotNull
    public Condition lessOrEqual(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14);

    @Support
    @NotNull
    public Condition lessOrEqual(Select<? extends Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> var1);

    @Support
    @NotNull
    public Condition lessOrEqual(QuantifiedSelect<? extends Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> var1);

    @Support
    @NotNull
    public Condition le(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1);

    @Support
    @NotNull
    public Condition le(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1);

    @Support
    @NotNull
    public Condition le(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10, T11 var11, T12 var12, T13 var13, T14 var14);

    @Support
    @NotNull
    public Condition le(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14);

    @Support
    @NotNull
    public Condition le(Select<? extends Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> var1);

    @Support
    @NotNull
    public Condition le(QuantifiedSelect<? extends Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> var1);

    @Support
    @NotNull
    public Condition greaterThan(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1);

    @Support
    @NotNull
    public Condition greaterThan(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1);

    @Support
    @NotNull
    public Condition greaterThan(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10, T11 var11, T12 var12, T13 var13, T14 var14);

    @Support
    @NotNull
    public Condition greaterThan(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14);

    @Support
    @NotNull
    public Condition greaterThan(Select<? extends Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> var1);

    @Support
    @NotNull
    public Condition greaterThan(QuantifiedSelect<? extends Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> var1);

    @Support
    @NotNull
    public Condition gt(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1);

    @Support
    @NotNull
    public Condition gt(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1);

    @Support
    @NotNull
    public Condition gt(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10, T11 var11, T12 var12, T13 var13, T14 var14);

    @Support
    @NotNull
    public Condition gt(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14);

    @Support
    @NotNull
    public Condition gt(Select<? extends Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> var1);

    @Support
    @NotNull
    public Condition gt(QuantifiedSelect<? extends Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> var1);

    @Support
    @NotNull
    public Condition greaterOrEqual(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1);

    @Support
    @NotNull
    public Condition greaterOrEqual(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1);

    @Support
    @NotNull
    public Condition greaterOrEqual(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10, T11 var11, T12 var12, T13 var13, T14 var14);

    @Support
    @NotNull
    public Condition greaterOrEqual(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14);

    @Support
    @NotNull
    public Condition greaterOrEqual(Select<? extends Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> var1);

    @Support
    @NotNull
    public Condition greaterOrEqual(QuantifiedSelect<? extends Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> var1);

    @Support
    @NotNull
    public Condition ge(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1);

    @Support
    @NotNull
    public Condition ge(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1);

    @Support
    @NotNull
    public Condition ge(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10, T11 var11, T12 var12, T13 var13, T14 var14);

    @Support
    @NotNull
    public Condition ge(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14);

    @Support
    @NotNull
    public Condition ge(Select<? extends Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> var1);

    @Support
    @NotNull
    public Condition ge(QuantifiedSelect<? extends Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> var1);

    @Support
    @NotNull
    public BetweenAndStep14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> between(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10, T11 var11, T12 var12, T13 var13, T14 var14);

    @Support
    @NotNull
    public BetweenAndStep14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> between(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14);

    @Support
    @NotNull
    public BetweenAndStep14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> between(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1);

    @Support
    @NotNull
    public BetweenAndStep14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> between(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1);

    @Support
    @NotNull
    public Condition between(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1, Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var2);

    @Support
    @NotNull
    public Condition between(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1, Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var2);

    @Support
    @NotNull
    public BetweenAndStep14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> betweenSymmetric(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10, T11 var11, T12 var12, T13 var13, T14 var14);

    @Support
    @NotNull
    public BetweenAndStep14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> betweenSymmetric(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14);

    @Support
    @NotNull
    public BetweenAndStep14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> betweenSymmetric(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1);

    @Support
    @NotNull
    public BetweenAndStep14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> betweenSymmetric(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1);

    @Support
    @NotNull
    public Condition betweenSymmetric(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1, Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var2);

    @Support
    @NotNull
    public Condition betweenSymmetric(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1, Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var2);

    @Support
    @NotNull
    public BetweenAndStep14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> notBetween(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10, T11 var11, T12 var12, T13 var13, T14 var14);

    @Support
    @NotNull
    public BetweenAndStep14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> notBetween(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14);

    @Support
    @NotNull
    public BetweenAndStep14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> notBetween(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1);

    @Support
    @NotNull
    public BetweenAndStep14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> notBetween(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1);

    @Support
    @NotNull
    public Condition notBetween(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1, Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var2);

    @Support
    @NotNull
    public Condition notBetween(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1, Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var2);

    @Support
    @NotNull
    public BetweenAndStep14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> notBetweenSymmetric(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10, T11 var11, T12 var12, T13 var13, T14 var14);

    @Support
    @NotNull
    public BetweenAndStep14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> notBetweenSymmetric(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14);

    @Support
    @NotNull
    public BetweenAndStep14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> notBetweenSymmetric(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1);

    @Support
    @NotNull
    public BetweenAndStep14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> notBetweenSymmetric(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1);

    @Support
    @NotNull
    public Condition notBetweenSymmetric(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1, Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var2);

    @Support
    @NotNull
    public Condition notBetweenSymmetric(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1, Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var2);

    @Support
    @NotNull
    public Condition in(Collection<? extends Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> var1);

    @Support
    @NotNull
    public Condition in(Result<? extends Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> var1);

    @Support
    @NotNull
    public Condition in(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> ... var1);

    @Support
    @NotNull
    public Condition in(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> ... var1);

    @Support
    @NotNull
    public Condition in(Select<? extends Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> var1);

    @Support
    @NotNull
    public Condition notIn(Collection<? extends Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> var1);

    @Support
    @NotNull
    public Condition notIn(Result<? extends Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> var1);

    @Support
    @NotNull
    public Condition notIn(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> ... var1);

    @Support
    @NotNull
    public Condition notIn(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> ... var1);

    @Support
    @NotNull
    public Condition notIn(Select<? extends Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public Statement set(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public Statement set(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public Statement set(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10, T11 var11, T12 var12, T13 var13, T14 var14);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public Statement set(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public Statement set(Select<? extends Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> var1);
}

