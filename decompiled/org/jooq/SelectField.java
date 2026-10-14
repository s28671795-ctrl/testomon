/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jooq.Binding;
import org.jooq.Converter;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Named;
import org.jooq.SelectFieldOrAsterisk;
import org.jooq.Support;
import org.jooq.Typed;

public interface SelectField<T>
extends SelectFieldOrAsterisk,
Named,
Typed<T> {
    @Support
    @NotNull
    public SelectField<T> as(String var1);

    @Support
    @NotNull
    public SelectField<T> as(Name var1);

    @Support
    @NotNull
    public SelectField<T> as(Field<?> var1);

    @NotNull
    public <U> SelectField<U> convert(Binding<T, U> var1);

    @NotNull
    public <U> SelectField<U> convert(Converter<T, U> var1);

    @NotNull
    public <U> SelectField<U> convert(Class<U> var1, Function<? super T, ? extends U> var2, Function<? super U, ? extends T> var3);

    @NotNull
    public <U> SelectField<U> convertFrom(Class<U> var1, Function<? super T, ? extends U> var2);

    @NotNull
    public <U> SelectField<U> convertFrom(Function<? super T, ? extends U> var1);

    @NotNull
    public <U> SelectField<U> convertTo(Class<U> var1, Function<? super U, ? extends T> var2);

    @NotNull
    public <U> SelectField<U> convertTo(Function<? super U, ? extends T> var1);
}

