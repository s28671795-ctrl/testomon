/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.Support;

public interface BetweenAndStep2<T1, T2> {
    @Support
    @NotNull
    public Condition and(Field<T1> var1, Field<T2> var2);

    @Support
    @NotNull
    public Condition and(T1 var1, T2 var2);

    @Support
    @NotNull
    public Condition and(Row2<T1, T2> var1);

    @Support
    @NotNull
    public Condition and(Record2<T1, T2> var1);
}

