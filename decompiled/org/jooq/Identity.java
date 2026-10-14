/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.io.Serializable;
import org.jetbrains.annotations.NotNull;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;

public interface Identity<R extends Record, T>
extends Serializable {
    @NotNull
    public Table<R> getTable();

    @NotNull
    public TableField<R, T> getField();
}

