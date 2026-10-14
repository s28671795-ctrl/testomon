/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Arrays;
import java.util.Collection;
import org.jooq.BetweenAndStep2;
import org.jooq.Comparator;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Function2;
import org.jooq.QuantifiedSelect;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.Row;
import org.jooq.Row2;
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
import org.jooq.impl.RowOverlaps;
import org.jooq.impl.RowSubqueryCondition;
import org.jooq.impl.Tools;

final class RowImpl2<T1, T2>
extends AbstractRow<Record2<T1, T2>>
implements Row2<T1, T2> {
    RowImpl2(SelectField<T1> field1, SelectField<T2> field2) {
        super(field1, field2);
    }

    RowImpl2(FieldsImpl<?> fields) {
        super(fields);
    }

    @Override
    public final <U> SelectField<U> mapping(Function2<? super T1, ? super T2, ? extends U> function) {
        return this.convertFrom((? super R r) -> r == null ? null : function.apply((Object)r.value1(), (Object)r.value2()));
    }

    @Override
    public final <U> SelectField<U> mapping(Class<U> uType, Function2<? super T1, ? super T2, ? extends U> function) {
        return this.convertFrom(uType, (? super R r) -> r == null ? null : function.apply((Object)r.value1(), (Object)r.value2()));
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
    public final Condition compare(Comparator comparator, Row2<T1, T2> row) {
        return RowImpl2.compare(this, comparator, row);
    }

    @Override
    public final Condition compare(Comparator comparator, Record2<T1, T2> record) {
        return RowImpl2.compare(this, comparator, record.valuesRow());
    }

    @Override
    public final Condition compare(Comparator comparator, T1 t1, T2 t2) {
        return this.compare(comparator, DSL.row(Tools.field(t1, this.dataType(0)), Tools.field(t2, this.dataType(1))));
    }

    @Override
    public final Condition compare(Comparator comparator, Field<T1> t1, Field<T2> t2) {
        return this.compare(comparator, DSL.row(Tools.nullSafe(t1, this.dataType(0)), Tools.nullSafe(t2, this.dataType(1))));
    }

    @Override
    public final Condition compare(Comparator comparator, Select<? extends Record2<T1, T2>> select) {
        return new RowSubqueryCondition((Row)this, select, comparator);
    }

    @Override
    public final Condition compare(Comparator comparator, QuantifiedSelect<? extends Record2<T1, T2>> select) {
        return new RowSubqueryCondition((Row)this, select, comparator);
    }

    @Override
    public final Condition equal(Row2<T1, T2> row) {
        return this.compare(Comparator.EQUALS, row);
    }

    @Override
    public final Condition equal(Record2<T1, T2> record) {
        return this.compare(Comparator.EQUALS, record);
    }

    @Override
    public final Condition equal(T1 t1, T2 t2) {
        return this.compare(Comparator.EQUALS, t1, t2);
    }

    @Override
    public final Condition equal(Field<T1> t1, Field<T2> t2) {
        return this.compare(Comparator.EQUALS, t1, t2);
    }

    @Override
    public final Condition eq(Row2<T1, T2> row) {
        return this.equal(row);
    }

    @Override
    public final Condition eq(Record2<T1, T2> record) {
        return this.equal(record);
    }

    @Override
    public final Condition eq(T1 t1, T2 t2) {
        return this.equal(t1, t2);
    }

    @Override
    public final Condition eq(Field<T1> t1, Field<T2> t2) {
        return this.equal(t1, t2);
    }

    @Override
    public final Condition notEqual(Row2<T1, T2> row) {
        return this.compare(Comparator.NOT_EQUALS, row);
    }

    @Override
    public final Condition notEqual(Record2<T1, T2> record) {
        return this.compare(Comparator.NOT_EQUALS, record);
    }

    @Override
    public final Condition notEqual(T1 t1, T2 t2) {
        return this.compare(Comparator.NOT_EQUALS, t1, t2);
    }

    @Override
    public final Condition notEqual(Field<T1> t1, Field<T2> t2) {
        return this.compare(Comparator.NOT_EQUALS, t1, t2);
    }

    @Override
    public final Condition ne(Row2<T1, T2> row) {
        return this.notEqual(row);
    }

    @Override
    public final Condition ne(Record2<T1, T2> record) {
        return this.notEqual(record);
    }

    @Override
    public final Condition ne(T1 t1, T2 t2) {
        return this.notEqual(t1, t2);
    }

    @Override
    public final Condition ne(Field<T1> t1, Field<T2> t2) {
        return this.notEqual(t1, t2);
    }

    @Override
    public final Condition lessThan(Row2<T1, T2> row) {
        return this.compare(Comparator.LESS, row);
    }

    @Override
    public final Condition lessThan(Record2<T1, T2> record) {
        return this.compare(Comparator.LESS, record);
    }

    @Override
    public final Condition lessThan(T1 t1, T2 t2) {
        return this.compare(Comparator.LESS, t1, t2);
    }

    @Override
    public final Condition lessThan(Field<T1> t1, Field<T2> t2) {
        return this.compare(Comparator.LESS, t1, t2);
    }

    @Override
    public final Condition lt(Row2<T1, T2> row) {
        return this.lessThan(row);
    }

    @Override
    public final Condition lt(Record2<T1, T2> record) {
        return this.lessThan(record);
    }

    @Override
    public final Condition lt(T1 t1, T2 t2) {
        return this.lessThan(t1, t2);
    }

    @Override
    public final Condition lt(Field<T1> t1, Field<T2> t2) {
        return this.lessThan(t1, t2);
    }

    @Override
    public final Condition lessOrEqual(Row2<T1, T2> row) {
        return this.compare(Comparator.LESS_OR_EQUAL, row);
    }

    @Override
    public final Condition lessOrEqual(Record2<T1, T2> record) {
        return this.compare(Comparator.LESS_OR_EQUAL, record);
    }

    @Override
    public final Condition lessOrEqual(T1 t1, T2 t2) {
        return this.compare(Comparator.LESS_OR_EQUAL, t1, t2);
    }

    @Override
    public final Condition lessOrEqual(Field<T1> t1, Field<T2> t2) {
        return this.compare(Comparator.LESS_OR_EQUAL, t1, t2);
    }

    @Override
    public final Condition le(Row2<T1, T2> row) {
        return this.lessOrEqual(row);
    }

    @Override
    public final Condition le(Record2<T1, T2> record) {
        return this.lessOrEqual(record);
    }

    @Override
    public final Condition le(T1 t1, T2 t2) {
        return this.lessOrEqual(t1, t2);
    }

    @Override
    public final Condition le(Field<T1> t1, Field<T2> t2) {
        return this.lessOrEqual(t1, t2);
    }

    @Override
    public final Condition greaterThan(Row2<T1, T2> row) {
        return this.compare(Comparator.GREATER, row);
    }

    @Override
    public final Condition greaterThan(Record2<T1, T2> record) {
        return this.compare(Comparator.GREATER, record);
    }

    @Override
    public final Condition greaterThan(T1 t1, T2 t2) {
        return this.compare(Comparator.GREATER, t1, t2);
    }

    @Override
    public final Condition greaterThan(Field<T1> t1, Field<T2> t2) {
        return this.compare(Comparator.GREATER, t1, t2);
    }

    @Override
    public final Condition gt(Row2<T1, T2> row) {
        return this.greaterThan(row);
    }

    @Override
    public final Condition gt(Record2<T1, T2> record) {
        return this.greaterThan(record);
    }

    @Override
    public final Condition gt(T1 t1, T2 t2) {
        return this.greaterThan(t1, t2);
    }

    @Override
    public final Condition gt(Field<T1> t1, Field<T2> t2) {
        return this.greaterThan(t1, t2);
    }

    @Override
    public final Condition greaterOrEqual(Row2<T1, T2> row) {
        return this.compare(Comparator.GREATER_OR_EQUAL, row);
    }

    @Override
    public final Condition greaterOrEqual(Record2<T1, T2> record) {
        return this.compare(Comparator.GREATER_OR_EQUAL, record);
    }

    @Override
    public final Condition greaterOrEqual(T1 t1, T2 t2) {
        return this.compare(Comparator.GREATER_OR_EQUAL, t1, t2);
    }

    @Override
    public final Condition greaterOrEqual(Field<T1> t1, Field<T2> t2) {
        return this.compare(Comparator.GREATER_OR_EQUAL, t1, t2);
    }

    @Override
    public final Condition ge(Row2<T1, T2> row) {
        return this.greaterOrEqual(row);
    }

    @Override
    public final Condition ge(Record2<T1, T2> record) {
        return this.greaterOrEqual(record);
    }

    @Override
    public final Condition ge(T1 t1, T2 t2) {
        return this.greaterOrEqual(t1, t2);
    }

    @Override
    public final Condition ge(Field<T1> t1, Field<T2> t2) {
        return this.greaterOrEqual(t1, t2);
    }

    @Override
    public final BetweenAndStep2<T1, T2> between(T1 t1, T2 t2) {
        return this.between(DSL.row(Tools.field(t1, this.dataType(0)), Tools.field(t2, this.dataType(1))));
    }

    @Override
    public final BetweenAndStep2<T1, T2> between(Field<T1> t1, Field<T2> t2) {
        return this.between(DSL.row(t1, t2));
    }

    @Override
    public final BetweenAndStep2<T1, T2> between(Row2<T1, T2> row) {
        return new RowBetweenCondition(this, row, false, false);
    }

    @Override
    public final BetweenAndStep2<T1, T2> between(Record2<T1, T2> record) {
        return this.between((Row2<T1, T2>)record.valuesRow());
    }

    @Override
    public final Condition between(Row2<T1, T2> minValue, Row2<T1, T2> maxValue) {
        return this.between(minValue).and(maxValue);
    }

    @Override
    public final Condition between(Record2<T1, T2> minValue, Record2<T1, T2> maxValue) {
        return this.between(minValue).and(maxValue);
    }

    @Override
    public final BetweenAndStep2<T1, T2> betweenSymmetric(T1 t1, T2 t2) {
        return this.betweenSymmetric(DSL.row(Tools.field(t1, this.dataType(0)), Tools.field(t2, this.dataType(1))));
    }

    @Override
    public final BetweenAndStep2<T1, T2> betweenSymmetric(Field<T1> t1, Field<T2> t2) {
        return this.betweenSymmetric(DSL.row(t1, t2));
    }

    @Override
    public final BetweenAndStep2<T1, T2> betweenSymmetric(Row2<T1, T2> row) {
        return new RowBetweenCondition(this, row, false, true);
    }

    @Override
    public final BetweenAndStep2<T1, T2> betweenSymmetric(Record2<T1, T2> record) {
        return this.betweenSymmetric((Row2<T1, T2>)record.valuesRow());
    }

    @Override
    public final Condition betweenSymmetric(Row2<T1, T2> minValue, Row2<T1, T2> maxValue) {
        return this.betweenSymmetric(minValue).and(maxValue);
    }

    @Override
    public final Condition betweenSymmetric(Record2<T1, T2> minValue, Record2<T1, T2> maxValue) {
        return this.betweenSymmetric(minValue).and(maxValue);
    }

    @Override
    public final BetweenAndStep2<T1, T2> notBetween(T1 t1, T2 t2) {
        return this.notBetween(DSL.row(Tools.field(t1, this.dataType(0)), Tools.field(t2, this.dataType(1))));
    }

    @Override
    public final BetweenAndStep2<T1, T2> notBetween(Field<T1> t1, Field<T2> t2) {
        return this.notBetween(DSL.row(t1, t2));
    }

    @Override
    public final BetweenAndStep2<T1, T2> notBetween(Row2<T1, T2> row) {
        return new RowBetweenCondition(this, row, true, false);
    }

    @Override
    public final BetweenAndStep2<T1, T2> notBetween(Record2<T1, T2> record) {
        return this.notBetween((Row2<T1, T2>)record.valuesRow());
    }

    @Override
    public final Condition notBetween(Row2<T1, T2> minValue, Row2<T1, T2> maxValue) {
        return this.notBetween(minValue).and(maxValue);
    }

    @Override
    public final Condition notBetween(Record2<T1, T2> minValue, Record2<T1, T2> maxValue) {
        return this.notBetween(minValue).and(maxValue);
    }

    @Override
    public final BetweenAndStep2<T1, T2> notBetweenSymmetric(T1 t1, T2 t2) {
        return this.notBetweenSymmetric(DSL.row(Tools.field(t1, this.dataType(0)), Tools.field(t2, this.dataType(1))));
    }

    @Override
    public final BetweenAndStep2<T1, T2> notBetweenSymmetric(Field<T1> t1, Field<T2> t2) {
        return this.notBetweenSymmetric(DSL.row(t1, t2));
    }

    @Override
    public final BetweenAndStep2<T1, T2> notBetweenSymmetric(Row2<T1, T2> row) {
        return new RowBetweenCondition(this, row, true, true);
    }

    @Override
    public final BetweenAndStep2<T1, T2> notBetweenSymmetric(Record2<T1, T2> record) {
        return this.notBetweenSymmetric((Row2<T1, T2>)record.valuesRow());
    }

    @Override
    public final Condition notBetweenSymmetric(Row2<T1, T2> minValue, Row2<T1, T2> maxValue) {
        return this.notBetweenSymmetric(minValue).and(maxValue);
    }

    @Override
    public final Condition notBetweenSymmetric(Record2<T1, T2> minValue, Record2<T1, T2> maxValue) {
        return this.notBetweenSymmetric(minValue).and(maxValue);
    }

    @Override
    public final Condition isNotDistinctFrom(Row2<T1, T2> row) {
        return new RowIsDistinctFrom((Row)this, row, true);
    }

    @Override
    public final Condition isNotDistinctFrom(Record2<T1, T2> record) {
        return this.isNotDistinctFrom((Row2<T1, T2>)record.valuesRow());
    }

    @Override
    public final Condition isNotDistinctFrom(T1 t1, T2 t2) {
        return this.isNotDistinctFrom(Tools.field(t1, this.dataType(0)), Tools.field(t2, this.dataType(1)));
    }

    @Override
    public final Condition isNotDistinctFrom(Field<T1> t1, Field<T2> t2) {
        return this.isNotDistinctFrom(DSL.row(t1, t2));
    }

    @Override
    public final Condition isNotDistinctFrom(Select<? extends Record2<T1, T2>> select) {
        return new RowIsDistinctFrom((Row)this, select, true);
    }

    @Override
    public final Condition isDistinctFrom(Row2<T1, T2> row) {
        return new RowIsDistinctFrom((Row)this, row, false);
    }

    @Override
    public final Condition isDistinctFrom(Record2<T1, T2> record) {
        return this.isDistinctFrom((Row2<T1, T2>)record.valuesRow());
    }

    @Override
    public final Condition isDistinctFrom(T1 t1, T2 t2) {
        return this.isDistinctFrom(Tools.field(t1, this.dataType(0)), Tools.field(t2, this.dataType(1)));
    }

    @Override
    public final Condition isDistinctFrom(Field<T1> t1, Field<T2> t2) {
        return this.isDistinctFrom(DSL.row(t1, t2));
    }

    @Override
    public final Condition isDistinctFrom(Select<? extends Record2<T1, T2>> select) {
        return new RowIsDistinctFrom((Row)this, select, false);
    }

    @Override
    public final Condition in(Row2<T1, T2> ... rows) {
        return this.in(Arrays.asList(rows));
    }

    @Override
    public final Condition in(Record2<T1, T2> ... records) {
        QueryPartList<Row> rows = new QueryPartList<Row>();
        for (Record2<T1, T2> record : records) {
            rows.add(record.valuesRow());
        }
        return new RowInCondition(this, rows, false);
    }

    @Override
    public final Condition notIn(Row2<T1, T2> ... rows) {
        return this.notIn(Arrays.asList(rows));
    }

    @Override
    public final Condition notIn(Record2<T1, T2> ... records) {
        QueryPartList<Row> rows = new QueryPartList<Row>();
        for (Record2<T1, T2> record : records) {
            rows.add(record.valuesRow());
        }
        return new RowInCondition(this, rows, true);
    }

    @Override
    public final Condition in(Collection<? extends Row2<T1, T2>> rows) {
        return new RowInCondition(this, new QueryPartList<Row2<T1, T2>>(rows), false);
    }

    @Override
    public final Condition in(Result<? extends Record2<T1, T2>> result) {
        return new RowInCondition(this, new QueryPartList<Row>((Iterable<Row>)Tools.rows(result)), false);
    }

    @Override
    public final Condition notIn(Collection<? extends Row2<T1, T2>> rows) {
        return new RowInCondition(this, new QueryPartList<Row2<T1, T2>>(rows), true);
    }

    @Override
    public final Condition notIn(Result<? extends Record2<T1, T2>> result) {
        return new RowInCondition(this, new QueryPartList<Row>((Iterable<Row>)Tools.rows(result)), true);
    }

    @Override
    public final Condition equal(Select<? extends Record2<T1, T2>> select) {
        return this.compare(Comparator.EQUALS, select);
    }

    @Override
    public final Condition equal(QuantifiedSelect<? extends Record2<T1, T2>> select) {
        return this.compare(Comparator.EQUALS, select);
    }

    @Override
    public final Condition eq(Select<? extends Record2<T1, T2>> select) {
        return this.equal(select);
    }

    @Override
    public final Condition eq(QuantifiedSelect<? extends Record2<T1, T2>> select) {
        return this.equal(select);
    }

    @Override
    public final Condition notEqual(Select<? extends Record2<T1, T2>> select) {
        return this.compare(Comparator.NOT_EQUALS, select);
    }

    @Override
    public final Condition notEqual(QuantifiedSelect<? extends Record2<T1, T2>> select) {
        return this.compare(Comparator.NOT_EQUALS, select);
    }

    @Override
    public final Condition ne(Select<? extends Record2<T1, T2>> select) {
        return this.notEqual(select);
    }

    @Override
    public final Condition ne(QuantifiedSelect<? extends Record2<T1, T2>> select) {
        return this.notEqual(select);
    }

    @Override
    public final Condition greaterThan(Select<? extends Record2<T1, T2>> select) {
        return this.compare(Comparator.GREATER, select);
    }

    @Override
    public final Condition greaterThan(QuantifiedSelect<? extends Record2<T1, T2>> select) {
        return this.compare(Comparator.GREATER, select);
    }

    @Override
    public final Condition gt(Select<? extends Record2<T1, T2>> select) {
        return this.greaterThan(select);
    }

    @Override
    public final Condition gt(QuantifiedSelect<? extends Record2<T1, T2>> select) {
        return this.greaterThan(select);
    }

    @Override
    public final Condition greaterOrEqual(Select<? extends Record2<T1, T2>> select) {
        return this.compare(Comparator.GREATER_OR_EQUAL, select);
    }

    @Override
    public final Condition greaterOrEqual(QuantifiedSelect<? extends Record2<T1, T2>> select) {
        return this.compare(Comparator.GREATER_OR_EQUAL, select);
    }

    @Override
    public final Condition ge(Select<? extends Record2<T1, T2>> select) {
        return this.greaterOrEqual(select);
    }

    @Override
    public final Condition ge(QuantifiedSelect<? extends Record2<T1, T2>> select) {
        return this.greaterOrEqual(select);
    }

    @Override
    public final Condition lessThan(Select<? extends Record2<T1, T2>> select) {
        return this.compare(Comparator.LESS, select);
    }

    @Override
    public final Condition lessThan(QuantifiedSelect<? extends Record2<T1, T2>> select) {
        return this.compare(Comparator.LESS, select);
    }

    @Override
    public final Condition lt(Select<? extends Record2<T1, T2>> select) {
        return this.lessThan(select);
    }

    @Override
    public final Condition lt(QuantifiedSelect<? extends Record2<T1, T2>> select) {
        return this.lessThan(select);
    }

    @Override
    public final Condition lessOrEqual(Select<? extends Record2<T1, T2>> select) {
        return this.compare(Comparator.LESS_OR_EQUAL, select);
    }

    @Override
    public final Condition lessOrEqual(QuantifiedSelect<? extends Record2<T1, T2>> select) {
        return this.compare(Comparator.LESS_OR_EQUAL, select);
    }

    @Override
    public final Condition le(Select<? extends Record2<T1, T2>> select) {
        return this.lessOrEqual(select);
    }

    @Override
    public final Condition le(QuantifiedSelect<? extends Record2<T1, T2>> select) {
        return this.lessOrEqual(select);
    }

    @Override
    public final Condition in(Select<? extends Record2<T1, T2>> select) {
        return this.compare(Comparator.IN, select);
    }

    @Override
    public final Condition notIn(Select<? extends Record2<T1, T2>> select) {
        return this.compare(Comparator.NOT_IN, select);
    }

    @Override
    public final Condition overlaps(T1 t1, T2 t2) {
        return this.overlaps(DSL.row(t1, t2));
    }

    @Override
    public final Condition overlaps(Field<T1> t1, Field<T2> t2) {
        return this.overlaps(DSL.row(t1, t2));
    }

    @Override
    public final Condition overlaps(Row2<T1, T2> row) {
        return new RowOverlaps<T1, T2>(this, row);
    }

    @Override
    public final Statement set(Row2<T1, T2> row) {
        return new RowAssignment((Row)this, row);
    }

    @Override
    public final Statement set(Record2<T1, T2> record) {
        return this.set((Row2<T1, T2>)record.valuesRow());
    }

    @Override
    public final Statement set(T1 t1, T2 t2) {
        return this.set(DSL.row(Tools.field(t1, this.dataType(0)), Tools.field(t2, this.dataType(1))));
    }

    @Override
    public final Statement set(Field<T1> t1, Field<T2> t2) {
        return this.set(DSL.row(t1, t2));
    }

    @Override
    public final Statement set(Select<? extends Record2<T1, T2>> value) {
        return new RowAssignment((Row)this, value);
    }
}

