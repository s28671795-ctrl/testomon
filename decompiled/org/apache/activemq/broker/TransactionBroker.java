/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.jms.JMSException;
import javax.transaction.xa.XAException;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerFilter;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.ConsumerBrokerExchange;
import org.apache.activemq.broker.ProducerBrokerExchange;
import org.apache.activemq.broker.jmx.ManagedRegionBroker;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.BaseCommand;
import org.apache.activemq.command.ConnectionInfo;
import org.apache.activemq.command.LocalTransactionId;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.MessageAck;
import org.apache.activemq.command.ProducerInfo;
import org.apache.activemq.command.TransactionId;
import org.apache.activemq.command.XATransactionId;
import org.apache.activemq.state.ProducerState;
import org.apache.activemq.store.TransactionRecoveryListener;
import org.apache.activemq.store.TransactionStore;
import org.apache.activemq.transaction.LocalTransaction;
import org.apache.activemq.transaction.Synchronization;
import org.apache.activemq.transaction.Transaction;
import org.apache.activemq.transaction.XATransaction;
import org.apache.activemq.util.IOExceptionSupport;
import org.apache.activemq.util.WrappedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionBroker
extends BrokerFilter {
    private static final Logger LOG = LoggerFactory.getLogger(TransactionBroker.class);
    private TransactionStore transactionStore;
    private Map<TransactionId, XATransaction> xaTransactions = new LinkedHashMap<TransactionId, XATransaction>();

    public TransactionBroker(Broker next, TransactionStore transactionStore) {
        super(next);
        this.transactionStore = transactionStore;
    }

    @Override
    public void start() throws Exception {
        this.transactionStore.start();
        try {
            final ConnectionContext context = new ConnectionContext();
            context.setBroker(this);
            context.setInRecoveryMode(true);
            context.setTransactions(new ConcurrentHashMap<TransactionId, Transaction>());
            context.setProducerFlowControl(false);
            ProducerBrokerExchange producerExchange = new ProducerBrokerExchange();
            producerExchange.setMutable(true);
            producerExchange.setConnectionContext(context);
            producerExchange.setProducerState(new ProducerState(new ProducerInfo()));
            ConsumerBrokerExchange consumerExchange = new ConsumerBrokerExchange();
            consumerExchange.setConnectionContext(context);
            this.transactionStore.recover(new TransactionRecoveryListener(){

                @Override
                public void recover(XATransactionId xid, Message[] addedMessages, MessageAck[] aks) {
                    try {
                        int i;
                        TransactionBroker.this.beginTransaction(context, xid);
                        XATransaction transaction = (XATransaction)TransactionBroker.this.getTransaction(context, xid, false);
                        for (i = 0; i < addedMessages.length; ++i) {
                            TransactionBroker.this.forceDestinationWakeupOnCompletion(context, transaction, addedMessages[i].getDestination(), addedMessages[i]);
                        }
                        for (i = 0; i < aks.length; ++i) {
                            TransactionBroker.this.forceDestinationWakeupOnCompletion(context, transaction, aks[i].getDestination(), aks[i]);
                        }
                        transaction.setState((byte)2);
                        TransactionBroker.this.registerMBean(transaction);
                        LOG.debug("recovered prepared transaction: {}", (Object)transaction.getTransactionId());
                    }
                    catch (Throwable e) {
                        throw new WrappedException(e);
                    }
                }
            });
        }
        catch (WrappedException e) {
            Throwable cause = e.getCause();
            throw IOExceptionSupport.create("Recovery Failed: " + cause.getMessage(), cause);
        }
        this.next.start();
    }

    private void registerMBean(XATransaction transaction) {
        if (this.getBrokerService().getRegionBroker() instanceof ManagedRegionBroker) {
            ManagedRegionBroker managedRegionBroker = (ManagedRegionBroker)this.getBrokerService().getRegionBroker();
            managedRegionBroker.registerRecoveredTransactionMBean(transaction);
        }
    }

    private void forceDestinationWakeupOnCompletion(ConnectionContext context, Transaction transaction, ActiveMQDestination amqDestination, BaseCommand ack) throws Exception {
        Destination destination = this.addDestination(context, amqDestination, false);
        this.registerSync(destination, transaction, ack);
    }

    private void registerSync(Destination destination, Transaction transaction, BaseCommand command) {
        PreparedDestinationCompletion sync = new PreparedDestinationCompletion(destination, command.isMessage());
        Synchronization existing = transaction.findMatching(sync);
        if (existing != null) {
            ((PreparedDestinationCompletion)existing).incrementOpCount();
        } else {
            transaction.addSynchronization(sync);
        }
    }

    @Override
    public void stop() throws Exception {
        this.transactionStore.stop();
        this.next.stop();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public TransactionId[] getPreparedTransactions(ConnectionContext context) throws Exception {
        ArrayList<TransactionId> txs = new ArrayList<TransactionId>();
        Map<TransactionId, XATransaction> map = this.xaTransactions;
        synchronized (map) {
            for (Transaction transaction : this.xaTransactions.values()) {
                if (!transaction.isPrepared()) continue;
                LOG.debug("prepared transaction: {}", (Object)transaction.getTransactionId());
                txs.add(transaction.getTransactionId());
            }
        }
        TransactionId[] rc = new XATransactionId[txs.size()];
        txs.toArray(rc);
        LOG.debug("prepared transaction list size: {}", (Object)rc.length);
        return rc;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void beginTransaction(ConnectionContext context, TransactionId xid) throws Exception {
        if (xid.isXATransaction()) {
            XATransaction transaction = null;
            Map<TransactionId, XATransaction> map = this.xaTransactions;
            synchronized (map) {
                transaction = this.xaTransactions.get(xid);
                if (transaction != null) {
                    return;
                }
                transaction = new XATransaction(this.transactionStore, (XATransactionId)xid, this, context.getConnectionId());
                this.xaTransactions.put(xid, transaction);
            }
        } else {
            ConcurrentMap<TransactionId, Transaction> transactionMap = context.getTransactions();
            Transaction transaction = (Transaction)transactionMap.get(xid);
            if (transaction != null) {
                throw new JMSException("Transaction '" + xid + "' has already been started.");
            }
            transaction = new LocalTransaction(this.transactionStore, (LocalTransactionId)xid, context);
            transactionMap.put(xid, transaction);
        }
    }

    @Override
    public int prepareTransaction(ConnectionContext context, TransactionId xid) throws Exception {
        Transaction transaction = this.getTransaction(context, xid, false);
        return transaction.prepare();
    }

    @Override
    public void commitTransaction(ConnectionContext context, TransactionId xid, boolean onePhase) throws Exception {
        Transaction transaction = this.getTransaction(context, xid, true);
        transaction.commit(onePhase);
    }

    @Override
    public void rollbackTransaction(ConnectionContext context, TransactionId xid) throws Exception {
        Transaction transaction = this.getTransaction(context, xid, true);
        transaction.rollback();
    }

    @Override
    public void forgetTransaction(ConnectionContext context, TransactionId xid) throws Exception {
        Transaction transaction = this.getTransaction(context, xid, true);
        transaction.rollback();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void acknowledge(ConsumerBrokerExchange consumerExchange, MessageAck ack) throws Exception {
        ConnectionContext context = consumerExchange.getConnectionContext();
        Transaction originalTx = context.getTransaction();
        Transaction transaction = null;
        if (ack.isInTransaction()) {
            transaction = this.getTransaction(context, ack.getTransactionId(), false);
        }
        context.setTransaction(transaction);
        try {
            this.next.acknowledge(consumerExchange, ack);
        }
        finally {
            context.setTransaction(originalTx);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void send(ProducerBrokerExchange producerExchange, Message message) throws Exception {
        ConnectionContext context = producerExchange.getConnectionContext();
        Transaction originalTx = context.getTransaction();
        Transaction transaction = null;
        if (message.getTransactionId() != null) {
            transaction = this.getTransaction(context, message.getTransactionId(), false);
        }
        context.setTransaction(transaction);
        try {
            this.next.send(producerExchange, message);
        }
        finally {
            context.setTransaction(originalTx);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void removeConnection(ConnectionContext context, ConnectionInfo info, Throwable error) throws Exception {
        Iterator iter = context.getTransactions().values().iterator();
        while (iter.hasNext()) {
            try {
                Transaction transaction = (Transaction)iter.next();
                transaction.rollback();
            }
            catch (Exception e) {
                LOG.warn("ERROR Rolling back disconnected client's transactions: ", e);
            }
            iter.remove();
        }
        Map<TransactionId, XATransaction> map = this.xaTransactions;
        synchronized (map) {
            ArrayList<XATransaction> txs = new ArrayList<XATransaction>();
            for (XATransaction tx : this.xaTransactions.values()) {
                if (tx.getConnectionId() == null || !tx.getConnectionId().equals(info.getConnectionId()) || tx.isPrepared()) continue;
                txs.add(tx);
            }
            for (XATransaction tx : txs) {
                try {
                    tx.rollback();
                }
                catch (Exception e) {
                    LOG.warn("ERROR Rolling back disconnected client's xa transactions: ", e);
                }
            }
        }
        this.next.removeConnection(context, info, error);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Transaction getTransaction(ConnectionContext context, TransactionId xid, boolean mightBePrepared) throws JMSException, XAException {
        Transaction transaction = null;
        if (xid.isXATransaction()) {
            Map<TransactionId, XATransaction> map = this.xaTransactions;
            synchronized (map) {
                transaction = this.xaTransactions.get(xid);
            }
        } else {
            transaction = (Transaction)context.getTransactions().get(xid);
        }
        if (transaction != null) {
            return transaction;
        }
        if (xid.isXATransaction()) {
            XAException e = XATransaction.newXAException("Transaction '" + xid + "' has not been started.", -4);
            throw e;
        }
        throw new JMSException("Transaction '" + xid + "' has not been started.");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void removeTransaction(XATransactionId xid) {
        Map<TransactionId, XATransaction> map = this.xaTransactions;
        synchronized (map) {
            this.xaTransactions.remove(xid);
        }
    }

    static class PreparedDestinationCompletion
    extends Synchronization {
        final Destination destination;
        final boolean messageSend;
        int opCount = 1;

        public PreparedDestinationCompletion(Destination destination, boolean messageSend) {
            this.destination = destination;
            this.messageSend = messageSend;
        }

        public void incrementOpCount() {
            ++this.opCount;
        }

        public int hashCode() {
            return System.identityHashCode(this.destination) + System.identityHashCode(this.messageSend);
        }

        public boolean equals(Object other) {
            return other instanceof PreparedDestinationCompletion && this.destination.equals(((PreparedDestinationCompletion)other).destination) && this.messageSend == ((PreparedDestinationCompletion)other).messageSend;
        }

        @Override
        public void afterRollback() throws Exception {
            if (!this.messageSend) {
                this.destination.clearPendingMessages();
                LOG.debug("cleared pending from afterRollback: {}", (Object)this.destination);
            }
        }

        @Override
        public void afterCommit() throws Exception {
            if (this.messageSend) {
                this.destination.clearPendingMessages();
                this.destination.getDestinationStatistics().getEnqueues().add(this.opCount);
                this.destination.getDestinationStatistics().getMessages().add(this.opCount);
                LOG.debug("cleared pending from afterCommit: {}", (Object)this.destination);
            } else {
                this.destination.getDestinationStatistics().getDequeues().add(this.opCount);
                this.destination.getDestinationStatistics().getMessages().subtract(this.opCount);
            }
        }
    }
}

