/*
 * Decompiled with CFR 0.152.
 */
package javax.transaction;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import javax.transaction.xa.XAResource;

public interface Transaction {
    public void commit() throws HeuristicMixedException, HeuristicRollbackException, RollbackException, SecurityException, SystemException;

    public boolean delistResource(XAResource var1, int var2) throws IllegalStateException, SystemException;

    public boolean enlistResource(XAResource var1) throws IllegalStateException, RollbackException, SystemException;

    public int getStatus() throws SystemException;

    public void registerSynchronization(Synchronization var1) throws IllegalStateException, RollbackException, SystemException;

    public void rollback() throws IllegalStateException, SystemException;

    public void setRollbackOnly() throws IllegalStateException, SystemException;
}

