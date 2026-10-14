/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.JSON;
import org.jooq.JSONEntry;
import org.jooq.JSONObjectAggNullStep;
import org.jooq.JSONObjectNullStep;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.SQLDialect;
import org.jooq.impl.AbstractAggregateFunction;
import org.jooq.impl.CustomField;
import org.jooq.impl.DSL;
import org.jooq.impl.JSONEntryImpl;
import org.jooq.impl.JSONNull;
import org.jooq.impl.JSONReturning;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartCollectionView;
import org.jooq.impl.QueryPartListView;
import org.jooq.impl.RegexpReplace;
import org.jooq.impl.SQLDataType;

final class JSONObjectAgg<J>
extends AbstractAggregateFunction<J>
implements JSONObjectAggNullStep<J>,
QOM.JSONObjectAgg<J> {
    private final JSONEntry<?> entry;
    private QOM.JSONOnNull onNull;
    private DataType<?> returning;

    JSONObjectAgg(DataType<J> type, JSONEntry<?> entry) {
        super(false, Names.N_JSON_OBJECTAGG, type, entry.key(), entry.value());
        this.entry = entry;
    }

    @Override
    public void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                this.acceptJava(ctx);
                break;
            }
            case DB2: 
            case COCKROACHDB: {
                this.acceptGroupConcat(ctx);
                break;
            }
            case SNOWFLAKE: {
                this.acceptSnowflake(ctx);
                break;
            }
            case AURORA_POSTGRES: 
            case POSTGRES: 
            case YUGABYTEDB: {
                this.acceptPostgres(ctx);
                break;
            }
            case MARIADB: 
            case MYSQL: {
                if (this.onNull == QOM.JSONOnNull.ABSENT_ON_NULL || this.filter.hasWhere()) {
                    this.acceptGroupConcat(ctx);
                    break;
                }
                if (ctx.dialect().precedes(SQLDialect.MARIADB_10_4)) {
                    this.acceptGroupConcat(ctx);
                    break;
                }
                this.acceptStandard(ctx);
                break;
            }
            case SQLITE: {
                this.acceptSQLite(ctx);
                break;
            }
            default: {
                this.acceptStandard(ctx);
            }
        }
    }

    private final void acceptPostgres(Context<?> ctx) {
        ctx.visit(this.getDataType() == SQLDataType.JSON ? Names.N_JSON_OBJECT_AGG : Names.N_JSONB_OBJECT_AGG).sql('(');
        ctx.visit(this.entry);
        ctx.sql(')');
        if (this.onNull == QOM.JSONOnNull.ABSENT_ON_NULL) {
            JSONObjectAgg.acceptFilterClause(ctx, this.f(this.entry.value().isNotNull()));
        } else {
            this.acceptFilterClause(ctx);
        }
        this.acceptOverClause(ctx);
    }

    private final void acceptSQLite(Context<?> ctx) {
        ctx.visit(Names.N_JSON_GROUP_OBJECT).sql('(');
        ctx.visit(this.entry);
        ctx.sql(')');
        if (this.onNull == QOM.JSONOnNull.ABSENT_ON_NULL) {
            JSONObjectAgg.acceptFilterClause(ctx, this.f(this.entry.value().isNotNull()));
        } else {
            this.acceptFilterClause(ctx);
        }
        this.acceptOverClause(ctx);
    }

    @Pro
    private final void acceptSnowflake(Context<?> ctx) {
        ctx.visit(Names.N_OBJECT_AGG).sql('(');
        if (this.onNull == QOM.JSONOnNull.ABSENT_ON_NULL) {
            this.acceptArguments2(ctx, this.arguments);
        } else {
            this.acceptArguments2(ctx, QueryPartCollectionView.wrap(this.arguments).map(JSONEntryImpl::coalesceToJSONNull));
        }
        ctx.sql(')');
        this.acceptOverClause(ctx);
    }

    @Override
    boolean applyFilter(Context<?> ctx, Field<?> arg, int i) {
        switch (ctx.family()) {
            case SNOWFLAKE: {
                return i == 1;
            }
        }
        return true;
    }

    private final void acceptGroupConcat(Context<?> ctx) {
        ctx.sql('(').visit(this.groupConcatEmulation(ctx)).sql(')');
    }

    private final Field<?> groupConcatEmulation(Context<?> ctx) {
        CustomField<String> listagg = CustomField.of(Names.N_GROUP_CONCAT, SQLDataType.VARCHAR, c1 -> {
            Field<JSON> o1 = DSL.jsonObject(this.entry.key(), this.entry.value());
            if (this.onNull == QOM.JSONOnNull.ABSENT_ON_NULL) {
                o1 = DSL.when(this.entry.value().isNull(), DSL.inline((JSON)null)).else_(o1);
            }
            JSONObjectNullStep<JSON> o2 = o1;
            c1.visit(DSL.groupConcat(DSL.concat(CustomField.of(Names.N_FIELD, SQLDataType.VARCHAR, c2 -> this.acceptArguments2((Context<?>)c2, QueryPartListView.wrap((QueryPart[])new Field[]{DSL.regexpReplaceAll(o2.cast(SQLDataType.VARCHAR), DSL.inline("^\\{(.*)\\}$"), DSL.inline(RegexpReplace.replacement(ctx, 1)))}))))));
            this.acceptFilterClause((Context<?>)c1);
            this.acceptOverClause((Context<?>)c1);
        });
        Field<String> result = DSL.concat(DSL.inline('{'), listagg, DSL.inline('}'));
        switch (ctx.family()) {
            case DB2: {
                if (this.getDataType() == SQLDataType.JSON) {
                    return result;
                }
                return result.cast(SQLDataType.BLOB);
            }
        }
        return result;
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("jsonObjectAgg(").visit(this.entry).sql(')').visit(new JSONNull(this.onNull)).visit(new JSONReturning(this.returning));
        this.acceptFilterClause(ctx);
        this.acceptOverClause(ctx);
    }

    private final void acceptStandard(Context<?> ctx) {
        JSONReturning jsonReturning;
        ctx.visit(Names.N_JSON_OBJECTAGG).sql('(').visit(this.entry);
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

    @Override
    public final JSONObjectAgg<J> nullOnNull() {
        this.onNull = QOM.JSONOnNull.NULL_ON_NULL;
        return this;
    }

    @Override
    public final JSONObjectAgg<J> absentOnNull() {
        this.onNull = QOM.JSONOnNull.ABSENT_ON_NULL;
        return this;
    }

    @Override
    public final JSONObjectAgg<J> returning(DataType<?> r) {
        this.returning = r;
        return this;
    }

    @Override
    public final JSONEntry<?> $arg1() {
        return this.entry;
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
    public final Function1<? super JSONEntry<?>, ? extends QOM.JSONObjectAgg<J>> $constructor() {
        return e -> {
            JSONObjectAgg r = new JSONObjectAgg(this.getDataType(), (JSONEntry<?>)e);
            r.onNull = this.onNull;
            r.returning = this.returning;
            return r;
        };
    }
}

