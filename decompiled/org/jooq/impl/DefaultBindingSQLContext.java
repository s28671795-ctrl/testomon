/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Map;
import org.jooq.BindingSQLContext;
import org.jooq.Configuration;
import org.jooq.ContextConverter;
import org.jooq.Converter;
import org.jooq.ConverterContext;
import org.jooq.RenderContext;
import org.jooq.impl.AbstractScope;

class DefaultBindingSQLContext<U>
extends AbstractScope
implements BindingSQLContext<U> {
    private final RenderContext render;
    private final U value;
    private final String variable;

    DefaultBindingSQLContext(Configuration configuration, Map<Object, Object> data, RenderContext render, U value) {
        this(configuration, data, render, value, "?");
    }

    DefaultBindingSQLContext(Configuration configuration, Map<Object, Object> data, RenderContext render, U value, String variable) {
        super(configuration, data);
        this.render = render;
        this.value = value;
        this.variable = variable;
    }

    @Override
    public final ConverterContext converterContext() {
        return this.render.converterContext();
    }

    @Override
    public final RenderContext render() {
        return this.render;
    }

    @Override
    public final U value() {
        return this.value;
    }

    @Override
    public final String variable() {
        return this.variable;
    }

    @Override
    public <T> BindingSQLContext<T> convert(Converter<? extends T, ? super U> converter) {
        return new DefaultBindingSQLContext<T>(this.configuration, this.data, this.render, ContextConverter.scoped(converter).to(this.value, this.converterContext()), this.variable);
    }

    public String toString() {
        return "DefaultBindingSQLContext [value=" + String.valueOf(this.value) + ", variable=" + this.variable + "]";
    }
}

