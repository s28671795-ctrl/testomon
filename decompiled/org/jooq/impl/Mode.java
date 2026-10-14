/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.OrderField;
import org.jooq.SQLDialect;
import org.jooq.impl.AbstractAggregateFunction;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultAggregateFunction;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;

final class Mode<T>
extends AbstractAggregateFunction<T>
implements QOM.Mode<T> {
    private static final Set<SQLDialect> EMULATE_AS_ORDERED_SET_AGG = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.H2, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB);

    Mode(Field<T> arg) {
        super(false, Names.N_MODE, arg.getDataType(), arg);
    }

    @Override
    public void accept(Context<?> ctx) {
        if (EMULATE_AS_ORDERED_SET_AGG.contains((Object)ctx.dialect())) {
            ctx.visit(DSL.mode().withinGroupOrderBy((OrderField)this.arguments.get(0)));
        } else if (ctx.family() == SQLDialect.ORACLE) {
            ctx.visit(new DefaultAggregateFunction(Names.N_STATS_MODE, this.getDataType(), (Field)this.arguments.get(0)));
        } else if (ctx.family() == SQLDialect.SNOWFLAKE) {
            ctx.visit(new DefaultAggregateFunction(Names.N_MODE, this.getDataType(), (Field)this.arguments.get(0)));
        } else {
            super.accept(ctx);
        }
    }

    @Override
    public final Field<T> $arg1() {
        return (Field)this.getArguments().get(0);
    }

    @Override
    public final Function1<? super Field<T>, ? extends QOM.Mode<T>> $constructor() {
        return f -> new Mode(f);
    }
}

