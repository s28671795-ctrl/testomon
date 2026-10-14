/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.Support;

public interface BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> {
    @Support
    @NotNull
    public Condition and(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7);

    @Support
    @NotNull
    public Condition and(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7);

    @Support
    @NotNull
    public Condition and(Row7<T1, T2, T3, T4, T5, T6, T7> var1);

    @Support
    @NotNull
    public Condition and(Record7<T1, T2, T3, T4, T5, T6, T7> var1);
}

