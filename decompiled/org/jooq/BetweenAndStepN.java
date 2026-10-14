/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.RowN;
import org.jooq.Support;

public interface BetweenAndStepN {
    @Support
    @NotNull
    public Condition and(Field<?> ... var1);

    @Support
    @NotNull
    public Condition and(Object ... var1);

    @Support
    @NotNull
    public Condition and(RowN var1);

    @Support
    @NotNull
    public Condition and(Record var1);
}

