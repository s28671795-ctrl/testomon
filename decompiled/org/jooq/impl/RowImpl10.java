/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Arrays;
import java.util.Collection;
import org.jooq.BetweenAndStep10;
import org.jooq.Comparator;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Function10;
import org.jooq.QuantifiedSelect;
import org.jooq.Record10;
import org.jooq.Result;
import org.jooq.Row;
import org.jooq.Row10;
import org.jooq.Select;
import org.jooq.SelectField;
import org.jooq.Statement;
import org.jooq.impl.AbstractRow;
import org.jooq.impl.DSL;
import org.jooq.impl.FieldsImpl;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.RowAssignment;
import org.jooq.impl.RowBetweenCondition;
import org.jooq.impl.RowInCondition;
import org.jooq.impl.RowIsDistinctFrom;
import org.jooq.impl.RowSubqueryCondition;
import org.jooq.impl.Tools;

final class RowImpl10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>
extends AbstractRow<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>>
implements Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> {
    RowImpl10(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10) {
        super(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10);
    }

    RowImpl10(FieldsImpl<?> fields) {
        super(fields);
    }

    @Override
    public final <U> SelectField<U> mapping(Function10<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? extends U> function) {
        return this.convertFrom((? super R r) -> r == null ? null : function.apply((Object)r.value1(), (Object)r.value2(), (Object)r.value3(), (Object)r.value4(), (Object)r.value5(), (Object)r.value6(), (Object)r.value7(), (Object)r.value8(), (Object)r.value9(), (Object)r.value10()));
    }

    @Override
    public final <U> SelectField<U> mapping(Class<U> uType, Function10<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? extends U> function) {
        return this.convertFrom(uType, (? super R r) -> r == null ? null : function.apply((Object)r.value1(), (Object)r.value2(), (Object)r.value3(), (Object)r.value4(), (Object)r.value5(), (Object)r.value6(), (Object)r.value7(), (Object)r.value8(), (Object)r.value9(), (Object)r.value10()));
    }

    @Override
    public final Field<T1> field1() {
        return this.fields.field(0);
    }

    @Override
    public final Field<T2> field2() {
        return this.fields.field(1);
    }

    @Override
    public final Field<T3> field3() {
        return this.fields.field(2);
    }

    @Override
    public final Field<T4> field4() {
        return this.fields.field(3);
    }

    @Override
    public final Field<T5> field5() {
        return this.fields.field(4);
    }

    @Override
    public final Field<T6> field6() {
        return this.fields.field(5);
    }

    @Override
    public final Field<T7> field7() {
        return this.fields.field(6);
    }

    @Override
    public final Field<T8> field8() {
        return this.fields.field(7);
    }

    @Override
    public final Field<T9> field9() {
        return this.fields.field(8);
    }

    @Override
    public final Field<T10> field10() {
        return this.fields.field(9);
    }

    @Override
    public final Condition compare(Comparator comparator, Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row) {
        return RowImpl10.compare(this, comparator, row);
    }

    @Override
    public final Condition compare(Comparator comparator, Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> record) {
        return RowImpl10.compare(this, comparator, record.valuesRow());
    }

    @Override
    public final Condition compare(Comparator comparator, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
        return this.compare(comparator, DSL.row(Tools.field(t1, this.dataType(0)), Tools.field(t2, this.dataType(1)), Tools.field(t3, this.dataType(2)), Tools.field(t4, this.dataType(3)), Tools.field(t5, this.dataType(4)), Tools.field(t6, this.dataType(5)), Tools.field(t7, this.dataType(6)), Tools.field(t8, this.dataType(7)), Tools.field(t9, this.dataType(8)), Tools.field(t10, this.dataType(9))));
    }

    @Override
    public final Condition compare(Comparator comparator, Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10) {
        return this.compare(comparator, DSL.row(Tools.nullSafe(t1, this.dataType(0)), Tools.nullSafe(t2, this.dataType(1)), Tools.nullSafe(t3, this.dataType(2)), Tools.nullSafe(t4, this.dataType(3)), Tools.nullSafe(t5, this.dataType(4)), Tools.nullSafe(t6, this.dataType(5)), Tools.nullSafe(t7, this.dataType(6)), Tools.nullSafe(t8, this.dataType(7)), Tools.nullSafe(t9, this.dataType(8)), Tools.nullSafe(t10, this.dataType(9))));
    }

    @Override
    public final Condition compare(Comparator comparator, Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> select) {
        return new RowSubqueryCondition((Row)this, select, comparator);
    }

    @Override
    public final Condition compare(Comparator comparator, QuantifiedSelect<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> select) {
        return new RowSubqueryCondition((Row)this, select, comparator);
    }

    @Override
    public final Condition equal(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row) {
        return this.compare(Comparator.EQUALS, row);
    }

    @Override
    public final Condition equal(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> record) {
        return this.compare(Comparator.EQUALS, record);
    }

    @Override
    public final Condition equal(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
        return this.compare(Comparator.EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
    }

    @Override
    public final Condition equal(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10) {
        return this.compare(Comparator.EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
    }

    @Override
    public final Condition eq(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row) {
        return this.equal(row);
    }

    @Override
    public final Condition eq(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> record) {
        return this.equal(record);
    }

    @Override
    public final Condition eq(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
        return this.equal(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
    }

    @Override
    public final Condition eq(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10) {
        return this.equal(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
    }

    @Override
    public final Condition notEqual(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row) {
        return this.compare(Comparator.NOT_EQUALS, row);
    }

    @Override
    public final Condition notEqual(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> record) {
        return this.compare(Comparator.NOT_EQUALS, record);
    }

    @Override
    public final Condition notEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
        return this.compare(Comparator.NOT_EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
    }

    @Override
    public final Condition notEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10) {
        return this.compare(Comparator.NOT_EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
    }

    @Override
    public final Condition ne(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row) {
        return this.notEqual(row);
    }

    @Override
    public final Condition ne(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> record) {
        return this.notEqual(record);
    }

    @Override
    public final Condition ne(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
        return this.notEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
    }

    @Override
    public final Condition ne(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10) {
        return this.notEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
    }

    @Override
    public final Condition lessThan(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row) {
        return this.compare(Comparator.LESS, row);
    }

    @Override
    public final Condition lessThan(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> record) {
        return this.compare(Comparator.LESS, record);
    }

    @Override
    public final Condition lessThan(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
        return this.compare(Comparator.LESS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
    }

    @Override
    public final Condition lessThan(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10) {
        return this.compare(Comparator.LESS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
    }

    @Override
    public final Condition lt(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row) {
        return this.lessThan(row);
    }

    @Override
    public final Condition lt(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> record) {
        return this.lessThan(record);
    }

    @Override
    public final Condition lt(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
        return this.lessThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
    }

    @Override
    public final Condition lt(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10) {
        return this.lessThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
    }

    @Override
    public final Condition lessOrEqual(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row) {
        return this.compare(Comparator.LESS_OR_EQUAL, row);
    }

    @Override
    public final Condition lessOrEqual(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> record) {
        return this.compare(Comparator.LESS_OR_EQUAL, record);
    }

    @Override
    public final Condition lessOrEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
        return this.compare(Comparator.LESS_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
    }

    @Override
    public final Condition lessOrEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10) {
        return this.compare(Comparator.LESS_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
    }

    @Override
    public final Condition le(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row) {
        return this.lessOrEqual(row);
    }

    @Override
    public final Condition le(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> record) {
        return this.lessOrEqual(record);
    }

    @Override
    public final Condition le(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
        return this.lessOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
    }

    @Override
    public final Condition le(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10) {
        return this.lessOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
    }

    @Override
    public final Condition greaterThan(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row) {
        return this.compare(Comparator.GREATER, row);
    }

    @Override
    public final Condition greaterThan(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> record) {
        return this.compare(Comparator.GREATER, record);
    }

    @Override
    public final Condition greaterThan(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
        return this.compare(Comparator.GREATER, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
    }

    @Override
    public final Condition greaterThan(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10) {
        return this.compare(Comparator.GREATER, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
    }

    @Override
    public final Condition gt(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row) {
        return this.greaterThan(row);
    }

    @Override
    public final Condition gt(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> record) {
        return this.greaterThan(record);
    }

    @Override
    public final Condition gt(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
        return this.greaterThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
    }

    @Override
    public final Condition gt(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10) {
        return this.greaterThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
    }

    @Override
    public final Condition greaterOrEqual(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row) {
        return this.compare(Comparator.GREATER_OR_EQUAL, row);
    }

    @Override
    public final Condition greaterOrEqual(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> record) {
        return this.compare(Comparator.GREATER_OR_EQUAL, record);
    }

    @Override
    public final Condition greaterOrEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
        return this.compare(Comparator.GREATER_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
    }

    @Override
    public final Condition greaterOrEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10) {
        return this.compare(Comparator.GREATER_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
    }

    @Override
    public final Condition ge(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row) {
        return this.greaterOrEqual(row);
    }

    @Override
    public final Condition ge(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> record) {
        return this.greaterOrEqual(record);
    }

    @Override
    public final Condition ge(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
        return this.greaterOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
    }

    @Override
    public final Condition ge(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10) {
        return this.greaterOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
    }

    @Override
    public final BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> between(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
        return this.between(DSL.row(Tools.field(t1, this.dataType(0)), Tools.field(t2, this.dataType(1)), Tools.field(t3, this.dataType(2)), Tools.field(t4, this.dataType(3)), Tools.field(t5, this.dataType(4)), Tools.field(t6, this.dataType(5)), Tools.field(t7, this.dataType(6)), Tools.field(t8, this.dataType(7)), Tools.field(t9, this.dataType(8)), Tools.field(t10, this.dataType(9))));
    }

    @Override
    public final BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> between(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10) {
        return this.between(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10));
    }

    @Override
    public final BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> between(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row) {
        return new RowBetweenCondition(this, row, false, false);
    }

    @Override
    public final BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> between(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> record) {
        return this.between((Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>)record.valuesRow());
    }

    @Override
    public final Condition between(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> minValue, Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> maxValue) {
        return this.between(minValue).and(maxValue);
    }

    @Override
    public final Condition between(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> minValue, Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> maxValue) {
        return this.between(minValue).and(maxValue);
    }

    @Override
    public final BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> betweenSymmetric(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
        return this.betweenSymmetric(DSL.row(Tools.field(t1, this.dataType(0)), Tools.field(t2, this.dataType(1)), Tools.field(t3, this.dataType(2)), Tools.field(t4, this.dataType(3)), Tools.field(t5, this.dataType(4)), Tools.field(t6, this.dataType(5)), Tools.field(t7, this.dataType(6)), Tools.field(t8, this.dataType(7)), Tools.field(t9, this.dataType(8)), Tools.field(t10, this.dataType(9))));
    }

    @Override
    public final BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> betweenSymmetric(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10) {
        return this.betweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10));
    }

    @Override
    public final BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> betweenSymmetric(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row) {
        return new RowBetweenCondition(this, row, false, true);
    }

    @Override
    public final BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> betweenSymmetric(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> record) {
        return this.betweenSymmetric((Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>)record.valuesRow());
    }

    @Override
    public final Condition betweenSymmetric(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> minValue, Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> maxValue) {
        return this.betweenSymmetric(minValue).and(maxValue);
    }

    @Override
    public final Condition betweenSymmetric(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> minValue, Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> maxValue) {
        return this.betweenSymmetric(minValue).and(maxValue);
    }

    @Override
    public final BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> notBetween(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
        return this.notBetween(DSL.row(Tools.field(t1, this.dataType(0)), Tools.field(t2, this.dataType(1)), Tools.field(t3, this.dataType(2)), Tools.field(t4, this.dataType(3)), Tools.field(t5, this.dataType(4)), Tools.field(t6, this.dataType(5)), Tools.field(t7, this.dataType(6)), Tools.field(t8, this.dataType(7)), Tools.field(t9, this.dataType(8)), Tools.field(t10, this.dataType(9))));
    }

    @Override
    public final BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> notBetween(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10) {
        return this.notBetween(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10));
    }

    @Override
    public final BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> notBetween(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row) {
        return new RowBetweenCondition(this, row, true, false);
    }

    @Override
    public final BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> notBetween(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> record) {
        return this.notBetween((Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>)record.valuesRow());
    }

    @Override
    public final Condition notBetween(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> minValue, Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> maxValue) {
        return this.notBetween(minValue).and(maxValue);
    }

    @Override
    public final Condition notBetween(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> minValue, Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> maxValue) {
        return this.notBetween(minValue).and(maxValue);
    }

    @Override
    public final BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> notBetweenSymmetric(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
        return this.notBetweenSymmetric(DSL.row(Tools.field(t1, this.dataType(0)), Tools.field(t2, this.dataType(1)), Tools.field(t3, this.dataType(2)), Tools.field(t4, this.dataType(3)), Tools.field(t5, this.dataType(4)), Tools.field(t6, this.dataType(5)), Tools.field(t7, this.dataType(6)), Tools.field(t8, this.dataType(7)), Tools.field(t9, this.dataType(8)), Tools.field(t10, this.dataType(9))));
    }

    @Override
    public final BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> notBetweenSymmetric(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10) {
        return this.notBetweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10));
    }

    @Override
    public final BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> notBetweenSymmetric(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row) {
        return new RowBetweenCondition(this, row, true, true);
    }

    @Override
    public final BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> notBetweenSymmetric(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> record) {
        return this.notBetweenSymmetric((Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>)record.valuesRow());
    }

    @Override
    public final Condition notBetweenSymmetric(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> minValue, Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> maxValue) {
        return this.notBetweenSymmetric(minValue).and(maxValue);
    }

    @Override
    public final Condition notBetweenSymmetric(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> minValue, Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> maxValue) {
        return this.notBetweenSymmetric(minValue).and(maxValue);
    }

    @Override
    public final Condition isNotDistinctFrom(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row) {
        return new RowIsDistinctFrom((Row)this, row, true);
    }

    @Override
    public final Condition isNotDistinctFrom(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> record) {
        return this.isNotDistinctFrom((Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>)record.valuesRow());
    }

    @Override
    public final Condition isNotDistinctFrom(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
        return this.isNotDistinctFrom(Tools.field(t1, this.dataType(0)), Tools.field(t2, this.dataType(1)), Tools.field(t3, this.dataType(2)), Tools.field(t4, this.dataType(3)), Tools.field(t5, this.dataType(4)), Tools.field(t6, this.dataType(5)), Tools.field(t7, this.dataType(6)), Tools.field(t8, this.dataType(7)), Tools.field(t9, this.dataType(8)), Tools.field(t10, this.dataType(9)));
    }

    @Override
    public final Condition isNotDistinctFrom(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10) {
        return this.isNotDistinctFrom(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10));
    }

    @Override
    public final Condition isNotDistinctFrom(Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> select) {
        return new RowIsDistinctFrom((Row)this, select, true);
    }

    @Override
    public final Condition isDistinctFrom(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row) {
        return new RowIsDistinctFrom((Row)this, row, false);
    }

    @Override
    public final Condition isDistinctFrom(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> record) {
        return this.isDistinctFrom((Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>)record.valuesRow());
    }

    @Override
    public final Condition isDistinctFrom(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
        return this.isDistinctFrom(Tools.field(t1, this.dataType(0)), Tools.field(t2, this.dataType(1)), Tools.field(t3, this.dataType(2)), Tools.field(t4, this.dataType(3)), Tools.field(t5, this.dataType(4)), Tools.field(t6, this.dataType(5)), Tools.field(t7, this.dataType(6)), Tools.field(t8, this.dataType(7)), Tools.field(t9, this.dataType(8)), Tools.field(t10, this.dataType(9)));
    }

    @Override
    public final Condition isDistinctFrom(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10) {
        return this.isDistinctFrom(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10));
    }

    @Override
    public final Condition isDistinctFrom(Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> select) {
        return new RowIsDistinctFrom((Row)this, select, false);
    }

    @Override
    public final Condition in(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> ... rows) {
        return this.in(Arrays.asList(rows));
    }

    @Override
    public final Condition in(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> ... records) {
        QueryPartList<Row> rows = new QueryPartList<Row>();
        for (Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> record : records) {
            rows.add(record.valuesRow());
        }
        return new RowInCondition(this, rows, false);
    }

    @Override
    public final Condition notIn(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> ... rows) {
        return this.notIn(Arrays.asList(rows));
    }

    @Override
    public final Condition notIn(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> ... records) {
        QueryPartList<Row> rows = new QueryPartList<Row>();
        for (Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> record : records) {
            rows.add(record.valuesRow());
        }
        return new RowInCondition(this, rows, true);
    }

    @Override
    public final Condition in(Collection<? extends Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> rows) {
        return new RowInCondition(this, new QueryPartList<Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>>(rows), false);
    }

    @Override
    public final Condition in(Result<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> result) {
        return new RowInCondition(this, new QueryPartList<Row>((Iterable<Row>)Tools.rows(result)), false);
    }

    @Override
    public final Condition notIn(Collection<? extends Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> rows) {
        return new RowInCondition(this, new QueryPartList<Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>>(rows), true);
    }

    @Override
    public final Condition notIn(Result<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> result) {
        return new RowInCondition(this, new QueryPartList<Row>((Iterable<Row>)Tools.rows(result)), true);
    }

    @Override
    public final Condition equal(Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> select) {
        return this.compare(Comparator.EQUALS, select);
    }

    @Override
    public final Condition equal(QuantifiedSelect<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> select) {
        return this.compare(Comparator.EQUALS, select);
    }

    @Override
    public final Condition eq(Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> select) {
        return this.equal(select);
    }

    @Override
    public final Condition eq(QuantifiedSelect<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> select) {
        return this.equal(select);
    }

    @Override
    public final Condition notEqual(Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> select) {
        return this.compare(Comparator.NOT_EQUALS, select);
    }

    @Override
    public final Condition notEqual(QuantifiedSelect<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> select) {
        return this.compare(Comparator.NOT_EQUALS, select);
    }

    @Override
    public final Condition ne(Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> select) {
        return this.notEqual(select);
    }

    @Override
    public final Condition ne(QuantifiedSelect<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> select) {
        return this.notEqual(select);
    }

    @Override
    public final Condition greaterThan(Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> select) {
        return this.compare(Comparator.GREATER, select);
    }

    @Override
    public final Condition greaterThan(QuantifiedSelect<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> select) {
        return this.compare(Comparator.GREATER, select);
    }

    @Override
    public final Condition gt(Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> select) {
        return this.greaterThan(select);
    }

    @Override
    public final Condition gt(QuantifiedSelect<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> select) {
        return this.greaterThan(select);
    }

    @Override
    public final Condition greaterOrEqual(Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> select) {
        return this.compare(Comparator.GREATER_OR_EQUAL, select);
    }

    @Override
    public final Condition greaterOrEqual(QuantifiedSelect<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> select) {
        return this.compare(Comparator.GREATER_OR_EQUAL, select);
    }

    @Override
    public final Condition ge(Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> select) {
        return this.greaterOrEqual(select);
    }

    @Override
    public final Condition ge(QuantifiedSelect<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> select) {
        return this.greaterOrEqual(select);
    }

    @Override
    public final Condition lessThan(Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> select) {
        return this.compare(Comparator.LESS, select);
    }

    @Override
    public final Condition lessThan(QuantifiedSelect<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> select) {
        return this.compare(Comparator.LESS, select);
    }

    @Override
    public final Condition lt(Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> select) {
        return this.lessThan(select);
    }

    @Override
    public final Condition lt(QuantifiedSelect<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> select) {
        return this.lessThan(select);
    }

    @Override
    public final Condition lessOrEqual(Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> select) {
        return this.compare(Comparator.LESS_OR_EQUAL, select);
    }

    @Override
    public final Condition lessOrEqual(QuantifiedSelect<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> select) {
        return this.compare(Comparator.LESS_OR_EQUAL, select);
    }

    @Override
    public final Condition le(Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> select) {
        return this.lessOrEqual(select);
    }

    @Override
    public final Condition le(QuantifiedSelect<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> select) {
        return this.lessOrEqual(select);
    }

    @Override
    public final Condition in(Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> select) {
        return this.compare(Comparator.IN, select);
    }

    @Override
    public final Condition notIn(Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> select) {
        return this.compare(Comparator.NOT_IN, select);
    }

    @Override
    public final Statement set(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row) {
        return new RowAssignment((Row)this, row);
    }

    @Override
    public final Statement set(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> record) {
        return this.set((Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>)record.valuesRow());
    }

    @Override
    public final Statement set(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
        return this.set(DSL.row(Tools.field(t1, this.dataType(0)), Tools.field(t2, this.dataType(1)), Tools.field(t3, this.dataType(2)), Tools.field(t4, this.dataType(3)), Tools.field(t5, this.dataType(4)), Tools.field(t6, this.dataType(5)), Tools.field(t7, this.dataType(6)), Tools.field(t8, this.dataType(7)), Tools.field(t9, this.dataType(8)), Tools.field(t10, this.dataType(9))));
    }

    @Override
    public final Statement set(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10) {
        return this.set(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10));
    }

    @Override
    public final Statement set(Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> value) {
        return new RowAssignment((Row)this, value);
    }
}

