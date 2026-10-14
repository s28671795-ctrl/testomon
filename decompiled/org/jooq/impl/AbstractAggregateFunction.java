/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.function.Function;
import org.jooq.AggregateFilterStep;
import org.jooq.AggregateFunction;
import org.jooq.ArrayAggOrderByStep;
import org.jooq.Condition;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.OrderField;
import org.jooq.OrderedAggregateFunction;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.SQL;
import org.jooq.SQLDialect;
import org.jooq.Traverser;
import org.jooq.WindowBeforeOverStep;
import org.jooq.impl.AbstractFunction;
import org.jooq.impl.AbstractWindowFunction;
import org.jooq.impl.ConditionProviderImpl;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.Lazy;
import org.jooq.impl.ListAgg;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartCollectionView;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.SortFieldList;
import org.jooq.impl.Tools;

abstract class AbstractAggregateFunction<T>
extends AbstractWindowFunction<T>
implements AggregateFunction<T>,
OrderedAggregateFunction<T>,
ArrayAggOrderByStep<T> {
    static final Set<SQLDialect> SUPPORT_COUNTIF = SQLDialect.supportedBy(SQLDialect.BIGQUERY, SQLDialect.SNOWFLAKE);
    static final Set<SQLDialect> SUPPORT_EMPTY_WITHIN_GROUP = SQLDialect.supportedBy(SQLDialect.DB2);
    static final Set<SQLDialect> NO_SUPPORT_FILTER = SQLDialect.supportedUntil(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.BIGQUERY, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.HANA, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_3, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_28, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA);
    static final Set<SQLDialect> SUPPORT_DISTINCT_RVE = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.POSTGRES);
    static final Lazy<Field<Integer>> ASTERISK = Lazy.of(() -> DSL.field(DSL.raw("*"), Integer.class));
    final QueryPartList<Field<?>> arguments;
    final boolean distinct;
    final ConditionProviderImpl filter;
    SortFieldList withinGroupOrderBy;
    SortFieldList keepDenseRankOrderBy;
    boolean first;

    AbstractAggregateFunction(String name, DataType<T> type, Field<?> ... arguments) {
        this(false, name, type, arguments);
    }

    AbstractAggregateFunction(Name name, DataType<T> type, Field<?> ... arguments) {
        this(false, name, type, arguments);
    }

    AbstractAggregateFunction(boolean distinct2, String name, DataType<T> type, Field<?> ... arguments) {
        this(distinct2, DSL.unquotedName(name), type, arguments);
    }

    AbstractAggregateFunction(boolean distinct2, Name name, DataType<T> type, Field<?> ... arguments) {
        super(name, type);
        this.distinct = distinct2;
        this.arguments = new QueryPartList((QueryPart[])arguments);
        this.filter = new ConditionProviderImpl();
    }

    @Override
    public void accept(Context<?> ctx) {
        this.toSQLArguments(ctx);
        this.acceptKeepDenseRankOrderByClause(ctx);
        this.acceptWithinGroupClause(ctx);
        this.acceptFilterClause(ctx);
        this.acceptOverClause(ctx);
    }

    private final void acceptKeepDenseRankOrderByClause(Context<?> ctx) {
        if (!Tools.isEmpty(this.keepDenseRankOrderBy)) {
            switch (ctx.family()) {
                case JAVA: {
                    ctx.sql(this.first ? ".keepDenseRankFirstOrderBy(" : ".keepDenseRankLastOrderBy(").visit(this.keepDenseRankOrderBy).sql(')');
                    break;
                }
                default: {
                    ctx.sql(' ').visit(Keywords.K_KEEP).sql(" (").visit(Keywords.K_DENSE_RANK).sql(' ').visit(this.first ? Keywords.K_FIRST : Keywords.K_LAST).sql(' ').visit(Keywords.K_ORDER_BY).sql(' ').visit(this.keepDenseRankOrderBy).sql(')');
                }
            }
        }
    }

    final void acceptWithinGroupClause(Context<?> ctx) {
        if (this.withinGroupOrderBy != null) {
            switch (ctx.family()) {
                case JAVA: {
                    ctx.sql(".withinGroupOrderBy(").visit(this.withinGroupOrderBy).sql(')');
                    break;
                }
                default: {
                    if (SUPPORT_EMPTY_WITHIN_GROUP.contains((Object)ctx.dialect()) && this.withinGroupOrderBy.isEmpty()) break;
                    ctx.sql(' ').visit(Keywords.K_WITHIN_GROUP).sql(" (").visit(Keywords.K_ORDER_BY).sql(' ');
                    if (this.withinGroupOrderBy.isEmpty()) {
                        ctx.visit(Keywords.K_NULL);
                    } else {
                        ctx.visit(this.withinGroupOrderBy);
                    }
                    ctx.sql(')');
                }
            }
        }
    }

    private final void toSQLArguments(Context<?> ctx) {
        this.acceptFunctionName(ctx);
        ctx.sql('(');
        this.acceptArguments0(ctx);
        ctx.sql(')');
    }

    void acceptFunctionName(Context<?> ctx) {
        if (ctx.family() == SQLDialect.JAVA) {
            ctx.sql(Tools.camelCase(this.getName()));
            if (this.distinct) {
                ctx.sql("Distinct");
            }
        } else if (this.emulateUsingCountIf(ctx)) {
            switch (ctx.family()) {
                case SNOWFLAKE: {
                    ctx.visit(Names.N_COUNT_IF);
                    break;
                }
                default: {
                    ctx.visit(Names.N_COUNTIF);
                    break;
                }
            }
        } else {
            AbstractFunction.acceptFunctionName(ctx, true, this.getQualifiedName());
        }
    }

    final void acceptArguments0(Context<?> ctx) {
        if (ctx.family() == SQLDialect.JAVA) {
            if (this.arguments.size() != 1 || this.arguments.get(0) != ASTERISK.get()) {
                ctx.visit(this.arguments);
            }
        } else {
            this.acceptArguments1(ctx, this.arguments);
        }
    }

    final void acceptArguments1(Context<?> ctx, QueryPartCollectionView<Field<?>> args2) {
        boolean parens = false;
        if (this.distinct) {
            ctx.visit(Keywords.K_DISTINCT).sql(' ');
            if (parens |= args2.size() > 1 && SUPPORT_DISTINCT_RVE.contains((Object)ctx.dialect()) && !(this instanceof ListAgg)) {
                ctx.sql('(');
            }
        }
        if (!args2.isEmpty()) {
            this.acceptArguments2(ctx, args2);
        }
        if (parens) {
            ctx.sql(')');
        }
    }

    final void acceptArguments2(Context<?> ctx, QueryPartCollectionView<Field<?>> args2) {
        this.acceptArguments3(ctx, args2, Function.identity());
    }

    final void acceptArguments3(Context<?> ctx, QueryPartCollectionView<Field<?>> args2, Function<? super Field<?>, ? extends Field<?>> fun) {
        if (!this.filter.hasWhere() || !NO_SUPPORT_FILTER.contains((Object)ctx.dialect())) {
            ctx.visit(QueryPartCollectionView.wrap(args2).map(fun));
        } else if (this.emulateUsingCountIf(ctx)) {
            ctx.visit(DSL.field(this.filter));
        } else {
            ctx.visit(QueryPartCollectionView.wrap(args2).map((arg, i) -> this.applyFilter(ctx, (Field<?>)arg, i) ? DSL.when((Condition)this.filter, arg == ASTERISK.get() ? DSL.one() : arg) : arg).map(fun));
        }
    }

    boolean applyFilter(Context<?> ctx, Field<?> arg, int i) {
        return true;
    }

    @Pro
    private final boolean emulateUsingCountIf(Context<?> ctx) {
        return this.filter.hasWhere() && Names.N_COUNT.equals(this.getQualifiedName()) && SUPPORT_COUNTIF.contains((Object)ctx.dialect()) && this.arguments.size() == 1 && this.arguments.get(0) == ASTERISK.get();
    }

    final void acceptFilterClause(Context<?> ctx) {
        if (this.filter.hasWhere()) {
            AbstractAggregateFunction.acceptFilterClause(ctx, this.filter);
        }
    }

    static final void acceptFilterClause(Context<?> ctx, Condition filter2) {
        switch (ctx.family()) {
            case JAVA: {
                ctx.sql(".filterWhere(").visit(filter2).sql(')');
                break;
            }
            default: {
                if (NO_SUPPORT_FILTER.contains((Object)ctx.dialect())) break;
                ctx.sql(' ').visit(Keywords.K_FILTER).sql(" (").visit(Keywords.K_WHERE).sql(' ').visit(filter2).sql(')');
            }
        }
    }

    final void acceptOrderBy(Context<?> ctx) {
        if (!Tools.isEmpty(this.withinGroupOrderBy)) {
            switch (ctx.family()) {
                case JAVA: {
                    ctx.sql(".orderBy(").visit(this.withinGroupOrderBy).sql(')');
                    break;
                }
                default: {
                    ctx.sql(' ').visit(Keywords.K_ORDER_BY).sql(' ').visit(this.withinGroupOrderBy);
                }
            }
        }
    }

    final QueryPartList<Field<?>> getArguments() {
        return this.arguments;
    }

    @Override
    public final WindowBeforeOverStep<T> filterWhere(Condition c) {
        this.filter.addConditions(c);
        return this;
    }

    @Override
    public final WindowBeforeOverStep<T> filterWhere(Condition ... conditions) {
        return this.filterWhere(DSL.and(conditions));
    }

    @Override
    public final WindowBeforeOverStep<T> filterWhere(Collection<? extends Condition> conditions) {
        return this.filterWhere(DSL.and(conditions));
    }

    @Override
    public final WindowBeforeOverStep<T> filterWhere(Field<Boolean> field2) {
        return this.filterWhere(DSL.condition(field2));
    }

    @Override
    public final WindowBeforeOverStep<T> filterWhere(SQL sql) {
        return this.filterWhere(DSL.condition(sql));
    }

    @Override
    public final WindowBeforeOverStep<T> filterWhere(String sql) {
        return this.filterWhere(DSL.condition(sql));
    }

    @Override
    public final WindowBeforeOverStep<T> filterWhere(String sql, Object ... bindings) {
        return this.filterWhere(DSL.condition(sql, bindings));
    }

    @Override
    public final WindowBeforeOverStep<T> filterWhere(String sql, QueryPart ... parts) {
        return this.filterWhere(DSL.condition(sql, parts));
    }

    @Override
    public final AggregateFunction<T> withinGroupOrderBy(OrderField<?> ... fields) {
        return this.withinGroupOrderBy(Arrays.asList(fields));
    }

    @Override
    public final AggregateFunction<T> withinGroupOrderBy(Collection<? extends OrderField<?>> fields) {
        if (this.withinGroupOrderBy == null) {
            this.withinGroupOrderBy = new SortFieldList();
        }
        this.withinGroupOrderBy.addAll(Tools.sortFields(fields));
        return this;
    }

    @Override
    @Pro
    public final AggregateFilterStep<T> keepDenseRankFirstOrderBy(OrderField<?> ... fields) {
        return this.keepDenseRankFirstOrderBy(Arrays.asList(fields));
    }

    @Override
    @Pro
    public final AggregateFilterStep<T> keepDenseRankFirstOrderBy(Collection<? extends OrderField<?>> fields) {
        this.first = true;
        return this.keepDenseRankOrderBy(fields);
    }

    @Override
    @Pro
    public final AggregateFilterStep<T> keepDenseRankLastOrderBy(OrderField<?> ... fields) {
        return this.keepDenseRankLastOrderBy(Arrays.asList(fields));
    }

    @Override
    @Pro
    public final AggregateFilterStep<T> keepDenseRankLastOrderBy(Collection<? extends OrderField<?>> fields) {
        return this.keepDenseRankOrderBy(fields);
    }

    @Pro
    private final AggregateFilterStep<T> keepDenseRankOrderBy(Collection<? extends OrderField<?>> fields) {
        if (this.keepDenseRankOrderBy == null) {
            this.keepDenseRankOrderBy = new SortFieldList();
        }
        this.keepDenseRankOrderBy.addAll(Tools.sortFields(fields));
        return this;
    }

    @Override
    public AbstractAggregateFunction<T> orderBy(OrderField<?> ... fields) {
        if (this.windowSpecification != null) {
            super.orderBy((OrderField[])fields);
        } else {
            this.withinGroupOrderBy((OrderField[])fields);
        }
        return this;
    }

    @Override
    public AbstractAggregateFunction<T> orderBy(Collection<? extends OrderField<?>> fields) {
        if (this.windowSpecification != null) {
            this.windowSpecification.orderBy((Collection)fields);
        } else {
            this.withinGroupOrderBy((Collection)fields);
        }
        return this;
    }

    final Condition f(Condition c) {
        return this.filter.hasWhere() ? this.filter.and(c) : c;
    }

    final <U> Field<U> fon(AggregateFunction<U> function) {
        return DSL.nullif(this.fo(function), DSL.zero());
    }

    final <U> Field<U> ofo(AbstractAggregateFunction<U> function) {
        return this.fo((AggregateFilterStep<U>)((Object)(Tools.isEmpty(this.withinGroupOrderBy) ? function : function.orderBy((Collection)this.withinGroupOrderBy))));
    }

    final <U> Field<U> fo(AggregateFilterStep<U> function) {
        return this.o(this.filter.hasWhere() ? function.filterWhere((Condition)this.filter) : function);
    }

    final <U> Field<U> o(WindowBeforeOverStep<U> function) {
        if (this.windowSpecification != null) {
            return function.over(this.windowSpecification);
        }
        if (this.windowDefinition != null) {
            return function.over(this.windowDefinition);
        }
        if (this.windowName != null) {
            return function.over(this.windowName);
        }
        return function;
    }

    final <U extends Number> Field<U> x(Field<U> x, Field<? extends Number> y) {
        return DSL.nvl2(y, x, DSL.inline(null, x.getDataType()));
    }

    final <U extends Number> Field<U> y(Field<? extends Number> x, Field<U> y) {
        return DSL.nvl2(x, y, DSL.inline(null, y.getDataType()));
    }

    final DataType<? extends Number> d(Context<?> ctx) {
        switch (ctx.family()) {
            case DERBY: 
            case FIREBIRD: 
            case HSQLDB: 
            case SQLITE: {
                return SQLDataType.DOUBLE;
            }
        }
        return SQLDataType.NUMERIC;
    }

    public final boolean $distinct() {
        return this.distinct;
    }

    @Override
    public final Condition $filterWhere() {
        return this.filter.getWhereOrNull();
    }

    @Override
    public <R> R $traverse(Traverser<?, R> traverser) {
        QOM.traverse(traverser, this, this.$filterWhere());
        return super.$traverse(traverser);
    }
}

