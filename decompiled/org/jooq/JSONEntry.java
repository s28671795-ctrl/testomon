/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jooq.Field;
import org.jooq.QueryPart;

public interface JSONEntry<T>
extends QueryPart {
    @NotNull
    public Field<String> key();

    @NotNull
    public Field<T> value();

    @ApiStatus.Experimental
    @NotNull
    public Field<String> $key();

    @ApiStatus.Experimental
    @NotNull
    public Field<?> $value();
}

