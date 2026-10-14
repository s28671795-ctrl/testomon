/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transaction;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import javax.jms.TransactionRolledBackException;
import javax.transaction.xa.XAException;
import org.apache.activemq.command.TransactionId;
import org.apache.activemq.transaction.Synchronization;
import org.slf4j.Logger;

public abstract class Transaction {
    public static final byte START_STATE = 0;
    public static final byte IN_USE_STATE = 1;
    public static final byte PREPARED_STATE = 2;
    public static final byte FINISHED_STATE = 3;
    boolean committed = false;
    Throwable rollackOnlyCause = null;
    private final ArrayList<Synchronization> synchronizations = new ArrayList();
    private byte state = 0;
    protected FutureTask<?> preCommitTask = new FutureTask<Object>(new Callable<Object>(){

        @Override
        public Object call() throws Exception {
            Transaction.this.doPreCommit();
            return null;
        }
    });
    protected FutureTask<?> postCommitTask = new FutureTask<Object>(new Callable<Object>(){

        @Override
        public Object call() throws Exception {
            Transaction.this.doPostCommit();
            return null;
        }
    });

    public byte getState() {
        return this.state;
    }

    public void setState(byte state) {
        this.state = state;
    }

    public boolean isCommitted() {
        return this.committed;
    }

    public void setCommitted(boolean committed) {
        this.committed = committed;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addSynchronization(Synchronization r) {
        ArrayList<Synchronization> arrayList = this.synchronizations;
        synchronized (arrayList) {
            this.synchronizations.add(r);
        }
        if (this.state == 0) {
            this.state = 1;
        }
    }

    public Synchronization findMatching(Synchronization r) {
        int existing = this.synchronizations.indexOf(r);
        if (existing != -1) {
            return this.synchronizations.get(existing);
        }
        return null;
    }

    public void removeSynchronization(Synchronization r) {
        this.synchronizations.remove(r);
    }

    public void prePrepare() throws Exception {
        switch (this.state) {
            case 0: 
            case 1: {
                break;
            }
            default: {
                XAException xae = Transaction.newXAException("Prepare cannot be called now", -6);
                throw xae;
            }
        }
        if (this.isRollbackOnly()) {
            XAException xae = Transaction.newXAException("COMMIT FAILED: Transaction marked rollback only", 100);
            TransactionRolledBackException transactionRolledBackException = new TransactionRolledBackException(xae.getLocalizedMessage());
            transactionRolledBackException.initCause(this.rollackOnlyCause);
            xae.initCause(transactionRolledBackException);
            throw xae;
        }
    }

    protected void fireBeforeCommit() throws Exception {
        for (Synchronization s2 : this.synchronizations) {
            s2.beforeCommit();
        }
    }

    protected void fireAfterCommit() throws Exception {
        for (Synchronization s2 : this.synchronizations) {
            s2.afterCommit();
        }
    }

    public void fireAfterRollback() throws Exception {
        Collections.reverse(this.synchronizations);
        for (Synchronization s2 : this.synchronizations) {
            s2.afterRollback();
        }
    }

    public String toString() {
        return "Local-" + this.getTransactionId() + "[synchronizations=" + this.synchronizations + "]";
    }

    public abstract void commit(boolean var1) throws XAException, IOException;

    public abstract void rollback() throws XAException, IOException;

    public abstract int prepare() throws XAException, IOException;

    public abstract TransactionId getTransactionId();

    public abstract Logger getLog();

    public boolean isPrepared() {
        return this.getState() == 2;
    }

    public int size() {
        return this.synchronizations.size();
    }

    protected void waitPostCommitDone(FutureTask<?> postCommitTask) throws XAException, IOException {
        try {
            postCommitTask.get();
        }
        catch (InterruptedException e) {
            throw new InterruptedIOException(e.toString());
        }
        catch (ExecutionException e) {
            Throwable t2 = e.getCause();
            if (t2 instanceof XAException) {
                throw (XAException)t2;
            }
            if (t2 instanceof IOException) {
                throw (IOException)t2;
            }
            throw new XAException(e.toString());
        }
    }

    protected void doPreCommit() throws XAException {
        try {
            this.fireBeforeCommit();
        }
        catch (Throwable e) {
            this.getLog().warn("PRE COMMIT FAILED: ", e);
            XAException xae = Transaction.newXAException("PRE COMMIT FAILED", -3);
            xae.initCause(e);
            throw xae;
        }
    }

    protected void doPostCommit() throws XAException {
        try {
            this.setCommitted(true);
            this.fireAfterCommit();
        }
        catch (Throwable e) {
            this.getLog().warn("POST COMMIT FAILED: ", e);
            XAException xae = Transaction.newXAException("POST COMMIT FAILED", -3);
            xae.initCause(e);
            throw xae;
        }
    }

    public static XAException newXAException(String s2, int errorCode) {
        XAException xaException = new XAException(s2 + " " + "xaErrorCode:" + errorCode);
        xaException.errorCode = errorCode;
        return xaException;
    }

    public void setRollbackOnly(Throwable cause) {
        if (!this.isRollbackOnly()) {
            this.getLog().trace("setting rollback only, cause:", cause);
            this.rollackOnlyCause = cause;
        }
    }

    public boolean isRollbackOnly() {
        return this.rollackOnlyCause != null;
    }
}

