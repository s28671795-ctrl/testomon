/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.impl.AbstractAggregateFunction;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;

final class DefaultAggregateFunction<T>
extends AbstractAggregateFunction<T> {
    DefaultAggregateFunction(String name, DataType<T> type, Field<?> ... arguments) {
        super(name, type, arguments);
    }

    DefaultAggregateFunction(Name name, DataType<T> type, Field<?> ... arguments) {
        super(name, type, arguments);
    }

    DefaultAggregateFunction(boolean distinct2, String name, DataType<T> type, Field<?> ... arguments) {
        super(distinct2, name, type, arguments);
    }

    DefaultAggregateFunction(boolean distinct2, Name name, DataType<T> type, Field<?> ... arguments) {
        super(distinct2, name, type, arguments);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.arguments, a -> new DefaultAggregateFunction(this.distinct, this.getQualifiedName(), this.getDataType(), a.toArray((E[])Tools.EMPTY_FIELD)), replacer);
    }
}

