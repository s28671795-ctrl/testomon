/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

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

final class Max<T>
extends AbstractAggregateFunction<T>
implements QOM.Max<T> {
    Max(Field<T> field2, boolean distinct2) {
        super(distinct2, Names.N_MAX, Tools.nullSafeDataType(field2), Tools.nullSafeNotNull(field2, SQLDataType.OTHER));
    }

    @Override
    public final void accept(Context<?> ctx) {
        super.accept(ctx);
    }

    @Override
    public final Field<T> $field() {
        return (Field)this.getArguments().get(0);
    }

    @Override
    public final QOM.Max<T> $field(Field<T> newValue) {
        return this.$constructor().apply(newValue, this.$distinct());
    }

    @Override
    public final QOM.Max<T> $distinct(boolean newValue) {
        return this.$constructor().apply(this.$field(), newValue);
    }

    public final Function2<? super Field<T>, ? super Boolean, ? extends QOM.Max<T>> $constructor() {
        return (a1, a2) -> new Max(a1, (boolean)a2);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$field(), this.$distinct(), (a1, a2) -> this.$constructor().apply((Field<T>)a1, (Boolean)a2), replacer);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        QOM.traverse(traverser, this, this.$field());
        return super.$traverse(traverser);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Max) {
            QOM.Max o = (QOM.Max)that;
            return StringUtils.equals(this.$field(), o.$field()) && this.$distinct() == o.$distinct();
        }
        return super.equals(that);
    }
}

