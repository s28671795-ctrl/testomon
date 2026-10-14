/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.Field;
import org.jooq.Pro;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.SelectOnStep;
import org.jooq.Support;

public interface SelectJoinPartitionByStep<R extends Record>
extends SelectOnStep<R> {
    @Support(value={SQLDialect.ORACLE})
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectOnStep<R> partitionBy(Field<?> ... var1);

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectOnStep<R> partitionBy(Collection<? extends Field<?>> var1);
}

