/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.impl.AbstractFunction;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartListView;

final class Function1<T>
extends AbstractFunction<T> {
    private final Field<?> argument;

    Function1(Name name, DataType<T> type, Field<?> argument) {
        super(name, type, true);
        this.argument = argument;
    }

    @Override
    final QueryPart arguments() {
        return this.argument;
    }

    @Override
    public final QOM.UnmodifiableList<? extends Field<?>> $args() {
        return QOM.unmodifiable(QueryPartListView.wrap((QueryPart[])new Field[]{this.argument}));
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.argument, a -> new Function1(this.getQualifiedName(), this.getDataType(), (Field<?>)a), replacer);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof Function1) {
            return this.getQualifiedName().equals(((Function1)that).getQualifiedName()) && this.argument.equals(((Function1)that).argument);
        }
        return super.equals(that);
    }
}

