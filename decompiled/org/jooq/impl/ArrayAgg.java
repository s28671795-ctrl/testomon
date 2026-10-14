/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.QueryPart;
import org.jooq.impl.AbstractAggregateFunction;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartListView;

final class ArrayAgg<T>
extends AbstractAggregateFunction<T[]>
implements QOM.ArrayAgg<T> {
    ArrayAgg(boolean distinct2, Field<T> arg) {
        super(distinct2, Names.N_ARRAY_AGG, arg.getDataType().getArrayDataType(), arg);
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                ctx.sql(this.distinct ? "arrayAggDistinct(" : "arrayAgg(");
                this.acceptArguments0(ctx);
                ctx.sql(')');
                this.acceptOrderBy(ctx);
                break;
            }
            default: {
                ctx.visit(Names.N_ARRAY_AGG).sql('(');
                this.acceptArguments1(ctx, new QueryPartListView((QueryPart[])new Field[]{(Field)this.arguments.get(0)}));
                this.acceptOrderBy(ctx);
                ctx.sql(')');
            }
        }
        this.acceptFilterClause(ctx);
        this.acceptOverClause(ctx);
    }

    @Override
    public final Field<T> $arg1() {
        return (Field)this.getArguments().get(0);
    }

    @Override
    public final Function1<? super Field<T>, ? extends QOM.ArrayAgg<T>> $constructor() {
        return f -> new ArrayAgg<T>(this.distinct, f);
    }
}

