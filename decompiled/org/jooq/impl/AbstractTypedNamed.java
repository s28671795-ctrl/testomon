/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Binding;
import org.jooq.Comment;
import org.jooq.Configuration;
import org.jooq.ContextConverter;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Generator;
import org.jooq.Name;
import org.jooq.Typed;
import org.jooq.impl.AbstractNamed;
import org.jooq.impl.TypedReference;

abstract class AbstractTypedNamed<T>
extends AbstractNamed
implements Typed<T> {
    private final DataType<T> type;

    AbstractTypedNamed(Name name, Comment comment, DataType<T> type) {
        super(name, comment);
        this.type = type.computed() && !(this instanceof TypedReference) ? type.generatedAlwaysAs((Generator)null) : (type.defaulted() && !(this instanceof TypedReference) ? type.default_((Field)null) : type);
    }

    @Override
    public final ContextConverter<?, T> getConverter() {
        return this.getDataType().getConverter();
    }

    @Override
    public final Binding<?, T> getBinding() {
        return this.getDataType().getBinding();
    }

    @Override
    public final Class<T> getType() {
        return this.getDataType().getType();
    }

    DataType<?> getExpressionDataType() {
        return this.getDataType();
    }

    @Override
    public final DataType<T> getDataType() {
        return this.type;
    }

    @Override
    public final DataType<T> getDataType(Configuration configuration) {
        return this.getDataType().getDataType(configuration);
    }

    @Override
    public final DataType<T> $dataType() {
        return this.getDataType();
    }
}

