/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.math.BigDecimal;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.Traverser;
import org.jooq.impl.AbstractAggregateFunction;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;

final class StddevPop
extends AbstractAggregateFunction<BigDecimal>
implements QOM.StddevPop {
    StddevPop(Field<? extends Number> field2) {
        super(false, Names.N_STDDEV_POP, SQLDataType.NUMERIC, Tools.nullSafeNotNull(field2, SQLDataType.INTEGER));
    }

    @Override
    void acceptFunctionName(Context<?> ctx) {
        switch (ctx.family()) {
            case DB2: {
                ctx.visit(Names.N_STDDEV);
                break;
            }
            case INFORMIX: {
                ctx.visit(Names.N_STDEV);
                break;
            }
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: {
                ctx.visit(Names.N_STDEVP);
                break;
            }
            default: {
                super.acceptFunctionName(ctx);
            }
        }
    }

    @Override
    public final Field<? extends Number> $field() {
        return (Field)this.getArguments().get(0);
    }

    @Override
    public final QOM.StddevPop $field(Field<? extends Number> newValue) {
        return this.$constructor().apply((Field<? extends Number>)newValue);
    }

    public final Function1<? super Field<? extends Number>, ? extends QOM.StddevPop> $constructor() {
        return a1 -> new StddevPop((Field<? extends Number>)a1);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$field(), a1 -> this.$constructor().apply((Field<? extends Number>)a1), replacer);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        QOM.traverse(traverser, this, this.$field());
        return super.$traverse(traverser);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.StddevPop) {
            QOM.StddevPop o = (QOM.StddevPop)that;
            return StringUtils.equals(this.$field(), o.$field());
        }
        return super.equals(that);
    }
}

