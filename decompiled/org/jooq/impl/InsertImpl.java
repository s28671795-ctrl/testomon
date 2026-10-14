/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Constraint;
import org.jooq.Field;
import org.jooq.FieldLike;
import org.jooq.FieldOrRow;
import org.jooq.FieldOrRowOrSelect;
import org.jooq.InsertOnConflictConditionStep;
import org.jooq.InsertOnConflictWhereIndexPredicateStep;
import org.jooq.InsertOnDuplicateSetMoreStep;
import org.jooq.InsertResultStep;
import org.jooq.InsertSetMoreStep;
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
import org.jooq.Name;
import org.jooq.Operator;
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
import org.jooq.Replacer;
import org.jooq.Row;
import org.jooq.Row1;
import org.jooq.Row10;
import org.jooq.Row11;
import org.jooq.Row12;
import org.jooq.Row13;
import org.jooq.Row14;
import org.jooq.Row15;
import org.jooq.Row16;
import org.jooq.Row17;
import org.jooq.Row18;
import org.jooq.Row19;
import org.jooq.Row2;
import org.jooq.Row20;
import org.jooq.Row21;
import org.jooq.Row22;
import org.jooq.Row3;
import org.jooq.Row4;
import org.jooq.Row5;
import org.jooq.Row6;
import org.jooq.Row7;
import org.jooq.Row8;
import org.jooq.Row9;
import org.jooq.RowN;
import org.jooq.SQL;
import org.jooq.Select;
import org.jooq.SelectField;
import org.jooq.SelectFieldOrAsterisk;
import org.jooq.Table;
import org.jooq.Traverser;
import org.jooq.UniqueKey;
import org.jooq.impl.AbstractDelegatingDMLQuery;
import org.jooq.impl.DSL;
import org.jooq.impl.InsertAsResultQuery;
import org.jooq.impl.InsertQueryImpl;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;
import org.jooq.impl.WithImpl;

final class InsertImpl<R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>
extends AbstractDelegatingDMLQuery<R, InsertQueryImpl<R>>
implements InsertValuesStep1<R, T1>,
InsertValuesStep2<R, T1, T2>,
InsertValuesStep3<R, T1, T2, T3>,
InsertValuesStep4<R, T1, T2, T3, T4>,
InsertValuesStep5<R, T1, T2, T3, T4, T5>,
InsertValuesStep6<R, T1, T2, T3, T4, T5, T6>,
InsertValuesStep7<R, T1, T2, T3, T4, T5, T6, T7>,
InsertValuesStep8<R, T1, T2, T3, T4, T5, T6, T7, T8>,
InsertValuesStep9<R, T1, T2, T3, T4, T5, T6, T7, T8, T9>,
InsertValuesStep10<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>,
InsertValuesStep11<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>,
InsertValuesStep12<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>,
InsertValuesStep13<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>,
InsertValuesStep14<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>,
InsertValuesStep15<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>,
InsertValuesStep16<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>,
InsertValuesStep17<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>,
InsertValuesStep18<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>,
InsertValuesStep19<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>,
InsertValuesStep20<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>,
InsertValuesStep21<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>,
InsertValuesStep22<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>,
InsertValuesStepN<R>,
InsertSetStep<R>,
InsertSetMoreStep<R>,
InsertOnDuplicateSetMoreStep<R>,
InsertOnConflictWhereIndexPredicateStep<R>,
InsertOnConflictConditionStep<R>,
QOM.Insert<R> {
    private final Table<R> into;
    private Field<?>[] fields;
    private boolean onDuplicateKeyUpdate;
    private boolean returningResult;
    private transient boolean doUpdateWhere;

    InsertImpl(Configuration configuration, WithImpl with, Table<R> into) {
        this(configuration, with, into, Collections.emptyList());
    }

    InsertImpl(Configuration configuration, WithImpl with, Table<R> into, Collection<? extends Field<?>> fields) {
        super(new InsertQueryImpl<R>(configuration, with, into));
        this.into = into;
        this.columns((Collection)fields);
    }

    public final InsertImpl select(Select select) {
        ((InsertQueryImpl)this.getDelegate()).setSelect(this.fields, select);
        return this;
    }

    public final InsertImpl values(T1 value1) {
        return this.values(new Object[]{value1});
    }

    public final InsertImpl values(T1 value1, T2 value2) {
        return this.values(new Object[]{value1, value2});
    }

    public final InsertImpl values(T1 value1, T2 value2, T3 value3) {
        return this.values(new Object[]{value1, value2, value3});
    }

    public final InsertImpl values(T1 value1, T2 value2, T3 value3, T4 value4) {
        return this.values(new Object[]{value1, value2, value3, value4});
    }

    public final InsertImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5) {
        return this.values(new Object[]{value1, value2, value3, value4, value5});
    }

    public final InsertImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6) {
        return this.values(new Object[]{value1, value2, value3, value4, value5, value6});
    }

    public final InsertImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7) {
        return this.values(new Object[]{value1, value2, value3, value4, value5, value6, value7});
    }

    public final InsertImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8) {
        return this.values(new Object[]{value1, value2, value3, value4, value5, value6, value7, value8});
    }

    public final InsertImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9) {
        return this.values(new Object[]{value1, value2, value3, value4, value5, value6, value7, value8, value9});
    }

    public final InsertImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10) {
        return this.values(new Object[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10});
    }

    public final InsertImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10, T11 value11) {
        return this.values(new Object[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11});
    }

    public final InsertImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10, T11 value11, T12 value12) {
        return this.values(new Object[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12});
    }

    public final InsertImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10, T11 value11, T12 value12, T13 value13) {
        return this.values(new Object[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13});
    }

    public final InsertImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10, T11 value11, T12 value12, T13 value13, T14 value14) {
        return this.values(new Object[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14});
    }

    public final InsertImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10, T11 value11, T12 value12, T13 value13, T14 value14, T15 value15) {
        return this.values(new Object[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15});
    }

    public final InsertImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10, T11 value11, T12 value12, T13 value13, T14 value14, T15 value15, T16 value16) {
        return this.values(new Object[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16});
    }

    public final InsertImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10, T11 value11, T12 value12, T13 value13, T14 value14, T15 value15, T16 value16, T17 value17) {
        return this.values(new Object[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17});
    }

    public final InsertImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10, T11 value11, T12 value12, T13 value13, T14 value14, T15 value15, T16 value16, T17 value17, T18 value18) {
        return this.values(new Object[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18});
    }

    public final InsertImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10, T11 value11, T12 value12, T13 value13, T14 value14, T15 value15, T16 value16, T17 value17, T18 value18, T19 value19) {
        return this.values(new Object[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19});
    }

    public final InsertImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10, T11 value11, T12 value12, T13 value13, T14 value14, T15 value15, T16 value16, T17 value17, T18 value18, T19 value19, T20 value20) {
        return this.values(new Object[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20});
    }

    public final InsertImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10, T11 value11, T12 value12, T13 value13, T14 value14, T15 value15, T16 value16, T17 value17, T18 value18, T19 value19, T20 value20, T21 value21) {
        return this.values(new Object[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20, value21});
    }

    public final InsertImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10, T11 value11, T12 value12, T13 value13, T14 value14, T15 value15, T16 value16, T17 value17, T18 value18, T19 value19, T20 value20, T21 value21, T22 value22) {
        return this.values(new Object[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20, value21, value22});
    }

    public final InsertImpl values(RowN values2) {
        return this.values((Field[])values2.fields());
    }

    public final InsertImpl valuesOfRows(RowN ... values2) {
        return this.valuesOfRows(Arrays.asList(values2));
    }

    public final InsertImpl values(Row1<T1> values2) {
        return this.values((Field[])values2.fields());
    }

    public final InsertImpl valuesOfRows(Row1<T1> ... values2) {
        return this.valuesOfRows(Arrays.asList(values2));
    }

    public final InsertImpl values(Row2<T1, T2> values2) {
        return this.values((Field[])values2.fields());
    }

    public final InsertImpl valuesOfRows(Row2<T1, T2> ... values2) {
        return this.valuesOfRows(Arrays.asList(values2));
    }

    public final InsertImpl values(Row3<T1, T2, T3> values2) {
        return this.values((Field[])values2.fields());
    }

    public final InsertImpl valuesOfRows(Row3<T1, T2, T3> ... values2) {
        return this.valuesOfRows(Arrays.asList(values2));
    }

    public final InsertImpl values(Row4<T1, T2, T3, T4> values2) {
        return this.values((Field[])values2.fields());
    }

    public final InsertImpl valuesOfRows(Row4<T1, T2, T3, T4> ... values2) {
        return this.valuesOfRows(Arrays.asList(values2));
    }

    public final InsertImpl values(Row5<T1, T2, T3, T4, T5> values2) {
        return this.values((Field[])values2.fields());
    }

    public final InsertImpl valuesOfRows(Row5<T1, T2, T3, T4, T5> ... values2) {
        return this.valuesOfRows(Arrays.asList(values2));
    }

    public final InsertImpl values(Row6<T1, T2, T3, T4, T5, T6> values2) {
        return this.values((Field[])values2.fields());
    }

    public final InsertImpl valuesOfRows(Row6<T1, T2, T3, T4, T5, T6> ... values2) {
        return this.valuesOfRows(Arrays.asList(values2));
    }

    public final InsertImpl values(Row7<T1, T2, T3, T4, T5, T6, T7> values2) {
        return this.values((Field[])values2.fields());
    }

    public final InsertImpl valuesOfRows(Row7<T1, T2, T3, T4, T5, T6, T7> ... values2) {
        return this.valuesOfRows(Arrays.asList(values2));
    }

    public final InsertImpl values(Row8<T1, T2, T3, T4, T5, T6, T7, T8> values2) {
        return this.values((Field[])values2.fields());
    }

    public final InsertImpl valuesOfRows(Row8<T1, T2, T3, T4, T5, T6, T7, T8> ... values2) {
        return this.valuesOfRows(Arrays.asList(values2));
    }

    public final InsertImpl values(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> values2) {
        return this.values((Field[])values2.fields());
    }

    public final InsertImpl valuesOfRows(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> ... values2) {
        return this.valuesOfRows(Arrays.asList(values2));
    }

    public final InsertImpl values(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> values2) {
        return this.values((Field[])values2.fields());
    }

    public final InsertImpl valuesOfRows(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> ... values2) {
        return this.valuesOfRows(Arrays.asList(values2));
    }

    public final InsertImpl values(Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> values2) {
        return this.values((Field[])values2.fields());
    }

    public final InsertImpl valuesOfRows(Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> ... values2) {
        return this.valuesOfRows(Arrays.asList(values2));
    }

    public final InsertImpl values(Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> values2) {
        return this.values((Field[])values2.fields());
    }

    public final InsertImpl valuesOfRows(Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> ... values2) {
        return this.valuesOfRows(Arrays.asList(values2));
    }

    public final InsertImpl values(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> values2) {
        return this.values((Field[])values2.fields());
    }

    public final InsertImpl valuesOfRows(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> ... values2) {
        return this.valuesOfRows(Arrays.asList(values2));
    }

    public final InsertImpl values(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> values2) {
        return this.values((Field[])values2.fields());
    }

    public final InsertImpl valuesOfRows(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> ... values2) {
        return this.valuesOfRows(Arrays.asList(values2));
    }

    public final InsertImpl values(Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> values2) {
        return this.values((Field[])values2.fields());
    }

    public final InsertImpl valuesOfRows(Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> ... values2) {
        return this.valuesOfRows(Arrays.asList(values2));
    }

    public final InsertImpl values(Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> values2) {
        return this.values((Field[])values2.fields());
    }

    public final InsertImpl valuesOfRows(Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> ... values2) {
        return this.valuesOfRows(Arrays.asList(values2));
    }

    public final InsertImpl values(Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> values2) {
        return this.values((Field[])values2.fields());
    }

    public final InsertImpl valuesOfRows(Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> ... values2) {
        return this.valuesOfRows(Arrays.asList(values2));
    }

    public final InsertImpl values(Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> values2) {
        return this.values((Field[])values2.fields());
    }

    public final InsertImpl valuesOfRows(Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> ... values2) {
        return this.valuesOfRows(Arrays.asList(values2));
    }

    public final InsertImpl values(Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> values2) {
        return this.values((Field[])values2.fields());
    }

    public final InsertImpl valuesOfRows(Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> ... values2) {
        return this.valuesOfRows(Arrays.asList(values2));
    }

    public final InsertImpl values(Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> values2) {
        return this.values((Field[])values2.fields());
    }

    public final InsertImpl valuesOfRows(Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> ... values2) {
        return this.valuesOfRows(Arrays.asList(values2));
    }

    public final InsertImpl values(Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> values2) {
        return this.values((Field[])values2.fields());
    }

    public final InsertImpl valuesOfRows(Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> ... values2) {
        return this.valuesOfRows(Arrays.asList(values2));
    }

    public final InsertImpl values(Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> values2) {
        return this.values((Field[])values2.fields());
    }

    public final InsertImpl valuesOfRows(Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> ... values2) {
        return this.valuesOfRows(Arrays.asList(values2));
    }

    public final InsertImpl values(Record values2) {
        return this.values(values2.intoArray());
    }

    public final InsertImpl valuesOfRecords(Record ... values2) {
        return this.valuesOfRecords(Arrays.asList(values2));
    }

    public final InsertImpl values(Record1<T1> values2) {
        return this.values(values2.intoArray());
    }

    public final InsertImpl valuesOfRecords(Record1<T1> ... values2) {
        return this.valuesOfRecords(Arrays.asList(values2));
    }

    public final InsertImpl values(Record2<T1, T2> values2) {
        return this.values(values2.intoArray());
    }

    public final InsertImpl valuesOfRecords(Record2<T1, T2> ... values2) {
        return this.valuesOfRecords(Arrays.asList(values2));
    }

    public final InsertImpl values(Record3<T1, T2, T3> values2) {
        return this.values(values2.intoArray());
    }

    public final InsertImpl valuesOfRecords(Record3<T1, T2, T3> ... values2) {
        return this.valuesOfRecords(Arrays.asList(values2));
    }

    public final InsertImpl values(Record4<T1, T2, T3, T4> values2) {
        return this.values(values2.intoArray());
    }

    public final InsertImpl valuesOfRecords(Record4<T1, T2, T3, T4> ... values2) {
        return this.valuesOfRecords(Arrays.asList(values2));
    }

    public final InsertImpl values(Record5<T1, T2, T3, T4, T5> values2) {
        return this.values(values2.intoArray());
    }

    public final InsertImpl valuesOfRecords(Record5<T1, T2, T3, T4, T5> ... values2) {
        return this.valuesOfRecords(Arrays.asList(values2));
    }

    public final InsertImpl values(Record6<T1, T2, T3, T4, T5, T6> values2) {
        return this.values(values2.intoArray());
    }

    public final InsertImpl valuesOfRecords(Record6<T1, T2, T3, T4, T5, T6> ... values2) {
        return this.valuesOfRecords(Arrays.asList(values2));
    }

    public final InsertImpl values(Record7<T1, T2, T3, T4, T5, T6, T7> values2) {
        return this.values(values2.intoArray());
    }

    public final InsertImpl valuesOfRecords(Record7<T1, T2, T3, T4, T5, T6, T7> ... values2) {
        return this.valuesOfRecords(Arrays.asList(values2));
    }

    public final InsertImpl values(Record8<T1, T2, T3, T4, T5, T6, T7, T8> values2) {
        return this.values(values2.intoArray());
    }

    public final InsertImpl valuesOfRecords(Record8<T1, T2, T3, T4, T5, T6, T7, T8> ... values2) {
        return this.valuesOfRecords(Arrays.asList(values2));
    }

    public final InsertImpl values(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> values2) {
        return this.values(values2.intoArray());
    }

    public final InsertImpl valuesOfRecords(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> ... values2) {
        return this.valuesOfRecords(Arrays.asList(values2));
    }

    public final InsertImpl values(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> values2) {
        return this.values(values2.intoArray());
    }

    public final InsertImpl valuesOfRecords(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> ... values2) {
        return this.valuesOfRecords(Arrays.asList(values2));
    }

    public final InsertImpl values(Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> values2) {
        return this.values(values2.intoArray());
    }

    public final InsertImpl valuesOfRecords(Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> ... values2) {
        return this.valuesOfRecords(Arrays.asList(values2));
    }

    public final InsertImpl values(Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> values2) {
        return this.values(values2.intoArray());
    }

    public final InsertImpl valuesOfRecords(Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> ... values2) {
        return this.valuesOfRecords(Arrays.asList(values2));
    }

    public final InsertImpl values(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> values2) {
        return this.values(values2.intoArray());
    }

    public final InsertImpl valuesOfRecords(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> ... values2) {
        return this.valuesOfRecords(Arrays.asList(values2));
    }

    public final InsertImpl values(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> values2) {
        return this.values(values2.intoArray());
    }

    public final InsertImpl valuesOfRecords(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> ... values2) {
        return this.valuesOfRecords(Arrays.asList(values2));
    }

    public final InsertImpl values(Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> values2) {
        return this.values(values2.intoArray());
    }

    public final InsertImpl valuesOfRecords(Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> ... values2) {
        return this.valuesOfRecords(Arrays.asList(values2));
    }

    public final InsertImpl values(Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> values2) {
        return this.values(values2.intoArray());
    }

    public final InsertImpl valuesOfRecords(Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> ... values2) {
        return this.valuesOfRecords(Arrays.asList(values2));
    }

    public final InsertImpl values(Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> values2) {
        return this.values(values2.intoArray());
    }

    public final InsertImpl valuesOfRecords(Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> ... values2) {
        return this.valuesOfRecords(Arrays.asList(values2));
    }

    public final InsertImpl values(Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> values2) {
        return this.values(values2.intoArray());
    }

    public final InsertImpl valuesOfRecords(Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> ... values2) {
        return this.valuesOfRecords(Arrays.asList(values2));
    }

    public final InsertImpl values(Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> values2) {
        return this.values(values2.intoArray());
    }

    public final InsertImpl valuesOfRecords(Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> ... values2) {
        return this.valuesOfRecords(Arrays.asList(values2));
    }

    public final InsertImpl values(Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> values2) {
        return this.values(values2.intoArray());
    }

    public final InsertImpl valuesOfRecords(Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> ... values2) {
        return this.valuesOfRecords(Arrays.asList(values2));
    }

    public final InsertImpl values(Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> values2) {
        return this.values(values2.intoArray());
    }

    public final InsertImpl valuesOfRecords(Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> ... values2) {
        return this.valuesOfRecords(Arrays.asList(values2));
    }

    public final InsertImpl values(Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> values2) {
        return this.values(values2.intoArray());
    }

    public final InsertImpl valuesOfRecords(Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> ... values2) {
        return this.valuesOfRecords(Arrays.asList(values2));
    }

    public final InsertImpl valuesOfRows(Collection values2) {
        for (Object row : values2) {
            this.values((Field[])((Row)row).fields());
        }
        return this;
    }

    public final InsertImpl valuesOfRecords(Collection values2) {
        for (Object record : values2) {
            this.values(((Record)record).intoArray());
        }
        return this;
    }

    public final InsertImpl values(Object ... values2) {
        if (values2.length == 0) {
            return this.defaultValues();
        }
        if (!Tools.isEmpty(this.fields) && this.fields.length != values2.length) {
            throw new IllegalArgumentException("The number of values must match the number of fields");
        }
        ((InsertQueryImpl)this.getDelegate()).newRecord();
        if (Tools.isEmpty(this.fields)) {
            for (int i = 0; i < values2.length; ++i) {
                this.addValue((InsertQueryImpl)this.getDelegate(), null, i, values2[i]);
            }
        } else {
            for (int i = 0; i < this.fields.length; ++i) {
                this.addValue((InsertQueryImpl)this.getDelegate(), this.fields.length > 0 ? this.fields[i] : null, i, values2[i]);
            }
        }
        return this;
    }

    public final InsertImpl values(Collection<?> values2) {
        return this.values(values2.toArray());
    }

    private final <T> void addValue(InsertQueryImpl<R> delegate, Field<T> field2, int index, Object object) {
        if (object instanceof Field) {
            Field f = (Field)object;
            delegate.addValue(field2, index, f);
        } else if (object instanceof FieldLike) {
            FieldLike f = (FieldLike)object;
            delegate.addValue(field2, index, f.asField());
        } else if (field2 != null) {
            delegate.addValue(field2, index, field2.getDataType().convert(object));
        } else {
            delegate.addValue(field2, index, object);
        }
    }

    public final InsertImpl values(Field<T1> value1) {
        return this.values(new Field[]{value1});
    }

    public final InsertImpl values(Field<T1> value1, Field<T2> value2) {
        return this.values(new Field[]{value1, value2});
    }

    public final InsertImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3) {
        return this.values(new Field[]{value1, value2, value3});
    }

    public final InsertImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4) {
        return this.values(new Field[]{value1, value2, value3, value4});
    }

    public final InsertImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5) {
        return this.values(new Field[]{value1, value2, value3, value4, value5});
    }

    public final InsertImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6) {
        return this.values(new Field[]{value1, value2, value3, value4, value5, value6});
    }

    public final InsertImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7) {
        return this.values(new Field[]{value1, value2, value3, value4, value5, value6, value7});
    }

    public final InsertImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8) {
        return this.values(new Field[]{value1, value2, value3, value4, value5, value6, value7, value8});
    }

    public final InsertImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9) {
        return this.values(new Field[]{value1, value2, value3, value4, value5, value6, value7, value8, value9});
    }

    public final InsertImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10) {
        return this.values(new Field[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10});
    }

    public final InsertImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10, Field<T11> value11) {
        return this.values(new Field[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11});
    }

    public final InsertImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10, Field<T11> value11, Field<T12> value12) {
        return this.values(new Field[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12});
    }

    public final InsertImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10, Field<T11> value11, Field<T12> value12, Field<T13> value13) {
        return this.values(new Field[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13});
    }

    public final InsertImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10, Field<T11> value11, Field<T12> value12, Field<T13> value13, Field<T14> value14) {
        return this.values(new Field[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14});
    }

    public final InsertImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10, Field<T11> value11, Field<T12> value12, Field<T13> value13, Field<T14> value14, Field<T15> value15) {
        return this.values(new Field[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15});
    }

    public final InsertImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10, Field<T11> value11, Field<T12> value12, Field<T13> value13, Field<T14> value14, Field<T15> value15, Field<T16> value16) {
        return this.values(new Field[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16});
    }

    public final InsertImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10, Field<T11> value11, Field<T12> value12, Field<T13> value13, Field<T14> value14, Field<T15> value15, Field<T16> value16, Field<T17> value17) {
        return this.values(new Field[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17});
    }

    public final InsertImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10, Field<T11> value11, Field<T12> value12, Field<T13> value13, Field<T14> value14, Field<T15> value15, Field<T16> value16, Field<T17> value17, Field<T18> value18) {
        return this.values(new Field[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18});
    }

    public final InsertImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10, Field<T11> value11, Field<T12> value12, Field<T13> value13, Field<T14> value14, Field<T15> value15, Field<T16> value16, Field<T17> value17, Field<T18> value18, Field<T19> value19) {
        return this.values(new Field[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19});
    }

    public final InsertImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10, Field<T11> value11, Field<T12> value12, Field<T13> value13, Field<T14> value14, Field<T15> value15, Field<T16> value16, Field<T17> value17, Field<T18> value18, Field<T19> value19, Field<T20> value20) {
        return this.values(new Field[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20});
    }

    public final InsertImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10, Field<T11> value11, Field<T12> value12, Field<T13> value13, Field<T14> value14, Field<T15> value15, Field<T16> value16, Field<T17> value17, Field<T18> value18, Field<T19> value19, Field<T20> value20, Field<T21> value21) {
        return this.values(new Field[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20, value21});
    }

    public final InsertImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10, Field<T11> value11, Field<T12> value12, Field<T13> value13, Field<T14> value14, Field<T15> value15, Field<T16> value16, Field<T17> value17, Field<T18> value18, Field<T19> value19, Field<T20> value20, Field<T21> value21, Field<T22> value22) {
        return this.values(new Field[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20, value21, value22});
    }

    public final InsertImpl values(Field<?> ... values2) {
        if (values2.length == 0) {
            return this.defaultValues();
        }
        if (!Tools.isEmpty(this.fields) && this.fields.length != values2.length) {
            throw new IllegalArgumentException("The number of values must match the number of fields");
        }
        ((InsertQueryImpl)this.getDelegate()).newRecord();
        if (Tools.isEmpty(this.fields)) {
            for (int i = 0; i < values2.length; ++i) {
                this.addValue((InsertQueryImpl)this.getDelegate(), null, i, values2[i]);
            }
        } else {
            for (int i = 0; i < this.fields.length; ++i) {
                this.addValue((InsertQueryImpl)this.getDelegate(), this.fields[i], i, values2[i]);
            }
        }
        return this;
    }

    public final <T1> InsertImpl columns(Field<T1> field1) {
        return this.columns(new Field[]{field1});
    }

    public final <T1, T2> InsertImpl columns(Field<T1> field1, Field<T2> field2) {
        return this.columns(new Field[]{field1, field2});
    }

    public final <T1, T2, T3> InsertImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3) {
        return this.columns(new Field[]{field1, field2, field3});
    }

    public final <T1, T2, T3, T4> InsertImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4) {
        return this.columns(new Field[]{field1, field2, field3, field4});
    }

    public final <T1, T2, T3, T4, T5> InsertImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5) {
        return this.columns(new Field[]{field1, field2, field3, field4, field5});
    }

    public final <T1, T2, T3, T4, T5, T6> InsertImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6) {
        return this.columns(new Field[]{field1, field2, field3, field4, field5, field6});
    }

    public final <T1, T2, T3, T4, T5, T6, T7> InsertImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7) {
        return this.columns(new Field[]{field1, field2, field3, field4, field5, field6, field7});
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8> InsertImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8) {
        return this.columns(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8});
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9> InsertImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9) {
        return this.columns(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9});
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> InsertImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10) {
        return this.columns(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10});
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> InsertImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11) {
        return this.columns(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11});
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> InsertImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12) {
        return this.columns(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12});
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> InsertImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13) {
        return this.columns(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13});
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> InsertImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14) {
        return this.columns(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14});
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> InsertImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15) {
        return this.columns(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15});
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> InsertImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16) {
        return this.columns(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16});
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> InsertImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17) {
        return this.columns(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17});
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> InsertImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18) {
        return this.columns(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18});
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> InsertImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19) {
        return this.columns(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19});
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> InsertImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20) {
        return this.columns(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20});
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> InsertImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21) {
        return this.columns(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21});
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> InsertImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21, Field<T22> field22) {
        return this.columns(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22});
    }

    public final InsertImpl columns(Field<?> ... f) {
        this.fields = Tools.isEmpty(f) ? this.into.fields() : f;
        return this;
    }

    public final InsertImpl columns(Collection<? extends Field<?>> f) {
        return this.columns((Field[])f.toArray(Tools.EMPTY_FIELD));
    }

    public final InsertImpl defaultValues() {
        ((InsertQueryImpl)this.getDelegate()).setDefaultValues();
        return this;
    }

    public final InsertImpl doUpdate() {
        this.doUpdateWhere = true;
        return this.onDuplicateKeyUpdate();
    }

    public final InsertImpl doNothing() {
        this.doUpdateWhere = true;
        return this.onDuplicateKeyIgnore();
    }

    public final InsertImpl onConflictOnConstraint(Constraint constraint) {
        ((InsertQueryImpl)this.getDelegate()).onConflictOnConstraint(constraint);
        return this;
    }

    public final InsertImpl onConflictOnConstraint(Name constraint) {
        ((InsertQueryImpl)this.getDelegate()).onConflictOnConstraint(constraint);
        return this;
    }

    public final InsertImpl onConflictOnConstraint(UniqueKey<R> constraint) {
        ((InsertQueryImpl)this.getDelegate()).onConflictOnConstraint(constraint);
        return this;
    }

    public final InsertImpl onConflict(Field<?> ... keys2) {
        return this.onConflict(Arrays.asList(keys2));
    }

    public final InsertImpl onConflict(Collection<? extends Field<?>> keys2) {
        ((InsertQueryImpl)this.getDelegate()).onConflict(keys2);
        return this;
    }

    public final InsertImpl onConflictDoNothing() {
        this.doUpdateWhere = true;
        ((InsertImpl)this.onConflict(new Field[0])).doNothing();
        return this;
    }

    public final InsertImpl onDuplicateKeyUpdate() {
        this.doUpdateWhere = true;
        this.onDuplicateKeyUpdate = true;
        ((InsertQueryImpl)this.getDelegate()).onDuplicateKeyUpdate(true);
        return this;
    }

    public final InsertImpl onDuplicateKeyIgnore() {
        this.doUpdateWhere = true;
        ((InsertQueryImpl)this.getDelegate()).onDuplicateKeyIgnore(true);
        return this;
    }

    public final <T> InsertImpl set(Field<T> field2, T value) {
        if (this.onDuplicateKeyUpdate) {
            ((InsertQueryImpl)this.getDelegate()).addValueForUpdate(field2, value);
        } else {
            ((InsertQueryImpl)this.getDelegate()).addValue(field2, value);
        }
        return this;
    }

    public final <T> InsertImpl set(Field<T> field2, Field<T> value) {
        if (this.onDuplicateKeyUpdate) {
            ((InsertQueryImpl)this.getDelegate()).addValueForUpdate(field2, value);
        } else {
            ((InsertQueryImpl)this.getDelegate()).addValue(field2, value);
        }
        return this;
    }

    public final <T> InsertImpl set(Field<T> field2, Select<? extends Record1<T>> value) {
        return this.set((Field)field2, value.asField());
    }

    public final <T> InsertImpl setNull(Field<T> field2) {
        return this.set((Field)field2, (Object)null);
    }

    public final InsertImpl set(Map<?, ?> map) {
        if (this.onDuplicateKeyUpdate) {
            ((InsertQueryImpl)this.getDelegate()).addValuesForUpdate(map);
        } else {
            ((InsertQueryImpl)this.getDelegate()).addValues(map);
        }
        return this;
    }

    public final InsertImpl set(Record record) {
        return this.set((Map)Tools.mapOfChangedValues(record));
    }

    public final InsertImpl setAllToExcluded() {
        for (Field field2 : ((InsertQueryImpl)this.getDelegate()).$columns()) {
            this.set(field2, DSL.excluded(field2));
        }
        return this;
    }

    public final InsertImpl and(Condition condition) {
        ((InsertQueryImpl)this.getDelegate()).addConditions(condition);
        return this;
    }

    public final InsertImpl and(Field<Boolean> condition) {
        return this.and(DSL.condition(condition));
    }

    public final InsertImpl and(SQL sql) {
        return this.and(DSL.condition(sql));
    }

    public final InsertImpl and(String sql) {
        return this.and(DSL.condition(sql));
    }

    public final InsertImpl and(String sql, Object ... bindings) {
        return this.and(DSL.condition(sql, bindings));
    }

    public final InsertImpl and(String sql, QueryPart ... parts) {
        return this.and(DSL.condition(sql, parts));
    }

    public final InsertImpl andNot(Condition condition) {
        return this.and(DSL.not(condition));
    }

    public final InsertImpl andNot(Field<Boolean> condition) {
        return this.and(DSL.not(DSL.condition(condition)));
    }

    public final InsertImpl andExists(Select<?> select) {
        return this.and(DSL.exists(select));
    }

    public final InsertImpl andNotExists(Select<?> select) {
        return this.and(DSL.notExists(select));
    }

    public final InsertImpl or(Condition condition) {
        ((InsertQueryImpl)this.getDelegate()).addConditions(Operator.OR, condition);
        return this;
    }

    public final InsertImpl or(Field<Boolean> condition) {
        return this.or(DSL.condition(condition));
    }

    public final InsertImpl or(SQL sql) {
        return this.or(DSL.condition(sql));
    }

    public final InsertImpl or(String sql) {
        return this.or(DSL.condition(sql));
    }

    public final InsertImpl or(String sql, Object ... bindings) {
        return this.or(DSL.condition(sql, bindings));
    }

    public final InsertImpl or(String sql, QueryPart ... parts) {
        return this.or(DSL.condition(sql, parts));
    }

    public final InsertImpl orNot(Condition condition) {
        return this.or(DSL.not(condition));
    }

    public final InsertImpl orNot(Field<Boolean> condition) {
        return this.or(DSL.not(DSL.condition(condition)));
    }

    public final InsertImpl orExists(Select<?> select) {
        return this.or(DSL.exists(select));
    }

    public final InsertImpl orNotExists(Select<?> select) {
        return this.or(DSL.notExists(select));
    }

    public final InsertImpl where(Condition condition) {
        if (this.doUpdateWhere) {
            ((InsertQueryImpl)this.getDelegate()).addConditions(condition);
        } else {
            ((InsertQueryImpl)this.getDelegate()).onConflictWhere(condition);
        }
        return this;
    }

    public final InsertImpl where(Condition ... conditions) {
        if (this.doUpdateWhere) {
            ((InsertQueryImpl)this.getDelegate()).addConditions(conditions);
        } else {
            ((InsertQueryImpl)this.getDelegate()).onConflictWhere(DSL.and(conditions));
        }
        return this;
    }

    public final InsertImpl where(Collection<? extends Condition> conditions) {
        if (this.doUpdateWhere) {
            ((InsertQueryImpl)this.getDelegate()).addConditions(conditions);
        } else {
            ((InsertQueryImpl)this.getDelegate()).onConflictWhere(DSL.and(conditions));
        }
        return this;
    }

    public final InsertImpl where(Field<Boolean> field2) {
        return this.where(DSL.condition(field2));
    }

    public final InsertImpl where(SQL sql) {
        return this.where(DSL.condition(sql));
    }

    public final InsertImpl where(String sql) {
        return this.where(DSL.condition(sql));
    }

    public final InsertImpl where(String sql, Object ... bindings) {
        return this.where(DSL.condition(sql, bindings));
    }

    public final InsertImpl where(String sql, QueryPart ... parts) {
        return this.where(DSL.condition(sql, parts));
    }

    public final InsertImpl whereExists(Select<?> select) {
        return this.where(DSL.exists(select));
    }

    public final InsertImpl whereNotExists(Select<?> select) {
        return this.where(DSL.notExists(select));
    }

    public final InsertImpl newRecord() {
        ((InsertQueryImpl)this.getDelegate()).newRecord();
        return this;
    }

    @Override
    public final InsertResultStep<R> returning() {
        ((InsertQueryImpl)this.getDelegate()).setReturning();
        return new InsertAsResultQuery((InsertQueryImpl)this.getDelegate(), this.returningResult);
    }

    @Override
    public final InsertResultStep<R> returning(SelectFieldOrAsterisk ... f) {
        ((InsertQueryImpl)this.getDelegate()).setReturning(f);
        return new InsertAsResultQuery((InsertQueryImpl)this.getDelegate(), this.returningResult);
    }

    @Override
    public final InsertResultStep<R> returning(Collection<? extends SelectFieldOrAsterisk> f) {
        ((InsertQueryImpl)this.getDelegate()).setReturning(f);
        return new InsertAsResultQuery((InsertQueryImpl)this.getDelegate(), this.returningResult);
    }

    @Override
    public final InsertResultStep<Record> returningResult(SelectFieldOrAsterisk ... f) {
        this.returningResult = true;
        ((InsertQueryImpl)this.getDelegate()).setReturning(f);
        return new InsertAsResultQuery<Record>((InsertQueryImpl)this.getDelegate(), this.returningResult);
    }

    @Override
    public final InsertResultStep<Record> returningResult(Collection<? extends SelectFieldOrAsterisk> f) {
        this.returningResult = true;
        ((InsertQueryImpl)this.getDelegate()).setReturning(f);
        return new InsertAsResultQuery<Record>((InsertQueryImpl)this.getDelegate(), this.returningResult);
    }

    @Override
    public final <T1> InsertResultStep<Record1<T1>> returningResult(SelectField<T1> field1) {
        return this.returningResult(new SelectField[]{field1});
    }

    @Override
    public final <T1, T2> InsertResultStep<Record2<T1, T2>> returningResult(SelectField<T1> field1, SelectField<T2> field2) {
        return this.returningResult(new SelectField[]{field1, field2});
    }

    @Override
    public final <T1, T2, T3> InsertResultStep<Record3<T1, T2, T3>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3) {
        return this.returningResult(new SelectField[]{field1, field2, field3});
    }

    @Override
    public final <T1, T2, T3, T4> InsertResultStep<Record4<T1, T2, T3, T4>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4});
    }

    @Override
    public final <T1, T2, T3, T4, T5> InsertResultStep<Record5<T1, T2, T3, T4, T5>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6> InsertResultStep<Record6<T1, T2, T3, T4, T5, T6>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7> InsertResultStep<Record7<T1, T2, T3, T4, T5, T6, T7>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6, field7});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8> InsertResultStep<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9> InsertResultStep<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> InsertResultStep<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> InsertResultStep<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> InsertResultStep<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> InsertResultStep<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> InsertResultStep<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> InsertResultStep<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> InsertResultStep<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> InsertResultStep<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> InsertResultStep<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> InsertResultStep<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> InsertResultStep<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> InsertResultStep<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20, SelectField<T21> field21) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> InsertResultStep<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20, SelectField<T21> field21, SelectField<T22> field22) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22});
    }

    @Override
    public final WithImpl $with() {
        return ((InsertQueryImpl)this.getDelegate()).$with();
    }

    @Override
    public final Table<R> $into() {
        return ((InsertQueryImpl)this.getDelegate()).$into();
    }

    @Override
    public final QOM.Insert<?> $into(Table<?> newInto) {
        return ((InsertQueryImpl)this.getDelegate()).$into(newInto);
    }

    @Override
    public final QOM.UnmodifiableList<? extends Field<?>> $columns() {
        return ((InsertQueryImpl)this.getDelegate()).$columns();
    }

    @Override
    public final QOM.Insert<?> $columns(Collection<? extends Field<?>> columns) {
        return ((InsertQueryImpl)this.getDelegate()).$columns(columns);
    }

    @Override
    public final Select<?> $select() {
        return ((InsertQueryImpl)this.getDelegate()).$select();
    }

    @Override
    public final QOM.Insert<?> $select(Select<?> select) {
        return ((InsertQueryImpl)this.getDelegate()).$select(select);
    }

    @Override
    public final boolean $defaultValues() {
        return ((InsertQueryImpl)this.getDelegate()).$defaultValues();
    }

    @Override
    public final QOM.Insert<?> $defaultValues(boolean defaultValues) {
        return ((InsertQueryImpl)this.getDelegate()).$defaultValues(defaultValues);
    }

    @Override
    public final QOM.UnmodifiableList<? extends Row> $values() {
        return ((InsertQueryImpl)this.getDelegate()).$values();
    }

    @Override
    public final QOM.Insert<?> $values(Collection<? extends Row> values2) {
        return ((InsertQueryImpl)this.getDelegate()).$values(values2);
    }

    @Override
    public final boolean $onDuplicateKeyIgnore() {
        return ((InsertQueryImpl)this.getDelegate()).$onDuplicateKeyIgnore();
    }

    @Override
    public final QOM.Insert<?> $onDuplicateKeyIgnore(boolean onDuplicateKeyIgnore) {
        return ((InsertQueryImpl)this.getDelegate()).$onDuplicateKeyIgnore(onDuplicateKeyIgnore);
    }

    @Override
    public boolean $onDuplicateKeyUpdate() {
        return ((InsertQueryImpl)this.getDelegate()).$onDuplicateKeyUpdate();
    }

    @Override
    public final QOM.Insert<?> $onDuplicateKeyUpdate(boolean onDuplicateKeyUpdate) {
        return ((InsertQueryImpl)this.getDelegate()).$onDuplicateKeyUpdate(onDuplicateKeyUpdate);
    }

    @Override
    public final QOM.UnmodifiableList<? extends Field<?>> $onConflict() {
        return ((InsertQueryImpl)this.getDelegate()).$onConflict();
    }

    @Override
    public final QOM.Insert<?> $onConflict(Collection<? extends Field<?>> onConflictFields) {
        return ((InsertQueryImpl)this.getDelegate()).$onConflict(onConflictFields);
    }

    @Override
    public final Condition $onConflictWhere() {
        return ((InsertQueryImpl)this.getDelegate()).$onConflictWhere();
    }

    @Override
    public final QOM.Insert<?> $onConflictWhere(Condition where) {
        return ((InsertQueryImpl)this.getDelegate()).$onConflictWhere(where);
    }

    @Override
    public final QOM.UnmodifiableMap<? extends FieldOrRow, ? extends FieldOrRowOrSelect> $updateSet() {
        return ((InsertQueryImpl)this.getDelegate()).$updateSet();
    }

    @Override
    public final QOM.Insert<?> $updateSet(Map<? extends FieldOrRow, ? extends FieldOrRowOrSelect> updateSet) {
        return ((InsertQueryImpl)this.getDelegate()).$updateSet(updateSet);
    }

    @Override
    public final Condition $updateWhere() {
        return ((InsertQueryImpl)this.getDelegate()).$updateWhere();
    }

    @Override
    public final QOM.Insert<?> $updateWhere(Condition where) {
        return ((InsertQueryImpl)this.getDelegate()).$updateWhere(where);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return ((InsertQueryImpl)this.getDelegate()).$traverse(traverser);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return ((InsertQueryImpl)this.getDelegate()).$replace(replacer);
    }
}

