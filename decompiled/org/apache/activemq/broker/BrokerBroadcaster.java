/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker;

import java.util.ArrayList;
import java.util.List;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerFilter;
import org.apache.activemq.broker.Connection;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.ConsumerBrokerExchange;
import org.apache.activemq.broker.ProducerBrokerExchange;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.BrokerInfo;
import org.apache.activemq.command.ConnectionInfo;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.MessageAck;
import org.apache.activemq.command.ProducerInfo;
import org.apache.activemq.command.RemoveSubscriptionInfo;
import org.apache.activemq.command.SessionInfo;
import org.apache.activemq.command.TransactionId;

public class BrokerBroadcaster
extends BrokerFilter {
    protected volatile Broker[] listeners = new Broker[0];

    public BrokerBroadcaster(Broker next) {
        super(next);
    }

    @Override
    public void acknowledge(ConsumerBrokerExchange consumerExchange, MessageAck ack) throws Exception {
        this.next.acknowledge(consumerExchange, ack);
        Broker[] brokers = this.getListeners();
        for (int i = 0; i < brokers.length; ++i) {
            brokers[i].acknowledge(consumerExchange, ack);
        }
    }

    @Override
    public void addConnection(ConnectionContext context, ConnectionInfo info) throws Exception {
        this.next.addConnection(context, info);
        Broker[] brokers = this.getListeners();
        for (int i = 0; i < brokers.length; ++i) {
            brokers[i].addConnection(context, info);
        }
    }

    @Override
    public Subscription addConsumer(ConnectionContext context, ConsumerInfo info) throws Exception {
        Subscription answer = this.next.addConsumer(context, info);
        Broker[] brokers = this.getListeners();
        for (int i = 0; i < brokers.length; ++i) {
            brokers[i].addConsumer(context, info);
        }
        return answer;
    }

    @Override
    public void addProducer(ConnectionContext context, ProducerInfo info) throws Exception {
        this.next.addProducer(context, info);
        Broker[] brokers = this.getListeners();
        for (int i = 0; i < brokers.length; ++i) {
            brokers[i].addProducer(context, info);
        }
    }

    @Override
    public void commitTransaction(ConnectionContext context, TransactionId xid, boolean onePhase) throws Exception {
        this.next.commitTransaction(context, xid, onePhase);
        Broker[] brokers = this.getListeners();
        for (int i = 0; i < brokers.length; ++i) {
            brokers[i].commitTransaction(context, xid, onePhase);
        }
    }

    @Override
    public void removeSubscription(ConnectionContext context, RemoveSubscriptionInfo info) throws Exception {
        this.next.removeSubscription(context, info);
        Broker[] brokers = this.getListeners();
        for (int i = 0; i < brokers.length; ++i) {
            brokers[i].removeSubscription(context, info);
        }
    }

    @Override
    public int prepareTransaction(ConnectionContext context, TransactionId xid) throws Exception {
        int result = this.next.prepareTransaction(context, xid);
        Broker[] brokers = this.getListeners();
        for (int i = 0; i < brokers.length; ++i) {
            brokers[i].prepareTransaction(context, xid);
        }
        return result;
    }

    @Override
    public void removeConnection(ConnectionContext context, ConnectionInfo info, Throwable error) throws Exception {
        this.next.removeConnection(context, info, error);
        Broker[] brokers = this.getListeners();
        for (int i = 0; i < brokers.length; ++i) {
            brokers[i].removeConnection(context, info, error);
        }
    }

    @Override
    public void removeConsumer(ConnectionContext context, ConsumerInfo info) throws Exception {
        this.next.removeConsumer(context, info);
        Broker[] brokers = this.getListeners();
        for (int i = 0; i < brokers.length; ++i) {
            brokers[i].removeConsumer(context, info);
        }
    }

    @Override
    public void removeProducer(ConnectionContext context, ProducerInfo info) throws Exception {
        this.next.removeProducer(context, info);
        Broker[] brokers = this.getListeners();
        for (int i = 0; i < brokers.length; ++i) {
            brokers[i].removeProducer(context, info);
        }
    }

    @Override
    public void rollbackTransaction(ConnectionContext context, TransactionId xid) throws Exception {
        this.next.rollbackTransaction(context, xid);
        Broker[] brokers = this.getListeners();
        for (int i = 0; i < brokers.length; ++i) {
            brokers[i].rollbackTransaction(context, xid);
        }
    }

    @Override
    public void send(ProducerBrokerExchange producerExchange, Message messageSend) throws Exception {
        this.next.send(producerExchange, messageSend);
        Broker[] brokers = this.getListeners();
        for (int i = 0; i < brokers.length; ++i) {
            brokers[i].send(producerExchange, messageSend);
        }
    }

    @Override
    public void beginTransaction(ConnectionContext context, TransactionId xid) throws Exception {
        this.next.beginTransaction(context, xid);
        Broker[] brokers = this.getListeners();
        for (int i = 0; i < brokers.length; ++i) {
            brokers[i].beginTransaction(context, xid);
        }
    }

    @Override
    public void forgetTransaction(ConnectionContext context, TransactionId transactionId) throws Exception {
        this.next.forgetTransaction(context, transactionId);
        Broker[] brokers = this.getListeners();
        for (int i = 0; i < brokers.length; ++i) {
            brokers[i].forgetTransaction(context, transactionId);
        }
    }

    @Override
    public Destination addDestination(ConnectionContext context, ActiveMQDestination destination, boolean createIfTemporary) throws Exception {
        Destination result = this.next.addDestination(context, destination, createIfTemporary);
        Broker[] brokers = this.getListeners();
        for (int i = 0; i < brokers.length; ++i) {
            brokers[i].addDestination(context, destination, createIfTemporary);
        }
        return result;
    }

    @Override
    public void removeDestination(ConnectionContext context, ActiveMQDestination destination, long timeout2) throws Exception {
        this.next.removeDestination(context, destination, timeout2);
        Broker[] brokers = this.getListeners();
        for (int i = 0; i < brokers.length; ++i) {
            brokers[i].removeDestination(context, destination, timeout2);
        }
    }

    @Override
    public void start() throws Exception {
        this.next.start();
        Broker[] brokers = this.getListeners();
        for (int i = 0; i < brokers.length; ++i) {
            brokers[i].start();
        }
    }

    @Override
    public void stop() throws Exception {
        this.next.stop();
        Broker[] brokers = this.getListeners();
        for (int i = 0; i < brokers.length; ++i) {
            brokers[i].stop();
        }
    }

    @Override
    public void addSession(ConnectionContext context, SessionInfo info) throws Exception {
        this.next.addSession(context, info);
        Broker[] brokers = this.getListeners();
        for (int i = 0; i < brokers.length; ++i) {
            brokers[i].addSession(context, info);
        }
    }

    @Override
    public void removeSession(ConnectionContext context, SessionInfo info) throws Exception {
        this.next.removeSession(context, info);
        Broker[] brokers = this.getListeners();
        for (int i = 0; i < brokers.length; ++i) {
            brokers[i].removeSession(context, info);
        }
    }

    @Override
    public void gc() {
        this.next.gc();
        Broker[] brokers = this.getListeners();
        for (int i = 0; i < brokers.length; ++i) {
            brokers[i].gc();
        }
    }

    @Override
    public void addBroker(Connection connection, BrokerInfo info) {
        this.next.addBroker(connection, info);
        Broker[] brokers = this.getListeners();
        for (int i = 0; i < brokers.length; ++i) {
            brokers[i].addBroker(connection, info);
        }
    }

    protected Broker[] getListeners() {
        return this.listeners;
    }

    public synchronized void addListener(Broker broker) {
        List<Broker> tmp = this.getListenersAsList();
        tmp.add(broker);
        this.listeners = tmp.toArray(new Broker[tmp.size()]);
    }

    public synchronized void removeListener(Broker broker) {
        List<Broker> tmp = this.getListenersAsList();
        tmp.remove(broker);
        this.listeners = tmp.toArray(new Broker[tmp.size()]);
    }

    protected List<Broker> getListenersAsList() {
        ArrayList<Broker> tmp = new ArrayList<Broker>();
        Broker[] brokers = this.getListeners();
        for (int i = 0; i < brokers.length; ++i) {
            tmp.add(brokers[i]);
        }
        return tmp;
    }
}

