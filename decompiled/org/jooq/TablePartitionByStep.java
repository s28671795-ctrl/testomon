/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jooq.Field;
import org.jooq.Pro;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.Support;
import org.jooq.TableOnStep;

public interface TablePartitionByStep<R extends Record>
extends TableOnStep<R> {
    @Support(value={SQLDialect.ORACLE})
    @NotNull
    @Pro
    public TableOnStep<R> partitionBy(Field<?> ... var1);

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    @Pro
    public TableOnStep<R> partitionBy(Collection<? extends Field<?>> var1);
}

