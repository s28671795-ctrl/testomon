/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.SQLDialect;
import org.jooq.XML;
import org.jooq.XMLAggOrderByStep;
import org.jooq.impl.AbstractAggregateFunction;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartListView;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;

final class ListAgg
extends AbstractAggregateFunction<String>
implements QOM.UNotYetImplemented {
    private static final Set<SQLDialect> SET_GROUP_CONCAT_MAX_LEN = SQLDialect.supportedBy(SQLDialect.AURORA_MYSQL, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL);
    private static final Set<SQLDialect> SUPPORT_GROUP_CONCAT = SQLDialect.supportedBy(SQLDialect.AURORA_MYSQL, SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.SQLITE);
    private static final Set<SQLDialect> SUPPORT_STRING_AGG = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.HANA, SQLDialect.POSTGRES, SQLDialect.SQLSERVER2017, SQLDialect.SYBASE);
    @Pro
    private static final Set<SQLDialect> EMULATE_LISTAGG_USING_XMLAGG = SQLDialect.supportedUntil(SQLDialect.DB2_10, SQLDialect.TERADATA);

    ListAgg(boolean distinct2, Field<?> arg) {
        super(distinct2, Names.N_LISTAGG, SQLDataType.VARCHAR, arg);
    }

    ListAgg(boolean distinct2, Field<?> arg, Field<String> separator) {
        super(distinct2, Names.N_LISTAGG, SQLDataType.VARCHAR, arg, separator);
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (SUPPORT_GROUP_CONCAT.contains((Object)ctx.dialect())) {
            if (SET_GROUP_CONCAT_MAX_LEN.contains((Object)ctx.dialect()) && !Boolean.FALSE.equals(ctx.settings().isRenderGroupConcatMaxLenSessionVariable()) && ctx.data(Tools.BooleanDataKey.DATA_GROUP_CONCAT_MAX_LEN_SET) == null) {
                ctx.skipUpdateCounts(2).data(Tools.BooleanDataKey.DATA_GROUP_CONCAT_MAX_LEN_SET, true);
                Tools.prependSQL(ctx, DSL.query("{set} @t = @@group_concat_max_len"), DSL.query("{set} @@group_concat_max_len = 4294967295"));
                this.acceptGroupConcat(ctx);
                if (ctx.family() != SQLDialect.MEMSQL) {
                    Tools.appendSQL(ctx, DSL.query("{set} @@group_concat_max_len = @t"));
                }
            } else {
                this.acceptGroupConcat(ctx);
            }
        } else if (SUPPORT_STRING_AGG.contains((Object)ctx.dialect())) {
            this.acceptStringAgg(ctx);
            this.acceptFilterClause(ctx);
            if (ctx.family() == SQLDialect.SQLSERVER) {
                this.acceptWithinGroupClause(ctx);
            }
            this.acceptOverClause(ctx);
        } else if (EMULATE_LISTAGG_USING_XMLAGG.contains((Object)ctx.dialect())) {
            this.acceptXMLAGG(ctx);
        } else {
            super.accept(ctx);
        }
    }

    @Override
    void acceptFunctionName(Context<?> ctx) {
        if (ctx.family() == SQLDialect.JAVA) {
            ctx.sql("listAgg");
        } else {
            super.acceptFunctionName(ctx);
        }
    }

    @Override
    boolean applyFilter(Context<?> ctx, Field<?> arg, int i) {
        return i == 0;
    }

    private final void acceptGroupConcat(Context<?> ctx) {
        ctx.visit(Names.N_GROUP_CONCAT).sql('(');
        this.acceptArguments1(ctx, new QueryPartListView((QueryPart[])new Field[]{(Field)this.arguments.get(0)}));
        this.acceptOrderBy(ctx);
        if (this.arguments.size() > 1) {
            if (ctx.family() == SQLDialect.SQLITE) {
                ctx.sql(", ").visit((Field)this.arguments.get(1));
            } else {
                ctx.sql(' ').visit(Keywords.K_SEPARATOR).sql(' ').visit((Field)this.arguments.get(1));
            }
        }
        ctx.sql(')');
        this.acceptFilterClause(ctx);
        this.acceptOverClause(ctx);
    }

    private final void acceptStringAgg(Context<?> ctx) {
        switch (ctx.family()) {
            case SYBASE: {
                ctx.visit(Names.N_LIST);
                break;
            }
            default: {
                ctx.visit(Names.N_STRING_AGG);
            }
        }
        ctx.sql('(');
        QueryPartListView args2 = QueryPartListView.wrap((QueryPart[])new Field[]{Tools.castIfNeeded((Field)this.arguments.get(0), String.class)});
        this.acceptArguments1(ctx, args2);
        if (this.arguments.size() > 1) {
            ctx.sql(", ").visit((Field)this.arguments.get(1));
        } else {
            ctx.sql(", ").visit(DSL.inline(""));
        }
        if (ctx.family() != SQLDialect.SQLSERVER) {
            this.acceptOrderBy(ctx);
        }
        ctx.sql(')');
    }

    @Pro
    private final void acceptXMLAGG(Context<?> ctx) {
        Field<String> result;
        boolean db2 = ctx.family() == SQLDialect.DB2;
        Field<Object> arg = (Field<XML>)this.arguments.get(0);
        if (this.arguments.size() > 1) {
            arg = DSL.concat((Field)this.arguments.get(1), arg);
        }
        if (db2) {
            arg = DSL.function(Names.N_XMLTEXT, SQLDataType.XML, arg);
        }
        XMLAggOrderByStep<XML> xmlagg = Tools.isEmpty(this.withinGroupOrderBy) ? DSL.xmlagg(arg.coerce(SQLDataType.XML)) : DSL.xmlagg(arg.coerce(SQLDataType.XML)).orderBy(this.withinGroupOrderBy);
        Field<String> field2 = result = db2 ? DSL.xmlserializeDocument(xmlagg, SQLDataType.VARCHAR) : DSL.xmlserializeContent(xmlagg, SQLDataType.VARCHAR);
        if (this.arguments.size() > 1) {
            result = DSL.substring(result, ((Field)this.arguments.get(1)).toString().length() - 1);
        }
        ctx.visit(result);
    }
}

