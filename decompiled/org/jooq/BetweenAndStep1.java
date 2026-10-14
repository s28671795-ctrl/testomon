/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Row1;
import org.jooq.Support;

public interface BetweenAndStep1<T1> {
    @Support
    @NotNull
    public Condition and(Field<T1> var1);

    @Support
    @NotNull
    public Condition and(T1 var1);

    @Support
    @NotNull
    public Condition and(Row1<T1> var1);

    @Support
    @NotNull
    public Condition and(Record1<T1> var1);
}

