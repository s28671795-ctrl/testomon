/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.TransactionListener;

@FunctionalInterface
public interface TransactionListenerProvider {
    @NotNull
    public TransactionListener provide();
}

