/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.CaseConditionStep;
import org.jooq.CaseValueStep;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Select;
import org.jooq.Support;

public interface Case {
    @Support
    @NotNull
    public <V> CaseValueStep<V> value(V var1);

    @Support
    @NotNull
    public <V> CaseValueStep<V> value(Field<V> var1);

    @Support
    @NotNull
    public <T> CaseConditionStep<T> when(Condition var1, T var2);

    @Support
    @NotNull
    public <T> CaseConditionStep<T> when(Condition var1, Field<T> var2);

    @Support
    @NotNull
    public <T> CaseConditionStep<T> when(Condition var1, Select<? extends Record1<T>> var2);

    @Support
    @NotNull
    public <T> CaseConditionStep<T> when(Field<Boolean> var1, T var2);

    @Support
    @NotNull
    public <T> CaseConditionStep<T> when(Field<Boolean> var1, Field<T> var2);

    @Support
    @NotNull
    public <T> CaseConditionStep<T> when(Field<Boolean> var1, Select<? extends Record1<T>> var2);
}

