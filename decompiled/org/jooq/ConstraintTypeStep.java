/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jooq.Condition;
import org.jooq.ConstraintEnforcementStep;
import org.jooq.ConstraintFinalStep;
import org.jooq.ConstraintForeignKeyReferencesStep1;
import org.jooq.ConstraintForeignKeyReferencesStep10;
import org.jooq.ConstraintForeignKeyReferencesStep11;
import org.jooq.ConstraintForeignKeyReferencesStep12;
import org.jooq.ConstraintForeignKeyReferencesStep13;
import org.jooq.ConstraintForeignKeyReferencesStep14;
import org.jooq.ConstraintForeignKeyReferencesStep15;
import org.jooq.ConstraintForeignKeyReferencesStep16;
import org.jooq.ConstraintForeignKeyReferencesStep17;
import org.jooq.ConstraintForeignKeyReferencesStep18;
import org.jooq.ConstraintForeignKeyReferencesStep19;
import org.jooq.ConstraintForeignKeyReferencesStep2;
import org.jooq.ConstraintForeignKeyReferencesStep20;
import org.jooq.ConstraintForeignKeyReferencesStep21;
import org.jooq.ConstraintForeignKeyReferencesStep22;
import org.jooq.ConstraintForeignKeyReferencesStep3;
import org.jooq.ConstraintForeignKeyReferencesStep4;
import org.jooq.ConstraintForeignKeyReferencesStep5;
import org.jooq.ConstraintForeignKeyReferencesStep6;
import org.jooq.ConstraintForeignKeyReferencesStep7;
import org.jooq.ConstraintForeignKeyReferencesStep8;
import org.jooq.ConstraintForeignKeyReferencesStep9;
import org.jooq.ConstraintForeignKeyReferencesStepN;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.SQLDialect;
import org.jooq.Support;

public interface ConstraintTypeStep
extends ConstraintFinalStep {
    @Support
    @NotNull
    public ConstraintEnforcementStep primaryKey(String ... var1);

    @Support
    @NotNull
    public ConstraintEnforcementStep primaryKey(Name ... var1);

    @Support
    @NotNull
    public ConstraintEnforcementStep primaryKey(Field<?> ... var1);

    @Support
    @NotNull
    public ConstraintEnforcementStep primaryKey(Collection<? extends Field<?>> var1);

    @Support
    @NotNull
    public ConstraintForeignKeyReferencesStepN foreignKey(String ... var1);

    @Support
    @NotNull
    public ConstraintForeignKeyReferencesStepN foreignKey(Name ... var1);

    @Support
    @NotNull
    public ConstraintForeignKeyReferencesStepN foreignKey(Field<?> ... var1);

    @Support
    @NotNull
    public ConstraintForeignKeyReferencesStepN foreignKey(Collection<? extends Field<?>> var1);

    @Support
    @NotNull
    public <T1> ConstraintForeignKeyReferencesStep1<T1> foreignKey(Field<T1> var1);

    @Support
    @NotNull
    public <T1, T2> ConstraintForeignKeyReferencesStep2<T1, T2> foreignKey(Field<T1> var1, Field<T2> var2);

    @Support
    @NotNull
    public <T1, T2, T3> ConstraintForeignKeyReferencesStep3<T1, T2, T3> foreignKey(Field<T1> var1, Field<T2> var2, Field<T3> var3);

    @Support
    @NotNull
    public <T1, T2, T3, T4> ConstraintForeignKeyReferencesStep4<T1, T2, T3, T4> foreignKey(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4);

    @Support
    @NotNull
    public <T1, T2, T3, T4, T5> ConstraintForeignKeyReferencesStep5<T1, T2, T3, T4, T5> foreignKey(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5);

    @Support
    @NotNull
    public <T1, T2, T3, T4, T5, T6> ConstraintForeignKeyReferencesStep6<T1, T2, T3, T4, T5, T6> foreignKey(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6);

    @Support
    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7> ConstraintForeignKeyReferencesStep7<T1, T2, T3, T4, T5, T6, T7> foreignKey(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7);

    @Support
    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8> ConstraintForeignKeyReferencesStep8<T1, T2, T3, T4, T5, T6, T7, T8> foreignKey(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

    @Support
    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> ConstraintForeignKeyReferencesStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> foreignKey(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9);

    @Support
    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> ConstraintForeignKeyReferencesStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> foreignKey(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10);

    @Support
    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> ConstraintForeignKeyReferencesStep11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> foreignKey(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11);

    @Support
    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> ConstraintForeignKeyReferencesStep12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> foreignKey(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12);

    @Support
    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> ConstraintForeignKeyReferencesStep13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> foreignKey(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13);

    @Support
    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> ConstraintForeignKeyReferencesStep14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> foreignKey(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14);

    @Support
    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> ConstraintForeignKeyReferencesStep15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> foreignKey(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15);

    @Support
    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> ConstraintForeignKeyReferencesStep16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> foreignKey(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16);

    @Support
    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> ConstraintForeignKeyReferencesStep17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> foreignKey(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17);

    @Support
    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> ConstraintForeignKeyReferencesStep18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> foreignKey(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18);

    @Support
    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> ConstraintForeignKeyReferencesStep19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> foreignKey(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18, Field<T19> var19);

    @Support
    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> ConstraintForeignKeyReferencesStep20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> foreignKey(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18, Field<T19> var19, Field<T20> var20);

    @Support
    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> ConstraintForeignKeyReferencesStep21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> foreignKey(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18, Field<T19> var19, Field<T20> var20, Field<T21> var21);

    @Support
    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> ConstraintForeignKeyReferencesStep22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> foreignKey(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18, Field<T19> var19, Field<T20> var20, Field<T21> var21, Field<T22> var22);

    @Support
    @NotNull
    public ConstraintForeignKeyReferencesStep1<?> foreignKey(Name var1);

    @Support
    @NotNull
    public ConstraintForeignKeyReferencesStep2<?, ?> foreignKey(Name var1, Name var2);

    @Support
    @NotNull
    public ConstraintForeignKeyReferencesStep3<?, ?, ?> foreignKey(Name var1, Name var2, Name var3);

    @Support
    @NotNull
    public ConstraintForeignKeyReferencesStep4<?, ?, ?, ?> foreignKey(Name var1, Name var2, Name var3, Name var4);

    @Support
    @NotNull
    public ConstraintForeignKeyReferencesStep5<?, ?, ?, ?, ?> foreignKey(Name var1, Name var2, Name var3, Name var4, Name var5);

    @Support
    @NotNull
    public ConstraintForeignKeyReferencesStep6<?, ?, ?, ?, ?, ?> foreignKey(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6);

    @Support
    @NotNull
    public ConstraintForeignKeyReferencesStep7<?, ?, ?, ?, ?, ?, ?> foreignKey(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7);

    @Support
    @NotNull
    public ConstraintForeignKeyReferencesStep8<?, ?, ?, ?, ?, ?, ?, ?> foreignKey(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8);

    @Support
    @NotNull
    public ConstraintForeignKeyReferencesStep9<?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9);

    @Support
    @NotNull
    public ConstraintForeignKeyReferencesStep10<?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10);

    @Support
    @NotNull
    public ConstraintForeignKeyReferencesStep11<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11);

    @Support
    @NotNull
    public ConstraintForeignKeyReferencesStep12<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12);

    @Support
    @NotNull
    public ConstraintForeignKeyReferencesStep13<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13);

    @Support
    @NotNull
    public ConstraintForeignKeyReferencesStep14<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13, Name var14);

    @Support
    @NotNull
    public ConstraintForeignKeyReferencesStep15<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13, Name var14, Name var15);

    @Support
    @NotNull
    public ConstraintForeignKeyReferencesStep16<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13, Name var14, Name var15, Name var16);

    @Support
    @NotNull
    public ConstraintForeignKeyReferencesStep17<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13, Name var14, Name var15, Name var16, Name var17);

    @Support
    @NotNull
    public ConstraintForeignKeyReferencesStep18<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13, Name var14, Name var15, Name var16, Name var17, Name var18);

    @Support
    @NotNull
    public ConstraintForeignKeyReferencesStep19<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13, Name var14, Name var15, Name var16, Name var17, Name var18, Name var19);

    @Support
    @NotNull
    public ConstraintForeignKeyReferencesStep20<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13, Name var14, Name var15, Name var16, Name var17, Name var18, Name var19, Name var20);

    @Support
    @NotNull
    public ConstraintForeignKeyReferencesStep21<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13, Name var14, Name var15, Name var16, Name var17, Name var18, Name var19, Name var20, Name var21);

    @Support
    @NotNull
    public ConstraintForeignKeyReferencesStep22<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13, Name var14, Name var15, Name var16, Name var17, Name var18, Name var19, Name var20, Name var21, Name var22);

    @Support
    @NotNull
    public ConstraintForeignKeyReferencesStep1<?> foreignKey(String var1);

    @Support
    @NotNull
    public ConstraintForeignKeyReferencesStep2<?, ?> foreignKey(String var1, String var2);

    @Support
    @NotNull
    public ConstraintForeignKeyReferencesStep3<?, ?, ?> foreignKey(String var1, String var2, String var3);

    @Support
    @NotNull
    public ConstraintForeignKeyReferencesStep4<?, ?, ?, ?> foreignKey(String var1, String var2, String var3, String var4);

    @Support
    @NotNull
    public ConstraintForeignKeyReferencesStep5<?, ?, ?, ?, ?> foreignKey(String var1, String var2, String var3, String var4, String var5);

    @Support
    @NotNull
    public ConstraintForeignKeyReferencesStep6<?, ?, ?, ?, ?, ?> foreignKey(String var1, String var2, String var3, String var4, String var5, String var6);

    @Support
    @NotNull
    public ConstraintForeignKeyReferencesStep7<?, ?, ?, ?, ?, ?, ?> foreignKey(String var1, String var2, String var3, String var4, String var5, String var6, String var7);

    @Support
    @NotNull
    public ConstraintForeignKeyReferencesStep8<?, ?, ?, ?, ?, ?, ?, ?> foreignKey(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8);

    @Support
    @NotNull
    public ConstraintForeignKeyReferencesStep9<?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9);

    @Support
    @NotNull
    public ConstraintForeignKeyReferencesStep10<?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10);

    @Support
    @NotNull
    public ConstraintForeignKeyReferencesStep11<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11);

    @Support
    @NotNull
    public ConstraintForeignKeyReferencesStep12<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12);

    @Support
    @NotNull
    public ConstraintForeignKeyReferencesStep13<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13);

    @Support
    @NotNull
    public ConstraintForeignKeyReferencesStep14<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14);

    @Support
    @NotNull
    public ConstraintForeignKeyReferencesStep15<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15);

    @Support
    @NotNull
    public ConstraintForeignKeyReferencesStep16<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16);

    @Support
    @NotNull
    public ConstraintForeignKeyReferencesStep17<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, String var17);

    @Support
    @NotNull
    public ConstraintForeignKeyReferencesStep18<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, String var17, String var18);

    @Support
    @NotNull
    public ConstraintForeignKeyReferencesStep19<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, String var17, String var18, String var19);

    @Support
    @NotNull
    public ConstraintForeignKeyReferencesStep20<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, String var17, String var18, String var19, String var20);

    @Support
    @NotNull
    public ConstraintForeignKeyReferencesStep21<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, String var17, String var18, String var19, String var20, String var21);

    @Support
    @NotNull
    public ConstraintForeignKeyReferencesStep22<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, String var17, String var18, String var19, String var20, String var21, String var22);

    @Support
    @NotNull
    public ConstraintEnforcementStep unique(String ... var1);

    @Support
    @NotNull
    public ConstraintEnforcementStep unique(Name ... var1);

    @Support
    @NotNull
    public ConstraintEnforcementStep unique(Field<?> ... var1);

    @Support
    @NotNull
    public ConstraintEnforcementStep unique(Collection<? extends Field<?>> var1);

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0_19, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public ConstraintEnforcementStep check(Condition var1);
}

