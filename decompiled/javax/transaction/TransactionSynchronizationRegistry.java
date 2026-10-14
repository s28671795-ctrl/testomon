/*
 * Decompiled with CFR 0.152.
 */
package javax.transaction;

import javax.transaction.Synchronization;

public interface TransactionSynchronizationRegistry {
    public Object getResource(Object var1);

    public boolean getRollbackOnly();

    public Object getTransactionKey();

    public int getTransactionStatus();

    public void putResource(Object var1, Object var2);

    public void registerInterposedSynchronization(Synchronization var1);

    public void setRollbackOnly();
}

