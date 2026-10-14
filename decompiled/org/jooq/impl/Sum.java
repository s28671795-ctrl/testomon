/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.math.BigDecimal;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function2;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.Traverser;
import org.jooq.impl.AbstractAggregateFunction;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;

final class Sum
extends AbstractAggregateFunction<BigDecimal>
implements QOM.Sum {
    Sum(Field<? extends Number> field2, boolean distinct2) {
        super(distinct2, Names.N_SUM, SQLDataType.NUMERIC, Tools.nullSafeNotNull(field2, SQLDataType.INTEGER));
    }

    @Override
    public final void accept(Context<?> ctx) {
        super.accept(ctx);
    }

    @Override
    public final Field<? extends Number> $field() {
        return (Field)this.getArguments().get(0);
    }

    @Override
    public final QOM.Sum $field(Field<? extends Number> newValue) {
        return this.$constructor().apply((Field<? extends Number>)newValue, (Boolean)this.$distinct());
    }

    @Override
    public final QOM.Sum $distinct(boolean newValue) {
        return this.$constructor().apply((Field<? extends Number>)this.$field(), (Boolean)newValue);
    }

    public final Function2<? super Field<? extends Number>, ? super Boolean, ? extends QOM.Sum> $constructor() {
        return (a1, a2) -> new Sum((Field<? extends Number>)a1, (boolean)a2);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$field(), this.$distinct(), (a1, a2) -> this.$constructor().apply((Field<? extends Number>)a1, (Boolean)a2), replacer);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        QOM.traverse(traverser, this, this.$field());
        return super.$traverse(traverser);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Sum) {
            QOM.Sum o = (QOM.Sum)that;
            return StringUtils.equals(this.$field(), o.$field()) && this.$distinct() == o.$distinct();
        }
        return super.equals(that);
    }
}

