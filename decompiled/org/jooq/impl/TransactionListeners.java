/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Arrays;
import org.jooq.Configuration;
import org.jooq.TransactionContext;
import org.jooq.TransactionListener;
import org.jooq.conf.InvocationOrder;
import org.jooq.impl.Tools;

class TransactionListeners
implements TransactionListener {
    private final TransactionListener[] listeners;

    TransactionListeners(Configuration configuration) {
        this.listeners = Tools.map(configuration.transactionListenerProviders(), p -> p.provide(), TransactionListener[]::new);
    }

    @Override
    public final void beginStart(TransactionContext ctx) {
        for (TransactionListener listener : ctx.settings().getTransactionListenerStartInvocationOrder() != InvocationOrder.REVERSE ? Arrays.asList(this.listeners) : Tools.reverseIterable(this.listeners)) {
            listener.beginStart(ctx);
        }
    }

    @Override
    public final void beginEnd(TransactionContext ctx) {
        for (TransactionListener listener : ctx.settings().getTransactionListenerEndInvocationOrder() != InvocationOrder.REVERSE ? Arrays.asList(this.listeners) : Tools.reverseIterable(this.listeners)) {
            listener.beginEnd(ctx);
        }
    }

    @Override
    public final void commitStart(TransactionContext ctx) {
        for (TransactionListener listener : ctx.settings().getTransactionListenerStartInvocationOrder() != InvocationOrder.REVERSE ? Arrays.asList(this.listeners) : Tools.reverseIterable(this.listeners)) {
            listener.commitStart(ctx);
        }
    }

    @Override
    public final void commitEnd(TransactionContext ctx) {
        for (TransactionListener listener : ctx.settings().getTransactionListenerEndInvocationOrder() != InvocationOrder.REVERSE ? Arrays.asList(this.listeners) : Tools.reverseIterable(this.listeners)) {
            listener.commitEnd(ctx);
        }
    }

    @Override
    public final void rollbackStart(TransactionContext ctx) {
        for (TransactionListener listener : ctx.settings().getTransactionListenerStartInvocationOrder() != InvocationOrder.REVERSE ? Arrays.asList(this.listeners) : Tools.reverseIterable(this.listeners)) {
            listener.rollbackStart(ctx);
        }
    }

    @Override
    public final void rollbackEnd(TransactionContext ctx) {
        for (TransactionListener listener : ctx.settings().getTransactionListenerEndInvocationOrder() != InvocationOrder.REVERSE ? Arrays.asList(this.listeners) : Tools.reverseIterable(this.listeners)) {
            listener.rollbackEnd(ctx);
        }
    }
}

