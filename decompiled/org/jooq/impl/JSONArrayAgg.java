/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Collection;
import java.util.Set;
import java.util.function.Function;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.JSONArrayAggOrderByStep;
import org.jooq.OrderField;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.Scope;
import org.jooq.Select;
import org.jooq.SelectGroupByStep;
import org.jooq.Table;
import org.jooq.impl.AbstractAggregateFunction;
import org.jooq.impl.CustomField;
import org.jooq.impl.DSL;
import org.jooq.impl.JSONEntryImpl;
import org.jooq.impl.JSONNull;
import org.jooq.impl.JSONReturning;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartCollectionView;
import org.jooq.impl.QueryPartListView;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.SortFieldList;
import org.jooq.impl.Tools;

final class JSONArrayAgg<J>
extends AbstractAggregateFunction<J>
implements JSONArrayAggOrderByStep<J>,
QOM.JSONArrayAgg<J> {
    static final Set<SQLDialect> EMULATE_WITH_GROUP_CONCAT = SQLDialect.supportedBy(SQLDialect.DB2, SQLDialect.MARIADB, SQLDialect.MYSQL);
    @Pro
    static final Set<SQLDialect> ORACLE_JSON_ARRAYAGG_BUG1 = SQLDialect.supportedUntil(SQLDialect.ORACLE20C);
    @Pro
    static final Set<SQLDialect> ORACLE_JSON_ARRAYAGG_BUG2 = SQLDialect.supportedBy(SQLDialect.ORACLE21C);
    private QOM.JSONOnNull onNull;
    private DataType<?> returning;
    private static final Set<SQLDialect> REQUIRE_ELSE_NULL = SQLDialect.supportedUntil(SQLDialect.ORACLE18C);

    JSONArrayAgg(DataType<J> type, Field<?> arg, boolean distinct2) {
        super(distinct2, Names.N_JSON_ARRAYAGG, type, arg);
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case MARIADB: 
            case MYSQL: {
                ctx.visit(JSONEntryImpl.jsonMerge(ctx, "[]", this.groupConcatEmulation(ctx)));
                break;
            }
            case JAVA: {
                this.acceptJava(ctx);
                break;
            }
            case DB2: {
                ctx.visit(this.groupConcatEmulation(ctx).cast(this.getDataType() == SQLDataType.JSON ? SQLDataType.VARCHAR : SQLDataType.BLOB));
                break;
            }
            case SNOWFLAKE: {
                ctx.visit(Names.N_ARRAY_AGG).sql('(');
                this.acceptDistinct(ctx);
                if (this.onNull == QOM.JSONOnNull.ABSENT_ON_NULL) {
                    this.acceptArguments2(ctx, this.arguments);
                } else {
                    this.acceptArguments2(ctx, QueryPartCollectionView.wrap(this.arguments).map(JSONEntryImpl::coalesceToJSONNull));
                }
                ctx.sql(')');
                this.acceptWithinGroupClause(ctx);
                this.acceptOverClause(ctx);
                break;
            }
            case AURORA_POSTGRES: 
            case COCKROACHDB: 
            case POSTGRES: 
            case YUGABYTEDB: {
                ctx.visit(this.getDataType() == SQLDataType.JSON ? Names.N_JSON_AGG : Names.N_JSONB_AGG).sql('(');
                this.acceptDistinct(ctx);
                ctx.visit((Field)this.arguments.get(0));
                this.acceptOrderBy(ctx);
                ctx.sql(')');
                if (this.onNull == QOM.JSONOnNull.ABSENT_ON_NULL) {
                    JSONArrayAgg.acceptFilterClause(ctx, this.f(((Field)this.arguments.get(0)).isNotNull()));
                } else {
                    this.acceptFilterClause(ctx);
                }
                this.acceptOverClause(ctx);
                break;
            }
            case SQLITE: {
                ctx.visit(Names.N_JSON_GROUP_ARRAY).sql('(');
                this.acceptDistinct(ctx);
                ctx.visit((Field)this.arguments.get(0));
                this.acceptOrderBy(ctx);
                ctx.sql(')');
                if (this.onNull == QOM.JSONOnNull.ABSENT_ON_NULL) {
                    JSONArrayAgg.acceptFilterClause(ctx, this.f(((Field)this.arguments.get(0)).isNotNull()));
                } else {
                    this.acceptFilterClause(ctx);
                }
                this.acceptOverClause(ctx);
                break;
            }
            default: {
                this.acceptStandard(ctx);
            }
        }
    }

    private final Field<?> groupConcatEmulation(Context<?> ctx) {
        Field<Object> arg1 = (Field)this.arguments.get(0);
        if (arg1.getDataType().isString()) {
            switch (ctx.family()) {
                case MARIADB: 
                case MYSQL: {
                    arg1 = DSL.function(Names.N_JSON_QUOTE, this.getDataType(), arg1);
                    break;
                }
                case DB2: {
                    arg1 = DSL.concat(DSL.inline('\"'), DSL.replace(arg1, DSL.inline('\"'), DSL.inline("\\\"")), DSL.inline('\"'));
                }
            }
        }
        if (arg1.getDataType() == SQLDataType.JSONB) {
            switch (ctx.family()) {
                case DB2: {
                    arg1 = arg1.cast(SQLDataType.VARCHAR);
                }
            }
        }
        Field arg2 = arg1;
        return DSL.concat(DSL.inline('['), CustomField.of(Names.N_GROUP_CONCAT, SQLDataType.VARCHAR, c1 -> {
            c1.visit(JSONArrayAgg.groupConcatEmulationWithoutArrayWrappers(this.distinct, CustomField.of(Names.N_FIELD, SQLDataType.VARCHAR, c2 -> this.acceptArguments2((Context<?>)c2, QueryPartListView.wrap((QueryPart[])new Field[]{arg2}))), this.withinGroupOrderBy));
            this.acceptFilterClause(ctx);
            this.acceptOverClause((Context<?>)c1);
        }), DSL.inline(']'));
    }

    static final Field<?> groupConcatEmulationWithoutArrayWrappers(boolean distinct2, Field<?> field2, SortFieldList orderBy) {
        return Tools.apply(distinct2 ? DSL.groupConcatDistinct(field2) : DSL.groupConcat(field2), agg -> Tools.isEmpty(orderBy) ? agg : agg.orderBy(orderBy));
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql(this.distinct ? "jsonArrayAggDistinct(" : "jsonArrayAgg(").visit((Field)this.arguments.get(0)).sql(')');
        this.acceptOrderBy(ctx);
        ctx.visit(new JSONNull(this.onNull)).visit(new JSONReturning(this.returning));
        this.acceptFilterClause(ctx);
        this.acceptOverClause(ctx);
    }

    private final void acceptStandard(Context<?> ctx) {
        JSONReturning jsonReturning;
        ctx.visit(Names.N_JSON_ARRAYAGG).sql('(');
        this.acceptDistinct(ctx);
        if (REQUIRE_ELSE_NULL.contains((Object)ctx.dialect())) {
            ctx.data(Tools.BooleanDataKey.DATA_FORCE_CASE_ELSE_NULL, true, c -> this.acceptArguments3((Context<?>)c, this.arguments, (Function<? super Field<?>, ? extends Field<?>>)JSONEntryImpl.jsonCastMapper(ctx)));
        } else {
            this.acceptArguments3(ctx, this.arguments, JSONEntryImpl.jsonCastMapper(ctx));
        }
        this.acceptOrderBy(ctx);
        JSONNull jsonNull = new JSONNull(this.onNull);
        if (jsonNull.rendersContent(ctx)) {
            ctx.sql(' ').visit(jsonNull);
        }
        if ((jsonReturning = new JSONReturning(this.returning)).rendersContent(ctx)) {
            ctx.sql(' ').visit(jsonReturning);
        }
        ctx.sql(')');
        this.acceptFilterClause(ctx);
        this.acceptOverClause(ctx);
    }

    private void acceptDistinct(Context<?> ctx) {
        if (this.distinct) {
            ctx.visit(Keywords.K_DISTINCT).sql(' ');
        }
    }

    @Override
    public final JSONArrayAgg<J> nullOnNull() {
        this.onNull = QOM.JSONOnNull.NULL_ON_NULL;
        return this;
    }

    @Override
    public final JSONArrayAgg<J> absentOnNull() {
        this.onNull = QOM.JSONOnNull.ABSENT_ON_NULL;
        return this;
    }

    @Override
    public final JSONArrayAgg<J> returning(DataType<?> r) {
        this.returning = r;
        return this;
    }

    @Override
    public final JSONArrayAgg<J> orderBy(OrderField<?> ... fields) {
        return (JSONArrayAgg)super.orderBy((OrderField[])fields);
    }

    @Override
    public final JSONArrayAgg<J> orderBy(Collection<? extends OrderField<?>> fields) {
        return (JSONArrayAgg)super.orderBy((Collection)fields);
    }

    static final <R extends Record> Select<R> patchOracleArrayAggBug(Scope scope, SelectGroupByStep<R> select) {
        Select<?> s2;
        if (ORACLE_JSON_ARRAYAGG_BUG2.contains((Object)scope.dialect()) && select.$from().size() == 1 && (s2 = Tools.extractSelectFromDerivedTable((Table)select.$from().get(0))).$from().isEmpty()) {
            select.groupBy(DSL.inline(null, SQLDataType.INTEGER));
        }
        if (ORACLE_JSON_ARRAYAGG_BUG1.contains((Object)scope.dialect())) {
            return select.having(DSL.count().eq(DSL.count()));
        }
        return select;
    }

    @Override
    public final Field<?> $arg1() {
        return (Field)this.getArguments().get(0);
    }

    @Override
    public final QOM.JSONOnNull $onNull() {
        return this.onNull;
    }

    @Override
    public final DataType<?> $returning() {
        return this.returning;
    }

    @Override
    public final Function1<? super Field<?>, ? extends QOM.JSONArrayAgg<J>> $constructor() {
        return f -> {
            JSONArrayAgg r = new JSONArrayAgg(this.getDataType(), (Field<?>)f, this.distinct);
            r.onNull = this.onNull;
            r.returning = this.returning;
            return r;
        };
    }
}

