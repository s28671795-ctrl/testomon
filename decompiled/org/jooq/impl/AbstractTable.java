/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.jooq.Binding;
import org.jooq.Catalog;
import org.jooq.Check;
import org.jooq.Clause;
import org.jooq.Comment;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.ContextConverter;
import org.jooq.Converter;
import org.jooq.DataType;
import org.jooq.DivideByOnStep;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Generator;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.JoinType;
import org.jooq.Link;
import org.jooq.Name;
import org.jooq.Named;
import org.jooq.Package;
import org.jooq.PeriodSpecification;
import org.jooq.PivotForStep;
import org.jooq.Pro;
import org.jooq.QualifiedAsterisk;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.RecordType;
import org.jooq.Result;
import org.jooq.Row;
import org.jooq.RowId;
import org.jooq.SQL;
import org.jooq.Schema;
import org.jooq.Select;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableLike;
import org.jooq.TableOnStep;
import org.jooq.TableOptionalOnStep;
import org.jooq.TableOptions;
import org.jooq.TableOuterJoinStep;
import org.jooq.TablePartitionByStep;
import org.jooq.UniqueKey;
import org.jooq.VersionsBetweenAndStep;
import org.jooq.impl.AbstractNamed;
import org.jooq.impl.CrossApply;
import org.jooq.impl.CrossJoin;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultBinding;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DivideBy;
import org.jooq.impl.FieldsImpl;
import org.jooq.impl.FieldsTrait;
import org.jooq.impl.FlashbackTable;
import org.jooq.impl.FullJoin;
import org.jooq.impl.HintedTable;
import org.jooq.impl.IdentityImpl;
import org.jooq.impl.InlineDerivedTable;
import org.jooq.impl.Join;
import org.jooq.impl.LeftAntiJoin;
import org.jooq.impl.LeftJoin;
import org.jooq.impl.LeftSemiJoin;
import org.jooq.impl.LinkedTable;
import org.jooq.impl.NaturalFullJoin;
import org.jooq.impl.NaturalJoin;
import org.jooq.impl.NaturalLeftJoin;
import org.jooq.impl.NaturalRightJoin;
import org.jooq.impl.OuterApply;
import org.jooq.impl.PartitionJoinTable;
import org.jooq.impl.PeriodTable;
import org.jooq.impl.Pivot;
import org.jooq.impl.QOM;
import org.jooq.impl.QualifiedAsteriskImpl;
import org.jooq.impl.QualifiedRowid;
import org.jooq.impl.RightJoin;
import org.jooq.impl.StraightJoin;
import org.jooq.impl.TableAlias;
import org.jooq.impl.TableAsField;
import org.jooq.impl.TableDataType;
import org.jooq.impl.TableEq;
import org.jooq.impl.TableFieldImpl;
import org.jooq.impl.TableImpl;
import org.jooq.impl.TableNe;
import org.jooq.impl.Tools;
import org.jooq.impl.WithOrdinalityTable;
import org.jooq.impl.WithTable;
import org.jooq.tools.JooqLogger;

abstract class AbstractTable<R extends Record>
extends AbstractNamed
implements Table<R>,
FieldsTrait,
QOM.Aliasable<Table<R>> {
    private static final JooqLogger log = JooqLogger.getLogger(AbstractTable.class);
    private static final Clause[] CLAUSES = new Clause[]{Clause.TABLE};
    private final TableOptions options;
    private Schema tableschema;
    private transient DataType<R> tabletype;
    private transient Identity<R, ?> identity;
    private transient Row fieldsRow;

    AbstractTable(TableOptions options, Name name) {
        this(options, name, null, null);
    }

    AbstractTable(TableOptions options, Name name, Schema schema) {
        this(options, name, schema, null);
    }

    AbstractTable(TableOptions options, Name name, Schema schema, Comment comment) {
        super(AbstractTable.qualify(schema, name), comment);
        this.options = options;
        this.tableschema = schema;
    }

    @Override
    public Name $alias() {
        return null;
    }

    @Override
    public Table<R> $aliased() {
        return this;
    }

    @Override
    public final Class<R> getType() {
        return this.getDataType().getType();
    }

    @Override
    public final DataType<R> getDataType(Configuration configuration) {
        return this.getDataType();
    }

    @Override
    public final DataType<R> $dataType() {
        return this.getDataType();
    }

    @Override
    public final Binding<?, R> getBinding() {
        return this.getDataType().getBinding();
    }

    @Override
    public final ContextConverter<?, R> getConverter() {
        return this.getDataType().getConverter();
    }

    @Override
    public final <U> SelectField<U> convert(Binding<R, U> binding) {
        return this.tf().convert((Binding)binding);
    }

    @Override
    public final <U> SelectField<U> convert(Converter<R, U> converter) {
        return this.tf().convert((Converter)converter);
    }

    @Override
    public final <U> SelectField<U> convert(Class<U> toType, Function<? super R, ? extends U> from, Function<? super U, ? extends R> to) {
        return this.tf().convert((Class)toType, from, to);
    }

    @Override
    public final <U> SelectField<U> convertFrom(Class<U> toType, Function<? super R, ? extends U> from) {
        return this.tf().convertFrom((Class)toType, from);
    }

    @Override
    public final <U> SelectField<U> convertFrom(Function<? super R, ? extends U> from) {
        return this.tf().convertFrom(from);
    }

    @Override
    public final <U> SelectField<U> convertTo(Class<U> toType, Function<? super U, ? extends R> to) {
        return this.tf().convertTo((Class)toType, to);
    }

    @Override
    public final <U> SelectField<U> convertTo(Function<? super U, ? extends R> to) {
        return this.tf().convertTo(to);
    }

    @Override
    public final SelectField<R> as(Field<?> otherField) {
        return this.as(otherField.getUnqualifiedName());
    }

    final TableAsField<R> tf() {
        return new TableAsField(this);
    }

    @Override
    public Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }

    @Override
    public final R from(Record record) {
        return record.into(this);
    }

    @Override
    public final QualifiedAsterisk asterisk() {
        return new QualifiedAsteriskImpl(this);
    }

    abstract FieldsImpl<R> fields0();

    @Override
    public final DataType<R> getDataType() {
        if (this.tabletype == null) {
            this.tabletype = new TableDataType(this);
        }
        return this.tabletype;
    }

    @Override
    public final RecordType<R> recordType() {
        return this.fields0();
    }

    @Override
    public final R newRecord() {
        return DSL.using(new DefaultConfiguration()).newRecord(this);
    }

    @Override
    public Row fieldsRow() {
        if (this.fieldsRow == null) {
            this.fieldsRow = Tools.row0(this.fields0());
        }
        return this.fieldsRow;
    }

    @Override
    public final Field<?>[] fields() {
        return this.fieldsRow().fields();
    }

    @Override
    public final Field<Result<R>> asMultiset() {
        return DSL.multiset(this);
    }

    @Override
    public final Field<Result<R>> asMultiset(String alias) {
        return DSL.multiset(this).as(alias);
    }

    @Override
    public final Field<Result<R>> asMultiset(Name alias) {
        return DSL.multiset(this).as(alias);
    }

    @Override
    public final Field<Result<R>> asMultiset(Field<?> alias) {
        return DSL.multiset(this).as((Field)alias);
    }

    @Override
    public final Table<R> asTable() {
        return this;
    }

    @Override
    public final Table<R> asTable(String alias) {
        return this.as(alias);
    }

    @Override
    public final Table<R> asTable(String alias, String ... fieldAliases) {
        return this.as(alias, fieldAliases);
    }

    @Override
    public final Table<R> asTable(String alias, Collection<? extends String> fieldAliases) {
        return this.as(alias, fieldAliases);
    }

    @Override
    public final Table<R> asTable(Name alias) {
        return this.as(alias);
    }

    @Override
    public final Table<R> asTable(Name alias, Name ... fieldAliases) {
        return this.as(alias, fieldAliases);
    }

    @Override
    public final Table<R> asTable(Name alias, Collection<? extends Name> fieldAliases) {
        return this.as(alias, fieldAliases);
    }

    @Override
    public final Table<R> asTable(Table<?> alias) {
        return this.as(alias);
    }

    @Override
    public final Table<R> asTable(Table<?> alias, Field<?> ... fieldAliases) {
        return this.as(alias, fieldAliases);
    }

    @Override
    public final Table<R> asTable(Table<?> alias, Collection<? extends Field<?>> fieldAliases) {
        return this.as(alias, fieldAliases);
    }

    @Override
    public final Table<R> asTable(String alias, Function<? super Field<?>, ? extends String> aliasFunction) {
        return this.as(alias, aliasFunction);
    }

    @Override
    public final Table<R> asTable(String alias, BiFunction<? super Field<?>, ? super Integer, ? extends String> aliasFunction) {
        return this.as(alias, aliasFunction);
    }

    @Override
    public Table<R> as(String alias) {
        return this.as(DSL.name(alias));
    }

    @Override
    public final Table<R> as(String alias, String ... fieldAliases) {
        return this.as(DSL.name(alias), Tools.names(fieldAliases));
    }

    @Override
    public final Table<R> as(String alias, Collection<? extends String> fieldAliases) {
        return this.as(DSL.name(alias), Tools.names(fieldAliases));
    }

    @Override
    public final Table<R> as(String alias, Function<? super Field<?>, ? extends String> aliasFunction) {
        return this.as(alias, Tools.map(this.fields(), f -> (String)aliasFunction.apply((Field<?>)f), String[]::new));
    }

    @Override
    public final Table<R> as(String alias, BiFunction<? super Field<?>, ? super Integer, ? extends String> aliasFunction) {
        return this.as(alias, Tools.map(this.fields(), (f, i) -> (String)aliasFunction.apply((Field<?>)f, (Integer)i), String[]::new));
    }

    @Override
    public Table<R> as(Name alias) {
        return new TableAlias(this, alias);
    }

    @Override
    public Table<R> as(Name alias, Name ... fieldAliases) {
        return new TableAlias(this, alias, fieldAliases);
    }

    @Override
    public final Table<R> as(Name alias, Collection<? extends Name> fieldAliases) {
        return this.as(alias, fieldAliases == null ? null : fieldAliases.toArray(Tools.EMPTY_NAME));
    }

    @Override
    public final Table<R> as(Name alias, Function<? super Field<?>, ? extends Name> aliasFunction) {
        return this.as(alias, Tools.map(this.fields(), f -> (Name)aliasFunction.apply((Field<?>)f), Name[]::new));
    }

    @Override
    public final Table<R> as(Name alias, BiFunction<? super Field<?>, ? super Integer, ? extends Name> aliasFunction) {
        return this.as(alias, Tools.map(this.fields(), (f, i) -> (Name)aliasFunction.apply((Field<?>)f, (Integer)i), Name[]::new));
    }

    @Override
    public final TableOptions.TableType getTableType() {
        return this.options.type();
    }

    @Override
    public final TableOptions getOptions() {
        return this.options;
    }

    @Override
    public final Catalog getCatalog() {
        return this.getSchema() == null ? null : this.getSchema().getCatalog();
    }

    @Override
    public final Package getPackage() {
        return null;
    }

    @Override
    public Schema getSchema() {
        if (this.tableschema == null) {
            this.tableschema = this.getQualifiedName().qualified() ? DSL.schema(this.getQualifiedName().qualifier()) : null;
        }
        return this.tableschema;
    }

    @Override
    public Identity<R, ?> getIdentity() {
        if (this.identity == null) {
            for (Field<?> f : this.fields()) {
                if (!(f instanceof TableField) || !f.getDataType().identity()) continue;
                if (this.identity == null) {
                    this.identity = new IdentityImpl(this, (TableField)f);
                    continue;
                }
                log.info((Object)"Multiple identities", "There are multiple identity fields in table " + String.valueOf(this) + ", which is not supported by jOOQ");
            }
            if (this.identity == null) {
                this.identity = IdentityImpl.NULL;
            }
        }
        return this.identity == IdentityImpl.NULL ? null : this.identity;
    }

    @Override
    public UniqueKey<R> getPrimaryKey() {
        return null;
    }

    @Override
    public TableField<R, ?> getRecordVersion() {
        return null;
    }

    @Override
    public TableField<R, ?> getRecordTimestamp() {
        return null;
    }

    @Override
    public List<Index> getIndexes() {
        return Collections.emptyList();
    }

    @Override
    public List<UniqueKey<R>> getKeys() {
        ArrayList<UniqueKey<R>> result = new ArrayList<UniqueKey<R>>();
        UniqueKey<R> pk = this.getPrimaryKey();
        if (pk != null) {
            result.add(pk);
        }
        result.addAll(this.getUniqueKeys());
        return result;
    }

    @Override
    public List<UniqueKey<R>> getUniqueKeys() {
        return Collections.emptyList();
    }

    @Override
    public final <O extends Record> List<ForeignKey<O, R>> getReferencesFrom(Table<O> other) {
        return other.getReferencesTo(this);
    }

    @Override
    public List<ForeignKey<R, ?>> getReferences() {
        return Collections.emptyList();
    }

    @Override
    public final <O extends Record> List<ForeignKey<R, O>> getReferencesTo(Table<O> other) {
        ArrayList result = new ArrayList();
        for (ForeignKey reference : this.getReferences()) {
            Tools.traverseJoins(other, o -> {
                if (Tools.unwrap(o).equals(reference.getKey().getTable())) {
                    result.add(reference);
                }
            });
        }
        return Collections.unmodifiableList(result);
    }

    @Override
    public List<Check<R>> getChecks() {
        return Collections.emptyList();
    }

    @Deprecated
    protected static final <R extends Record, T> TableField<R, T> createField(String name, DataType<T> type, Table<R> table) {
        return AbstractTable.createField(DSL.name(name), type, table, null, null, null, null);
    }

    @Deprecated
    protected static final <R extends Record, T> TableField<R, T> createField(String name, DataType<T> type, Table<R> table, String comment) {
        return AbstractTable.createField(DSL.name(name), type, table, comment, null, null, null);
    }

    @Deprecated
    protected static final <R extends Record, T, U> TableField<R, U> createField(String name, DataType<T> type, Table<R> table, String comment, Converter<T, U> converter) {
        return AbstractTable.createField(DSL.name(name), type, table, comment, converter, null, null);
    }

    @Deprecated
    protected static final <R extends Record, T, U> TableField<R, U> createField(String name, DataType<T> type, Table<R> table, String comment, Binding<T, U> binding) {
        return AbstractTable.createField(DSL.name(name), type, table, comment, null, binding);
    }

    @Deprecated
    protected static final <R extends Record, T, X, U> TableField<R, U> createField(String name, DataType<T> type, Table<R> table, String comment, Converter<X, U> converter, Binding<T, X> binding) {
        return AbstractTable.createField(DSL.name(name), type, table, comment, converter, binding);
    }

    @Deprecated
    protected final <T> TableField<R, T> createField(String name, DataType<T> type) {
        return AbstractTable.createField(DSL.name(name), type, this, null, null, null, null);
    }

    @Deprecated
    protected final <T> TableField<R, T> createField(String name, DataType<T> type, String comment) {
        return AbstractTable.createField(DSL.name(name), type, this, comment, null, null, null);
    }

    @Deprecated
    protected final <T, U> TableField<R, U> createField(String name, DataType<T> type, String comment, Converter<T, U> converter) {
        return AbstractTable.createField(DSL.name(name), type, this, comment, converter, null, null);
    }

    @Deprecated
    protected final <T, U> TableField<R, U> createField(String name, DataType<T> type, String comment, Binding<T, U> binding) {
        return AbstractTable.createField(DSL.name(name), type, this, comment, null, binding);
    }

    @Deprecated
    protected final <T, X, U> TableField<R, U> createField(String name, DataType<T> type, String comment, Converter<X, U> converter, Binding<T, X> binding) {
        return AbstractTable.createField(DSL.name(name), type, this, comment, converter, binding);
    }

    protected static final <R extends Record, T> TableField<R, T> createField(Name name, DataType<T> type, Table<R> table) {
        return AbstractTable.createField(name, type, table, null, null, null, null);
    }

    protected static final <R extends Record, T> TableField<R, T> createField(Name name, DataType<T> type, Table<R> table, String comment) {
        return AbstractTable.createField(name, type, table, comment, null, null, null);
    }

    protected static final <R extends Record, T, U> TableField<R, U> createField(Name name, DataType<T> type, Table<R> table, String comment, Converter<T, U> converter) {
        return AbstractTable.createField(name, type, table, comment, converter, null, null);
    }

    protected static final <R extends Record, T, U> TableField<R, U> createField(Name name, DataType<T> type, Table<R> table, String comment, Binding<T, U> binding) {
        return AbstractTable.createField(name, type, table, comment, null, binding, null);
    }

    protected static final <R extends Record, T, X, U> TableField<R, U> createField(Name name, DataType<T> type, Table<R> table, String comment, Converter<X, U> converter, Binding<T, X> binding) {
        return AbstractTable.createField(name, type, table, comment, converter, binding, null);
    }

    protected static final <R extends Record, TR extends Table<R>, T> TableField<R, T> createField(Name name, DataType<T> type, TR table, String comment, Generator<R, TR, T> generator) {
        return AbstractTable.createField(name, type, table, comment, null, null, generator);
    }

    protected static final <R extends Record, TR extends Table<R>, T, U> TableField<R, U> createField(Name name, DataType<T> type, TR table, String comment, Converter<T, U> converter, Generator<R, TR, U> generator) {
        return AbstractTable.createField(name, type, table, comment, converter, null, generator);
    }

    protected static final <R extends Record, TR extends Table<R>, T, U> TableField<R, U> createField(Name name, DataType<T> type, TR table, String comment, Binding<T, U> binding, Generator<R, TR, U> generator) {
        return AbstractTable.createField(name, type, table, comment, null, binding, generator);
    }

    protected static final <R extends Record, TR extends Table<R>, T, X, U> TableField<R, U> createField(Name name, DataType<T> type, TR table, String comment, Converter<X, U> converter, Binding<T, X> binding, Generator<R, TR, U> generator) {
        DataType<Object> actualType;
        Binding<T, U> actualBinding = DefaultBinding.newBinding(converter, type, binding);
        DataType<Object> dataType = actualType = converter == null && binding == null ? type : type.asConvertedDataType(actualBinding);
        if (generator != null) {
            actualType = actualType.generatedAlwaysAs(generator).generationLocation(QOM.GenerationLocation.CLIENT);
        }
        TableFieldImpl tableField = new TableFieldImpl(name, actualType, table, DSL.comment(comment), actualBinding);
        if (table instanceof TableImpl) {
            TableImpl t2 = (TableImpl)table;
            t2.fields0().add(tableField);
        }
        return tableField;
    }

    protected final <T> TableField<R, T> createField(Name name, DataType<T> type) {
        return AbstractTable.createField(name, type, this, null, null, null, null);
    }

    protected final <T> TableField<R, T> createField(Name name, DataType<T> type, String comment) {
        return AbstractTable.createField(name, type, this, comment, null, null, null);
    }

    protected final <T, U> TableField<R, U> createField(Name name, DataType<T> type, String comment, Converter<T, U> converter) {
        return AbstractTable.createField(name, type, this, comment, converter, null, null);
    }

    protected final <T, U> TableField<R, U> createField(Name name, DataType<T> type, String comment, Binding<T, U> binding) {
        return AbstractTable.createField(name, type, this, comment, null, binding, null);
    }

    protected final <T, X, U> TableField<R, U> createField(Name name, DataType<T> type, String comment, Converter<X, U> converter, Binding<T, X> binding) {
        return AbstractTable.createField(name, type, this, comment, converter, binding, null);
    }

    protected final <TR extends Table<R>, T> TableField<R, T> createField0(Name name, DataType<T> type, TR table, String comment, Generator<R, TR, T> generator) {
        return AbstractTable.createField(name, type, table, comment, null, null, generator);
    }

    protected final <TR extends Table<R>, T, U> TableField<R, U> createField0(Name name, DataType<T> type, TR table, String comment, Converter<T, U> converter, Generator<R, TR, U> generator) {
        return AbstractTable.createField(name, type, table, comment, converter, null, generator);
    }

    protected final <TR extends Table<R>, T, U> TableField<R, U> createField0(Name name, DataType<T> type, TR table, String comment, Binding<T, U> binding, Generator<R, TR, U> generator) {
        return AbstractTable.createField(name, type, table, comment, null, binding, generator);
    }

    protected final <TR extends Table<R>, T, X, U> TableField<R, U> createField0(Name name, DataType<T> type, TR table, String comment, Converter<X, U> converter, Binding<T, X> binding, Generator<R, TR, U> generator) {
        return AbstractTable.createField(name, type, table, comment, converter, binding, generator);
    }

    @Override
    public final Condition eq(Table<R> arg2) {
        return new TableEq<R>(this, arg2);
    }

    @Override
    public final Condition equal(Table<R> arg2) {
        return this.eq(arg2);
    }

    @Override
    public final Condition ne(Table<R> arg2) {
        return new TableNe<R>(this, arg2);
    }

    @Override
    public final Condition notEqual(Table<R> arg2) {
        return this.ne(arg2);
    }

    @Override
    public Field<RowId> rowid() {
        return new QualifiedRowid(this);
    }

    @Override
    public final Table<R> useIndex(String ... indexes) {
        return new HintedTable(this, "use index", indexes);
    }

    @Override
    public final Table<R> useIndexForJoin(String ... indexes) {
        return new HintedTable(this, "use index for join", indexes);
    }

    @Override
    public final Table<R> useIndexForOrderBy(String ... indexes) {
        return new HintedTable(this, "use index for order by", indexes);
    }

    @Override
    public final Table<R> useIndexForGroupBy(String ... indexes) {
        return new HintedTable(this, "use index for group by", indexes);
    }

    @Override
    public final Table<R> ignoreIndex(String ... indexes) {
        return new HintedTable(this, "ignore index", indexes);
    }

    @Override
    public final Table<R> ignoreIndexForJoin(String ... indexes) {
        return new HintedTable(this, "ignore index for join", indexes);
    }

    @Override
    public final Table<R> ignoreIndexForOrderBy(String ... indexes) {
        return new HintedTable(this, "ignore index for order by", indexes);
    }

    @Override
    public final Table<R> ignoreIndexForGroupBy(String ... indexes) {
        return new HintedTable(this, "ignore index for group by", indexes);
    }

    @Override
    public final Table<R> forceIndex(String ... indexes) {
        return new HintedTable(this, "force index", indexes);
    }

    @Override
    public final Table<R> forceIndexForJoin(String ... indexes) {
        return new HintedTable(this, "force index for join", indexes);
    }

    @Override
    public final Table<R> forceIndexForOrderBy(String ... indexes) {
        return new HintedTable(this, "force index for order by", indexes);
    }

    @Override
    public final Table<R> forceIndexForGroupBy(String ... indexes) {
        return new HintedTable(this, "force index for group by", indexes);
    }

    @Override
    public Table<R> as(Table<?> otherTable) {
        return this.as(otherTable.getUnqualifiedName());
    }

    @Override
    public final Table<R> as(Table<?> otherTable, Field<?> ... otherFields) {
        return this.as(otherTable.getUnqualifiedName(), Tools.map(otherFields, Named::getUnqualifiedName, Name[]::new));
    }

    @Override
    public final Table<R> as(Table<?> otherTable, Collection<? extends Field<?>> otherFields) {
        return this.as(otherTable.getUnqualifiedName(), Tools.map(otherFields, Named::getUnqualifiedName));
    }

    @Override
    public final Table<R> as(Table<?> otherTable, Function<? super Field<?>, ? extends Field<?>> aliasFunction) {
        return this.as(otherTable.getUnqualifiedName(), (? super Field<?> f) -> ((Field)aliasFunction.apply((Field<?>)f)).getUnqualifiedName());
    }

    @Override
    public final Table<R> as(Table<?> otherTable, BiFunction<? super Field<?>, ? super Integer, ? extends Field<?>> aliasFunction) {
        return this.as(otherTable.getUnqualifiedName(), (? super Field<?> f, ? super Integer i) -> ((Field)aliasFunction.apply((Field<?>)f, (Integer)i)).getUnqualifiedName());
    }

    @Override
    public final Table<Record> withOrdinality() {
        return new WithOrdinalityTable<Record>(this);
    }

    @Override
    @Pro
    public final Table<R> with(String hint) {
        return this.with(DSL.sql(hint));
    }

    @Override
    public final Table<R> with(String hint, Object ... binds) {
        return this.with(DSL.sql(hint, binds));
    }

    @Override
    public final Table<R> with(String hint, QueryPart ... parts) {
        return this.with(DSL.sql(hint, parts));
    }

    @Override
    public final Table<R> with(SQL hint) {
        return new WithTable(this, hint);
    }

    @Override
    @Pro
    public final Table<R> at(String link) {
        return this.at(DSL.link(link));
    }

    @Override
    @Pro
    public final Table<R> at(Name link) {
        return this.at(DSL.link(link));
    }

    @Override
    @Pro
    public final Table<R> at(Link link) {
        return new LinkedTable(this, link);
    }

    @Override
    @Pro
    public final PivotForStep pivot(Field<?> aggregateFunction) {
        return new Pivot(this, aggregateFunction);
    }

    @Override
    @Pro
    public final PivotForStep pivot(Field<?> ... aggregateFunctions) {
        return new Pivot(this, aggregateFunctions);
    }

    @Override
    @Pro
    public final PivotForStep pivot(Collection<? extends Field<?>> aggregateFunctions) {
        return this.pivot(aggregateFunctions.toArray(Tools.EMPTY_FIELD));
    }

    @Override
    @Pro
    public final Table<R> for_(PeriodSpecification periodSpecification) {
        return new PeriodTable(this, PeriodTable.Prefix.FOR, periodSpecification);
    }

    @Override
    public final Table<R> forPortionOf(PeriodSpecification periodSpecification) {
        return new PeriodTable(this, PeriodTable.Prefix.FOR_PORTION_OF, periodSpecification);
    }

    @Override
    @Pro
    public final VersionsBetweenAndStep<R, Number> versionsBetweenScn(Number scn) {
        return this.versionsBetweenScn(DSL.val(scn));
    }

    @Override
    @Pro
    public final VersionsBetweenAndStep<R, Number> versionsBetweenScn(Field<? extends Number> scn) {
        return new FlashbackTable(this, null, scn, FlashbackTable.FlashbackType.SCN);
    }

    @Override
    @Pro
    public final VersionsBetweenAndStep<R, Number> versionsBetweenScnMinvalue() {
        return new FlashbackTable(this, null, null, FlashbackTable.FlashbackType.SCN);
    }

    @Override
    @Pro
    public final VersionsBetweenAndStep<R, Timestamp> versionsBetweenTimestamp(Timestamp timestamp) {
        return this.versionsBetweenTimestamp(DSL.val(timestamp));
    }

    @Override
    @Pro
    public final VersionsBetweenAndStep<R, Timestamp> versionsBetweenTimestamp(Field<Timestamp> timestamp) {
        return new FlashbackTable(this, null, timestamp, FlashbackTable.FlashbackType.TIMESTAMP);
    }

    @Override
    @Pro
    public final VersionsBetweenAndStep<R, Timestamp> versionsBetweenTimestampMinvalue() {
        return new FlashbackTable(this, null, null, FlashbackTable.FlashbackType.TIMESTAMP);
    }

    @Override
    @Pro
    public final Table<R> asOfScn(Number scn) {
        return this.asOfScn(DSL.val(scn));
    }

    @Override
    @Pro
    public final Table<R> asOfScn(Field<? extends Number> scn) {
        return new FlashbackTable(this, scn, null, FlashbackTable.FlashbackType.SCN);
    }

    @Override
    @Pro
    public final Table<R> asOfTimestamp(Timestamp timestamp) {
        return this.asOfTimestamp(DSL.val(timestamp));
    }

    @Override
    @Pro
    public final Table<R> asOfTimestamp(Field<Timestamp> timestamp) {
        return new FlashbackTable(this, timestamp, null, FlashbackTable.FlashbackType.TIMESTAMP);
    }

    @Override
    public final DivideByOnStep divideBy(Table<?> divisor) {
        return new DivideBy(this, divisor);
    }

    @Override
    public final TableOnStep<R> leftSemiJoin(TableLike<?> table) {
        return this.join(table, JoinType.LEFT_SEMI_JOIN);
    }

    @Override
    public final TableOnStep<R> leftAntiJoin(TableLike<?> table) {
        return this.join(table, JoinType.LEFT_ANTI_JOIN);
    }

    @Override
    public Table<R> where(Condition condition) {
        return new InlineDerivedTable(this, condition);
    }

    @Override
    public Table<R> where(Condition ... conditions) {
        return this.where(DSL.and(conditions));
    }

    @Override
    public Table<R> where(Collection<? extends Condition> conditions) {
        return this.where(DSL.and(conditions));
    }

    @Override
    public Table<R> where(Field<Boolean> field2) {
        return this.where(DSL.condition(field2));
    }

    @Override
    public Table<R> where(SQL sql) {
        return this.where(DSL.condition(sql));
    }

    @Override
    public Table<R> where(String sql) {
        return this.where(DSL.condition(sql));
    }

    @Override
    public Table<R> where(String sql, Object ... bindings) {
        return this.where(DSL.condition(sql, bindings));
    }

    @Override
    public Table<R> where(String sql, QueryPart ... parts) {
        return this.where(DSL.condition(sql, parts));
    }

    @Override
    public Table<R> whereExists(Select<?> select) {
        return this.where(DSL.exists(select));
    }

    @Override
    public Table<R> whereNotExists(Select<?> select) {
        return this.where(DSL.notExists(select));
    }

    @Override
    public TableOptionalOnStep<Record> join(TableLike<?> table, JoinType type) {
        switch (type) {
            case CROSS_APPLY: {
                return new CrossApply(this, table);
            }
            case CROSS_JOIN: {
                return new CrossJoin(this, table);
            }
            case FULL_OUTER_JOIN: {
                return new FullJoin(this, table);
            }
            case JOIN: {
                return new Join(this, table);
            }
            case LEFT_ANTI_JOIN: {
                return new LeftAntiJoin(this, table);
            }
            case LEFT_OUTER_JOIN: {
                return new LeftJoin(this, table);
            }
            case LEFT_SEMI_JOIN: {
                return new LeftSemiJoin(this, table);
            }
            case NATURAL_FULL_OUTER_JOIN: {
                return new NaturalFullJoin(this, table);
            }
            case NATURAL_JOIN: {
                return new NaturalJoin(this, table);
            }
            case NATURAL_LEFT_OUTER_JOIN: {
                return new NaturalLeftJoin(this, table);
            }
            case NATURAL_RIGHT_OUTER_JOIN: {
                return new NaturalRightJoin(this, table);
            }
            case OUTER_APPLY: {
                return new OuterApply(this, table);
            }
            case RIGHT_OUTER_JOIN: {
                return new RightJoin(this, table);
            }
            case STRAIGHT_JOIN: {
                return new StraightJoin(this, table);
            }
        }
        throw new IllegalArgumentException("Unsupported join type: " + String.valueOf((Object)type));
    }

    @Override
    public final TableOnStep<Record> join(TableLike<?> table) {
        return this.innerJoin(table);
    }

    @Override
    public final TableOnStep<Record> join(SQL sql) {
        return this.innerJoin(sql);
    }

    @Override
    public final TableOnStep<Record> join(String sql) {
        return this.innerJoin(sql);
    }

    @Override
    public final TableOnStep<Record> join(String sql, Object ... bindings) {
        return this.innerJoin(sql, bindings);
    }

    @Override
    public final TableOnStep<Record> join(String sql, QueryPart ... parts) {
        return this.innerJoin(sql, parts);
    }

    @Override
    public final TableOnStep<Record> join(Name name) {
        return this.innerJoin(DSL.table(name));
    }

    @Override
    public final TableOnStep<Record> innerJoin(TableLike<?> table) {
        return this.join(table, JoinType.JOIN);
    }

    @Override
    public final TableOnStep<Record> innerJoin(SQL sql) {
        return this.innerJoin(DSL.table(sql));
    }

    @Override
    public final TableOnStep<Record> innerJoin(String sql) {
        return this.innerJoin(DSL.table(sql));
    }

    @Override
    public final TableOnStep<Record> innerJoin(String sql, Object ... bindings) {
        return this.innerJoin(DSL.table(sql, bindings));
    }

    @Override
    public final TableOnStep<Record> innerJoin(String sql, QueryPart ... parts) {
        return this.innerJoin(DSL.table(sql, parts));
    }

    @Override
    public final TableOnStep<Record> innerJoin(Name name) {
        return this.innerJoin(DSL.table(name));
    }

    @Override
    @Pro
    public TableOuterJoinStep<Record> partitionBy(Field<?> ... fields) {
        return this.partitionBy(Tools.list(fields));
    }

    @Override
    @Pro
    public TableOuterJoinStep<Record> partitionBy(Collection<? extends Field<?>> fields) {
        return new PartitionJoinTable(this, fields);
    }

    @Override
    public final TablePartitionByStep<Record> leftJoin(TableLike<?> table) {
        return this.leftOuterJoin(table);
    }

    @Override
    public final TablePartitionByStep<Record> leftJoin(SQL sql) {
        return this.leftOuterJoin(sql);
    }

    @Override
    public final TablePartitionByStep<Record> leftJoin(String sql) {
        return this.leftOuterJoin(sql);
    }

    @Override
    public final TablePartitionByStep<Record> leftJoin(String sql, Object ... bindings) {
        return this.leftOuterJoin(sql, bindings);
    }

    @Override
    public final TablePartitionByStep<Record> leftJoin(String sql, QueryPart ... parts) {
        return this.leftOuterJoin(sql, parts);
    }

    @Override
    public final TablePartitionByStep<Record> leftJoin(Name name) {
        return this.leftOuterJoin(DSL.table(name));
    }

    @Override
    public final TablePartitionByStep<Record> leftOuterJoin(TableLike<?> table) {
        return (TablePartitionByStep)((Object)this.join(table, JoinType.LEFT_OUTER_JOIN));
    }

    @Override
    public final TablePartitionByStep<Record> leftOuterJoin(SQL sql) {
        return this.leftOuterJoin(DSL.table(sql));
    }

    @Override
    public final TablePartitionByStep<Record> leftOuterJoin(String sql) {
        return this.leftOuterJoin(DSL.table(sql));
    }

    @Override
    public final TablePartitionByStep<Record> leftOuterJoin(String sql, Object ... bindings) {
        return this.leftOuterJoin(DSL.table(sql, bindings));
    }

    @Override
    public final TablePartitionByStep<Record> leftOuterJoin(String sql, QueryPart ... parts) {
        return this.leftOuterJoin(DSL.table(sql, parts));
    }

    @Override
    public final TablePartitionByStep<Record> leftOuterJoin(Name name) {
        return this.leftOuterJoin(DSL.table(name));
    }

    @Override
    public final TablePartitionByStep<Record> rightJoin(TableLike<?> table) {
        return this.rightOuterJoin(table);
    }

    @Override
    public final TablePartitionByStep<Record> rightJoin(SQL sql) {
        return this.rightOuterJoin(sql);
    }

    @Override
    public final TablePartitionByStep<Record> rightJoin(String sql) {
        return this.rightOuterJoin(sql);
    }

    @Override
    public final TablePartitionByStep<Record> rightJoin(String sql, Object ... bindings) {
        return this.rightOuterJoin(sql, bindings);
    }

    @Override
    public final TablePartitionByStep<Record> rightJoin(String sql, QueryPart ... parts) {
        return this.rightOuterJoin(sql, parts);
    }

    @Override
    public final TablePartitionByStep<Record> rightJoin(Name name) {
        return this.rightOuterJoin(DSL.table(name));
    }

    @Override
    public final TablePartitionByStep<Record> rightOuterJoin(TableLike<?> table) {
        return (TablePartitionByStep)((Object)this.join(table, JoinType.RIGHT_OUTER_JOIN));
    }

    @Override
    public final TablePartitionByStep<Record> rightOuterJoin(SQL sql) {
        return this.rightOuterJoin(DSL.table(sql));
    }

    @Override
    public final TablePartitionByStep<Record> rightOuterJoin(String sql) {
        return this.rightOuterJoin(DSL.table(sql));
    }

    @Override
    public final TablePartitionByStep<Record> rightOuterJoin(String sql, Object ... bindings) {
        return this.rightOuterJoin(DSL.table(sql, bindings));
    }

    @Override
    public final TablePartitionByStep<Record> rightOuterJoin(String sql, QueryPart ... parts) {
        return this.rightOuterJoin(DSL.table(sql, parts));
    }

    @Override
    public final TablePartitionByStep<Record> rightOuterJoin(Name name) {
        return this.rightOuterJoin(DSL.table(name));
    }

    @Override
    public final TablePartitionByStep<Record> fullOuterJoin(TableLike<?> table) {
        return (TablePartitionByStep)((Object)this.join(table, JoinType.FULL_OUTER_JOIN));
    }

    @Override
    public final TablePartitionByStep<Record> fullOuterJoin(SQL sql) {
        return this.fullOuterJoin(DSL.table(sql));
    }

    @Override
    public final TablePartitionByStep<Record> fullOuterJoin(String sql) {
        return this.fullOuterJoin(DSL.table(sql));
    }

    @Override
    public final TablePartitionByStep<Record> fullOuterJoin(String sql, Object ... bindings) {
        return this.fullOuterJoin(DSL.table(sql, bindings));
    }

    @Override
    public final TablePartitionByStep<Record> fullOuterJoin(String sql, QueryPart ... parts) {
        return this.fullOuterJoin(DSL.table(sql, parts));
    }

    @Override
    public final TablePartitionByStep<Record> fullOuterJoin(Name name) {
        return this.fullOuterJoin(DSL.table(name));
    }

    @Override
    public final TablePartitionByStep<Record> fullJoin(TableLike<?> table) {
        return this.fullOuterJoin(table);
    }

    @Override
    public final TablePartitionByStep<Record> fullJoin(SQL sql) {
        return this.fullOuterJoin(sql);
    }

    @Override
    public final TablePartitionByStep<Record> fullJoin(String sql) {
        return this.fullOuterJoin(sql);
    }

    @Override
    public final TablePartitionByStep<Record> fullJoin(String sql, Object ... bindings) {
        return this.fullOuterJoin(sql, bindings);
    }

    @Override
    public final TablePartitionByStep<Record> fullJoin(String sql, QueryPart ... parts) {
        return this.fullOuterJoin(sql, parts);
    }

    @Override
    public final TablePartitionByStep<Record> fullJoin(Name name) {
        return this.fullOuterJoin(name);
    }

    @Override
    public final Table<Record> crossJoin(TableLike<?> table) {
        return this.join(table, JoinType.CROSS_JOIN);
    }

    @Override
    public final Table<Record> crossJoin(SQL sql) {
        return this.crossJoin(DSL.table(sql));
    }

    @Override
    public final Table<Record> crossJoin(String sql) {
        return this.crossJoin(DSL.table(sql));
    }

    @Override
    public final Table<Record> crossJoin(String sql, Object ... bindings) {
        return this.crossJoin(DSL.table(sql, bindings));
    }

    @Override
    public final Table<Record> crossJoin(String sql, QueryPart ... parts) {
        return this.crossJoin(DSL.table(sql, parts));
    }

    @Override
    public final Table<Record> crossJoin(Name name) {
        return this.crossJoin(DSL.table(name));
    }

    @Override
    public final Table<Record> naturalJoin(TableLike<?> table) {
        return this.join(table, JoinType.NATURAL_JOIN);
    }

    @Override
    public final Table<Record> naturalJoin(SQL sql) {
        return this.naturalJoin(DSL.table(sql));
    }

    @Override
    public final Table<Record> naturalJoin(String sql) {
        return this.naturalJoin(DSL.table(sql));
    }

    @Override
    public final Table<Record> naturalJoin(String sql, Object ... bindings) {
        return this.naturalJoin(DSL.table(sql, bindings));
    }

    @Override
    public final Table<Record> naturalJoin(String sql, QueryPart ... parts) {
        return this.naturalJoin(DSL.table(sql, parts));
    }

    @Override
    public final Table<Record> naturalJoin(Name name) {
        return this.naturalJoin(DSL.table(name));
    }

    @Override
    public final Table<Record> naturalLeftOuterJoin(TableLike<?> table) {
        return this.join(table, JoinType.NATURAL_LEFT_OUTER_JOIN);
    }

    @Override
    public final Table<Record> naturalLeftOuterJoin(SQL sql) {
        return this.naturalLeftOuterJoin(DSL.table(sql));
    }

    @Override
    public final Table<Record> naturalLeftOuterJoin(String sql) {
        return this.naturalLeftOuterJoin(DSL.table(sql));
    }

    @Override
    public final Table<Record> naturalLeftOuterJoin(String sql, Object ... bindings) {
        return this.naturalLeftOuterJoin(DSL.table(sql, bindings));
    }

    @Override
    public final Table<Record> naturalLeftOuterJoin(String sql, QueryPart ... parts) {
        return this.naturalLeftOuterJoin(DSL.table(sql, parts));
    }

    @Override
    public final Table<Record> naturalLeftOuterJoin(Name name) {
        return this.naturalLeftOuterJoin(DSL.table(name));
    }

    @Override
    public final Table<Record> naturalRightOuterJoin(TableLike<?> table) {
        return this.join(table, JoinType.NATURAL_RIGHT_OUTER_JOIN);
    }

    @Override
    public final Table<Record> naturalRightOuterJoin(SQL sql) {
        return this.naturalRightOuterJoin(DSL.table(sql));
    }

    @Override
    public final Table<Record> naturalRightOuterJoin(String sql) {
        return this.naturalRightOuterJoin(DSL.table(sql));
    }

    @Override
    public final Table<Record> naturalRightOuterJoin(String sql, Object ... bindings) {
        return this.naturalRightOuterJoin(DSL.table(sql, bindings));
    }

    @Override
    public final Table<Record> naturalRightOuterJoin(String sql, QueryPart ... parts) {
        return this.naturalRightOuterJoin(DSL.table(sql, parts));
    }

    @Override
    public final Table<Record> naturalRightOuterJoin(Name name) {
        return this.naturalRightOuterJoin(DSL.table(name));
    }

    @Override
    public final Table<Record> naturalFullOuterJoin(TableLike<?> table) {
        return this.join(table, JoinType.NATURAL_FULL_OUTER_JOIN);
    }

    @Override
    public final Table<Record> naturalFullOuterJoin(SQL sql) {
        return this.naturalFullOuterJoin(DSL.table(sql));
    }

    @Override
    public final Table<Record> naturalFullOuterJoin(String sql) {
        return this.naturalFullOuterJoin(DSL.table(sql));
    }

    @Override
    public final Table<Record> naturalFullOuterJoin(String sql, Object ... bindings) {
        return this.naturalFullOuterJoin(DSL.table(sql, bindings));
    }

    @Override
    public final Table<Record> naturalFullOuterJoin(String sql, QueryPart ... parts) {
        return this.naturalFullOuterJoin(DSL.table(sql, parts));
    }

    @Override
    public final Table<Record> naturalFullOuterJoin(Name name) {
        return this.naturalFullOuterJoin(DSL.table(name));
    }

    @Override
    public final Table<Record> crossApply(TableLike<?> table) {
        return this.join(table, JoinType.CROSS_APPLY);
    }

    @Override
    public final Table<Record> crossApply(SQL sql) {
        return this.crossApply(DSL.table(sql));
    }

    @Override
    public final Table<Record> crossApply(String sql) {
        return this.crossApply(DSL.table(sql));
    }

    @Override
    public final Table<Record> crossApply(String sql, Object ... bindings) {
        return this.crossApply(DSL.table(sql, bindings));
    }

    @Override
    public final Table<Record> crossApply(String sql, QueryPart ... parts) {
        return this.crossApply(DSL.table(sql, parts));
    }

    @Override
    public final Table<Record> crossApply(Name name) {
        return this.crossApply(DSL.table(name));
    }

    @Override
    public final Table<Record> outerApply(TableLike<?> table) {
        return this.join(table, JoinType.OUTER_APPLY);
    }

    @Override
    public final Table<Record> outerApply(SQL sql) {
        return this.outerApply(DSL.table(sql));
    }

    @Override
    public final Table<Record> outerApply(String sql) {
        return this.outerApply(DSL.table(sql));
    }

    @Override
    public final Table<Record> outerApply(String sql, Object ... bindings) {
        return this.outerApply(DSL.table(sql, bindings));
    }

    @Override
    public final Table<Record> outerApply(String sql, QueryPart ... parts) {
        return this.outerApply(DSL.table(sql, parts));
    }

    @Override
    public final Table<Record> outerApply(Name name) {
        return this.outerApply(DSL.table(name));
    }

    public final TableOptionalOnStep<Record> straightJoin(TableLike<?> table) {
        return this.join(table, JoinType.STRAIGHT_JOIN);
    }

    public final TableOptionalOnStep<Record> straightJoin(SQL sql) {
        return this.straightJoin(DSL.table(sql));
    }

    public final TableOptionalOnStep<Record> straightJoin(String sql) {
        return this.straightJoin(DSL.table(sql));
    }

    public final TableOptionalOnStep<Record> straightJoin(String sql, Object ... bindings) {
        return this.straightJoin(DSL.table(sql, bindings));
    }

    public final TableOptionalOnStep<Record> straightJoin(String sql, QueryPart ... parts) {
        return this.straightJoin(DSL.table(sql, parts));
    }

    public final TableOptionalOnStep<Record> straightJoin(Name name) {
        return this.straightJoin(DSL.table(name));
    }

    @Override
    public final Schema $schema() {
        return this.getSchema();
    }
}

