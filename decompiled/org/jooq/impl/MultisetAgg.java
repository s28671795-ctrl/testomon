/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.JSON;
import org.jooq.JSONArrayAggOrderByStep;
import org.jooq.JSONB;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Replacer;
import org.jooq.Result;
import org.jooq.Row;
import org.jooq.SQLDialect;
import org.jooq.SelectField;
import org.jooq.Traverser;
import org.jooq.XML;
import org.jooq.XMLAggOrderByStep;
import org.jooq.impl.AbstractAggregateFunction;
import org.jooq.impl.AbstractRow;
import org.jooq.impl.DSL;
import org.jooq.impl.Multiset;
import org.jooq.impl.MultisetDataType;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartListView;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;

final class MultisetAgg<R extends Record>
extends AbstractAggregateFunction<Result<R>>
implements QOM.MultisetAgg<R> {
    private final AbstractRow<R> row;

    MultisetAgg(boolean distinct2, SelectField<R> row) {
        super(distinct2, Names.N_MULTISET_AGG, new MultisetDataType((AbstractRow)row, null), ((AbstractRow)row).fields());
        this.row = (AbstractRow)row;
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (ctx.family() == SQLDialect.JAVA) {
            ctx.sql(this.distinct ? "multisetAggDistinct(" : "multisetAgg(");
            this.acceptArguments0(ctx);
            ctx.sql(')');
            this.acceptOrderBy(ctx);
            this.acceptFilterClause(ctx);
            this.acceptOverClause(ctx);
        } else if (Boolean.TRUE.equals(ctx.data(Tools.BooleanDataKey.DATA_MULTISET_CONDITION))) {
            ctx.data().remove(Tools.BooleanDataKey.DATA_MULTISET_CONDITION);
            ctx.data(Tools.BooleanDataKey.DATA_MULTISET_CONTENT, true, c -> this.accept0((Context<?>)c, true));
            ctx.data(Tools.BooleanDataKey.DATA_MULTISET_CONDITION, true);
        } else {
            ctx.data(Tools.BooleanDataKey.DATA_MULTISET_CONTENT, true, c -> this.accept0((Context<?>)c, false));
        }
    }

    private final void accept0(Context<?> ctx, boolean multisetCondition) {
        switch (Tools.emulateMultiset(ctx.configuration())) {
            case JSON: {
                Field f;
                JSONArrayAggOrderByStep<JSON> order = Multiset.jsonArrayaggEmulation(ctx, this.row, true, this.distinct);
                Field field2 = f = multisetCondition ? this.fo((AbstractAggregateFunction)Multiset.returningClob(ctx, order.orderBy(this.row.fields()))) : this.ofo((AbstractAggregateFunction)Multiset.returningClob(ctx, order));
                if (multisetCondition && Multiset.NO_SUPPORT_JSON_COMPARE.contains((Object)ctx.dialect())) {
                    ctx.visit(f.cast(SQLDataType.VARCHAR));
                    break;
                }
                ctx.visit(f);
                break;
            }
            case JSONB: {
                Field f;
                JSONArrayAggOrderByStep<JSONB> order = Multiset.jsonbArrayaggEmulation(ctx, this.row, true, this.distinct);
                Field field3 = f = multisetCondition ? this.fo((AbstractAggregateFunction)Multiset.returningClob(ctx, order.orderBy(this.row.fields()))) : this.ofo((AbstractAggregateFunction)Multiset.returningClob(ctx, order));
                if (multisetCondition && Multiset.NO_SUPPORT_JSONB_COMPARE.contains((Object)ctx.dialect())) {
                    ctx.visit(f.cast(SQLDataType.VARCHAR));
                    break;
                }
                ctx.visit(f);
                break;
            }
            case XML: {
                XMLAggOrderByStep<XML> order = Multiset.xmlaggEmulation(ctx, this.row, true);
                Field<XML> f = DSL.xmlelement(Multiset.nResult(ctx), multisetCondition ? this.fo((AbstractAggregateFunction)order.orderBy(this.row.fields())) : this.ofo((AbstractAggregateFunction)((Object)order)));
                if (multisetCondition && Multiset.NO_SUPPORT_XML_COMPARE.contains((Object)ctx.dialect())) {
                    ctx.visit(DSL.xmlserializeContent(f, SQLDataType.VARCHAR));
                    break;
                }
                ctx.visit(f);
                break;
            }
            case NATIVE: {
                ctx.visit(Names.N_MULTISET_AGG).sql('(');
                this.acceptArguments1(ctx, new QueryPartListView((QueryPart[])new Field[]{(Field)this.arguments.get(0)}));
                this.acceptOrderBy(ctx);
                ctx.sql(')');
                this.acceptFilterClause(ctx);
                this.acceptOverClause(ctx);
            }
        }
    }

    @Override
    public final Row $row() {
        return this.row;
    }

    @Override
    public final <T> T $traverse(Traverser<?, T> traverser) {
        return QOM.traverse(traverser, this, this.row);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.distinct, this.row, MultisetAgg::new, replacer);
    }
}

