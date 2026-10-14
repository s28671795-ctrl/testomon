/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Transaction;
import org.jooq.TransactionContext;
import org.jooq.impl.AbstractScope;

class DefaultTransactionContext
extends AbstractScope
implements TransactionContext {
    Transaction transaction;
    Throwable cause;

    DefaultTransactionContext(Configuration configuration) {
        super(configuration);
    }

    @Override
    public final Transaction transaction() {
        return this.transaction;
    }

    @Override
    public final TransactionContext transaction(Transaction t2) {
        this.transaction = t2;
        return this;
    }

    @Override
    public final Exception cause() {
        Exception e;
        Throwable throwable = this.cause;
        return throwable instanceof Exception ? (e = (Exception)throwable) : null;
    }

    @Override
    public final TransactionContext cause(Exception c) {
        this.cause = c;
        return this;
    }

    @Override
    public final Throwable causeThrowable() {
        return this.cause;
    }

    @Override
    public final TransactionContext causeThrowable(Throwable c) {
        this.cause = c;
        return this;
    }
}

