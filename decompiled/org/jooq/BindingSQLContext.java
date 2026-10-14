/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.BindingScope;
import org.jooq.Converter;
import org.jooq.RenderContext;

public interface BindingSQLContext<U>
extends BindingScope {
    @NotNull
    public RenderContext render();

    public U value();

    @NotNull
    public String variable();

    @NotNull
    public <T> BindingSQLContext<T> convert(Converter<? extends T, ? super U> var1);
}

