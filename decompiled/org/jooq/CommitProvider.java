/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jooq.Commits;

@FunctionalInterface
@ApiStatus.Experimental
public interface CommitProvider {
    @NotNull
    public Commits provide();
}

