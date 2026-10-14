/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Support;

public interface BetweenAndStep<T> {
    @Support
    @NotNull
    public Condition and(T var1);

    @Support
    @NotNull
    public Condition and(Field<T> var1);
}

