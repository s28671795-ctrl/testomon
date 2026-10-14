/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.Support;

public interface BetweenAndStep3<T1, T2, T3> {
    @Support
    @NotNull
    public Condition and(Field<T1> var1, Field<T2> var2, Field<T3> var3);

    @Support
    @NotNull
    public Condition and(T1 var1, T2 var2, T3 var3);

    @Support
    @NotNull
    public Condition and(Row3<T1, T2, T3> var1);

    @Support
    @NotNull
    public Condition and(Record3<T1, T2, T3> var1);
}

