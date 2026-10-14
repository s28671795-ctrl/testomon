/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.time.Instant;
import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.FieldOrRow;
import org.jooq.Generator;
import org.jooq.GeneratorStatementType;
import org.jooq.Param;
import org.jooq.Pro;
import org.jooq.Record;
import org.jooq.RenderContext;
import org.jooq.Row;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.SelectFieldOrAsterisk;
import org.jooq.SelectSelectStep;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableLike;
import org.jooq.conf.WriteIfReadonly;
import org.jooq.exception.DataTypeException;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.AbstractStoreQuery;
import org.jooq.impl.CustomField;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultGeneratorContext;
import org.jooq.impl.Keywords;
import org.jooq.impl.LazyVal;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartCollectionView;
import org.jooq.impl.ScalarSubquery;
import org.jooq.impl.ThrowingFunction;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;

final class FieldMapsForInsert
extends AbstractQueryPart
implements QOM.UNotYetImplemented {
    static final Set<SQLDialect> CASTS_NEEDED = SQLDialect.supportedBy(SQLDialect.BIGQUERY, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB);
    static final Set<SQLDialect> CASTS_NEEDED_FOR_MERGE = SQLDialect.supportedBy(SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB);
    final Table<?> table;
    final Map<Field<?>, Field<?>> empty;
    final Map<Field<?>, List<Field<?>>> values;
    int rows;
    int nextRow = -1;

    FieldMapsForInsert(Table<?> table) {
        this.table = table;
        this.values = new LinkedHashMap();
        this.empty = new LinkedHashMap();
    }

    void clear() {
        this.empty.clear();
        this.values.clear();
        this.rows = 0;
        this.nextRow = -1;
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (!this.isExecutable()) {
            ctx.formatSeparator().start(Clause.INSERT_VALUES).visit(Keywords.K_DEFAULT_VALUES).end(Clause.INSERT_VALUES);
        } else if (Boolean.TRUE.equals(ctx.data(Tools.BooleanDataKey.DATA_EMULATE_BULK_INSERT_RETURNING))) {
            ctx.formatSeparator().start(Clause.INSERT_VALUES).visit(Keywords.K_VALUES).sql(' ');
            this.toSQL92ValuesEmulateBulkInsertReturning(ctx);
            ctx.end(Clause.INSERT_VALUES);
        } else if (Tools.anyComputedOnClientStoredFields(ctx.configuration(), this.table)) {
            FieldMapsForInsert.toSQLInsertSelect(ctx, this.computeOnClientStoredSelect(ctx));
        } else if (this.rows == 1 && this.supportsValues(ctx)) {
            this.toSQLValues(ctx);
        } else {
            switch (ctx.family()) {
                case ACCESS: 
                case INFORMIX: 
                case TERADATA: {
                    Select<Record> select = this.insertSelect(ctx, GeneratorStatementType.INSERT);
                    FieldMapsForInsert.toSQLInsertSelect(ctx, DSL.select(new SelectFieldOrAsterisk[0]).from((TableLike<?>)select.asTable(DSL.name("t"), Tools.fieldNames(Tools.degree(select)))));
                    break;
                }
                case ORACLE: {
                    FieldMapsForInsert.toSQLInsertSelect(ctx, this.insertSelect(ctx, GeneratorStatementType.INSERT));
                    break;
                }
                case SNOWFLAKE: 
                case SQLSERVER: 
                case MARIADB: {
                    if (this.supportsValues(ctx)) {
                        this.toSQLValues(ctx);
                        break;
                    }
                    FieldMapsForInsert.toSQLInsertSelect(ctx, this.insertSelect(ctx, GeneratorStatementType.INSERT));
                    break;
                }
                case ASE: 
                case HANA: 
                case INGRES: 
                case SQLDATAWAREHOUSE: 
                case VERTICA: 
                case FIREBIRD: {
                    FieldMapsForInsert.toSQLInsertSelect(ctx, this.insertSelect(ctx, GeneratorStatementType.INSERT));
                    break;
                }
                default: {
                    this.toSQLValues(ctx);
                }
            }
        }
    }

    private final void toSQLValues(Context<?> ctx) {
        ctx.formatSeparator().start(Clause.INSERT_VALUES).visit(Keywords.K_VALUES).sql(' ');
        this.toSQL92Values(ctx);
        ctx.end(Clause.INSERT_VALUES);
    }

    static final void toSQLInsertSelect(Context<?> ctx, Select<?> select) {
        ctx.formatSeparator().start(Clause.INSERT_SELECT).visit(select).end(Clause.INSERT_SELECT);
    }

    @Pro
    final Set<Field<?>> keysFlattenedAndComputedOnClientStored(Context<?> ctx, GeneratorStatementType statementType) {
        return FieldMapsForInsert.keysFlattenedAndComputedOnClientStored(ctx, this.keysFlattened(ctx, statementType), this.table, statementType);
    }

    @Pro
    static final Set<Field<?>> keysFlattenedAndComputedOnClientStored(Context<?> ctx, Collection<? extends FieldOrRow> keys2, Table<?> table, GeneratorStatementType statementType) {
        Set result = Tools.flattenFieldOrRows(keys2, new LinkedHashSet());
        result.removeIf(k -> k.getDataType().computedOnClientStored(ctx.configuration()));
        for (Field<?> f : Tools.flattenCollection(Arrays.asList(table.fields()))) {
            if (!f.getDataType().computedOnClientStoredOn(statementType, ctx.configuration())) continue;
            result.add(f);
        }
        return result;
    }

    @Pro
    final Select<?> computeOnClientStoredSelect(Context<?> ctx) {
        return FieldMapsForInsert.computeOnClientStoredSelect(ctx, this.keysFlattened(ctx, GeneratorStatementType.INSERT), this.table, this.insertSelect(ctx, GeneratorStatementType.INSERT), GeneratorStatementType.INSERT);
    }

    @Pro
    static final Select<?> computeOnClientStoredSelect(Context<?> ctx, Set<? extends FieldOrRow> keys2, Table<?> table, Select<?> s2, GeneratorStatementType statementType) {
        return DSL.select(Tools.collect(Tools.filter(Tools.map(FieldMapsForInsert.keysFlattenedAndComputedOnClientStored(ctx, keys2, table, statementType), FieldMapsForInsert.computeOnClientStoredFieldMapper(ctx, false, statementType)), e -> e != null))).from((TableLike<?>)s2.asTable(DSL.table(DSL.name("t")), Tools.flattenFieldOrRows(keys2, new LinkedHashSet())));
    }

    @Pro
    static final <T> ThrowingFunction<FieldOrRow, Field<T>, RuntimeException> computeOnClientStoredFieldMapper(Context<?> ctx, boolean qualify, GeneratorStatementType statementType) {
        return fr -> {
            CustomField c;
            CustomField f = (CustomField)fr;
            if (f.getDataType().computedOnClientStored(ctx.configuration())) {
                if (f.getDataType().computedOnClientStoredOn(statementType, ctx.configuration())) {
                    Table table;
                    Generator generator = f.getDataType().generatedAlwaysAsGenerator();
                    Configuration configuration = ctx.configuration();
                    Map<Object, Object> map = ctx.data();
                    Instant instant = ctx.creationTime();
                    if (f instanceof TableField) {
                        TableField tf = (TableField)((Object)f);
                        table = tf.getTable();
                    } else {
                        table = null;
                    }
                    v5 = (Field)generator.apply(new DefaultGeneratorContext(configuration, map, instant, table, f, statementType));
                } else {
                    v5 = null;
                }
            } else {
                v5 = c = f;
            }
            return c == null ? null : (qualify ? c : CustomField.of(f.getUnqualifiedName(), f.getDataType(), c1 -> c1.qualify(qualify, c2 -> c2.visit(c))));
        };
    }

    @Pro
    final void checkReadonly(Context<?> ctx) {
        if (WriteIfReadonly.THROW.equals((Object)ctx.settings().getReadonlyInsert())) {
            for (Field<?> f : this.values.keySet()) {
                if (!f.getDataType().readonly()) continue;
                throw new DataTypeException("Cannot insert into readonly column: " + String.valueOf(f));
            }
        }
    }

    @Pro
    final boolean checkAnyWritables(Configuration c) {
        if (WriteIfReadonly.IGNORE.equals((Object)c.settings().getReadonlyInsert())) {
            return Tools.anyMatch(this.values.keySet(), f -> !f.getDataType().readonlyInternal(c));
        }
        return true;
    }

    private final boolean supportsValues(Context<?> ctx) {
        switch (ctx.family()) {
            case SQLDATAWAREHOUSE: {
                for (List<Field<?>> row : this.values.values()) {
                    for (Field<?> value : row) {
                        if (value instanceof Param) continue;
                        return false;
                    }
                }
                return true;
            }
            case SQLSERVER: {
                return this.rows <= 1000;
            }
            case VERTICA: 
            case MEMSQL: {
                for (List<Field<?>> row : this.values.values()) {
                    for (Field<?> value : row) {
                        if (!(value instanceof ScalarSubquery)) continue;
                        return false;
                    }
                }
                return true;
            }
            case SNOWFLAKE: {
                for (List<Field<?>> row : this.values.values()) {
                    for (Field<?> value : row) {
                        if (!Tools.isParamOrCastParam(value)) {
                            return false;
                        }
                        if (!value.getDataType().isJSON() && !value.getDataType().isSpatial()) continue;
                        return false;
                    }
                }
                return true;
            }
            case MARIADB: {
                for (List<Field<?>> row : this.values.values()) {
                    for (Field<?> value : row) {
                        if (!(value instanceof ScalarSubquery)) continue;
                        ScalarSubquery s2 = (ScalarSubquery)value;
                        if (!Tools.containsTable(s2.query.$from(), this.table, false)) continue;
                        return false;
                    }
                }
                return true;
            }
        }
        return true;
    }

    final Select<Record> insertSelect(Context<?> ctx, GeneratorStatementType statementType) {
        Select<Record> select = null;
        Map<Field<?>, List<Field<?>>> v = this.valuesFlattened(ctx, statementType);
        boolean needsCast = CASTS_NEEDED_FOR_MERGE.contains((Object)ctx.dialect()) && ctx.data(Tools.ExtendedDataKey.DATA_INSERT_ON_DUPLICATE_KEY_UPDATE) != null;
        for (int i = 0; i < this.rows; ++i) {
            int row = i;
            SelectSelectStep<Record> iteration = DSL.select(Tools.map(v.values(), l -> this.castNullsIfNeeded(ctx, needsCast, (Field)l.get(row))));
            select = select == null ? iteration : select.unionAll(iteration);
        }
        return select;
    }

    final Field<?> castNullsIfNeeded(Context<?> ctx, boolean needsCast, Field<?> f) {
        Val val;
        if (needsCast && f instanceof Val && (val = (Val)f).isInline(ctx) && val.getValue() == null) {
            return f.cast(f.getDataType());
        }
        return f;
    }

    final void toSQL92Values(Context<?> ctx) {
        boolean indent = this.values.size() > 1;
        boolean castFirstRowNumericValues = this.rows > 1 && ctx.family() == SQLDialect.SQLSERVER;
        RenderContext.CastMode previous = ctx.castMode();
        if (!CASTS_NEEDED.contains((Object)ctx.dialect())) {
            ctx.castMode(RenderContext.CastMode.NEVER);
        }
        for (int row = 0; row < this.rows; ++row) {
            if (row > 0) {
                ctx.sql(", ");
            }
            ctx.start(Clause.FIELD_ROW).sql('(');
            if (indent) {
                ctx.formatIndentStart();
            }
            String separator = "";
            for (List<Field<?>> list : this.valuesFlattened(ctx, GeneratorStatementType.INSERT).values()) {
                ctx.sql(separator);
                if (indent) {
                    ctx.formatNewLine();
                }
                if (castFirstRowNumericValues) {
                    Field<?> field2 = list.get(row);
                    DataType dataType = field2.getDataType();
                    if (dataType.isNumeric() && dataType.hasScale()) {
                        ctx.visit(field2.cast(dataType));
                    } else {
                        ctx.visit(field2);
                    }
                } else {
                    ctx.visit(list.get(row));
                }
                separator = ", ";
            }
            if (indent) {
                ctx.formatIndentEnd().formatNewLine();
            }
            ctx.sql(')').end(Clause.FIELD_ROW);
        }
        if (!CASTS_NEEDED.contains((Object)ctx.dialect())) {
            ctx.castMode(previous);
        }
    }

    @Pro
    final void toSQL92ValuesEmulateBulkInsertReturning(Context<?> ctx) {
        Set<Field<?>> k = this.keysFlattenedAndComputedOnClientStored(ctx, GeneratorStatementType.INSERT);
        boolean indent = k.size() > 1;
        RenderContext.CastMode previous = ctx.castMode();
        if (!CASTS_NEEDED.contains((Object)ctx.dialect())) {
            ctx.castMode(RenderContext.CastMode.NEVER);
        }
        ctx.start(Clause.FIELD_ROW).sql('(');
        if (indent) {
            ctx.formatIndentStart();
        }
        String separator = "";
        int i = 0;
        for (i = 0; i < k.size(); ++i) {
            ctx.sql(separator);
            if (indent) {
                ctx.formatNewLine();
            }
            ctx.sql('i').sql(i).sql("(i)");
            separator = ", ";
        }
        if (indent) {
            ctx.formatIndentEnd().formatNewLine();
        }
        ctx.sql(')').end(Clause.FIELD_ROW);
        if (!CASTS_NEEDED.contains((Object)ctx.dialect())) {
            ctx.castMode(previous);
        }
    }

    final void addFields(Collection<?> fields) {
        if (this.rows == 0) {
            this.newRecord();
        }
        this.initNextRow();
        for (Object field2 : fields) {
            Field<?> f = Tools.tableField(this.table, field2);
            Field<?> e = this.empty.get(f);
            if (e == null) {
                e = new LazyVal(f);
                this.empty.put(f, e);
            }
            if (this.values.containsKey(f)) continue;
            this.values.put(f, this.rows > 0 ? new ArrayList(Collections.nCopies(this.rows, e)) : new ArrayList());
        }
    }

    final void set(Collection<? extends Field<?>> fields) {
        this.initNextRow();
        Iterator<Field<?>> it1 = fields.iterator();
        Iterator<List<Field<?>>> it2 = this.values.values().iterator();
        while (it1.hasNext() && it2.hasNext()) {
            it2.next().set(this.rows - 1, it1.next());
        }
        if (it1.hasNext() || it2.hasNext()) {
            throw new IllegalArgumentException("Added record size (" + fields.size() + ") must match fields size (" + this.values.size() + ")");
        }
    }

    final <T> Field<T> set(Field<T> field2, Field<T> value) {
        this.addFields(Collections.singletonList(field2));
        return this.values.get(field2).set(this.rows - 1, value);
    }

    final void set(Map<?, ?> map) {
        this.addFields(map.keySet());
        map.forEach((k, v) -> {
            Field<?> field2 = Tools.tableField(this.table, k);
            this.values.get(field2).set(this.rows - 1, Tools.field(v, field2));
        });
    }

    private final void initNextRow() {
        if (this.rows == this.nextRow) {
            Iterator<List<Field<?>>> v = this.values.values().iterator();
            Iterator<Field<?>> e = this.empty.values().iterator();
            while (v.hasNext() && e.hasNext()) {
                v.next().add(e.next());
            }
            ++this.rows;
        }
    }

    final void newRecord() {
        if (this.nextRow < this.rows) {
            ++this.nextRow;
        }
    }

    final List<Row> rows() {
        final List<Map<Field<?>, Field<?>>> maps = this.maps();
        return new AbstractList<Row>(){

            @Override
            public Row get(int index) {
                return Tools.row0(((Map)maps.get(index)).values().toArray(Tools.EMPTY_FIELD));
            }

            @Override
            public int size() {
                return FieldMapsForInsert.this.rows;
            }
        };
    }

    final List<Map<Field<?>, Field<?>>> maps() {
        return new AbstractList<Map<Field<?>, Field<?>>>(){

            @Override
            public Map<Field<?>, Field<?>> get(int index) {
                return FieldMapsForInsert.this.map(index);
            }

            @Override
            public int size() {
                return FieldMapsForInsert.this.rows;
            }
        };
    }

    final Map<Field<?>, Field<?>> map(final int index) {
        return new AbstractMap<Field<?>, Field<?>>(){
            transient Set<Map.Entry<Field<?>, Field<?>>> entrySet;

            @Override
            public Set<Map.Entry<Field<?>, Field<?>>> entrySet() {
                if (this.entrySet == null) {
                    this.entrySet = new EntrySet();
                }
                return this.entrySet;
            }

            @Override
            public boolean containsKey(Object key) {
                return FieldMapsForInsert.this.values.containsKey(key);
            }

            @Override
            public boolean containsValue(Object value) {
                return Tools.anyMatch(FieldMapsForInsert.this.values.values(), list -> ((Field)list.get(index)).equals(value));
            }

            @Override
            public Field<?> get(Object key) {
                List<Field<?>> list = FieldMapsForInsert.this.values.get(key);
                return list == null ? null : list.get(index);
            }

            @Override
            public Field<?> put(Field<?> key, Field<?> value) {
                return FieldMapsForInsert.this.set(key, value);
            }

            @Override
            public Field<?> remove(Object key) {
                List<Field<?>> list = FieldMapsForInsert.this.values.get(key);
                FieldMapsForInsert.this.values.remove(key);
                return list == null ? null : list.get(index);
            }

            @Override
            public Set<Field<?>> keySet() {
                return FieldMapsForInsert.this.values.keySet();
            }

            final class EntrySet
            extends AbstractSet<Map.Entry<Field<?>, Field<?>>> {
                EntrySet() {
                }

                @Override
                public final int size() {
                    return FieldMapsForInsert.this.values.size();
                }

                @Override
                public final void clear() {
                    FieldMapsForInsert.this.values.clear();
                }

                @Override
                public final Iterator<Map.Entry<Field<?>, Field<?>>> iterator() {
                    return new Iterator<Map.Entry<Field<?>, Field<?>>>(){
                        final Iterator<Map.Entry<Field<?>, List<Field<?>>>> delegate;
                        {
                            this.delegate = FieldMapsForInsert.this.values.entrySet().iterator();
                        }

                        @Override
                        public boolean hasNext() {
                            return this.delegate.hasNext();
                        }

                        @Override
                        public Map.Entry<Field<?>, Field<?>> next() {
                            Map.Entry<Field<?>, List<Field<?>>> entry = this.delegate.next();
                            return new AbstractMap.SimpleImmutableEntry(entry.getKey(), entry.getValue().get(index));
                        }

                        @Override
                        public void remove() {
                            this.delegate.remove();
                        }
                    };
                }
            }
        };
    }

    final Map<Field<?>, Field<?>> lastMap() {
        return this.map(this.rows - 1);
    }

    final boolean isExecutable() {
        return this.rows > 0;
    }

    final Set<Field<?>> toSQLReferenceKeys(Context<?> ctx) {
        block4: {
            if (!this.isExecutable()) {
                return Collections.emptySet();
            }
            if (this.values.isEmpty()) {
                return Collections.emptySet();
            }
            for (Field<?> field2 : this.values.keySet()) {
                if (field2 instanceof AbstractStoreQuery.UnknownField) continue;
                break block4;
            }
            return Collections.emptySet();
        }
        Set<Field<?>> fields = Tools.anyComputedOnClientStoredFields(ctx.configuration(), this.table) ? this.keysFlattenedAndComputedOnClientStored(ctx, GeneratorStatementType.INSERT) : this.keysFlattened(ctx, GeneratorStatementType.INSERT);
        if (!fields.isEmpty()) {
            ctx.sql(" (").visit(QueryPartCollectionView.wrap(fields).qualify(false)).sql(')');
        }
        return fields;
    }

    private static final <E> Iterable<E> removeReadonly(Context<?> ctx, Iterable<E> it, Function<? super E, ? extends Field<?>> f) {
        if (ctx.settings().getReadonlyInsert() == WriteIfReadonly.IGNORE) {
            it = Tools.filter(it, e -> !((Field)f.apply((Object)e)).getDataType().readonlyInternal(ctx.configuration()));
        }
        return it;
    }

    final Set<Field<?>> keysFlattened(Context<?> ctx, GeneratorStatementType statementType) {
        return this.valuesFlattened(ctx, statementType).keySet();
    }

    final Map<Field<?>, List<Field<?>>> valuesFlattened(Context<?> ctx, GeneratorStatementType statementType) {
        LinkedHashMap result = new LinkedHashMap();
        Set overlapping = null;
        for (Map.Entry entry : FieldMapsForInsert.removeReadonly(ctx, this.values.entrySet(), Map.Entry::getKey)) {
            Field key = (Field)entry.getKey();
            DataType keyType = key.getDataType();
            List value = (List)entry.getValue();
            if (keyType.isEmbeddable()) {
                ArrayList<Iterator<Field>> valueFlattened = new ArrayList<Iterator<Field>>(value.size());
                for (Field v : value) {
                    valueFlattened.add(Tools.flatten(v).iterator());
                }
                for (Field k : Tools.flatten(key)) {
                    DataType t2 = k.getDataType();
                    if (statementType != null && t2.computedOnClientStored(ctx.configuration()) && !t2.computedOnClientStoredOn(statementType, ctx.configuration()) || !(overlapping = Tools.lazy(overlapping)).add(k)) {
                        for (Iterator iterator2 : valueFlattened) {
                            if (!iterator2.hasNext()) continue;
                            iterator2.next();
                        }
                        continue;
                    }
                    ArrayList<Field> list = new ArrayList<Field>(value.size());
                    for (Iterator iterator3 : valueFlattened) {
                        list.add(iterator3.hasNext() ? (Field)iterator3.next() : null);
                    }
                    result.put(k, list);
                }
                continue;
            }
            if (statementType != null && keyType.computedOnClientStored() && !keyType.computedOnClientStoredOn(statementType)) continue;
            result.put(key, value);
        }
        return result;
    }
}

