/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.Record;
import org.jooq.SelectDistinctOnStep;
import org.jooq.SelectFieldOrAsterisk;
import org.jooq.Support;

public interface SelectSelectStep<R extends Record>
extends SelectDistinctOnStep<R> {
    @Support
    @NotNull
    @CheckReturnValue
    public SelectSelectStep<Record> select(SelectFieldOrAsterisk ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectSelectStep<Record> select(Collection<? extends SelectFieldOrAsterisk> var1);
}

