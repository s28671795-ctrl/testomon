/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jooq.CaseWhenStep;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Select;
import org.jooq.Support;

public interface CaseValueStep<V> {
    @Support
    @NotNull
    public <T> CaseWhenStep<V, T> when(V var1, T var2);

    @Support
    @NotNull
    public <T> CaseWhenStep<V, T> when(V var1, Field<T> var2);

    @Support
    @NotNull
    public <T> CaseWhenStep<V, T> when(V var1, Select<? extends Record1<T>> var2);

    @Support
    @NotNull
    public <T> CaseWhenStep<V, T> when(Field<V> var1, T var2);

    @Support
    @NotNull
    public <T> CaseWhenStep<V, T> when(Field<V> var1, Field<T> var2);

    @Support
    @NotNull
    public <T> CaseWhenStep<V, T> when(Field<V> var1, Select<? extends Record1<T>> var2);

    @Support
    @NotNull
    public <T> CaseWhenStep<V, T> mapValues(Map<V, T> var1);

    @Support
    @NotNull
    public <T> CaseWhenStep<V, T> mapFields(Map<? extends Field<V>, ? extends Field<T>> var1);
}

