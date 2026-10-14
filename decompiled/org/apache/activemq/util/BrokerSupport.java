/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.util;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.ProducerBrokerExchange;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.ProducerInfo;
import org.apache.activemq.security.SecurityContext;
import org.apache.activemq.state.ProducerState;

public final class BrokerSupport {
    private BrokerSupport() {
    }

    public static void resendNoCopy(ConnectionContext context, Message originalMessage, ActiveMQDestination deadLetterDestination) throws Exception {
        BrokerSupport.doResend(context, originalMessage, deadLetterDestination, false);
    }

    public static void resend(ConnectionContext context, Message originalMessage, ActiveMQDestination deadLetterDestination) throws Exception {
        BrokerSupport.doResend(context, originalMessage, deadLetterDestination, true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void doResend(ConnectionContext context, Message originalMessage, ActiveMQDestination deadLetterDestination, boolean copy) throws Exception {
        Message message = copy ? originalMessage.copy() : originalMessage;
        message.setOriginalDestination(message.getDestination());
        message.setOriginalTransactionId(message.getTransactionId());
        message.setDestination(deadLetterDestination);
        message.setTransactionId(null);
        message.setMemoryUsage(null);
        message.setRedeliveryCounter(0);
        message.getMessageId().setDataLocator(null);
        boolean originalFlowControl = context.isProducerFlowControl();
        try {
            context.setProducerFlowControl(false);
            ProducerInfo info = new ProducerInfo();
            ProducerState state = new ProducerState(info);
            ProducerBrokerExchange producerExchange = new ProducerBrokerExchange();
            producerExchange.setProducerState(state);
            producerExchange.setMutable(true);
            producerExchange.setConnectionContext(context);
            context.getBroker().send(producerExchange, message);
        }
        finally {
            context.setProducerFlowControl(originalFlowControl);
        }
    }

    public static ConnectionContext getConnectionContext(Broker broker) {
        ConnectionContext adminConnectionContext = broker.getAdminConnectionContext();
        if (adminConnectionContext == null) {
            adminConnectionContext = BrokerSupport.createAdminConnectionContext(broker);
            broker.setAdminConnectionContext(adminConnectionContext);
        }
        return adminConnectionContext;
    }

    protected static ConnectionContext createAdminConnectionContext(Broker broker) {
        ConnectionContext context = new ConnectionContext();
        context.setBroker(broker);
        context.setSecurityContext(SecurityContext.BROKER_SECURITY_CONTEXT);
        return context;
    }
}

