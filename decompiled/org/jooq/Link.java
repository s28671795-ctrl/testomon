/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Name;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Schema;

@Pro
public interface Link
extends QueryPart {
    public Schema schema();

    public String name();

    @ApiStatus.Experimental
    @Nullable
    public Schema $schema();

    @ApiStatus.Experimental
    @NotNull
    public Name $name();
}

