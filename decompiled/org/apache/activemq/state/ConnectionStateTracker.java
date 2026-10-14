/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.state;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.jms.TransactionRolledBackException;
import org.apache.activemq.command.Command;
import org.apache.activemq.command.ConnectionId;
import org.apache.activemq.command.ConnectionInfo;
import org.apache.activemq.command.ConsumerControl;
import org.apache.activemq.command.ConsumerId;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.DestinationInfo;
import org.apache.activemq.command.ExceptionResponse;
import org.apache.activemq.command.IntegerResponse;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.MessagePull;
import org.apache.activemq.command.ProducerId;
import org.apache.activemq.command.ProducerInfo;
import org.apache.activemq.command.Response;
import org.apache.activemq.command.SessionId;
import org.apache.activemq.command.SessionInfo;
import org.apache.activemq.command.TransactionInfo;
import org.apache.activemq.state.CommandVisitorAdapter;
import org.apache.activemq.state.ConnectionState;
import org.apache.activemq.state.ConsumerState;
import org.apache.activemq.state.ProducerState;
import org.apache.activemq.state.ResponseHandler;
import org.apache.activemq.state.SessionState;
import org.apache.activemq.state.Tracked;
import org.apache.activemq.state.TransactionState;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.util.IOExceptionSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionStateTracker
extends CommandVisitorAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(ConnectionStateTracker.class);
    private static final Tracked TRACKED_RESPONSE_MARKER = new Tracked(null);
    private static final int MESSAGE_PULL_SIZE = 400;
    protected final ConcurrentMap<ConnectionId, ConnectionState> connectionStates = new ConcurrentHashMap<ConnectionId, ConnectionState>();
    private boolean trackTransactions;
    private boolean restoreSessions = true;
    private boolean restoreConsumers = true;
    private boolean restoreProducers = true;
    private boolean restoreTransaction = true;
    private boolean trackMessages = true;
    private boolean trackTransactionProducers = true;
    private int maxCacheSize = 131072;
    private long currentCacheSize;
    private final Map<Object, Command> messageCache = new LinkedHashMap<Object, Command>(){

        @Override
        protected boolean removeEldestEntry(Map.Entry<Object, Command> eldest) {
            boolean result;
            boolean bl = result = ConnectionStateTracker.this.currentCacheSize > (long)ConnectionStateTracker.this.maxCacheSize;
            if (result) {
                if (eldest.getValue() instanceof Message) {
                    ConnectionStateTracker.this.currentCacheSize = ConnectionStateTracker.this.currentCacheSize - (long)((Message)eldest.getValue()).getSize();
                } else if (eldest.getValue() instanceof MessagePull) {
                    ConnectionStateTracker.this.currentCacheSize = ConnectionStateTracker.this.currentCacheSize - 400L;
                }
                if (LOG.isTraceEnabled()) {
                    LOG.trace("removing tracked message: " + eldest.getKey());
                }
            }
            return result;
        }
    };

    public Tracked track(Command command) throws IOException {
        try {
            return (Tracked)command.visit(this);
        }
        catch (IOException e) {
            throw e;
        }
        catch (Throwable e) {
            throw IOExceptionSupport.create(e);
        }
    }

    public void trackBack(Command command) {
        if (command != null) {
            if (this.trackMessages && command.isMessage()) {
                Message message = (Message)command;
                if (message.getTransactionId() == null) {
                    this.currentCacheSize += (long)message.getSize();
                }
            } else if (command instanceof MessagePull && ((MessagePull)command).isTracked()) {
                this.currentCacheSize += 400L;
            }
        }
    }

    public void restore(Transport transport) throws IOException {
        for (ConnectionState connectionState : this.connectionStates.values()) {
            connectionState.getInfo().setFailoverReconnect(true);
            if (LOG.isDebugEnabled()) {
                LOG.debug("conn: " + connectionState.getInfo().getConnectionId());
            }
            transport.oneway(connectionState.getInfo());
            this.restoreTempDestinations(transport, connectionState);
            if (this.restoreSessions) {
                this.restoreSessions(transport, connectionState);
            }
            if (!this.restoreTransaction) continue;
            this.restoreTransactions(transport, connectionState);
        }
        for (Command msg : this.messageCache.values()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("command: " + (msg.isMessage() ? ((Message)msg).getMessageId() : msg));
            }
            transport.oneway(msg);
        }
    }

    private void restoreTransactions(Transport transport, ConnectionState connectionState) throws IOException {
        Vector<TransactionInfo> toRollback = new Vector<TransactionInfo>();
        for (TransactionState transactionState : connectionState.getTransactionStates()) {
            TransactionInfo transactionInfo;
            Iterator<Object> lastCommand;
            if (LOG.isDebugEnabled()) {
                LOG.debug("tx: " + transactionState.getId());
            }
            if (!transactionState.getCommands().isEmpty() && (lastCommand = transactionState.getCommands().get(transactionState.getCommands().size() - 1)) instanceof TransactionInfo && (transactionInfo = (TransactionInfo)((Object)lastCommand)).getType() == 2) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("rolling back potentially completed tx: " + transactionState.getId());
                }
                toRollback.add(transactionInfo);
                continue;
            }
            for (ProducerState producerState : transactionState.getProducerStates().values()) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("tx replay producer :" + producerState.getInfo());
                }
                transport.oneway(producerState.getInfo());
            }
            for (Command command : transactionState.getCommands()) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("tx replay: " + command);
                }
                transport.oneway(command);
            }
            lastCommand = transactionState.getProducerStates().values().iterator();
            while (lastCommand.hasNext()) {
                ProducerState producerState;
                producerState = (ProducerState)lastCommand.next();
                if (LOG.isDebugEnabled()) {
                    LOG.debug("tx remove replayed producer :" + producerState.getInfo());
                }
                transport.oneway(producerState.getInfo().createRemoveCommand());
            }
        }
        for (TransactionInfo command : toRollback) {
            ExceptionResponse response = new ExceptionResponse();
            response.setException(new TransactionRolledBackException("Transaction completion in doubt due to failover. Forcing rollback of " + command.getTransactionId()));
            response.setCorrelationId(command.getCommandId());
            transport.getTransportListener().onCommand(response);
        }
    }

    protected void restoreSessions(Transport transport, ConnectionState connectionState) throws IOException {
        for (SessionState sessionState : connectionState.getSessionStates()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("session: " + sessionState.getInfo().getSessionId());
            }
            transport.oneway(sessionState.getInfo());
            if (this.restoreProducers) {
                this.restoreProducers(transport, sessionState);
            }
            if (!this.restoreConsumers) continue;
            this.restoreConsumers(transport, sessionState);
        }
    }

    protected void restoreConsumers(Transport transport, SessionState sessionState) throws IOException {
        ConnectionState connectionState = (ConnectionState)this.connectionStates.get(sessionState.getInfo().getSessionId().getParentId());
        boolean connectionInterruptionProcessingComplete = connectionState.isConnectionInterruptProcessingComplete();
        for (ConsumerState consumerState : sessionState.getConsumerStates()) {
            ConsumerInfo infoToSend = consumerState.getInfo();
            if (!connectionInterruptionProcessingComplete && infoToSend.getPrefetchSize() > 0) {
                infoToSend = consumerState.getInfo().copy();
                connectionState.getRecoveringPullConsumers().put(infoToSend.getConsumerId(), consumerState.getInfo());
                infoToSend.setPrefetchSize(0);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("restore consumer: " + infoToSend.getConsumerId() + " in pull mode pending recovery, overriding prefetch: " + consumerState.getInfo().getPrefetchSize());
                }
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("consumer: " + infoToSend.getConsumerId());
            }
            transport.oneway(infoToSend);
        }
    }

    protected void restoreProducers(Transport transport, SessionState sessionState) throws IOException {
        for (ProducerState producerState : sessionState.getProducerStates()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("producer: " + producerState.getInfo().getProducerId());
            }
            transport.oneway(producerState.getInfo());
        }
    }

    protected void restoreTempDestinations(Transport transport, ConnectionState connectionState) throws IOException {
        for (DestinationInfo info : connectionState.getTempDestinations()) {
            transport.oneway(info);
            if (!LOG.isDebugEnabled()) continue;
            LOG.debug("tempDest: " + info.getDestination());
        }
    }

    @Override
    public Response processAddDestination(DestinationInfo info) {
        ConnectionState cs;
        if (info != null && (cs = (ConnectionState)this.connectionStates.get(info.getConnectionId())) != null && info.getDestination().isTemporary()) {
            cs.addTempDestination(info);
        }
        return TRACKED_RESPONSE_MARKER;
    }

    @Override
    public Response processRemoveDestination(DestinationInfo info) {
        ConnectionState cs;
        if (info != null && (cs = (ConnectionState)this.connectionStates.get(info.getConnectionId())) != null && info.getDestination().isTemporary()) {
            cs.removeTempDestination(info.getDestination());
        }
        return TRACKED_RESPONSE_MARKER;
    }

    @Override
    public Response processAddProducer(ProducerInfo info) {
        SessionState ss;
        ConnectionState cs;
        ConnectionId connectionId;
        SessionId sessionId;
        if (info != null && info.getProducerId() != null && (sessionId = info.getProducerId().getParentId()) != null && (connectionId = sessionId.getParentId()) != null && (cs = (ConnectionState)this.connectionStates.get(connectionId)) != null && (ss = cs.getSessionState(sessionId)) != null) {
            ss.addProducer(info);
            if (info.isResponseRequired()) {
                return new Tracked(new ExceptionResponseCheckAction(info));
            }
        }
        return TRACKED_RESPONSE_MARKER;
    }

    @Override
    public Response processRemoveProducer(ProducerId id) {
        SessionState ss;
        ConnectionState cs;
        ConnectionId connectionId;
        SessionId sessionId;
        if (id != null && (sessionId = id.getParentId()) != null && (connectionId = sessionId.getParentId()) != null && (cs = (ConnectionState)this.connectionStates.get(connectionId)) != null && (ss = cs.getSessionState(sessionId)) != null) {
            ss.removeProducer(id);
        }
        return TRACKED_RESPONSE_MARKER;
    }

    @Override
    public Response processAddConsumer(ConsumerInfo info) {
        SessionState ss;
        ConnectionState cs;
        ConnectionId connectionId;
        SessionId sessionId;
        if (info != null && (sessionId = info.getConsumerId().getParentId()) != null && (connectionId = sessionId.getParentId()) != null && (cs = (ConnectionState)this.connectionStates.get(connectionId)) != null && (ss = cs.getSessionState(sessionId)) != null) {
            ss.addConsumer(info);
            if (info.isResponseRequired()) {
                return new Tracked(new ExceptionResponseCheckAction(info));
            }
        }
        return TRACKED_RESPONSE_MARKER;
    }

    @Override
    public Response processRemoveConsumer(ConsumerId id, long lastDeliveredSequenceId) {
        ConnectionState cs;
        ConnectionId connectionId;
        SessionId sessionId;
        if (id != null && (sessionId = id.getParentId()) != null && (connectionId = sessionId.getParentId()) != null && (cs = (ConnectionState)this.connectionStates.get(connectionId)) != null) {
            SessionState ss = cs.getSessionState(sessionId);
            if (ss != null) {
                ss.removeConsumer(id);
            }
            cs.getRecoveringPullConsumers().remove(id);
        }
        return TRACKED_RESPONSE_MARKER;
    }

    @Override
    public Response processAddSession(SessionInfo info) {
        ConnectionState cs;
        ConnectionId connectionId;
        if (info != null && (connectionId = info.getSessionId().getParentId()) != null && (cs = (ConnectionState)this.connectionStates.get(connectionId)) != null) {
            cs.addSession(info);
        }
        return TRACKED_RESPONSE_MARKER;
    }

    @Override
    public Response processRemoveSession(SessionId id, long lastDeliveredSequenceId) {
        ConnectionState cs;
        ConnectionId connectionId;
        if (id != null && (connectionId = id.getParentId()) != null && (cs = (ConnectionState)this.connectionStates.get(connectionId)) != null) {
            cs.removeSession(id);
        }
        return TRACKED_RESPONSE_MARKER;
    }

    @Override
    public Response processAddConnection(ConnectionInfo info) {
        if (info != null) {
            this.connectionStates.put(info.getConnectionId(), new ConnectionState(info));
        }
        return TRACKED_RESPONSE_MARKER;
    }

    @Override
    public Response processRemoveConnection(ConnectionId id, long lastDeliveredSequenceId) throws Exception {
        if (id != null) {
            this.connectionStates.remove(id);
        }
        return TRACKED_RESPONSE_MARKER;
    }

    @Override
    public Response processMessage(Message send) throws Exception {
        if (send != null) {
            if (this.trackTransactions && send.getTransactionId() != null) {
                TransactionState transactionState;
                ConnectionState cs;
                ProducerId producerId = send.getProducerId();
                ConnectionId connectionId = producerId.getParentId().getParentId();
                if (connectionId != null && (cs = (ConnectionState)this.connectionStates.get(connectionId)) != null && (transactionState = cs.getTransactionState(send.getTransactionId())) != null) {
                    transactionState.addCommand(send);
                    if (this.trackTransactionProducers) {
                        SessionState ss = cs.getSessionState(producerId.getParentId());
                        ProducerState producerState = ss.getProducerState(producerId);
                        producerState.setTransactionState(transactionState);
                    }
                }
                return TRACKED_RESPONSE_MARKER;
            }
            if (this.trackMessages) {
                this.messageCache.put(send.getMessageId(), send);
            }
        }
        return null;
    }

    @Override
    public Response processBeginTransaction(TransactionInfo info) {
        if (this.trackTransactions && info != null && info.getTransactionId() != null) {
            ConnectionState cs;
            ConnectionId connectionId = info.getConnectionId();
            if (connectionId != null && (cs = (ConnectionState)this.connectionStates.get(connectionId)) != null) {
                cs.addTransactionState(info.getTransactionId());
                TransactionState state = cs.getTransactionState(info.getTransactionId());
                state.addCommand(info);
            }
            return TRACKED_RESPONSE_MARKER;
        }
        return null;
    }

    @Override
    public Response processPrepareTransaction(TransactionInfo info) throws Exception {
        TransactionState transactionState;
        ConnectionState cs;
        ConnectionId connectionId;
        if (this.trackTransactions && info != null && info.getTransactionId() != null && (connectionId = info.getConnectionId()) != null && (cs = (ConnectionState)this.connectionStates.get(connectionId)) != null && (transactionState = cs.getTransactionState(info.getTransactionId())) != null) {
            transactionState.addCommand(info);
            return new Tracked(new PrepareReadonlyTransactionAction(info));
        }
        return null;
    }

    @Override
    public Response processCommitTransactionOnePhase(TransactionInfo info) throws Exception {
        TransactionState transactionState;
        ConnectionState cs;
        ConnectionId connectionId;
        if (this.trackTransactions && info != null && info.getTransactionId() != null && (connectionId = info.getConnectionId()) != null && (cs = (ConnectionState)this.connectionStates.get(connectionId)) != null && (transactionState = cs.getTransactionState(info.getTransactionId())) != null) {
            transactionState.addCommand(info);
            return new Tracked(new RemoveTransactionAction(info));
        }
        return null;
    }

    @Override
    public Response processCommitTransactionTwoPhase(TransactionInfo info) throws Exception {
        TransactionState transactionState;
        ConnectionState cs;
        ConnectionId connectionId;
        if (this.trackTransactions && info != null && info.getTransactionId() != null && (connectionId = info.getConnectionId()) != null && (cs = (ConnectionState)this.connectionStates.get(connectionId)) != null && (transactionState = cs.getTransactionState(info.getTransactionId())) != null) {
            transactionState.addCommand(info);
            return new Tracked(new RemoveTransactionAction(info));
        }
        return null;
    }

    @Override
    public Response processRollbackTransaction(TransactionInfo info) throws Exception {
        TransactionState transactionState;
        ConnectionState cs;
        ConnectionId connectionId;
        if (this.trackTransactions && info != null && info.getTransactionId() != null && (connectionId = info.getConnectionId()) != null && (cs = (ConnectionState)this.connectionStates.get(connectionId)) != null && (transactionState = cs.getTransactionState(info.getTransactionId())) != null) {
            transactionState.addCommand(info);
            return new Tracked(new RemoveTransactionAction(info));
        }
        return null;
    }

    @Override
    public Response processEndTransaction(TransactionInfo info) throws Exception {
        if (this.trackTransactions && info != null && info.getTransactionId() != null) {
            TransactionState transactionState;
            ConnectionState cs;
            ConnectionId connectionId = info.getConnectionId();
            if (connectionId != null && (cs = (ConnectionState)this.connectionStates.get(connectionId)) != null && (transactionState = cs.getTransactionState(info.getTransactionId())) != null) {
                transactionState.addCommand(info);
            }
            return TRACKED_RESPONSE_MARKER;
        }
        return null;
    }

    @Override
    public Response processMessagePull(MessagePull pull) throws Exception {
        String id;
        if (pull != null && this.messageCache.put((id = pull.getDestination() + "::" + pull.getConsumerId()).intern(), pull) == null) {
            pull.setTracked(true);
        }
        return null;
    }

    public boolean isRestoreConsumers() {
        return this.restoreConsumers;
    }

    public void setRestoreConsumers(boolean restoreConsumers) {
        this.restoreConsumers = restoreConsumers;
    }

    public boolean isRestoreProducers() {
        return this.restoreProducers;
    }

    public void setRestoreProducers(boolean restoreProducers) {
        this.restoreProducers = restoreProducers;
    }

    public boolean isRestoreSessions() {
        return this.restoreSessions;
    }

    public void setRestoreSessions(boolean restoreSessions) {
        this.restoreSessions = restoreSessions;
    }

    public boolean isTrackTransactions() {
        return this.trackTransactions;
    }

    public void setTrackTransactions(boolean trackTransactions) {
        this.trackTransactions = trackTransactions;
    }

    public boolean isTrackTransactionProducers() {
        return this.trackTransactionProducers;
    }

    public void setTrackTransactionProducers(boolean trackTransactionProducers) {
        this.trackTransactionProducers = trackTransactionProducers;
    }

    public boolean isRestoreTransaction() {
        return this.restoreTransaction;
    }

    public void setRestoreTransaction(boolean restoreTransaction) {
        this.restoreTransaction = restoreTransaction;
    }

    public boolean isTrackMessages() {
        return this.trackMessages;
    }

    public void setTrackMessages(boolean trackMessages) {
        this.trackMessages = trackMessages;
    }

    public int getMaxCacheSize() {
        return this.maxCacheSize;
    }

    public void setMaxCacheSize(int maxCacheSize) {
        this.maxCacheSize = maxCacheSize;
    }

    public long getCurrentCacheSize() {
        return this.currentCacheSize;
    }

    public void connectionInterruptProcessingComplete(Transport transport, ConnectionId connectionId) {
        ConnectionState connectionState = (ConnectionState)this.connectionStates.get(connectionId);
        if (connectionState != null) {
            connectionState.setConnectionInterruptProcessingComplete(true);
            Map<ConsumerId, ConsumerInfo> stalledConsumers = connectionState.getRecoveringPullConsumers();
            for (Map.Entry<ConsumerId, ConsumerInfo> entry : stalledConsumers.entrySet()) {
                ConsumerControl control = new ConsumerControl();
                control.setConsumerId(entry.getKey());
                control.setPrefetch(entry.getValue().getPrefetchSize());
                control.setDestination(entry.getValue().getDestination());
                try {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("restored recovering consumer: " + control.getConsumerId() + " with: " + control.getPrefetch());
                    }
                    transport.oneway(control);
                }
                catch (Exception ex) {
                    if (!LOG.isDebugEnabled()) continue;
                    LOG.debug("Failed to submit control for consumer: " + control.getConsumerId() + " with: " + control.getPrefetch(), ex);
                }
            }
            stalledConsumers.clear();
        }
    }

    public void transportInterrupted(ConnectionId connectionId) {
        ConnectionState connectionState = (ConnectionState)this.connectionStates.get(connectionId);
        if (connectionState != null) {
            connectionState.setConnectionInterruptProcessingComplete(false);
        }
    }

    private class PrepareReadonlyTransactionAction
    extends RemoveTransactionAction {
        public PrepareReadonlyTransactionAction(TransactionInfo info) {
            super(info);
        }

        @Override
        public void onResponse(Command command) {
            IntegerResponse response;
            if (command instanceof IntegerResponse && 3 == (response = (IntegerResponse)command).getResult()) {
                super.onResponse(command);
            }
        }
    }

    private final class ExceptionResponseCheckAction
    implements ResponseHandler {
        private final Command tracked;

        public ExceptionResponseCheckAction(Command tracked) {
            this.tracked = tracked;
        }

        @Override
        public void onResponse(Command response) {
            if (31 == response.getDataStructureType()) {
                if (this.tracked.getDataStructureType() == 5) {
                    ConnectionStateTracker.this.processRemoveConsumer(((ConsumerInfo)this.tracked).getConsumerId(), 0L);
                } else if (this.tracked.getDataStructureType() == 6) {
                    ConnectionStateTracker.this.processRemoveProducer(((ProducerInfo)this.tracked).getProducerId());
                }
            }
        }
    }

    private class RemoveTransactionAction
    implements ResponseHandler {
        private final TransactionInfo info;

        public RemoveTransactionAction(TransactionInfo info) {
            this.info = info;
        }

        @Override
        public void onResponse(Command response) {
            ConnectionId connectionId = this.info.getConnectionId();
            ConnectionState cs = (ConnectionState)ConnectionStateTracker.this.connectionStates.get(connectionId);
            if (cs != null) {
                cs.removeTransactionState(this.info.getTransactionId());
            }
        }
    }
}

