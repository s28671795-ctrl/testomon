/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.RecordType;

@FunctionalInterface
public interface RecordMapperProvider {
    @NotNull
    public <R extends Record, E> RecordMapper<R, E> provide(RecordType<R> var1, Class<? extends E> var2);
}

