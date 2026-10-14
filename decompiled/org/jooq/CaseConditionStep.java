/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Select;
import org.jooq.Support;

public interface CaseConditionStep<T>
extends Field<T> {
    @Support
    @NotNull
    public CaseConditionStep<T> when(Condition var1, T var2);

    @Support
    @NotNull
    public CaseConditionStep<T> when(Condition var1, Field<T> var2);

    @Support
    @NotNull
    public CaseConditionStep<T> when(Condition var1, Select<? extends Record1<T>> var2);

    @Support
    @NotNull
    public CaseConditionStep<T> when(Field<Boolean> var1, T var2);

    @Support
    @NotNull
    public CaseConditionStep<T> when(Field<Boolean> var1, Field<T> var2);

    @Support
    @NotNull
    public CaseConditionStep<T> when(Field<Boolean> var1, Select<? extends Record1<T>> var2);

    @Support
    @NotNull
    public Field<T> otherwise(T var1);

    @Support
    @NotNull
    public Field<T> otherwise(Field<T> var1);

    @Support
    @NotNull
    public Field<T> otherwise(Select<? extends Record1<T>> var1);

    @Support
    @NotNull
    public Field<T> else_(T var1);

    @Support
    @NotNull
    public Field<T> else_(Field<T> var1);

    @Support
    @NotNull
    public Field<T> else_(Select<? extends Record1<T>> var1);
}

