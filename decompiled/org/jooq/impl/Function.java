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
import org.jooq.impl.DSL;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.Tools;

final class Function<T>
extends AbstractFunction<T> {
    private final QueryPartList<Field<?>> arguments;

    Function(String name, DataType<T> type, Field<?> ... arguments) {
        this(DSL.unquotedName(name), type, arguments);
    }

    Function(Name name, DataType<T> type, Field<?> ... arguments) {
        this(name, type, true, arguments);
    }

    Function(Name name, DataType<T> type, boolean applySchemaMapping, Field<?> ... arguments) {
        super(name, type, applySchemaMapping);
        this.arguments = new QueryPartList((QueryPart[])arguments);
    }

    @Override
    final QueryPart arguments() {
        return this.arguments;
    }

    @Override
    public final QOM.UnmodifiableList<? extends Field<?>> $args() {
        return QOM.unmodifiable(this.arguments);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$args(), a -> new Function(this.getQualifiedName(), this.getDataType(), a.toArray(Tools.EMPTY_FIELD)), replacer);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof Function) {
            return this.getQualifiedName().equals(((Function)that).getQualifiedName()) && this.arguments.equals(((Function)that).arguments);
        }
        return super.equals(that);
    }
}

