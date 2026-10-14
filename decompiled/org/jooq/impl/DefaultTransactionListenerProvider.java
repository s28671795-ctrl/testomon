/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.io.Serializable;
import org.jooq.TransactionListener;
import org.jooq.TransactionListenerProvider;
import org.jooq.impl.Tools;

public class DefaultTransactionListenerProvider
implements TransactionListenerProvider,
Serializable {
    private final TransactionListener listener;

    public static TransactionListenerProvider[] providers(TransactionListener ... listeners) {
        return Tools.map(listeners, DefaultTransactionListenerProvider::new, TransactionListenerProvider[]::new);
    }

    public DefaultTransactionListenerProvider(TransactionListener listener) {
        this.listener = listener;
    }

    @Override
    public final TransactionListener provide() {
        return this.listener;
    }

    public String toString() {
        return this.listener.toString();
    }
}

