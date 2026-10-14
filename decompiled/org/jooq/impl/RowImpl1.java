/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Arrays;
import java.util.Collection;
import org.jooq.BetweenAndStep1;
import org.jooq.Comparator;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.QuantifiedSelect;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.Row;
import org.jooq.Row1;
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

final class RowImpl1<T1>
extends AbstractRow<Record1<T1>>
implements Row1<T1> {
    RowImpl1(SelectField<T1> field1) {
        super(field1);
    }

    RowImpl1(FieldsImpl<?> fields) {
        super(fields);
    }

    @Override
    public final <U> SelectField<U> mapping(Function1<? super T1, ? extends U> function) {
        return this.convertFrom((? super R r) -> r == null ? null : function.apply((Object)r.value1()));
    }

    @Override
    public final <U> SelectField<U> mapping(Class<U> uType, Function1<? super T1, ? extends U> function) {
        return this.convertFrom(uType, (? super R r) -> r == null ? null : function.apply((Object)r.value1()));
    }

    @Override
    public final Field<T1> field1() {
        return this.fields.field(0);
    }

    @Override
    public final Condition compare(Comparator comparator, Row1<T1> row) {
        return RowImpl1.compare(this, comparator, row);
    }

    @Override
    public final Condition compare(Comparator comparator, Record1<T1> record) {
        return RowImpl1.compare(this, comparator, record.valuesRow());
    }

    @Override
    public final Condition compare(Comparator comparator, T1 t1) {
        return this.compare(comparator, (T1)DSL.row(Tools.field(t1, this.dataType(0))));
    }

    @Override
    public final Condition compare(Comparator comparator, Field<T1> t1) {
        return this.compare(comparator, (T1)DSL.row(Tools.nullSafe(t1, this.dataType(0))));
    }

    @Override
    public final Condition compare(Comparator comparator, Select<? extends Record1<T1>> select) {
        return new RowSubqueryCondition((Row)this, select, comparator);
    }

    @Override
    public final Condition compare(Comparator comparator, QuantifiedSelect<? extends Record1<T1>> select) {
        return new RowSubqueryCondition((Row)this, select, comparator);
    }

    @Override
    public final Condition equal(Row1<T1> row) {
        return this.compare(Comparator.EQUALS, (T1)row);
    }

    @Override
    public final Condition equal(Record1<T1> record) {
        return this.compare(Comparator.EQUALS, (T1)record);
    }

    @Override
    public final Condition equal(T1 t1) {
        return this.compare(Comparator.EQUALS, t1);
    }

    @Override
    public final Condition equal(Field<T1> t1) {
        return this.compare(Comparator.EQUALS, t1);
    }

    @Override
    public final Condition eq(Row1<T1> row) {
        return this.equal((T1)row);
    }

    @Override
    public final Condition eq(Record1<T1> record) {
        return this.equal((T1)record);
    }

    @Override
    public final Condition eq(T1 t1) {
        return this.equal(t1);
    }

    @Override
    public final Condition eq(Field<T1> t1) {
        return this.equal(t1);
    }

    @Override
    public final Condition notEqual(Row1<T1> row) {
        return this.compare(Comparator.NOT_EQUALS, (T1)row);
    }

    @Override
    public final Condition notEqual(Record1<T1> record) {
        return this.compare(Comparator.NOT_EQUALS, (T1)record);
    }

    @Override
    public final Condition notEqual(T1 t1) {
        return this.compare(Comparator.NOT_EQUALS, t1);
    }

    @Override
    public final Condition notEqual(Field<T1> t1) {
        return this.compare(Comparator.NOT_EQUALS, t1);
    }

    @Override
    public final Condition ne(Row1<T1> row) {
        return this.notEqual((T1)row);
    }

    @Override
    public final Condition ne(Record1<T1> record) {
        return this.notEqual((T1)record);
    }

    @Override
    public final Condition ne(T1 t1) {
        return this.notEqual(t1);
    }

    @Override
    public final Condition ne(Field<T1> t1) {
        return this.notEqual(t1);
    }

    @Override
    public final Condition lessThan(Row1<T1> row) {
        return this.compare(Comparator.LESS, (T1)row);
    }

    @Override
    public final Condition lessThan(Record1<T1> record) {
        return this.compare(Comparator.LESS, (T1)record);
    }

    @Override
    public final Condition lessThan(T1 t1) {
        return this.compare(Comparator.LESS, t1);
    }

    @Override
    public final Condition lessThan(Field<T1> t1) {
        return this.compare(Comparator.LESS, t1);
    }

    @Override
    public final Condition lt(Row1<T1> row) {
        return this.lessThan((T1)row);
    }

    @Override
    public final Condition lt(Record1<T1> record) {
        return this.lessThan((T1)record);
    }

    @Override
    public final Condition lt(T1 t1) {
        return this.lessThan(t1);
    }

    @Override
    public final Condition lt(Field<T1> t1) {
        return this.lessThan(t1);
    }

    @Override
    public final Condition lessOrEqual(Row1<T1> row) {
        return this.compare(Comparator.LESS_OR_EQUAL, (T1)row);
    }

    @Override
    public final Condition lessOrEqual(Record1<T1> record) {
        return this.compare(Comparator.LESS_OR_EQUAL, (T1)record);
    }

    @Override
    public final Condition lessOrEqual(T1 t1) {
        return this.compare(Comparator.LESS_OR_EQUAL, t1);
    }

    @Override
    public final Condition lessOrEqual(Field<T1> t1) {
        return this.compare(Comparator.LESS_OR_EQUAL, t1);
    }

    @Override
    public final Condition le(Row1<T1> row) {
        return this.lessOrEqual((T1)row);
    }

    @Override
    public final Condition le(Record1<T1> record) {
        return this.lessOrEqual((T1)record);
    }

    @Override
    public final Condition le(T1 t1) {
        return this.lessOrEqual(t1);
    }

    @Override
    public final Condition le(Field<T1> t1) {
        return this.lessOrEqual(t1);
    }

    @Override
    public final Condition greaterThan(Row1<T1> row) {
        return this.compare(Comparator.GREATER, (T1)row);
    }

    @Override
    public final Condition greaterThan(Record1<T1> record) {
        return this.compare(Comparator.GREATER, (T1)record);
    }

    @Override
    public final Condition greaterThan(T1 t1) {
        return this.compare(Comparator.GREATER, t1);
    }

    @Override
    public final Condition greaterThan(Field<T1> t1) {
        return this.compare(Comparator.GREATER, t1);
    }

    @Override
    public final Condition gt(Row1<T1> row) {
        return this.greaterThan((T1)row);
    }

    @Override
    public final Condition gt(Record1<T1> record) {
        return this.greaterThan((T1)record);
    }

    @Override
    public final Condition gt(T1 t1) {
        return this.greaterThan(t1);
    }

    @Override
    public final Condition gt(Field<T1> t1) {
        return this.greaterThan(t1);
    }

    @Override
    public final Condition greaterOrEqual(Row1<T1> row) {
        return this.compare(Comparator.GREATER_OR_EQUAL, (T1)row);
    }

    @Override
    public final Condition greaterOrEqual(Record1<T1> record) {
        return this.compare(Comparator.GREATER_OR_EQUAL, (T1)record);
    }

    @Override
    public final Condition greaterOrEqual(T1 t1) {
        return this.compare(Comparator.GREATER_OR_EQUAL, t1);
    }

    @Override
    public final Condition greaterOrEqual(Field<T1> t1) {
        return this.compare(Comparator.GREATER_OR_EQUAL, t1);
    }

    @Override
    public final Condition ge(Row1<T1> row) {
        return this.greaterOrEqual((T1)row);
    }

    @Override
    public final Condition ge(Record1<T1> record) {
        return this.greaterOrEqual((T1)record);
    }

    @Override
    public final Condition ge(T1 t1) {
        return this.greaterOrEqual(t1);
    }

    @Override
    public final Condition ge(Field<T1> t1) {
        return this.greaterOrEqual(t1);
    }

    @Override
    public final BetweenAndStep1<T1> between(T1 t1) {
        return this.between(DSL.row(Tools.field(t1, this.dataType(0))));
    }

    @Override
    public final BetweenAndStep1<T1> between(Field<T1> t1) {
        return this.between(DSL.row(t1));
    }

    @Override
    public final BetweenAndStep1<T1> between(Row1<T1> row) {
        return new RowBetweenCondition(this, row, false, false);
    }

    @Override
    public final BetweenAndStep1<T1> between(Record1<T1> record) {
        return this.between((Row1<T1>)record.valuesRow());
    }

    @Override
    public final Condition between(Row1<T1> minValue, Row1<T1> maxValue) {
        return this.between(minValue).and(maxValue);
    }

    @Override
    public final Condition between(Record1<T1> minValue, Record1<T1> maxValue) {
        return this.between(minValue).and(maxValue);
    }

    @Override
    public final BetweenAndStep1<T1> betweenSymmetric(T1 t1) {
        return this.betweenSymmetric(DSL.row(Tools.field(t1, this.dataType(0))));
    }

    @Override
    public final BetweenAndStep1<T1> betweenSymmetric(Field<T1> t1) {
        return this.betweenSymmetric(DSL.row(t1));
    }

    @Override
    public final BetweenAndStep1<T1> betweenSymmetric(Row1<T1> row) {
        return new RowBetweenCondition(this, row, false, true);
    }

    @Override
    public final BetweenAndStep1<T1> betweenSymmetric(Record1<T1> record) {
        return this.betweenSymmetric((Row1<T1>)record.valuesRow());
    }

    @Override
    public final Condition betweenSymmetric(Row1<T1> minValue, Row1<T1> maxValue) {
        return this.betweenSymmetric(minValue).and(maxValue);
    }

    @Override
    public final Condition betweenSymmetric(Record1<T1> minValue, Record1<T1> maxValue) {
        return this.betweenSymmetric(minValue).and(maxValue);
    }

    @Override
    public final BetweenAndStep1<T1> notBetween(T1 t1) {
        return this.notBetween(DSL.row(Tools.field(t1, this.dataType(0))));
    }

    @Override
    public final BetweenAndStep1<T1> notBetween(Field<T1> t1) {
        return this.notBetween(DSL.row(t1));
    }

    @Override
    public final BetweenAndStep1<T1> notBetween(Row1<T1> row) {
        return new RowBetweenCondition(this, row, true, false);
    }

    @Override
    public final BetweenAndStep1<T1> notBetween(Record1<T1> record) {
        return this.notBetween((Row1<T1>)record.valuesRow());
    }

    @Override
    public final Condition notBetween(Row1<T1> minValue, Row1<T1> maxValue) {
        return this.notBetween(minValue).and(maxValue);
    }

    @Override
    public final Condition notBetween(Record1<T1> minValue, Record1<T1> maxValue) {
        return this.notBetween(minValue).and(maxValue);
    }

    @Override
    public final BetweenAndStep1<T1> notBetweenSymmetric(T1 t1) {
        return this.notBetweenSymmetric(DSL.row(Tools.field(t1, this.dataType(0))));
    }

    @Override
    public final BetweenAndStep1<T1> notBetweenSymmetric(Field<T1> t1) {
        return this.notBetweenSymmetric(DSL.row(t1));
    }

    @Override
    public final BetweenAndStep1<T1> notBetweenSymmetric(Row1<T1> row) {
        return new RowBetweenCondition(this, row, true, true);
    }

    @Override
    public final BetweenAndStep1<T1> notBetweenSymmetric(Record1<T1> record) {
        return this.notBetweenSymmetric((Row1<T1>)record.valuesRow());
    }

    @Override
    public final Condition notBetweenSymmetric(Row1<T1> minValue, Row1<T1> maxValue) {
        return this.notBetweenSymmetric(minValue).and(maxValue);
    }

    @Override
    public final Condition notBetweenSymmetric(Record1<T1> minValue, Record1<T1> maxValue) {
        return this.notBetweenSymmetric(minValue).and(maxValue);
    }

    @Override
    public final Condition isNotDistinctFrom(Row1<T1> row) {
        return new RowIsDistinctFrom((Row)this, row, true);
    }

    @Override
    public final Condition isNotDistinctFrom(Record1<T1> record) {
        return this.isNotDistinctFrom(record.valuesRow());
    }

    @Override
    public final Condition isNotDistinctFrom(T1 t1) {
        return this.isNotDistinctFrom(Tools.field(t1, this.dataType(0)));
    }

    @Override
    public final Condition isNotDistinctFrom(Field<T1> t1) {
        return this.isNotDistinctFrom((T1)DSL.row(t1));
    }

    @Override
    public final Condition isNotDistinctFrom(Select<? extends Record1<T1>> select) {
        return new RowIsDistinctFrom((Row)this, select, true);
    }

    @Override
    public final Condition isDistinctFrom(Row1<T1> row) {
        return new RowIsDistinctFrom((Row)this, row, false);
    }

    @Override
    public final Condition isDistinctFrom(Record1<T1> record) {
        return this.isDistinctFrom(record.valuesRow());
    }

    @Override
    public final Condition isDistinctFrom(T1 t1) {
        return this.isDistinctFrom(Tools.field(t1, this.dataType(0)));
    }

    @Override
    public final Condition isDistinctFrom(Field<T1> t1) {
        return this.isDistinctFrom((T1)DSL.row(t1));
    }

    @Override
    public final Condition isDistinctFrom(Select<? extends Record1<T1>> select) {
        return new RowIsDistinctFrom((Row)this, select, false);
    }

    @Override
    public final Condition in(Row1<T1> ... rows) {
        return this.in(Arrays.asList(rows));
    }

    @Override
    public final Condition in(Record1<T1> ... records) {
        QueryPartList<Row> rows = new QueryPartList<Row>();
        for (Record1<T1> record : records) {
            rows.add(record.valuesRow());
        }
        return new RowInCondition(this, rows, false);
    }

    @Override
    public final Condition notIn(Row1<T1> ... rows) {
        return this.notIn(Arrays.asList(rows));
    }

    @Override
    public final Condition notIn(Record1<T1> ... records) {
        QueryPartList<Row> rows = new QueryPartList<Row>();
        for (Record1<T1> record : records) {
            rows.add(record.valuesRow());
        }
        return new RowInCondition(this, rows, true);
    }

    @Override
    public final Condition in(Collection<? extends Row1<T1>> rows) {
        return new RowInCondition(this, new QueryPartList<Row1<T1>>(rows), false);
    }

    @Override
    public final Condition in(Result<? extends Record1<T1>> result) {
        return new RowInCondition(this, new QueryPartList<Row>((Iterable<Row>)Tools.rows(result)), false);
    }

    @Override
    public final Condition notIn(Collection<? extends Row1<T1>> rows) {
        return new RowInCondition(this, new QueryPartList<Row1<T1>>(rows), true);
    }

    @Override
    public final Condition notIn(Result<? extends Record1<T1>> result) {
        return new RowInCondition(this, new QueryPartList<Row>((Iterable<Row>)Tools.rows(result)), true);
    }

    @Override
    public final Condition equal(Select<? extends Record1<T1>> select) {
        return this.compare(Comparator.EQUALS, select);
    }

    @Override
    public final Condition equal(QuantifiedSelect<? extends Record1<T1>> select) {
        return this.compare(Comparator.EQUALS, select);
    }

    @Override
    public final Condition eq(Select<? extends Record1<T1>> select) {
        return this.equal(select);
    }

    @Override
    public final Condition eq(QuantifiedSelect<? extends Record1<T1>> select) {
        return this.equal(select);
    }

    @Override
    public final Condition notEqual(Select<? extends Record1<T1>> select) {
        return this.compare(Comparator.NOT_EQUALS, select);
    }

    @Override
    public final Condition notEqual(QuantifiedSelect<? extends Record1<T1>> select) {
        return this.compare(Comparator.NOT_EQUALS, select);
    }

    @Override
    public final Condition ne(Select<? extends Record1<T1>> select) {
        return this.notEqual(select);
    }

    @Override
    public final Condition ne(QuantifiedSelect<? extends Record1<T1>> select) {
        return this.notEqual(select);
    }

    @Override
    public final Condition greaterThan(Select<? extends Record1<T1>> select) {
        return this.compare(Comparator.GREATER, select);
    }

    @Override
    public final Condition greaterThan(QuantifiedSelect<? extends Record1<T1>> select) {
        return this.compare(Comparator.GREATER, select);
    }

    @Override
    public final Condition gt(Select<? extends Record1<T1>> select) {
        return this.greaterThan(select);
    }

    @Override
    public final Condition gt(QuantifiedSelect<? extends Record1<T1>> select) {
        return this.greaterThan(select);
    }

    @Override
    public final Condition greaterOrEqual(Select<? extends Record1<T1>> select) {
        return this.compare(Comparator.GREATER_OR_EQUAL, select);
    }

    @Override
    public final Condition greaterOrEqual(QuantifiedSelect<? extends Record1<T1>> select) {
        return this.compare(Comparator.GREATER_OR_EQUAL, select);
    }

    @Override
    public final Condition ge(Select<? extends Record1<T1>> select) {
        return this.greaterOrEqual(select);
    }

    @Override
    public final Condition ge(QuantifiedSelect<? extends Record1<T1>> select) {
        return this.greaterOrEqual(select);
    }

    @Override
    public final Condition lessThan(Select<? extends Record1<T1>> select) {
        return this.compare(Comparator.LESS, select);
    }

    @Override
    public final Condition lessThan(QuantifiedSelect<? extends Record1<T1>> select) {
        return this.compare(Comparator.LESS, select);
    }

    @Override
    public final Condition lt(Select<? extends Record1<T1>> select) {
        return this.lessThan(select);
    }

    @Override
    public final Condition lt(QuantifiedSelect<? extends Record1<T1>> select) {
        return this.lessThan(select);
    }

    @Override
    public final Condition lessOrEqual(Select<? extends Record1<T1>> select) {
        return this.compare(Comparator.LESS_OR_EQUAL, select);
    }

    @Override
    public final Condition lessOrEqual(QuantifiedSelect<? extends Record1<T1>> select) {
        return this.compare(Comparator.LESS_OR_EQUAL, select);
    }

    @Override
    public final Condition le(Select<? extends Record1<T1>> select) {
        return this.lessOrEqual(select);
    }

    @Override
    public final Condition le(QuantifiedSelect<? extends Record1<T1>> select) {
        return this.lessOrEqual(select);
    }

    @Override
    public final Condition in(Select<? extends Record1<T1>> select) {
        return this.compare(Comparator.IN, select);
    }

    @Override
    public final Condition notIn(Select<? extends Record1<T1>> select) {
        return this.compare(Comparator.NOT_IN, select);
    }

    @Override
    public final Statement set(Row1<T1> row) {
        return new RowAssignment((Row)this, row);
    }

    @Override
    public final Statement set(Record1<T1> record) {
        return this.set(record.valuesRow());
    }

    @Override
    public final Statement set(T1 t1) {
        return this.set((T1)DSL.row(Tools.field(t1, this.dataType(0))));
    }

    @Override
    public final Statement set(Field<T1> t1) {
        return this.set((T1)DSL.row(t1));
    }

    @Override
    public final Statement set(Select<? extends Record1<T1>> value) {
        return new RowAssignment((Row)this, value);
    }
}

