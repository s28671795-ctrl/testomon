/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.Support;

public interface BetweenAndStep5<T1, T2, T3, T4, T5> {
    @Support
    @NotNull
    public Condition and(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5);

    @Support
    @NotNull
    public Condition and(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5);

    @Support
    @NotNull
    public Condition and(Row5<T1, T2, T3, T4, T5> var1);

    @Support
    @NotNull
    public Condition and(Record5<T1, T2, T3, T4, T5> var1);
}

