/*
 * Decompiled with CFR 0.152.
 */
package javax.transaction;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

public interface UserTransaction {
    public void begin() throws NotSupportedException, SystemException;

    public void commit() throws HeuristicMixedException, HeuristicRollbackException, IllegalStateException, RollbackException, SecurityException, SystemException;

    public int getStatus() throws SystemException;

    public void rollback() throws IllegalStateException, SecurityException, SystemException;

    public void setRollbackOnly() throws IllegalStateException, SystemException;

    public void setTransactionTimeout(int var1) throws SystemException;
}

