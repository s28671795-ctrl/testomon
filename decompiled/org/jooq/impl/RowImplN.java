/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;
import org.jooq.BetweenAndStepN;
import org.jooq.Comparator;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.QuantifiedSelect;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.Row;
import org.jooq.RowN;
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

final class RowImplN
extends AbstractRow<Record>
implements RowN {
    RowImplN(SelectField<?> ... fields) {
        super(fields);
    }

    RowImplN(Collection<? extends SelectField<?>> fields) {
        super(fields);
    }

    RowImplN(FieldsImpl<?> fields) {
        super(fields);
    }

    @Override
    public final <U> SelectField<U> mapping(Function<? super Object[], ? extends U> function) {
        return this.convertFrom((? super R r) -> r == null ? null : function.apply(r.intoArray()));
    }

    @Override
    public final <U> SelectField<U> mapping(Class<U> uType, Function<? super Object[], ? extends U> function) {
        return this.convertFrom(uType, (? super R r) -> r == null ? null : function.apply(r.intoArray()));
    }

    @Override
    public final Condition compare(Comparator comparator, RowN row) {
        return RowImplN.compare(this, comparator, row);
    }

    @Override
    public final Condition compare(Comparator comparator, Record record) {
        return RowImplN.compare(this, comparator, record.valuesRow());
    }

    @Override
    public final Condition compare(Comparator comparator, Object ... values2) {
        return this.compare(comparator, DSL.row(Tools.fields(values2, this.dataTypes())));
    }

    @Override
    public final Condition compare(Comparator comparator, Field<?> ... values2) {
        return this.compare(comparator, DSL.row(Tools.fields((Object[])values2, this.dataTypes())));
    }

    @Override
    public final Condition compare(Comparator comparator, Select<? extends Record> select) {
        return new RowSubqueryCondition((Row)this, select, comparator);
    }

    @Override
    public final Condition compare(Comparator comparator, QuantifiedSelect<? extends Record> select) {
        return new RowSubqueryCondition((Row)this, select, comparator);
    }

    @Override
    public final Condition equal(RowN row) {
        return this.compare(Comparator.EQUALS, row);
    }

    @Override
    public final Condition equal(Record record) {
        return this.compare(Comparator.EQUALS, record);
    }

    @Override
    public final Condition equal(Object ... values2) {
        return this.compare(Comparator.EQUALS, values2);
    }

    @Override
    public final Condition equal(Field<?> ... values2) {
        return this.compare(Comparator.EQUALS, values2);
    }

    @Override
    public final Condition eq(RowN row) {
        return this.equal(row);
    }

    @Override
    public final Condition eq(Record record) {
        return this.equal(record);
    }

    @Override
    public final Condition eq(Object ... values2) {
        return this.equal(values2);
    }

    @Override
    public final Condition eq(Field<?> ... values2) {
        return this.equal(values2);
    }

    @Override
    public final Condition notEqual(RowN row) {
        return this.compare(Comparator.NOT_EQUALS, row);
    }

    @Override
    public final Condition notEqual(Record record) {
        return this.compare(Comparator.NOT_EQUALS, record);
    }

    @Override
    public final Condition notEqual(Object ... values2) {
        return this.compare(Comparator.NOT_EQUALS, values2);
    }

    @Override
    public final Condition notEqual(Field<?> ... values2) {
        return this.compare(Comparator.NOT_EQUALS, values2);
    }

    @Override
    public final Condition ne(RowN row) {
        return this.notEqual(row);
    }

    @Override
    public final Condition ne(Record record) {
        return this.notEqual(record);
    }

    @Override
    public final Condition ne(Object ... values2) {
        return this.notEqual(values2);
    }

    @Override
    public final Condition ne(Field<?> ... values2) {
        return this.notEqual(values2);
    }

    @Override
    public final Condition lessThan(RowN row) {
        return this.compare(Comparator.LESS, row);
    }

    @Override
    public final Condition lessThan(Record record) {
        return this.compare(Comparator.LESS, record);
    }

    @Override
    public final Condition lessThan(Object ... values2) {
        return this.compare(Comparator.LESS, values2);
    }

    @Override
    public final Condition lessThan(Field<?> ... values2) {
        return this.compare(Comparator.LESS, values2);
    }

    @Override
    public final Condition lt(RowN row) {
        return this.lessThan(row);
    }

    @Override
    public final Condition lt(Record record) {
        return this.lessThan(record);
    }

    @Override
    public final Condition lt(Object ... values2) {
        return this.lessThan(values2);
    }

    @Override
    public final Condition lt(Field<?> ... values2) {
        return this.lessThan(values2);
    }

    @Override
    public final Condition lessOrEqual(RowN row) {
        return this.compare(Comparator.LESS_OR_EQUAL, row);
    }

    @Override
    public final Condition lessOrEqual(Record record) {
        return this.compare(Comparator.LESS_OR_EQUAL, record);
    }

    @Override
    public final Condition lessOrEqual(Object ... values2) {
        return this.compare(Comparator.LESS_OR_EQUAL, values2);
    }

    @Override
    public final Condition lessOrEqual(Field<?> ... values2) {
        return this.compare(Comparator.LESS_OR_EQUAL, values2);
    }

    @Override
    public final Condition le(RowN row) {
        return this.lessOrEqual(row);
    }

    @Override
    public final Condition le(Record record) {
        return this.lessOrEqual(record);
    }

    @Override
    public final Condition le(Object ... values2) {
        return this.lessOrEqual(values2);
    }

    @Override
    public final Condition le(Field<?> ... values2) {
        return this.lessOrEqual(values2);
    }

    @Override
    public final Condition greaterThan(RowN row) {
        return this.compare(Comparator.GREATER, row);
    }

    @Override
    public final Condition greaterThan(Record record) {
        return this.compare(Comparator.GREATER, record);
    }

    @Override
    public final Condition greaterThan(Object ... values2) {
        return this.compare(Comparator.GREATER, values2);
    }

    @Override
    public final Condition greaterThan(Field<?> ... values2) {
        return this.compare(Comparator.GREATER, values2);
    }

    @Override
    public final Condition gt(RowN row) {
        return this.greaterThan(row);
    }

    @Override
    public final Condition gt(Record record) {
        return this.greaterThan(record);
    }

    @Override
    public final Condition gt(Object ... values2) {
        return this.greaterThan(values2);
    }

    @Override
    public final Condition gt(Field<?> ... values2) {
        return this.greaterThan(values2);
    }

    @Override
    public final Condition greaterOrEqual(RowN row) {
        return this.compare(Comparator.GREATER_OR_EQUAL, row);
    }

    @Override
    public final Condition greaterOrEqual(Record record) {
        return this.compare(Comparator.GREATER_OR_EQUAL, record);
    }

    @Override
    public final Condition greaterOrEqual(Object ... values2) {
        return this.compare(Comparator.GREATER_OR_EQUAL, values2);
    }

    @Override
    public final Condition greaterOrEqual(Field<?> ... values2) {
        return this.compare(Comparator.GREATER_OR_EQUAL, values2);
    }

    @Override
    public final Condition ge(RowN row) {
        return this.greaterOrEqual(row);
    }

    @Override
    public final Condition ge(Record record) {
        return this.greaterOrEqual(record);
    }

    @Override
    public final Condition ge(Object ... values2) {
        return this.greaterOrEqual(values2);
    }

    @Override
    public final Condition ge(Field<?> ... values2) {
        return this.greaterOrEqual(values2);
    }

    @Override
    public final BetweenAndStepN between(Object ... values2) {
        return this.between(DSL.row(Tools.fieldsArray(values2, this.dataTypes())));
    }

    @Override
    public final BetweenAndStepN between(Field<?> ... values2) {
        return this.between(DSL.row(values2));
    }

    @Override
    public final BetweenAndStepN between(RowN row) {
        return new RowBetweenCondition(this, row, false, false);
    }

    @Override
    public final BetweenAndStepN between(Record record) {
        return this.between(record.valuesRow());
    }

    @Override
    public final Condition between(RowN minValue, RowN maxValue) {
        return this.between(minValue).and(maxValue);
    }

    @Override
    public final Condition between(Record minValue, Record maxValue) {
        return this.between(minValue).and(maxValue);
    }

    @Override
    public final BetweenAndStepN betweenSymmetric(Object ... values2) {
        return this.betweenSymmetric(DSL.row(Tools.fieldsArray(values2, this.dataTypes())));
    }

    @Override
    public final BetweenAndStepN betweenSymmetric(Field<?> ... values2) {
        return this.betweenSymmetric(DSL.row(values2));
    }

    @Override
    public final BetweenAndStepN betweenSymmetric(RowN row) {
        return new RowBetweenCondition(this, row, false, true);
    }

    @Override
    public final BetweenAndStepN betweenSymmetric(Record record) {
        return this.betweenSymmetric(record.valuesRow());
    }

    @Override
    public final Condition betweenSymmetric(RowN minValue, RowN maxValue) {
        return this.betweenSymmetric(minValue).and(maxValue);
    }

    @Override
    public final Condition betweenSymmetric(Record minValue, Record maxValue) {
        return this.betweenSymmetric(minValue).and(maxValue);
    }

    @Override
    public final BetweenAndStepN notBetween(Object ... values2) {
        return this.notBetween(DSL.row(Tools.fieldsArray(values2, this.dataTypes())));
    }

    @Override
    public final BetweenAndStepN notBetween(Field<?> ... values2) {
        return this.notBetween(DSL.row(values2));
    }

    @Override
    public final BetweenAndStepN notBetween(RowN row) {
        return new RowBetweenCondition(this, row, true, false);
    }

    @Override
    public final BetweenAndStepN notBetween(Record record) {
        return this.notBetween(record.valuesRow());
    }

    @Override
    public final Condition notBetween(RowN minValue, RowN maxValue) {
        return this.notBetween(minValue).and(maxValue);
    }

    @Override
    public final Condition notBetween(Record minValue, Record maxValue) {
        return this.notBetween(minValue).and(maxValue);
    }

    @Override
    public final BetweenAndStepN notBetweenSymmetric(Object ... values2) {
        return this.notBetweenSymmetric(DSL.row(Tools.fieldsArray(values2, this.dataTypes())));
    }

    @Override
    public final BetweenAndStepN notBetweenSymmetric(Field<?> ... values2) {
        return this.notBetweenSymmetric(DSL.row(values2));
    }

    @Override
    public final BetweenAndStepN notBetweenSymmetric(RowN row) {
        return new RowBetweenCondition(this, row, true, true);
    }

    @Override
    public final BetweenAndStepN notBetweenSymmetric(Record record) {
        return this.notBetweenSymmetric(record.valuesRow());
    }

    @Override
    public final Condition notBetweenSymmetric(RowN minValue, RowN maxValue) {
        return this.notBetweenSymmetric(minValue).and(maxValue);
    }

    @Override
    public final Condition notBetweenSymmetric(Record minValue, Record maxValue) {
        return this.notBetweenSymmetric(minValue).and(maxValue);
    }

    @Override
    public final Condition isNotDistinctFrom(RowN row) {
        return new RowIsDistinctFrom((Row)this, row, true);
    }

    @Override
    public final Condition isNotDistinctFrom(Record record) {
        return this.isNotDistinctFrom(record.valuesRow());
    }

    @Override
    public final Condition isNotDistinctFrom(Object ... values2) {
        return this.isNotDistinctFrom(Tools.fieldsArray(values2, this.dataTypes()));
    }

    @Override
    public final Condition isNotDistinctFrom(Field<?> ... values2) {
        return this.isNotDistinctFrom(DSL.row(values2));
    }

    @Override
    public final Condition isNotDistinctFrom(Select<? extends Record> select) {
        return new RowIsDistinctFrom((Row)this, select, true);
    }

    @Override
    public final Condition isDistinctFrom(RowN row) {
        return new RowIsDistinctFrom((Row)this, row, false);
    }

    @Override
    public final Condition isDistinctFrom(Record record) {
        return this.isDistinctFrom(record.valuesRow());
    }

    @Override
    public final Condition isDistinctFrom(Object ... values2) {
        return this.isDistinctFrom(Tools.fieldsArray(values2, this.dataTypes()));
    }

    @Override
    public final Condition isDistinctFrom(Field<?> ... values2) {
        return this.isDistinctFrom(DSL.row(values2));
    }

    @Override
    public final Condition isDistinctFrom(Select<? extends Record> select) {
        return new RowIsDistinctFrom((Row)this, select, false);
    }

    @Override
    public final Condition in(RowN ... rows) {
        return this.in(Arrays.asList(rows));
    }

    @Override
    public final Condition in(Record ... records) {
        QueryPartList<Row> rows = new QueryPartList<Row>();
        for (Record record : records) {
            rows.add(record.valuesRow());
        }
        return new RowInCondition(this, rows, false);
    }

    @Override
    public final Condition notIn(RowN ... rows) {
        return this.notIn(Arrays.asList(rows));
    }

    @Override
    public final Condition notIn(Record ... records) {
        QueryPartList<Row> rows = new QueryPartList<Row>();
        for (Record record : records) {
            rows.add(record.valuesRow());
        }
        return new RowInCondition(this, rows, true);
    }

    @Override
    public final Condition in(Collection<? extends RowN> rows) {
        return new RowInCondition(this, new QueryPartList<RowN>((Iterable<? extends RowN>)rows), false);
    }

    @Override
    public final Condition in(Result<? extends Record> result) {
        return new RowInCondition(this, new QueryPartList<Row>((Iterable<Row>)Tools.rows(result)), false);
    }

    @Override
    public final Condition notIn(Collection<? extends RowN> rows) {
        return new RowInCondition(this, new QueryPartList<RowN>((Iterable<? extends RowN>)rows), true);
    }

    @Override
    public final Condition notIn(Result<? extends Record> result) {
        return new RowInCondition(this, new QueryPartList<Row>((Iterable<Row>)Tools.rows(result)), true);
    }

    @Override
    public final Condition equal(Select<? extends Record> select) {
        return this.compare(Comparator.EQUALS, select);
    }

    @Override
    public final Condition equal(QuantifiedSelect<? extends Record> select) {
        return this.compare(Comparator.EQUALS, select);
    }

    @Override
    public final Condition eq(Select<? extends Record> select) {
        return this.equal(select);
    }

    @Override
    public final Condition eq(QuantifiedSelect<? extends Record> select) {
        return this.equal(select);
    }

    @Override
    public final Condition notEqual(Select<? extends Record> select) {
        return this.compare(Comparator.NOT_EQUALS, select);
    }

    @Override
    public final Condition notEqual(QuantifiedSelect<? extends Record> select) {
        return this.compare(Comparator.NOT_EQUALS, select);
    }

    @Override
    public final Condition ne(Select<? extends Record> select) {
        return this.notEqual(select);
    }

    @Override
    public final Condition ne(QuantifiedSelect<? extends Record> select) {
        return this.notEqual(select);
    }

    @Override
    public final Condition greaterThan(Select<? extends Record> select) {
        return this.compare(Comparator.GREATER, select);
    }

    @Override
    public final Condition greaterThan(QuantifiedSelect<? extends Record> select) {
        return this.compare(Comparator.GREATER, select);
    }

    @Override
    public final Condition gt(Select<? extends Record> select) {
        return this.greaterThan(select);
    }

    @Override
    public final Condition gt(QuantifiedSelect<? extends Record> select) {
        return this.greaterThan(select);
    }

    @Override
    public final Condition greaterOrEqual(Select<? extends Record> select) {
        return this.compare(Comparator.GREATER_OR_EQUAL, select);
    }

    @Override
    public final Condition greaterOrEqual(QuantifiedSelect<? extends Record> select) {
        return this.compare(Comparator.GREATER_OR_EQUAL, select);
    }

    @Override
    public final Condition ge(Select<? extends Record> select) {
        return this.greaterOrEqual(select);
    }

    @Override
    public final Condition ge(QuantifiedSelect<? extends Record> select) {
        return this.greaterOrEqual(select);
    }

    @Override
    public final Condition lessThan(Select<? extends Record> select) {
        return this.compare(Comparator.LESS, select);
    }

    @Override
    public final Condition lessThan(QuantifiedSelect<? extends Record> select) {
        return this.compare(Comparator.LESS, select);
    }

    @Override
    public final Condition lt(Select<? extends Record> select) {
        return this.lessThan(select);
    }

    @Override
    public final Condition lt(QuantifiedSelect<? extends Record> select) {
        return this.lessThan(select);
    }

    @Override
    public final Condition lessOrEqual(Select<? extends Record> select) {
        return this.compare(Comparator.LESS_OR_EQUAL, select);
    }

    @Override
    public final Condition lessOrEqual(QuantifiedSelect<? extends Record> select) {
        return this.compare(Comparator.LESS_OR_EQUAL, select);
    }

    @Override
    public final Condition le(Select<? extends Record> select) {
        return this.lessOrEqual(select);
    }

    @Override
    public final Condition le(QuantifiedSelect<? extends Record> select) {
        return this.lessOrEqual(select);
    }

    @Override
    public final Condition in(Select<? extends Record> select) {
        return this.compare(Comparator.IN, select);
    }

    @Override
    public final Condition notIn(Select<? extends Record> select) {
        return this.compare(Comparator.NOT_IN, select);
    }

    @Override
    public final Statement set(RowN row) {
        return new RowAssignment((Row)this, row);
    }

    @Override
    public final Statement set(Record record) {
        return this.set(record.valuesRow());
    }

    @Override
    public final Statement set(Object ... values2) {
        return this.set(DSL.row(Tools.fields(values2, this.dataTypes())));
    }

    @Override
    public final Statement set(Field<?> ... values2) {
        return this.set(DSL.row(values2));
    }

    @Override
    public final Statement set(Select<? extends Record> value) {
        return new RowAssignment((Row)this, value);
    }
}

