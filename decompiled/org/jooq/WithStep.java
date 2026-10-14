/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.CommonTableExpression;
import org.jooq.DeleteUsingStep;
import org.jooq.Field;
import org.jooq.InsertSetStep;
import org.jooq.InsertValuesStep1;
import org.jooq.InsertValuesStep10;
import org.jooq.InsertValuesStep11;
import org.jooq.InsertValuesStep12;
import org.jooq.InsertValuesStep13;
import org.jooq.InsertValuesStep14;
import org.jooq.InsertValuesStep15;
import org.jooq.InsertValuesStep16;
import org.jooq.InsertValuesStep17;
import org.jooq.InsertValuesStep18;
import org.jooq.InsertValuesStep19;
import org.jooq.InsertValuesStep2;
import org.jooq.InsertValuesStep20;
import org.jooq.InsertValuesStep21;
import org.jooq.InsertValuesStep22;
import org.jooq.InsertValuesStep3;
import org.jooq.InsertValuesStep4;
import org.jooq.InsertValuesStep5;
import org.jooq.InsertValuesStep6;
import org.jooq.InsertValuesStep7;
import org.jooq.InsertValuesStep8;
import org.jooq.InsertValuesStep9;
import org.jooq.InsertValuesStepN;
import org.jooq.MergeKeyStep1;
import org.jooq.MergeKeyStep10;
import org.jooq.MergeKeyStep11;
import org.jooq.MergeKeyStep12;
import org.jooq.MergeKeyStep13;
import org.jooq.MergeKeyStep14;
import org.jooq.MergeKeyStep15;
import org.jooq.MergeKeyStep16;
import org.jooq.MergeKeyStep17;
import org.jooq.MergeKeyStep18;
import org.jooq.MergeKeyStep19;
import org.jooq.MergeKeyStep2;
import org.jooq.MergeKeyStep20;
import org.jooq.MergeKeyStep21;
import org.jooq.MergeKeyStep22;
import org.jooq.MergeKeyStep3;
import org.jooq.MergeKeyStep4;
import org.jooq.MergeKeyStep5;
import org.jooq.MergeKeyStep6;
import org.jooq.MergeKeyStep7;
import org.jooq.MergeKeyStep8;
import org.jooq.MergeKeyStep9;
import org.jooq.MergeKeyStepN;
import org.jooq.MergeUsingStep;
import org.jooq.Name;
import org.jooq.PlainSQL;
import org.jooq.QueryPart;
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
import org.jooq.SQL;
import org.jooq.SQLDialect;
import org.jooq.SelectField;
import org.jooq.SelectFieldOrAsterisk;
import org.jooq.SelectSelectStep;
import org.jooq.SelectWhereStep;
import org.jooq.Support;
import org.jooq.Table;
import org.jooq.TableLike;
import org.jooq.UpdateSetFirstStep;
import org.jooq.WithAsStep;
import org.jooq.WithAsStep1;
import org.jooq.WithAsStep10;
import org.jooq.WithAsStep11;
import org.jooq.WithAsStep12;
import org.jooq.WithAsStep13;
import org.jooq.WithAsStep14;
import org.jooq.WithAsStep15;
import org.jooq.WithAsStep16;
import org.jooq.WithAsStep17;
import org.jooq.WithAsStep18;
import org.jooq.WithAsStep19;
import org.jooq.WithAsStep2;
import org.jooq.WithAsStep20;
import org.jooq.WithAsStep21;
import org.jooq.WithAsStep22;
import org.jooq.WithAsStep3;
import org.jooq.WithAsStep4;
import org.jooq.WithAsStep5;
import org.jooq.WithAsStep6;
import org.jooq.WithAsStep7;
import org.jooq.WithAsStep8;
import org.jooq.WithAsStep9;

public interface WithStep
extends QueryPart {
    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep with(String var1);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep with(String var1, String ... var2);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep with(String var1, Collection<String> var2);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep with(Name var1);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep with(Name var1, Name ... var2);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep with(Name var1, Collection<? extends Name> var2);

    @Deprecated(forRemoval=true, since="3.14")
    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep with(String var1, Function<? super Field<?>, ? extends String> var2);

    @Deprecated(forRemoval=true, since="3.14")
    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep with(String var1, BiFunction<? super Field<?>, ? super Integer, ? extends String> var2);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep1 with(String var1, String var2);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep2 with(String var1, String var2, String var3);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep3 with(String var1, String var2, String var3, String var4);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep4 with(String var1, String var2, String var3, String var4, String var5);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep5 with(String var1, String var2, String var3, String var4, String var5, String var6);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep6 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep7 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep8 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep9 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep10 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep11 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep12 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep13 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep14 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep15 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep16 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, String var17);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep17 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, String var17, String var18);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep18 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, String var17, String var18, String var19);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep19 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, String var17, String var18, String var19, String var20);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep20 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, String var17, String var18, String var19, String var20, String var21);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep21 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, String var17, String var18, String var19, String var20, String var21, String var22);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep22 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, String var17, String var18, String var19, String var20, String var21, String var22, String var23);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep1 with(Name var1, Name var2);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep2 with(Name var1, Name var2, Name var3);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep3 with(Name var1, Name var2, Name var3, Name var4);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep4 with(Name var1, Name var2, Name var3, Name var4, Name var5);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep5 with(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep6 with(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep7 with(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep8 with(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep9 with(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep10 with(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep11 with(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep12 with(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep13 with(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13, Name var14);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep14 with(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13, Name var14, Name var15);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep15 with(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13, Name var14, Name var15, Name var16);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep16 with(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13, Name var14, Name var15, Name var16, Name var17);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep17 with(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13, Name var14, Name var15, Name var16, Name var17, Name var18);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep18 with(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13, Name var14, Name var15, Name var16, Name var17, Name var18, Name var19);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep19 with(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13, Name var14, Name var15, Name var16, Name var17, Name var18, Name var19, Name var20);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep20 with(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13, Name var14, Name var15, Name var16, Name var17, Name var18, Name var19, Name var20, Name var21);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep21 with(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13, Name var14, Name var15, Name var16, Name var17, Name var18, Name var19, Name var20, Name var21, Name var22);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep22 with(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13, Name var14, Name var15, Name var16, Name var17, Name var18, Name var19, Name var20, Name var21, Name var22, Name var23);

    @Support
    @NotNull
    @CheckReturnValue
    public WithStep with(CommonTableExpression<?> ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public WithStep with(Collection<? extends CommonTableExpression<?>> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public <R extends Record> SelectWhereStep<R> selectFrom(TableLike<R> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectWhereStep<Record> selectFrom(Name var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectWhereStep<Record> selectFrom(SQL var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectWhereStep<Record> selectFrom(String var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectWhereStep<Record> selectFrom(String var1, Object ... var2);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectWhereStep<Record> selectFrom(String var1, QueryPart ... var2);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectSelectStep<Record> select(Collection<? extends SelectFieldOrAsterisk> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectSelectStep<Record> select(SelectFieldOrAsterisk ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1> SelectSelectStep<Record1<T1>> select(SelectField<T1> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2> SelectSelectStep<Record2<T1, T2>> select(SelectField<T1> var1, SelectField<T2> var2);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3> SelectSelectStep<Record3<T1, T2, T3>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4> SelectSelectStep<Record4<T1, T2, T3, T4>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5> SelectSelectStep<Record5<T1, T2, T3, T4, T5>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6> SelectSelectStep<Record6<T1, T2, T3, T4, T5, T6>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7> SelectSelectStep<Record7<T1, T2, T3, T4, T5, T6, T7>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8> SelectSelectStep<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> SelectSelectStep<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> SelectSelectStep<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> SelectSelectStep<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> SelectSelectStep<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> SelectSelectStep<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> SelectSelectStep<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> SelectSelectStep<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> SelectSelectStep<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> SelectSelectStep<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16, SelectField<T17> var17);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> SelectSelectStep<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16, SelectField<T17> var17, SelectField<T18> var18);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> SelectSelectStep<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16, SelectField<T17> var17, SelectField<T18> var18, SelectField<T19> var19);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> SelectSelectStep<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16, SelectField<T17> var17, SelectField<T18> var18, SelectField<T19> var19, SelectField<T20> var20);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> SelectSelectStep<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16, SelectField<T17> var17, SelectField<T18> var18, SelectField<T19> var19, SelectField<T20> var20, SelectField<T21> var21);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> SelectSelectStep<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16, SelectField<T17> var17, SelectField<T18> var18, SelectField<T19> var19, SelectField<T20> var20, SelectField<T21> var21, SelectField<T22> var22);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectSelectStep<Record> selectDistinct(Collection<? extends SelectFieldOrAsterisk> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectSelectStep<Record> selectDistinct(SelectFieldOrAsterisk ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1> SelectSelectStep<Record1<T1>> selectDistinct(SelectField<T1> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2> SelectSelectStep<Record2<T1, T2>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3> SelectSelectStep<Record3<T1, T2, T3>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4> SelectSelectStep<Record4<T1, T2, T3, T4>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5> SelectSelectStep<Record5<T1, T2, T3, T4, T5>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6> SelectSelectStep<Record6<T1, T2, T3, T4, T5, T6>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7> SelectSelectStep<Record7<T1, T2, T3, T4, T5, T6, T7>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8> SelectSelectStep<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> SelectSelectStep<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> SelectSelectStep<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> SelectSelectStep<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> SelectSelectStep<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> SelectSelectStep<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> SelectSelectStep<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> SelectSelectStep<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> SelectSelectStep<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> SelectSelectStep<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16, SelectField<T17> var17);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> SelectSelectStep<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16, SelectField<T17> var17, SelectField<T18> var18);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> SelectSelectStep<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16, SelectField<T17> var17, SelectField<T18> var18, SelectField<T19> var19);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> SelectSelectStep<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16, SelectField<T17> var17, SelectField<T18> var18, SelectField<T19> var19, SelectField<T20> var20);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> SelectSelectStep<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16, SelectField<T17> var17, SelectField<T18> var18, SelectField<T19> var19, SelectField<T20> var20, SelectField<T21> var21);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> SelectSelectStep<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16, SelectField<T17> var17, SelectField<T18> var18, SelectField<T19> var19, SelectField<T20> var20, SelectField<T21> var21, SelectField<T22> var22);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectSelectStep<Record1<Integer>> selectZero();

    @Support
    @NotNull
    @CheckReturnValue
    public SelectSelectStep<Record1<Integer>> selectOne();

    @Support
    @NotNull
    @CheckReturnValue
    public SelectSelectStep<Record1<Integer>> selectCount();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record> InsertSetStep<R> insertInto(Table<R> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1> InsertValuesStep1<R, T1> insertInto(Table<R> var1, Field<T1> var2);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2> InsertValuesStep2<R, T1, T2> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3> InsertValuesStep3<R, T1, T2, T3> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4> InsertValuesStep4<R, T1, T2, T3, T4> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5> InsertValuesStep5<R, T1, T2, T3, T4, T5> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6> InsertValuesStep6<R, T1, T2, T3, T4, T5, T6> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7> InsertValuesStep7<R, T1, T2, T3, T4, T5, T6, T7> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8> InsertValuesStep8<R, T1, T2, T3, T4, T5, T6, T7, T8> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9> InsertValuesStep9<R, T1, T2, T3, T4, T5, T6, T7, T8, T9> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> InsertValuesStep10<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> InsertValuesStep11<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> InsertValuesStep12<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> InsertValuesStep13<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> InsertValuesStep14<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> InsertValuesStep15<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15, Field<T15> var16);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> InsertValuesStep16<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15, Field<T15> var16, Field<T16> var17);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> InsertValuesStep17<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15, Field<T15> var16, Field<T16> var17, Field<T17> var18);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> InsertValuesStep18<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15, Field<T15> var16, Field<T16> var17, Field<T17> var18, Field<T18> var19);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> InsertValuesStep19<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15, Field<T15> var16, Field<T16> var17, Field<T17> var18, Field<T18> var19, Field<T19> var20);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> InsertValuesStep20<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15, Field<T15> var16, Field<T16> var17, Field<T17> var18, Field<T18> var19, Field<T19> var20, Field<T20> var21);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> InsertValuesStep21<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15, Field<T15> var16, Field<T16> var17, Field<T17> var18, Field<T18> var19, Field<T19> var20, Field<T20> var21, Field<T21> var22);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> InsertValuesStep22<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15, Field<T15> var16, Field<T16> var17, Field<T17> var18, Field<T18> var19, Field<T19> var20, Field<T20> var21, Field<T21> var22, Field<T22> var23);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record> InsertValuesStepN<R> insertInto(Table<R> var1, Field<?> ... var2);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record> InsertValuesStepN<R> insertInto(Table<R> var1, Collection<? extends Field<?>> var2);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record> UpdateSetFirstStep<R> update(Table<R> var1);

    @Support(value={SQLDialect.H2, SQLDialect.POSTGRES_15, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    public <R extends Record> MergeUsingStep<R> mergeInto(Table<R> var1);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.H2, SQLDialect.POSTGRES_15, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1> MergeKeyStep1<R, T1> mergeInto(Table<R> var1, Field<T1> var2);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.H2, SQLDialect.POSTGRES_15, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2> MergeKeyStep2<R, T1, T2> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.H2, SQLDialect.POSTGRES_15, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3> MergeKeyStep3<R, T1, T2, T3> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.H2, SQLDialect.POSTGRES_15, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4> MergeKeyStep4<R, T1, T2, T3, T4> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.H2, SQLDialect.POSTGRES_15, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5> MergeKeyStep5<R, T1, T2, T3, T4, T5> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.H2, SQLDialect.POSTGRES_15, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6> MergeKeyStep6<R, T1, T2, T3, T4, T5, T6> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.H2, SQLDialect.POSTGRES_15, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7> MergeKeyStep7<R, T1, T2, T3, T4, T5, T6, T7> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.H2, SQLDialect.POSTGRES_15, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8> MergeKeyStep8<R, T1, T2, T3, T4, T5, T6, T7, T8> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.H2, SQLDialect.POSTGRES_15, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9> MergeKeyStep9<R, T1, T2, T3, T4, T5, T6, T7, T8, T9> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.H2, SQLDialect.POSTGRES_15, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> MergeKeyStep10<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.H2, SQLDialect.POSTGRES_15, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> MergeKeyStep11<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.H2, SQLDialect.POSTGRES_15, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> MergeKeyStep12<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.H2, SQLDialect.POSTGRES_15, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> MergeKeyStep13<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.H2, SQLDialect.POSTGRES_15, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> MergeKeyStep14<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.H2, SQLDialect.POSTGRES_15, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> MergeKeyStep15<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15, Field<T15> var16);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.H2, SQLDialect.POSTGRES_15, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> MergeKeyStep16<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15, Field<T15> var16, Field<T16> var17);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.H2, SQLDialect.POSTGRES_15, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> MergeKeyStep17<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15, Field<T15> var16, Field<T16> var17, Field<T17> var18);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.H2, SQLDialect.POSTGRES_15, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> MergeKeyStep18<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15, Field<T15> var16, Field<T16> var17, Field<T17> var18, Field<T18> var19);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.H2, SQLDialect.POSTGRES_15, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> MergeKeyStep19<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15, Field<T15> var16, Field<T16> var17, Field<T17> var18, Field<T18> var19, Field<T19> var20);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.H2, SQLDialect.POSTGRES_15, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> MergeKeyStep20<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15, Field<T15> var16, Field<T16> var17, Field<T17> var18, Field<T18> var19, Field<T19> var20, Field<T20> var21);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.H2, SQLDialect.POSTGRES_15, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> MergeKeyStep21<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15, Field<T15> var16, Field<T16> var17, Field<T17> var18, Field<T18> var19, Field<T19> var20, Field<T20> var21, Field<T21> var22);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.H2, SQLDialect.POSTGRES_15, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> MergeKeyStep22<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15, Field<T15> var16, Field<T16> var17, Field<T17> var18, Field<T18> var19, Field<T19> var20, Field<T20> var21, Field<T21> var22, Field<T22> var23);

    @Support(value={SQLDialect.H2, SQLDialect.POSTGRES_15, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    public <R extends Record> MergeKeyStepN<R> mergeInto(Table<R> var1, Field<?> ... var2);

    @Support(value={SQLDialect.H2, SQLDialect.POSTGRES_15, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    public <R extends Record> MergeKeyStepN<R> mergeInto(Table<R> var1, Collection<? extends Field<?>> var2);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record> DeleteUsingStep<R> delete(Table<R> var1);
}

