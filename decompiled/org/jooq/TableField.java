/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.Nullable;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;

public interface TableField<R extends Record, T>
extends Field<T> {
    @Nullable
    public Table<R> getTable();
}

