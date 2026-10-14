/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jooq.MigrationListener;

@FunctionalInterface
@ApiStatus.Experimental
public interface MigrationListenerProvider {
    @NotNull
    public MigrationListener provide();
}

