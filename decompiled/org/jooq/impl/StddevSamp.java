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

final class StddevSamp
extends AbstractAggregateFunction<BigDecimal>
implements QOM.StddevSamp {
    StddevSamp(Field<? extends Number> field2) {
        super(false, Names.N_STDDEV_SAMP, SQLDataType.NUMERIC, Tools.nullSafeNotNull(field2, SQLDataType.INTEGER));
    }

    @Override
    void acceptFunctionName(Context<?> ctx) {
        switch (ctx.family()) {
            case HANA: {
                ctx.visit(Names.N_STDDEV);
                break;
            }
            case INFORMIX: {
                ctx.visit(Names.N_STDEV_SAMP);
                break;
            }
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: {
                ctx.visit(Names.N_STDEV);
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
    public final QOM.StddevSamp $field(Field<? extends Number> newValue) {
        return this.$constructor().apply((Field<? extends Number>)newValue);
    }

    public final Function1<? super Field<? extends Number>, ? extends QOM.StddevSamp> $constructor() {
        return a1 -> new StddevSamp((Field<? extends Number>)a1);
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
        if (that instanceof QOM.StddevSamp) {
            QOM.StddevSamp o = (QOM.StddevSamp)that;
            return StringUtils.equals(this.$field(), o.$field());
        }
        return super.equals(that);
    }
}

