/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.stream.Stream;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.NotNull;
import org.jooq.AttachableQueryPart;
import org.jooq.Batch;
import org.jooq.Block;
import org.jooq.Query;
import org.jooq.Results;
import org.jooq.impl.QOM;

public interface Queries
extends AttachableQueryPart,
Iterable<Query> {
    @NotNull
    public Queries concat(Queries var1);

    @NotNull
    public @NotNull Query @NotNull [] queries();

    @NotNull
    public Block block();

    public Batch batch();

    @Deprecated(forRemoval=true, since="3.10")
    @NotNull
    public Stream<Query> stream();

    @NotNull
    public Stream<Query> queryStream();

    @NotNull
    @Blocking
    public Results fetchMany();

    @Blocking
    public int @NotNull [] executeBatch();

    @ApiStatus.Experimental
    @NotNull
    public QOM.UnmodifiableList<? extends Query> $queries();
}

