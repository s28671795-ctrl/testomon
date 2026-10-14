/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jooq.impl.SelectQueryImpl$ForAutoEmulation.PathTree
 *  org.jooq.impl.SelectQueryImpl$RownumTransformation.ConditionLimit
 */
package org.jooq.impl;

import java.io.Serializable;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;
import org.jooq.Asterisk;
import org.jooq.Clause;
import org.jooq.CommonTableExpression;
import org.jooq.Comparator;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Generator;
import org.jooq.GeneratorStatementType;
import org.jooq.GroupField;
import org.jooq.JSON;
import org.jooq.JSONB;
import org.jooq.JSONEntry;
import org.jooq.JSONObjectNullStep;
import org.jooq.JSONObjectReturningStep;
import org.jooq.JoinType;
import org.jooq.Name;
import org.jooq.Operator;
import org.jooq.OrderField;
import org.jooq.Param;
import org.jooq.Pro;
import org.jooq.QualifiedAsterisk;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Replacer;
import org.jooq.Result;
import org.jooq.Row;
import org.jooq.SQLDialect;
import org.jooq.Scope;
import org.jooq.Select;
import org.jooq.SelectField;
import org.jooq.SelectFieldOrAsterisk;
import org.jooq.SelectJoinStep;
import org.jooq.SelectLimitPercentStep;
import org.jooq.SelectOffsetStep;
import org.jooq.SelectQuery;
import org.jooq.SelectSeekStepN;
import org.jooq.SelectWithTiesStep;
import org.jooq.SortField;
import org.jooq.SortOrder;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableLike;
import org.jooq.TableOnConditionStep;
import org.jooq.TableOnStep;
import org.jooq.TableOptionalOnStep;
import org.jooq.TablePartitionByStep;
import org.jooq.Traverser;
import org.jooq.Traversers;
import org.jooq.Variable;
import org.jooq.WindowDefinition;
import org.jooq.WindowFinalStep;
import org.jooq.conf.ParamType;
import org.jooq.conf.SettingsTools;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.AbstractContext;
import org.jooq.impl.AbstractField;
import org.jooq.impl.AbstractResultQuery;
import org.jooq.impl.AbstractWindowFunction;
import org.jooq.impl.AliasedSelect;
import org.jooq.impl.And;
import org.jooq.impl.AsteriskImpl;
import org.jooq.impl.CombineOperator;
import org.jooq.impl.CommonTableExpressionImpl;
import org.jooq.impl.CommonTableExpressionList;
import org.jooq.impl.ConditionProviderImpl;
import org.jooq.impl.Convert;
import org.jooq.impl.CustomField;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultGeneratorContext;
import org.jooq.impl.DerivedTable;
import org.jooq.impl.EmptyGroupingSet;
import org.jooq.impl.Eq;
import org.jooq.impl.FieldsImpl;
import org.jooq.impl.ForJSON;
import org.jooq.impl.ForLock;
import org.jooq.impl.ForXML;
import org.jooq.impl.GroupFieldList;
import org.jooq.impl.Gt;
import org.jooq.impl.InlineDerivedTable;
import org.jooq.impl.Internal;
import org.jooq.impl.JSONArrayAgg;
import org.jooq.impl.JSONNull;
import org.jooq.impl.JoinTable;
import org.jooq.impl.Keywords;
import org.jooq.impl.Lateral;
import org.jooq.impl.LazyName;
import org.jooq.impl.Level;
import org.jooq.impl.Limit;
import org.jooq.impl.Lt;
import org.jooq.impl.MetaDataFieldProvider;
import org.jooq.impl.Multiset;
import org.jooq.impl.Names;
import org.jooq.impl.NoCondition;
import org.jooq.impl.NoField;
import org.jooq.impl.Or;
import org.jooq.impl.Patterns;
import org.jooq.impl.PlusJoin;
import org.jooq.impl.QOM;
import org.jooq.impl.QualifiedSelectFieldList;
import org.jooq.impl.QueryPartCollectionView;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.QueryPartListView;
import org.jooq.impl.RowAsField;
import org.jooq.impl.Rownum;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.ScalarSubquery;
import org.jooq.impl.SelectFieldList;
import org.jooq.impl.SelectImpl;
import org.jooq.impl.SelectQueryImpl;
import org.jooq.impl.SortFieldList;
import org.jooq.impl.TableAsField;
import org.jooq.impl.TableImpl;
import org.jooq.impl.TableList;
import org.jooq.impl.ThrowingSupplier;
import org.jooq.impl.Tools;
import org.jooq.impl.TopLevelCte;
import org.jooq.impl.Transformations;
import org.jooq.impl.Val;
import org.jooq.impl.WindowList;
import org.jooq.impl.WithImpl;
import org.jooq.impl.WithTable;
import org.jooq.impl.Xor;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.StringUtils;

final class SelectQueryImpl<R extends Record>
extends AbstractResultQuery<R>
implements SelectQuery<R> {
    private static final JooqLogger log = JooqLogger.getLogger(SelectQueryImpl.class);
    private static final Clause[] CLAUSES = new Clause[]{Clause.SELECT};
    static final Set<SQLDialect> EMULATE_SELECT_INTO_AS_CTAS = SQLDialect.supportedBy(SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB);
    private static final Set<SQLDialect> SUPPORT_SELECT_INTO_TABLE = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB);
    static final Set<SQLDialect> NO_SUPPORT_WINDOW_CLAUSE = SQLDialect.supportedUntil(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD_3_0, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.ORACLE18C, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_28, SQLDialect.SQLSERVER2017, SQLDialect.TERADATA, SQLDialect.VERTICA);
    private static final Set<SQLDialect> OPTIONAL_FROM_CLAUSE = SQLDialect.supportedBy(SQLDialect.ASE, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DEFAULT, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.IGNITE, SQLDialect.MARIADB_10_4, SQLDialect.MYSQL_8_0, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB);
    private static final Set<SQLDialect> REQUIRES_DERIVED_TABLE_DML = SQLDialect.supportedUntil(SQLDialect.AURORA_MYSQL, SQLDialect.MARIADB_10_2, SQLDialect.MEMSQL_6, SQLDialect.MYSQL);
    private static final Set<SQLDialect> NO_IMPLICIT_GROUP_BY_ON_HAVING = SQLDialect.supportedBy(SQLDialect.SQLITE);
    @Pro
    private static final Set<SQLDialect> EMULATE_CONNECT_BY = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB);
    @Pro
    private static final Set<SQLDialect> REQUIRES_FROM_WITH_WHERE = SQLDialect.supportedBy(SQLDialect.BIGQUERY, SQLDialect.EXASOL);
    @Pro
    private static final Set<SQLDialect> REQUIRE_ORDER_BY_WITH_OFFSET = SQLDialect.supportedBy(SQLDialect.EXASOL, SQLDialect.SQLSERVER2012);
    @Pro
    static final Set<SQLDialect> EMULATE_SELECT_INTO_PROJECTION = SQLDialect.supportedBy(SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER);
    @Pro
    static final Set<SQLDialect> NO_SUPPORT_WITH_TABLE = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB);
    private static final Set<SQLDialect> SUPPORT_FULL_WITH_TIES = SQLDialect.supportedBy(SQLDialect.H2, SQLDialect.MARIADB, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_13);
    private static final Set<SQLDialect> EMULATE_DISTINCT_ON = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA);
    static final Set<SQLDialect> NO_SUPPORT_FOR_UPDATE_OF_FIELDS = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB);
    static final Set<SQLDialect> NO_SUPPORT_UNION_ORDER_BY_ALIAS = SQLDialect.supportedBy(SQLDialect.FIREBIRD);
    static final Set<SQLDialect> NO_SUPPORT_WITH_READ_ONLY = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB);
    @Pro
    private static final Set<SQLDialect> SUPPORT_TRAILING_SELECT_INTO = SQLDialect.supportedBy(SQLDialect.INFORMIX, SQLDialect.VERTICA);
    @Pro
    private static final Set<SQLDialect> SUPPORT_TOP = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE);
    @Pro
    private static final Set<SQLDialect> SUPPORT_TOP_SQLSERVER = SQLDialect.supportedBy(SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.TERADATA);
    @Pro
    private static final Set<SQLDialect> EMULATE_FOR_JSON = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB);
    @Pro
    private static final Set<SQLDialect> EMULATE_FOR_XML = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB);
    @Pro
    private static final Set<SQLDialect> WRAP_EXP_BODY_IN_DERIVED_TABLE = SQLDialect.supportedUntil(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.DB2, SQLDialect.MEMSQL, SQLDialect.SYBASE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER2008);
    @Pro
    private static final Set<SQLDialect> REQUIRE_TOP_DERIVED_ORDER_BY = SQLDialect.supportedUntil(SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER2008, SQLDialect.TERADATA);
    final WithImpl with;
    private final SelectFieldList<SelectFieldOrAsterisk> select;
    private Table<?> intoTable;
    private String hint;
    private String option;
    private boolean distinct;
    private final QueryPartList<SelectFieldOrAsterisk> distinctOn;
    private ForLock forLock;
    private boolean withCheckOption;
    private boolean withReadOnly;
    @Pro
    private Collection<? extends Variable<?>> intoVariables;
    @Pro
    private ForXML forXML;
    @Pro
    private ForJSON forJSON;
    @Pro
    private final ConditionProviderImpl connectBy;
    @Pro
    private boolean connectByNoCycle;
    @Pro
    private final ConditionProviderImpl connectByStartWith;
    private final TableList from;
    private final ConditionProviderImpl condition;
    private final GroupFieldList groupBy;
    private boolean groupByDistinct;
    private final ConditionProviderImpl having;
    private WindowList window;
    private final ConditionProviderImpl qualify;
    private final SortFieldList orderBy;
    @Pro
    private boolean orderBySiblings;
    private final QueryPartList<Field<?>> seek;
    private boolean seekBefore;
    private final Limit limit;
    private final List<CombineOperator> unionOp;
    private final List<QueryPartList<Select<?>>> union;
    private final SortFieldList unionOrderBy;
    @Pro
    private boolean unionOrderBySiblings;
    private final QueryPartList<Field<?>> unionSeek;
    private boolean unionSeekBefore;
    private final Limit unionLimit;
    private final Map<QueryPart, QueryPart> localQueryPartMapping;
    private static final Set<SQLDialect> NO_SUPPORT_UNION_PARENTHESES = SQLDialect.supportedBy(SQLDialect.SQLITE);
    private static final Set<SQLDialect> NO_SUPPORT_CTE_IN_UNION = SQLDialect.supportedBy(SQLDialect.DB2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.ORACLE);
    private static final Set<SQLDialect> UNION_PARENTHESIS = SQLDialect.supportedUntil(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.DERBY, SQLDialect.MARIADB_10_3, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0_20);

    SelectQueryImpl(Configuration configuration, WithImpl with) {
        this(configuration, with, null);
    }

    SelectQueryImpl(Configuration configuration, WithImpl with, boolean distinct2) {
        this(configuration, with, null, distinct2);
    }

    SelectQueryImpl(Configuration configuration, WithImpl with, TableLike<? extends R> from) {
        this(configuration, with, from, false);
    }

    SelectQueryImpl(Configuration configuration, WithImpl with, TableLike<? extends R> from, boolean distinct2) {
        super(configuration);
        this.with = with;
        this.distinct = distinct2;
        this.distinctOn = new SelectFieldList<SelectFieldOrAsterisk>();
        this.select = new SelectFieldList();
        this.from = new TableList();
        this.condition = new ConditionProviderImpl();
        this.connectBy = new ConditionProviderImpl();
        this.connectByStartWith = new ConditionProviderImpl();
        this.groupBy = new GroupFieldList();
        this.having = new ConditionProviderImpl();
        this.qualify = new ConditionProviderImpl();
        this.orderBy = new SortFieldList();
        this.seek = new QueryPartList();
        this.limit = new Limit();
        this.unionOp = new ArrayList<CombineOperator>();
        this.union = new ArrayList();
        this.unionOrderBy = new SortFieldList();
        this.unionSeek = new QueryPartList();
        this.unionLimit = new Limit();
        if (from != null) {
            this.from.add(from.asTable());
        }
        this.localQueryPartMapping = new LinkedHashMap<QueryPart, QueryPart>();
    }

    @Pro
    private final boolean stopsAt(CopyClause clause, boolean scalarSelect) {
        return !this.unionLimit.isApplicable() && Tools.isEmpty(this.unionSeek) && !this.unionSeekBefore && !this.unionOrderBySiblings && Tools.isEmpty(this.unionOrderBy) && Tools.isEmpty(this.unionOp) && Tools.isEmpty(this.union) && this.intoTable == null && this.option == null && this.forJSON == null && this.forXML == null && !this.withCheckOption && !this.withReadOnly && this.forLock == null && !this.limit.isApplicable() && Tools.isEmpty(this.seek) && !this.orderBySiblings && Tools.isEmpty(this.orderBy) && Tools.isEmpty(this.distinctOn) && !this.distinct && this.hint == null && scalarSelect && (clause == CopyClause.QUALIFY || !this.qualify.hasWhere() && this.window == null && !this.having.hasWhere() && Tools.isEmpty(this.groupBy) && !this.groupByDistinct && !this.connectBy.hasWhere() && !this.connectByNoCycle && !this.connectByStartWith.hasWhere());
    }

    @Pro
    private final boolean scalarOnly(SelectFieldList<SelectFieldOrAsterisk> s2) {
        return !s2.$traverse(Traversers.recursing(q -> !(q instanceof Select), Traversers.findingAny(q -> q instanceof AbstractWindowFunction))).isPresent();
    }

    private final SelectQueryImpl<R> copyTo(CopyClause clause, boolean scalarSelect, SelectQueryImpl<R> result) {
        return this.copyBetween(CopyClause.START, clause, scalarSelect, result);
    }

    private final SelectQueryImpl<R> copyAfter(CopyClause clause, boolean scalarSelect, SelectQueryImpl<R> result) {
        return this.copyBetween(clause, CopyClause.END, scalarSelect, result);
    }

    private final SelectQueryImpl<R> copyBetween(CopyClause start2, CopyClause end, boolean scalarSelect, SelectQueryImpl<R> result) {
        if (CopyClause.START.between(start2, end)) {
            result.from.addAll(this.from);
            result.condition.setWhere(this.condition.getWhere());
            if (scalarSelect) {
                result.select.addAll(this.select);
            }
        }
        if (CopyClause.WHERE.between(start2, end)) {
            result.connectBy.setWhere(this.connectBy.getWhere());
            result.connectByNoCycle = this.connectByNoCycle;
            result.connectByStartWith.setWhere(this.connectByStartWith.getWhere());
            result.groupBy.addAll(this.groupBy);
            result.groupByDistinct = this.groupByDistinct;
            result.having.setWhere(this.having.getWhere());
            if (this.window != null) {
                result.addWindow0(this.window);
            }
            result.qualify.setWhere(this.qualify.getWhere());
        }
        if (CopyClause.QUALIFY.between(start2, end)) {
            if (!scalarSelect) {
                result.select.addAll(this.select);
            }
            result.hint = this.hint;
            result.distinct = this.distinct;
            result.distinctOn.addAll((Collection<SelectFieldOrAsterisk>)this.distinctOn);
            result.orderBy.addAll(this.orderBy);
            result.orderBySiblings = this.orderBySiblings;
            result.seek.addAll((Collection<Field<?>>)this.seek);
            result.limit.from(this.limit);
            result.forLock = this.forLock;
            result.forJSON = this.forJSON;
            result.forXML = this.forXML;
            result.withCheckOption = this.withCheckOption;
            result.withReadOnly = this.withReadOnly;
            result.option = this.option;
            result.intoTable = this.intoTable;
            result.union.addAll(this.union);
            result.unionOp.addAll(this.unionOp);
            result.unionOrderBy.addAll(this.unionOrderBy);
            result.unionOrderBySiblings = this.unionOrderBySiblings;
            result.unionSeek.addAll((Collection<Field<?>>)this.unionSeek);
            result.unionSeekBefore = this.unionSeekBefore;
            result.unionLimit.from(this.unionLimit);
        }
        return result;
    }

    private final SelectQueryImpl<R> copy(Consumer<? super SelectQueryImpl<R>> finisher) {
        return this.copy(finisher, this.with);
    }

    private final SelectQueryImpl<R> copy(Consumer<? super SelectQueryImpl<R>> finisher, WithImpl newWith) {
        SelectQueryImpl<R> result = this.copyTo(CopyClause.END, false, new SelectQueryImpl<R>(this.configuration(), newWith));
        finisher.accept(result);
        return result;
    }

    @Pro
    private final SelectQueryImpl<R> nestTo(CopyClause clause, boolean optionalNesting, Function<? super SelectQueryImpl<R>, ? extends SelectQueryImpl<R>> nestedFinisher) {
        SelectQueryImpl<R> result = new SelectQueryImpl<R>(this.configuration(), this.with);
        boolean scalarSelect = this.scalarOnly(this.select);
        if (optionalNesting && this.stopsAt(clause, scalarSelect)) {
            return nestedFinisher.apply(this.copyTo(clause, scalarSelect, result));
        }
        return this.copyAfter(clause, false, this.nest(result, this.copyTo(clause, false, new SelectQueryImpl<R>(this.configuration(), null)), nestedFinisher));
    }

    @Pro
    private final SelectQueryImpl<R> nest(SelectQueryImpl<R> result, SelectQueryImpl<R> nested, Function<? super SelectQueryImpl<R>, ? extends SelectQueryImpl<R>> nestedFinisher) {
        List<Field<?>> fields = this.getFrom().fields();
        if (Tools.hasAmbiguousNames(fields)) {
            List<Field<?>> aliased = Tools.aliasedFields(fields);
            aliased.forEach((? super T f) -> result.localQueryPartMapping.put(Tools.aliased(f), DSL.field(f.getUnqualifiedName(), f.getDataType())));
            nested.select.addAll(aliased);
        } else {
            nested.select.add(DSL.asterisk());
        }
        nested = nestedFinisher.apply(nested);
        Table t2 = nested.asTable("t");
        Tools.traverseJoins(this.from, t0 -> result.localQueryPartMapping.put((QueryPart)t0, t2));
        result.from.add(t2);
        return result;
    }

    @Override
    public final <T> Field<T> asField() {
        return new ScalarSubquery(this, Tools.scalarType(this), false);
    }

    @Override
    public final <T> Field<T> asField(String alias) {
        return this.asField().as(alias);
    }

    @Override
    public <T> Field<T> asField(Function<? super Field<T>, ? extends String> aliasFunction) {
        return this.asField().as(aliasFunction);
    }

    @Override
    public final Row fieldsRow() {
        return this.asTable().fieldsRow();
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
        return new DerivedTable(this).as(new LazyName(() -> DSL.name(Tools.autoAlias(this))));
    }

    @Override
    public final Table<R> asTable(String alias) {
        return new DerivedTable(this).as(alias);
    }

    @Override
    public final Table<R> asTable(String alias, String ... fieldAliases) {
        return new DerivedTable(this).as(alias, fieldAliases);
    }

    @Override
    public final Table<R> asTable(String alias, Collection<? extends String> fieldAliases) {
        return new DerivedTable(this).as(alias, fieldAliases);
    }

    @Override
    public final Table<R> asTable(Name alias) {
        return new DerivedTable(this).as(alias);
    }

    @Override
    public final Table<R> asTable(Name alias, Name ... fieldAliases) {
        return new DerivedTable(this).as(alias, fieldAliases);
    }

    @Override
    public final Table<R> asTable(Name alias, Collection<? extends Name> fieldAliases) {
        return new DerivedTable(this).as(alias, fieldAliases);
    }

    @Override
    public final Table<R> asTable(Table<?> alias) {
        return new DerivedTable(this).as(alias);
    }

    @Override
    public final Table<R> asTable(Table<?> alias, Field<?> ... fieldAliases) {
        return new DerivedTable(this).as(alias, fieldAliases);
    }

    @Override
    public final Table<R> asTable(Table<?> alias, Collection<? extends Field<?>> fieldAliases) {
        return new DerivedTable(this).as(alias, fieldAliases);
    }

    @Override
    public final Table<R> asTable(String alias, Function<? super Field<?>, ? extends String> aliasFunction) {
        return new DerivedTable(this).as(alias, aliasFunction);
    }

    @Override
    public final Table<R> asTable(String alias, BiFunction<? super Field<?>, ? super Integer, ? extends String> aliasFunction) {
        return new DerivedTable(this).as(alias, aliasFunction);
    }

    @Override
    public final Field<?>[] getFields(ThrowingSupplier<? extends ResultSetMetaData, SQLException> rs) throws SQLException {
        Field<?>[] fields = this.getFields();
        if (fields.length == 0) {
            return new MetaDataFieldProvider(this.configuration(), rs.get()).getFields();
        }
        return fields;
    }

    @Override
    public final Field<?>[] getFields() {
        Collection<Field<?>> fields = this.coerce();
        if (fields == null || fields.isEmpty()) {
            fields = this.forXML != null ? Arrays.asList(ForXML.FIELD.get()) : (this.forJSON != null ? (this.forJSON.forJSONB ? Arrays.asList(ForJSON.F_JSONB.get()) : Arrays.asList(ForJSON.F_JSON.get())) : this.getSelect());
        }
        return Tools.fieldArray(fields);
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }

    private final Select<?> distinctOnEmulation() {
        ArrayList<Field> partitionBy = new ArrayList<Field>(this.distinctOn.size());
        for (SelectFieldOrAsterisk s2 : this.distinctOn) {
            if (!(s2 instanceof Field)) continue;
            Field f = (Field)s2;
            partitionBy.add(f);
        }
        SelectField rn = DSL.rowNumber().over(DSL.partitionBy(partitionBy).orderBy(this.orderBy)).as("rn");
        SelectQueryImpl<R> copy = this.copy(x -> {});
        copy.distinctOn.clear();
        copy.select.add(rn);
        copy.orderBy.clear();
        copy.limit.clear();
        SelectSeekStepN s1 = DSL.select(new QualifiedSelectFieldList(DSL.table(DSL.name("t")), (Iterable<SelectFieldOrAsterisk>)this.select)).from((TableLike<?>)copy.asTable("t")).where(rn.eq(DSL.one())).orderBy(Tools.map(this.orderBy, o -> Tools.unqualified(o)));
        if (this.limit.limit != null) {
            SelectLimitPercentStep s2 = s1.limit(this.limit.limit);
            SelectWithTiesStep s3 = this.limit.percent ? s2.percent() : s2;
            SelectOffsetStep s4 = this.limit.withTies ? s3.withTies() : s3;
            return this.limit.offset != null ? s4.offset(this.limit.offset) : s4;
        }
        return this.limit.offset != null ? s1.offset(this.limit.offset) : s1;
    }

    @Pro
    private final Select<?> qualifyEmulation() {
        LinkedHashMap w = new LinkedHashMap();
        int[] i = new int[]{1};
        Function<QueryPart, Boolean> consumer = p -> {
            Field field2;
            if (p instanceof Field) {
                Field f;
                v0 = f = (Field)p;
            } else if (p instanceof SortField) {
                SortField s2 = (SortField)p;
                v0 = s2.$field();
            } else {
                v0 = field2 = null;
            }
            if (field2 != null && Tools.isWindow(field2)) {
                int n = i[0];
                i[0] = n + 1;
                w.putIfAbsent(field2, DSL.field(DSL.name("w" + n), field2.getDataType()));
            }
            return false;
        };
        this.select.$traverse(Traverser.of(() -> false, r -> false, p -> !(p instanceof Select), (r, p) -> (Boolean)consumer.apply((QueryPart)p)));
        this.orderBy.$traverse(Traverser.of(() -> false, r -> false, p -> !(p instanceof Select), (r, p) -> (Boolean)consumer.apply((QueryPart)p)));
        if (w.isEmpty() && this.qualify.$traverse(Traverser.of(() -> true, r -> r == false, p -> !(p instanceof Select), (r, p) -> r != false && !Tools.isWindow(p))).booleanValue()) {
            return this.copy(s2 -> {
                s2.having.addConditions(this.qualify.getWhere());
                s2.qualify.setWhere(DSL.noCondition());
            });
        }
        SelectQueryImpl<R> result = this.nestTo(CopyClause.QUALIFY, false, s2 -> {
            s2.qualify.setWhere(DSL.noCondition());
            s2.select.add(this.qualify.getWhere().as("w0"));
            w.forEach((? super K k, ? super V v) -> s2.select.add(k.as((Field)v)));
            return s2;
        });
        List<Field<?>> s1 = this.getSelect();
        List<Field<?>> s22 = result.getSelect();
        result.select.clear();
        result.select.addAll(Tools.map(s22, (f, j) -> {
            Field g2 = (Field)s1.get(j);
            if (g2.getQualifiedName().quoted() == Name.Quoted.SYSTEM) {
                return f;
            }
            return f.as(g2);
        }));
        result.localQueryPartMapping.putAll(w);
        result.addConditions(DSL.condition(DSL.field(DSL.name("w0"), SQLDataType.BOOLEAN)));
        return result;
    }

    @Pro
    private final Select<?> connectByEmulation(Context<?> ctx) {
        SelectQueryImpl<R> result = new SelectQueryImpl<R>(this.configuration(), this.with);
        QOM.CompareCondition c = (QOM.CompareCondition)this.connectBy.getWhere();
        Field<Integer> f1 = ConnectByEmulation.LEVEL.contains(c.$arg1()) ? (c instanceof Lt ? Internal.isub((Field)c.$arg2(), DSL.one()) : (Field<Integer>)c.$arg2()) : (c instanceof Gt ? Internal.isub((Field)c.$arg1(), DSL.one()) : (Field)c.$arg1());
        result.from.add(DSL.generateSeries(DSL.one(), f1).as(DSL.unquotedName(ctx.nextAlias()), Names.N_LEVEL));
        for (SelectFieldOrAsterisk s2 : this.select) {
            if (s2 instanceof Rownum) {
                result.select.add(DSL.level().as(Rownum.NATIVE_SUPPORT.contains((Object)ctx.dialect()) ? DSL.name("ROWNUM") : Names.N_ROWNUM));
                continue;
            }
            result.select.add(s2);
        }
        result.distinct = this.distinct;
        result.orderBy.addAll(this.orderBy);
        result.limit.from(this.limit);
        return result;
    }

    @Pro
    private final boolean canEmulateConnectBy(Context<?> ctx) {
        if (!this.getFrom().isEmpty()) {
            return false;
        }
        if (this.getWhere(ctx).hasWhere()) {
            return false;
        }
        if (this.connectByStartWith.hasWhere()) {
            return false;
        }
        if (this.connectByNoCycle) {
            return false;
        }
        if (!this.getGroupBy().isEmpty()) {
            return false;
        }
        if (this.having.hasWhere()) {
            return false;
        }
        if (this.qualify.hasWhere()) {
            return false;
        }
        Condition condition = this.connectBy.getWhere();
        if (condition instanceof QOM.CompareCondition) {
            QOM.CompareCondition cc = (QOM.CompareCondition)condition;
            return ConnectByEmulation.LEVEL.contains(cc.$arg1()) || ConnectByEmulation.LEVEL.contains(cc.$arg2());
        }
        return false;
    }

    @Override
    public final void accept(Context<?> ctx) {
        List dmlTables;
        Table dmlTable;
        if (ctx.subqueryLevel() == 1 && REQUIRES_DERIVED_TABLE_DML.contains((Object)ctx.dialect()) && !Boolean.TRUE.equals(ctx.data(Tools.BooleanDataKey.DATA_INSERT_SELECT)) && (dmlTable = (Table)ctx.data(Tools.SimpleDataKey.DATA_DML_TARGET_TABLE)) != null && Tools.containsUnaliasedTable(this.getFrom(), dmlTable)) {
            ctx.visit(DSL.select(DSL.asterisk()).from((TableLike<?>)this.asTable("t")));
        } else if (ctx.subqueryLevel() == 1 && ctx.family() == SQLDialect.MARIADB && !Boolean.TRUE.equals(ctx.data(Tools.BooleanDataKey.DATA_INSERT_SELECT)) && ((dmlTable = (Table)ctx.data(Tools.SimpleDataKey.DATA_DML_TARGET_TABLE)) != null && Tools.aliased(dmlTable) != null && Tools.containsUnaliasedTable(this.getFrom(), dmlTable) || (dmlTables = (List)ctx.data(Tools.SimpleDataKey.DATA_DML_USING_TABLES)) != null && Tools.anyMatch(dmlTables, t2 -> Tools.containsUnaliasedTable(this.getFrom(), t2)))) {
            ctx.visit(DSL.select(DSL.asterisk()).from((TableLike<?>)this.asTable("t")));
        } else if (Tools.isNotEmpty(this.distinctOn) && EMULATE_DISTINCT_ON.contains((Object)ctx.dialect())) {
            ctx.visit(this.distinctOnEmulation());
        } else if (this.qualify.hasWhere() && Transformations.transformQualify(ctx.configuration())) {
            ctx.visit(this.qualifyEmulation());
        } else if (this.connectBy.hasWhere() && EMULATE_CONNECT_BY.contains((Object)ctx.dialect()) && this.canEmulateConnectBy(ctx)) {
            ctx.visit(this.connectByEmulation(ctx));
        } else if (this.forXML != null && EMULATE_FOR_XML.contains((Object)ctx.dialect())) {
            ctx.visit(new ForAutoEmulation(SQLDataType.XML, ctx).select());
        } else if (this.forJSON != null && EMULATE_FOR_JSON.contains((Object)ctx.dialect())) {
            ctx.visit(new ForAutoEmulation(this.forJSON.forJSONB ? SQLDataType.JSONB : SQLDataType.JSON, ctx).select());
        } else if (!(this.forXML == null && this.forJSON == null || ctx.subquery())) {
            ctx.visit(DSL.select(new SelectFieldOrAsterisk[]{this.forXML != null ? new ScalarSubquery<JSONB>(this, SQLDataType.XML, false) : (this.forJSON.forJSONB ? new ScalarSubquery<JSONB>(this, SQLDataType.JSONB, false) : new ScalarSubquery<JSON>(this, SQLDataType.JSON, false))}));
        } else if (Transformations.transformRownum(ctx.configuration()) && !Boolean.TRUE.equals(ctx.data(Tools.ExtendedDataKey.DATA_TRANSFORM_ROWNUM_TO_LIMIT))) {
            ctx.data(Tools.ExtendedDataKey.DATA_TRANSFORM_ROWNUM_TO_LIMIT, true, c -> c.visit(new RownumTransformation(ctx).select()));
        } else if (Tools.isNotEmpty(this.intoVariables) && EMULATE_SELECT_INTO_PROJECTION.contains((Object)ctx.dialect()) && !Boolean.TRUE.equals(ctx.data(Tools.ExtendedDataKey.DATA_TRANSFORM_SELECT_INTO_TO_SELECT_ALIASED))) {
            ctx.data(Tools.ExtendedDataKey.DATA_TRANSFORM_SELECT_INTO_TO_SELECT_ALIASED, true, c -> c.visit(new AliasedSelect(this, false, true, false, (Name[])this.intoVariables.stream().map(v -> DSL.unquotedName("@" + String.valueOf(v.getQualifiedName().unquotedName()))).toArray(Name[]::new))));
        } else if (Boolean.TRUE.equals(ctx.data(Tools.ExtendedDataKey.DATA_TRANSFORM_ROWNUM_TO_LIMIT))) {
            ctx.data(Tools.ExtendedDataKey.DATA_TRANSFORM_ROWNUM_TO_LIMIT, null, c -> this.accept0((Context<?>)c));
        } else if (this.withReadOnly && NO_SUPPORT_WITH_READ_ONLY.contains((Object)ctx.dialect())) {
            ctx.visit(this.copy(s2 -> {
                s2.withReadOnly = false;
                if (!(s2.distinct || !s2.groupBy.isEmpty() || s2.having.hasWhere() || s2.limit.isApplicable() || s2.hasUnions())) {
                    s2.union((Select)DSL.select(Tools.map(s2.getSelect(), f -> DSL.inline(null, f))).where((Condition)DSL.falseCondition()));
                }
            }));
        } else {
            this.accept0(ctx);
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql(this.distinct ? "selectDistinct(" : "select(").declareFields(true, c -> c.visit(this.select)).sql(')');
        if (!Tools.isEmpty(this.from)) {
            ctx.formatNewLine().sql(".from(").declareTables(true, c -> c.visit(this.from)).sql(')');
        }
        if (this.condition.hasWhere()) {
            ctx.formatNewLine().sql(".where(").visit(this.condition).sql(')');
        }
        if (this.connectBy.hasWhere()) {
            ctx.formatNewLine().sql(".connectBy(").visit(this.connectBy).sql(')');
        }
        if (this.connectByStartWith.hasWhere()) {
            ctx.formatNewLine().sql(".startWith(").visit(this.condition).sql(')');
        }
        if (!Tools.isEmpty(this.groupBy)) {
            ctx.formatNewLine().sql(this.groupByDistinct ? ".groupByDistinct(" : ".groupBy(").visit(this.groupBy).sql(')');
        }
        if (this.having.hasWhere()) {
            ctx.formatNewLine().sql(".having(").visit(this.condition).sql(')');
        }
        if (!Tools.isEmpty(this.window)) {
            ctx.formatNewLine().sql(".window(").declareWindows(true, c -> c.visit(this.window)).sql(')');
        }
        if (this.qualify.hasWhere()) {
            ctx.formatNewLine().sql(".qualify(").visit(this.qualify).sql(')');
        }
        if (!Tools.isEmpty(this.orderBy)) {
            ctx.formatNewLine().sql(".orderBy(").visit(this.orderBy).sql(')');
        }
        if (this.limit.limit != null) {
            ctx.formatNewLine().sql(".limit(").visit(Val.getJavaValue(this.limit.limit)).sql(')');
        }
        if (this.limit.withTies) {
            ctx.formatNewLine().sql(".withTies()");
        } else if (this.limit.percent) {
            ctx.formatNewLine().sql(".percent()");
        }
        if (this.limit.offset != null) {
            ctx.formatNewLine().sql(".offset(").visit(Val.getJavaValue(this.limit.offset)).sql(')');
        }
        if (this.hasUnions()) {
            for (int i = 0; i < this.unionOp.size(); ++i) {
                for (Select select : this.union.get(i)) {
                    ctx.formatNewLine().sql('.').sql(Tools.camelCase(this.unionOp.get(i).name())).sqlIndentStart('(').visit(select).sqlIndentEnd(')');
                }
            }
        }
        if (this.forLock != null) {
            ctx.formatNewLine();
            switch (this.forLock.forLockMode) {
                case UPDATE: {
                    ctx.sql(".forUpdate()");
                    break;
                }
                case KEY_SHARE: {
                    ctx.sql(".forKeyShare()");
                    break;
                }
                case NO_KEY_UPDATE: {
                    ctx.sql(".forNoKeyUpdate()");
                    break;
                }
                case SHARE: {
                    ctx.sql(".forShare()");
                }
            }
            if (!Tools.isEmpty(this.forLock.forLockOfTables)) {
                ctx.sql(".of(").visit(this.forLock.forLockOfTables).sql(')');
            } else if (!Tools.isEmpty(this.forLock.forLockOf)) {
                ctx.sql(".of(").visit(this.forLock.forLockOf).sql(')');
            }
            if (this.forLock.forLockWaitMode != null) {
                switch (this.forLock.forLockWaitMode) {
                    case NOWAIT: {
                        ctx.sql(".noWait()");
                        break;
                    }
                    case SKIP_LOCKED: {
                        ctx.sql(".skipLocked()");
                        break;
                    }
                    case WAIT: {
                        ctx.sql(".wait(").sql(this.forLock.forLockWait).sql(')');
                    }
                }
            }
        } else if (this.forXML != null) {
            ctx.formatNewLine().sql(".forXML()");
            if (this.forXML.forXMLRaw) {
                if (this.forXML.forXMLRawElement != null) {
                    ctx.sql(".raw(").sql(Tools.stringLiteral(this.forXML.forXMLRawElement)).sql(')');
                } else {
                    ctx.sql(".raw()");
                }
            } else if (this.forXML.forXMLAuto) {
                ctx.sql(".auto()");
            } else if (this.forXML.forXMLPath) {
                if (this.forXML.forXMLPathElement != null) {
                    ctx.sql(".path(").sql(Tools.stringLiteral(this.forXML.forXMLPathElement)).sql(')');
                } else {
                    ctx.sql(".path()");
                }
            } else if (this.forXML.forXMLExplicit) {
                ctx.sql(".explicit()");
            }
            if (this.forXML.forXMLElements) {
                ctx.sql(".elements()");
            } else if (this.forXML.forXMLElementsXsinil) {
                ctx.sql(".elementsXsinil()");
            } else if (this.forXML.forXMLElementsAbsent) {
                ctx.sql(".elementsAbsent()");
            }
            if (this.forXML.forXMLRoot) {
                if (this.forXML.forXMLRootElement != null) {
                    ctx.sql(".root(").sql(Tools.stringLiteral(this.forXML.forXMLRootElement)).sql(')');
                } else {
                    ctx.sql(".root()");
                }
            }
            if (this.forXML.forXMLBinaryBase64) {
                ctx.sql(".binaryBase64()");
            }
            if (this.forXML.forXMLType) {
                ctx.sql(".type()");
            }
        } else if (this.forJSON != null) {
            ctx.formatNewLine().sql(this.forJSON.forJSONB ? ".forJSONB()" : ".forJSON()");
            if (this.forJSON.forJSONAuto) {
                ctx.sql(".auto()");
            } else if (this.forJSON.forJSONPath) {
                ctx.sql(".path()");
            }
            if (this.forJSON.forJSONRoot) {
                if (this.forJSON.forJSONRootElement != null) {
                    ctx.sql(".root(").sql(Tools.stringLiteral(this.forJSON.forJSONRootElement)).sql(')');
                } else {
                    ctx.sql(".root()");
                }
            }
            if (this.forJSON.forJSONIncludeNullValues) {
                ctx.sql(".includeNullValues()");
            }
            if (this.forJSON.forJSONWithoutArrayWrapper) {
                ctx.sql(".withoutArrayWrapper()");
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    final void accept0(Context<?> context) {
        if (context.family() == SQLDialect.JAVA) {
            this.acceptJava(context);
            return;
        }
        boolean topLevelCte = false;
        if (context.subqueryLevel() == 0) {
            context.scopeStart();
            if (topLevelCte |= context.data(Tools.SimpleDataKey.DATA_TOP_LEVEL_CTE) == null) {
                context.data(Tools.SimpleDataKey.DATA_TOP_LEVEL_CTE, new TopLevelCte());
            }
        }
        SQLDialect dialect = context.dialect();
        switch (SettingsTools.getRenderTable(context.settings())) {
            case NEVER: {
                context.data(Tools.ExtendedDataKey.DATA_RENDER_TABLE, false);
                break;
            }
            case WHEN_MULTIPLE_TABLES: {
                if (!this.knownTableSource() || this.getFrom().size() >= 2) break;
                context.data(Tools.ExtendedDataKey.DATA_RENDER_TABLE, false);
                break;
            }
            case WHEN_AMBIGUOUS_COLUMNS: {
                if (!this.knownTableSource() || Tools.hasAmbiguousNames(this.getSelect())) break;
                context.data(Tools.ExtendedDataKey.DATA_RENDER_TABLE, false);
            }
        }
        Object renderTrailingLimit = context.data(Tools.BooleanDataKey.DATA_RENDER_TRAILING_LIMIT_IF_APPLICABLE);
        Name[] selectAliases = (Name[])context.data(Tools.SimpleDataKey.DATA_SELECT_ALIASES);
        try {
            List<Field<?>> originalFields = null;
            List<Field<?>> alternativeFields = null;
            if (selectAliases != null) {
                context.data().remove(Tools.SimpleDataKey.DATA_SELECT_ALIASES);
                originalFields = this.getSelect();
                alternativeFields = Tools.map(originalFields, (f, i) -> i < selectAliases.length ? f.as(selectAliases[i]) : f);
            }
            if (Boolean.TRUE.equals(renderTrailingLimit)) {
                context.data().remove(Tools.BooleanDataKey.DATA_RENDER_TRAILING_LIMIT_IF_APPLICABLE);
            }
            if (this.intoTable != null && !Boolean.TRUE.equals(context.data(Tools.BooleanDataKey.DATA_OMIT_INTO_CLAUSE)) && EMULATE_SELECT_INTO_AS_CTAS.contains((Object)dialect)) {
                context.data(Tools.BooleanDataKey.DATA_OMIT_INTO_CLAUSE, true, c -> c.visit(DSL.createTable(this.intoTable).as(this)));
                return;
            }
            if (this.with != null) {
                context.visit(this.with);
            } else if (topLevelCte) {
                CommonTableExpressionList.markTopLevelCteAndAccept(context, c -> {});
            }
            this.pushWindow(context);
            Boolean wrapDerivedTables = (Boolean)context.data(Tools.BooleanDataKey.DATA_WRAP_DERIVED_TABLES_IN_PARENTHESES);
            if (Boolean.TRUE.equals(wrapDerivedTables)) {
                context.sqlIndentStart('(').data().remove(Tools.BooleanDataKey.DATA_WRAP_DERIVED_TABLES_IN_PARENTHESES);
            }
            switch (context.family()) {
                case ORACLE: {
                    if (SQLDialect.ORACLE20C.precedes(dialect)) {
                        this.toSQLReferenceLimitDefault(context, originalFields, alternativeFields);
                        break;
                    }
                    if (!(!SQLDialect.ORACLE12C.precedes(dialect) || this.getLimit().isApplicable() && Tools.hasAmbiguousNames(this.getSelect()))) {
                        this.toSQLReferenceLimitDefault(context, originalFields, alternativeFields);
                        break;
                    }
                    if (this.getLimit().isApplicable()) {
                        if (this.getLimit().withTies()) {
                            this.toSQLReferenceLimitWithWindowFunctions(context);
                            break;
                        }
                        this.toSQLReferenceLimitOracle10and11(context);
                        break;
                    }
                    this.toSQLReference0(context, originalFields, alternativeFields);
                    break;
                }
                case DB2: {
                    if (this.getLimit().isApplicable()) {
                        if (this.getLimit().withTies() || this.getLimit().percent() || !this.getLimit().offsetZero() && dialect.precedes(SQLDialect.DB2_10) || this.getLimit().isSubquery()) {
                            this.toSQLReferenceLimitWithWindowFunctions(context);
                            break;
                        }
                        this.toSQLReferenceLimitDefault(context, originalFields, alternativeFields);
                        break;
                    }
                    this.toSQLReference0(context, originalFields, alternativeFields);
                    break;
                }
                case ACCESS: 
                case ASE: 
                case SQLDATAWAREHOUSE: 
                case TERADATA: {
                    if (this.topSupported(context)) {
                        this.toSQLReference0(context, originalFields, alternativeFields);
                        break;
                    }
                    if (this.getLimit().isApplicable()) {
                        this.toSQLReferenceLimitWithWindowFunctions(context);
                        break;
                    }
                    this.toSQLReference0(context, originalFields, alternativeFields);
                    break;
                }
                case SQLSERVER: {
                    if (this.topSupported(context) && this.unionOp.isEmpty()) {
                        this.toSQLReference0(context, originalFields, alternativeFields);
                        break;
                    }
                    if (this.getLimit().isApplicable() && (this.getLimit().withTies() || dialect.precedes(SQLDialect.SQLSERVER2008))) {
                        this.toSQLReferenceLimitWithWindowFunctions(context);
                        break;
                    }
                    this.toSQLReferenceLimitDefault(context, originalFields, alternativeFields);
                    break;
                }
                case BIGQUERY: 
                case HANA: 
                case SNOWFLAKE: {
                    if (this.getLimit().isApplicable() && (this.getLimit().withTies() || this.getLimit().isExpression())) {
                        this.toSQLReferenceLimitWithWindowFunctions(context);
                        break;
                    }
                    this.toSQLReferenceLimitDefault(context, originalFields, alternativeFields);
                    break;
                }
                case INFORMIX: 
                case SYBASE: {
                    if (this.getLimit().withTies() || this.getLimit().isExpression()) {
                        this.toSQLReferenceLimitWithWindowFunctions(context);
                        break;
                    }
                    this.toSQLReference0(context, originalFields, alternativeFields);
                    break;
                }
                case MARIADB: {
                    if (this.getLimit().isApplicable() && this.getLimit().isExpression()) {
                        this.toSQLReferenceLimitWithWindowFunctions(context);
                        break;
                    }
                    if (this.getLimit().isApplicable() && this.getLimit().withTies() && dialect.precedes(SQLDialect.MARIADB_10_5)) {
                        this.toSQLReferenceLimitWithWindowFunctions(context);
                        break;
                    }
                    this.toSQLReferenceLimitDefault(context, originalFields, alternativeFields);
                    break;
                }
                case POSTGRES: {
                    if (this.getLimit().isApplicable() && this.getLimit().withTies() && dialect.precedes(SQLDialect.POSTGRES_12)) {
                        this.toSQLReferenceLimitWithWindowFunctions(context);
                        break;
                    }
                    this.toSQLReferenceLimitDefault(context, originalFields, alternativeFields);
                    break;
                }
                case AURORA_MYSQL: 
                case EXASOL: 
                case VERTICA: 
                case FIREBIRD: 
                case MYSQL: {
                    if (this.getLimit().isApplicable() && (this.getLimit().withTies() || this.getLimit().isExpression())) {
                        this.toSQLReferenceLimitWithWindowFunctions(context);
                        break;
                    }
                    this.toSQLReferenceLimitDefault(context, originalFields, alternativeFields);
                    break;
                }
                case AURORA_POSTGRES: 
                case COCKROACHDB: 
                case REDSHIFT: 
                case MEMSQL: 
                case CUBRID: 
                case YUGABYTEDB: {
                    if (this.getLimit().isApplicable() && this.getLimit().withTies()) {
                        this.toSQLReferenceLimitWithWindowFunctions(context);
                        break;
                    }
                    this.toSQLReferenceLimitDefault(context, originalFields, alternativeFields);
                    break;
                }
                default: {
                    this.toSQLReferenceLimitDefault(context, originalFields, alternativeFields);
                }
            }
            if (this.forLock != null) {
                context.visit(this.forLock);
            } else if (this.withCheckOption) {
                context.formatSeparator().visit(Keywords.K_WITH_CHECK_OPTION);
            } else if (this.withReadOnly && !NO_SUPPORT_WITH_READ_ONLY.contains((Object)context.dialect())) {
                context.formatSeparator().visit(Keywords.K_WITH_READ_ONLY);
            } else {
                WithTable.ScopeWithTableForUpdate wt = (WithTable.ScopeWithTableForUpdate)context.data(Tools.ExtendedDataKey.DATA_SCOPE_WITH_TABLE_HINTS);
                if (wt != null && wt.active) {
                    context.visit(wt);
                }
            }
            if (this.forXML != null && !EMULATE_FOR_XML.contains((Object)context.dialect())) {
                context.formatSeparator().visit(this.forXML);
            } else if (this.forJSON != null && !EMULATE_FOR_JSON.contains((Object)context.dialect())) {
                context.formatSeparator().visit(this.forJSON);
            }
            if (!StringUtils.isBlank(this.option)) {
                context.formatSeparator().sql(this.option);
            }
            if (Boolean.TRUE.equals(wrapDerivedTables)) {
                context.sqlIndentEnd(')').data(Tools.BooleanDataKey.DATA_WRAP_DERIVED_TABLES_IN_PARENTHESES, true);
            }
            if (SUPPORT_TRAILING_SELECT_INTO.contains((Object)dialect)) {
                context.start(Clause.SELECT_INTO);
                Table<?> actualInto = (Table<?>)context.data(Tools.SimpleDataKey.DATA_SELECT_INTO_TABLE);
                if (actualInto == null) {
                    actualInto = this.intoTable;
                }
                if (actualInto != null && !Boolean.TRUE.equals(context.data(Tools.BooleanDataKey.DATA_OMIT_INTO_CLAUSE))) {
                    context.formatSeparator().visit(Keywords.K_INTO).sql(' ').visit(actualInto);
                }
                context.end(Clause.SELECT_INTO);
            }
        }
        finally {
            if (renderTrailingLimit != null) {
                context.data(Tools.BooleanDataKey.DATA_RENDER_TRAILING_LIMIT_IF_APPLICABLE, renderTrailingLimit);
            }
            if (selectAliases != null) {
                context.data(Tools.SimpleDataKey.DATA_SELECT_ALIASES, selectAliases);
            }
        }
        if (context.subqueryLevel() == 0) {
            context.scopeEnd();
        }
    }

    @Pro
    private final boolean topSupported(Context<?> context) {
        switch (context.family()) {
            case TERADATA: {
                return this.getLimit().offsetZero() && !this.distinct && this.getLimit().limit instanceof Param;
            }
        }
        return this.getLimit().offsetZero();
    }

    private final void pushWindow(Context<?> context) {
        if (Tools.isNotEmpty(this.window)) {
            context.data(Tools.SimpleDataKey.DATA_WINDOW_DEFINITIONS, this.window);
        }
    }

    private final void toSQLReferenceLimitDefault(Context<?> context, List<Field<?>> originalFields, List<Field<?>> alternativeFields) {
        context.data(Tools.BooleanDataKey.DATA_RENDER_TRAILING_LIMIT_IF_APPLICABLE, true, c -> this.toSQLReference0(context, originalFields, alternativeFields));
    }

    private final void toSQLReferenceLimitWithWindowFunctions(Context<?> ctx) {
        List<Field<?>> originalFields = this.getSelect();
        ArrayList alternativeFields = new ArrayList(originalFields.size());
        if (originalFields.isEmpty()) {
            alternativeFields.add(DSL.field("*"));
        } else {
            alternativeFields.addAll(Tools.aliasedFields(originalFields));
        }
        alternativeFields.add((Field<?>)CustomField.of("rn", SQLDataType.INTEGER, c -> {
            boolean wrapQueryExpressionBodyInDerivedTable = this.wrapQueryExpressionBodyInDerivedTable((Context<?>)c);
            c.data(Tools.BooleanDataKey.DATA_UNALIAS_ALIASED_EXPRESSIONS, !wrapQueryExpressionBodyInDerivedTable);
            boolean q = c.qualify();
            c.data(Tools.SimpleDataKey.DATA_OVERRIDE_ALIASES_IN_ORDER_BY, new Object[]{originalFields, alternativeFields});
            if (wrapQueryExpressionBodyInDerivedTable) {
                c.qualify(false);
            }
            c.visit(this.distinct ? DSL.denseRank().over(DSL.orderBy(this.getNonEmptyOrderByForDistinct(c.configuration()))) : (this.getLimit().withTies() ? DSL.rank().over(DSL.orderBy(this.getNonEmptyOrderBy(c.configuration()))) : DSL.rowNumber().over(DSL.orderBy(this.getNonEmptyOrderBy(c.configuration())))));
            c.data().remove(Tools.BooleanDataKey.DATA_UNALIAS_ALIASED_EXPRESSIONS);
            c.data().remove(Tools.SimpleDataKey.DATA_OVERRIDE_ALIASES_IN_ORDER_BY);
            if (wrapQueryExpressionBodyInDerivedTable) {
                c.qualify(q);
            }
        }).as("rn"));
        List<Field<?>> unaliasedFields = Tools.unaliasedFields(originalFields);
        ctx.visit(Keywords.K_SELECT).separatorRequired(true).declareFields(true, c -> c.visit(new SelectFieldList(unaliasedFields))).formatSeparator().visit(Keywords.K_FROM).sqlIndentStart(" (").subquery(true);
        this.toSQLReference0(ctx, originalFields, alternativeFields);
        ctx.subquery(false).sqlIndentEnd(") ").visit(DSL.name("x")).formatSeparator().visit(Keywords.K_WHERE).sql(' ').visit(DSL.name("rn")).sql(" > ").visit(this.getLimit().getLowerRownum());
        if (!this.getLimit().limitZero()) {
            ctx.formatSeparator().visit(Keywords.K_AND).sql(' ').visit(DSL.name("rn")).sql(" <= ").visit(this.getLimit().getUpperRownum());
        }
        if (!(ctx.subquery() || this.getOrderBy().isEmpty() || Boolean.FALSE.equals(ctx.settings().isRenderOrderByRownumberForEmulatedPagination()))) {
            ctx.formatSeparator().visit(Keywords.K_ORDER_BY).sql(' ').visit(DSL.name("rn"));
        }
    }

    @Pro
    private final void toSQLReferenceLimitOracle10and11(Context<?> ctx) {
        List<Field<?>> originalFields = this.getSelect();
        List<Field<?>> alternativeFields = Tools.aliasedFields(originalFields);
        ArrayList<SelectField> qualifiedAlternativeFields = new ArrayList<SelectField>(alternativeFields.size() + 1);
        if (originalFields.isEmpty()) {
            qualifiedAlternativeFields.add(DSL.field("{0}.*", DSL.name("x")));
        } else {
            qualifiedAlternativeFields.addAll(Arrays.asList(Tools.fieldsByName(DSL.name("x"), originalFields.size())));
        }
        qualifiedAlternativeFields.add(DSL.rownum().as("rn"));
        List<Field<?>> unaliasedFields = Tools.unaliasedFields(originalFields);
        ctx.visit(Keywords.K_SELECT).sql(' ').declareFields(true, c -> c.visit(new SelectFieldList(unaliasedFields))).formatSeparator().visit(Keywords.K_FROM).sqlIndentStart(" (").visit(Keywords.K_SELECT).sql(' ').declareFields(true, c -> c.visit(new SelectFieldList(qualifiedAlternativeFields))).formatSeparator().visit(Keywords.K_FROM).sqlIndentStart(" (");
        if (ctx.executeContext() != null) {
            ctx.executeContext().data(Tools.BooleanDataKey.DATA_MULTISET_CONTENT, true);
        }
        ctx.data(Tools.BooleanDataKey.DATA_MULTISET_CONTENT, true, c -> this.toSQLReference0((Context<?>)c, originalFields, alternativeFields));
        ctx.sqlIndentEnd(") ").visit(DSL.name("x"));
        if (!this.getLimit().limitZero()) {
            ctx.formatSeparator().visit(Keywords.K_WHERE).sql(' ').visit(DSL.rownum()).sql(" <= ").visit(this.getLimit().getUpperRownum());
        }
        ctx.sqlIndentEnd(")").formatSeparator().visit(Keywords.K_WHERE).sql(' ').visit(DSL.name("rn")).sql(" > ").visit(this.getLimit().getLowerRownum());
        if (!(ctx.subquery() || this.getOrderBy().isEmpty() || Boolean.FALSE.equals(ctx.settings().isRenderOrderByRownumberForEmulatedPagination()))) {
            ctx.formatSeparator().visit(Keywords.K_ORDER_BY).sql(' ').visit(DSL.name("rn"));
        }
    }

    /*
     * WARNING - void declaration
     */
    private final void toSQLReference0(Context<?> context, List<Field<?>> originalFields, List<Field<?>> alternativeFields) {
        boolean wrapQueryExpressionBodyInDerivedTable;
        boolean wrapQueryExpressionInDerivedTable;
        SQLDialect family = context.family();
        boolean qualify = context.qualify();
        int unionOpSize = this.unionOp.size();
        boolean unionParensRequired = false;
        boolean unionOpNesting = false;
        boolean applySeekOnDerivedTable = this.applySeekOnDerivedTable();
        boolean bl = wrapQueryExpressionInDerivedTable = !(family != SQLDialect.INFORMIX && family != SQLDialect.TERADATA || !context.subquery() || !this.getLimit().isApplicable() && this.getOrderBy().isEmpty());
        if (wrapQueryExpressionInDerivedTable) {
            context.visit(Keywords.K_SELECT).sql(" *").formatSeparator().visit(Keywords.K_FROM).sql(" (").formatIndentStart().formatNewLine();
        }
        boolean bl2 = wrapQueryExpressionBodyInDerivedTable = this.wrapQueryExpressionBodyInDerivedTable(context) || applySeekOnDerivedTable;
        if (wrapQueryExpressionBodyInDerivedTable) {
            context.visit(Keywords.K_SELECT).sql(' ');
            this.toSQLTopClause(context);
            context.formatIndentStart().formatNewLine().sql("t.*");
            if (alternativeFields != null && originalFields.size() < alternativeFields.size()) {
                context.sql(", ").formatSeparator().declareFields(true, c -> c.visit((Field)alternativeFields.get(alternativeFields.size() - 1)));
            }
            context.formatIndentEnd().formatSeparator().visit(Keywords.K_FROM).sql(" (").formatIndentStart().formatNewLine();
        }
        if (unionOpSize > 0) {
            if (!Boolean.TRUE.equals(context.data(Tools.BooleanDataKey.DATA_NESTED_SET_OPERATIONS))) {
                unionOpNesting = this.unionOpNesting();
                context.data(Tools.BooleanDataKey.DATA_NESTED_SET_OPERATIONS, unionOpNesting);
            }
            for (int i = unionOpSize - 1; i >= 0; --i) {
                switch (this.unionOp.get(i)) {
                    case EXCEPT: {
                        context.start(Clause.SELECT_EXCEPT);
                        break;
                    }
                    case EXCEPT_ALL: {
                        context.start(Clause.SELECT_EXCEPT_ALL);
                        break;
                    }
                    case INTERSECT: {
                        context.start(Clause.SELECT_INTERSECT);
                        break;
                    }
                    case INTERSECT_ALL: {
                        context.start(Clause.SELECT_INTERSECT_ALL);
                        break;
                    }
                    case UNION: {
                        context.start(Clause.SELECT_UNION);
                        break;
                    }
                    case UNION_ALL: {
                        context.start(Clause.SELECT_UNION_ALL);
                    }
                }
                unionParensRequired = unionOpNesting || this.unionParensRequired(context);
                this.unionParenthesis(context, '(', alternativeFields != null ? alternativeFields : this.getSelect(), this.derivedTableRequired(context, this), unionParensRequired);
            }
        }
        Tools.traverseJoins(this.getFrom(), t2 -> {
            if (t2 instanceof TableImpl) {
                context.scopeRegister((QueryPart)t2, true);
            }
        });
        if (this.with != null && Transformations.transformInlineCTE(context.configuration())) {
            for (CommonTableExpression cte : this.with.ctes) {
                context.scopeRegister(cte, false, ((CommonTableExpressionImpl)cte).asDerivedTable());
            }
        }
        for (Map.Entry<QueryPart, QueryPart> entry : this.localQueryPartMapping.entrySet()) {
            context.scopeRegister(entry.getKey(), true, entry.getValue());
        }
        context.start(Clause.SELECT_SELECT).visit(Keywords.K_SELECT).separatorRequired(true);
        if (!StringUtils.isBlank(this.hint)) {
            context.sql(' ').sql(this.hint).separatorRequired(true);
        }
        if (context.data(Tools.SimpleDataKey.DATA_MATERIALIZED_CTE) != null) {
            if (QOM.Materialized.MATERIALIZED.equals(context.data(Tools.SimpleDataKey.DATA_MATERIALIZED_CTE))) {
                context.sql(' ').sql("/*+ ").visit(Keywords.K_MATERIALIZE).sql(" */").separatorRequired(true);
            } else {
                context.sql(' ').sql("/*+ ").visit(Keywords.K_INLINE).sql(" */").separatorRequired(true);
            }
        }
        if (family == SQLDialect.INFORMIX && this.getLimit().isApplicable() && !this.getLimit().withTies() && !this.getLimit().percent()) {
            context.visit(this.getLimit()).separatorRequired(true);
        }
        if (Tools.isNotEmpty(this.distinctOn)) {
            context.visit(Keywords.K_DISTINCT_ON).sql(" (").visit(this.distinctOn).sql(')').separatorRequired(true);
        } else if (this.distinct) {
            context.visit(Keywords.K_DISTINCT).separatorRequired(true);
        }
        if (!wrapQueryExpressionBodyInDerivedTable) {
            this.toSQLTopClause(context);
        }
        if (Integer.valueOf(1).equals(context.data(Tools.SimpleDataKey.DATA_RENDERING_DATA_CHANGE_DELTA_TABLE))) {
            context.qualify(false);
        }
        context.declareFields(true);
        if (alternativeFields != null) {
            if (wrapQueryExpressionBodyInDerivedTable && originalFields.size() < alternativeFields.size()) {
                context.visit(new SelectFieldList((Iterable<Field<?>>)alternativeFields.subList(0, originalFields.size())));
            } else {
                context.visit(new SelectFieldList((Iterable<Field<?>>)alternativeFields));
            }
        } else {
            context.visit(this.getSelectResolveUnsupportedAsterisks(context));
        }
        if (Integer.valueOf(1).equals(context.data(Tools.SimpleDataKey.DATA_RENDERING_DATA_CHANGE_DELTA_TABLE))) {
            context.qualify(qualify);
        }
        context.declareFields(false).end(Clause.SELECT_SELECT);
        if (!context.subquery()) {
            context.start(Clause.SELECT_INTO);
            Table<?> actualIntoTable = (Table<?>)context.data(Tools.SimpleDataKey.DATA_SELECT_INTO_TABLE);
            if (actualIntoTable == null) {
                actualIntoTable = (QueryPart)context.data(Tools.SimpleDataKey.DATA_SELECT_INTO_VARIABLES);
            }
            if (actualIntoTable == null) {
                actualIntoTable = this.intoTable;
            }
            if (!(actualIntoTable == null || Boolean.TRUE.equals(context.data(Tools.BooleanDataKey.DATA_OMIT_INTO_CLAUSE)) || !SUPPORT_SELECT_INTO_TABLE.contains((Object)context.dialect()) && actualIntoTable instanceof Table || SUPPORT_TRAILING_SELECT_INTO.contains((Object)context.dialect()))) {
                context.formatSeparator().visit(Keywords.K_INTO).sql(' ').visit(actualIntoTable);
            } else if (Tools.isNotEmpty(this.intoVariables) && !EMULATE_SELECT_INTO_PROJECTION.contains((Object)context.dialect())) {
                context.formatSeparator().visit(Keywords.K_INTO).sql(' ').visit(QueryPartCollectionView.wrap(this.intoVariables));
            }
            context.end(Clause.SELECT_INTO);
        }
        context.start(Clause.SELECT_FROM).declareTables(true);
        boolean hasFrom = !this.getFrom().isEmpty() || !OPTIONAL_FROM_CLAUSE.contains((Object)context.dialect()) || REQUIRES_FROM_WITH_WHERE.contains((Object)context.dialect()) && (this.distinct || this.getWhere(context).hasWhere() || this.getConnectByStartWith().hasWhere() || this.getConnectBy().hasWhere() || !this.getGroupBy().isEmpty() || this.getHaving().hasWhere() || this.getQualify().hasWhere() || !this.getOrderBy().isEmpty());
        List semiAntiJoinPredicates = null;
        ConditionProviderImpl where = this.getWhere(context);
        if (hasFrom) {
            Object previousCollect = context.data(Tools.BooleanDataKey.DATA_COLLECT_SEMI_ANTI_JOIN, true);
            Object previousCollected = context.data(Tools.SimpleDataKey.DATA_COLLECTED_SEMI_ANTI_JOIN, null);
            TableList tablelist = this.getFrom();
            if (Boolean.TRUE.equals(context.settings().isTransformTableListsToAnsiJoin()) && tablelist.size() > 1) {
                tablelist = this.transformTableListsToAnsiJoin(tablelist, where);
            } else if (Boolean.TRUE.equals(context.settings().isTransformAnsiJoinToTableLists())) {
                tablelist = this.transformAnsiJoinToTableLists(tablelist, where);
            }
            Object var18_22 = null;
            if (this.forLock != null && ForLock.EMULATE_FOR_UPDATE.contains((Object)context.dialect())) {
                ForLock forLock = (ForLock)context.data(Tools.ExtendedDataKey.DATA_FOR_LOCK, this.forLock);
            }
            tablelist = this.transformLateralToCrossApply(context, tablelist);
            tablelist = this.transformInlineDerivedTables(tablelist, where);
            context.formatSeparator().visit(Keywords.K_FROM).separatorRequired(true).visit(tablelist);
            if (this.forLock != null && ForLock.EMULATE_FOR_UPDATE.contains((Object)context.dialect())) {
                void var18_24;
                context.data(Tools.ExtendedDataKey.DATA_FOR_LOCK, var18_24);
            }
            if (!this.groupBy.isEmpty() && Tools.allMatch(this.groupBy, g2 -> g2 == EmptyGroupingSet.INSTANCE)) {
                if (EmptyGroupingSet.EMULATE_EMPTY_GROUP_BY_SUBQ.contains((Object)context.dialect())) {
                    context.sql(", (select 1 as dual) as empty_grouping_dummy_table");
                } else if (family == SQLDialect.INFORMIX) {
                    context.sql(", (select 1 as dual from systables where tabid = 1) as empty_grouping_dummy_table");
                } else if (family == SQLDialect.ACCESS) {
                    context.sql(", (select count(*) dual from MSysResources) as empty_grouping_dummy_table");
                }
            }
            semiAntiJoinPredicates = (List)context.data(Tools.SimpleDataKey.DATA_COLLECTED_SEMI_ANTI_JOIN, previousCollected);
            context.data(Tools.BooleanDataKey.DATA_COLLECT_SEMI_ANTI_JOIN, previousCollect);
        }
        context.declareTables(false).end(Clause.SELECT_FROM);
        context.start(Clause.SELECT_WHERE);
        if (Boolean.TRUE.equals(context.data().get(Tools.BooleanDataKey.DATA_SELECT_NO_DATA))) {
            context.formatSeparator().visit(Keywords.K_WHERE).sql(' ').visit(DSL.falseCondition());
        } else if (where.hasWhere() || semiAntiJoinPredicates != null || Boolean.TRUE.equals(context.data().get(Tools.BooleanDataKey.DATA_MANDATORY_WHERE_CLAUSE))) {
            ConditionProviderImpl actual = new ConditionProviderImpl();
            if (semiAntiJoinPredicates != null) {
                actual.addConditions(semiAntiJoinPredicates);
            }
            if (where.hasWhere()) {
                actual.addConditions(where.getWhere());
            }
            context.formatSeparator().visit(Keywords.K_WHERE).sql(' ').visit(actual);
        }
        context.end(Clause.SELECT_WHERE);
        context.start(Clause.SELECT_START_WITH);
        if (this.getConnectByStartWith().hasWhere()) {
            context.paramTypeIf(ParamType.INLINED, context.family() == SQLDialect.EXASOL, c -> c.formatSeparator().visit(Keywords.K_START_WITH).sql(' ').visit(this.getConnectByStartWith()));
        }
        context.end(Clause.SELECT_START_WITH);
        context.start(Clause.SELECT_CONNECT_BY);
        if (this.getConnectBy().hasWhere()) {
            context.paramTypeIf(ParamType.INLINED, context.family() == SQLDialect.EXASOL, c -> {
                c.formatSeparator().visit(Keywords.K_CONNECT_BY);
                if (this.connectByNoCycle) {
                    c.sql(' ').visit(Keywords.K_NOCYCLE);
                }
                c.sql(' ').visit(this.getConnectBy());
            });
        }
        context.end(Clause.SELECT_CONNECT_BY);
        context.start(Clause.SELECT_GROUP_BY);
        if (!this.getGroupBy().isEmpty() || this.getHaving().hasWhere() && NO_IMPLICIT_GROUP_BY_ON_HAVING.contains((Object)context.dialect())) {
            context.formatSeparator().visit(Keywords.K_GROUP_BY);
            if (this.groupByDistinct) {
                context.sql(' ').visit(Keywords.K_DISTINCT);
            }
            GroupFieldList g3 = this.groupBy;
            if (Transformations.transformGroupByColumnIndex(context.configuration()) && Tools.anyMatch(g3, f -> Patterns.isInlineVal1(f, v -> v instanceof Number))) {
                SelectFieldList<Field<?>> s2 = this.getSelectResolveAllAsterisks(context);
                g3 = new GroupFieldList(g3);
                g3.replaceAll(f -> {
                    int i;
                    if (Patterns.isInlineVal1(f, v -> v instanceof Number) && (i = ((Number)((Val)f).getValue()).intValue()) > 0 && i <= s2.size()) {
                        return Tools.unalias((Field)s2.get(i - 1));
                    }
                    return f;
                });
            }
            context.separatorRequired(true).visit(g3);
        }
        context.end(Clause.SELECT_GROUP_BY);
        context.start(Clause.SELECT_HAVING);
        if (this.getHaving().hasWhere()) {
            context.formatSeparator().visit(Keywords.K_HAVING).sql(' ').visit(this.getHaving());
        }
        context.end(Clause.SELECT_HAVING);
        context.start(Clause.SELECT_WINDOW);
        if (Tools.isNotEmpty(this.window) && !NO_SUPPORT_WINDOW_CLAUSE.contains((Object)context.dialect())) {
            context.formatSeparator().visit(Keywords.K_WINDOW).separatorRequired(true).declareWindows(true, c -> c.visit(this.window));
        }
        context.end(Clause.SELECT_WINDOW);
        if (this.getQualify().hasWhere()) {
            context.formatSeparator().visit(Keywords.K_QUALIFY).sql(' ').visit(this.getQualify());
        }
        this.toSQLOrderBy(context, originalFields, alternativeFields, false, wrapQueryExpressionBodyInDerivedTable, false, this.orderBy, this.limit);
        if (unionOpSize > 0) {
            this.unionParenthesis(context, ')', null, this.derivedTableRequired(context, this), unionParensRequired);
            block19: for (int i = 0; i < unionOpSize; ++i) {
                CombineOperator op = this.unionOp.get(i);
                for (Select select : this.union.get(i)) {
                    boolean derivedTableRequired = this.derivedTableRequired(context, select);
                    boolean otherUnionParensRequired = unionParensRequired || this.unionOpNesting();
                    context.formatSeparator().visit(op.toKeyword(family));
                    if (otherUnionParensRequired) {
                        context.sql(' ');
                    } else {
                        context.formatSeparator();
                    }
                    this.unionParenthesis(context, '(', select.getSelect(), derivedTableRequired, otherUnionParensRequired);
                    context.visit(select);
                    this.unionParenthesis(context, ')', null, derivedTableRequired, otherUnionParensRequired);
                }
                if (i < unionOpSize - 1) {
                    this.unionParenthesis(context, ')', null, this.derivedTableRequired(context, this), unionParensRequired);
                }
                switch (this.unionOp.get(i)) {
                    case EXCEPT: {
                        context.end(Clause.SELECT_EXCEPT);
                        continue block19;
                    }
                    case EXCEPT_ALL: {
                        context.end(Clause.SELECT_EXCEPT_ALL);
                        continue block19;
                    }
                    case INTERSECT: {
                        context.end(Clause.SELECT_INTERSECT);
                        continue block19;
                    }
                    case INTERSECT_ALL: {
                        context.end(Clause.SELECT_INTERSECT_ALL);
                        continue block19;
                    }
                    case UNION: {
                        context.end(Clause.SELECT_UNION);
                        continue block19;
                    }
                    case UNION_ALL: {
                        context.end(Clause.SELECT_UNION_ALL);
                    }
                }
            }
            if (unionOpNesting) {
                context.data().remove(Tools.BooleanDataKey.DATA_NESTED_SET_OPERATIONS);
            }
        }
        if (wrapQueryExpressionBodyInDerivedTable) {
            context.formatIndentEnd().formatNewLine().sql(") t");
            if (applySeekOnDerivedTable) {
                context.formatSeparator().visit(Keywords.K_WHERE).sql(' ').qualify(false, c -> c.visit(this.getSeekCondition(context)));
            }
        }
        context.qualify(false, c -> this.toSQLOrderBy(context, originalFields, alternativeFields, wrapQueryExpressionInDerivedTable, wrapQueryExpressionBodyInDerivedTable, true, this.unionOrderBy, this.unionLimit));
    }

    private final boolean hasInlineDerivedTables(TableList tablelist) {
        return Tools.anyMatch(tablelist, t2 -> t2 instanceof InlineDerivedTable || t2 instanceof JoinTable && this.hasInlineDerivedTables((JoinTable)t2));
    }

    private final boolean hasInlineDerivedTables(JoinTable join) {
        return join.lhs instanceof InlineDerivedTable || join.rhs instanceof InlineDerivedTable || join.lhs instanceof JoinTable && this.hasInlineDerivedTables((JoinTable)join.lhs) || join.rhs instanceof JoinTable && this.hasInlineDerivedTables((JoinTable)join.rhs);
    }

    @Pro
    private final TableList transformLateralToCrossApply(Context<?> ctx, TableList tablelist) {
        if (tablelist.size() > 1) {
            switch (ctx.family()) {
                case SQLDATAWAREHOUSE: 
                case SQLSERVER: {
                    if (!Tools.anyMatch(tablelist, t2 -> t2 instanceof Lateral)) break;
                    Table<Record> t3 = (Table<Record>)tablelist.get(0);
                    for (int i = 1; i < tablelist.size(); ++i) {
                        Table u = (Table)tablelist.get(i);
                        if (u instanceof Lateral) {
                            Lateral l = (Lateral)u;
                            t3 = t3.crossApply((TableLike<?>)l.$arg1());
                            continue;
                        }
                        t3 = t3.crossJoin(u);
                    }
                    return new TableList(t3);
                }
            }
        }
        return tablelist;
    }

    private final TableList transformInlineDerivedTables(TableList tablelist, ConditionProviderImpl where) {
        if (!this.hasInlineDerivedTables(tablelist)) {
            return tablelist;
        }
        TableList result = new TableList();
        for (Table table : tablelist) {
            this.transformInlineDerivedTable0(table, result, where);
        }
        return result;
    }

    private final void transformInlineDerivedTable0(Table<?> table, TableList result, ConditionProviderImpl where) {
        if (table instanceof InlineDerivedTable) {
            InlineDerivedTable t2 = (InlineDerivedTable)table;
            result.add(t2.table);
            where.addConditions(t2.condition);
        } else if (table instanceof JoinTable) {
            result.add(this.transformInlineDerivedTables0(table, where, false));
        } else {
            result.add(table);
        }
    }

    private final Table<?> transformInlineDerivedTables0(Table<?> table, ConditionProviderImpl where, boolean keepDerivedTable) {
        if (table instanceof InlineDerivedTable) {
            InlineDerivedTable t2 = (InlineDerivedTable)table;
            if (keepDerivedTable) {
                return t2.query().asTable(t2.table);
            }
            where.addConditions(t2.condition);
            return t2.table;
        }
        if (table instanceof JoinTable) {
            Table<?> lhs;
            JoinTable j = (JoinTable)table;
            return j.transform(lhs, switch (j.type) {
                case JoinType.LEFT_OUTER_JOIN, JoinType.LEFT_ANTI_JOIN, JoinType.LEFT_SEMI_JOIN, JoinType.STRAIGHT_JOIN, JoinType.CROSS_APPLY, JoinType.OUTER_APPLY, JoinType.NATURAL_LEFT_OUTER_JOIN -> {
                    lhs = this.transformInlineDerivedTables0(j.lhs, where, keepDerivedTable);
                    yield this.transformInlineDerivedTables0(j.rhs, where, true);
                }
                case JoinType.RIGHT_OUTER_JOIN, JoinType.NATURAL_RIGHT_OUTER_JOIN -> {
                    lhs = this.transformInlineDerivedTables0(j.lhs, where, true);
                    yield this.transformInlineDerivedTables0(j.rhs, where, keepDerivedTable);
                }
                case JoinType.FULL_OUTER_JOIN, JoinType.NATURAL_FULL_OUTER_JOIN -> {
                    lhs = this.transformInlineDerivedTables0(j.lhs, where, true);
                    yield this.transformInlineDerivedTables0(j.rhs, where, true);
                }
                default -> {
                    lhs = this.transformInlineDerivedTables0(j.lhs, where, keepDerivedTable);
                    yield this.transformInlineDerivedTables0(j.rhs, where, keepDerivedTable);
                }
            });
        }
        return table;
    }

    @Pro
    private static final Map<Name, TableField<?, ?>> unqualifiedProjection(TableList tablelist) {
        LinkedHashMap result = new LinkedHashMap();
        for (Table table : tablelist) {
            Field<?>[] fields = table.fields();
            if (fields.length == 0) {
                return Collections.emptyMap();
            }
            for (Field<?> field2 : fields) {
                if (!(field2 instanceof TableField)) continue;
                TableField f = (TableField)field2;
                Name key = field2.getUnqualifiedName();
                if (result.containsKey(key)) {
                    result.put(key, null);
                    continue;
                }
                result.put(key, f);
            }
        }
        return result;
    }

    @Pro
    private final TableList transformTableListsToAnsiJoin(TableList tablelist, ConditionProviderImpl where) {
        Table t2;
        if (tablelist.size() <= 1) {
            return tablelist;
        }
        if (where.getWhere() instanceof Or || where.getWhere() instanceof Xor) {
            return tablelist;
        }
        ExtractTableConditionsParams params = new ExtractTableConditionsParams(tablelist);
        ArrayDeque<Table> q1 = new ArrayDeque<Table>(tablelist);
        ArrayDeque<Table<Record>> q2 = new ArrayDeque<Table<Record>>();
        Table t1 = (Table)q1.pop();
        while (true) {
            int size = q1.size();
            for (int i = 0; i < size; ++i) {
                t2 = (Table)q1.pop();
                Table result = this.extractTableConditions(params, t1, t2, where);
                if (result == t1) {
                    q1.add(t2);
                }
                t1 = result;
            }
            if (size > q1.size()) continue;
            q2.add(t1);
            t1 = (Table)q1.pollFirst();
            if (t1 == null) break;
        }
        t1 = (Table)q2.pop();
        while ((t2 = (Table)q2.pollFirst()) != null) {
            t1 = t1.crossJoin(t2);
        }
        return new TableList(t1);
    }

    @Pro
    private final boolean equalJoinTypes(JoinTable t1, JoinTable t22) {
        JoinType j1 = Tools.traverseJoins(t1, t1.type, null, null, null, (t2, j) -> t2 == j ? j : null, null);
        if (j1 == null) {
            return false;
        }
        JoinType j2 = Tools.traverseJoins(t22, t22.type, null, null, null, (t2, j) -> t2 == j ? j : null, null);
        return j1 == j2;
    }

    @Pro
    private final Table<?> extractTableConditions(ExtractTableConditionsParams params, Table<?> t1, Table<?> t2, ConditionProviderImpl where) {
        if (t1 instanceof JoinTable) {
            JoinTable j = (JoinTable)t1;
            Table<?> newLhs = this.extractTableConditions(params, j.lhs, t2, where);
            Table<?> newRhs = this.extractTableConditions(params, j.rhs, t2, where);
            if (j.lhs != newLhs && newLhs instanceof JoinTable && this.equalJoinTypes(j, (JoinTable)newLhs)) {
                return ((JoinTable)newLhs).transform(t1, t2);
            }
            if (j.rhs != newRhs && newRhs instanceof JoinTable && this.equalJoinTypes(j, (JoinTable)newRhs)) {
                return ((JoinTable)newRhs).transform(t1, t2);
            }
            return j.transform(newLhs, newRhs);
        }
        ExtractTableConditions result = new ExtractTableConditions(DSL.noCondition(), null);
        Condition condition = where.getWhere();
        if (condition instanceof QOM.CompareCondition) {
            QOM.CompareCondition cc = (QOM.CompareCondition)condition;
            result.from(this.extractTableConditions(params, t1, t2, result, cc, null));
            if (!(result.condition instanceof NoCondition)) {
                where.setWhere(DSL.noCondition());
            }
        } else if (where.getWhere() instanceof And) {
            where.setWhere((Condition)where.getWhere().$replace(QOM.internalReplacer(Replacer.of(p -> p instanceof Condition, old -> {
                if (old instanceof QOM.CompareCondition) {
                    QOM.CompareCondition cc = (QOM.CompareCondition)old;
                    boolean[] removed = new boolean[]{false};
                    result.from(this.extractTableConditions(params, t1, t2, result, cc, removed));
                    if (removed[0]) {
                        return DSL.noCondition();
                    }
                }
                return old;
            }))));
        }
        if (result.condition instanceof NoCondition) {
            return t1;
        }
        return (result.joinType == JoinType.LEFT_OUTER_JOIN ? t1.leftJoin(t2) : (result.joinType == JoinType.RIGHT_OUTER_JOIN ? t1.rightJoin(t2) : t1.join(t2))).on(result.condition);
    }

    @Pro
    private final <T> ExtractTableConditions extractTableConditions(ExtractTableConditionsParams params, Table<?> t1, Table<?> t2, ExtractTableConditions result, QOM.CompareCondition<T, ?> cc, boolean[] removed) {
        boolean b22;
        boolean b21;
        boolean b12;
        boolean b11;
        boolean t2Ambiguous;
        TableField<?, ?> f;
        TableField<?, ?> f2;
        Field f1 = (Field)cc.$arg1();
        Field f22 = (Field)cc.$arg2();
        JoinType j = JoinType.JOIN;
        Condition c = result.condition;
        if (f1 instanceof PlusJoin) {
            PlusJoin p = (PlusJoin)f1;
            f1 = p.field;
            j = result.joinType != JoinType.JOIN ? JoinType.RIGHT_OUTER_JOIN : JoinType.JOIN;
        } else if (f22 instanceof PlusJoin) {
            PlusJoin p = (PlusJoin)f22;
            f22 = p.field;
            j = result.joinType != JoinType.JOIN ? JoinType.LEFT_OUTER_JOIN : JoinType.JOIN;
        }
        TableField<?, ?> tf1 = f1 instanceof TableField ? (f2 = (TableField<?, ?>)f1) : null;
        TableField<?, ?> tf2 = f22 instanceof TableField ? (f = (TableField<?, ?>)f22) : null;
        TableField<?, ?> tfq1 = tf1 != null && tf1.getTable() == null && params.unqualifiedProjection.containsKey(tf1.getUnqualifiedName()) ? params.unqualifiedProjection.get(tf1.getUnqualifiedName()) : tf1;
        TableField<?, ?> tfq2 = tf2 != null && tf2.getTable() == null && params.unqualifiedProjection.containsKey(tf2.getUnqualifiedName()) ? params.unqualifiedProjection.get(tf2.getUnqualifiedName()) : tf2;
        boolean t1Ambiguous = params.ambiguousNames.getOrDefault(t1.getUnqualifiedName(), 0) > 1;
        boolean bl = t2Ambiguous = params.ambiguousNames.getOrDefault(t2.getUnqualifiedName(), 0) > 1;
        boolean bl2 = tfq1 != null && tfq1.getTable() != null && (t1Ambiguous ? t1.equals(tfq1.getTable()) : t1.getUnqualifiedName().equals(tfq1.getTable().getUnqualifiedName())) ? true : (b11 = false);
        boolean bl3 = tfq2 != null && tfq2.getTable() != null && (t1Ambiguous ? t1.equals(tfq2.getTable()) : t1.getUnqualifiedName().equals(tfq2.getTable().getUnqualifiedName())) ? true : (b12 = false);
        boolean bl4 = tfq1 != null && tfq1.getTable() != null && (t2Ambiguous ? t2.equals(tfq1.getTable()) : t2.getUnqualifiedName().equals(tfq1.getTable().getUnqualifiedName())) ? true : (b21 = false);
        boolean bl5 = tfq2 != null && tfq2.getTable() != null && (t2Ambiguous ? t2.equals(tfq2.getTable()) : t2.getUnqualifiedName().equals(tfq2.getTable().getUnqualifiedName())) ? true : (b22 = false);
        if (b21 || b12) {
            if (b21 && b12) {
                if (j == JoinType.LEFT_OUTER_JOIN) {
                    j = JoinType.RIGHT_OUTER_JOIN;
                } else if (j == JoinType.RIGHT_OUTER_JOIN) {
                    j = JoinType.LEFT_OUTER_JOIN;
                }
            } else if (!(tfq1 != null && tfq2 != null || j != JoinType.LEFT_OUTER_JOIN && j != JoinType.RIGHT_OUTER_JOIN)) {
                c = c.and((Condition)cc.$constructor().apply(f1, f22));
                if (removed != null) {
                    removed[0] = true;
                }
                return new ExtractTableConditions(c, result.joinType);
            }
        }
        if (tfq1 != null && tfq2 != null) {
            if (b11 && b22 || b21 && b12) {
                c = c.and(tf1 == cc.$arg1() && tf2 == cc.$arg2() ? cc : (Condition)cc.$constructor().apply(tf1, tf2));
                if (removed != null) {
                    removed[0] = true;
                }
                return new ExtractTableConditions(c, j);
            }
            return result;
        }
        return result;
    }

    @Pro
    private final TableList transformAnsiJoinToTableLists(TableList tablelist, ConditionProviderImpl where) {
        TableList result = new TableList();
        for (Table table : tablelist) {
            if (table instanceof JoinTable) {
                JoinTable j = (JoinTable)table;
                this.transformAnsiJoinToTableLists0(j, result, where);
                continue;
            }
            result.add(table);
        }
        return result;
    }

    @Pro
    private final void transformAnsiJoinToTableLists0(JoinTable table, TableList result, ConditionProviderImpl where) {
        JoinTable j;
        Table<?> table2 = table.lhs;
        if (table2 instanceof JoinTable) {
            j = (JoinTable)table2;
            this.transformAnsiJoinToTableLists0(j, result, where);
        } else {
            result.add(table.lhs);
        }
        table2 = table.rhs;
        if (table2 instanceof JoinTable) {
            j = (JoinTable)table2;
            this.transformAnsiJoinToTableLists0(j, result, where);
        } else {
            result.add(table.rhs);
        }
        switch (table.type) {
            case STRAIGHT_JOIN: 
            case CROSS_JOIN: 
            case JOIN: {
                if (table.condition.hasWhere()) {
                    where.addConditions(table.condition.getWhere());
                    break;
                }
                if (table.using.isEmpty()) break;
                where.addConditions(table.usingCondition());
                break;
            }
            case LEFT_OUTER_JOIN: {
                if (table.condition.hasWhere()) {
                    where.addConditions(this.transformAnsiOuterJoinConditionToLegacyCondition(table.rhs, table.condition.getWhere()));
                    break;
                }
                if (table.using.isEmpty()) break;
                where.addConditions(this.transformAnsiOuterJoinConditionToLegacyCondition(table.rhs, table.usingCondition()));
                break;
            }
            case RIGHT_OUTER_JOIN: {
                if (table.condition.hasWhere()) {
                    where.addConditions(this.transformAnsiOuterJoinConditionToLegacyCondition(table.lhs, table.condition.getWhere()));
                    break;
                }
                if (table.using.isEmpty()) break;
                where.addConditions(this.transformAnsiOuterJoinConditionToLegacyCondition(table.lhs, table.usingCondition()));
                break;
            }
            default: {
                log.info("ANSI join to pre-ANSI join transformation not supported for " + String.valueOf((Object)table.type));
            }
        }
    }

    private final Condition transformAnsiOuterJoinConditionToLegacyCondition(Table<?> optional, Condition joinCondition) {
        return (Condition)joinCondition.$replace(QOM.internalReplacer(Replacer.of(p -> p instanceof Field && optional.getQualifiedName().equals(((Field)p).getQualifiedName().qualifier()) ? ((Field)p).plus() : p)));
    }

    private final void toSQLOrderBy(Context<?> ctx, List<Field<?>> originalFields, List<Field<?>> alternativeFields, boolean wrapQueryExpressionInDerivedTable, boolean wrapQueryExpressionBodyInDerivedTable, boolean isUnionOrderBy, QueryPartListView<SortField<?>> actualOrderBy, Limit actualLimit) {
        ctx.start(Clause.SELECT_ORDER_BY);
        if (!this.getLimit().withTies() || SUPPORT_FULL_WITH_TIES.contains((Object)ctx.dialect()) || (ctx.family() == SQLDialect.SQLSERVER || ctx.family() == SQLDialect.SQLDATAWAREHOUSE || ctx.family() == SQLDialect.TERADATA) && this.topSupported(ctx)) {
            if (!actualOrderBy.isEmpty()) {
                ctx.formatSeparator().visit(Keywords.K_ORDER);
                if (this.orderBySiblings) {
                    ctx.sql(' ').visit(Keywords.K_SIBLINGS);
                }
                ctx.sql(' ').visit(Keywords.K_BY).separatorRequired(true);
                if (RowAsField.NO_NATIVE_SUPPORT.contains((Object)ctx.dialect()) && Tools.findAny(actualOrderBy, s2 -> s2.$field() instanceof Val) != null) {
                    SelectFieldIndexes s3 = this.getSelectFieldIndexes(ctx);
                    if (s3.mapped) {
                        actualOrderBy = new QueryPartListView((List<SortField<?>>)((Object)actualOrderBy)).map(t1 -> {
                            Field in = t1.$field();
                            if (in instanceof Val && in.getDataType().isNumeric()) {
                                Val val = (Val)in;
                                int x = Convert.convert(val.getValue(), Integer.TYPE) - 1;
                                int mapped = s2.mapping[x];
                                Val out = s2.projectionSizes[x] == 1 ? val.copy(mapped + 1) : DSL.field("{0}", DSL.list((QueryPart[])IntStream.range(mapped, mapped + s2.projectionSizes[mapped]).mapToObj(i -> val.copy(i + 1)).toArray(SelectField[]::new)));
                                return t1.$field(out);
                            }
                            return t1;
                        });
                    }
                }
                actualOrderBy = new QueryPartListView((List<SortField<?>>)((Object)actualOrderBy)).map(t1 -> {
                    Field out = this.getResolveProjection(ctx, t1.$field());
                    return t1.$field() == out ? t1 : t1.$field(out);
                });
                if (originalFields != null) {
                    QueryPartCollectionView o = actualOrderBy;
                    ctx.qualify(!wrapQueryExpressionBodyInDerivedTable && ctx.qualify(), c1 -> c1.data(Tools.SimpleDataKey.DATA_OVERRIDE_ALIASES_IN_ORDER_BY, new Object[]{originalFields, alternativeFields}, c2 -> c2.visit(o)));
                } else {
                    if (NO_SUPPORT_UNION_ORDER_BY_ALIAS.contains((Object)ctx.dialect()) && this.hasUnions() && isUnionOrderBy) {
                        List<String> n = Tools.map(this.getSelect(), Field::getName);
                        actualOrderBy = new QueryPartListView((List<SortField<?>>)((Object)actualOrderBy)).map(t1 -> {
                            int i = n.indexOf(t1.$field().getName());
                            return i >= 0 ? t1.$field(DSL.inline(i + 1)) : t1;
                        });
                    }
                    ctx.visit(actualOrderBy);
                }
            } else if (actualLimit.isApplicable() && !actualLimit.offsetZero() && REQUIRE_ORDER_BY_WITH_OFFSET.contains((Object)ctx.dialect())) {
                ctx.formatSeparator().visit(Keywords.K_ORDER_BY).sql(" (").visit(Keywords.K_SELECT).sql(" 0)");
            }
        }
        ctx.end(Clause.SELECT_ORDER_BY);
        if (wrapQueryExpressionInDerivedTable) {
            ctx.formatIndentEnd().formatNewLine().sql(") x");
        }
        if (Boolean.TRUE.equals(ctx.data(Tools.BooleanDataKey.DATA_RENDER_TRAILING_LIMIT_IF_APPLICABLE))) {
            if (actualLimit.isApplicable()) {
                ctx.visit(actualLimit);
            } else if (!actualOrderBy.isEmpty() && Boolean.TRUE.equals(ctx.data(Tools.BooleanDataKey.DATA_FORCE_LIMIT_WITH_ORDER_BY))) {
                Limit l = new Limit();
                l.setLimit(Long.MAX_VALUE);
                ctx.visit(l);
            }
        }
    }

    private final boolean applySeekOnDerivedTable() {
        return !this.getSeek().isEmpty() && !this.getOrderBy().isEmpty() && !this.unionOp.isEmpty();
    }

    private final boolean wrapQueryExpressionBodyInDerivedTable(Context<?> ctx) {
        return WRAP_EXP_BODY_IN_DERIVED_TABLE.contains((Object)ctx.dialect()) && (this.getLimit().isApplicable() || ctx.family() == SQLDialect.MEMSQL) && this.unionOp.size() > 0;
    }

    @Pro
    private final void toSQLTopClause(Context<?> context) {
        SQLDialect family = context.family();
        SQLDialect dialect = context.dialect();
        switch (family) {
            case SYBASE: {
                if (!this.limit.isApplicable()) break;
                context.data(Tools.BooleanDataKey.DATA_PREFER_TOP_OVER_FETCH, true, c -> c.visit(this.limit).separatorRequired(true));
                break;
            }
            case ACCESS: 
            case ASE: 
            case SQLDATAWAREHOUSE: 
            case TERADATA: 
            case SQLSERVER: {
                if ((SUPPORT_TOP.contains((Object)dialect) || SUPPORT_TOP_SQLSERVER.contains((Object)dialect)) && this.limit.isApplicable() && this.topSupported(context)) {
                    context.data(Tools.BooleanDataKey.DATA_PREFER_TOP_OVER_FETCH, true, c -> c.visit(this.limit).separatorRequired(true));
                    break;
                }
                if (!SUPPORT_TOP_SQLSERVER.contains((Object)dialect) || !context.subquery() || this.hasUnions() || this.getOrderBy().isEmpty() || this.forXML != null || this.forJSON != null || !REQUIRE_TOP_DERIVED_ORDER_BY.contains((Object)dialect) && this.limit.isApplicable()) break;
                if (family == SQLDialect.TERADATA) {
                    context.visit(Keywords.K_TOP).sql(' ').visit(DSL.inline(999999999999999999L)).separatorRequired(true);
                    break;
                }
                context.visit(Keywords.K_TOP).sql(' ').visit(DSL.inline(Long.MAX_VALUE)).separatorRequired(true);
            }
        }
    }

    final boolean hasUnions() {
        return !this.unionOp.isEmpty();
    }

    private final boolean unionOpNesting() {
        if (this.unionOp.size() > 1) {
            return true;
        }
        for (QueryPartList<Select<?>> s1 : this.union) {
            for (Select select : s1) {
                SelectQueryImpl s3 = Tools.selectQueryImpl(select);
                if (s3 == null || s3.unionOp.isEmpty()) continue;
                return true;
            }
        }
        return false;
    }

    private final boolean derivedTableRequired(Context<?> context, Select<?> s1) {
        SelectQueryImpl s2;
        return NO_SUPPORT_CTE_IN_UNION.contains((Object)context.dialect()) && (s2 = Tools.selectQueryImpl(s1)) != null && s2.with != null;
    }

    private final boolean unionParensRequired(Context<?> context) {
        if (this.unionOp.isEmpty()) {
            return false;
        }
        if (this.unionParensRequired(this) || context.settings().isRenderParenthesisAroundSetOperationQueries().booleanValue()) {
            return true;
        }
        CombineOperator op = this.unionOp.get(0);
        if ((op == CombineOperator.EXCEPT || op == CombineOperator.EXCEPT_ALL) && this.union.get(0).size() > 1) {
            return true;
        }
        for (QueryPartList<Select<?>> s1 : this.union) {
            for (Select select : s1) {
                SelectQueryImpl s3 = Tools.selectQueryImpl(select);
                if (s3 == null || !this.unionParensRequired(s3)) continue;
                return true;
            }
        }
        return false;
    }

    private final boolean unionParensRequired(SelectQueryImpl<?> s2) {
        return s2.orderBy.size() > 0 || s2.limit.isApplicable() || s2.with != null;
    }

    private final void unionParenthesis(Context<?> ctx, char parenthesis, List<Field<?>> fields, boolean derivedTableRequired, boolean parensRequired) {
        if ('(' == parenthesis) {
            ((AbstractContext)ctx).subquery0(true, true);
        } else if (')' == parenthesis) {
            ((AbstractContext)ctx).subquery0(false, true);
        }
        if ((parensRequired |= (derivedTableRequired |= derivedTableRequired || parensRequired && NO_SUPPORT_UNION_PARENTHESES.contains((Object)ctx.dialect()) || Boolean.TRUE.equals(ctx.data(Tools.BooleanDataKey.DATA_NESTED_SET_OPERATIONS)) && UNION_PARENTHESIS.contains((Object)ctx.dialect()) || Boolean.TRUE.equals(ctx.data(Tools.BooleanDataKey.DATA_INSERT_SELECT_WITHOUT_INSERT_COLUMN_LIST)))) && ')' == parenthesis) {
            ctx.formatIndentEnd().formatNewLine();
        } else if (parensRequired && '(' == parenthesis && derivedTableRequired) {
            ctx.formatNewLine().visit(Keywords.K_SELECT).sql(' ');
            if (ctx.family() == SQLDialect.DERBY) {
                ctx.visit(new SelectFieldList<Field>((Iterable<Field>)Tools.map(fields, f -> Tools.unqualified(f))));
            } else {
                ctx.sql('*');
            }
            ctx.formatSeparator().visit(Keywords.K_FROM).sql(' ');
        }
        switch (ctx.family()) {
            case FIREBIRD: {
                break;
            }
            default: {
                if (!parensRequired) break;
                ctx.sql(parenthesis);
            }
        }
        if (parensRequired && '(' == parenthesis) {
            ctx.formatIndentStart().formatNewLine();
        } else if (parensRequired && ')' == parenthesis && derivedTableRequired) {
            ctx.sql(" x");
        }
    }

    @Override
    public final void addSelect(Collection<? extends SelectFieldOrAsterisk> fields) {
        this.getSelectAsSpecified().addAll(fields);
    }

    @Override
    public final void addSelect(SelectFieldOrAsterisk ... fields) {
        this.addSelect(Arrays.asList(fields));
    }

    @Override
    public final void setDistinct(boolean distinct2) {
        this.distinct = distinct2;
    }

    @Override
    public final void addDistinctOn(SelectFieldOrAsterisk ... fields) {
        this.addDistinctOn(Arrays.asList(fields));
    }

    @Override
    public final void addDistinctOn(Collection<? extends SelectFieldOrAsterisk> fields) {
        this.distinctOn.addAll((Collection<SelectFieldOrAsterisk>)fields);
    }

    @Override
    public final void setInto(Table<?> table) {
        this.intoTable = table;
    }

    @Override
    public final void setInto(Collection<? extends Variable<?>> variables) {
        this.intoVariables = variables;
    }

    @Override
    public final void addOffset(Number offset) {
        this.getLimit().setOffset(offset);
    }

    @Override
    public final void addOffset(Field<? extends Number> offset) {
        this.getLimit().setOffset(offset);
    }

    @Override
    public final void addLimit(Number l) {
        this.getLimit().setLimit(l);
    }

    @Override
    public final void addLimit(Field<? extends Number> l) {
        this.getLimit().setLimit(l);
    }

    @Override
    public final void addLimit(Number offset, Number l) {
        this.getLimit().setOffset(offset);
        this.getLimit().setLimit(l);
    }

    @Override
    public final void addLimit(Number offset, Field<? extends Number> l) {
        this.getLimit().setOffset(offset);
        this.getLimit().setLimit(l);
    }

    @Override
    public final void addLimit(Field<? extends Number> offset, Number l) {
        this.getLimit().setOffset(offset);
        this.getLimit().setLimit(l);
    }

    @Override
    public final void addLimit(Field<? extends Number> offset, Field<? extends Number> l) {
        this.getLimit().setOffset(offset);
        this.getLimit().setLimit(l);
    }

    @Override
    public final void setLimitPercent(boolean percent) {
        this.getLimit().setPercent(percent);
    }

    @Override
    public final void setWithTies(boolean withTies) {
        this.getLimit().setWithTies(withTies);
    }

    final ForLock forLock() {
        if (this.forLock == null) {
            this.forLock = new ForLock();
        }
        return this.forLock;
    }

    @Override
    public final void setForUpdate(boolean forUpdate) {
        if (forUpdate) {
            this.forLock().forLockMode = ForLock.ForLockMode.UPDATE;
        } else {
            this.forLock = null;
        }
    }

    @Override
    public final void setForNoKeyUpdate(boolean forNoKeyUpdate) {
        if (forNoKeyUpdate) {
            this.forLock().forLockMode = ForLock.ForLockMode.NO_KEY_UPDATE;
        } else {
            this.forLock = null;
        }
    }

    @Override
    public final void setForKeyShare(boolean forKeyShare) {
        if (forKeyShare) {
            this.forLock().forLockMode = ForLock.ForLockMode.KEY_SHARE;
        } else {
            this.forLock = null;
        }
    }

    @Override
    public final void setForUpdateOf(Field<?> ... fields) {
        this.setForLockModeOf(fields);
    }

    @Override
    public final void setForUpdateOf(Collection<? extends Field<?>> fields) {
        this.setForLockModeOf(fields);
    }

    @Override
    public final void setForUpdateOf(Table<?> ... tables) {
        this.setForLockModeOf(tables);
    }

    @Override
    public final void setForUpdateWait(int seconds) {
        this.setForLockModeWait(seconds);
    }

    @Override
    public final void setForUpdateNoWait() {
        this.setForLockModeNoWait();
    }

    @Override
    public final void setForUpdateSkipLocked() {
        this.setForLockModeSkipLocked();
    }

    @Override
    public final void setForShare(boolean forShare) {
        if (forShare) {
            this.forLock().forLockMode = ForLock.ForLockMode.SHARE;
        } else {
            this.forLock = null;
        }
    }

    @Override
    public final void setForLockModeOf(Field<?> ... fields) {
        this.setForLockModeOf(Arrays.asList(fields));
    }

    @Override
    public final void setForLockModeOf(Collection<? extends Field<?>> fields) {
        this.initLockMode();
        this.forLock().forLockOf = new QueryPartList((Iterable<Field<?>>)fields);
        this.forLock().forLockOfTables = null;
    }

    @Override
    public final void setForLockModeOf(Table<?> ... tables) {
        this.initLockMode();
        this.forLock().forLockOf = null;
        this.forLock().forLockOfTables = new TableList((List<? extends Table<?>>)Arrays.asList(tables));
    }

    @Override
    public final void setForLockModeWait(int seconds) {
        this.initLockMode();
        this.forLock().forLockWaitMode = ForLock.ForLockWaitMode.WAIT;
        this.forLock().forLockWait = seconds;
    }

    @Override
    public final void setForLockModeNoWait() {
        this.initLockMode();
        this.forLock().forLockWaitMode = ForLock.ForLockWaitMode.NOWAIT;
        this.forLock().forLockWait = 0;
    }

    @Override
    public final void setForLockModeSkipLocked() {
        this.initLockMode();
        this.forLock().forLockWaitMode = ForLock.ForLockWaitMode.SKIP_LOCKED;
        this.forLock().forLockWait = 0;
    }

    private final void initLockMode() {
        this.forLock().forLockMode = this.forLock().forLockMode == null ? ForLock.ForLockMode.UPDATE : this.forLock().forLockMode;
    }

    final ForXML forXML() {
        if (this.forXML == null) {
            this.forXML = new ForXML();
        }
        return this.forXML;
    }

    final ForJSON forJSON() {
        if (this.forJSON == null) {
            this.forJSON = new ForJSON();
        }
        return this.forJSON;
    }

    final boolean hasForXML() {
        return this.forXML != null;
    }

    final boolean hasForJSON() {
        return this.forJSON != null;
    }

    @Override
    public final void setForXMLRaw(boolean forXMLRaw) {
        this.forXML().forXMLRaw = forXMLRaw;
    }

    @Override
    public final void setForXMLRaw(boolean forXMLRaw, String elementName) {
        this.forXML().forXMLRaw = forXMLRaw;
        this.forXML().forXMLRawElement = elementName;
    }

    @Override
    public final void setForXMLAuto(boolean forXMLAuto) {
        this.forXML().forXMLAuto = forXMLAuto;
    }

    @Override
    public final void setForXMLPath(boolean forXMLPath) {
        this.forXML().forXMLPath = forXMLPath;
    }

    @Override
    public final void setForXMLPath(boolean forXMLPath, String elementName) {
        this.forXML().forXMLPath = forXMLPath;
        this.forXML().forXMLPathElement = elementName;
    }

    @Override
    public final void setForXMLExplicit(boolean forXMLExplicit) {
        this.forXML().forXMLExplicit = forXMLExplicit;
    }

    @Override
    public final void setForXMLBinaryBase64(boolean forXMLBinaryBase64) {
        this.forXML().forXMLBinaryBase64 = forXMLBinaryBase64;
    }

    @Override
    public final void setForXMLType(boolean forXMLType) {
        this.forXML().forXMLType = forXMLType;
    }

    @Override
    public final void setForXMLRoot(boolean forXMLRoot) {
        this.forXML().forXMLRoot = forXMLRoot;
    }

    @Override
    public final void setForXMLRoot(boolean forXMLRoot, String rootName) {
        this.forXML().forXMLRoot = forXMLRoot;
        this.forXML().forXMLRootElement = rootName;
    }

    @Override
    public final void setForXMLElements(boolean forXMLElements) {
        this.forXML().forXMLElements = forXMLElements;
    }

    @Override
    public final void setForXMLElementsXsinil(boolean forXMLElementsXsinil) {
        this.forXML().forXMLElementsXsinil = forXMLElementsXsinil;
    }

    @Override
    public final void setForXMLElementsAbsent(boolean forXMLElementsAbsent) {
        this.forXML().forXMLElementsAbsent = forXMLElementsAbsent;
    }

    @Override
    public final void setForJSONB(boolean forJSONB) {
        this.forJSON().forJSONB = forJSONB;
    }

    @Override
    public final void setForJSONAuto(boolean forJSONAuto) {
        this.forJSON().forJSONAuto = forJSONAuto;
    }

    @Override
    public final void setForJSONPath(boolean forJSONPath) {
        this.forJSON().forJSONPath = forJSONPath;
    }

    @Override
    public final void setForJSONRoot(boolean forJSONRoot) {
        this.forJSON().forJSONRoot = forJSONRoot;
    }

    @Override
    public final void setForJSONRoot(boolean forJSONRoot, String rootName) {
        this.forJSON().forJSONRoot = forJSONRoot;
        this.forJSON().forJSONRootElement = rootName;
    }

    @Override
    public final void setForJSONIncludeNullValues(boolean forJSONIncludeNullValues) {
        this.forJSON().forJSONIncludeNullValues = forJSONIncludeNullValues;
    }

    @Override
    public final void setForJSONWithoutArrayWrapper(boolean forJSONWithoutArrayWrapper) {
        this.forJSON().forJSONWithoutArrayWrapper = forJSONWithoutArrayWrapper;
    }

    @Override
    public final void setWithCheckOption() {
        this.withCheckOption = true;
        this.withReadOnly = false;
    }

    @Override
    public final void setWithReadOnly() {
        this.withCheckOption = false;
        this.withReadOnly = true;
    }

    @Override
    public final List<Field<?>> getSelect() {
        return this.getSelectResolveAllAsterisks(Tools.configuration(this.configuration()).dsl());
    }

    private static final Collection<? extends Field<?>> subtract(List<? extends Field<?>> left, List<? extends Field<?>> right) {
        FieldsImpl e = new FieldsImpl(right);
        ArrayList result = new ArrayList();
        for (Field<?> f : left) {
            if (e.field(f) != null) continue;
            result.add(f);
        }
        return result;
    }

    final SelectFieldList<SelectFieldOrAsterisk> getSelectAsSpecified() {
        return this.select;
    }

    final SelectFieldList<SelectFieldOrAsterisk> getSelectResolveImplicitAsterisks() {
        if (this.getSelectAsSpecified().isEmpty()) {
            return this.resolveAsterisk(new SelectFieldList());
        }
        return this.getSelectAsSpecified();
    }

    final SelectFieldList<SelectFieldOrAsterisk> getSelectResolveUnsupportedAsterisks(Context<?> ctx) {
        return this.getSelectResolveSomeAsterisks0(ctx, false);
    }

    final SelectFieldList<Field<?>> getSelectResolveAllAsterisks(Scope ctx) {
        return this.getSelectResolveSomeAsterisks0(ctx, true);
    }

    private final SelectFieldList<SelectFieldOrAsterisk> getSelectResolveSomeAsterisks0(Scope ctx, boolean resolveSupported) {
        SelectFieldList<SelectFieldOrAsterisk> result = new SelectFieldList<SelectFieldOrAsterisk>();
        boolean resolveExcept = resolveSupported || !AsteriskImpl.SUPPORT_NATIVE_EXCEPT.contains((Object)ctx.dialect());
        boolean resolveUnqualifiedCombined = resolveSupported || AsteriskImpl.NO_SUPPORT_UNQUALIFIED_COMBINED.contains((Object)ctx.dialect());
        SelectFieldList<SelectFieldOrAsterisk> list = this.getSelectResolveImplicitAsterisks();
        int size = 0;
        for (SelectFieldOrAsterisk s2 : list) {
            this.appendResolveSomeAsterisks0(ctx, resolveSupported, result, resolveExcept, resolveUnqualifiedCombined, list, s2);
            if (resolveSupported && size == result.size()) {
                return new SelectFieldList<SelectFieldOrAsterisk>();
            }
            size = result.size();
        }
        return result;
    }

    private final void appendResolveSomeAsterisks0(Scope ctx, boolean resolveSupported, SelectFieldList<SelectFieldOrAsterisk> result, boolean resolveExcept, boolean resolveUnqualifiedCombined, SelectFieldList<SelectFieldOrAsterisk> list, SelectFieldOrAsterisk s2) {
        if (s2 instanceof Field) {
            Field f = (Field)s2;
            result.add(this.getResolveProjection(ctx, f));
        } else if (s2 instanceof QualifiedAsterisk) {
            QualifiedAsterisk q = (QualifiedAsterisk)s2;
            Table<?> table = q.qualifier();
            if (table instanceof QOM.JoinTable) {
                QOM.JoinTable j = (QOM.JoinTable)table;
                this.appendResolveSomeAsterisks0(ctx, resolveSupported, result, resolveExcept, resolveUnqualifiedCombined, list, j.$table1().asterisk());
                this.appendResolveSomeAsterisks0(ctx, resolveSupported, result, resolveExcept, resolveUnqualifiedCombined, list, j.$table2().asterisk());
            } else if (q.$except().isEmpty()) {
                if (resolveSupported) {
                    result.addAll(Arrays.asList(q.qualifier().fields()));
                } else {
                    result.add(s2);
                }
            } else if (resolveExcept) {
                result.addAll(SelectQueryImpl.subtract(Arrays.asList(q.qualifier().fields()), q.$except()));
            } else {
                result.add(s2);
            }
        } else if (s2 instanceof Asterisk) {
            Asterisk a = (Asterisk)s2;
            if (a.$except().isEmpty()) {
                if (resolveSupported || resolveUnqualifiedCombined && list.size() > 1) {
                    result.addAll(this.resolveAsterisk(new QueryPartList()));
                } else {
                    result.add(s2);
                }
            } else if (resolveExcept) {
                result.addAll(this.resolveAsterisk(new QueryPartList(), (QueryPartListView)a.$except()));
            } else {
                result.add(s2);
            }
        } else if (s2 instanceof Row) {
            Row r = (Row)((Object)s2);
            result.add(this.getResolveProjection(ctx, new RowAsField(r)));
        } else if (s2 instanceof Table) {
            Table t2 = (Table)s2;
            result.add(this.getResolveProjection(ctx, new TableAsField(t2)));
        } else {
            throw new AssertionError((Object)("Type not supported: " + String.valueOf(s2)));
        }
    }

    private final Field<?> getResolveProjection(Scope ctx, Field<?> f) {
        if (f.getDataType().computedOnClientVirtual(ctx.configuration())) {
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
            Field computed = (Field)generator.apply(new DefaultGeneratorContext(configuration, map, instant, table, (Field<?>)f, GeneratorStatementType.SELECT));
            if (computed != null) {
                f = computed.as(f.getUnqualifiedName());
            }
        }
        if (Transformations.transformRownum(ctx.configuration()) && ctx.family() != SQLDialect.ORACLE && f.$traverse(Traversers.containing(DSL.rownum())).booleanValue()) {
            Table t2 = null;
            SelectQueryImpl s2 = this.getFrom().size() == 1 && (t2 = Tools.unalias((Table)this.getFrom().get(0))) instanceof DerivedTable ? Tools.selectQueryImpl(((DerivedTable)t2).query()) : null;
            WindowFinalStep<Integer> rn = DSL.rowNumber().over(DSL.orderBy(s2 == null ? Collections.emptyList() : Tools.map(s2.getOrderBy(), o -> Tools.unqualified(o))));
            if (f instanceof Rownum) {
                return rn.as(f.getName());
            }
            return (Field)f.$replace(QOM.internalReplacer(Replacer.of(p -> !(p instanceof Select), p -> p instanceof Rownum ? DSL.rowNumber().over(DSL.orderBy(s2 == null ? Collections.emptyList() : Tools.map(s2.getOrderBy(), o -> Tools.unqualified(o)))) : p)));
        }
        return f;
    }

    private final <Q extends QueryPartList<? super Field<?>>> Q resolveAsterisk(Q result) {
        return this.resolveAsterisk(result, null);
    }

    private final <Q extends QueryPartList<? super Field<?>>> Q resolveAsterisk(Q result, QueryPartCollectionView<? extends Field<?>> except) {
        FieldsImpl e;
        FieldsImpl fieldsImpl = e = except == null ? null : new FieldsImpl(except);
        if (this.knownTableSource()) {
            if (e == null) {
                for (TableLike table : this.getFrom()) {
                    for (Field<?> field2 : table.asTable().fields()) {
                        result.add(field2);
                    }
                }
            } else {
                for (TableLike table : this.getFrom()) {
                    for (Field<?> field3 : table.asTable().fields()) {
                        if (e.field(field3) != null) continue;
                        result.add(field3);
                    }
                }
            }
        }
        if (this.getFrom().isEmpty()) {
            result.add(DSL.one());
        }
        return result;
    }

    private final SelectFieldIndexes getSelectFieldIndexes(Context<?> ctx) {
        List<Field<?>> s2 = this.getSelect();
        boolean mapped = false;
        int[] mapping = new int[s2.size()];
        int[] projectionSizes = new int[s2.size()];
        if (RowAsField.NO_NATIVE_SUPPORT.contains((Object)ctx.dialect())) {
            for (int i = 0; i < mapping.length; ++i) {
                projectionSizes[i] = ((AbstractField)s2.get(i)).projectionSize();
                mapped |= projectionSizes[i] > 1;
                if (i >= mapping.length - 1) continue;
                mapping[i + 1] = mapping[i] + projectionSizes[i];
            }
        } else {
            for (int i = 0; i < mapping.length; ++i) {
                mapping[i] = i;
            }
        }
        return new SelectFieldIndexes(mapped, mapping, projectionSizes);
    }

    private final boolean knownTableSource() {
        return Tools.traverseJoins(this.getFrom(), Boolean.valueOf(true), r -> r == false, null, j -> j.type != JoinType.LEFT_ANTI_JOIN && j.type != JoinType.LEFT_SEMI_JOIN, null, (r, t2) -> r != false && t2.fieldsRow().size() > 0);
    }

    @Override
    final Class<? extends R> getRecordType0() {
        if (this.getFrom().size() == 1 && this.getSelectAsSpecified().isEmpty()) {
            return ((Table)this.getFrom().get(0)).asTable().getRecordType();
        }
        return Tools.recordType(this.getSelect().size());
    }

    final TableList getFrom() {
        return this.from;
    }

    final ConditionProviderImpl getWhere(Context<?> ctx) {
        ConditionProviderImpl result = new ConditionProviderImpl();
        if (this.condition.hasWhere()) {
            result.addConditions(this.condition.getWhere());
        }
        if (!this.getOrderBy().isEmpty() && !this.getSeek().isEmpty() && this.unionOp.isEmpty()) {
            result.addConditions(this.getSeekCondition(ctx));
        }
        if (ctx.family() == SQLDialect.BIGQUERY && !result.hasWhere() && this.getQualify().hasWhere()) {
            result.addConditions((Condition)DSL.trueCondition());
        }
        return result;
    }

    final Condition getSeekCondition(Context<?> ctx) {
        SortFieldList o = this.getOrderBy();
        Condition c = null;
        QueryPartList<Field<?>> s2 = this.getSeek();
        if (o.nulls()) {
            // empty if block
        }
        if (o.size() > 1 && o.uniform() && !Boolean.FALSE.equals(ctx.settings().isRenderRowConditionForSeekClause())) {
            List<Field<?>> l = o.fields();
            Serializable r = s2;
            if (Tools.anyMatch(r, e -> e instanceof NoField)) {
                l = new ArrayList(l);
                r = new ArrayList((Collection<Field<?>>)((Object)r));
                for (int i = 0; i < r.size(); ++i) {
                    if (!(r.get(i) instanceof NoField)) continue;
                    l.remove(i);
                    r.remove(i);
                }
            }
            c = l.isEmpty() ? DSL.noCondition() : (((SortField)o.get(0)).getOrder() != SortOrder.DESC ^ this.seekBefore ? (l.size() == 1 ? l.get(0).gt((Field)r.get(0)) : DSL.row(l).gt(DSL.row(r))) : (l.size() == 1 ? l.get(0).lt((Field)r.get(0)) : DSL.row(l).lt(DSL.row(r))));
        } else {
            ConditionProviderImpl or = new ConditionProviderImpl();
            for (int i = 0; i < o.size(); ++i) {
                if (s2.get(i) instanceof NoField) continue;
                ConditionProviderImpl and = new ConditionProviderImpl();
                for (int j = 0; j < i; ++j) {
                    if (s2.get(j) instanceof NoField) continue;
                    and.addConditions(((SortField)o.get(j)).$field().eq((Field)s2.get(j)));
                }
                SortField sf = (SortField)o.get(i);
                if (sf.getOrder() != SortOrder.DESC ^ this.seekBefore) {
                    and.addConditions(sf.$field().gt((Field)s2.get(i)));
                } else {
                    and.addConditions(sf.$field().lt((Field)s2.get(i)));
                }
                or.addConditions(Operator.OR, (Condition)and);
            }
            c = or.getWhere();
        }
        if (o.size() > 1 && Boolean.TRUE.equals(ctx.settings().isRenderRedundantConditionForSeekClause())) {
            c = ((SortField)o.get(0)).getOrder() != SortOrder.DESC ^ this.seekBefore ? ((SortField)o.get(0)).$field().ge((Field)s2.get(0)).and(c) : ((SortField)o.get(0)).$field().le((Field)s2.get(0)).and(c);
        }
        return c;
    }

    @Pro
    final ConditionProviderImpl getConnectBy() {
        return this.connectBy;
    }

    @Pro
    final ConditionProviderImpl getConnectByStartWith() {
        return this.connectByStartWith;
    }

    final GroupFieldList getGroupBy() {
        return this.groupBy;
    }

    final ConditionProviderImpl getHaving() {
        return this.having;
    }

    final ConditionProviderImpl getQualify() {
        return this.qualify;
    }

    final SortFieldList getOrderBy() {
        return this.unionOp.size() == 0 ? this.orderBy : this.unionOrderBy;
    }

    final QueryPartList<Field<?>> getSeek() {
        return this.unionOp.size() == 0 ? this.seek : this.unionSeek;
    }

    final Limit getLimit() {
        return this.unionOp.size() == 0 ? this.limit : this.unionLimit;
    }

    final SortFieldList getNonEmptyOrderBy(Configuration configuration) {
        if (this.getOrderBy().isEmpty()) {
            SortFieldList result = new SortFieldList();
            switch (configuration.family()) {
                case DB2: {
                    result.add(DSL.one().asc());
                    break;
                }
                case SYBASE: {
                    result.add(DSL.field("@@version").asc());
                    break;
                }
                default: {
                    result.add(DSL.field("({select} 0)").asc());
                }
            }
            return result;
        }
        return this.getOrderBy();
    }

    final SortFieldList getNonEmptyOrderByForDistinct(Configuration configuration) {
        SortFieldList order = new SortFieldList();
        order.addAll(this.getNonEmptyOrderBy(configuration));
        for (Field<?> field2 : this.getSelect()) {
            order.add(field2.asc());
        }
        return order;
    }

    @Override
    public final void addOrderBy(Collection<? extends OrderField<?>> fields) {
        this.getOrderBy().addAll(Tools.sortFields(fields));
    }

    @Override
    public final void addOrderBy(OrderField<?> ... fields) {
        this.addOrderBy(Arrays.asList(fields));
    }

    @Override
    public final void addOrderBy(int ... fieldIndexes) {
        this.addOrderBy(Tools.map(fieldIndexes, v -> DSL.inline(v)));
    }

    @Override
    @Pro
    public final void setOrderBySiblings(boolean orderBySiblings) {
        if (this.unionOp.size() == 0) {
            this.orderBySiblings = orderBySiblings;
        } else {
            this.unionOrderBySiblings = orderBySiblings;
        }
    }

    @Override
    public final void addSeekAfter(Field<?> ... fields) {
        this.addSeekAfter(Arrays.asList(fields));
    }

    @Override
    public final void addSeekAfter(Collection<? extends Field<?>> fields) {
        if (this.unionOp.size() == 0) {
            this.seekBefore = false;
        } else {
            this.unionSeekBefore = false;
        }
        this.getSeek().addAll((Collection<Field<?>>)fields);
    }

    @Override
    @Deprecated
    public final void addSeekBefore(Field<?> ... fields) {
        this.addSeekBefore(Arrays.asList(fields));
    }

    @Override
    @Deprecated
    public final void addSeekBefore(Collection<? extends Field<?>> fields) {
        if (this.unionOp.size() == 0) {
            this.seekBefore = true;
        } else {
            this.unionSeekBefore = true;
        }
        this.getSeek().addAll((Collection<Field<?>>)fields);
    }

    @Override
    public final void addConditions(Condition conditions) {
        this.condition.addConditions(conditions);
    }

    @Override
    public final void addConditions(Condition ... conditions) {
        this.condition.addConditions(conditions);
    }

    @Override
    public final void addConditions(Collection<? extends Condition> conditions) {
        this.condition.addConditions(conditions);
    }

    @Override
    public final void addConditions(Operator operator, Condition conditions) {
        this.condition.addConditions(operator, conditions);
    }

    @Override
    public final void addConditions(Operator operator, Condition ... conditions) {
        this.condition.addConditions(operator, conditions);
    }

    @Override
    public final void addConditions(Operator operator, Collection<? extends Condition> conditions) {
        this.condition.addConditions(operator, conditions);
    }

    @Pro
    final void setConnectByNoCycle(boolean connectByNoCycle) {
        this.connectByNoCycle = connectByNoCycle;
    }

    @Pro
    final void setStartWith(Condition condition) {
        this.connectByStartWith.addConditions(condition);
    }

    final void setHint(String hint) {
        this.hint = hint;
    }

    final void setOption(String option) {
        this.option = option;
    }

    @Override
    public final void addFrom(Collection<? extends TableLike<?>> f) {
        for (TableLike<?> provider : f) {
            this.getFrom().add(provider.asTable());
        }
    }

    @Override
    public final void addFrom(TableLike<?> f) {
        this.getFrom().add(f.asTable());
    }

    @Override
    public final void addFrom(TableLike<?> ... f) {
        for (TableLike<?> provider : f) {
            this.getFrom().add(provider.asTable());
        }
    }

    @Override
    @Pro
    public final void addConnectBy(Condition c) {
        this.getConnectBy().addConditions(c);
    }

    @Override
    @Pro
    public final void addConnectByNoCycle(Condition c) {
        this.getConnectBy().addConditions(c);
        this.setConnectByNoCycle(true);
    }

    @Override
    @Pro
    public final void setConnectByStartWith(Condition c) {
        this.setStartWith(c);
    }

    @Override
    public final void addGroupBy(Collection<? extends GroupField> fields) {
        if (fields.isEmpty()) {
            this.groupBy.add(DSL.emptyGroupingSet());
        } else {
            this.groupBy.addAll(fields);
        }
    }

    @Override
    public final void setGroupByDistinct(boolean groupByDistinct) {
        this.groupByDistinct = groupByDistinct;
    }

    @Override
    public final void addGroupBy(GroupField ... fields) {
        this.addGroupBy(Arrays.asList(fields));
    }

    @Override
    public final void addHaving(Condition conditions) {
        this.getHaving().addConditions(conditions);
    }

    @Override
    public final void addHaving(Condition ... conditions) {
        this.getHaving().addConditions(conditions);
    }

    @Override
    public final void addHaving(Collection<? extends Condition> conditions) {
        this.getHaving().addConditions(conditions);
    }

    @Override
    public final void addHaving(Operator operator, Condition conditions) {
        this.getHaving().addConditions(operator, conditions);
    }

    @Override
    public final void addHaving(Operator operator, Condition ... conditions) {
        this.getHaving().addConditions(operator, conditions);
    }

    @Override
    public final void addHaving(Operator operator, Collection<? extends Condition> conditions) {
        this.getHaving().addConditions(operator, conditions);
    }

    @Override
    public final void addWindow(WindowDefinition ... definitions) {
        this.addWindow0(Arrays.asList(definitions));
    }

    @Override
    public final void addWindow(Collection<? extends WindowDefinition> definitions) {
        this.addWindow0(definitions);
    }

    final void addWindow0(Collection<? extends WindowDefinition> definitions) {
        if (this.window == null) {
            this.window = new WindowList();
        }
        this.window.addAll(definitions);
    }

    @Override
    public final void addQualify(Condition conditions) {
        this.getQualify().addConditions(conditions);
    }

    @Override
    public final void addQualify(Condition ... conditions) {
        this.getQualify().addConditions(conditions);
    }

    @Override
    public final void addQualify(Collection<? extends Condition> conditions) {
        this.getQualify().addConditions(conditions);
    }

    @Override
    public final void addQualify(Operator operator, Condition conditions) {
        this.getQualify().addConditions(operator, conditions);
    }

    @Override
    public final void addQualify(Operator operator, Condition ... conditions) {
        this.getQualify().addConditions(operator, conditions);
    }

    @Override
    public final void addQualify(Operator operator, Collection<? extends Condition> conditions) {
        this.getQualify().addConditions(operator, conditions);
    }

    private final SelectQueryImpl<R> combine(CombineOperator op, Select<? extends R> other) {
        if (this == other || other instanceof SelectImpl && this == ((SelectImpl)other).getDelegate()) {
            throw new IllegalArgumentException("In jOOQ 3.x's mutable DSL API, it is not possible to use the same instance of a Select query on both sides of a set operation like s.union(s)");
        }
        int index = this.unionOp.size() - 1;
        if (index == -1 || this.unionOp.get(index) != op || op == CombineOperator.EXCEPT || op == CombineOperator.EXCEPT_ALL) {
            this.unionOp.add(op);
            this.union.add(new QueryPartList());
            ++index;
        }
        this.union.get(index).add(other);
        return this;
    }

    @Override
    public final SelectQueryImpl<R> union(Select<? extends R> other) {
        return this.combine(CombineOperator.UNION, other);
    }

    @Override
    public final SelectQueryImpl<R> unionAll(Select<? extends R> other) {
        return this.combine(CombineOperator.UNION_ALL, other);
    }

    @Override
    public final SelectQueryImpl<R> except(Select<? extends R> other) {
        return this.combine(CombineOperator.EXCEPT, other);
    }

    @Override
    public final SelectQueryImpl<R> exceptAll(Select<? extends R> other) {
        return this.combine(CombineOperator.EXCEPT_ALL, other);
    }

    @Override
    public final SelectQueryImpl<R> intersect(Select<? extends R> other) {
        return this.combine(CombineOperator.INTERSECT, other);
    }

    @Override
    public final SelectQueryImpl<R> intersectAll(Select<? extends R> other) {
        return this.combine(CombineOperator.INTERSECT_ALL, other);
    }

    @Override
    public final void addJoin(TableLike<?> table, Condition conditions) {
        this.addJoin(table, JoinType.JOIN, conditions);
    }

    @Override
    public final void addJoin(TableLike<?> table, Condition ... conditions) {
        this.addJoin(table, JoinType.JOIN, conditions);
    }

    @Override
    public final void addJoin(TableLike<?> table, JoinType type, Condition conditions) {
        this.addJoin0(table, type, conditions, null);
    }

    @Override
    public final void addJoin(TableLike<?> table, JoinType type, Condition ... conditions) {
        this.addJoin0(table, type, conditions, null);
    }

    @Override
    @Pro
    public final void addJoin(TableLike<?> table, JoinType type, Condition conditions, Field<?>[] partitionBy) {
        this.addJoin0(table, type, conditions, partitionBy);
    }

    @Override
    @Pro
    public final void addJoin(TableLike<?> table, JoinType type, Condition[] conditions, Field<?>[] partitionBy) {
        this.addJoin0(table, type, conditions, partitionBy);
    }

    private final void addJoin0(TableLike<?> table, JoinType type, Object conditions, Field<?>[] partitionBy) {
        int index = this.getFrom().size() - 1;
        Table<Record> joined = null;
        switch (type) {
            case LEFT_ANTI_JOIN: 
            case LEFT_SEMI_JOIN: 
            case STRAIGHT_JOIN: 
            case FULL_OUTER_JOIN: 
            case JOIN: {
                TableOptionalOnStep<Record> o = ((Table)this.getFrom().get(index)).join(table, type);
                if (conditions instanceof Condition) {
                    Condition c = (Condition)conditions;
                    joined = o.on(c);
                    break;
                }
                joined = o.on((Condition[])conditions);
                break;
            }
            case LEFT_OUTER_JOIN: 
            case RIGHT_OUTER_JOIN: {
                TablePartitionByStep p = (TablePartitionByStep)((Object)((Table)this.getFrom().get(index)).join(table, type));
                TableOnStep o = p;
                if (partitionBy != null && partitionBy.length > 0) {
                    o = p.partitionBy(partitionBy);
                }
                if (conditions instanceof Condition) {
                    Condition c = (Condition)conditions;
                    joined = o.on(c);
                    break;
                }
                joined = o.on((Condition[])conditions);
                break;
            }
            case CROSS_APPLY: 
            case OUTER_APPLY: 
            case NATURAL_LEFT_OUTER_JOIN: 
            case NATURAL_RIGHT_OUTER_JOIN: 
            case NATURAL_FULL_OUTER_JOIN: 
            case CROSS_JOIN: 
            case NATURAL_JOIN: {
                joined = ((Table)this.getFrom().get(index)).join(table, type);
                break;
            }
            default: {
                throw new IllegalArgumentException("Bad join type: " + String.valueOf((Object)type));
            }
        }
        this.getFrom().set(index, joined);
    }

    @Override
    public final void addJoinOnKey(TableLike<?> table, JoinType type) throws DataAccessException {
        int index = this.getFrom().size() - 1;
        TableOnConditionStep joined = null;
        switch (type) {
            case LEFT_OUTER_JOIN: 
            case LEFT_ANTI_JOIN: 
            case LEFT_SEMI_JOIN: 
            case RIGHT_OUTER_JOIN: 
            case FULL_OUTER_JOIN: 
            case JOIN: {
                joined = ((Table)this.getFrom().get(index)).join(table, type).onKey();
                break;
            }
            default: {
                throw new IllegalArgumentException("JoinType " + String.valueOf((Object)type) + " is not supported with the addJoinOnKey() method. Use INNER or OUTER JOINs only");
            }
        }
        this.getFrom().set(index, joined);
    }

    @Override
    public final void addJoinOnKey(TableLike<?> table, JoinType type, TableField<?, ?> ... keyFields) throws DataAccessException {
        int index = this.getFrom().size() - 1;
        TableOnConditionStep joined = null;
        switch (type) {
            case LEFT_OUTER_JOIN: 
            case LEFT_ANTI_JOIN: 
            case LEFT_SEMI_JOIN: 
            case RIGHT_OUTER_JOIN: 
            case FULL_OUTER_JOIN: 
            case JOIN: {
                joined = ((Table)this.getFrom().get(index)).join(table, type).onKey(keyFields);
                break;
            }
            default: {
                throw new IllegalArgumentException("JoinType " + String.valueOf((Object)type) + " is not supported with the addJoinOnKey() method. Use INNER or OUTER JOINs only");
            }
        }
        this.getFrom().set(index, joined);
    }

    @Override
    public final void addJoinOnKey(TableLike<?> table, JoinType type, ForeignKey<?, ?> key) {
        int index = this.getFrom().size() - 1;
        TableOnConditionStep joined = null;
        switch (type) {
            case LEFT_OUTER_JOIN: 
            case LEFT_ANTI_JOIN: 
            case LEFT_SEMI_JOIN: 
            case RIGHT_OUTER_JOIN: 
            case FULL_OUTER_JOIN: 
            case JOIN: {
                joined = ((Table)this.getFrom().get(index)).join(table, type).onKey(key);
                break;
            }
            default: {
                throw new IllegalArgumentException("JoinType " + String.valueOf((Object)type) + " is not supported with the addJoinOnKey() method. Use INNER or OUTER JOINs only");
            }
        }
        this.getFrom().set(index, joined);
    }

    @Override
    public final void addJoinUsing(TableLike<?> table, Collection<? extends Field<?>> fields) {
        this.addJoinUsing(table, JoinType.JOIN, fields);
    }

    @Override
    public final void addJoinUsing(TableLike<?> table, JoinType type, Collection<? extends Field<?>> fields) {
        int index = this.getFrom().size() - 1;
        Table<Record> joined = null;
        switch (type) {
            case LEFT_OUTER_JOIN: 
            case LEFT_ANTI_JOIN: 
            case LEFT_SEMI_JOIN: 
            case RIGHT_OUTER_JOIN: 
            case FULL_OUTER_JOIN: 
            case JOIN: {
                joined = ((Table)this.getFrom().get(index)).join(table, type).using(fields);
                break;
            }
            default: {
                throw new IllegalArgumentException("JoinType " + String.valueOf((Object)type) + " is not supported with the addJoinUsing() method. Use INNER or OUTER JOINs only");
            }
        }
        this.getFrom().set(index, joined);
    }

    @Override
    public final void addHint(String h2) {
        this.setHint(h2);
    }

    @Override
    public final void addOption(String o) {
        this.setOption(o);
    }

    @Override
    public final WithImpl $with() {
        return this.with;
    }

    @Override
    public final SelectQueryImpl<?> $with(QOM.With newWith) {
        return this.copy(s2 -> {}, (WithImpl)newWith);
    }

    @Override
    public final QOM.UnmodifiableList<SelectFieldOrAsterisk> $select() {
        return QOM.unmodifiable(this.select);
    }

    @Override
    public final SelectQueryImpl<?> $select(Collection<? extends SelectFieldOrAsterisk> newSelect) {
        return this.copy(s2 -> {
            s2.select.clear();
            s2.select.addAll(newSelect);
        });
    }

    @Override
    public final boolean $distinct() {
        return this.distinct;
    }

    @Override
    public final Select<R> $distinct(boolean newDistinct) {
        if (this.$distinct() == newDistinct) {
            return this;
        }
        return this.copy(s2 -> {
            s2.distinct = newDistinct;
        });
    }

    @Override
    public final QOM.UnmodifiableList<SelectFieldOrAsterisk> $distinctOn() {
        return QOM.unmodifiable(this.distinctOn);
    }

    @Override
    public final Select<R> $distinctOn(Collection<? extends SelectFieldOrAsterisk> newDistinctOn) {
        return this.copy(s2 -> {
            s2.distinctOn.clear();
            s2.distinctOn.addAll((Collection<SelectFieldOrAsterisk>)newDistinctOn);
        });
    }

    @Override
    public final QOM.UnmodifiableList<Table<?>> $from() {
        return QOM.unmodifiable(this.from);
    }

    @Override
    public final SelectQueryImpl<R> $from(Collection<? extends Table<?>> newFrom) {
        return this.copy(s2 -> {
            s2.from.clear();
            s2.from.addAll(newFrom);
        });
    }

    @Override
    public final Condition $where() {
        return this.condition.getWhereOrNull();
    }

    @Override
    public final Select<R> $where(Condition newWhere) {
        if (this.$where() == newWhere) {
            return this;
        }
        return this.copy(s2 -> s2.condition.setWhere(newWhere));
    }

    @Override
    public final QOM.UnmodifiableList<GroupField> $groupBy() {
        return QOM.unmodifiable(this.groupBy);
    }

    @Override
    public final Select<R> $groupBy(Collection<? extends GroupField> newGroupBy) {
        return this.copy(s2 -> {
            s2.groupBy.clear();
            s2.groupBy.addAll(newGroupBy);
        });
    }

    @Override
    public final boolean $groupByDistinct() {
        return this.groupByDistinct;
    }

    @Override
    public final Select<R> $groupByDistinct(boolean newGroupByDistinct) {
        if (this.$groupByDistinct() == newGroupByDistinct) {
            return this;
        }
        return this.copy(s2 -> {
            s2.groupByDistinct = newGroupByDistinct;
        });
    }

    @Override
    public final Condition $having() {
        return this.having.getWhereOrNull();
    }

    @Override
    public final Select<R> $having(Condition newHaving) {
        if (this.$having() == newHaving) {
            return this;
        }
        return this.copy(s2 -> s2.having.setWhere(newHaving));
    }

    @Override
    public final QOM.UnmodifiableList<? extends WindowDefinition> $window() {
        return QOM.unmodifiable(this.window == null ? QueryPartList.emptyList() : this.window);
    }

    @Override
    public final Select<R> $window(Collection<? extends WindowDefinition> newWindow) {
        return this.copy(s2 -> {
            s2.window.clear();
            s2.window.addAll(newWindow);
        });
    }

    @Override
    public final Condition $qualify() {
        return this.qualify.getWhereOrNull();
    }

    @Override
    public final Select<R> $qualify(Condition newQualify) {
        if (this.$qualify() == newQualify) {
            return this;
        }
        return this.copy(s2 -> s2.qualify.setWhere(newQualify));
    }

    @Override
    public final QOM.UnmodifiableList<SortField<?>> $orderBy() {
        return QOM.unmodifiable(this.orderBy);
    }

    @Override
    public final Select<R> $orderBy(Collection<? extends SortField<?>> newOrderBy) {
        return this.copy(s2 -> {
            s2.orderBy.clear();
            s2.orderBy.addAll(newOrderBy);
        });
    }

    @Override
    public final Field<? extends Number> $limit() {
        return this.getLimit().limit;
    }

    @Override
    public final Select<R> $limit(Field<? extends Number> newLimit) {
        if (this.$limit() == newLimit) {
            return this;
        }
        return this.copy(s2 -> s2.getLimit().setLimit(newLimit));
    }

    @Override
    public final boolean $limitPercent() {
        return this.getLimit().percent;
    }

    @Override
    public final Select<R> $limitPercent(boolean newLimitPercent) {
        if (this.$limitPercent() == newLimitPercent) {
            return this;
        }
        return this.copy(s2 -> s2.getLimit().setPercent(newLimitPercent));
    }

    @Override
    public final boolean $limitWithTies() {
        return this.getLimit().withTies;
    }

    @Override
    public final Select<R> $limitWithTies(boolean newLimitWithTies) {
        if (this.$limitPercent() == newLimitWithTies) {
            return this;
        }
        return this.copy(s2 -> s2.getLimit().setWithTies(newLimitWithTies));
    }

    @Override
    public final Field<? extends Number> $offset() {
        return this.getLimit().offset;
    }

    @Override
    public final Select<R> $offset(Field<? extends Number> newOffset) {
        if (this.$limit() == newOffset) {
            return this;
        }
        return this.copy(s2 -> s2.getLimit().setOffset(newOffset));
    }

    @Override
    public final Condition $connectBy() {
        return this.connectBy.getWhereOrNull();
    }

    @Override
    public final Select<R> $connectBy(Condition newConnectBy) {
        if (this.$connectBy() == newConnectBy) {
            return this;
        }
        return this.copy(s2 -> s2.connectBy.setWhere(newConnectBy));
    }

    @Override
    public final boolean $connectByNoCycle() {
        return this.connectByNoCycle;
    }

    @Override
    public final Select<R> $connectByNoCycle(boolean newConnectByNoCycle) {
        if (this.$connectByNoCycle() == newConnectByNoCycle) {
            return this;
        }
        return this.copy(s2 -> {
            s2.connectByNoCycle = newConnectByNoCycle;
        });
    }

    @Override
    public final Condition $connectByStartWith() {
        return this.connectByStartWith.getWhereOrNull();
    }

    @Override
    public final Select<R> $connectByStartWith(Condition newConnectByStartWith) {
        if (this.$connectByStartWith() == newConnectByStartWith) {
            return this;
        }
        return this.copy(s2 -> s2.connectByStartWith.setWhere(newConnectByStartWith));
    }

    public final <T> T $traverse(Traverser<?, T> traverser) {
        return QOM.traverse(traverser, this, this.$with(), this.$select(), this.$distinct(), this.$distinctOn(), this.$from(), this.$where(), this.$groupBy(), this.$having(), this.$window(), this.$qualify(), this.$orderBy());
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$with(), this.$select(), this.$distinct(), this.$distinctOn(), this.$from(), this.$where(), this.$groupBy(), this.$groupByDistinct(), this.$having(), this.$window(), this.$qualify(), this.$orderBy(), this.$offset(), this.$limit(), this.$limitPercent(), this.$limitWithTies(), this.$connectBy(), this.$connectByNoCycle(), this.$connectByStartWith(), (cte, s2, d, don, f, c, g2, gd, h2, w, q, o, off, l, lp, lt, cb, cbnc, cbsw) -> {
            SelectQueryImpl<R> r = new SelectQueryImpl<R>(this.configuration(), (WithImpl)cte, (boolean)d);
            r.select.addAll(s2);
            r.distinctOn.addAll((Collection<SelectFieldOrAsterisk>)don);
            r.from.addAll(f);
            r.condition.addConditions(ConditionProviderImpl.extractCondition(c));
            r.groupBy.addAll(g2);
            r.groupByDistinct = gd;
            r.having.addConditions(ConditionProviderImpl.extractCondition(h2));
            r.addWindow0((Collection<WindowDefinition>)w);
            r.qualify.addConditions(ConditionProviderImpl.extractCondition(q));
            r.orderBy.addAll(o);
            r.limit.setOffset((Field<? extends Number>)off);
            r.limit.setLimit((Field<? extends Number>)l);
            r.limit.setPercent((boolean)lp);
            r.limit.setWithTies((boolean)lt);
            r.connectBy.addConditions(ConditionProviderImpl.extractCondition(cb));
            r.connectByNoCycle = cbnc;
            r.connectByStartWith.addConditions(ConditionProviderImpl.extractCondition(cbsw));
            return r;
        }, replacer);
    }

    private static enum CopyClause {
        START,
        WHERE,
        QUALIFY,
        END;


        final boolean between(CopyClause startInclusive, CopyClause endExclusive) {
            return this.compareTo(startInclusive) >= 0 && this.compareTo(endExclusive) < 0;
        }
    }

    private static class ConnectByEmulation {
        private static final List<Field<?>> LEVEL = Arrays.asList(new Level(), new Rownum());

        private ConnectByEmulation() {
        }
    }

    @Pro
    final class ForAutoEmulation {
        final DataType<?> type;
        final Scope scope;
        final char pathSeparator;

        ForAutoEmulation(DataType<?> type, Scope scope) {
            this.type = type;
            this.scope = scope;
            this.pathSeparator = (char)(type == SQLDataType.XML ? 47 : 46);
        }

        final Field<?> xmlelement0(List<Field<?>> children, Name elementName, Field<?> content) {
            if (SelectQueryImpl.this.forXML.forXMLElements || SelectQueryImpl.this.forXML.forXMLPath) {
                ArrayList elements = new ArrayList();
                if (SelectQueryImpl.this.forXML.forXMLPath) {
                    elements.addAll(new PathTree().init(children).xmlelements());
                } else {
                    for (Field<?> child : children) {
                        elements.add(DSL.xmlelement(child.getUnqualifiedName(), child));
                    }
                }
                if (content != null) {
                    elements.add(content);
                }
                return DSL.xmlelement(elementName, elements);
            }
            if (content != null) {
                return DSL.xmlelement(elementName, DSL.xmlattributes(children), content);
            }
            return DSL.xmlelement(elementName, DSL.xmlattributes(children), new Field[0]);
        }

        final Field<?> jsonObject0(JSONEntry<?> content) {
            return Multiset.returningClob(this.scope, this.onNull(this.type == SQLDataType.JSONB ? DSL.jsonbObject(content) : DSL.jsonObject(content)));
        }

        final Field<?> jsonObject0(Collection<? extends JSONEntry<?>> content) {
            return Multiset.returningClob(this.scope, this.onNull(this.type == SQLDataType.JSONB ? DSL.jsonbObject(content) : DSL.jsonObject(content)));
        }

        final Field<?> jsonObject0(List<Field<?>> children, Name elementName, Field<?> content) {
            ArrayList entries = new ArrayList();
            if (SelectQueryImpl.this.forJSON.forJSONPath) {
                entries.addAll(new PathTree().init(children).jsonEntries());
            } else {
                for (Field<?> child : children) {
                    entries.add(DSL.key(DSL.inline(child.getName())).value(child));
                }
            }
            if (content != null) {
                entries.add(DSL.key(DSL.inline(elementName.last())).value(content));
            }
            return this.jsonObject0(entries);
        }

        final JSONObjectReturningStep<?> onNull(JSONObjectNullStep<?> nullStep) {
            return SelectQueryImpl.this.forJSON.forJSONIncludeNullValues || JSONNull.NO_SUPPORT_ABSENT_ON_NULL.contains((Object)this.scope.dialect()) ? nullStep.nullOnNull() : nullStep.absentOnNull();
        }

        final Field<?> record0(List<Field<?>> children, Name elementName) {
            return this.record0(children, elementName, null);
        }

        final Field<?> record0(List<Field<?>> children, Name elementName, Field<?> content) {
            return this.type == SQLDataType.XML ? this.xmlelement0(children, elementName, content) : this.jsonObject0(children, elementName, content);
        }

        private Field<?> agg0(Field<?> field2, SortFieldList o, boolean topLevelAgg) {
            return this.type == SQLDataType.XML ? (o.isEmpty() ? DSL.xmlagg(field2) : DSL.xmlagg(field2).orderBy(o)) : (SelectQueryImpl.this.forJSON.forJSONWithoutArrayWrapper && topLevelAgg && JSONArrayAgg.EMULATE_WITH_GROUP_CONCAT.contains((Object)this.scope.dialect()) ? JSONArrayAgg.groupConcatEmulationWithoutArrayWrappers(false, field2, o) : (o.isEmpty() ? (this.type == SQLDataType.JSONB ? Multiset.returningClob(this.scope, DSL.jsonbArrayAgg(field2)) : Multiset.returningClob(this.scope, DSL.jsonArrayAgg(field2))) : (this.type == SQLDataType.JSONB ? Multiset.returningClob(this.scope, DSL.jsonbArrayAgg(field2).orderBy(o)) : Multiset.returningClob(this.scope, DSL.jsonArrayAgg(field2).orderBy(o)))));
        }

        /*
         * WARNING - void declaration
         */
        final Select<?> select() {
            Field<Object> f;
            boolean auto = this.type == SQLDataType.XML ? SelectQueryImpl.this.forXML.forXMLAuto : SelectQueryImpl.this.forJSON.forJSONAuto;
            Table first = null;
            Table previous = null;
            LinkedHashMap childrenPerTable = new LinkedHashMap();
            for (Field<?> f2 : SelectQueryImpl.this.getSelect()) {
                if (!((f2 = Tools.unalias(f2)) instanceof TableField)) continue;
                previous = first = ((TableField)f2).getTable();
                if (first == null) continue;
                break;
            }
            for (Field<?> f2 : SelectQueryImpl.this.getSelect()) {
                void var10_9;
                Field<?> aliased = Tools.unalias(f2);
                Table t2 = auto && aliased instanceof TableField && ((TableField)aliased).getTable() != null ? ((TableField)aliased).getTable() : previous;
                List list = (List)childrenPerTable.get(t2);
                if (list == null) {
                    previous = t2;
                    ArrayList arrayList = new ArrayList();
                    childrenPerTable.put(previous, arrayList);
                }
                var10_9.add(Tools.qualify(DSL.table(DSL.name("t")), f2).as(f2.getUnqualifiedName()));
            }
            Table firstTable = first;
            ArrayList autoColumns = null;
            ArrayList<Table> autoTables = null;
            if (auto && childrenPerTable.size() > 1) {
                autoColumns = new ArrayList();
                autoTables = new ArrayList<Table>();
                ArrayList sublist = new ArrayList();
                for (Map.Entry entry : childrenPerTable.entrySet()) {
                    sublist.addAll((Collection)entry.getValue());
                    autoTables.add((Table)entry.getKey());
                    autoColumns.add(new ArrayList(sublist));
                }
            }
            Select s2 = SelectQueryImpl.this.copy(x -> {});
            s2.forXML = null;
            s2.forJSON = null;
            if (autoColumns != null && autoTables != null) {
                void var10_15;
                f = this.agg0(DSL.field(DSL.name("e"), this.type), new SortFieldList(), true);
                int n = autoColumns.size() - 1;
                while (var10_15 > 0) {
                    ArrayList<SelectField> l = new ArrayList<SelectField>();
                    if (var10_15 > true) {
                        l.addAll((Collection)autoColumns.get((int)(var10_15 - 2)));
                    }
                    l.add(this.record0((List)childrenPerTable.get(autoTables.get((int)(var10_15 - true))), ((Table)autoTables.get((int)(this.type == SQLDataType.XML ? var10_15 - true : var10_15))).getUnqualifiedName(), var10_15 == autoColumns.size() - 1 ? this.agg0(this.record0((List)childrenPerTable.get(autoTables.get((int)var10_15)), ((Table)autoTables.get((int)var10_15)).getUnqualifiedName()), new SortFieldList(), false) : f).as("e"));
                    s2 = DSL.select(l).from((TableLike<?>)s2.asTable("t")).groupBy((Collection)autoColumns.get((int)(var10_15 - true)));
                    --var10_15;
                }
            } else {
                Name name = SelectQueryImpl.this.forXML != null ? (SelectQueryImpl.this.forXML.forXMLPath ? (SelectQueryImpl.this.forXML.forXMLPathElement != null ? DSL.name(SelectQueryImpl.this.forXML.forXMLPathElement) : DSL.name("row")) : (SelectQueryImpl.this.forXML.forXMLRaw ? (SelectQueryImpl.this.forXML.forXMLRawElement != null ? DSL.name(SelectQueryImpl.this.forXML.forXMLRawElement) : DSL.name("row")) : (firstTable != null ? firstTable.getUnqualifiedName() : DSL.name("row")))) : DSL.name("row");
                f = this.agg0(this.record0((List)childrenPerTable.get(firstTable), name), new SortFieldList(), true);
            }
            if (SelectQueryImpl.this.forXML != null && SelectQueryImpl.this.forXML.forXMLRoot) {
                f = DSL.xmlelement(SelectQueryImpl.this.forXML.forXMLRootElement != null ? DSL.name(SelectQueryImpl.this.forXML.forXMLRootElement) : (SelectQueryImpl.this.forXML.forXMLPath ? DSL.name("root") : (SelectQueryImpl.this.getFrom().isEmpty() ? DSL.name("root") : ((Table)SelectQueryImpl.this.getFrom().get(SelectQueryImpl.this.getFrom().size() - 1)).getUnqualifiedName())), f);
            } else if (SelectQueryImpl.this.forJSON != null && SelectQueryImpl.this.forJSON.forJSONRoot) {
                f = this.jsonObject0(DSL.key(DSL.inline(SelectQueryImpl.this.forJSON.forJSONRootElement != null ? SelectQueryImpl.this.forJSON.forJSONRootElement : (SelectQueryImpl.this.forJSON.forJSONPath ? "root" : (SelectQueryImpl.this.getFrom().isEmpty() ? "root" : ((Table)SelectQueryImpl.this.getFrom().get(SelectQueryImpl.this.getFrom().size() - 1)).getName())))).value(f));
            }
            if (SelectQueryImpl.this.forJSON != null && SelectQueryImpl.this.forJSON.forJSONWithoutArrayWrapper && !JSONArrayAgg.EMULATE_WITH_GROUP_CONCAT.contains((Object)this.scope.dialect())) {
                f = DSL.regexpReplaceAll(f.cast(SQLDataType.VARCHAR), DSL.inline("^\\[(.*)\\]$"), this.scope.family() == SQLDialect.H2 ? DSL.inline("$1") : DSL.inline("\\1"));
            }
            SelectJoinStep selectJoinStep = DSL.select(f).from((TableLike<?>)s2.asTable("t"));
            if (SelectQueryImpl.this.forJSON != null) {
                return JSONArrayAgg.patchOracleArrayAggBug(this.scope, selectJoinStep);
            }
            return selectJoinStep;
        }

        @Pro
        final class PathTree {
            final String name;
            Field<?> field;
            Map<String, org.jooq.impl.SelectQueryImpl$ForAutoEmulation.PathTree> children;

            PathTree() {
                this(null);
            }

            PathTree(String name) {
                this.name = name;
            }

            final org.jooq.impl.SelectQueryImpl$ForAutoEmulation.PathTree init(String n, Field<?> f) {
                String s2;
                int i = n.indexOf(ForAutoEmulation.this.pathSeparator);
                String string = s2 = i >= 0 ? n.substring(0, i) : n;
                if (this.children == null) {
                    this.children = new LinkedHashMap<String, org.jooq.impl.SelectQueryImpl$ForAutoEmulation.PathTree>();
                }
                PathTree tree = this.children.computeIfAbsent(s2, x$0 -> new PathTree((String)x$0));
                if (i >= 0) {
                    tree.init(n.substring(i + 1), f);
                } else {
                    tree.field = f;
                }
                return this;
            }

            final org.jooq.impl.SelectQueryImpl$ForAutoEmulation.PathTree init(List<Field<?>> list) {
                for (Field<?> f : list) {
                    this.init(f.getName(), f);
                }
                return this;
            }

            final List<Field<?>> xmlelements() {
                ArrayList list = new ArrayList();
                if (this.children != null) {
                    for (PathTree pathTree : this.children.values()) {
                        list.addAll(pathTree.xmlelements());
                    }
                }
                if (this.field != null) {
                    list.add(this.field);
                }
                return this.name == null ? list : Arrays.asList(DSL.xmlelement(DSL.name(this.name), list));
            }

            final List<JSONEntry<?>> jsonEntries() {
                if (this.field != null && this.name != null) {
                    return Arrays.asList(DSL.key(DSL.inline(this.name)).value(this.field));
                }
                ArrayList list = new ArrayList();
                if (this.children != null) {
                    for (PathTree pathTree : this.children.values()) {
                        list.addAll(pathTree.jsonEntries());
                    }
                }
                return this.name == null ? list : Arrays.asList(DSL.key(DSL.inline(this.name)).value(ForAutoEmulation.this.jsonObject0(list)));
            }

            public String toString() {
                StringBuilder sb = new StringBuilder();
                if (this.field != null) {
                    sb.append(this.field);
                }
                if (this.children != null) {
                    sb.append(this.field != null ? ", " : "").append(this.children);
                }
                return sb.toString();
            }
        }
    }

    @Pro
    static final class ExtractTableConditionsParams {
        final Map<Name, TableField<?, ?>> unqualifiedProjection;
        final Map<Name, Integer> ambiguousNames;

        ExtractTableConditionsParams(TableList tl) {
            this.unqualifiedProjection = SelectQueryImpl.unqualifiedProjection(tl);
            this.ambiguousNames = new HashMap<Name, Integer>();
            for (Table t2 : tl) {
                this.ambiguousNames.compute(t2.getUnqualifiedName(), (k, v) -> v == null ? 1 : v + 1);
            }
        }
    }

    @Pro
    static final class ExtractTableConditions {
        Condition condition;
        JoinType joinType;

        ExtractTableConditions(Condition c, JoinType j) {
            this.condition = c;
            this.joinType = j;
        }

        void from(ExtractTableConditions other) {
            this.condition = other.condition;
            this.joinType = other.joinType;
        }

        public String toString() {
            return String.valueOf((Object)this.joinType) + ": " + this.condition.toString();
        }
    }

    private record SelectFieldIndexes(boolean mapped, int[] mapping, int[] projectionSizes) {
    }

    @Pro
    final class RownumTransformation {
        final Context<?> ctx;

        RownumTransformation(Context<?> ctx) {
            this.ctx = ctx;
        }

        final Select<?> select() {
            ConditionProviderImpl c = SelectQueryImpl.this.getWhere(this.ctx);
            if (!c.hasWhere()) {
                return SelectQueryImpl.this;
            }
            if (c.getWhere() instanceof Or || c.getWhere() instanceof Xor) {
                return SelectQueryImpl.this;
            }
            Limit l = new Limit().from(SelectQueryImpl.this.getLimit());
            ConditionLimit result = this.extractLimit(new ConditionLimit(c.getWhere(), l));
            if (result.condition != c.getWhere() || result.limit != l) {
                return this.transform(result);
            }
            return SelectQueryImpl.this;
        }

        /*
         * Ignored method signature, as it can't be verified against descriptor
         */
        private final ConditionLimit extractLimit(ConditionLimit cl) {
            Condition condition = cl.condition;
            if (condition instanceof QOM.CompareCondition) {
                QOM.CompareCondition cc = (QOM.CompareCondition)condition;
                boolean rn1 = cc.$arg1() instanceof Rownum;
                boolean rn2 = cc.$arg2() instanceof Rownum;
                Val param2 = rn1 && cc.$arg2() instanceof Val ? (Val)cc.$arg2() : (rn2 && cc.$arg1() instanceof Val ? (Val)cc.$arg1() : null);
                Comparator comparator = Eq.comparator(cl.condition);
                if (param2 == cc.$arg1()) {
                    comparator = comparator.mirror();
                }
                if (param2 != null) {
                    Long value = Convert.convert(param2.getValue(), Long.TYPE);
                    switch (comparator) {
                        case EQUALS: {
                            if (value == 1L) break;
                            return cl.never();
                        }
                        case NOT_EQUALS: {
                            if (value <= 0L) {
                                return cl.always();
                            }
                            if (value == 1L) {
                                return cl.never();
                            }
                            param2 = param2.copy(value - 1L);
                            break;
                        }
                        case LESS: {
                            if (value <= 1L) {
                                return cl.never();
                            }
                            param2 = param2.copy(value - 1L);
                            break;
                        }
                        case LESS_OR_EQUAL: {
                            if (value > 0L) break;
                            return cl.never();
                        }
                        case GREATER: {
                            if (value >= 1L) {
                                return cl.never();
                            }
                            return cl.always();
                        }
                        case GREATER_OR_EQUAL: {
                            if (value >= 2L) {
                                return cl.never();
                            }
                            return cl.always();
                        }
                    }
                    ConditionLimit result = new ConditionLimit(DSL.noCondition(), new Limit().from(cl.limit));
                    result.limit.setLimit(param2);
                    return result;
                }
                if (rn1 && rn2) {
                    switch (comparator) {
                        case EQUALS: 
                        case LESS_OR_EQUAL: 
                        case GREATER_OR_EQUAL: {
                            return cl.always();
                        }
                    }
                    return cl.never();
                }
            } else {
                condition = cl.condition;
                if (condition instanceof And) {
                    And a = (And)condition;
                    return new ConditionLimit((Condition)a.$replace(QOM.internalReplacer(Replacer.of(p -> p instanceof Condition, old -> {
                        if (old instanceof And) {
                            return old;
                        }
                        ConditionLimit result = this.extractLimit(new ConditionLimit((Condition)old, cl.limit));
                        if (result.limit == cl.limit) {
                            return result.condition;
                        }
                        cl.limit.from(result.limit);
                        return result.condition;
                    }))), cl.limit);
                }
            }
            return cl;
        }

        /*
         * Ignored method signature, as it can't be verified against descriptor
         */
        private final SelectQueryImpl transform(ConditionLimit cl) {
            if (cl.limit.isApplicable()) {
                return SelectQueryImpl.this.nestTo(CopyClause.WHERE, true, s2 -> {
                    s2.condition.setWhere(cl.condition);
                    s2.limit.from(cl.limit);
                    return s2;
                });
            }
            return SelectQueryImpl.this.copy(s2 -> s2.condition.setWhere(cl.condition));
        }

        private final class ConditionLimit {
            final Limit limit;
            final Condition condition;

            ConditionLimit(Condition condition, Limit limit) {
                this.condition = condition;
                this.limit = limit;
            }

            final org.jooq.impl.SelectQueryImpl$RownumTransformation.ConditionLimit always() {
                return new ConditionLimit(DSL.noCondition(), this.limit);
            }

            final org.jooq.impl.SelectQueryImpl$RownumTransformation.ConditionLimit never() {
                return new ConditionLimit(DSL.falseCondition(), this.limit);
            }

            public String toString() {
                return String.valueOf(this.condition) + ":" + String.valueOf(this.limit);
            }
        }
    }
}

