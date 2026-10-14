/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.SQLWarning;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Converter;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Fields;
import org.jooq.Name;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.RecordType;
import org.jooq.Row;
import org.jooq.Select;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.AbstractRecord;
import org.jooq.impl.AbstractRow;
import org.jooq.impl.DSL;
import org.jooq.impl.EmbeddableTableField;
import org.jooq.impl.FieldsTrait;
import org.jooq.impl.Mappable;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartListView;
import org.jooq.impl.RowAsField;
import org.jooq.impl.RowImplN;
import org.jooq.impl.TableAsField;
import org.jooq.impl.ThrowingFunction;
import org.jooq.impl.Tools;
import org.jooq.tools.JooqLogger;

final class FieldsImpl<R extends Record>
extends AbstractQueryPart
implements RecordType<R>,
Mappable<R>,
QOM.UTransient {
    private static final JooqLogger log = JooqLogger.getLogger(FieldsImpl.class);
    Field<?>[] fields;
    private static final FieldOrIndex<Field<?>> RETURN_FIELD = new FieldOrIndex<Field<?>>(){

        @Override
        public Field<?> result(Field<?> field2, int index) {
            return field2;
        }

        @Override
        public Field<?> resultNull() {
            return null;
        }
    };
    private static final FieldOrIndex<Integer> RETURN_INDEX = new FieldOrIndex<Integer>(){

        @Override
        public Integer result(Field<?> field2, int index) {
            return index;
        }

        @Override
        public Integer resultNull() {
            return -1;
        }
    };

    FieldsImpl(SelectField<?> ... fields) {
        this.fields = Tools.map(fields, FieldsImpl.toField(), Field[]::new);
    }

    FieldsImpl(Collection<? extends SelectField<?>> fields) {
        this.fields = Tools.map(fields, FieldsImpl.toField(), Field[]::new);
    }

    @Override
    public final RecordMapper<R, ?> mapper(int fieldIndex) {
        return r -> r.get(fieldIndex);
    }

    @Override
    public final <U> RecordMapper<R, U> mapper(int fieldIndex, Configuration configuration, Class<? extends U> type) {
        return this.mapper(fieldIndex, Tools.converterOrFail(configuration, null, this.fields[this.safeIndex(fieldIndex)].getType(), type));
    }

    @Override
    public final <U> RecordMapper<R, U> mapper(int fieldIndex, Converter<?, ? extends U> converter) {
        return r -> r.get(fieldIndex, converter);
    }

    @Override
    public final RecordMapper<R, Record> mapper(int[] fieldIndexes) {
        return this.mapper(this.fields(fieldIndexes));
    }

    @Override
    public final RecordMapper<R, ?> mapper(String fieldName) {
        return this.mapper(Tools.indexOrFail((Fields)this, fieldName));
    }

    @Override
    public final <U> RecordMapper<R, U> mapper(String fieldName, Configuration configuration, Class<? extends U> type) {
        return this.mapper(fieldName, Tools.converterOrFail(configuration, null, this.field(Tools.indexOrFail((Fields)this, fieldName)).getType(), type));
    }

    @Override
    public final <U> RecordMapper<R, U> mapper(String fieldName, Converter<?, ? extends U> converter) {
        return r -> r.get(fieldName, converter);
    }

    @Override
    public final RecordMapper<R, Record> mapper(String[] fieldNames) {
        return this.mapper(this.fields(fieldNames));
    }

    @Override
    public final RecordMapper<R, ?> mapper(Name fieldName) {
        return this.mapper(Tools.indexOrFail((Fields)this, fieldName));
    }

    @Override
    public final <U> RecordMapper<R, U> mapper(Name fieldName, Configuration configuration, Class<? extends U> type) {
        return this.mapper(fieldName, Tools.converterOrFail(configuration, null, this.field(Tools.indexOrFail((Fields)this, fieldName)).getType(), type));
    }

    @Override
    public final <U> RecordMapper<R, U> mapper(Name fieldName, Converter<?, ? extends U> converter) {
        return r -> r.get(fieldName, converter);
    }

    @Override
    public final RecordMapper<R, Record> mapper(Name[] fieldNames) {
        return this.mapper(this.fields(fieldNames));
    }

    @Override
    public final <T> RecordMapper<R, T> mapper(Field<T> field2) {
        return this.mapper(Tools.indexOrFail((Fields)this, field2));
    }

    @Override
    public final <U> RecordMapper<R, U> mapper(Field<?> field2, Configuration configuration, Class<? extends U> type) {
        return this.mapper(field2, Tools.converterOrFail(configuration, null, field2.getType(), type));
    }

    @Override
    public final <T, U> RecordMapper<R, U> mapper(Field<T> field2, Converter<? super T, ? extends U> converter) {
        return this.mapper(Tools.indexOrFail((Fields)this, field2), converter);
    }

    @Override
    public final RecordMapper<R, Record> mapper(Field<?>[] f) {
        AbstractRow<?> row = Tools.row0(f == null ? Tools.EMPTY_FIELD : f);
        return r -> Tools.newRecord(false, AbstractRecord.class, row, r.configuration()).operate(x -> {
            for (Field<?> field2 : row.fields.fields) {
                Tools.copyValue((AbstractRecord)x, field2, r, field2);
            }
            return x;
        });
    }

    @Override
    public final <S extends Record> RecordMapper<R, S> mapper(Table<S> table) {
        return r -> r.into(table);
    }

    @Override
    public final <E> RecordMapper<R, E> mapper(Configuration configuration, Class<? extends E> type) {
        return configuration.recordMapperProvider().provide(this, type);
    }

    static final Row fieldsRow0(FieldsTrait fields) {
        Row row;
        if (fields instanceof Select) {
            Select s2 = (Select)((Object)fields);
            row = s2.asTable("t").fieldsRow();
        } else {
            row = fields.fieldsRow();
        }
        return row;
    }

    private static final ThrowingFunction<SelectField<?>, Field<?>, RuntimeException> toField() {
        return f -> {
            Field<Object> field2;
            if (f instanceof Row) {
                Row r = (Row)((Object)f);
                field2 = new RowAsField(r);
            } else if (f instanceof Table) {
                Table t2 = (Table)f;
                field2 = new TableAsField(t2);
            } else {
                field2 = (Field)f;
            }
            return field2;
        };
    }

    @Override
    public final int size() {
        return this.fields.length;
    }

    @Override
    public final <T> Field<T> field(Field<T> field2) {
        return this.field0(field2, RETURN_FIELD);
    }

    private final <U> U field0(Field<?> field2, FieldOrIndex<U> result) {
        Field<?> f;
        int i;
        if (field2 == null) {
            return result.resultNull();
        }
        for (i = 0; i < this.fields.length; ++i) {
            f = this.fields[i];
            if (f != field2) continue;
            return result.result(f, i);
        }
        for (i = 0; i < this.fields.length; ++i) {
            f = this.fields[i];
            if (!f.equals(field2)) continue;
            return result.result(f, i);
        }
        Field<?> columnOnlyMatch = null;
        Field<?> columnOnlyMatch2 = null;
        int columnOnlyIndexMatch = -1;
        Field<?> unaliased = null;
        Field<?> aliasMatch = null;
        Field<?> aliasMatch2 = null;
        int aliasIndexMatch = -1;
        String tableName = this.tableName(field2);
        String fieldName = field2.getName();
        for (int i2 = 0; i2 < this.fields.length; ++i2) {
            String tName;
            Field<?> f2 = this.fields[i2];
            String fName = f2.getName();
            if (tableName != null && (tName = this.tableName(f2)) != null && tableName.equals(tName) && fName.equals(fieldName)) {
                return result.result(f2, i2);
            }
            if (!fName.equals(fieldName)) continue;
            if (unaliased == null) {
                unaliased = Tools.unaliasTable(field2);
            }
            if (unaliased != null && unaliased.equals(Tools.unaliasTable(f2))) {
                if (aliasMatch == null) {
                    aliasMatch = f2;
                    aliasIndexMatch = i2;
                } else {
                    aliasMatch2 = f2;
                }
            }
            if (columnOnlyMatch == null) {
                columnOnlyMatch = f2;
                columnOnlyIndexMatch = i2;
                continue;
            }
            columnOnlyMatch2 = f2;
        }
        if (aliasMatch2 != null && log.isInfoEnabled()) {
            log.info((Object)("Ambiguous match found for " + String.valueOf(field2) + ". Both " + String.valueOf(aliasMatch) + " and " + String.valueOf(aliasMatch2) + " match."), new SQLWarning());
        }
        if (aliasMatch != null) {
            return result.result(aliasMatch, aliasIndexMatch);
        }
        if (columnOnlyMatch2 != null && log.isInfoEnabled()) {
            log.info((Object)("Ambiguous match found for " + String.valueOf(field2) + ". Both " + String.valueOf(columnOnlyMatch) + " and " + String.valueOf(columnOnlyMatch2) + " match."), new SQLWarning());
        }
        return result.result(columnOnlyMatch, columnOnlyIndexMatch);
    }

    private final String tableName(Field<?> field2) {
        TableField f;
        Table table;
        if (field2 instanceof TableField && (table = (f = (TableField)field2).getTable()) != null) {
            return table.getName();
        }
        return null;
    }

    @Override
    public final Field<?> field(String fieldName) {
        return this.field0(fieldName, RETURN_FIELD);
    }

    private final <U> U field0(String fieldName, FieldOrIndex<U> result) {
        if (fieldName == null) {
            return result.resultNull();
        }
        Field<?> columnMatch = null;
        int indexMatch = -1;
        for (int i = 0; i < this.fields.length; ++i) {
            Field<?> f = this.fields[i];
            if (!f.getName().equals(fieldName)) continue;
            if (columnMatch == null) {
                columnMatch = f;
                indexMatch = i;
                continue;
            }
            log.info((Object)("Ambiguous match found for " + fieldName + ". Both " + String.valueOf(columnMatch) + " and " + String.valueOf(f) + " match."), new SQLWarning());
        }
        return result.result(columnMatch, indexMatch);
    }

    @Override
    public final <T> Field<T> field(String fieldName, Class<T> type) {
        Field<?> result = this.field(fieldName);
        return result == null ? null : result.coerce(type);
    }

    @Override
    public final <T> Field<T> field(String fieldName, DataType<T> dataType) {
        Field<?> result = this.field(fieldName);
        return result == null ? null : result.coerce(dataType);
    }

    @Override
    public final Field<?> field(Name name) {
        return this.field0(name, RETURN_FIELD);
    }

    private final <U> U field0(Name name, FieldOrIndex<U> result) {
        if (name == null) {
            return result.resultNull();
        }
        return this.field0(DSL.field(name), result);
    }

    @Override
    public final <T> Field<T> field(Name fieldName, Class<T> type) {
        Field<?> result = this.field(fieldName);
        return result == null ? null : result.coerce(type);
    }

    @Override
    public final <T> Field<T> field(Name fieldName, DataType<T> dataType) {
        Field<?> result = this.field(fieldName);
        return result == null ? null : result.coerce(dataType);
    }

    @Override
    public final Field<?> field(int index) {
        if (index >= 0 && index < this.fields.length) {
            return this.fields[index];
        }
        return null;
    }

    final int safeIndex(int index) {
        if (index >= 0 && index < this.fields.length) {
            return index;
        }
        throw new IllegalArgumentException("No field at index " + index + " in Record type " + String.valueOf(this.fields));
    }

    @Override
    public final <T> Field<T> field(int fieldIndex, Class<T> type) {
        Field<?> result = this.field(fieldIndex);
        return result == null ? null : result.coerce(type);
    }

    @Override
    public final <T> Field<T> field(int fieldIndex, DataType<T> dataType) {
        Field<?> result = this.field(fieldIndex);
        return result == null ? null : result.coerce(dataType);
    }

    @Override
    public final Field<?>[] fields() {
        return this.fields;
    }

    @Override
    public final Row fieldsRow() {
        return new RowImplN(this.fields);
    }

    @Override
    public final Stream<Field<?>> fieldStream() {
        return Stream.of(this.fields);
    }

    @Override
    public final Field<?>[] fields(Field<?> ... f) {
        return Tools.map(f, i -> this.field((Field)i), Field[]::new);
    }

    @Override
    public final Field<?>[] fields(String ... f) {
        return Tools.map(f, i -> this.field((String)i), Field[]::new);
    }

    @Override
    public final Field<?>[] fields(Name ... f) {
        return Tools.map(f, i -> this.field((Name)i), Field[]::new);
    }

    @Override
    public final Field<?>[] fields(int ... f) {
        return Tools.map(f, i -> this.field(i), Field[]::new);
    }

    @Override
    public final int indexOf(Field<?> field2) {
        return this.field0(field2, RETURN_INDEX);
    }

    @Override
    public final int indexOf(String fieldName) {
        return this.field0(fieldName, RETURN_INDEX);
    }

    @Override
    public final int indexOf(Name fieldName) {
        return this.field0(fieldName, RETURN_INDEX);
    }

    @Override
    public final Class<?>[] types() {
        return Tools.map(this.fields, f -> f.getType(), Class[]::new);
    }

    @Override
    public final Class<?> type(int fieldIndex) {
        return fieldIndex >= 0 && fieldIndex < this.size() ? this.field(fieldIndex).getType() : null;
    }

    @Override
    public final Class<?> type(String fieldName) {
        return this.type(Tools.indexOrFail((Fields)this, fieldName));
    }

    @Override
    public final Class<?> type(Name fieldName) {
        return this.type(Tools.indexOrFail((Fields)this, fieldName));
    }

    @Override
    public final DataType<?>[] dataTypes() {
        return Tools.map(this.fields, f -> f.getDataType(), DataType[]::new);
    }

    @Override
    public final DataType<?> dataType(int fieldIndex) {
        return fieldIndex >= 0 && fieldIndex < this.size() ? this.field(fieldIndex).getDataType() : null;
    }

    @Override
    public final DataType<?> dataType(String fieldName) {
        return this.dataType(Tools.indexOrFail((Fields)this, fieldName));
    }

    @Override
    public final DataType<?> dataType(Name fieldName) {
        return this.dataType(Tools.indexOrFail((Fields)this, fieldName));
    }

    final int[] indexesOf(Field<?> ... f) {
        int[] result = new int[f.length];
        for (int i = 0; i < f.length; ++i) {
            result[i] = Tools.indexOrFail((Fields)this, f[i]);
        }
        return result;
    }

    final int[] indexesOf(String ... fieldNames) {
        int[] result = new int[fieldNames.length];
        for (int i = 0; i < fieldNames.length; ++i) {
            result[i] = Tools.indexOrFail((Fields)this, fieldNames[i]);
        }
        return result;
    }

    final int[] indexesOf(Name ... fieldNames) {
        int[] result = new int[fieldNames.length];
        for (int i = 0; i < fieldNames.length; ++i) {
            result[i] = Tools.indexOrFail((Fields)this, fieldNames[i]);
        }
        return result;
    }

    @Override
    public final void accept(Context<?> ctx) {
        ctx.visit(QueryPartListView.wrap((QueryPart[])this.fields));
    }

    final void add(Field<?> f) {
        Field[] result = new Field[this.fields.length + 1];
        System.arraycopy(this.fields, 0, result, 0, this.fields.length);
        result[this.fields.length] = f;
        this.fields = result;
    }

    final void replace(Field<?>[] replace, EmbeddableTableField<?, ?> by) {
        Field<?> r;
        if (replace == null || replace.length == 0) {
            return;
        }
        ArrayList l = new ArrayList(Arrays.asList(this.fields));
        int index = -1;
        Field<?>[] fieldArray = replace;
        int n = fieldArray.length;
        for (int i = 0; i < n && (index = this.indexOf(r = fieldArray[i])) == -1; ++i) {
        }
        if (index == -1) {
            return;
        }
        l.set(index, by);
        l.removeAll(Arrays.asList(replace));
        this.fields = l.toArray(Tools.EMPTY_FIELD);
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that instanceof FieldsImpl) {
            FieldsImpl f = (FieldsImpl)that;
            return Arrays.equals(this.fields, f.fields);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.fields);
    }

    private static interface FieldOrIndex<U> {
        public U result(Field<?> var1, int var2);

        public U resultNull();
    }
}

