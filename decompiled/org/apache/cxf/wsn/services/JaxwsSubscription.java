/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsn.services;

import javax.jws.WebService;
import org.apache.cxf.wsn.jms.JmsSubscription;
import org.apache.cxf.wsn.util.WSNHelper;
import org.oasis_open.docs.wsn.b_2.Notify;
import org.oasis_open.docs.wsn.b_2.Subscribe;
import org.oasis_open.docs.wsn.b_2.SubscribeCreationFailedFaultType;
import org.oasis_open.docs.wsn.bw_2.InvalidFilterFault;
import org.oasis_open.docs.wsn.bw_2.InvalidMessageContentExpressionFault;
import org.oasis_open.docs.wsn.bw_2.InvalidProducerPropertiesExpressionFault;
import org.oasis_open.docs.wsn.bw_2.InvalidTopicExpressionFault;
import org.oasis_open.docs.wsn.bw_2.NotificationConsumer;
import org.oasis_open.docs.wsn.bw_2.SubscribeCreationFailedFault;
import org.oasis_open.docs.wsn.bw_2.TopicExpressionDialectUnknownFault;
import org.oasis_open.docs.wsn.bw_2.TopicNotSupportedFault;
import org.oasis_open.docs.wsn.bw_2.UnacceptableInitialTerminationTimeFault;
import org.oasis_open.docs.wsn.bw_2.UnrecognizedPolicyRequestFault;
import org.oasis_open.docs.wsn.bw_2.UnsupportedPolicyRequestFault;

@WebService(endpointInterface="org.oasis_open.docs.wsn.bw_2.PausableSubscriptionManager", targetNamespace="http://cxf.apache.org/wsn/jaxws", serviceName="PausableSubscriptionManagerService", portName="PausableSubscriptionManagerPort")
public class JaxwsSubscription
extends JmsSubscription {
    private NotificationConsumer consumer;

    public JaxwsSubscription(String name) {
        super(name);
    }

    @Override
    protected void validateSubscription(Subscribe subscribeRequest) throws InvalidFilterFault, InvalidMessageContentExpressionFault, InvalidProducerPropertiesExpressionFault, InvalidTopicExpressionFault, SubscribeCreationFailedFault, TopicExpressionDialectUnknownFault, TopicNotSupportedFault, UnacceptableInitialTerminationTimeFault, UnsupportedPolicyRequestFault, UnrecognizedPolicyRequestFault {
        super.validateSubscription(subscribeRequest);
        if (this.useRaw) {
            SubscribeCreationFailedFaultType fault = new SubscribeCreationFailedFaultType();
            throw new SubscribeCreationFailedFault("Raw notifications are not supported", fault);
        }
        try {
            this.consumer = WSNHelper.getInstance().getPort(subscribeRequest.getConsumerReference(), NotificationConsumer.class, new Class[0]);
        }
        catch (Exception e) {
            SubscribeCreationFailedFaultType fault = new SubscribeCreationFailedFaultType();
            throw new SubscribeCreationFailedFault("Unable to resolve consumer reference endpoint", fault, e);
        }
    }

    @Override
    protected void doNotify(Notify notify) {
        this.consumer.notify(notify);
    }
}

