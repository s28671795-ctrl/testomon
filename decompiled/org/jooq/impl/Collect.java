/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.ArrayRecord;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.impl.AbstractAggregateFunction;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartListView;
import org.jooq.impl.Tools;

@Pro
final class Collect<T, A extends ArrayRecord<T>>
extends AbstractAggregateFunction<A>
implements QOM.Collect<T, A> {
    Collect(Field<T> field2, DataType<A> type, boolean distinct2) {
        super(distinct2, Names.N_COLLECT, type, field2);
    }

    @Override
    public final void accept(Context<?> ctx) {
        ctx.visit(Keywords.K_CAST).sql('(');
        this.toSQLArrayAgg(ctx);
        ctx.sql(' ').visit(Keywords.K_AS).sql(' ').visit(Tools.getMappedArrayName(ctx, this.getType())).sql(')');
    }

    final void toSQLArrayAgg(Context<?> ctx) {
        ctx.visit(Names.N_COLLECT).sql('(');
        this.acceptArguments1(ctx, new QueryPartListView((QueryPart[])new Field[]{(Field)this.arguments.get(0)}));
        this.acceptOrderBy(ctx);
        ctx.sql(')');
    }

    @Override
    public final Field<T> $arg1() {
        return (Field)this.getArguments().get(0);
    }

    @Override
    public final Function1<? super Field<T>, ? extends QOM.Collect<T, A>> $constructor() {
        return f -> new Collect(f, this.getDataType(), this.distinct);
    }
}

