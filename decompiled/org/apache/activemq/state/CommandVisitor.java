/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.state;

import org.apache.activemq.command.BrokerInfo;
import org.apache.activemq.command.BrokerSubscriptionInfo;
import org.apache.activemq.command.ConnectionControl;
import org.apache.activemq.command.ConnectionError;
import org.apache.activemq.command.ConnectionId;
import org.apache.activemq.command.ConnectionInfo;
import org.apache.activemq.command.ConsumerControl;
import org.apache.activemq.command.ConsumerId;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.ControlCommand;
import org.apache.activemq.command.DestinationInfo;
import org.apache.activemq.command.FlushCommand;
import org.apache.activemq.command.KeepAliveInfo;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.MessageAck;
import org.apache.activemq.command.MessageDispatch;
import org.apache.activemq.command.MessageDispatchNotification;
import org.apache.activemq.command.MessagePull;
import org.apache.activemq.command.ProducerAck;
import org.apache.activemq.command.ProducerId;
import org.apache.activemq.command.ProducerInfo;
import org.apache.activemq.command.RemoveSubscriptionInfo;
import org.apache.activemq.command.Response;
import org.apache.activemq.command.SessionId;
import org.apache.activemq.command.SessionInfo;
import org.apache.activemq.command.ShutdownInfo;
import org.apache.activemq.command.TransactionInfo;
import org.apache.activemq.command.WireFormatInfo;

public interface CommandVisitor {
    public Response processAddConnection(ConnectionInfo var1) throws Exception;

    public Response processAddSession(SessionInfo var1) throws Exception;

    public Response processAddProducer(ProducerInfo var1) throws Exception;

    public Response processAddConsumer(ConsumerInfo var1) throws Exception;

    public Response processRemoveConnection(ConnectionId var1, long var2) throws Exception;

    public Response processRemoveSession(SessionId var1, long var2) throws Exception;

    public Response processRemoveProducer(ProducerId var1) throws Exception;

    public Response processRemoveConsumer(ConsumerId var1, long var2) throws Exception;

    public Response processAddDestination(DestinationInfo var1) throws Exception;

    public Response processRemoveDestination(DestinationInfo var1) throws Exception;

    public Response processRemoveSubscription(RemoveSubscriptionInfo var1) throws Exception;

    public Response processMessage(Message var1) throws Exception;

    public Response processMessageAck(MessageAck var1) throws Exception;

    public Response processMessagePull(MessagePull var1) throws Exception;

    public Response processBeginTransaction(TransactionInfo var1) throws Exception;

    public Response processPrepareTransaction(TransactionInfo var1) throws Exception;

    public Response processCommitTransactionOnePhase(TransactionInfo var1) throws Exception;

    public Response processCommitTransactionTwoPhase(TransactionInfo var1) throws Exception;

    public Response processRollbackTransaction(TransactionInfo var1) throws Exception;

    public Response processWireFormat(WireFormatInfo var1) throws Exception;

    public Response processKeepAlive(KeepAliveInfo var1) throws Exception;

    public Response processShutdown(ShutdownInfo var1) throws Exception;

    public Response processFlush(FlushCommand var1) throws Exception;

    public Response processBrokerInfo(BrokerInfo var1) throws Exception;

    public Response processBrokerSubscriptionInfo(BrokerSubscriptionInfo var1) throws Exception;

    public Response processRecoverTransactions(TransactionInfo var1) throws Exception;

    public Response processForgetTransaction(TransactionInfo var1) throws Exception;

    public Response processEndTransaction(TransactionInfo var1) throws Exception;

    public Response processMessageDispatchNotification(MessageDispatchNotification var1) throws Exception;

    public Response processProducerAck(ProducerAck var1) throws Exception;

    public Response processMessageDispatch(MessageDispatch var1) throws Exception;

    public Response processControlCommand(ControlCommand var1) throws Exception;

    public Response processConnectionError(ConnectionError var1) throws Exception;

    public Response processConnectionControl(ConnectionControl var1) throws Exception;

    public Response processConsumerControl(ConsumerControl var1) throws Exception;
}

