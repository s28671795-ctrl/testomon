/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Collection;
import java.util.Map;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.FieldOrRow;
import org.jooq.FieldOrRowOrSelect;
import org.jooq.Name;
import org.jooq.Operator;
import org.jooq.OrderField;
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
import org.jooq.SortField;
import org.jooq.Table;
import org.jooq.TableLike;
import org.jooq.Traverser;
import org.jooq.UpdateConditionStep;
import org.jooq.UpdateFromStep;
import org.jooq.UpdateResultStep;
import org.jooq.UpdateSetFirstStep;
import org.jooq.UpdateSetMoreStep;
import org.jooq.UpdateWhereStep;
import org.jooq.impl.AbstractDelegatingDMLQuery;
import org.jooq.impl.DSL;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;
import org.jooq.impl.UpdateAsResultQuery;
import org.jooq.impl.UpdateQueryImpl;
import org.jooq.impl.WithImpl;

final class UpdateImpl<R extends Record>
extends AbstractDelegatingDMLQuery<R, UpdateQueryImpl<R>>
implements UpdateSetFirstStep<R>,
UpdateSetMoreStep<R>,
UpdateConditionStep<R>,
QOM.Update<R> {
    private boolean returningResult;

    UpdateImpl(Configuration configuration, WithImpl with, Table<R> table) {
        super(new UpdateQueryImpl<R>(configuration, with, table));
    }

    @Override
    public final <T> UpdateImpl<R> set(Field<T> field2, T value) {
        ((UpdateQueryImpl)this.getDelegate()).addValue(field2, value);
        return this;
    }

    @Override
    public final <T> UpdateImpl<R> set(Field<T> field2, Field<T> value) {
        ((UpdateQueryImpl)this.getDelegate()).addValue(field2, value);
        return this;
    }

    @Override
    public final <T> UpdateImpl<R> set(Field<T> field2, Select<? extends Record1<T>> value) {
        if (value == null) {
            return this.set((Field)field2, (Object)null);
        }
        return this.set((Field)field2, value.asField());
    }

    @Override
    public final <T> UpdateImpl<R> setNull(Field<T> field2) {
        return this.set((Field)field2, (Object)null);
    }

    @Override
    public final UpdateImpl<R> set(Map<?, ?> map) {
        ((UpdateQueryImpl)this.getDelegate()).addValues(map);
        return this;
    }

    @Override
    public final UpdateImpl<R> set(Record record) {
        return this.set((Map)Tools.mapOfChangedValues(record));
    }

    @Override
    public final UpdateFromStep<R> set(RowN row, RowN value) {
        ((UpdateQueryImpl)this.getDelegate()).addValues(row, value);
        return this;
    }

    @Override
    public final <T1> UpdateFromStep<R> set(Row1<T1> row, Row1<T1> value) {
        ((UpdateQueryImpl)this.getDelegate()).addValues(row, value);
        return this;
    }

    @Override
    public final <T1, T2> UpdateFromStep<R> set(Row2<T1, T2> row, Row2<T1, T2> value) {
        ((UpdateQueryImpl)this.getDelegate()).addValues(row, value);
        return this;
    }

    @Override
    public final <T1, T2, T3> UpdateFromStep<R> set(Row3<T1, T2, T3> row, Row3<T1, T2, T3> value) {
        ((UpdateQueryImpl)this.getDelegate()).addValues(row, value);
        return this;
    }

    @Override
    public final <T1, T2, T3, T4> UpdateFromStep<R> set(Row4<T1, T2, T3, T4> row, Row4<T1, T2, T3, T4> value) {
        ((UpdateQueryImpl)this.getDelegate()).addValues(row, value);
        return this;
    }

    @Override
    public final <T1, T2, T3, T4, T5> UpdateFromStep<R> set(Row5<T1, T2, T3, T4, T5> row, Row5<T1, T2, T3, T4, T5> value) {
        ((UpdateQueryImpl)this.getDelegate()).addValues(row, value);
        return this;
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6> UpdateFromStep<R> set(Row6<T1, T2, T3, T4, T5, T6> row, Row6<T1, T2, T3, T4, T5, T6> value) {
        ((UpdateQueryImpl)this.getDelegate()).addValues(row, value);
        return this;
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7> UpdateFromStep<R> set(Row7<T1, T2, T3, T4, T5, T6, T7> row, Row7<T1, T2, T3, T4, T5, T6, T7> value) {
        ((UpdateQueryImpl)this.getDelegate()).addValues(row, value);
        return this;
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8> UpdateFromStep<R> set(Row8<T1, T2, T3, T4, T5, T6, T7, T8> row, Row8<T1, T2, T3, T4, T5, T6, T7, T8> value) {
        ((UpdateQueryImpl)this.getDelegate()).addValues(row, value);
        return this;
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9> UpdateFromStep<R> set(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> row, Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> value) {
        ((UpdateQueryImpl)this.getDelegate()).addValues(row, value);
        return this;
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> UpdateFromStep<R> set(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row, Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> value) {
        ((UpdateQueryImpl)this.getDelegate()).addValues(row, value);
        return this;
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> UpdateFromStep<R> set(Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> row, Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> value) {
        ((UpdateQueryImpl)this.getDelegate()).addValues(row, value);
        return this;
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> UpdateFromStep<R> set(Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> row, Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> value) {
        ((UpdateQueryImpl)this.getDelegate()).addValues(row, value);
        return this;
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> UpdateFromStep<R> set(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> row, Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> value) {
        ((UpdateQueryImpl)this.getDelegate()).addValues(row, value);
        return this;
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> UpdateFromStep<R> set(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> row, Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> value) {
        ((UpdateQueryImpl)this.getDelegate()).addValues(row, value);
        return this;
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> UpdateFromStep<R> set(Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> row, Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> value) {
        ((UpdateQueryImpl)this.getDelegate()).addValues(row, value);
        return this;
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> UpdateFromStep<R> set(Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> row, Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> value) {
        ((UpdateQueryImpl)this.getDelegate()).addValues(row, value);
        return this;
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> UpdateFromStep<R> set(Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> row, Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> value) {
        ((UpdateQueryImpl)this.getDelegate()).addValues(row, value);
        return this;
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> UpdateFromStep<R> set(Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> row, Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> value) {
        ((UpdateQueryImpl)this.getDelegate()).addValues(row, value);
        return this;
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> UpdateFromStep<R> set(Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> row, Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> value) {
        ((UpdateQueryImpl)this.getDelegate()).addValues(row, value);
        return this;
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> UpdateFromStep<R> set(Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> row, Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> value) {
        ((UpdateQueryImpl)this.getDelegate()).addValues(row, value);
        return this;
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> UpdateFromStep<R> set(Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> row, Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> value) {
        ((UpdateQueryImpl)this.getDelegate()).addValues(row, value);
        return this;
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> UpdateFromStep<R> set(Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> row, Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> value) {
        ((UpdateQueryImpl)this.getDelegate()).addValues(row, value);
        return this;
    }

    @Override
    public final UpdateFromStep<R> set(RowN row, Select<? extends Record> select) {
        ((UpdateQueryImpl)this.getDelegate()).addValues(row, select);
        return this;
    }

    @Override
    public final <T1> UpdateFromStep<R> set(Row1<T1> row, Select<? extends Record1<T1>> select) {
        ((UpdateQueryImpl)this.getDelegate()).addValues(row, select);
        return this;
    }

    @Override
    public final <T1, T2> UpdateFromStep<R> set(Row2<T1, T2> row, Select<? extends Record2<T1, T2>> select) {
        ((UpdateQueryImpl)this.getDelegate()).addValues(row, select);
        return this;
    }

    @Override
    public final <T1, T2, T3> UpdateFromStep<R> set(Row3<T1, T2, T3> row, Select<? extends Record3<T1, T2, T3>> select) {
        ((UpdateQueryImpl)this.getDelegate()).addValues(row, select);
        return this;
    }

    @Override
    public final <T1, T2, T3, T4> UpdateFromStep<R> set(Row4<T1, T2, T3, T4> row, Select<? extends Record4<T1, T2, T3, T4>> select) {
        ((UpdateQueryImpl)this.getDelegate()).addValues(row, select);
        return this;
    }

    @Override
    public final <T1, T2, T3, T4, T5> UpdateFromStep<R> set(Row5<T1, T2, T3, T4, T5> row, Select<? extends Record5<T1, T2, T3, T4, T5>> select) {
        ((UpdateQueryImpl)this.getDelegate()).addValues(row, select);
        return this;
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6> UpdateFromStep<R> set(Row6<T1, T2, T3, T4, T5, T6> row, Select<? extends Record6<T1, T2, T3, T4, T5, T6>> select) {
        ((UpdateQueryImpl)this.getDelegate()).addValues(row, select);
        return this;
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7> UpdateFromStep<R> set(Row7<T1, T2, T3, T4, T5, T6, T7> row, Select<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> select) {
        ((UpdateQueryImpl)this.getDelegate()).addValues(row, select);
        return this;
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8> UpdateFromStep<R> set(Row8<T1, T2, T3, T4, T5, T6, T7, T8> row, Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> select) {
        ((UpdateQueryImpl)this.getDelegate()).addValues(row, select);
        return this;
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9> UpdateFromStep<R> set(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> row, Select<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> select) {
        ((UpdateQueryImpl)this.getDelegate()).addValues(row, select);
        return this;
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> UpdateFromStep<R> set(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row, Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> select) {
        ((UpdateQueryImpl)this.getDelegate()).addValues(row, select);
        return this;
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> UpdateFromStep<R> set(Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> row, Select<? extends Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> select) {
        ((UpdateQueryImpl)this.getDelegate()).addValues(row, select);
        return this;
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> UpdateFromStep<R> set(Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> row, Select<? extends Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> select) {
        ((UpdateQueryImpl)this.getDelegate()).addValues(row, select);
        return this;
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> UpdateFromStep<R> set(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> row, Select<? extends Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> select) {
        ((UpdateQueryImpl)this.getDelegate()).addValues(row, select);
        return this;
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> UpdateFromStep<R> set(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> row, Select<? extends Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> select) {
        ((UpdateQueryImpl)this.getDelegate()).addValues(row, select);
        return this;
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> UpdateFromStep<R> set(Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> row, Select<? extends Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> select) {
        ((UpdateQueryImpl)this.getDelegate()).addValues(row, select);
        return this;
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> UpdateFromStep<R> set(Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> row, Select<? extends Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> select) {
        ((UpdateQueryImpl)this.getDelegate()).addValues(row, select);
        return this;
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> UpdateFromStep<R> set(Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> row, Select<? extends Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> select) {
        ((UpdateQueryImpl)this.getDelegate()).addValues(row, select);
        return this;
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> UpdateFromStep<R> set(Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> row, Select<? extends Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> select) {
        ((UpdateQueryImpl)this.getDelegate()).addValues(row, select);
        return this;
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> UpdateFromStep<R> set(Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> row, Select<? extends Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> select) {
        ((UpdateQueryImpl)this.getDelegate()).addValues(row, select);
        return this;
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> UpdateFromStep<R> set(Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> row, Select<? extends Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> select) {
        ((UpdateQueryImpl)this.getDelegate()).addValues(row, select);
        return this;
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> UpdateFromStep<R> set(Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> row, Select<? extends Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> select) {
        ((UpdateQueryImpl)this.getDelegate()).addValues(row, select);
        return this;
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> UpdateFromStep<R> set(Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> row, Select<? extends Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> select) {
        ((UpdateQueryImpl)this.getDelegate()).addValues(row, select);
        return this;
    }

    @Override
    public final UpdateWhereStep<R> from(TableLike<?> table) {
        ((UpdateQueryImpl)this.getDelegate()).addFrom(table);
        return this;
    }

    @Override
    public final UpdateWhereStep<R> from(TableLike<?> ... tables) {
        ((UpdateQueryImpl)this.getDelegate()).addFrom(tables);
        return this;
    }

    @Override
    public final UpdateWhereStep<R> from(Collection<? extends TableLike<?>> tables) {
        ((UpdateQueryImpl)this.getDelegate()).addFrom(tables);
        return this;
    }

    @Override
    public final UpdateWhereStep<R> from(SQL sql) {
        return this.from((TableLike<?>)DSL.table(sql));
    }

    @Override
    public final UpdateWhereStep<R> from(String sql) {
        return this.from((TableLike<?>)DSL.table(sql));
    }

    @Override
    public final UpdateWhereStep<R> from(String sql, Object ... bindings) {
        return this.from((TableLike<?>)DSL.table(sql, bindings));
    }

    @Override
    public final UpdateWhereStep<R> from(String sql, QueryPart ... parts) {
        return this.from((TableLike<?>)DSL.table(sql, parts));
    }

    @Override
    public final UpdateWhereStep<R> from(Name name) {
        return this.from((TableLike<?>)DSL.table(name));
    }

    @Override
    public final UpdateImpl<R> where(Condition conditions) {
        ((UpdateQueryImpl)this.getDelegate()).addConditions(conditions);
        return this;
    }

    @Override
    public final UpdateImpl<R> where(Condition ... conditions) {
        ((UpdateQueryImpl)this.getDelegate()).addConditions(conditions);
        return this;
    }

    @Override
    public final UpdateImpl<R> where(Collection<? extends Condition> conditions) {
        ((UpdateQueryImpl)this.getDelegate()).addConditions(conditions);
        return this;
    }

    @Override
    public final UpdateImpl<R> where(Field<Boolean> condition) {
        return this.where(DSL.condition(condition));
    }

    @Override
    public final UpdateImpl<R> where(SQL sql) {
        return this.where(DSL.condition(sql));
    }

    @Override
    public final UpdateImpl<R> where(String sql) {
        return this.where(DSL.condition(sql));
    }

    @Override
    public final UpdateImpl<R> where(String sql, Object ... bindings) {
        return this.where(DSL.condition(sql, bindings));
    }

    @Override
    public final UpdateImpl<R> where(String sql, QueryPart ... parts) {
        return this.where(DSL.condition(sql, parts));
    }

    @Override
    public final UpdateImpl<R> whereExists(Select<?> select) {
        return this.andExists((Select)select);
    }

    @Override
    public final UpdateImpl<R> whereNotExists(Select<?> select) {
        return this.andNotExists((Select)select);
    }

    @Override
    public final UpdateImpl<R> and(Condition condition) {
        ((UpdateQueryImpl)this.getDelegate()).addConditions(condition);
        return this;
    }

    @Override
    public final UpdateImpl<R> and(Field<Boolean> condition) {
        return this.and(DSL.condition(condition));
    }

    @Override
    public final UpdateImpl<R> and(SQL sql) {
        return this.and(DSL.condition(sql));
    }

    @Override
    public final UpdateImpl<R> and(String sql) {
        return this.and(DSL.condition(sql));
    }

    @Override
    public final UpdateImpl<R> and(String sql, Object ... bindings) {
        return this.and(DSL.condition(sql, bindings));
    }

    @Override
    public final UpdateImpl<R> and(String sql, QueryPart ... parts) {
        return this.and(DSL.condition(sql, parts));
    }

    @Override
    public final UpdateImpl<R> andNot(Condition condition) {
        return this.and(condition.not());
    }

    @Override
    public final UpdateImpl<R> andNot(Field<Boolean> condition) {
        return this.andNot(DSL.condition(condition));
    }

    @Override
    public final UpdateImpl<R> andExists(Select<?> select) {
        return this.and(DSL.exists(select));
    }

    @Override
    public final UpdateImpl<R> andNotExists(Select<?> select) {
        return this.and(DSL.notExists(select));
    }

    @Override
    public final UpdateImpl<R> or(Condition condition) {
        ((UpdateQueryImpl)this.getDelegate()).addConditions(Operator.OR, condition);
        return this;
    }

    @Override
    public final UpdateImpl<R> or(Field<Boolean> condition) {
        return this.or(DSL.condition(condition));
    }

    @Override
    public final UpdateImpl<R> or(SQL sql) {
        return this.or(DSL.condition(sql));
    }

    @Override
    public final UpdateImpl<R> or(String sql) {
        return this.or(DSL.condition(sql));
    }

    @Override
    public final UpdateImpl<R> or(String sql, Object ... bindings) {
        return this.or(DSL.condition(sql, bindings));
    }

    @Override
    public final UpdateImpl<R> or(String sql, QueryPart ... parts) {
        return this.or(DSL.condition(sql, parts));
    }

    @Override
    public final UpdateImpl<R> orNot(Condition condition) {
        return this.or(condition.not());
    }

    @Override
    public final UpdateImpl<R> orNot(Field<Boolean> condition) {
        return this.orNot(DSL.condition(condition));
    }

    @Override
    public final UpdateImpl<R> orExists(Select<?> select) {
        return this.or(DSL.exists(select));
    }

    @Override
    public final UpdateImpl<R> orNotExists(Select<?> select) {
        return this.or(DSL.notExists(select));
    }

    @Override
    public final UpdateImpl<R> orderBy(OrderField<?> ... fields) {
        ((UpdateQueryImpl)this.getDelegate()).addOrderBy(fields);
        return this;
    }

    @Override
    public final UpdateImpl<R> orderBy(Collection<? extends OrderField<?>> fields) {
        ((UpdateQueryImpl)this.getDelegate()).addOrderBy(fields);
        return this;
    }

    @Override
    public final UpdateImpl<R> orderBy(int ... fieldIndexes) {
        return this.orderBy(Tools.map(fieldIndexes, v -> DSL.inline(v)));
    }

    @Override
    public final UpdateImpl<R> limit(Number limit) {
        ((UpdateQueryImpl)this.getDelegate()).addLimit(limit);
        return this;
    }

    @Override
    public final UpdateImpl<R> limit(Field<? extends Number> limit) {
        ((UpdateQueryImpl)this.getDelegate()).addLimit(limit);
        return this;
    }

    @Override
    public final UpdateResultStep<R> returning() {
        ((UpdateQueryImpl)this.getDelegate()).setReturning();
        return new UpdateAsResultQuery((UpdateQueryImpl)this.getDelegate(), this.returningResult);
    }

    @Override
    public final UpdateResultStep<R> returning(SelectFieldOrAsterisk ... f) {
        ((UpdateQueryImpl)this.getDelegate()).setReturning(f);
        return new UpdateAsResultQuery((UpdateQueryImpl)this.getDelegate(), this.returningResult);
    }

    @Override
    public final UpdateResultStep<R> returning(Collection<? extends SelectFieldOrAsterisk> f) {
        ((UpdateQueryImpl)this.getDelegate()).setReturning(f);
        return new UpdateAsResultQuery((UpdateQueryImpl)this.getDelegate(), this.returningResult);
    }

    @Override
    public final UpdateResultStep<Record> returningResult(SelectFieldOrAsterisk ... f) {
        this.returningResult = true;
        ((UpdateQueryImpl)this.getDelegate()).setReturning(f);
        return new UpdateAsResultQuery<Record>((UpdateQueryImpl)this.getDelegate(), this.returningResult);
    }

    @Override
    public final UpdateResultStep<Record> returningResult(Collection<? extends SelectFieldOrAsterisk> f) {
        this.returningResult = true;
        ((UpdateQueryImpl)this.getDelegate()).setReturning(f);
        return new UpdateAsResultQuery<Record>((UpdateQueryImpl)this.getDelegate(), this.returningResult);
    }

    @Override
    public final <T1> UpdateResultStep<Record1<T1>> returningResult(SelectField<T1> field1) {
        return this.returningResult(new SelectField[]{field1});
    }

    @Override
    public final <T1, T2> UpdateResultStep<Record2<T1, T2>> returningResult(SelectField<T1> field1, SelectField<T2> field2) {
        return this.returningResult(new SelectField[]{field1, field2});
    }

    @Override
    public final <T1, T2, T3> UpdateResultStep<Record3<T1, T2, T3>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3) {
        return this.returningResult(new SelectField[]{field1, field2, field3});
    }

    @Override
    public final <T1, T2, T3, T4> UpdateResultStep<Record4<T1, T2, T3, T4>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4});
    }

    @Override
    public final <T1, T2, T3, T4, T5> UpdateResultStep<Record5<T1, T2, T3, T4, T5>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6> UpdateResultStep<Record6<T1, T2, T3, T4, T5, T6>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7> UpdateResultStep<Record7<T1, T2, T3, T4, T5, T6, T7>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6, field7});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8> UpdateResultStep<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9> UpdateResultStep<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> UpdateResultStep<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> UpdateResultStep<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> UpdateResultStep<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> UpdateResultStep<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> UpdateResultStep<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> UpdateResultStep<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> UpdateResultStep<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> UpdateResultStep<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> UpdateResultStep<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> UpdateResultStep<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> UpdateResultStep<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> UpdateResultStep<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20, SelectField<T21> field21) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> UpdateResultStep<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20, SelectField<T21> field21, SelectField<T22> field22) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22});
    }

    @Override
    public final QOM.With $with() {
        return ((UpdateQueryImpl)this.getDelegate()).$with();
    }

    @Override
    public final Table<R> $table() {
        return ((UpdateQueryImpl)this.getDelegate()).$table();
    }

    @Override
    public final QOM.Update<?> $table(Table<?> table) {
        return ((UpdateQueryImpl)this.getDelegate()).$table(table);
    }

    @Override
    public final QOM.UnmodifiableList<? extends Table<?>> $from() {
        return ((UpdateQueryImpl)this.getDelegate()).$from();
    }

    @Override
    public final QOM.Update<R> $from(Collection<? extends Table<?>> using2) {
        return ((UpdateQueryImpl)this.getDelegate()).$from(using2);
    }

    @Override
    public final QOM.UnmodifiableMap<? extends FieldOrRow, ? extends FieldOrRowOrSelect> $set() {
        return ((UpdateQueryImpl)this.getDelegate()).$set();
    }

    @Override
    public final QOM.Update<R> $set(Map<? extends FieldOrRow, ? extends FieldOrRowOrSelect> set) {
        return ((UpdateQueryImpl)this.getDelegate()).$set(set);
    }

    @Override
    public final Condition $where() {
        return ((UpdateQueryImpl)this.getDelegate()).$where();
    }

    @Override
    public final QOM.Update<R> $where(Condition condition) {
        return ((UpdateQueryImpl)this.getDelegate()).$where(condition);
    }

    @Override
    public final QOM.UnmodifiableList<? extends SortField<?>> $orderBy() {
        return ((UpdateQueryImpl)this.getDelegate()).$orderBy();
    }

    @Override
    public final QOM.Update<R> $orderBy(Collection<? extends SortField<?>> orderBy) {
        return ((UpdateQueryImpl)this.getDelegate()).$orderBy(orderBy);
    }

    @Override
    public final Field<? extends Number> $limit() {
        return ((UpdateQueryImpl)this.getDelegate()).$limit();
    }

    @Override
    public final QOM.Update<R> $limit(Field<? extends Number> limit) {
        return ((UpdateQueryImpl)this.getDelegate()).$limit(limit);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return ((UpdateQueryImpl)this.getDelegate()).$traverse(traverser);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return ((UpdateQueryImpl)this.getDelegate()).$replace(replacer);
    }
}

