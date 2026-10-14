/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.Support;

public interface BetweenAndStepR<R extends Record> {
    @Support
    @NotNull
    public Condition and(R var1);
}

