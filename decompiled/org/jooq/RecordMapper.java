/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.function.Function;
import org.jetbrains.annotations.Nullable;
import org.jooq.Record;

@FunctionalInterface
public interface RecordMapper<R extends Record, E>
extends Function<R, E> {
    @Nullable
    public E map(R var1);

    @Override
    default public E apply(R record) {
        return this.map(record);
    }
}

