/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jooq.CloseableQuery;
import org.jooq.Field;
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
import org.jooq.ResultQuery;
import org.jooq.Table;
import org.jooq.exception.DataTypeException;

public interface CloseableResultQuery<R extends Record>
extends ResultQuery<R>,
CloseableQuery {
    @Override
    @NotNull
    public CloseableResultQuery<R> bind(String var1, Object var2) throws IllegalArgumentException, DataTypeException;

    @Override
    @NotNull
    public CloseableResultQuery<R> bind(int var1, Object var2) throws IllegalArgumentException, DataTypeException;

    @Override
    @NotNull
    public CloseableResultQuery<R> poolable(boolean var1);

    @Override
    @NotNull
    public CloseableResultQuery<R> queryTimeout(int var1);

    @Override
    @NotNull
    public CloseableResultQuery<R> keepStatement(boolean var1);

    @Override
    @NotNull
    public CloseableResultQuery<R> maxRows(int var1);

    @Override
    @NotNull
    public CloseableResultQuery<R> fetchSize(int var1);

    @Override
    @NotNull
    public CloseableResultQuery<R> resultSetConcurrency(int var1);

    @Override
    @NotNull
    public CloseableResultQuery<R> resultSetType(int var1);

    @Override
    @NotNull
    public CloseableResultQuery<R> resultSetHoldability(int var1);

    @Override
    @Deprecated(forRemoval=true, since="3.10")
    @NotNull
    public CloseableResultQuery<R> intern(Field<?> ... var1);

    @Override
    @Deprecated(forRemoval=true, since="3.10")
    @NotNull
    public CloseableResultQuery<R> intern(int ... var1);

    @Override
    @Deprecated(forRemoval=true, since="3.10")
    @NotNull
    public CloseableResultQuery<R> intern(String ... var1);

    @Override
    @Deprecated(forRemoval=true, since="3.10")
    @NotNull
    public CloseableResultQuery<R> intern(Name ... var1);

    @Override
    @NotNull
    public <X extends Record> CloseableResultQuery<X> coerce(Table<X> var1);

    @NotNull
    public CloseableResultQuery<Record> coerce(Field<?> ... var1);

    @Override
    @NotNull
    public CloseableResultQuery<Record> coerce(Collection<? extends Field<?>> var1);

    @Override
    @NotNull
    public <T1> CloseableResultQuery<Record1<T1>> coerce(Field<T1> var1);

    @Override
    @NotNull
    public <T1, T2> CloseableResultQuery<Record2<T1, T2>> coerce(Field<T1> var1, Field<T2> var2);

    @Override
    @NotNull
    public <T1, T2, T3> CloseableResultQuery<Record3<T1, T2, T3>> coerce(Field<T1> var1, Field<T2> var2, Field<T3> var3);

    @Override
    @NotNull
    public <T1, T2, T3, T4> CloseableResultQuery<Record4<T1, T2, T3, T4>> coerce(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4);

    @Override
    @NotNull
    public <T1, T2, T3, T4, T5> CloseableResultQuery<Record5<T1, T2, T3, T4, T5>> coerce(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5);

    @Override
    @NotNull
    public <T1, T2, T3, T4, T5, T6> CloseableResultQuery<Record6<T1, T2, T3, T4, T5, T6>> coerce(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6);

    @Override
    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7> CloseableResultQuery<Record7<T1, T2, T3, T4, T5, T6, T7>> coerce(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7);

    @Override
    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8> CloseableResultQuery<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> coerce(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

    @Override
    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> CloseableResultQuery<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> coerce(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9);

    @Override
    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> CloseableResultQuery<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> coerce(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10);

    @Override
    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> CloseableResultQuery<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> coerce(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11);

    @Override
    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> CloseableResultQuery<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> coerce(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12);

    @Override
    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> CloseableResultQuery<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> coerce(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13);

    @Override
    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> CloseableResultQuery<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> coerce(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14);

    @Override
    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> CloseableResultQuery<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> coerce(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15);

    @Override
    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> CloseableResultQuery<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> coerce(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16);

    @Override
    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> CloseableResultQuery<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> coerce(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17);

    @Override
    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> CloseableResultQuery<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> coerce(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18);

    @Override
    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> CloseableResultQuery<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> coerce(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18, Field<T19> var19);

    @Override
    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> CloseableResultQuery<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> coerce(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18, Field<T19> var19, Field<T20> var20);

    @Override
    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> CloseableResultQuery<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> coerce(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18, Field<T19> var19, Field<T20> var20, Field<T21> var21);

    @Override
    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> CloseableResultQuery<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> coerce(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18, Field<T19> var19, Field<T20> var20, Field<T21> var21, Field<T22> var22);
}

