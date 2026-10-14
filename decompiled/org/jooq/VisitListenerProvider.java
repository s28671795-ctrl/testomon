/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.VisitListener;

@FunctionalInterface
public interface VisitListenerProvider {
    @NotNull
    public VisitListener provide();
}

