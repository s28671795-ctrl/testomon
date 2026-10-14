/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.DerivedColumnList;
import org.jooq.Record;
import org.jooq.ResultQuery;
import org.jooq.Table;
import org.jooq.impl.QOM;

public interface CommonTableExpression<R extends Record>
extends Table<R> {
    @ApiStatus.Experimental
    @NotNull
    public DerivedColumnList $derivedColumnList();

    @ApiStatus.Experimental
    @NotNull
    public ResultQuery<R> $query();

    @ApiStatus.Experimental
    @Nullable
    public QOM.Materialized $materialized();
}

