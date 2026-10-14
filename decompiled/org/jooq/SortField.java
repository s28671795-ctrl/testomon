/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Field;
import org.jooq.OrderField;
import org.jooq.SortOrder;
import org.jooq.Support;
import org.jooq.impl.QOM;

public interface SortField<T>
extends OrderField<T> {
    @NotNull
    public String getName();

    @NotNull
    public SortOrder getOrder();

    @Support
    @NotNull
    public SortField<T> nullsFirst();

    @Support
    @NotNull
    public SortField<T> nullsLast();

    @ApiStatus.Experimental
    @NotNull
    public Field<T> $field();

    @ApiStatus.Experimental
    @NotNull
    public <U> SortField<U> $field(Field<U> var1);

    @ApiStatus.Experimental
    @NotNull
    public SortOrder $sortOrder();

    @ApiStatus.Experimental
    @NotNull
    public SortField<T> $sortOrder(SortOrder var1);

    @ApiStatus.Experimental
    @Nullable
    public QOM.NullOrdering $nullOrdering();

    @ApiStatus.Experimental
    @NotNull
    public SortField<T> $nullOrdering(QOM.NullOrdering var1);
}

