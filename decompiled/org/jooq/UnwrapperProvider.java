/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.Unwrapper;

@FunctionalInterface
public interface UnwrapperProvider {
    @NotNull
    public Unwrapper provide();
}

