/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import org.jooq.Asterisk;
import org.jooq.Binding;
import org.jooq.CommonTableExpression;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DMLQuery;
import org.jooq.DSLContext;
import org.jooq.DataType;
import org.jooq.Delete;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.jooq.Field;
import org.jooq.Generator;
import org.jooq.GeneratorStatementType;
import org.jooq.Identity;
import org.jooq.Insert;
import org.jooq.Name;
import org.jooq.Param;
import org.jooq.Pro;
import org.jooq.QualifiedAsterisk;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.Row;
import org.jooq.RowId;
import org.jooq.SQLDialect;
import org.jooq.Scope;
import org.jooq.Select;
import org.jooq.SelectField;
import org.jooq.SelectFieldOrAsterisk;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableLike;
import org.jooq.UniqueKey;
import org.jooq.Update;
import org.jooq.conf.ExecuteWithoutWhere;
import org.jooq.conf.RenderNameCase;
import org.jooq.conf.SettingsTools;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.AbstractQuery;
import org.jooq.impl.AbstractRecord;
import org.jooq.impl.AbstractRow;
import org.jooq.impl.AbstractRowCountQuery;
import org.jooq.impl.Alias;
import org.jooq.impl.CommonTableExpressionList;
import org.jooq.impl.CursorImpl;
import org.jooq.impl.DSL;
import org.jooq.impl.DataChangeDeltaTable;
import org.jooq.impl.DefaultBindingGetStatementContext;
import org.jooq.impl.DefaultBindingRegisterContext;
import org.jooq.impl.DefaultExecuteContext;
import org.jooq.impl.DefaultGeneratorContext;
import org.jooq.impl.DefaultUnwrapperProvider;
import org.jooq.impl.DeleteQueryImpl;
import org.jooq.impl.ExecuteListeners;
import org.jooq.impl.FieldMapsForInsert;
import org.jooq.impl.FieldsImpl;
import org.jooq.impl.InsertQueryImpl;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.ResultImpl;
import org.jooq.impl.RowAsField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.SelectFieldList;
import org.jooq.impl.TableAsField;
import org.jooq.impl.ThrowingSupplier;
import org.jooq.impl.Tools;
import org.jooq.impl.TopLevelCte;
import org.jooq.impl.WithImpl;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.StringUtils;
import org.jooq.tools.jdbc.BatchedPreparedStatement;
import org.jooq.tools.jdbc.JDBCUtils;
import org.jooq.util.sqlite.SQLiteDSL;

abstract class AbstractDMLQuery<R extends Record>
extends AbstractRowCountQuery
implements DMLQuery<R> {
    private static final JooqLogger log = JooqLogger.getLogger(AbstractQuery.class);
    private static final Set<SQLDialect> NO_SUPPORT_INSERT_ALIASED_TABLE = SQLDialect.supportedBy(SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.SNOWFLAKE);
    private static final Set<SQLDialect> NO_NATIVE_SUPPORT_INSERT_RETURNING = SQLDialect.supportedUntil(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.BIGQUERY, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_4, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA);
    private static final Set<SQLDialect> NO_NATIVE_SUPPORT_UPDATE_RETURNING = SQLDialect.supportedUntil(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.BIGQUERY, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_4, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA);
    private static final Set<SQLDialect> NO_NATIVE_SUPPORT_DELETE_RETURNING = SQLDialect.supportedUntil(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.BIGQUERY, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_0, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA);
    private static final Set<SQLDialect> NATIVE_SUPPORT_DATA_CHANGE_DELTA_TABLE = SQLDialect.supportedBy(SQLDialect.DB2, SQLDialect.H2_2_0_202);
    private static final Set<SQLDialect> NO_SUPPORT_FETCHING_KEYS = SQLDialect.supportedBy(SQLDialect.BIGQUERY, SQLDialect.EXASOL, SQLDialect.HANA, SQLDialect.IGNITE, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.VERTICA);
    private static final Set<SQLDialect> NO_SUPPORT_RETURNING_ASTERISK = SQLDialect.supportedUntil(SQLDialect.FIREBIRD_3_0, SQLDialect.MARIADB);
    @Pro
    private static final Set<SQLDialect> EMULATE_UPDATABLE_VIEWS_WITH_CTE = SQLDialect.supportedBy(SQLDialect.SQLSERVER, SQLDialect.SQLDATAWAREHOUSE);
    @Pro
    private static final Set<SQLDialect> EMULATE_ALIASED_TABLES_WITH_CTE = SQLDialect.supportedBy(SQLDialect.SQLSERVER, SQLDialect.SQLDATAWAREHOUSE);
    @Pro
    static final Set<SQLDialect> EMULATE_RETURNING_WITH_OUTPUT = SQLDialect.supportedBy(SQLDialect.SQLSERVER, SQLDialect.SQLDATAWAREHOUSE);
    final WithImpl with;
    final Table<R> table;
    final SelectFieldList<SelectFieldOrAsterisk> returning;
    final List<Field<?>> returningResolvedAsterisks;
    Result<Record> returnedResult;
    Result<R> returned;

    AbstractDMLQuery(Configuration configuration, WithImpl with, Table<R> table) {
        super(configuration);
        this.with = with;
        this.table = table;
        this.returning = new SelectFieldList();
        this.returningResolvedAsterisks = new ArrayList();
    }

    public final void setReturning() {
        this.setReturning(this.table.fields());
    }

    public final void setReturning(Identity<R, ?> identity) {
        if (identity != null) {
            this.setReturning(identity.getField());
        }
    }

    public final void setReturning(SelectFieldOrAsterisk ... fields) {
        this.setReturning(Arrays.asList(fields));
    }

    public final void setReturning(Collection<? extends SelectFieldOrAsterisk> fields) {
        this.returning.clear();
        this.returning.addAll(fields.isEmpty() ? Arrays.asList(this.table.fields()) : fields);
        this.returningResolvedAsterisks.clear();
        for (SelectFieldOrAsterisk s2 : this.returning) {
            if (s2 instanceof Field) {
                Field f = (Field)s2;
                this.returningResolvedAsterisks.add(f);
                continue;
            }
            if (s2 instanceof QualifiedAsterisk) {
                QualifiedAsterisk a = (QualifiedAsterisk)s2;
                this.returningResolvedAsterisks.addAll(Arrays.asList(a.qualifier().fields()));
                continue;
            }
            if (s2 instanceof Asterisk) {
                this.returningResolvedAsterisks.addAll(Arrays.asList(this.table.fields()));
                continue;
            }
            if (s2 instanceof Row) {
                Row r = (Row)((Object)s2);
                this.returningResolvedAsterisks.add(new RowAsField(r));
                continue;
            }
            if (s2 instanceof Table) {
                Table t2 = (Table)s2;
                this.returningResolvedAsterisks.add(new TableAsField(t2));
                continue;
            }
            throw new UnsupportedOperationException("Type not supported: " + String.valueOf(s2));
        }
    }

    public final R getReturnedRecord() {
        if (this.getReturnedRecords().isEmpty()) {
            return null;
        }
        return (R)((Record)this.getReturnedRecords().get(0));
    }

    public final Result<R> getReturnedRecords() {
        if (this.returned == null) {
            if (this.table.fields().length > 0) {
                this.warnOnAPIMisuse();
                this.returned = this.getResult().into(this.table);
            } else {
                this.returned = this.getResult();
            }
        }
        return this.returned;
    }

    private final void warnOnAPIMisuse() {
        for (Field<?> field2 : this.getResult().fields()) {
            if (this.table.field(field2) != null) continue;
            log.warn((Object)"API misuse", "Column " + String.valueOf(field2) + " has been requested through the returning() clause, which is not present in table " + String.valueOf(this.table) + ". Use StoreQuery.getResult() or the returningResult() clause instead.");
        }
    }

    final Table<R> table() {
        return this.table;
    }

    final Table<?> table(Context<?> ctx) {
        CommonTableExpression<?> updatableCte = Tools.extractUpdatableCte(ctx);
        if (updatableCte != null) {
            return updatableCte;
        }
        if (ctx.family() == SQLDialect.ORACLE && this instanceof Insert && !this.returning.isEmpty() && this.table().getSchema() != null && !Boolean.FALSE.equals(ctx.settings().isApplyWorkaroundFor7962())) {
            Alias<Table<R>> alias = Tools.alias(this.table());
            if (alias != null) {
                return DSL.table(alias.wrapped().getSchema().getQualifiedName().unquotedName().append(alias.wrapped().getUnqualifiedName())).as(alias.alias);
            }
            return DSL.table(this.table().getSchema().getQualifiedName().unquotedName().append(this.table().getUnqualifiedName()));
        }
        if (NO_SUPPORT_INSERT_ALIASED_TABLE.contains((Object)ctx.dialect()) && this instanceof Insert) {
            return StringUtils.defaultIfNull(Tools.aliased(this.table()), this.table());
        }
        return this.table();
    }

    public final Result<?> getResult() {
        if (this.returnedResult == null) {
            this.returnedResult = new ResultImpl<Record>(this.configuration(), this.returningResolvedAsterisks);
        }
        return this.returnedResult;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public final void accept(Context<?> ctx) {
        boolean emulateTableWithCTE;
        QOM.UnmodifiableList<Table<?>> unmodifiableList;
        WithImpl w = this.with;
        ctx.scopeStart();
        ctx.data(Tools.SimpleDataKey.DATA_DML_TARGET_TABLE, this.table);
        AbstractDMLQuery abstractDMLQuery = this;
        if (abstractDMLQuery instanceof DeleteQueryImpl) {
            DeleteQueryImpl d = (DeleteQueryImpl)abstractDMLQuery;
            unmodifiableList = d.$using();
        } else {
            unmodifiableList = null;
        }
        ctx.data(Tools.SimpleDataKey.DATA_DML_USING_TABLES, unmodifiableList);
        if (WithImpl.NO_SUBQUERY_CTE.contains((Object)ctx.dialect()) && ctx.data(Tools.SimpleDataKey.DATA_TOP_LEVEL_CTE) == null) {
            ctx.data(Tools.SimpleDataKey.DATA_TOP_LEVEL_CTE, new TopLevelCte());
        }
        boolean bl = emulateTableWithCTE = (EMULATE_UPDATABLE_VIEWS_WITH_CTE.contains((Object)ctx.dialect()) || EMULATE_ALIASED_TABLES_WITH_CTE.contains((Object)ctx.dialect())) && Tools.extractSelectFromDerivedTable(this.table) != null;
        if (emulateTableWithCTE) {
            ctx.data(Tools.SimpleDataKey.DATA_DML_TABLE_AS_CTE, this.table);
            if (w == null) {
                w = new WithImpl(ctx.configuration(), false);
            }
        }
        if (w != null) {
            ctx.visit(w);
        } else {
            CommonTableExpressionList.markTopLevelCteAndAccept(ctx, c -> {});
        }
        boolean previousDeclareFields = ctx.declareFields();
        if (NATIVE_SUPPORT_DATA_CHANGE_DELTA_TABLE.contains((Object)ctx.dialect()) && !this.returning.isEmpty() && ctx.data(Tools.SimpleDataKey.DATA_RENDERING_DATA_CHANGE_DELTA_TABLE) == null) {
            Tools.increment(ctx.data(), Tools.SimpleDataKey.DATA_RENDERING_DATA_CHANGE_DELTA_TABLE, () -> ctx.visit(DSL.select(this.returning).from((TableLike<?>)((Object)new DataChangeDeltaTable(this instanceof Delete ? QOM.ResultOption.OLD : QOM.ResultOption.FINAL, this).as(this.table().getUnqualifiedName())))));
        } else if (this.emulateReturningUsingOutput(ctx) && this.fetchTriggerValuesAfterSQLServerOutput(ctx)) {
            ctx.visit(Keywords.K_DECLARE).sql(" @result ").visit(Keywords.K_TABLE).sqlIndentStart(" (");
            boolean first = true;
            boolean qualify = ctx.qualify();
            boolean declareFields = ctx.declareFields();
            ctx.qualify(false);
            ctx.declareFields(false);
            UniqueKey<R> pk = this.table().getPrimaryKey();
            boolean mergeNonKeyValuesIntoResult = pk != null && this.mergeNonKeyValuesIntoResult(ctx);
            BiFunction<Field, Boolean, Field> resultMapping = (field2, keepType) -> DSL.field(field2 instanceof TableField && !field2.getDataType().computedOnClientVirtual(ctx.configuration()) ? field2.getUnqualifiedName() : Tools.autoAliasName(field2), keepType != false ? field2.getDataType() : Tools.removeGenerator(ctx.configuration(), field2.getDataType()));
            ArrayList<Field> arrayList = new ArrayList<Field>();
            for (Field<?> field3 : Tools.flattenCollection(this.returningResolvedAsterisks, false, true)) {
                arrayList.add(resultMapping.apply(field3, false));
            }
            LinkedHashSet<Field<Object>> output = new LinkedHashSet<Field<Object>>();
            for (Field<?> field4 : Tools.flattenCollection(this.returningResolvedAsterisks, true, true)) {
                output.add(field4);
            }
            if (mergeNonKeyValuesIntoResult) {
                output.addAll(pk.getFields());
            }
            for (Field field5 : output) {
                if (!first) {
                    ctx.sql(',').formatSeparator();
                }
                ctx.visit(resultMapping.apply(field5, false)).sql(' ');
                Tools.toSQLDDLTypeDeclaration0(ctx, field5.getDataType());
                first = false;
            }
            ctx.qualify(qualify);
            ctx.declareFields(declareFields);
            ctx.sqlIndentEnd(");").formatSeparator();
            this.accept0(ctx);
            ctx.sql(';').formatSeparator();
            SelectField selectField = DSL.table("@result").as("r");
            if (mergeNonKeyValuesIntoResult) {
                LinkedHashMap<Field, Field<Object>> linkedHashMap = new LinkedHashMap<Field, Field<Object>>();
                ArrayList<SelectField> s2 = new ArrayList<SelectField>();
                List<Condition> conditions = Tools.map(Tools.flattenCollection(pk.getFields(), true, true), arg_0 -> AbstractDMLQuery.lambda$accept$3((Table)selectField, arg_0));
                for (Field field5 : output) {
                    if (field5.getDataType().computed() || !Tools.reference(Tools.unalias(field5))) continue;
                    if (!pk.getFields().contains(field5)) {
                        linkedHashMap.put(resultMapping.apply(field5, true), DSL.field(DSL.name("s", Tools.autoAlias(field5))));
                        s2.add(field5.as(Tools.autoAlias(field5)));
                        continue;
                    }
                    s2.add(field5);
                }
                if (!linkedHashMap.isEmpty()) {
                    ctx.visit(DSL.mergeInto(selectField).using(DSL.select(s2).from((TableLike<?>)this.table()).asTable("s")).on(DSL.and(conditions)).whenMatchedThenUpdate().set(linkedHashMap)).formatSeparator();
                }
            }
            ctx.visit(DSL.select(arrayList).from((TableLike<?>)((Object)selectField))).sql(';');
        } else if (ctx.family() == SQLDialect.ORACLE && this.emulateOracleReturning(ctx)) {
            if (this.emulateOracleReturningWithBulkCollectInto(ctx)) {
                int i;
                int i2;
                ctx.visit(Keywords.K_DECLARE).formatIndentStart();
                ArrayList insertFields = null;
                FieldMapsForInsert insertMaps = null;
                Select<?> insertSelect = null;
                boolean containsExpressions = false;
                AbstractDMLQuery resultMapping = this;
                if (resultMapping instanceof InsertQueryImpl) {
                    InsertQueryImpl insert = (InsertQueryImpl)resultMapping;
                    insertMaps = insert.getInsertMaps();
                    insertSelect = insert.getSelect() != null ? insert.getSelect() : (Tools.anyComputedOnClientStoredFields(ctx.configuration(), this.table) ? insertMaps.computeOnClientStoredSelect(ctx) : null);
                    insertFields = new ArrayList(insertMaps.keysFlattenedAndComputedOnClientStored(ctx, GeneratorStatementType.INSERT));
                    for (int i3 = 0; i3 < insertFields.size(); ++i3) {
                        ctx.formatSeparator().sql('i').sql(i3).sql(' ').visit(Tools.dbmsSQLType(((Field)insertFields.get(i3)).getDataType())).sql(';');
                    }
                    block5: for (Field field6 : insertFields) {
                        List<Field<?>> values2 = insertMaps.values.get(field6);
                        if (values2 == null) continue;
                        for (Field<?> field7 : values2) {
                            if (field7 instanceof Param) continue;
                            containsExpressions = true;
                            break block5;
                        }
                    }
                }
                for (int i22 = 0; i22 < this.returningResolvedAsterisks.size(); ++i22) {
                    ctx.formatSeparator().sql('o').sql(i22).sql(' ').visit(Tools.dbmsSQLType(this.returningResolvedAsterisks.get(i22).getDataType())).sql(';');
                }
                for (i2 = 0; i2 < this.returningResolvedAsterisks.size(); ++i2) {
                    ctx.formatSeparator().sql('c').sql(i2).sql(" sys_refcursor").sql(';');
                }
                ctx.formatIndentEnd().formatSeparator().visit(Keywords.K_BEGIN).formatIndentStart().formatSeparator();
                if (insertFields == null || insertMaps == null) {
                    this.accept0(ctx);
                } else {
                    if (containsExpressions || insertSelect != null) {
                        void var11_27;
                        Select<?> s3 = insertSelect != null ? insertSelect : insertMaps.insertSelect(ctx, GeneratorStatementType.INSERT);
                        QueryPartList<Name> into = new QueryPartList<Name>();
                        boolean bl2 = false;
                        while (var11_27 < insertFields.size()) {
                            into.add(DSL.unquotedName("i" + (int)var11_27));
                            ++var11_27;
                        }
                        ctx.visit(Keywords.K_SELECT).sql(" *").formatSeparator().visit(Keywords.K_BULK_COLLECT_INTO).sql(' ').visit(into).formatSeparator().visit(Keywords.K_FROM).sqlIndentStart(" (").visit(s3).sqlIndentEnd(");").formatSeparator();
                    } else {
                        for (i2 = 0; i2 < insertFields.size(); ++i2) {
                            Field field7 = (Field)insertFields.get(i2);
                            List<Field<?>> list = insertMaps.values.get(field7);
                            for (int j = 0; j < list.size(); ++j) {
                                ctx.sql('i').sql(i2).sql('(').sql(j + 1).sql(") := ").visit(list.get(j)).sql(";").formatSeparator();
                            }
                        }
                    }
                    ctx.visit(Keywords.K_FORALL).sql(" i ").visit(Keywords.K_IN).sql(" 1 .. i0.count").formatIndentStart().formatSeparator();
                    ctx.data(Tools.BooleanDataKey.DATA_EMULATE_BULK_INSERT_RETURNING, true, c -> this.accept0((Context<?>)c));
                }
                ctx.data(Tools.BooleanDataKey.DATA_UNALIAS_ALIASED_EXPRESSIONS, true, c -> c.formatSeparator().visit(Keywords.K_RETURNING).sql(' ').declareFields(true).visit(new SelectFieldList((Iterable<Field<?>>)this.returningResolvedAsterisks)).declareFields(previousDeclareFields).formatSeparator().visit(Keywords.K_BULK_COLLECT_INTO).sql(' '));
                for (i = 0; i < this.returningResolvedAsterisks.size(); ++i) {
                    ctx.sql(i == 0 ? "" : ", ").sql('o').sql(i);
                }
                ctx.sql(';');
                if (insertFields != null) {
                    ctx.formatIndentEnd();
                }
                ctx.formatSeparator().sql("? := ").visit(Keywords.K_SQL).sql('%').visit(Keywords.K_ROWCOUNT).sql(';');
                for (i = 0; i < this.returningResolvedAsterisks.size(); ++i) {
                    ctx.formatSeparator().visit(Keywords.K_OPEN).sql(" c").sql(i).sql(' ').visit(Keywords.K_FOR).sql(' ').visit(Keywords.K_SELECT).sql(" * ").visit(Keywords.K_FROM).sql(' ').visit(Keywords.K_TABLE).sql("(o").sql(i).sql(");");
                }
                for (i = 0; i < this.returningResolvedAsterisks.size(); ++i) {
                    ctx.formatSeparator().sql("? := c").sql(i).sql(';');
                }
                ctx.formatIndentEnd().formatSeparator().visit(Keywords.K_END).sql(";");
            } else {
                ctx.visit(Keywords.K_BEGIN).formatIndentStart().formatSeparator();
                this.accept0(ctx);
                ctx.data(Tools.BooleanDataKey.DATA_UNALIAS_ALIASED_EXPRESSIONS, true, c -> c.formatSeparator().visit(Keywords.K_RETURNING).sql(' ').declareFields(true).visit(this.returning).declareFields(previousDeclareFields).formatSeparator().visit(Keywords.K_INTO).sql(' '));
                for (int i = 0; i < this.returningResolvedAsterisks.size(); ++i) {
                    ctx.sql(i == 0 ? "" : ", ").sql('?');
                }
                ctx.sql(';').formatSeparator().sql("? := ").visit(Keywords.K_SQL).sql('%').visit(Keywords.K_ROWCOUNT).sql(';').formatIndentEnd().formatSeparator().visit(Keywords.K_END).sql(";");
            }
        } else {
            this.accept0(ctx);
        }
        if (emulateTableWithCTE) {
            ctx.data().remove(Tools.SimpleDataKey.DATA_DML_TABLE_AS_CTE);
        }
        ctx.data().remove(Tools.SimpleDataKey.DATA_DML_USING_TABLES);
        ctx.data().remove(Tools.SimpleDataKey.DATA_DML_TARGET_TABLE);
        ctx.scopeEnd();
    }

    @Pro
    final boolean emulateReturningUsingOutput(Scope ctx) {
        return EMULATE_RETURNING_WITH_OUTPUT.contains((Object)ctx.dialect()) && !this.returning.isEmpty() && !Boolean.FALSE.equals(ctx.settings().isRenderOutputForSQLServerReturningClause());
    }

    @Pro
    private final boolean fetchTriggerValuesAfterSQLServerOutput(Scope ctx) {
        return !Boolean.FALSE.equals(ctx.settings().isFetchTriggerValuesAfterSQLServerOutput()) && !(this instanceof Delete);
    }

    @Pro
    private final boolean mergeNonKeyValuesIntoResult(Context<?> ctx) {
        UniqueKey<R> pk = this.table().getPrimaryKey();
        return this.fetchTriggerValuesAfterSQLServerOutput(ctx) && pk != null && this.exceptAny(this.returningResolvedAsterisks, pk.getFields());
    }

    @Pro
    private final <T> boolean exceptAny(List<? extends T> l1, List<? extends T> l2) {
        return Tools.anyMatch(l1, e -> !l2.contains(e));
    }

    @Pro
    abstract int estimatedRowCount(Scope var1);

    @Pro
    private final boolean returningRowid() {
        return Tools.anyMatch(this.returning, f -> f instanceof Field && ((Field)f).getType() == RowId.class);
    }

    @Pro
    private final boolean returningExpressions(Scope ctx) {
        return Tools.anyMatch(this.returning, f -> f instanceof Field && (this.table.field((Field)f) == null || ((Field)f).getDataType().computedOnClientVirtual(ctx.configuration())));
    }

    @Pro
    private final boolean returningUDTs() {
        return Tools.anyMatch(this.returningResolvedAsterisks, f -> f.getDataType().isUDT());
    }

    @Pro
    private final boolean emulateOracleReturning(Scope ctx) {
        return !this.returning.isEmpty() && (this.estimatedRowCount(ctx) > 1 || this.returningRowid() || this.returningExpressions(ctx) || this.returningUDTs());
    }

    @Pro
    private final boolean emulateOracleReturningWithBulkCollectInto(Scope ctx) {
        return ctx.dialect().supports(SQLDialect.ORACLE12C) && this.estimatedRowCount(ctx) > 1;
    }

    abstract void accept0(Context<?> var1);

    void executeWithoutWhere(String message, ExecuteWithoutWhere executeWithoutWhere) {
        switch (executeWithoutWhere) {
            case IGNORE: {
                break;
            }
            case LOG_DEBUG: {
                if (!log.isDebugEnabled()) break;
                log.debug((Object)message, "A statement is executed without WHERE clause");
                break;
            }
            case LOG_INFO: {
                if (!log.isInfoEnabled()) break;
                log.info((Object)message, "A statement is executed without WHERE clause");
                break;
            }
            case LOG_WARN: {
                log.warn((Object)message, "A statement is executed without WHERE clause");
                break;
            }
            case THROW: {
                throw new DataAccessException("A statement is executed without WHERE clause");
            }
        }
    }

    @Pro
    final void toSQLOutput(Context<?> ctx) {
        if (!this.returning.isEmpty()) {
            switch (ctx.family()) {
                case SQLSERVER: 
                case SQLDATAWAREHOUSE: {
                    if (Boolean.FALSE.equals(ctx.settings().isRenderOutputForSQLServerReturningClause())) break;
                    UniqueKey<R> pk = this.table().getPrimaryKey();
                    LinkedHashSet<Field<Object>> output = new LinkedHashSet<Field<Object>>();
                    for (Field<?> field2 : Tools.flattenCollection(this.returningResolvedAsterisks, true, true)) {
                        output.add(field2);
                    }
                    if (pk != null && this.mergeNonKeyValuesIntoResult(ctx)) {
                        output.addAll(pk.getFields());
                    }
                    ctx.formatSeparator().visit(Keywords.K_OUTPUT).sql(' ').declareFields(true, c1 -> c1.visit(new SelectFieldList((List)((Object)new SelectFieldList(output).$replace((? super QueryPart q) -> {
                        Field f;
                        if (q instanceof Field && (f = (Field)q).getDataType().computedOnClientVirtual(c1.configuration())) {
                            Table table;
                            Generator generator = f.getDataType().generatedAlwaysAsGenerator();
                            Configuration configuration = ctx.configuration();
                            Map<Object, Object> map = ctx.data();
                            Instant instant = ctx.creationTime();
                            if (f instanceof TableField) {
                                TableField tf = (TableField)f;
                                table = tf.getTable();
                            } else {
                                table = null;
                            }
                            Field computed = (Field)generator.apply(new DefaultGeneratorContext(configuration, map, instant, table, f, GeneratorStatementType.SELECT));
                            if (computed != null) {
                                return computed;
                            }
                        }
                        return q;
                    }).$replace((? super QueryPart q) -> {
                        Field f;
                        if (q instanceof Field && Tools.reference(f = (Field)q)) {
                            return DSL.field((this instanceof Delete ? Names.N_DELETED : Names.N_INSERTED).append(f.getUnqualifiedName()), f.getDataType());
                        }
                        return q;
                    })))));
                    if (!this.fetchTriggerValuesAfterSQLServerOutput(ctx)) break;
                    ctx.formatSeparator().visit(Keywords.K_INTO).sql(" @result");
                    break;
                }
            }
        }
    }

    final void toSQLReturning(Context<?> ctx) {
        if (!this.returning.isEmpty() && this.nativeSupportReturning(ctx)) {
            boolean unqualify;
            boolean qualify = ctx.qualify();
            boolean bl = unqualify = ctx.family() == SQLDialect.MARIADB;
            if (unqualify) {
                ctx.qualify(false);
            }
            ctx.formatSeparator().visit(Keywords.K_RETURNING).sql(' ').declareFields(true, c -> c.visit(NO_SUPPORT_RETURNING_ASTERISK.contains((Object)c.dialect()) ? new SelectFieldList<SelectFieldOrAsterisk>((Iterable<SelectFieldOrAsterisk>)this.returningResolvedAsterisks) : this.returning));
            if (unqualify) {
                ctx.qualify(qualify);
            }
        }
    }

    final boolean nativeSupportReturning(Scope ctx) {
        return this instanceof Insert && !NO_NATIVE_SUPPORT_INSERT_RETURNING.contains((Object)ctx.dialect()) || this instanceof Update && !NO_NATIVE_SUPPORT_UPDATE_RETURNING.contains((Object)ctx.dialect()) || this instanceof Delete && !NO_NATIVE_SUPPORT_DELETE_RETURNING.contains((Object)ctx.dialect());
    }

    final boolean nativeSupportReturningOrDataChangeDeltaTable(Scope ctx) {
        return NATIVE_SUPPORT_DATA_CHANGE_DELTA_TABLE.contains((Object)ctx.dialect()) || this.nativeSupportReturning(ctx);
    }

    @Override
    protected final void prepare(ExecuteContext ctx) throws SQLException {
        this.prepare0(ctx);
        Tools.setFetchSize(ctx, 0);
    }

    private final void prepare0(ExecuteContext ctx) throws SQLException {
        Connection connection = ctx.connection();
        if (this.returning.isEmpty()) {
            super.prepare(ctx);
        } else if (NO_SUPPORT_FETCHING_KEYS.contains((Object)ctx.dialect())) {
            super.prepare(ctx);
        } else if (this.nativeSupportReturningOrDataChangeDeltaTable(ctx)) {
            super.prepare(ctx);
        } else if (ctx.family() == SQLDialect.ASE) {
            if (ctx.statement() == null) {
                ctx.statement(connection.prepareStatement(ctx.sql(), 1));
            }
        } else {
            switch (ctx.family()) {
                case ACCESS: 
                case SYBASE: 
                case SQLITE: 
                case CUBRID: {
                    super.prepare(ctx);
                    break;
                }
                case SQLSERVER: 
                case SQLDATAWAREHOUSE: 
                case ASE: 
                case AURORA_MYSQL: 
                case INFORMIX: 
                case INGRES: 
                case MEMSQL: 
                case REDSHIFT: 
                case DERBY: 
                case MARIADB: 
                case MYSQL: {
                    if (ctx.statement() != null) break;
                    ctx.statement(connection.prepareStatement(ctx.sql(), 1));
                    break;
                }
                case ORACLE: {
                    if (this.emulateOracleReturning(ctx)) {
                        if (ctx.statement() != null) break;
                        ctx.statement(connection.prepareCall(ctx.sql()));
                        break;
                    }
                }
                default: {
                    if (ctx.statement() != null) break;
                    RenderNameCase style = SettingsTools.getRenderNameCase(this.configuration().settings());
                    ctx.statement(connection.prepareStatement(ctx.sql(), Tools.map(Tools.flattenCollection(this.returningResolvedAsterisks, false, true), style == RenderNameCase.UPPER ? f -> f.getName().toUpperCase(SettingsTools.renderLocale(this.configuration().settings())) : (style == RenderNameCase.LOWER ? f -> f.getName().toLowerCase(SettingsTools.renderLocale(this.configuration().settings())) : f -> f.getName())).toArray(Tools.EMPTY_STRING)));
                }
            }
        }
    }

    @Override
    protected final int execute(ExecuteContext ctx, ExecuteListener listener) throws SQLException {
        ResultSet rs;
        this.returned = null;
        this.returnedResult = null;
        if (this.returning.isEmpty()) {
            return super.execute(ctx, listener);
        }
        if (NO_SUPPORT_FETCHING_KEYS.contains((Object)ctx.dialect())) {
            return super.execute(ctx, listener);
        }
        int result = 0;
        switch (ctx.family()) {
            case SQLITE: {
                listener.executeStart(ctx);
                result = this.executeImmediate(ctx.statement()).executeUpdate();
                ctx.rows(result);
                listener.executeEnd(ctx);
                DSLContext create = ctx.dsl();
                this.returnedResult = create.select(this.returning).from((TableLike<?>)this.table).where(SQLiteDSL.rowid().eq(DSL.field("last_insert_rowid()", SQLiteDSL.rowid().getDataType()))).fetch();
                this.returnedResult.attach(((DefaultExecuteContext)ctx).originalConfiguration());
                return result;
            }
            case ACCESS: 
            case SYBASE: 
            case CUBRID: {
                listener.executeStart(ctx);
                result = this.executeImmediate(ctx.statement()).executeUpdate();
                ctx.rows(result);
                listener.executeEnd(ctx);
                this.selectReturning(((DefaultExecuteContext)ctx).originalConfiguration(), ctx.configuration(), ctx.dsl().lastID());
                return result;
            }
            case SQLSERVER: 
            case SQLDATAWAREHOUSE: {
                if (this.emulateReturningUsingOutput(ctx)) {
                    rs = this.executeReturningQuery(ctx, listener);
                    break;
                }
                return this.executeReturningGeneratedKeysFetchAdditionalRows(ctx, listener);
            }
            case ASE: 
            case AURORA_MYSQL: 
            case INFORMIX: 
            case INGRES: 
            case MEMSQL: 
            case REDSHIFT: 
            case DERBY: 
            case MYSQL: {
                return this.executeReturningGeneratedKeysFetchAdditionalRows(ctx, listener);
            }
            case MARIADB: {
                if (!this.nativeSupportReturning(ctx)) {
                    return this.executeReturningGeneratedKeysFetchAdditionalRows(ctx, listener);
                }
                rs = this.executeReturningQuery(ctx, listener);
                break;
            }
            case H2: {
                if (!NATIVE_SUPPORT_DATA_CHANGE_DELTA_TABLE.contains((Object)ctx.dialect())) {
                    return this.executeReturningGeneratedKeysFetchAdditionalRows(ctx, listener);
                }
                rs = this.executeReturningQuery(ctx, listener);
                break;
            }
            case DB2: 
            case AURORA_POSTGRES: 
            case COCKROACHDB: 
            case FIREBIRD: 
            case POSTGRES: 
            case YUGABYTEDB: {
                rs = this.executeReturningQuery(ctx, listener);
                break;
            }
            case ORACLE: {
                if (this.emulateOracleReturning(ctx)) {
                    int rowcountOffset;
                    int offset = this.rendered.bindValues.size();
                    Binding<?, Result<Record>> resultBinding = SQLDataType.RESULT.getBinding();
                    if (this.emulateOracleReturningWithBulkCollectInto(ctx)) {
                        rowcountOffset = offset + 1;
                        for (i = 0; i < this.returningResolvedAsterisks.size(); ++i) {
                            resultBinding.register(new DefaultBindingRegisterContext(ctx, (CallableStatement)ctx.statement(), offset + i + 2));
                        }
                    } else {
                        rowcountOffset = offset + this.returningResolvedAsterisks.size() + 1;
                        for (i = 0; i < this.returningResolvedAsterisks.size(); ++i) {
                            this.returningResolvedAsterisks.get(i).getBinding().register(new DefaultBindingRegisterContext(ctx, (CallableStatement)ctx.statement(), offset + i + 1));
                        }
                    }
                    SQLDataType.INTEGER.getBinding().register(new DefaultBindingRegisterContext(ctx, (CallableStatement)ctx.statement(), rowcountOffset));
                    listener.executeStart(ctx);
                    result = this.executeImmediate(ctx.statement()).executeUpdate();
                    ctx.rows(result);
                    listener.executeEnd(ctx);
                    DSLContext dsl = ((DefaultExecuteContext)ctx).originalConfiguration().dsl();
                    this.returnedResult = dsl.newResult(this.returningResolvedAsterisks);
                    if (this.emulateOracleReturningWithBulkCollectInto(ctx)) {
                        Result[] results = new Result[this.returningResolvedAsterisks.size()];
                        for (int i = 0; i < this.returningResolvedAsterisks.size(); ++i) {
                            DefaultBindingGetStatementContext get2 = new DefaultBindingGetStatementContext(ctx, (CallableStatement)ctx.statement(), offset + i + 2);
                            resultBinding.get(get2);
                            results[i] = (Result)get2.value();
                        }
                        for (int j = 0; j < results[0].size(); ++j) {
                            Record record = dsl.newRecord(this.returningResolvedAsterisks);
                            for (int i = 0; i < this.returningResolvedAsterisks.size(); ++i) {
                                record.set(this.returningResolvedAsterisks.get(i), this.returningResolvedAsterisks.get(i).getDataType().convert(((Record)results[i].get(j)).get(0)));
                            }
                            record.changed(false);
                            this.returnedResult.add(record);
                        }
                    } else {
                        Record record = dsl.newRecord(this.returningResolvedAsterisks);
                        for (int i = 0; i < this.returningResolvedAsterisks.size(); ++i) {
                            DefaultBindingGetStatementContext get3 = new DefaultBindingGetStatementContext(ctx, (CallableStatement)ctx.statement(), offset + i + 1);
                            this.returningResolvedAsterisks.get(i).getBinding().get(get3);
                            record.set(this.returningResolvedAsterisks.get(i), get3.value());
                        }
                        record.changed(false);
                        this.returnedResult.add(record);
                    }
                    DefaultBindingGetStatementContext get4 = new DefaultBindingGetStatementContext(ctx, (CallableStatement)ctx.statement(), rowcountOffset);
                    SQLDataType.INTEGER.getBinding().get(get4);
                    return (Integer)get4.value();
                }
            }
            default: {
                rs = this.executeReturningGeneratedKeys(ctx, listener);
            }
        }
        DefaultExecuteContext ctx2 = new DefaultExecuteContext(((DefaultExecuteContext)ctx).originalConfiguration());
        ExecuteListener listener2 = ExecuteListeners.getAndStart(ctx2);
        ctx2.resultSet(rs);
        this.returnedResult = new CursorImpl(ctx2, listener2, this.returningResolvedAsterisks.toArray(Tools.EMPTY_FIELD), null, false, true).fetch();
        if (!this.returnedResult.isEmpty() || ctx.family() != SQLDialect.HSQLDB) {
            result = Math.max(Math.max(result, ctx.rows()), this.returnedResult.size());
            ctx.rows(result);
        }
        return result;
    }

    private final PreparedStatement executeImmediate(PreparedStatement s2) throws SQLException {
        if (DefaultUnwrapperProvider.DefaultUnwrapper.isWrapperFor(s2, BatchedPreparedStatement.class)) {
            s2.unwrap(BatchedPreparedStatement.class).setExecuteImmediate(true);
        }
        return s2;
    }

    private final ResultSet executeReturningGeneratedKeys(ExecuteContext ctx, ExecuteListener listener) throws SQLException {
        listener.executeStart(ctx);
        int result = this.executeImmediate(ctx.statement()).executeUpdate();
        ctx.rows(result);
        listener.executeEnd(ctx);
        return ctx.statement().getGeneratedKeys();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final int executeReturningGeneratedKeysFetchAdditionalRows(ExecuteContext ctx, ExecuteListener listener) throws SQLException {
        ResultSet rs;
        listener.executeStart(ctx);
        int result = this.executeImmediate(ctx.statement()).executeUpdate();
        ctx.rows(result);
        listener.executeEnd(ctx);
        try {
            rs = ctx.statement().getGeneratedKeys();
        }
        catch (SQLException e) {
            if (ctx.family() == SQLDialect.ASE && "JZ0NK".equals(e.getSQLState())) {
                return result;
            }
            throw e;
        }
        try {
            ArrayList<Object> list = new ArrayList<Object>();
            if (rs != null) {
                while (rs.next()) {
                    list.add(rs.getObject(1));
                }
            }
            this.selectReturning(((DefaultExecuteContext)ctx).originalConfiguration(), ctx.configuration(), list.toArray());
            int n = result;
            return n;
        }
        finally {
            JDBCUtils.safeClose(rs);
        }
    }

    private final ResultSet executeReturningQuery(ExecuteContext ctx, ExecuteListener listener) throws SQLException {
        listener.executeStart(ctx);
        ResultSet rs = ctx.statement().executeQuery();
        listener.executeEnd(ctx);
        return rs;
    }

    private final void selectReturning(Configuration originalConfiguration, Configuration derivedConfiguration, Object ... values2) {
        Field<?> returnIdentity;
        if (values2 != null && values2.length > 0 && (returnIdentity = this.returnedIdentity()) != null) {
            DataType type = returnIdentity.getDataType();
            Object[] ids = Tools.map(values2, v -> type.convert(v), Object[]::new);
            if (this.returningResolvedAsterisks.size() == 1 && new FieldsImpl(this.returningResolvedAsterisks).field(returnIdentity) != null) {
                AbstractRow<?> fields = Tools.row0(this.returningResolvedAsterisks.toArray(Tools.EMPTY_FIELD));
                for (Object id : ids) {
                    this.getResult().add(Tools.newRecord(true, AbstractRecord.class, fields, originalConfiguration).operate(record -> {
                        record.values[0] = id;
                        record.originals[0] = id;
                        return record;
                    }));
                }
            } else {
                this.returnedResult = derivedConfiguration.dsl().select(this.returning).from((TableLike<?>)this.table).where(this.table.field(returnIdentity).in(ids)).fetch();
                this.returnedResult.attach(originalConfiguration);
            }
        }
    }

    private final Field<?> returnedIdentity() {
        return this.table.getIdentity() != null ? this.table.getIdentity().getField() : Tools.findAny(this.returningResolvedAsterisks, f -> f.getDataType().identity());
    }

    public final Field<?>[] getFields(ThrowingSupplier<? extends ResultSetMetaData, SQLException> rs) throws SQLException {
        return this.returningResolvedAsterisks.toArray(Tools.EMPTY_FIELD);
    }

    public final Class<? extends Record> getRecordType() {
        return Tools.recordType(this.returningResolvedAsterisks.size());
    }

    private static /* synthetic */ Condition lambda$accept$3(Table r, Field f) throws RuntimeException {
        return DSL.field(DSL.name(r.getName(), f.getName())).eq(DSL.field(DSL.name("s", f.getName())));
    }
}

