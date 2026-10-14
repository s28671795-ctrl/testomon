/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.Record;
import org.jooq.RecordType;
import org.jooq.RecordUnmapper;

@FunctionalInterface
public interface RecordUnmapperProvider {
    @NotNull
    public <E, R extends Record> RecordUnmapper<E, R> provide(Class<? extends E> var1, RecordType<R> var2);
}

