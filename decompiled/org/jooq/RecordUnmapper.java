/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.Record;
import org.jooq.exception.MappingException;

@FunctionalInterface
public interface RecordUnmapper<E, R extends Record> {
    @NotNull
    public R unmap(E var1) throws MappingException;
}

