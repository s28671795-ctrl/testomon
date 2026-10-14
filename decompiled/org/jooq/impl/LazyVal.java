/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Param;
import org.jooq.ParamMode;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.Traverser;
import org.jooq.conf.ParamType;
import org.jooq.impl.AbstractParamX;
import org.jooq.impl.DSL;
import org.jooq.impl.Names;

final class LazyVal<T>
extends AbstractParamX<T> {
    private final Field<T> field;
    private transient AbstractParamX<T> delegate;

    LazyVal(Field<T> field2) {
        super(Names.N_VALUE, field2.getDataType());
        this.field = field2;
    }

    private final void init() {
        if (this.delegate == null) {
            this.delegate = (AbstractParamX)DSL.val(null, this.field);
        }
    }

    @Override
    public final void accept(Context<?> ctx) {
        this.init();
        ctx.visit(this.delegate);
    }

    @Override
    public final String getParamName() {
        if (this.delegate == null) {
            return null;
        }
        this.init();
        return this.delegate.getParamName();
    }

    @Override
    public final T getValue() {
        if (this.delegate == null) {
            return null;
        }
        this.init();
        return this.delegate.getValue();
    }

    @Override
    public final void setConverted0(Object value) {
        this.init();
        this.delegate.setConverted0(value);
    }

    @Override
    public final void setInline0(boolean inline2) {
        this.init();
        this.delegate.setInline0(inline2);
    }

    @Override
    public final boolean isInline() {
        if (this.delegate == null) {
            return false;
        }
        this.init();
        return this.delegate.isInline();
    }

    @Override
    public final ParamType getParamType() {
        if (this.delegate == null) {
            return ParamType.INDEXED;
        }
        this.init();
        return this.delegate.getParamType();
    }

    @Override
    public final ParamMode getParamMode() {
        if (this.delegate == null) {
            return ParamMode.IN;
        }
        this.init();
        return this.delegate.getParamMode();
    }

    @Override
    public final T $value() {
        return this.delegate.$value();
    }

    @Override
    public final Param<T> $value(T value) {
        return this.delegate.$value(value);
    }

    @Override
    public final boolean $inline() {
        return this.delegate.$inline();
    }

    @Override
    public final Param<T> $inline(boolean inline2) {
        return this.delegate.$inline(inline2);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return this.delegate.$traverse(traverser);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return this.delegate.$replace(replacer);
    }
}

