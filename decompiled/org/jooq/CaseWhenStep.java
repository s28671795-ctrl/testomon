/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jooq.Field;
import org.jooq.Support;

public interface CaseWhenStep<V, T>
extends Field<T> {
    @Support
    @NotNull
    public CaseWhenStep<V, T> when(V var1, T var2);

    @Support
    @NotNull
    public CaseWhenStep<V, T> when(V var1, Field<T> var2);

    @Support
    @NotNull
    public CaseWhenStep<V, T> when(Field<V> var1, T var2);

    @Support
    @NotNull
    public CaseWhenStep<V, T> when(Field<V> var1, Field<T> var2);

    @Support
    @NotNull
    public CaseWhenStep<V, T> mapValues(Map<V, T> var1);

    @Support
    @NotNull
    public CaseWhenStep<V, T> mapFields(Map<? extends Field<V>, ? extends Field<T>> var1);

    @Support
    @NotNull
    public Field<T> otherwise(T var1);

    @Support
    @NotNull
    public Field<T> otherwise(Field<T> var1);

    @Support
    @NotNull
    public Field<T> else_(T var1);

    @Support
    @NotNull
    public Field<T> else_(Field<T> var1);
}

