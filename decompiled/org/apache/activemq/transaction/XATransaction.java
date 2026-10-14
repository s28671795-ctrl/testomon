/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transaction;

import java.io.IOException;
import javax.transaction.xa.XAException;
import org.apache.activemq.broker.TransactionBroker;
import org.apache.activemq.command.ConnectionId;
import org.apache.activemq.command.TransactionId;
import org.apache.activemq.command.XATransactionId;
import org.apache.activemq.store.TransactionStore;
import org.apache.activemq.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XATransaction
extends Transaction {
    private static final Logger LOG = LoggerFactory.getLogger(XATransaction.class);
    private final TransactionStore transactionStore;
    private final XATransactionId xid;
    private final TransactionBroker broker;
    private final ConnectionId connectionId;

    public XATransaction(TransactionStore transactionStore, XATransactionId xid, TransactionBroker broker, ConnectionId connectionId) {
        this.transactionStore = transactionStore;
        this.xid = xid;
        this.broker = broker;
        this.connectionId = connectionId;
        if (LOG.isDebugEnabled()) {
            LOG.debug("XA Transaction new/begin : " + xid);
        }
    }

    @Override
    public void commit(boolean onePhase) throws XAException, IOException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("XA Transaction commit onePhase:" + onePhase + ", xid: " + this.xid);
        }
        switch (this.getState()) {
            case 0: {
                this.checkForPreparedState(onePhase);
                this.setStateFinished();
                break;
            }
            case 1: {
                this.checkForPreparedState(onePhase);
                this.doPrePrepare();
                this.setStateFinished();
                this.storeCommit(this.getTransactionId(), false, this.preCommitTask, this.postCommitTask);
                break;
            }
            case 2: {
                this.setStateFinished();
                this.storeCommit(this.getTransactionId(), true, this.preCommitTask, this.postCommitTask);
                break;
            }
            default: {
                this.illegalStateTransition("commit");
            }
        }
    }

    private void storeCommit(TransactionId txid, boolean wasPrepared, Runnable preCommit, Runnable postCommit) throws XAException, IOException {
        try {
            this.transactionStore.commit(this.getTransactionId(), wasPrepared, this.preCommitTask, this.postCommitTask);
            this.waitPostCommitDone(this.postCommitTask);
        }
        catch (XAException xae) {
            throw xae;
        }
        catch (Throwable t2) {
            LOG.warn("Store COMMIT FAILED: ", t2);
            this.rollback();
            XAException xae = XATransaction.newXAException("STORE COMMIT FAILED: Transaction rolled back", 104);
            xae.initCause(t2);
            throw xae;
        }
    }

    private void illegalStateTransition(String callName) throws XAException {
        XAException xae = XATransaction.newXAException("Cannot call " + callName + " now.", -6);
        throw xae;
    }

    private void checkForPreparedState(boolean onePhase) throws XAException {
        if (!onePhase) {
            XAException xae = XATransaction.newXAException("Cannot do 2 phase commit if the transaction has not been prepared", -6);
            throw xae;
        }
    }

    private void doPrePrepare() throws XAException, IOException {
        try {
            this.prePrepare();
        }
        catch (XAException e) {
            throw e;
        }
        catch (Throwable e) {
            LOG.warn("PRE-PREPARE FAILED: ", e);
            this.rollback();
            XAException xae = XATransaction.newXAException("PRE-PREPARE FAILED: Transaction rolled back", 104);
            xae.initCause(e);
            throw xae;
        }
    }

    @Override
    public void rollback() throws XAException, IOException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("XA Transaction rollback: " + this.xid);
        }
        switch (this.getState()) {
            case 0: {
                this.setStateFinished();
                break;
            }
            case 1: {
                this.setStateFinished();
                this.transactionStore.rollback(this.getTransactionId());
                this.doPostRollback();
                break;
            }
            case 2: {
                this.setStateFinished();
                this.transactionStore.rollback(this.getTransactionId());
                this.doPostRollback();
                break;
            }
            case 3: {
                this.transactionStore.rollback(this.getTransactionId());
                this.doPostRollback();
                break;
            }
            default: {
                throw XATransaction.newXAException("Invalid state: " + this.getState(), 105);
            }
        }
    }

    private void doPostRollback() throws XAException {
        try {
            this.fireAfterRollback();
        }
        catch (Throwable e) {
            LOG.warn("POST ROLLBACK FAILED: ", e);
            XAException xae = XATransaction.newXAException("POST ROLLBACK FAILED", -3);
            xae.initCause(e);
            throw xae;
        }
    }

    @Override
    public int prepare() throws XAException, IOException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("XA Transaction prepare: " + this.xid);
        }
        switch (this.getState()) {
            case 0: {
                this.setStateFinished();
                return 3;
            }
            case 1: {
                this.doPrePrepare();
                this.setState((byte)2);
                this.transactionStore.prepare(this.getTransactionId());
                return 0;
            }
        }
        this.illegalStateTransition("prepare");
        return 3;
    }

    private void setStateFinished() {
        this.setState((byte)3);
        this.broker.removeTransaction(this.xid);
    }

    public ConnectionId getConnectionId() {
        return this.connectionId;
    }

    @Override
    public TransactionId getTransactionId() {
        return this.xid;
    }

    @Override
    public Logger getLog() {
        return LOG;
    }

    public XATransactionId getXid() {
        return this.xid;
    }
}

