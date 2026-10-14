/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jooq.Constraint;
import org.jooq.Named;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;

public interface Key<R extends Record>
extends Named {
    @NotNull
    public Table<R> getTable();

    @NotNull
    public List<TableField<R, ?>> getFields();

    @NotNull
    public @NotNull TableField<R, ?> @NotNull [] getFieldsArray();

    @NotNull
    public Constraint constraint();

    public boolean enforced();

    public boolean nullable();
}

