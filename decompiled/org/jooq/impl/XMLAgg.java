/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.SQLDialect;
import org.jooq.XML;
import org.jooq.XMLAggOrderByStep;
import org.jooq.impl.AbstractAggregateFunction;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;

final class XMLAgg
extends AbstractAggregateFunction<XML>
implements XMLAggOrderByStep<XML>,
QOM.XMLAgg {
    XMLAgg(Field<XML> arg) {
        super(false, Names.N_XMLAGG, SQLDataType.XML, arg);
    }

    @Override
    public void accept(Context<?> ctx) {
        if (ctx.family() == SQLDialect.JAVA) {
            ctx.sql("xmlagg(").visit(this.arguments).sql(')');
            this.acceptOrderBy(ctx);
            this.acceptFilterClause(ctx);
            this.acceptOverClause(ctx);
            return;
        }
        ctx.visit(Names.N_XMLAGG).sql('(');
        this.acceptArguments0(ctx);
        this.acceptOrderBy(ctx);
        ctx.sql(')');
        this.acceptFilterClause(ctx);
        this.acceptOverClause(ctx);
    }

    @Override
    public final Field<XML> $arg1() {
        return (Field)this.getArguments().get(0);
    }

    @Override
    public final Function1<? super Field<XML>, ? extends QOM.XMLAgg> $constructor() {
        return f -> new XMLAgg((Field<XML>)f);
    }
}

