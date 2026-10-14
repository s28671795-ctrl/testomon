/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Param;
import org.jooq.ParamMode;
import org.jooq.conf.ParamType;
import org.jooq.impl.AbstractParamX;
import org.jooq.impl.QOM;

@Deprecated
final class ConvertedVal<T>
extends AbstractParamX<T>
implements QOM.UNotYetImplemented {
    final AbstractParamX<?> delegate;

    ConvertedVal(AbstractParamX<?> delegate, DataType<T> type) {
        super(delegate.getUnqualifiedName(), type);
        AbstractParamX<?> abstractParamX;
        if (delegate instanceof ConvertedVal) {
            ConvertedVal c = (ConvertedVal)delegate;
            abstractParamX = c.delegate;
        } else {
            abstractParamX = delegate;
        }
        this.delegate = abstractParamX;
    }

    @Override
    public final void accept(Context<?> ctx) {
        ctx.visit(this.delegate);
    }

    @Override
    public final String getParamName() {
        return this.delegate.getParamName();
    }

    @Override
    public final T getValue() {
        return this.getDataType().convert(this.delegate.getValue());
    }

    @Override
    public final void setConverted0(Object value) {
        this.delegate.setConverted0(value);
    }

    @Override
    public final void setInline0(boolean inline2) {
        this.delegate.setInline0(inline2);
    }

    @Override
    public final boolean isInline() {
        return this.delegate.isInline();
    }

    @Override
    public final ParamType getParamType() {
        return this.delegate.getParamType();
    }

    @Override
    public final ParamMode getParamMode() {
        return this.delegate.getParamMode();
    }

    @Override
    public final T $value() {
        return this.getValue();
    }

    @Override
    public final Param<T> $value(T value) {
        return this.delegate.$value(this.delegate.getDataType().convert((Object)value));
    }

    @Override
    public final boolean $inline() {
        return this.delegate.$inline();
    }

    @Override
    public final Param<T> $inline(boolean inline2) {
        throw new QOM.NotYetImplementedException();
    }

    @Override
    public String toString() {
        return this.delegate.toString();
    }

    @Override
    public int hashCode() {
        return this.delegate.hashCode();
    }

    @Override
    public boolean equals(Object that) {
        return this.delegate.equals(that);
    }
}

