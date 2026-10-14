/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.ExecuteListener;

@FunctionalInterface
public interface ExecuteListenerProvider {
    @NotNull
    public ExecuteListener provide();
}

