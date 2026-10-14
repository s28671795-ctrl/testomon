/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.jooq.Clause;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Constraint;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.FieldOrRow;
import org.jooq.FieldOrRowOrSelect;
import org.jooq.GeneratorStatementType;
import org.jooq.Identity;
import org.jooq.InsertQuery;
import org.jooq.MergeMatchedStep;
import org.jooq.MergeOnConditionStep;
import org.jooq.Name;
import org.jooq.Named;
import org.jooq.Operator;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Replacer;
import org.jooq.Row;
import org.jooq.SQLDialect;
import org.jooq.Scope;
import org.jooq.Select;
import org.jooq.SelectConditionStep;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableLike;
import org.jooq.Traverser;
import org.jooq.UniqueKey;
import org.jooq.conf.ParamType;
import org.jooq.conf.WriteIfReadonly;
import org.jooq.impl.AbstractStoreQuery;
import org.jooq.impl.AliasedSelect;
import org.jooq.impl.ConditionProviderImpl;
import org.jooq.impl.DSL;
import org.jooq.impl.Excluded;
import org.jooq.impl.FieldMapForUpdate;
import org.jooq.impl.FieldMapsForInsert;
import org.jooq.impl.FieldsImpl;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.QueryPartListView;
import org.jooq.impl.Tools;
import org.jooq.impl.WithImpl;
import org.jooq.tools.StringUtils;

final class InsertQueryImpl<R extends Record>
extends AbstractStoreQuery<R, Field<?>, Field<?>>
implements InsertQuery<R>,
QOM.Insert<R> {
    static final Clause[] CLAUSES = new Clause[]{Clause.INSERT};
    static final Set<SQLDialect> SUPPORT_INSERT_IGNORE = SQLDialect.supportedBy(SQLDialect.AURORA_MYSQL, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL);
    static final Set<SQLDialect> SUPPORTS_OPTIONAL_DO_UPDATE_CONFLICT_TARGETS = SQLDialect.supportedBy(SQLDialect.SQLITE_3_38);
    static final Set<SQLDialect> NO_SUPPORT_DERIVED_COLUMN_LIST_IN_MERGE_USING = SQLDialect.supportedBy(SQLDialect.DERBY, SQLDialect.H2);
    static final Set<SQLDialect> NO_SUPPORT_SUBQUERY_IN_MERGE_USING = SQLDialect.supportedBy(SQLDialect.DERBY);
    static final Set<SQLDialect> REQUIRE_NEW_MYSQL_EXCLUDED_EMULATION = SQLDialect.supportedBy(SQLDialect.MYSQL_8_0_20);
    final FieldMapsForInsert insertMaps;
    Select<?> select;
    boolean defaultValues;
    boolean onDuplicateKeyUpdate;
    boolean onDuplicateKeyIgnore;
    Constraint onConstraint;
    UniqueKey<R> onConstraintUniqueKey;
    QueryPartList<Field<?>> onConflict;
    final ConditionProviderImpl onConflictWhere;
    final FieldMapForUpdate updateMap;
    final ConditionProviderImpl updateWhere;

    InsertQueryImpl(Configuration configuration, WithImpl with, Table<R> into) {
        super(configuration, with, into);
        this.insertMaps = new FieldMapsForInsert(into);
        this.onConflictWhere = new ConditionProviderImpl();
        this.updateMap = new FieldMapForUpdate(into, FieldMapForUpdate.SetClause.INSERT, Clause.INSERT_ON_DUPLICATE_KEY_UPDATE_ASSIGNMENT);
        this.updateWhere = new ConditionProviderImpl();
    }

    @Override
    public final void newRecord() {
        this.insertMaps.newRecord();
    }

    @Override
    protected final Map<Field<?>, Field<?>> getValues() {
        return this.insertMaps.lastMap();
    }

    final FieldMapsForInsert getInsertMaps() {
        return this.insertMaps;
    }

    final Select<?> getSelect() {
        return this.select;
    }

    @Override
    public final void addRecord(R record) {
        this.newRecord();
        this.setRecord(record);
    }

    @Override
    public final void onConflict(Field<?> ... fields) {
        this.onConflict(Arrays.asList(fields));
    }

    @Override
    public final void onConflict(Collection<? extends Field<?>> fields) {
        this.onConflict = new QueryPartList((Iterable<Field<?>>)fields).qualify(false);
    }

    @Override
    public final void onConflictWhere(Condition conditions) {
        this.onConflictWhere.addConditions(conditions);
    }

    @Override
    public final void onConflictOnConstraint(Constraint constraint) {
        this.onConflictOnConstraint0(constraint);
    }

    @Override
    public void onConflictOnConstraint(UniqueKey<R> constraint) {
        if (StringUtils.isEmpty(constraint.getName())) {
            throw new IllegalArgumentException("UniqueKey's name is not specified");
        }
        this.onConstraintUniqueKey = constraint;
        this.onConflictOnConstraint0(DSL.constraint(DSL.name(constraint.getName())));
    }

    @Override
    public final void onConflictOnConstraint(Name constraint) {
        this.onConflictOnConstraint0(DSL.constraint(constraint));
    }

    private final void onConflictOnConstraint0(Constraint constraint) {
        this.onConstraint = constraint;
        if (this.onConstraintUniqueKey == null) {
            this.onConstraintUniqueKey = Tools.findAny(this.table().getKeys(), key -> constraint.getName().equals(key.getName()));
        }
    }

    @Override
    public final void onDuplicateKeyUpdate(boolean flag) {
        this.onDuplicateKeyUpdate = flag;
        if (flag) {
            this.onDuplicateKeyIgnore = false;
        }
    }

    @Override
    public final void onDuplicateKeyIgnore(boolean flag) {
        this.onDuplicateKeyIgnore = flag;
        if (flag) {
            this.onDuplicateKeyUpdate = false;
            this.updateMap.clear();
            this.updateWhere.setWhere(null);
        }
    }

    @Override
    public final <T> void addValueForUpdate(Field<T> field2, T value) {
        this.updateMap.put(field2, Tools.field(value, field2));
    }

    @Override
    public final <T> void addValueForUpdate(Field<T> field2, Field<T> value) {
        this.updateMap.put(field2, Tools.field(value, field2));
    }

    @Override
    public final void addValuesForUpdate(Map<?, ?> map) {
        this.updateMap.set(map);
    }

    @Override
    public final void addConditions(Condition conditions) {
        this.updateWhere.addConditions(conditions);
    }

    @Override
    public final void addConditions(Condition ... conditions) {
        this.updateWhere.addConditions(conditions);
    }

    @Override
    public final void addConditions(Collection<? extends Condition> conditions) {
        this.updateWhere.addConditions(conditions);
    }

    @Override
    public final void addConditions(Operator operator, Condition conditions) {
        this.updateWhere.addConditions(operator, conditions);
    }

    @Override
    public final void addConditions(Operator operator, Condition ... conditions) {
        this.updateWhere.addConditions(operator, conditions);
    }

    @Override
    public final void addConditions(Operator operator, Collection<? extends Condition> conditions) {
        this.updateWhere.addConditions(operator, conditions);
    }

    @Override
    public final void setDefaultValues() {
        this.defaultValues = true;
        this.select = null;
    }

    private final boolean defaultValues(Configuration c) {
        if (this.defaultValues || !this.insertMaps.values.isEmpty() && !this.insertMaps.checkAnyWritables(c)) {
            return true;
        }
        return this.defaultValues;
    }

    @Override
    public final void setSelect(Field<?>[] f, Select<?> s2) {
        this.setSelect(Arrays.asList(f), s2);
    }

    @Override
    public final void setSelect(Collection<? extends Field<?>> f, Select<?> s2) {
        this.defaultValues = false;
        this.insertMaps.clear();
        this.insertMaps.addFields(f);
        this.select = s2;
    }

    @Override
    public final void addValues(Map<?, ?> map) {
        this.insertMaps.set(map);
    }

    @Override
    final void accept0(Context<?> ctx) {
        this.insertMaps.checkReadonly(ctx);
        this.updateMap.checkReadonly(ctx);
        if (this.onDuplicateKeyUpdate) {
            switch (ctx.family()) {
                case COCKROACHDB: 
                case AURORA_POSTGRES: 
                case POSTGRES: 
                case SQLITE: 
                case YUGABYTEDB: {
                    if (ctx.dialect().supports(SQLDialect.POSTGRES_15) && this.onConstraint == null && this.onConflict == null && this.returning.isEmpty() && this.table().getKeys().size() > 1) {
                        this.acceptMerge(ctx);
                        break;
                    }
                    ctx.data(Tools.BooleanDataKey.DATA_MANDATORY_WHERE_CLAUSE, ctx.family() == SQLDialect.SQLITE, c -> this.toSQLInsert((Context<?>)c, false));
                    ctx.formatSeparator().start(Clause.INSERT_ON_DUPLICATE_KEY_UPDATE).visit(Keywords.K_ON_CONFLICT).sql(' ');
                    if (this.onConstraint != null && ctx.family() != SQLDialect.COCKROACHDB) {
                        ctx.data(Tools.BooleanDataKey.DATA_CONSTRAINT_REFERENCE, true);
                        ctx.visit(Keywords.K_ON_CONSTRAINT).sql(' ').visit(this.onConstraint);
                        ctx.data().remove(Tools.BooleanDataKey.DATA_CONSTRAINT_REFERENCE);
                    } else if (this.onConflict != null && this.onConflict.size() > 0) {
                        ctx.sql('(').visit(this.onConflict).sql(')');
                    } else if (this.onConstraint != null && ctx.family() == SQLDialect.COCKROACHDB) {
                        if (this.onConstraintUniqueKey != null) {
                            ctx.sql('(').qualify(false, c -> c.visit(new FieldsImpl(this.onConstraintUniqueKey.getFields()))).sql(')');
                        } else {
                            ctx.sql("[unknown unique key]");
                        }
                    } else if (!SUPPORTS_OPTIONAL_DO_UPDATE_CONFLICT_TARGETS.contains((Object)ctx.dialect()) || this.onConflictWhere.hasWhere()) {
                        if (this.table().getPrimaryKey() == null) {
                            ctx.sql("[unknown primary key]");
                        } else {
                            ctx.sql('(').qualify(false, c -> c.visit(new FieldsImpl(this.table().getPrimaryKey().getFields()))).sql(')');
                        }
                    }
                    this.acceptOnConflictWhere(ctx);
                    ctx.formatSeparator().visit(Keywords.K_DO_UPDATE).formatSeparator().visit(Keywords.K_SET).formatIndentStart().formatSeparator().visit(this.updateMapComputedOnClientStored(ctx)).formatIndentEnd();
                    if (this.updateWhere.hasWhere()) {
                        ctx.formatSeparator().visit(Keywords.K_WHERE).sql(' ').visit(this.updateWhere);
                    }
                    ctx.end(Clause.INSERT_ON_DUPLICATE_KEY_UPDATE);
                    break;
                }
                case DB2: 
                case EXASOL: 
                case HANA: 
                case INFORMIX: 
                case ORACLE: 
                case SNOWFLAKE: 
                case SQLSERVER: 
                case SYBASE: 
                case TERADATA: 
                case DERBY: 
                case FIREBIRD: 
                case H2: 
                case HSQLDB: {
                    this.acceptMerge(ctx);
                    break;
                }
                default: {
                    boolean oldQualify = ctx.qualify();
                    boolean newQualify = ctx.family() != SQLDialect.H2 && oldQualify;
                    FieldMapForUpdate um = this.updateMapComputedOnClientStored(ctx);
                    boolean requireNewMySQLExcludedEmulation = REQUIRE_NEW_MYSQL_EXCLUDED_EMULATION.contains((Object)ctx.dialect()) && Tools.anyMatch(um.values(), v -> v instanceof Excluded);
                    Set<Field<?>> keys2 = this.toSQLInsert(ctx, requireNewMySQLExcludedEmulation);
                    if (requireNewMySQLExcludedEmulation && this.select == null && !Tools.anyComputedOnClientStoredFields(ctx.configuration(), this.table)) {
                        ctx.formatSeparator().visit(Keywords.K_AS).sql(' ').visit(DSL.name("t"));
                    }
                    ctx.formatSeparator().start(Clause.INSERT_ON_DUPLICATE_KEY_UPDATE).visit(Keywords.K_ON_DUPLICATE_KEY_UPDATE).formatIndentStart().formatSeparator().qualify(newQualify);
                    if (this.updateWhere.hasWhere()) {
                        ctx.data(Tools.SimpleDataKey.DATA_ON_DUPLICATE_KEY_WHERE, this.updateWhere.getWhere());
                    }
                    if (requireNewMySQLExcludedEmulation) {
                        um.replaceAll((k, v) -> {
                            if (v instanceof Excluded) {
                                Excluded e = (Excluded)v;
                                return keys2.contains(e.$field()) ? v : Tools.qualify(this.table(), e.$field());
                            }
                            return v;
                        });
                    }
                    ctx.visit(um);
                    if (this.updateWhere.hasWhere()) {
                        ctx.data().remove(Tools.SimpleDataKey.DATA_ON_DUPLICATE_KEY_WHERE);
                    }
                    ctx.qualify(oldQualify).formatIndentEnd().end(Clause.INSERT_ON_DUPLICATE_KEY_UPDATE);
                    break;
                }
            }
        } else if (this.onDuplicateKeyIgnore) {
            switch (ctx.family()) {
                case EXASOL: 
                case HANA: 
                case INFORMIX: 
                case FIREBIRD: 
                case ACCESS: 
                case ASE: 
                case BIGQUERY: 
                case INGRES: 
                case MEMSQL: 
                case REDSHIFT: 
                case SQLDATAWAREHOUSE: 
                case VERTICA: 
                case IGNITE: {
                    this.acceptInsertSelect(ctx);
                    break;
                }
                case COCKROACHDB: 
                case AURORA_POSTGRES: 
                case POSTGRES: 
                case SQLITE: 
                case YUGABYTEDB: {
                    if (ctx.dialect().precedesStrictly(SQLDialect.POSTGRES_9_5)) {
                        this.acceptInsertSelect(ctx);
                        break;
                    }
                    ctx.data(Tools.BooleanDataKey.DATA_MANDATORY_WHERE_CLAUSE, ctx.family() == SQLDialect.SQLITE, c -> this.toSQLInsert((Context<?>)c, false));
                    ctx.formatSeparator().start(Clause.INSERT_ON_DUPLICATE_KEY_UPDATE).visit(Keywords.K_ON_CONFLICT);
                    if (this.onConstraint != null && ctx.family() != SQLDialect.COCKROACHDB) {
                        ctx.data(Tools.BooleanDataKey.DATA_CONSTRAINT_REFERENCE, true, c -> c.sql(' ').visit(Keywords.K_ON_CONSTRAINT).sql(' ').visit(this.onConstraint));
                    } else if (this.onConflict != null && this.onConflict.size() > 0) {
                        ctx.sql(" (").visit(this.onConflict).sql(')');
                        this.acceptOnConflictWhere(ctx);
                    } else if (this.onConstraint != null && ctx.family() == SQLDialect.COCKROACHDB) {
                        if (this.onConstraintUniqueKey != null) {
                            ctx.sql(" (").qualify(false, c -> c.visit(new FieldsImpl(this.onConstraintUniqueKey.getFields()))).sql(')');
                        } else {
                            ctx.sql(" [ unknown unique key ]");
                        }
                    }
                    ctx.formatSeparator().visit(Keywords.K_DO_NOTHING).end(Clause.INSERT_ON_DUPLICATE_KEY_UPDATE);
                    break;
                }
                case CUBRID: {
                    FieldMapForUpdate update = new FieldMapForUpdate(this.table(), FieldMapForUpdate.SetClause.INSERT, Clause.INSERT_ON_DUPLICATE_KEY_UPDATE_ASSIGNMENT);
                    Field<?> field2 = this.table().field(0);
                    update.put(field2, field2);
                    this.toSQLInsert(ctx, false);
                    ctx.formatSeparator().start(Clause.INSERT_ON_DUPLICATE_KEY_UPDATE).visit(Keywords.K_ON_DUPLICATE_KEY_UPDATE).sql(' ').visit(update).end(Clause.INSERT_ON_DUPLICATE_KEY_UPDATE);
                    break;
                }
                case DB2: 
                case ORACLE: 
                case SNOWFLAKE: 
                case SQLSERVER: 
                case SYBASE: 
                case TERADATA: 
                case H2: 
                case HSQLDB: {
                    this.acceptMerge(ctx);
                    break;
                }
                case DERBY: {
                    if (this.select != null) {
                        this.acceptInsertSelect(ctx);
                        break;
                    }
                    this.acceptMerge(ctx);
                    break;
                }
                default: {
                    this.toSQLInsert(ctx, false);
                    ctx.start(Clause.INSERT_ON_DUPLICATE_KEY_UPDATE).end(Clause.INSERT_ON_DUPLICATE_KEY_UPDATE);
                    break;
                }
            }
        } else {
            this.toSQLInsert(ctx, false);
            ctx.start(Clause.INSERT_ON_DUPLICATE_KEY_UPDATE).end(Clause.INSERT_ON_DUPLICATE_KEY_UPDATE);
        }
        ctx.start(Clause.INSERT_RETURNING);
        this.toSQLReturning(ctx);
        ctx.end(Clause.INSERT_RETURNING);
    }

    private final void acceptOnConflictWhere(Context<?> ctx) {
        if (this.onConflictWhere.hasWhere()) {
            ctx.paramType(ParamType.INLINED, c1 -> c1.qualify(false, c2 -> c2.formatSeparator().visit(Keywords.K_WHERE).sql(' ').visit(this.onConflictWhere.getWhere())));
        }
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }

    private final Set<Field<?>> toSQLInsert(Context<?> ctx, boolean requireNewMySQLExcludedEmulation) {
        ctx.start(Clause.INSERT_INSERT_INTO).visit(Keywords.K_INSERT).sql(' ');
        if (this.onDuplicateKeyIgnore && SUPPORT_INSERT_IGNORE.contains((Object)ctx.dialect())) {
            ctx.visit(Keywords.K_IGNORE).sql(' ');
        }
        ctx.visit(Keywords.K_INTO).sql(' ').declareTables(true, c -> c.visit(this.table((Context<?>)c)));
        Set<Field<?>> fields = this.insertMaps.toSQLReferenceKeys(ctx);
        ctx.end(Clause.INSERT_INSERT_INTO);
        this.toSQLOutput(ctx);
        if (this.select != null && !Boolean.TRUE.equals(ctx.data(Tools.BooleanDataKey.DATA_EMULATE_BULK_INSERT_RETURNING))) {
            Set<Field<?>> keysFlattened = this.insertMaps.keysFlattened(ctx, GeneratorStatementType.INSERT);
            if (keysFlattened.size() == 0) {
                ctx.data(Tools.BooleanDataKey.DATA_INSERT_SELECT_WITHOUT_INSERT_COLUMN_LIST, true);
            } else if (ctx.family() == SQLDialect.ORACLE && this.insertingDefaultIdentity(ctx)) {
                ctx.data(Tools.BooleanDataKey.DATA_INSERT_SELECT_WITHOUT_INSERT_COLUMN_LIST, true);
            } else if (ctx.family() == SQLDialect.TERADATA) {
                ctx.data(Tools.BooleanDataKey.DATA_INSERT_SELECT_WITHOUT_INSERT_COLUMN_LIST, true);
            }
            ctx.data(Tools.BooleanDataKey.DATA_INSERT_SELECT, true);
            Select<Record> s2 = this.select;
            if (ctx.settings().getReadonlyInsert() == WriteIfReadonly.IGNORE && fields.size() > 0 && fields.size() < s2.getSelect().size()) {
                s2 = DSL.select(Tools.map(fields, t2 -> Tools.unqualified(t2))).from((TableLike<?>)s2.asTable(DSL.table(DSL.name("t")), Tools.collect(Tools.flattenCollection(this.insertMaps.values.keySet(), true, true))));
            }
            if (Tools.anyComputedOnClientStoredFields(ctx.configuration(), this.table)) {
                s2 = FieldMapsForInsert.computeOnClientStoredSelect(ctx, keysFlattened, this.table, s2, GeneratorStatementType.INSERT);
            }
            if (requireNewMySQLExcludedEmulation) {
                s2 = DSL.selectFrom(s2.asTable(DSL.table(DSL.name("t")), keysFlattened));
            }
            FieldMapsForInsert.toSQLInsertSelect(ctx, s2);
            ctx.data().remove(Tools.BooleanDataKey.DATA_INSERT_SELECT_WITHOUT_INSERT_COLUMN_LIST);
            ctx.data().remove(Tools.BooleanDataKey.DATA_INSERT_SELECT);
        } else if (this.defaultValues(ctx.configuration())) {
            switch (ctx.family()) {
                case ASE: {
                    if (this.table().getIdentity() != null) {
                        this.acceptDefaultValuesEmulation(ctx, this.table().fields().length - 1);
                        break;
                    }
                    this.acceptDefaultValuesEmulation(ctx, this.table().fields().length);
                    break;
                }
                case DB2: 
                case ORACLE: 
                case DERBY: 
                case ACCESS: 
                case MEMSQL: 
                case AURORA_MYSQL: 
                case MARIADB: 
                case MYSQL: {
                    this.acceptDefaultValuesEmulation(ctx, this.table().fields().length);
                    break;
                }
                default: {
                    ctx.formatSeparator().visit(Keywords.K_DEFAULT_VALUES);
                    break;
                }
            }
        } else if (ctx.family() == SQLDialect.ORACLE && this.insertingDefaultIdentity(ctx)) {
            ctx.data(Tools.BooleanDataKey.DATA_INSERT_SELECT_WITHOUT_INSERT_COLUMN_LIST, true, c -> c.visit(this.insertMaps));
        } else {
            ctx.visit(this.insertMaps);
        }
        return fields;
    }

    private final void acceptDefaultValuesEmulation(Context<?> ctx, int length) {
        ctx.formatSeparator().visit(Keywords.K_VALUES).sql(" (").visit(QueryPartListView.wrap(Collections.nCopies(length, Keywords.K_DEFAULT))).sql(')');
    }

    @Pro
    private final boolean insertingDefaultIdentity(Context<?> ctx) {
        Identity identity = this.table().getIdentity();
        if (identity != null) {
            TableField identityField = identity.getField();
            if (Tools.anyMatch(this.insertMaps.keysFlattened(ctx, GeneratorStatementType.INSERT), identityField::equals)) {
                return false;
            }
            return Tools.anyMatch(this.table().fields(), identityField::equals);
        }
        return false;
    }

    private final List<List<? extends Field<?>>> conflictingKeys(Context<?> ctx) {
        if (this.onConflict != null && this.onConflict.size() > 0) {
            return Collections.singletonList(this.onConflict);
        }
        if (this.onConstraintUniqueKey != null) {
            return Collections.singletonList(this.onConstraintUniqueKey.getFields());
        }
        if (Boolean.TRUE.equals(Tools.settings(ctx.configuration()).isEmulateOnDuplicateKeyUpdateOnPrimaryKeyOnly())) {
            return Collections.singletonList(this.table().getPrimaryKey().getFields());
        }
        return Tools.map(this.table().getKeys(), k -> k.getFields());
    }

    private final void acceptInsertSelect(Context<?> ctx) {
        List<List<Field<?>>> keys2 = this.conflictingKeys(ctx);
        if (!keys2.isEmpty()) {
            Select rows = null;
            Set<Field<?>> fields = this.insertMaps.keysFlattened(ctx, GeneratorStatementType.INSERT);
            if (this.select != null) {
                HashMap map = new HashMap();
                Field<?>[] names = Tools.fields(Tools.degree(this.select));
                ArrayList f = new ArrayList(fields);
                for (int i = 0; i < fields.size() && i < names.length; ++i) {
                    map.put((Field)f.get(i), names[i]);
                }
                rows = DSL.selectFrom(this.select.asTable(DSL.table(DSL.name("t")), names)).whereNotExists(DSL.selectOne().from((TableLike<?>)this.table()).where(this.matchByConflictingKeys(ctx, map)));
            } else {
                for (Map<Field<?>, Field<?>> map : this.insertMaps.maps()) {
                    SelectConditionStep row = DSL.select(Tools.aliasedFields(map.entrySet().stream().filter(e -> fields.contains(e.getKey())).map(Map.Entry::getValue).collect(Collectors.toList()))).whereNotExists(DSL.selectOne().from((TableLike<?>)this.table()).where(this.matchByConflictingKeys(ctx, map)));
                    if (rows == null) {
                        rows = row;
                        continue;
                    }
                    rows = rows.unionAll(row);
                }
            }
            ctx.visit(ctx.dsl().insertInto(this.table()).columns(fields).select(DSL.selectFrom(rows.asTable("t"))));
        } else {
            ctx.sql("[ The ON DUPLICATE KEY IGNORE/UPDATE clause cannot be emulated when inserting into tables without any known keys : " + String.valueOf(this.table()) + " ]");
        }
    }

    private final void acceptMerge(Context<?> ctx) {
        ctx.data(Tools.ExtendedDataKey.DATA_INSERT_ON_DUPLICATE_KEY_UPDATE, this, c -> this.acceptMerge0((Context<?>)c));
    }

    private final void acceptMerge0(Context<?> ctx) {
        if (this.onConflict != null && this.onConflict.size() > 0 || this.onConstraint != null || !this.table().getKeys().isEmpty()) {
            MergeOnConditionStep on;
            SelectField t2;
            Set<Field<?>> k = this.insertMaps.keysFlattened(ctx, null);
            Set<Field<?>> f = null;
            if (!NO_SUPPORT_SUBQUERY_IN_MERGE_USING.contains((Object)ctx.dialect()) || this.select != null || this.insertMaps.rows > 1) {
                Select<?> s2;
                f = k.isEmpty() ? Arrays.asList(this.table().fields()) : k;
                Select<Object> select = s2 = this.select != null ? this.select : this.insertMaps.insertSelect(ctx, null);
                if (s2 == null) {
                    s2 = DSL.select(Tools.map(f, x -> x.getDataType().defaulted() ? x.getDataType().default_() : DSL.inline(null, x)));
                }
                if (Tools.anyComputedOnClientStoredFields(ctx.configuration(), this.table)) {
                    f = FieldMapsForInsert.keysFlattenedAndComputedOnClientStored(ctx, f, this.table, GeneratorStatementType.INSERT);
                    s2 = FieldMapsForInsert.computeOnClientStoredSelect(ctx, this.insertMaps.keysFlattened(ctx, null), this.table, s2, GeneratorStatementType.INSERT);
                }
                t2 = NO_SUPPORT_DERIVED_COLUMN_LIST_IN_MERGE_USING.contains((Object)ctx.dialect()) ? new AliasedSelect(s2, true, true, false, Tools.map(f, Named::getUnqualifiedName, Name[]::new)).as("t") : s2.asTable("t", Tools.map(f, Field::getName, String[]::new));
            } else {
                t2 = null;
            }
            MergeMatchedStep notMatched = on = t2 != null ? ctx.dsl().mergeInto(this.table()).using((TableLike<?>)((Object)t2)).on(this.matchByConflictingKeys(ctx, (Table<?>)t2)) : ctx.dsl().mergeInto(this.table()).usingDual().on(this.matchByConflictingKeys(ctx, this.insertMaps.lastMap()));
            if (this.onDuplicateKeyUpdate) {
                FieldMapForUpdate um = new FieldMapForUpdate(this.updateMap, FieldMapForUpdate.SetClause.INSERT);
                um.replaceAll((key, v) -> {
                    if (v instanceof Excluded) {
                        Excluded e = (Excluded)v;
                        if (t2 != null) {
                            return Tools.orElse(t2.field(e.$field()), () -> Tools.qualify(this.table(), e.$field()));
                        }
                        return Tools.orElse(this.insertMaps.lastMap().get(e.$field()), () -> Tools.qualify(this.table(), e.$field()));
                    }
                    return v;
                });
                um = this.updateMapComputedOnClientStored(ctx, um);
                notMatched = this.updateWhere.hasWhere() ? on.whenMatchedAnd(this.updateWhere.getWhere()).thenUpdate().set(um) : on.whenMatchedThenUpdate().set(um);
            }
            ctx.visit(t2 != null ? notMatched.whenNotMatchedThenInsert(f).values(t2.fields()) : notMatched.whenNotMatchedThenInsert(k).values(this.insertMaps.lastMap().entrySet().stream().filter(e -> k.contains(e.getKey())).map(Map.Entry::getValue).collect(Collectors.toList())));
        } else {
            ctx.sql("[ The ON DUPLICATE KEY IGNORE/UPDATE clause cannot be emulated when inserting into non-updatable tables : " + String.valueOf(this.table()) + " ]");
        }
    }

    private final FieldMapForUpdate updateMapComputedOnClientStored(Context<?> ctx) {
        return this.updateMapComputedOnClientStored(ctx, new FieldMapForUpdate(this.updateMap, FieldMapForUpdate.SetClause.INSERT));
    }

    private final FieldMapForUpdate updateMapComputedOnClientStored(Context<?> ctx, FieldMapForUpdate um) {
        if (Tools.anyComputedOnClientStoredFields(ctx.configuration(), this.table)) {
            if (FieldMapForUpdate.SET_ORDER_IS_RELEVANT.contains((Object)ctx.dialect())) {
                um.computeOnClientStoredReorder(ctx);
            } else {
                um.computeOnClientStoredSelect(ctx);
            }
            return um;
        }
        return um;
    }

    private final Condition matchByConflictingKeys(Context<?> ctx, Map<Field<?>, Field<?>> map) {
        Condition or = null;
        if (this.onConstraint != null && this.onConstraintUniqueKey == null) {
            return DSL.condition("[ cannot create predicate from constraint with unknown columns ]");
        }
        for (List<Field<?>> fields : this.conflictingKeys(ctx)) {
            Condition and = null;
            Iterator<Field<?>> iterator2 = fields.iterator();
            while (iterator2.hasNext()) {
                Field<?> field2;
                Field<?> f = field2 = iterator2.next();
                Condition other = this.matchByConflictingKey(ctx, f, map.get(f));
                and = and == null ? other : and.and(other);
            }
            or = or == null ? and : or.or(and);
        }
        return or;
    }

    private final Condition matchByConflictingKeys(Context<?> ctx, Table<?> s2) {
        Condition or = null;
        if (this.onConstraint != null && this.onConstraintUniqueKey == null) {
            return DSL.condition("[ cannot create predicate from constraint with unknown columns ]");
        }
        for (List<Field<?>> fields : this.conflictingKeys(ctx)) {
            Condition and = null;
            Iterator<Field<?>> iterator2 = fields.iterator();
            while (iterator2.hasNext()) {
                Field<?> field2;
                Field<?> f = field2 = iterator2.next();
                Condition other = this.matchByConflictingKey(ctx, f, s2.field(f));
                and = and == null ? other : and.and(other);
            }
            or = or == null ? and : or.or(and);
        }
        return or;
    }

    private final <T> Condition matchByConflictingKey(Context<?> ctx, Field<T> f, Field<T> v) {
        if (ctx.family() == SQLDialect.ORACLE) {
            return DSL.row(f, DSL.one()).eq(v, DSL.one()).or(DSL.falseCondition());
        }
        return f.eq(v);
    }

    @Override
    public final boolean isExecutable() {
        return this.insertMaps.isExecutable() || this.defaultValues(this.configuration()) || this.select != null;
    }

    @Override
    @Pro
    final int estimatedRowCount(Scope ctx) {
        if (this.defaultValues(ctx.configuration())) {
            return 1;
        }
        if (this.select != null) {
            return Integer.MAX_VALUE;
        }
        if (Tools.anyComputedOnClientStoredFields(ctx.configuration(), this.table)) {
            return Integer.MAX_VALUE;
        }
        return this.insertMaps.rows;
    }

    final InsertQueryImpl<R> copy(Consumer<? super InsertQueryImpl<R>> finisher) {
        return this.copy(finisher, this.table);
    }

    final <O extends Record> InsertQueryImpl<O> copy(Consumer<? super InsertQueryImpl<O>> finisher, Table<O> t2) {
        InsertQueryImpl<O> i = new InsertQueryImpl<O>(this.configuration(), this.with, t2);
        if (!this.returning.isEmpty()) {
            i.setReturning(this.returning);
        }
        i.insertMaps.empty.putAll(this.insertMaps.empty);
        i.insertMaps.values.putAll(this.insertMaps.values);
        i.insertMaps.rows = this.insertMaps.rows;
        i.insertMaps.nextRow = this.insertMaps.nextRow;
        i.defaultValues = this.defaultValues;
        i.select = this.select;
        if (this.onConflict != null) {
            i.onConflict(this.onConflict);
        }
        if (this.onConflictWhere.hasWhere()) {
            i.onConflictWhere.setWhere(ConditionProviderImpl.extractCondition(this.onConflictWhere));
        }
        i.onConstraint = this.onConstraint;
        i.onConstraintUniqueKey = this.onConstraintUniqueKey;
        i.onDuplicateKeyIgnore = this.onDuplicateKeyIgnore;
        i.onDuplicateKeyUpdate = this.onDuplicateKeyUpdate;
        i.updateWhere.setWhere(this.updateWhere.getWhere());
        i.updateMap.putAll(this.updateMap);
        finisher.accept(i);
        return i;
    }

    @Override
    public final WithImpl $with() {
        return this.with;
    }

    @Override
    public final Table<R> $into() {
        return this.table;
    }

    @Override
    public final QOM.Insert<?> $into(Table<?> newInto) {
        if (this.$into() == newInto) {
            return this;
        }
        return this.copy(i -> {}, newInto);
    }

    @Override
    public final QOM.UnmodifiableList<? extends Field<?>> $columns() {
        return QOM.unmodifiable(new ArrayList(this.insertMaps.values.keySet()));
    }

    @Override
    public final QOM.Insert<?> $columns(Collection<? extends Field<?>> columns) {
        throw new QOM.NotYetImplementedException();
    }

    @Override
    public final Select<?> $select() {
        return this.select;
    }

    @Override
    public final QOM.Insert<?> $select(Select<?> newSelect) {
        if (this.$select() == newSelect) {
            return this;
        }
        return this.copy(i -> i.setSelect(this.$columns(), newSelect));
    }

    @Override
    public final boolean $defaultValues() {
        return this.defaultValues;
    }

    @Override
    public final QOM.Insert<?> $defaultValues(boolean newDefaultValues) {
        if (this.$defaultValues() == newDefaultValues) {
            return this;
        }
        return this.copy(i -> {
            if (newDefaultValues) {
                i.setDefaultValues();
            } else {
                i.defaultValues = false;
            }
        });
    }

    @Override
    public final QOM.UnmodifiableList<? extends Row> $values() {
        return QOM.unmodifiable(this.insertMaps.rows());
    }

    @Override
    public final QOM.Insert<?> $values(Collection<? extends Row> values2) {
        throw new QOM.NotYetImplementedException();
    }

    @Override
    public final boolean $onDuplicateKeyIgnore() {
        return this.onDuplicateKeyIgnore;
    }

    @Override
    public final QOM.Insert<?> $onDuplicateKeyIgnore(boolean newOnDuplicateKeyIgnore) {
        if (this.$onDuplicateKeyIgnore() == newOnDuplicateKeyIgnore) {
            return this;
        }
        return this.copy(i -> i.onDuplicateKeyIgnore(newOnDuplicateKeyIgnore));
    }

    @Override
    public final boolean $onDuplicateKeyUpdate() {
        return this.onDuplicateKeyUpdate;
    }

    @Override
    public final QOM.Insert<?> $onDuplicateKeyUpdate(boolean newOnDuplicateKeyUpdate) {
        if (this.$onDuplicateKeyUpdate() == newOnDuplicateKeyUpdate) {
            return this;
        }
        return this.copy(i -> i.onDuplicateKeyUpdate(newOnDuplicateKeyUpdate));
    }

    @Override
    public final QOM.UnmodifiableList<? extends Field<?>> $onConflict() {
        return QOM.unmodifiable(this.onConflict == null ? new ArrayList() : this.onConflict);
    }

    @Override
    public final QOM.Insert<?> $onConflict(Collection<? extends Field<?>> newOnConflict) {
        if (this.$onConflict() == newOnConflict) {
            return this;
        }
        return this.copy(i -> i.onConflict(newOnConflict));
    }

    @Override
    public final Condition $onConflictWhere() {
        return this.onConflictWhere.getWhereOrNull();
    }

    @Override
    public final QOM.Insert<?> $onConflictWhere(Condition newWhere) {
        if (this.$onConflictWhere() == newWhere) {
            return this;
        }
        return this.copy(i -> i.onConflictWhere.setWhere(newWhere));
    }

    @Override
    public final QOM.UnmodifiableMap<? extends FieldOrRow, ? extends FieldOrRowOrSelect> $updateSet() {
        return QOM.unmodifiable(this.updateMap);
    }

    @Override
    public final QOM.Insert<?> $updateSet(Map<? extends FieldOrRow, ? extends FieldOrRowOrSelect> newUpdateSet) {
        if (this.$updateSet() == newUpdateSet) {
            return this;
        }
        return this.copy(i -> i.addValuesForUpdate(newUpdateSet));
    }

    @Override
    public final Condition $updateWhere() {
        return this.updateWhere.getWhereOrNull();
    }

    @Override
    public final QOM.Insert<?> $updateWhere(Condition newWhere) {
        if (this.$updateWhere() == newWhere) {
            return this;
        }
        return this.copy(i -> i.updateWhere.setWhere(newWhere));
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$with(), this.$into());
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$with(), this.$into(), (cte, i) -> {
            InsertQueryImpl<R> r = new InsertQueryImpl<R>(this.configuration(), (WithImpl)cte, i);
            r.setReturning(this.returning);
            return r;
        }, replacer);
    }
}

