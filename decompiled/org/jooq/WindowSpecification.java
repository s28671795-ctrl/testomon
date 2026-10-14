/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.GroupField;
import org.jooq.QueryPart;
import org.jooq.SortField;
import org.jooq.WindowDefinition;
import org.jooq.impl.QOM;

public interface WindowSpecification
extends QueryPart {
    @ApiStatus.Experimental
    @Nullable
    public WindowDefinition $windowDefinition();

    @ApiStatus.Experimental
    @NotNull
    public QOM.UnmodifiableList<? extends GroupField> $partitionBy();

    @ApiStatus.Experimental
    @NotNull
    public QOM.UnmodifiableList<? extends SortField<?>> $orderBy();

    @ApiStatus.Experimental
    @Nullable
    public QOM.FrameUnits $frameUnits();

    @ApiStatus.Experimental
    @Nullable
    public Integer $frameStart();

    @ApiStatus.Experimental
    @Nullable
    public Integer $frameEnd();

    @ApiStatus.Experimental
    @Nullable
    public QOM.FrameExclude $exclude();
}

