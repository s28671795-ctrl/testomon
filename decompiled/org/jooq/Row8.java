/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jooq.BetweenAndStep8;
import org.jooq.Comparator;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Function8;
import org.jooq.QuantifiedSelect;
import org.jooq.Record8;
import org.jooq.Result;
import org.jooq.Row;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.SelectField;
import org.jooq.Statement;
import org.jooq.Support;

public interface Row8<T1, T2, T3, T4, T5, T6, T7, T8>
extends Row,
SelectField<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> {
    @NotNull
    public <U> SelectField<U> mapping(Function8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? extends U> var1);

    @NotNull
    public <U> SelectField<U> mapping(Class<U> var1, Function8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? extends U> var2);

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

    @Support
    @NotNull
    public Condition compare(Comparator var1, Row8<T1, T2, T3, T4, T5, T6, T7, T8> var2);

    @Support
    @NotNull
    public Condition compare(Comparator var1, Record8<T1, T2, T3, T4, T5, T6, T7, T8> var2);

    @Support
    @NotNull
    public Condition compare(Comparator var1, T1 var2, T2 var3, T3 var4, T4 var5, T5 var6, T6 var7, T7 var8, T8 var9);

    @Support
    @NotNull
    public Condition compare(Comparator var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9);

    @Support
    @NotNull
    public Condition compare(Comparator var1, Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var2);

    @Support
    @NotNull
    public Condition compare(Comparator var1, QuantifiedSelect<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var2);

    @Support
    @NotNull
    public Condition equal(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

    @Support
    @NotNull
    public Condition equal(Record8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

    @Support
    @NotNull
    public Condition equal(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8);

    @Support
    @NotNull
    public Condition equal(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

    @Support
    @NotNull
    public Condition equal(Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

    @Support
    @NotNull
    public Condition equal(QuantifiedSelect<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

    @Support
    @NotNull
    public Condition eq(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

    @Support
    @NotNull
    public Condition eq(Record8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

    @Support
    @NotNull
    public Condition eq(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8);

    @Support
    @NotNull
    public Condition eq(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

    @Support
    @NotNull
    public Condition eq(Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

    @Support
    @NotNull
    public Condition eq(QuantifiedSelect<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

    @Support
    @NotNull
    public Condition notEqual(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

    @Support
    @NotNull
    public Condition notEqual(Record8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

    @Support
    @NotNull
    public Condition notEqual(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8);

    @Support
    @NotNull
    public Condition notEqual(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

    @Support
    @NotNull
    public Condition notEqual(Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

    @Support
    @NotNull
    public Condition notEqual(QuantifiedSelect<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

    @Support
    @NotNull
    public Condition ne(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

    @Support
    @NotNull
    public Condition ne(Record8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

    @Support
    @NotNull
    public Condition ne(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8);

    @Support
    @NotNull
    public Condition ne(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

    @Support
    @NotNull
    public Condition ne(Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

    @Support
    @NotNull
    public Condition ne(QuantifiedSelect<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

    @Support
    @NotNull
    public Condition isDistinctFrom(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

    @Support
    @NotNull
    public Condition isDistinctFrom(Record8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

    @Support
    @NotNull
    public Condition isDistinctFrom(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8);

    @Support
    @NotNull
    public Condition isDistinctFrom(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

    @Support
    @NotNull
    public Condition isDistinctFrom(Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

    @Support
    @NotNull
    public Condition isNotDistinctFrom(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

    @Support
    @NotNull
    public Condition isNotDistinctFrom(Record8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

    @Support
    @NotNull
    public Condition isNotDistinctFrom(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8);

    @Support
    @NotNull
    public Condition isNotDistinctFrom(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

    @Support
    @NotNull
    public Condition isNotDistinctFrom(Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

    @Support
    @NotNull
    public Condition lessThan(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

    @Support
    @NotNull
    public Condition lessThan(Record8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

    @Support
    @NotNull
    public Condition lessThan(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8);

    @Support
    @NotNull
    public Condition lessThan(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

    @Support
    @NotNull
    public Condition lessThan(Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

    @Support
    @NotNull
    public Condition lessThan(QuantifiedSelect<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

    @Support
    @NotNull
    public Condition lt(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

    @Support
    @NotNull
    public Condition lt(Record8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

    @Support
    @NotNull
    public Condition lt(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8);

    @Support
    @NotNull
    public Condition lt(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

    @Support
    @NotNull
    public Condition lt(Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

    @Support
    @NotNull
    public Condition lt(QuantifiedSelect<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

    @Support
    @NotNull
    public Condition lessOrEqual(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

    @Support
    @NotNull
    public Condition lessOrEqual(Record8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

    @Support
    @NotNull
    public Condition lessOrEqual(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8);

    @Support
    @NotNull
    public Condition lessOrEqual(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

    @Support
    @NotNull
    public Condition lessOrEqual(Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

    @Support
    @NotNull
    public Condition lessOrEqual(QuantifiedSelect<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

    @Support
    @NotNull
    public Condition le(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

    @Support
    @NotNull
    public Condition le(Record8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

    @Support
    @NotNull
    public Condition le(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8);

    @Support
    @NotNull
    public Condition le(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

    @Support
    @NotNull
    public Condition le(Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

    @Support
    @NotNull
    public Condition le(QuantifiedSelect<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

    @Support
    @NotNull
    public Condition greaterThan(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

    @Support
    @NotNull
    public Condition greaterThan(Record8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

    @Support
    @NotNull
    public Condition greaterThan(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8);

    @Support
    @NotNull
    public Condition greaterThan(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

    @Support
    @NotNull
    public Condition greaterThan(Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

    @Support
    @NotNull
    public Condition greaterThan(QuantifiedSelect<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

    @Support
    @NotNull
    public Condition gt(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

    @Support
    @NotNull
    public Condition gt(Record8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

    @Support
    @NotNull
    public Condition gt(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8);

    @Support
    @NotNull
    public Condition gt(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

    @Support
    @NotNull
    public Condition gt(Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

    @Support
    @NotNull
    public Condition gt(QuantifiedSelect<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

    @Support
    @NotNull
    public Condition greaterOrEqual(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

    @Support
    @NotNull
    public Condition greaterOrEqual(Record8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

    @Support
    @NotNull
    public Condition greaterOrEqual(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8);

    @Support
    @NotNull
    public Condition greaterOrEqual(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

    @Support
    @NotNull
    public Condition greaterOrEqual(Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

    @Support
    @NotNull
    public Condition greaterOrEqual(QuantifiedSelect<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

    @Support
    @NotNull
    public Condition ge(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

    @Support
    @NotNull
    public Condition ge(Record8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

    @Support
    @NotNull
    public Condition ge(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8);

    @Support
    @NotNull
    public Condition ge(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

    @Support
    @NotNull
    public Condition ge(Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

    @Support
    @NotNull
    public Condition ge(QuantifiedSelect<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

    @Support
    @NotNull
    public BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> between(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8);

    @Support
    @NotNull
    public BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> between(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

    @Support
    @NotNull
    public BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> between(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

    @Support
    @NotNull
    public BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> between(Record8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

    @Support
    @NotNull
    public Condition between(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1, Row8<T1, T2, T3, T4, T5, T6, T7, T8> var2);

    @Support
    @NotNull
    public Condition between(Record8<T1, T2, T3, T4, T5, T6, T7, T8> var1, Record8<T1, T2, T3, T4, T5, T6, T7, T8> var2);

    @Support
    @NotNull
    public BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> betweenSymmetric(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8);

    @Support
    @NotNull
    public BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> betweenSymmetric(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

    @Support
    @NotNull
    public BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> betweenSymmetric(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

    @Support
    @NotNull
    public BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> betweenSymmetric(Record8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

    @Support
    @NotNull
    public Condition betweenSymmetric(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1, Row8<T1, T2, T3, T4, T5, T6, T7, T8> var2);

    @Support
    @NotNull
    public Condition betweenSymmetric(Record8<T1, T2, T3, T4, T5, T6, T7, T8> var1, Record8<T1, T2, T3, T4, T5, T6, T7, T8> var2);

    @Support
    @NotNull
    public BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> notBetween(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8);

    @Support
    @NotNull
    public BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> notBetween(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

    @Support
    @NotNull
    public BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> notBetween(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

    @Support
    @NotNull
    public BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> notBetween(Record8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

    @Support
    @NotNull
    public Condition notBetween(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1, Row8<T1, T2, T3, T4, T5, T6, T7, T8> var2);

    @Support
    @NotNull
    public Condition notBetween(Record8<T1, T2, T3, T4, T5, T6, T7, T8> var1, Record8<T1, T2, T3, T4, T5, T6, T7, T8> var2);

    @Support
    @NotNull
    public BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> notBetweenSymmetric(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8);

    @Support
    @NotNull
    public BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> notBetweenSymmetric(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

    @Support
    @NotNull
    public BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> notBetweenSymmetric(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

    @Support
    @NotNull
    public BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> notBetweenSymmetric(Record8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

    @Support
    @NotNull
    public Condition notBetweenSymmetric(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1, Row8<T1, T2, T3, T4, T5, T6, T7, T8> var2);

    @Support
    @NotNull
    public Condition notBetweenSymmetric(Record8<T1, T2, T3, T4, T5, T6, T7, T8> var1, Record8<T1, T2, T3, T4, T5, T6, T7, T8> var2);

    @Support
    @NotNull
    public Condition in(Collection<? extends Row8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

    @Support
    @NotNull
    public Condition in(Result<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

    @Support
    @NotNull
    public Condition in(Row8<T1, T2, T3, T4, T5, T6, T7, T8> ... var1);

    @Support
    @NotNull
    public Condition in(Record8<T1, T2, T3, T4, T5, T6, T7, T8> ... var1);

    @Support
    @NotNull
    public Condition in(Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

    @Support
    @NotNull
    public Condition notIn(Collection<? extends Row8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

    @Support
    @NotNull
    public Condition notIn(Result<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

    @Support
    @NotNull
    public Condition notIn(Row8<T1, T2, T3, T4, T5, T6, T7, T8> ... var1);

    @Support
    @NotNull
    public Condition notIn(Record8<T1, T2, T3, T4, T5, T6, T7, T8> ... var1);

    @Support
    @NotNull
    public Condition notIn(Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public Statement set(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public Statement set(Record8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public Statement set(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public Statement set(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public Statement set(Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);
}

