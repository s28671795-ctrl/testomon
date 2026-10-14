/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.advisory;

import java.util.ArrayList;
import javax.jms.Destination;
import javax.jms.JMSException;
import org.apache.activemq.ActiveMQMessageTransformation;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQTopic;

public final class AdvisorySupport {
    public static final String ADVISORY_TOPIC_PREFIX = "ActiveMQ.Advisory.";
    public static final ActiveMQTopic CONNECTION_ADVISORY_TOPIC = new ActiveMQTopic("ActiveMQ.Advisory.Connection");
    public static final ActiveMQTopic QUEUE_ADVISORY_TOPIC = new ActiveMQTopic("ActiveMQ.Advisory.Queue");
    public static final ActiveMQTopic TOPIC_ADVISORY_TOPIC = new ActiveMQTopic("ActiveMQ.Advisory.Topic");
    public static final ActiveMQTopic TEMP_QUEUE_ADVISORY_TOPIC = new ActiveMQTopic("ActiveMQ.Advisory.TempQueue");
    public static final ActiveMQTopic TEMP_TOPIC_ADVISORY_TOPIC = new ActiveMQTopic("ActiveMQ.Advisory.TempTopic");
    public static final String PRODUCER_ADVISORY_TOPIC_PREFIX = "ActiveMQ.Advisory.Producer.";
    public static final String QUEUE_PRODUCER_ADVISORY_TOPIC_PREFIX = "ActiveMQ.Advisory.Producer.Queue.";
    public static final String TOPIC_PRODUCER_ADVISORY_TOPIC_PREFIX = "ActiveMQ.Advisory.Producer.Topic.";
    public static final String ANONYMOUS_PRODUCER_ADVISORY_TOPIC_PREFIX = "ActiveMQ.Advisory.Producer.Anonymous";
    public static final String CONSUMER_ADVISORY_TOPIC_PREFIX = "ActiveMQ.Advisory.Consumer.";
    public static final String VIRTUAL_DESTINATION_CONSUMER_ADVISORY_TOPIC_PREFIX = "ActiveMQ.Advisory.VirtualDestination.Consumer.";
    public static final String QUEUE_CONSUMER_ADVISORY_TOPIC_PREFIX = "ActiveMQ.Advisory.Consumer.Queue.";
    public static final String TOPIC_CONSUMER_ADVISORY_TOPIC_PREFIX = "ActiveMQ.Advisory.Consumer.Topic.";
    public static final String QUEUE_VIRTUAL_DESTINATION_CONSUMER_ADVISORY_TOPIC_PREFIX = "ActiveMQ.Advisory.VirtualDestination.Consumer.Queue.";
    public static final String TOPIC_VIRTUAL_DESTINATION_CONSUMER_ADVISORY_TOPIC_PREFIX = "ActiveMQ.Advisory.VirtualDestination.Consumer.Topic.";
    public static final String EXPIRED_TOPIC_MESSAGES_TOPIC_PREFIX = "ActiveMQ.Advisory.Expired.Topic.";
    public static final String EXPIRED_QUEUE_MESSAGES_TOPIC_PREFIX = "ActiveMQ.Advisory.Expired.Queue.";
    public static final String NO_TOPIC_CONSUMERS_TOPIC_PREFIX = "ActiveMQ.Advisory.NoConsumer.Topic.";
    public static final String NO_QUEUE_CONSUMERS_TOPIC_PREFIX = "ActiveMQ.Advisory.NoConsumer.Queue.";
    public static final String SLOW_CONSUMER_TOPIC_PREFIX = "ActiveMQ.Advisory.SlowConsumer.";
    public static final String FAST_PRODUCER_TOPIC_PREFIX = "ActiveMQ.Advisory.FastProducer.";
    public static final String MESSAGE_DISCAREDED_TOPIC_PREFIX = "ActiveMQ.Advisory.MessageDiscarded.";
    public static final String FULL_TOPIC_PREFIX = "ActiveMQ.Advisory.FULL.";
    public static final String MESSAGE_DELIVERED_TOPIC_PREFIX = "ActiveMQ.Advisory.MessageDelivered.";
    public static final String MESSAGE_CONSUMED_TOPIC_PREFIX = "ActiveMQ.Advisory.MessageConsumed.";
    public static final String MESSAGE_DLQ_TOPIC_PREFIX = "ActiveMQ.Advisory.MessageDLQd.";
    public static final String MASTER_BROKER_TOPIC_PREFIX = "ActiveMQ.Advisory.MasterBroker";
    public static final String NETWORK_BRIDGE_TOPIC_PREFIX = "ActiveMQ.Advisory.NetworkBridge";
    public static final String NETWORK_BRIDGE_FORWARD_FAILURE_TOPIC_PREFIX = "ActiveMQ.Advisory.NetworkBridge.ForwardFailure";
    public static final String AGENT_TOPIC = "ActiveMQ.Agent";
    public static final String ADIVSORY_MESSAGE_TYPE = "Advisory";
    public static final String MSG_PROPERTY_ORIGIN_BROKER_ID = "originBrokerId";
    public static final String MSG_PROPERTY_ORIGIN_BROKER_NAME = "originBrokerName";
    public static final String MSG_PROPERTY_ORIGIN_BROKER_URL = "originBrokerURL";
    public static final String MSG_PROPERTY_USAGE_NAME = "usageName";
    public static final String MSG_PROPERTY_USAGE_COUNT = "usageCount";
    public static final String MSG_PROPERTY_CONSUMER_ID = "consumerId";
    public static final String MSG_PROPERTY_PRODUCER_ID = "producerId";
    public static final String MSG_PROPERTY_MESSAGE_ID = "orignalMessageId";
    public static final String MSG_PROPERTY_DESTINATION = "orignalDestination";
    public static final String MSG_PROPERTY_CONSUMER_COUNT = "consumerCount";
    public static final String MSG_PROPERTY_DISCARDED_COUNT = "discardedCount";
    public static final ActiveMQTopic ALL_DESTINATIONS_COMPOSITE_ADVISORY_TOPIC = new ActiveMQTopic(TOPIC_ADVISORY_TOPIC.getPhysicalName() + "," + QUEUE_ADVISORY_TOPIC.getPhysicalName() + "," + TEMP_QUEUE_ADVISORY_TOPIC.getPhysicalName() + "," + TEMP_TOPIC_ADVISORY_TOPIC.getPhysicalName());
    public static final ActiveMQTopic TEMP_DESTINATION_COMPOSITE_ADVISORY_TOPIC = new ActiveMQTopic(TEMP_QUEUE_ADVISORY_TOPIC.getPhysicalName() + "," + TEMP_TOPIC_ADVISORY_TOPIC.getPhysicalName());
    private static final ActiveMQTopic AGENT_TOPIC_DESTINATION = new ActiveMQTopic("ActiveMQ.Agent");

    private AdvisorySupport() {
    }

    public static ActiveMQTopic getConnectionAdvisoryTopic() {
        return CONNECTION_ADVISORY_TOPIC;
    }

    public static ActiveMQTopic[] getAllDestinationAdvisoryTopics(Destination destination) throws JMSException {
        return AdvisorySupport.getAllDestinationAdvisoryTopics(ActiveMQMessageTransformation.transformDestination(destination));
    }

    public static ActiveMQTopic[] getAllDestinationAdvisoryTopics(ActiveMQDestination destination) throws JMSException {
        ArrayList<ActiveMQTopic> result = new ArrayList<ActiveMQTopic>();
        result.add(AdvisorySupport.getConsumerAdvisoryTopic(destination));
        result.add(AdvisorySupport.getProducerAdvisoryTopic(destination));
        result.add(AdvisorySupport.getExpiredMessageTopic(destination));
        result.add(AdvisorySupport.getNoConsumersAdvisoryTopic(destination));
        result.add(AdvisorySupport.getSlowConsumerAdvisoryTopic(destination));
        result.add(AdvisorySupport.getFastProducerAdvisoryTopic(destination));
        result.add(AdvisorySupport.getMessageDiscardedAdvisoryTopic(destination));
        result.add(AdvisorySupport.getMessageDeliveredAdvisoryTopic(destination));
        result.add(AdvisorySupport.getMessageConsumedAdvisoryTopic(destination));
        result.add(AdvisorySupport.getMessageDLQdAdvisoryTopic(destination));
        result.add(AdvisorySupport.getFullAdvisoryTopic(destination));
        return result.toArray(new ActiveMQTopic[0]);
    }

    public static ActiveMQTopic getConsumerAdvisoryTopic(Destination destination) throws JMSException {
        return AdvisorySupport.getConsumerAdvisoryTopic(ActiveMQMessageTransformation.transformDestination(destination));
    }

    public static ActiveMQTopic getConsumerAdvisoryTopic(ActiveMQDestination destination) {
        String prefix = destination.isQueue() ? QUEUE_CONSUMER_ADVISORY_TOPIC_PREFIX : TOPIC_CONSUMER_ADVISORY_TOPIC_PREFIX;
        return AdvisorySupport.getAdvisoryTopic(destination, prefix, true);
    }

    public static ActiveMQTopic getVirtualDestinationConsumerAdvisoryTopic(ActiveMQDestination destination) {
        String prefix = destination.isQueue() ? QUEUE_VIRTUAL_DESTINATION_CONSUMER_ADVISORY_TOPIC_PREFIX : TOPIC_VIRTUAL_DESTINATION_CONSUMER_ADVISORY_TOPIC_PREFIX;
        return AdvisorySupport.getAdvisoryTopic(destination, prefix, true);
    }

    public static ActiveMQTopic getProducerAdvisoryTopic(Destination destination) throws JMSException {
        return AdvisorySupport.getProducerAdvisoryTopic(ActiveMQMessageTransformation.transformDestination(destination));
    }

    public static ActiveMQTopic getProducerAdvisoryTopic(ActiveMQDestination destination) {
        String prefix = destination == null ? ANONYMOUS_PRODUCER_ADVISORY_TOPIC_PREFIX : (destination.isQueue() ? QUEUE_PRODUCER_ADVISORY_TOPIC_PREFIX : TOPIC_PRODUCER_ADVISORY_TOPIC_PREFIX);
        return AdvisorySupport.getAdvisoryTopic(destination, prefix, false);
    }

    private static ActiveMQTopic getAdvisoryTopic(ActiveMQDestination destination, String prefix, boolean consumerTopics) {
        return destination != null ? new ActiveMQTopic(prefix + destination.getPhysicalName().replaceAll(",", "&sbquo;")) : new ActiveMQTopic(prefix);
    }

    public static ActiveMQTopic getExpiredMessageTopic(Destination destination) throws JMSException {
        return AdvisorySupport.getExpiredMessageTopic(ActiveMQMessageTransformation.transformDestination(destination));
    }

    public static ActiveMQTopic getExpiredMessageTopic(ActiveMQDestination destination) {
        if (destination.isQueue()) {
            return AdvisorySupport.getExpiredQueueMessageAdvisoryTopic(destination);
        }
        return AdvisorySupport.getExpiredTopicMessageAdvisoryTopic(destination);
    }

    public static ActiveMQTopic getExpiredTopicMessageAdvisoryTopic(ActiveMQDestination destination) {
        String name = EXPIRED_TOPIC_MESSAGES_TOPIC_PREFIX + destination.getPhysicalName();
        return new ActiveMQTopic(name);
    }

    public static ActiveMQTopic getExpiredQueueMessageAdvisoryTopic(Destination destination) throws JMSException {
        return AdvisorySupport.getExpiredQueueMessageAdvisoryTopic(ActiveMQMessageTransformation.transformDestination(destination));
    }

    public static ActiveMQTopic getExpiredQueueMessageAdvisoryTopic(ActiveMQDestination destination) {
        String name = EXPIRED_QUEUE_MESSAGES_TOPIC_PREFIX + destination.getPhysicalName();
        return new ActiveMQTopic(name);
    }

    public static ActiveMQTopic getNoConsumersAdvisoryTopic(Destination destination) throws JMSException {
        return AdvisorySupport.getExpiredMessageTopic(ActiveMQMessageTransformation.transformDestination(destination));
    }

    public static ActiveMQTopic getNoConsumersAdvisoryTopic(ActiveMQDestination destination) {
        if (destination.isQueue()) {
            return AdvisorySupport.getNoQueueConsumersAdvisoryTopic(destination);
        }
        return AdvisorySupport.getNoTopicConsumersAdvisoryTopic(destination);
    }

    public static ActiveMQTopic getNoTopicConsumersAdvisoryTopic(Destination destination) throws JMSException {
        return AdvisorySupport.getNoTopicConsumersAdvisoryTopic(ActiveMQMessageTransformation.transformDestination(destination));
    }

    public static ActiveMQTopic getNoTopicConsumersAdvisoryTopic(ActiveMQDestination destination) {
        String name = NO_TOPIC_CONSUMERS_TOPIC_PREFIX + destination.getPhysicalName();
        return new ActiveMQTopic(name);
    }

    public static ActiveMQTopic getNoQueueConsumersAdvisoryTopic(Destination destination) throws JMSException {
        return AdvisorySupport.getNoQueueConsumersAdvisoryTopic(ActiveMQMessageTransformation.transformDestination(destination));
    }

    public static ActiveMQTopic getNoQueueConsumersAdvisoryTopic(ActiveMQDestination destination) {
        String name = NO_QUEUE_CONSUMERS_TOPIC_PREFIX + destination.getPhysicalName();
        return new ActiveMQTopic(name);
    }

    public static ActiveMQTopic getSlowConsumerAdvisoryTopic(Destination destination) throws JMSException {
        return AdvisorySupport.getSlowConsumerAdvisoryTopic(ActiveMQMessageTransformation.transformDestination(destination));
    }

    public static ActiveMQTopic getSlowConsumerAdvisoryTopic(ActiveMQDestination destination) {
        String name = SLOW_CONSUMER_TOPIC_PREFIX + destination.getDestinationTypeAsString() + "." + destination.getPhysicalName();
        return new ActiveMQTopic(name);
    }

    public static ActiveMQTopic getFastProducerAdvisoryTopic(Destination destination) throws JMSException {
        return AdvisorySupport.getFastProducerAdvisoryTopic(ActiveMQMessageTransformation.transformDestination(destination));
    }

    public static ActiveMQTopic getFastProducerAdvisoryTopic(ActiveMQDestination destination) {
        String name = FAST_PRODUCER_TOPIC_PREFIX + destination.getDestinationTypeAsString() + "." + destination.getPhysicalName();
        return new ActiveMQTopic(name);
    }

    public static ActiveMQTopic getMessageDiscardedAdvisoryTopic(Destination destination) throws JMSException {
        return AdvisorySupport.getMessageDiscardedAdvisoryTopic(ActiveMQMessageTransformation.transformDestination(destination));
    }

    public static ActiveMQTopic getMessageDiscardedAdvisoryTopic(ActiveMQDestination destination) {
        String name = MESSAGE_DISCAREDED_TOPIC_PREFIX + destination.getDestinationTypeAsString() + "." + destination.getPhysicalName();
        return new ActiveMQTopic(name);
    }

    public static ActiveMQTopic getMessageDeliveredAdvisoryTopic(Destination destination) throws JMSException {
        return AdvisorySupport.getMessageDeliveredAdvisoryTopic(ActiveMQMessageTransformation.transformDestination(destination));
    }

    public static ActiveMQTopic getMessageDeliveredAdvisoryTopic(ActiveMQDestination destination) {
        String name = MESSAGE_DELIVERED_TOPIC_PREFIX + destination.getDestinationTypeAsString() + "." + destination.getPhysicalName();
        return new ActiveMQTopic(name);
    }

    public static ActiveMQTopic getMessageConsumedAdvisoryTopic(Destination destination) throws JMSException {
        return AdvisorySupport.getMessageConsumedAdvisoryTopic(ActiveMQMessageTransformation.transformDestination(destination));
    }

    public static ActiveMQTopic getMessageConsumedAdvisoryTopic(ActiveMQDestination destination) {
        String name = MESSAGE_CONSUMED_TOPIC_PREFIX + destination.getDestinationTypeAsString() + "." + destination.getPhysicalName();
        return new ActiveMQTopic(name);
    }

    public static ActiveMQTopic getMessageDLQdAdvisoryTopic(ActiveMQDestination destination) {
        String name = MESSAGE_DLQ_TOPIC_PREFIX + destination.getDestinationTypeAsString() + "." + destination.getPhysicalName();
        return new ActiveMQTopic(name);
    }

    public static ActiveMQTopic getMasterBrokerAdvisoryTopic() {
        return new ActiveMQTopic(MASTER_BROKER_TOPIC_PREFIX);
    }

    public static ActiveMQTopic getNetworkBridgeAdvisoryTopic() {
        return new ActiveMQTopic(NETWORK_BRIDGE_TOPIC_PREFIX);
    }

    public static ActiveMQTopic getFullAdvisoryTopic(Destination destination) throws JMSException {
        return AdvisorySupport.getFullAdvisoryTopic(ActiveMQMessageTransformation.transformDestination(destination));
    }

    public static ActiveMQTopic getFullAdvisoryTopic(ActiveMQDestination destination) {
        String name = FULL_TOPIC_PREFIX + destination.getDestinationTypeAsString() + "." + destination.getPhysicalName();
        return new ActiveMQTopic(name);
    }

    public static ActiveMQTopic getDestinationAdvisoryTopic(Destination destination) throws JMSException {
        return AdvisorySupport.getDestinationAdvisoryTopic(ActiveMQMessageTransformation.transformDestination(destination));
    }

    public static ActiveMQTopic getDestinationAdvisoryTopic(ActiveMQDestination destination) {
        switch (destination.getDestinationType()) {
            case 1: {
                return QUEUE_ADVISORY_TOPIC;
            }
            case 2: {
                return TOPIC_ADVISORY_TOPIC;
            }
            case 5: {
                return TEMP_QUEUE_ADVISORY_TOPIC;
            }
            case 6: {
                return TEMP_TOPIC_ADVISORY_TOPIC;
            }
        }
        throw new RuntimeException("Unknown destination type: " + destination.getDestinationType());
    }

    public static boolean isDestinationAdvisoryTopic(Destination destination) throws JMSException {
        return AdvisorySupport.isDestinationAdvisoryTopic(ActiveMQMessageTransformation.transformDestination(destination));
    }

    public static boolean isTempDestinationAdvisoryTopic(ActiveMQDestination destination) {
        if (destination.isComposite()) {
            ActiveMQDestination[] compositeDestinations = destination.getCompositeDestinations();
            for (int i = 0; i < compositeDestinations.length; ++i) {
                if (AdvisorySupport.isTempDestinationAdvisoryTopic(compositeDestinations[i])) continue;
                return false;
            }
            return true;
        }
        return destination.equals(TEMP_QUEUE_ADVISORY_TOPIC) || destination.equals(TEMP_TOPIC_ADVISORY_TOPIC);
    }

    public static boolean isDestinationAdvisoryTopic(ActiveMQDestination destination) {
        if (destination.isComposite()) {
            ActiveMQDestination[] compositeDestinations = destination.getCompositeDestinations();
            for (int i = 0; i < compositeDestinations.length; ++i) {
                if (!AdvisorySupport.isDestinationAdvisoryTopic(compositeDestinations[i])) continue;
                return true;
            }
            return false;
        }
        return destination.equals(TEMP_QUEUE_ADVISORY_TOPIC) || destination.equals(TEMP_TOPIC_ADVISORY_TOPIC) || destination.equals(QUEUE_ADVISORY_TOPIC) || destination.equals(TOPIC_ADVISORY_TOPIC);
    }

    public static boolean isAdvisoryTopic(Destination destination) throws JMSException {
        return AdvisorySupport.isAdvisoryTopic(ActiveMQMessageTransformation.transformDestination(destination));
    }

    public static boolean isAdvisoryTopic(ActiveMQDestination destination) {
        if (destination != null) {
            if (destination.isComposite()) {
                ActiveMQDestination[] compositeDestinations = destination.getCompositeDestinations();
                for (int i = 0; i < compositeDestinations.length; ++i) {
                    if (!AdvisorySupport.isAdvisoryTopic(compositeDestinations[i])) continue;
                    return true;
                }
                return false;
            }
            return destination.isTopic() && destination.getPhysicalName().startsWith(ADVISORY_TOPIC_PREFIX);
        }
        return false;
    }

    public static boolean isConnectionAdvisoryTopic(Destination destination) throws JMSException {
        return AdvisorySupport.isConnectionAdvisoryTopic(ActiveMQMessageTransformation.transformDestination(destination));
    }

    public static boolean isConnectionAdvisoryTopic(ActiveMQDestination destination) {
        if (destination.isComposite()) {
            ActiveMQDestination[] compositeDestinations = destination.getCompositeDestinations();
            for (int i = 0; i < compositeDestinations.length; ++i) {
                if (!AdvisorySupport.isConnectionAdvisoryTopic(compositeDestinations[i])) continue;
                return true;
            }
            return false;
        }
        return destination.equals(CONNECTION_ADVISORY_TOPIC);
    }

    public static boolean isProducerAdvisoryTopic(Destination destination) throws JMSException {
        return AdvisorySupport.isProducerAdvisoryTopic(ActiveMQMessageTransformation.transformDestination(destination));
    }

    public static boolean isProducerAdvisoryTopic(ActiveMQDestination destination) {
        if (destination.isComposite()) {
            ActiveMQDestination[] compositeDestinations = destination.getCompositeDestinations();
            for (int i = 0; i < compositeDestinations.length; ++i) {
                if (!AdvisorySupport.isProducerAdvisoryTopic(compositeDestinations[i])) continue;
                return true;
            }
            return false;
        }
        return destination.isTopic() && destination.getPhysicalName().startsWith(PRODUCER_ADVISORY_TOPIC_PREFIX);
    }

    public static boolean isConsumerAdvisoryTopic(Destination destination) throws JMSException {
        return AdvisorySupport.isConsumerAdvisoryTopic(ActiveMQMessageTransformation.transformDestination(destination));
    }

    public static boolean isConsumerAdvisoryTopic(ActiveMQDestination destination) {
        if (destination.isComposite()) {
            ActiveMQDestination[] compositeDestinations = destination.getCompositeDestinations();
            for (int i = 0; i < compositeDestinations.length; ++i) {
                if (!AdvisorySupport.isConsumerAdvisoryTopic(compositeDestinations[i])) continue;
                return true;
            }
            return false;
        }
        return destination.isTopic() && destination.getPhysicalName().startsWith(CONSUMER_ADVISORY_TOPIC_PREFIX);
    }

    public static boolean isVirtualDestinationConsumerAdvisoryTopic(Destination destination) throws JMSException {
        return AdvisorySupport.isVirtualDestinationConsumerAdvisoryTopic(ActiveMQMessageTransformation.transformDestination(destination));
    }

    public static boolean isVirtualDestinationConsumerAdvisoryTopic(ActiveMQDestination destination) {
        if (destination.isComposite()) {
            ActiveMQDestination[] compositeDestinations = destination.getCompositeDestinations();
            for (int i = 0; i < compositeDestinations.length; ++i) {
                if (!AdvisorySupport.isVirtualDestinationConsumerAdvisoryTopic(compositeDestinations[i])) continue;
                return true;
            }
            return false;
        }
        return destination.isTopic() && destination.getPhysicalName().startsWith(VIRTUAL_DESTINATION_CONSUMER_ADVISORY_TOPIC_PREFIX);
    }

    public static boolean isSlowConsumerAdvisoryTopic(Destination destination) throws JMSException {
        return AdvisorySupport.isSlowConsumerAdvisoryTopic(ActiveMQMessageTransformation.transformDestination(destination));
    }

    public static boolean isSlowConsumerAdvisoryTopic(ActiveMQDestination destination) {
        if (destination.isComposite()) {
            ActiveMQDestination[] compositeDestinations = destination.getCompositeDestinations();
            for (int i = 0; i < compositeDestinations.length; ++i) {
                if (!AdvisorySupport.isSlowConsumerAdvisoryTopic(compositeDestinations[i])) continue;
                return true;
            }
            return false;
        }
        return destination.isTopic() && destination.getPhysicalName().startsWith(SLOW_CONSUMER_TOPIC_PREFIX);
    }

    public static boolean isFastProducerAdvisoryTopic(Destination destination) throws JMSException {
        return AdvisorySupport.isFastProducerAdvisoryTopic(ActiveMQMessageTransformation.transformDestination(destination));
    }

    public static boolean isFastProducerAdvisoryTopic(ActiveMQDestination destination) {
        if (destination.isComposite()) {
            ActiveMQDestination[] compositeDestinations = destination.getCompositeDestinations();
            for (int i = 0; i < compositeDestinations.length; ++i) {
                if (!AdvisorySupport.isFastProducerAdvisoryTopic(compositeDestinations[i])) continue;
                return true;
            }
            return false;
        }
        return destination.isTopic() && destination.getPhysicalName().startsWith(FAST_PRODUCER_TOPIC_PREFIX);
    }

    public static boolean isMessageConsumedAdvisoryTopic(Destination destination) throws JMSException {
        return AdvisorySupport.isMessageConsumedAdvisoryTopic(ActiveMQMessageTransformation.transformDestination(destination));
    }

    public static boolean isMessageConsumedAdvisoryTopic(ActiveMQDestination destination) {
        if (destination.isComposite()) {
            ActiveMQDestination[] compositeDestinations = destination.getCompositeDestinations();
            for (int i = 0; i < compositeDestinations.length; ++i) {
                if (!AdvisorySupport.isMessageConsumedAdvisoryTopic(compositeDestinations[i])) continue;
                return true;
            }
            return false;
        }
        return destination.isTopic() && destination.getPhysicalName().startsWith(MESSAGE_CONSUMED_TOPIC_PREFIX);
    }

    public static boolean isMasterBrokerAdvisoryTopic(Destination destination) throws JMSException {
        return AdvisorySupport.isMasterBrokerAdvisoryTopic(ActiveMQMessageTransformation.transformDestination(destination));
    }

    public static boolean isMasterBrokerAdvisoryTopic(ActiveMQDestination destination) {
        if (destination.isComposite()) {
            ActiveMQDestination[] compositeDestinations = destination.getCompositeDestinations();
            for (int i = 0; i < compositeDestinations.length; ++i) {
                if (!AdvisorySupport.isMasterBrokerAdvisoryTopic(compositeDestinations[i])) continue;
                return true;
            }
            return false;
        }
        return destination.isTopic() && destination.getPhysicalName().startsWith(MASTER_BROKER_TOPIC_PREFIX);
    }

    public static boolean isMessageDeliveredAdvisoryTopic(Destination destination) throws JMSException {
        return AdvisorySupport.isMessageDeliveredAdvisoryTopic(ActiveMQMessageTransformation.transformDestination(destination));
    }

    public static boolean isMessageDeliveredAdvisoryTopic(ActiveMQDestination destination) {
        if (destination.isComposite()) {
            ActiveMQDestination[] compositeDestinations = destination.getCompositeDestinations();
            for (int i = 0; i < compositeDestinations.length; ++i) {
                if (!AdvisorySupport.isMessageDeliveredAdvisoryTopic(compositeDestinations[i])) continue;
                return true;
            }
            return false;
        }
        return destination.isTopic() && destination.getPhysicalName().startsWith(MESSAGE_DELIVERED_TOPIC_PREFIX);
    }

    public static boolean isMessageDiscardedAdvisoryTopic(Destination destination) throws JMSException {
        return AdvisorySupport.isMessageDiscardedAdvisoryTopic(ActiveMQMessageTransformation.transformDestination(destination));
    }

    public static boolean isMessageDiscardedAdvisoryTopic(ActiveMQDestination destination) {
        if (destination.isComposite()) {
            ActiveMQDestination[] compositeDestinations = destination.getCompositeDestinations();
            for (int i = 0; i < compositeDestinations.length; ++i) {
                if (!AdvisorySupport.isMessageDiscardedAdvisoryTopic(compositeDestinations[i])) continue;
                return true;
            }
            return false;
        }
        return destination.isTopic() && destination.getPhysicalName().startsWith(MESSAGE_DISCAREDED_TOPIC_PREFIX);
    }

    public static boolean isFullAdvisoryTopic(Destination destination) throws JMSException {
        return AdvisorySupport.isFullAdvisoryTopic(ActiveMQMessageTransformation.transformDestination(destination));
    }

    public static boolean isFullAdvisoryTopic(ActiveMQDestination destination) {
        if (destination.isComposite()) {
            ActiveMQDestination[] compositeDestinations = destination.getCompositeDestinations();
            for (int i = 0; i < compositeDestinations.length; ++i) {
                if (!AdvisorySupport.isFullAdvisoryTopic(compositeDestinations[i])) continue;
                return true;
            }
            return false;
        }
        return destination.isTopic() && destination.getPhysicalName().startsWith(FULL_TOPIC_PREFIX);
    }

    public static boolean isNetworkBridgeAdvisoryTopic(Destination destination) throws JMSException {
        return AdvisorySupport.isNetworkBridgeAdvisoryTopic(ActiveMQMessageTransformation.transformDestination(destination));
    }

    public static boolean isNetworkBridgeAdvisoryTopic(ActiveMQDestination destination) {
        if (destination.isComposite()) {
            ActiveMQDestination[] compositeDestinations = destination.getCompositeDestinations();
            for (int i = 0; i < compositeDestinations.length; ++i) {
                if (!AdvisorySupport.isNetworkBridgeAdvisoryTopic(compositeDestinations[i])) continue;
                return true;
            }
            return false;
        }
        return destination.isTopic() && destination.getPhysicalName().startsWith(NETWORK_BRIDGE_TOPIC_PREFIX);
    }

    public static Destination getAgentDestination() {
        return AGENT_TOPIC_DESTINATION;
    }

    public static ActiveMQTopic getNetworkBridgeForwardFailureAdvisoryTopic() {
        return new ActiveMQTopic(NETWORK_BRIDGE_FORWARD_FAILURE_TOPIC_PREFIX);
    }
}

