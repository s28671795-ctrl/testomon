/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.List;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Condition;
import org.jooq.SortField;
import org.jooq.Table;
import org.jooq.TableElement;

public interface Index
extends TableElement {
    @Nullable
    public Table<?> getTable();

    @NotNull
    public List<SortField<?>> getFields();

    @Nullable
    public Condition getWhere();

    public boolean getUnique();

    @ApiStatus.Experimental
    @Nullable
    public Table<?> $table();
}

