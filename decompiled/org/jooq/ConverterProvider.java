/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.Nullable;
import org.jooq.Converter;

@FunctionalInterface
public interface ConverterProvider {
    @Nullable
    public <T, U> Converter<T, U> provide(Class<T> var1, Class<U> var2);
}

