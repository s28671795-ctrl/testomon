/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.ParseListener;
import org.jooq.Pro;

@FunctionalInterface
@Pro
public interface ParseListenerProvider {
    @NotNull
    public ParseListener provide();
}

