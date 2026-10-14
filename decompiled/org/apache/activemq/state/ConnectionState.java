/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.state;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ConnectionInfo;
import org.apache.activemq.command.ConsumerId;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.DestinationInfo;
import org.apache.activemq.command.SessionId;
import org.apache.activemq.command.SessionInfo;
import org.apache.activemq.command.TransactionId;
import org.apache.activemq.state.SessionState;
import org.apache.activemq.state.TransactionState;

public class ConnectionState {
    ConnectionInfo info;
    private final ConcurrentMap<TransactionId, TransactionState> transactions = new ConcurrentHashMap<TransactionId, TransactionState>();
    private final ConcurrentMap<SessionId, SessionState> sessions = new ConcurrentHashMap<SessionId, SessionState>();
    private final List<DestinationInfo> tempDestinations = Collections.synchronizedList(new ArrayList());
    private final AtomicBoolean shutdown = new AtomicBoolean(false);
    private boolean connectionInterruptProcessingComplete = true;
    private ConcurrentMap<ConsumerId, ConsumerInfo> recoveringPullConsumers;

    public ConnectionState(ConnectionInfo info) {
        this.info = info;
        this.addSession(new SessionInfo(info, -1L));
    }

    public String toString() {
        return this.info.toString();
    }

    public void reset(ConnectionInfo info) {
        this.info = info;
        this.transactions.clear();
        this.sessions.clear();
        this.tempDestinations.clear();
        this.shutdown.set(false);
        this.addSession(new SessionInfo(info, -1L));
    }

    public void addTempDestination(DestinationInfo info) {
        this.checkShutdown();
        this.tempDestinations.add(info);
    }

    public void removeTempDestination(ActiveMQDestination destination) {
        Iterator<DestinationInfo> iter = this.tempDestinations.iterator();
        while (iter.hasNext()) {
            DestinationInfo di = iter.next();
            if (!di.getDestination().equals(destination)) continue;
            iter.remove();
        }
    }

    public void addTransactionState(TransactionId id) {
        this.checkShutdown();
        this.transactions.put(id, new TransactionState(id));
    }

    public TransactionState getTransactionState(TransactionId id) {
        return (TransactionState)this.transactions.get(id);
    }

    public Collection<TransactionState> getTransactionStates() {
        return this.transactions.values();
    }

    public TransactionState removeTransactionState(TransactionId id) {
        return (TransactionState)this.transactions.remove(id);
    }

    public void addSession(SessionInfo info) {
        this.checkShutdown();
        this.sessions.put(info.getSessionId(), new SessionState(info));
    }

    public SessionState removeSession(SessionId id) {
        return (SessionState)this.sessions.remove(id);
    }

    public SessionState getSessionState(SessionId id) {
        return (SessionState)this.sessions.get(id);
    }

    public ConnectionInfo getInfo() {
        return this.info;
    }

    public Set<SessionId> getSessionIds() {
        return this.sessions.keySet();
    }

    public List<DestinationInfo> getTempDestinations() {
        return this.tempDestinations;
    }

    public Collection<SessionState> getSessionStates() {
        return this.sessions.values();
    }

    private void checkShutdown() {
        if (this.shutdown.get()) {
            throw new IllegalStateException("Disposed");
        }
    }

    public void shutdown() {
        if (this.shutdown.compareAndSet(false, true)) {
            for (SessionState ss : this.sessions.values()) {
                ss.shutdown();
            }
        }
    }

    public Map<ConsumerId, ConsumerInfo> getRecoveringPullConsumers() {
        if (this.recoveringPullConsumers == null) {
            this.recoveringPullConsumers = new ConcurrentHashMap<ConsumerId, ConsumerInfo>();
        }
        return this.recoveringPullConsumers;
    }

    public void setConnectionInterruptProcessingComplete(boolean connectionInterruptProcessingComplete) {
        this.connectionInterruptProcessingComplete = connectionInterruptProcessingComplete;
    }

    public boolean isConnectionInterruptProcessingComplete() {
        return this.connectionInterruptProcessingComplete;
    }
}

