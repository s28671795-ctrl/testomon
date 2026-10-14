/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.TransactionContext;
import org.jooq.TransactionProvider;

public class NoTransactionProvider
implements TransactionProvider {
    @Override
    public final void begin(TransactionContext ctx) {
        throw new UnsupportedOperationException("No transaction provider configured");
    }

    @Override
    public final void commit(TransactionContext ctx) {
        throw new UnsupportedOperationException("No transaction provider configured");
    }

    @Override
    public final void rollback(TransactionContext ctx) {
        throw new UnsupportedOperationException("No transaction provider configured");
    }
}

