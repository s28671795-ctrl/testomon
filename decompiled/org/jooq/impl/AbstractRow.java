/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;
import org.jooq.Binding;
import org.jooq.Clause;
import org.jooq.Comment;
import org.jooq.Comparator;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.ContextConverter;
import org.jooq.Converter;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Replacer;
import org.jooq.Row;
import org.jooq.SQLDialect;
import org.jooq.SelectField;
import org.jooq.Traverser;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.FieldsImpl;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartListView;
import org.jooq.impl.RowAsField;
import org.jooq.impl.RowEq;
import org.jooq.impl.RowGe;
import org.jooq.impl.RowGt;
import org.jooq.impl.RowIsDistinctFrom;
import org.jooq.impl.RowIsNotNull;
import org.jooq.impl.RowIsNull;
import org.jooq.impl.RowLe;
import org.jooq.impl.RowLt;
import org.jooq.impl.RowNe;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;

abstract class AbstractRow<R extends Record>
extends AbstractQueryPart
implements Row,
SelectField<R> {
    private static final Clause[] CLAUSES = new Clause[]{Clause.FIELD_ROW};
    final FieldsImpl<R> fields;

    AbstractRow(SelectField<?> ... fields) {
        this(new FieldsImpl(fields));
    }

    AbstractRow(Collection<? extends SelectField<?>> fields) {
        this(new FieldsImpl(fields));
    }

    AbstractRow(FieldsImpl<R> fields) {
        this.fields = fields;
    }

    final RowAsField<Row, R> rf() {
        return new RowAsField(this);
    }

    @Override
    public final <U> SelectField<U> convert(Binding<R, U> binding) {
        return this.rf().convert((Binding)binding);
    }

    @Override
    public final <U> SelectField<U> convert(Converter<R, U> converter) {
        return this.rf().convert((Converter)converter);
    }

    @Override
    public final <U> SelectField<U> convert(Class<U> toType, Function<? super R, ? extends U> from, Function<? super U, ? extends R> to) {
        return this.rf().convert((Class)toType, from, to);
    }

    @Override
    public final <U> SelectField<U> convertFrom(Class<U> toType, Function<? super R, ? extends U> from) {
        return this.rf().convertFrom((Class)toType, from);
    }

    @Override
    public final <U> SelectField<U> convertFrom(Function<? super R, ? extends U> from) {
        return this.rf().convertFrom(from);
    }

    @Override
    public final <U> SelectField<U> convertTo(Class<U> toType, Function<? super U, ? extends R> to) {
        return this.rf().convertTo((Class)toType, to);
    }

    @Override
    public final <U> SelectField<U> convertTo(Function<? super U, ? extends R> to) {
        return this.rf().convertTo(to);
    }

    @Override
    public final Field<R> as(String alias) {
        return this.rf().as(alias);
    }

    @Override
    public final Field<R> as(Name alias) {
        return this.rf().as(alias);
    }

    @Override
    public final Field<R> as(Field<?> otherField) {
        return this.rf().as((Field)otherField);
    }

    @Override
    public final ContextConverter<?, R> getConverter() {
        return this.rf().getConverter();
    }

    @Override
    public final Binding<?, R> getBinding() {
        return this.rf().getBinding();
    }

    @Override
    public final Class<R> getType() {
        return this.rf().getType();
    }

    @Override
    public final DataType<R> getDataType() {
        return this.rf().getDataType();
    }

    @Override
    public final DataType<R> getDataType(Configuration configuration) {
        return this.rf().getDataType(configuration);
    }

    @Override
    public final String getName() {
        return this.rf().getName();
    }

    @Override
    public final Name getQualifiedName() {
        return this.rf().getQualifiedName();
    }

    @Override
    public final Name getUnqualifiedName() {
        return this.rf().getUnqualifiedName();
    }

    @Override
    public final String getComment() {
        return this.rf().getComment();
    }

    @Override
    public final Comment getCommentPart() {
        return this.rf().getCommentPart();
    }

    final AbstractRow convertTo(Row row) {
        int size;
        block3: {
            size = this.fields.size();
            for (int i = 0; i < size; ++i) {
                if (!Tools.isVal(this.fields.field(i)) || Tools.isVal(row.field(i))) {
                    continue;
                }
                break block3;
            }
            return this;
        }
        Field[] result = new Field[size];
        for (int i = 0; i < size; ++i) {
            Field<?> f = this.fields.field(i);
            Val<?> v = Tools.extractVal(f);
            result[i] = v != null ? v.convertTo(row.field(i).getDataType()) : f;
        }
        return Tools.row0(result);
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (ctx.family() == SQLDialect.JAVA) {
            ctx.sql("row(").visit(QueryPartListView.wrap((QueryPart[])Val.getJavaValues(this.fields.fields))).sql(')');
            return;
        }
        if (ctx.family() == SQLDialect.INFORMIX) {
            ctx.visit(Keywords.K_ROW).sql(" ");
        }
        ctx.sql("(").visit(QueryPartListView.wrap((QueryPart[])this.fields.fields)).sql(")");
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }

    static final Condition compare(Row arg1, Comparator comparator, Row arg2) {
        switch (comparator) {
            case EQUALS: {
                return new RowEq<Row>(arg1, arg2);
            }
            case GREATER: {
                return new RowGt<Row>(arg1, arg2);
            }
            case GREATER_OR_EQUAL: {
                return new RowGe<Row>(arg1, arg2);
            }
            case LESS: {
                return new RowLt<Row>(arg1, arg2);
            }
            case LESS_OR_EQUAL: {
                return new RowLe<Row>(arg1, arg2);
            }
            case NOT_EQUALS: {
                return new RowNe<Row>(arg1, arg2);
            }
            case IS_DISTINCT_FROM: {
                return new RowIsDistinctFrom(arg1, arg2, false);
            }
            case IS_NOT_DISTINCT_FROM: {
                return new RowIsDistinctFrom(arg1, arg2, true);
            }
        }
        throw new IllegalArgumentException("Comparator not supported: " + String.valueOf((Object)comparator));
    }

    final Condition compare(Comparator comparator, Row row) {
        return AbstractRow.compare(this, comparator, row);
    }

    @Override
    public final int size() {
        return this.fields.size();
    }

    @Override
    public final Row fieldsRow() {
        return this;
    }

    @Override
    public final Stream<Field<?>> fieldStream() {
        return Stream.of(this.fields());
    }

    @Override
    public final <T> Field<T> field(Field<T> field2) {
        return this.fields.field(field2);
    }

    @Override
    @Deprecated
    public final Field<?> field(String name) {
        return this.fields.field(name);
    }

    @Override
    @Deprecated
    public final <T> Field<T> field(String name, Class<T> type) {
        return this.fields.field(name, type);
    }

    @Override
    @Deprecated
    public final <T> Field<T> field(String name, DataType<T> dataType) {
        return this.fields.field(name, dataType);
    }

    @Override
    @Deprecated
    public final Field<?> field(Name name) {
        return this.fields.field(name);
    }

    @Override
    @Deprecated
    public final <T> Field<T> field(Name name, Class<T> type) {
        return this.fields.field(name, type);
    }

    @Override
    @Deprecated
    public final <T> Field<T> field(Name name, DataType<T> dataType) {
        return this.fields.field(name, dataType);
    }

    @Override
    public final Field<?> field(int index) {
        return this.fields.field(index);
    }

    @Override
    public final <T> Field<T> field(int index, Class<T> type) {
        return this.fields.field(index, type);
    }

    @Override
    public final <T> Field<T> field(int index, DataType<T> dataType) {
        return this.fields.field(index, dataType);
    }

    @Override
    public final Field<?>[] fields() {
        return this.fields.fields();
    }

    @Override
    public final Field<?>[] fields(Field<?> ... f) {
        return this.fields.fields(f);
    }

    @Override
    public final Field<?>[] fields(String ... fieldNames) {
        return this.fields.fields(fieldNames);
    }

    @Override
    public final Field<?>[] fields(Name ... fieldNames) {
        return this.fields.fields(fieldNames);
    }

    @Override
    public final Field<?>[] fields(int ... fieldIndexes) {
        return this.fields.fields(fieldIndexes);
    }

    @Override
    public final int indexOf(Field<?> field2) {
        return this.fields.indexOf(field2);
    }

    @Override
    public final int indexOf(String fieldName) {
        return this.fields.indexOf(fieldName);
    }

    @Override
    public final int indexOf(Name fieldName) {
        return this.fields.indexOf(fieldName);
    }

    @Override
    public final Class<?>[] types() {
        return this.fields.types();
    }

    @Override
    public final Class<?> type(int fieldIndex) {
        return this.fields.type(fieldIndex);
    }

    @Override
    public final Class<?> type(String fieldName) {
        return this.fields.type(fieldName);
    }

    @Override
    public final Class<?> type(Name fieldName) {
        return this.fields.type(fieldName);
    }

    @Override
    public final DataType<?>[] dataTypes() {
        return this.fields.dataTypes();
    }

    @Override
    public final DataType<?> dataType(int fieldIndex) {
        return this.fields.dataType(fieldIndex);
    }

    @Override
    public final DataType<?> dataType(String fieldName) {
        return this.fields.dataType(fieldName);
    }

    @Override
    public final DataType<?> dataType(Name fieldName) {
        return this.fields.dataType(fieldName);
    }

    @Override
    public final Condition isNull() {
        return new RowIsNull(this);
    }

    @Override
    public final Condition isNotNull() {
        return new RowIsNotNull(this);
    }

    @Override
    public final Name $name() {
        return Names.N_ROW;
    }

    @Override
    public final DataType<R> $dataType() {
        return this.getDataType();
    }

    @Override
    public final QOM.UnmodifiableList<? extends Field<?>> $fields() {
        return QOM.unmodifiable((QueryPart[])this.fields());
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$fields());
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.fields(), Tools::row0, replacer);
    }

    @Override
    public int hashCode() {
        return this.fields.hashCode();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that instanceof AbstractRow) {
            AbstractRow r = (AbstractRow)that;
            return this.fields.equals(r.fields);
        }
        return super.equals(that);
    }
}

