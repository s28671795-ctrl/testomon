/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore.db;

import org.h2.mvstore.MVStore;
import org.h2.mvstore.tx.Transaction;
import org.h2.store.InDoubtTransaction;

final class MVInDoubtTransaction
implements InDoubtTransaction {
    private final MVStore store;
    private final Transaction transaction;
    private int state = 0;

    MVInDoubtTransaction(MVStore mVStore, Transaction transaction) {
        this.store = mVStore;
        this.transaction = transaction;
    }

    @Override
    public void setState(int n) {
        if (n == 1) {
            this.transaction.commit();
        } else {
            this.transaction.rollback();
        }
        this.store.commit();
        this.state = n;
    }

    @Override
    public int getState() {
        return this.state;
    }

    @Override
    public String getTransactionName() {
        return this.transaction.getName();
    }
}

