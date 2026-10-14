/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Name;
import org.jooq.WindowSpecification;
import org.jooq.WindowSpecificationOrderByStep;

public interface WindowDefinition
extends WindowSpecificationOrderByStep {
    @ApiStatus.Experimental
    @NotNull
    public Name $name();

    @ApiStatus.Experimental
    @Nullable
    public WindowSpecification $windowSpecification();
}

