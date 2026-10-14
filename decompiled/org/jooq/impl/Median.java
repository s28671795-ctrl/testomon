/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.math.BigDecimal;
import java.util.Set;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.Traverser;
import org.jooq.impl.AbstractAggregateFunction;
import org.jooq.impl.DSL;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;

final class Median
extends AbstractAggregateFunction<BigDecimal>
implements QOM.Median {
    private static final Set<SQLDialect> EMULATE_WITH_PERCENTILES = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.POSTGRES, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB);

    Median(Field<? extends Number> field2) {
        super(false, Names.N_MEDIAN, SQLDataType.NUMERIC, Tools.nullSafeNotNull(field2, SQLDataType.INTEGER));
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (EMULATE_WITH_PERCENTILES.contains((Object)ctx.dialect())) {
            ctx.visit(this.fo(DSL.percentileCont(DSL.inline(new BigDecimal("0.5"))).withinGroupOrderBy(this.arguments)));
        } else {
            super.accept(ctx);
        }
    }

    @Override
    public final Field<? extends Number> $field() {
        return (Field)this.getArguments().get(0);
    }

    @Override
    public final QOM.Median $field(Field<? extends Number> newValue) {
        return this.$constructor().apply((Field<? extends Number>)newValue);
    }

    public final Function1<? super Field<? extends Number>, ? extends QOM.Median> $constructor() {
        return a1 -> new Median((Field<? extends Number>)a1);
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
        if (that instanceof QOM.Median) {
            QOM.Median o = (QOM.Median)that;
            return StringUtils.equals(this.$field(), o.$field());
        }
        return super.equals(that);
    }
}

