/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transaction;

import java.io.IOException;
import javax.transaction.xa.XAException;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.command.LocalTransactionId;
import org.apache.activemq.command.TransactionId;
import org.apache.activemq.store.TransactionStore;
import org.apache.activemq.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalTransaction
extends Transaction {
    private static final Logger LOG = LoggerFactory.getLogger(LocalTransaction.class);
    private final TransactionStore transactionStore;
    private final LocalTransactionId xid;
    private final ConnectionContext context;

    public LocalTransaction(TransactionStore transactionStore, LocalTransactionId xid, ConnectionContext context) {
        this.transactionStore = transactionStore;
        this.xid = xid;
        this.context = context;
    }

    @Override
    public void commit(boolean onePhase) throws XAException, IOException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("commit: " + this.xid + " syncCount: " + this.size());
        }
        try {
            this.prePrepare();
        }
        catch (XAException e) {
            throw e;
        }
        catch (Throwable e) {
            LOG.warn("COMMIT FAILED: ", e);
            this.rollback();
            XAException xae = new XAException("COMMIT FAILED: Transaction rolled back");
            xae.errorCode = 104;
            xae.initCause(e);
            throw xae;
        }
        this.setState((byte)3);
        this.context.getTransactions().remove(this.xid);
        try {
            this.transactionStore.commit(this.getTransactionId(), false, this.preCommitTask, this.postCommitTask);
            this.waitPostCommitDone(this.postCommitTask);
        }
        catch (Throwable t2) {
            LOG.warn("Store COMMIT FAILED: ", t2);
            this.rollback();
            XAException xae = new XAException("STORE COMMIT FAILED: Transaction rolled back");
            xae.errorCode = 104;
            xae.initCause(t2);
            throw xae;
        }
    }

    @Override
    public void rollback() throws XAException, IOException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("rollback: " + this.xid + " syncCount: " + this.size());
        }
        this.setState((byte)3);
        this.context.getTransactions().remove(this.xid);
        this.transactionStore.rollback(this.getTransactionId());
        try {
            this.fireAfterRollback();
        }
        catch (Throwable e) {
            LOG.warn("POST ROLLBACK FAILED: ", e);
            XAException xae = new XAException("POST ROLLBACK FAILED");
            xae.errorCode = -3;
            xae.initCause(e);
            throw xae;
        }
    }

    @Override
    public int prepare() throws XAException {
        XAException xae = new XAException("Prepare not implemented on Local Transactions");
        xae.errorCode = -3;
        throw xae;
    }

    @Override
    public TransactionId getTransactionId() {
        return this.xid;
    }

    @Override
    public Logger getLog() {
        return LOG;
    }
}

