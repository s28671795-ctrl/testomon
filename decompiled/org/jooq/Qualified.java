/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.jooq.Catalog;
import org.jooq.Named;
import org.jooq.Schema;

public interface Qualified
extends Named {
    @Nullable
    public Catalog getCatalog();

    @Nullable
    public Schema getSchema();

    @ApiStatus.Experimental
    @Nullable
    public Schema $schema();
}

