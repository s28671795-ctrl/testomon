/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.OrderField;
import org.jooq.Pro;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.SelectLimitStep;
import org.jooq.SelectSeekStep1;
import org.jooq.SelectSeekStep10;
import org.jooq.SelectSeekStep11;
import org.jooq.SelectSeekStep12;
import org.jooq.SelectSeekStep13;
import org.jooq.SelectSeekStep14;
import org.jooq.SelectSeekStep15;
import org.jooq.SelectSeekStep16;
import org.jooq.SelectSeekStep17;
import org.jooq.SelectSeekStep18;
import org.jooq.SelectSeekStep19;
import org.jooq.SelectSeekStep2;
import org.jooq.SelectSeekStep20;
import org.jooq.SelectSeekStep21;
import org.jooq.SelectSeekStep22;
import org.jooq.SelectSeekStep3;
import org.jooq.SelectSeekStep4;
import org.jooq.SelectSeekStep5;
import org.jooq.SelectSeekStep6;
import org.jooq.SelectSeekStep7;
import org.jooq.SelectSeekStep8;
import org.jooq.SelectSeekStep9;
import org.jooq.SelectSeekStepN;
import org.jooq.Support;

public interface SelectOrderByStep<R extends Record>
extends SelectLimitStep<R> {
    @Support
    @NotNull
    @CheckReturnValue
    public <T1> SelectSeekStep1<R, T1> orderBy(OrderField<T1> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2> SelectSeekStep2<R, T1, T2> orderBy(OrderField<T1> var1, OrderField<T2> var2);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3> SelectSeekStep3<R, T1, T2, T3> orderBy(OrderField<T1> var1, OrderField<T2> var2, OrderField<T3> var3);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4> SelectSeekStep4<R, T1, T2, T3, T4> orderBy(OrderField<T1> var1, OrderField<T2> var2, OrderField<T3> var3, OrderField<T4> var4);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5> SelectSeekStep5<R, T1, T2, T3, T4, T5> orderBy(OrderField<T1> var1, OrderField<T2> var2, OrderField<T3> var3, OrderField<T4> var4, OrderField<T5> var5);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6> SelectSeekStep6<R, T1, T2, T3, T4, T5, T6> orderBy(OrderField<T1> var1, OrderField<T2> var2, OrderField<T3> var3, OrderField<T4> var4, OrderField<T5> var5, OrderField<T6> var6);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7> SelectSeekStep7<R, T1, T2, T3, T4, T5, T6, T7> orderBy(OrderField<T1> var1, OrderField<T2> var2, OrderField<T3> var3, OrderField<T4> var4, OrderField<T5> var5, OrderField<T6> var6, OrderField<T7> var7);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8> SelectSeekStep8<R, T1, T2, T3, T4, T5, T6, T7, T8> orderBy(OrderField<T1> var1, OrderField<T2> var2, OrderField<T3> var3, OrderField<T4> var4, OrderField<T5> var5, OrderField<T6> var6, OrderField<T7> var7, OrderField<T8> var8);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> SelectSeekStep9<R, T1, T2, T3, T4, T5, T6, T7, T8, T9> orderBy(OrderField<T1> var1, OrderField<T2> var2, OrderField<T3> var3, OrderField<T4> var4, OrderField<T5> var5, OrderField<T6> var6, OrderField<T7> var7, OrderField<T8> var8, OrderField<T9> var9);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> SelectSeekStep10<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> orderBy(OrderField<T1> var1, OrderField<T2> var2, OrderField<T3> var3, OrderField<T4> var4, OrderField<T5> var5, OrderField<T6> var6, OrderField<T7> var7, OrderField<T8> var8, OrderField<T9> var9, OrderField<T10> var10);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> SelectSeekStep11<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> orderBy(OrderField<T1> var1, OrderField<T2> var2, OrderField<T3> var3, OrderField<T4> var4, OrderField<T5> var5, OrderField<T6> var6, OrderField<T7> var7, OrderField<T8> var8, OrderField<T9> var9, OrderField<T10> var10, OrderField<T11> var11);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> SelectSeekStep12<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> orderBy(OrderField<T1> var1, OrderField<T2> var2, OrderField<T3> var3, OrderField<T4> var4, OrderField<T5> var5, OrderField<T6> var6, OrderField<T7> var7, OrderField<T8> var8, OrderField<T9> var9, OrderField<T10> var10, OrderField<T11> var11, OrderField<T12> var12);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> SelectSeekStep13<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> orderBy(OrderField<T1> var1, OrderField<T2> var2, OrderField<T3> var3, OrderField<T4> var4, OrderField<T5> var5, OrderField<T6> var6, OrderField<T7> var7, OrderField<T8> var8, OrderField<T9> var9, OrderField<T10> var10, OrderField<T11> var11, OrderField<T12> var12, OrderField<T13> var13);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> SelectSeekStep14<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> orderBy(OrderField<T1> var1, OrderField<T2> var2, OrderField<T3> var3, OrderField<T4> var4, OrderField<T5> var5, OrderField<T6> var6, OrderField<T7> var7, OrderField<T8> var8, OrderField<T9> var9, OrderField<T10> var10, OrderField<T11> var11, OrderField<T12> var12, OrderField<T13> var13, OrderField<T14> var14);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> SelectSeekStep15<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> orderBy(OrderField<T1> var1, OrderField<T2> var2, OrderField<T3> var3, OrderField<T4> var4, OrderField<T5> var5, OrderField<T6> var6, OrderField<T7> var7, OrderField<T8> var8, OrderField<T9> var9, OrderField<T10> var10, OrderField<T11> var11, OrderField<T12> var12, OrderField<T13> var13, OrderField<T14> var14, OrderField<T15> var15);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> SelectSeekStep16<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> orderBy(OrderField<T1> var1, OrderField<T2> var2, OrderField<T3> var3, OrderField<T4> var4, OrderField<T5> var5, OrderField<T6> var6, OrderField<T7> var7, OrderField<T8> var8, OrderField<T9> var9, OrderField<T10> var10, OrderField<T11> var11, OrderField<T12> var12, OrderField<T13> var13, OrderField<T14> var14, OrderField<T15> var15, OrderField<T16> var16);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> SelectSeekStep17<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> orderBy(OrderField<T1> var1, OrderField<T2> var2, OrderField<T3> var3, OrderField<T4> var4, OrderField<T5> var5, OrderField<T6> var6, OrderField<T7> var7, OrderField<T8> var8, OrderField<T9> var9, OrderField<T10> var10, OrderField<T11> var11, OrderField<T12> var12, OrderField<T13> var13, OrderField<T14> var14, OrderField<T15> var15, OrderField<T16> var16, OrderField<T17> var17);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> SelectSeekStep18<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> orderBy(OrderField<T1> var1, OrderField<T2> var2, OrderField<T3> var3, OrderField<T4> var4, OrderField<T5> var5, OrderField<T6> var6, OrderField<T7> var7, OrderField<T8> var8, OrderField<T9> var9, OrderField<T10> var10, OrderField<T11> var11, OrderField<T12> var12, OrderField<T13> var13, OrderField<T14> var14, OrderField<T15> var15, OrderField<T16> var16, OrderField<T17> var17, OrderField<T18> var18);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> SelectSeekStep19<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> orderBy(OrderField<T1> var1, OrderField<T2> var2, OrderField<T3> var3, OrderField<T4> var4, OrderField<T5> var5, OrderField<T6> var6, OrderField<T7> var7, OrderField<T8> var8, OrderField<T9> var9, OrderField<T10> var10, OrderField<T11> var11, OrderField<T12> var12, OrderField<T13> var13, OrderField<T14> var14, OrderField<T15> var15, OrderField<T16> var16, OrderField<T17> var17, OrderField<T18> var18, OrderField<T19> var19);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> SelectSeekStep20<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> orderBy(OrderField<T1> var1, OrderField<T2> var2, OrderField<T3> var3, OrderField<T4> var4, OrderField<T5> var5, OrderField<T6> var6, OrderField<T7> var7, OrderField<T8> var8, OrderField<T9> var9, OrderField<T10> var10, OrderField<T11> var11, OrderField<T12> var12, OrderField<T13> var13, OrderField<T14> var14, OrderField<T15> var15, OrderField<T16> var16, OrderField<T17> var17, OrderField<T18> var18, OrderField<T19> var19, OrderField<T20> var20);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> SelectSeekStep21<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> orderBy(OrderField<T1> var1, OrderField<T2> var2, OrderField<T3> var3, OrderField<T4> var4, OrderField<T5> var5, OrderField<T6> var6, OrderField<T7> var7, OrderField<T8> var8, OrderField<T9> var9, OrderField<T10> var10, OrderField<T11> var11, OrderField<T12> var12, OrderField<T13> var13, OrderField<T14> var14, OrderField<T15> var15, OrderField<T16> var16, OrderField<T17> var17, OrderField<T18> var18, OrderField<T19> var19, OrderField<T20> var20, OrderField<T21> var21);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> SelectSeekStep22<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> orderBy(OrderField<T1> var1, OrderField<T2> var2, OrderField<T3> var3, OrderField<T4> var4, OrderField<T5> var5, OrderField<T6> var6, OrderField<T7> var7, OrderField<T8> var8, OrderField<T9> var9, OrderField<T10> var10, OrderField<T11> var11, OrderField<T12> var12, OrderField<T13> var13, OrderField<T14> var14, OrderField<T15> var15, OrderField<T16> var16, OrderField<T17> var17, OrderField<T18> var18, OrderField<T19> var19, OrderField<T20> var20, OrderField<T21> var21, OrderField<T22> var22);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectSeekStepN<R> orderBy(OrderField<?> ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectSeekStepN<R> orderBy(Collection<? extends OrderField<?>> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectLimitStep<R> orderBy(int ... var1);

    @Support(value={SQLDialect.CUBRID, SQLDialect.INFORMIX, SQLDialect.ORACLE})
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectLimitStep<R> orderSiblingsBy(OrderField<?> ... var1);

    @Support(value={SQLDialect.CUBRID, SQLDialect.INFORMIX, SQLDialect.ORACLE})
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectLimitStep<R> orderSiblingsBy(Collection<? extends OrderField<?>> var1);

    @Support(value={SQLDialect.CUBRID, SQLDialect.INFORMIX, SQLDialect.ORACLE})
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectLimitStep<R> orderSiblingsBy(int ... var1);
}

