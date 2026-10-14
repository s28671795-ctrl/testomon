/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.openwire.v7;

import org.apache.activemq.openwire.DataStreamMarshaller;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.openwire.v7.ActiveMQBlobMessageMarshaller;
import org.apache.activemq.openwire.v7.ActiveMQBytesMessageMarshaller;
import org.apache.activemq.openwire.v7.ActiveMQMapMessageMarshaller;
import org.apache.activemq.openwire.v7.ActiveMQMessageMarshaller;
import org.apache.activemq.openwire.v7.ActiveMQObjectMessageMarshaller;
import org.apache.activemq.openwire.v7.ActiveMQQueueMarshaller;
import org.apache.activemq.openwire.v7.ActiveMQStreamMessageMarshaller;
import org.apache.activemq.openwire.v7.ActiveMQTempQueueMarshaller;
import org.apache.activemq.openwire.v7.ActiveMQTempTopicMarshaller;
import org.apache.activemq.openwire.v7.ActiveMQTextMessageMarshaller;
import org.apache.activemq.openwire.v7.ActiveMQTopicMarshaller;
import org.apache.activemq.openwire.v7.BrokerIdMarshaller;
import org.apache.activemq.openwire.v7.BrokerInfoMarshaller;
import org.apache.activemq.openwire.v7.ConnectionControlMarshaller;
import org.apache.activemq.openwire.v7.ConnectionErrorMarshaller;
import org.apache.activemq.openwire.v7.ConnectionIdMarshaller;
import org.apache.activemq.openwire.v7.ConnectionInfoMarshaller;
import org.apache.activemq.openwire.v7.ConsumerControlMarshaller;
import org.apache.activemq.openwire.v7.ConsumerIdMarshaller;
import org.apache.activemq.openwire.v7.ConsumerInfoMarshaller;
import org.apache.activemq.openwire.v7.ControlCommandMarshaller;
import org.apache.activemq.openwire.v7.DataArrayResponseMarshaller;
import org.apache.activemq.openwire.v7.DataResponseMarshaller;
import org.apache.activemq.openwire.v7.DestinationInfoMarshaller;
import org.apache.activemq.openwire.v7.DiscoveryEventMarshaller;
import org.apache.activemq.openwire.v7.ExceptionResponseMarshaller;
import org.apache.activemq.openwire.v7.FlushCommandMarshaller;
import org.apache.activemq.openwire.v7.IntegerResponseMarshaller;
import org.apache.activemq.openwire.v7.JournalQueueAckMarshaller;
import org.apache.activemq.openwire.v7.JournalTopicAckMarshaller;
import org.apache.activemq.openwire.v7.JournalTraceMarshaller;
import org.apache.activemq.openwire.v7.JournalTransactionMarshaller;
import org.apache.activemq.openwire.v7.KeepAliveInfoMarshaller;
import org.apache.activemq.openwire.v7.LastPartialCommandMarshaller;
import org.apache.activemq.openwire.v7.LocalTransactionIdMarshaller;
import org.apache.activemq.openwire.v7.MessageAckMarshaller;
import org.apache.activemq.openwire.v7.MessageDispatchMarshaller;
import org.apache.activemq.openwire.v7.MessageDispatchNotificationMarshaller;
import org.apache.activemq.openwire.v7.MessageIdMarshaller;
import org.apache.activemq.openwire.v7.MessagePullMarshaller;
import org.apache.activemq.openwire.v7.NetworkBridgeFilterMarshaller;
import org.apache.activemq.openwire.v7.PartialCommandMarshaller;
import org.apache.activemq.openwire.v7.ProducerAckMarshaller;
import org.apache.activemq.openwire.v7.ProducerIdMarshaller;
import org.apache.activemq.openwire.v7.ProducerInfoMarshaller;
import org.apache.activemq.openwire.v7.RemoveInfoMarshaller;
import org.apache.activemq.openwire.v7.RemoveSubscriptionInfoMarshaller;
import org.apache.activemq.openwire.v7.ReplayCommandMarshaller;
import org.apache.activemq.openwire.v7.ResponseMarshaller;
import org.apache.activemq.openwire.v7.SessionIdMarshaller;
import org.apache.activemq.openwire.v7.SessionInfoMarshaller;
import org.apache.activemq.openwire.v7.ShutdownInfoMarshaller;
import org.apache.activemq.openwire.v7.SubscriptionInfoMarshaller;
import org.apache.activemq.openwire.v7.TransactionInfoMarshaller;
import org.apache.activemq.openwire.v7.WireFormatInfoMarshaller;
import org.apache.activemq.openwire.v7.XATransactionIdMarshaller;

public class MarshallerFactory {
    private static final DataStreamMarshaller[] marshaller = new DataStreamMarshaller[256];

    private static void add(DataStreamMarshaller dsm) {
        MarshallerFactory.marshaller[dsm.getDataStructureType()] = dsm;
    }

    public static DataStreamMarshaller[] createMarshallerMap(OpenWireFormat wireFormat) {
        return marshaller;
    }

    static {
        MarshallerFactory.add(new ActiveMQBlobMessageMarshaller());
        MarshallerFactory.add(new ActiveMQBytesMessageMarshaller());
        MarshallerFactory.add(new ActiveMQMapMessageMarshaller());
        MarshallerFactory.add(new ActiveMQMessageMarshaller());
        MarshallerFactory.add(new ActiveMQObjectMessageMarshaller());
        MarshallerFactory.add(new ActiveMQQueueMarshaller());
        MarshallerFactory.add(new ActiveMQStreamMessageMarshaller());
        MarshallerFactory.add(new ActiveMQTempQueueMarshaller());
        MarshallerFactory.add(new ActiveMQTempTopicMarshaller());
        MarshallerFactory.add(new ActiveMQTextMessageMarshaller());
        MarshallerFactory.add(new ActiveMQTopicMarshaller());
        MarshallerFactory.add(new BrokerIdMarshaller());
        MarshallerFactory.add(new BrokerInfoMarshaller());
        MarshallerFactory.add(new ConnectionControlMarshaller());
        MarshallerFactory.add(new ConnectionErrorMarshaller());
        MarshallerFactory.add(new ConnectionIdMarshaller());
        MarshallerFactory.add(new ConnectionInfoMarshaller());
        MarshallerFactory.add(new ConsumerControlMarshaller());
        MarshallerFactory.add(new ConsumerIdMarshaller());
        MarshallerFactory.add(new ConsumerInfoMarshaller());
        MarshallerFactory.add(new ControlCommandMarshaller());
        MarshallerFactory.add(new DataArrayResponseMarshaller());
        MarshallerFactory.add(new DataResponseMarshaller());
        MarshallerFactory.add(new DestinationInfoMarshaller());
        MarshallerFactory.add(new DiscoveryEventMarshaller());
        MarshallerFactory.add(new ExceptionResponseMarshaller());
        MarshallerFactory.add(new FlushCommandMarshaller());
        MarshallerFactory.add(new IntegerResponseMarshaller());
        MarshallerFactory.add(new JournalQueueAckMarshaller());
        MarshallerFactory.add(new JournalTopicAckMarshaller());
        MarshallerFactory.add(new JournalTraceMarshaller());
        MarshallerFactory.add(new JournalTransactionMarshaller());
        MarshallerFactory.add(new KeepAliveInfoMarshaller());
        MarshallerFactory.add(new LastPartialCommandMarshaller());
        MarshallerFactory.add(new LocalTransactionIdMarshaller());
        MarshallerFactory.add(new MessageAckMarshaller());
        MarshallerFactory.add(new MessageDispatchMarshaller());
        MarshallerFactory.add(new MessageDispatchNotificationMarshaller());
        MarshallerFactory.add(new MessageIdMarshaller());
        MarshallerFactory.add(new MessagePullMarshaller());
        MarshallerFactory.add(new NetworkBridgeFilterMarshaller());
        MarshallerFactory.add(new PartialCommandMarshaller());
        MarshallerFactory.add(new ProducerAckMarshaller());
        MarshallerFactory.add(new ProducerIdMarshaller());
        MarshallerFactory.add(new ProducerInfoMarshaller());
        MarshallerFactory.add(new RemoveInfoMarshaller());
        MarshallerFactory.add(new RemoveSubscriptionInfoMarshaller());
        MarshallerFactory.add(new ReplayCommandMarshaller());
        MarshallerFactory.add(new ResponseMarshaller());
        MarshallerFactory.add(new SessionIdMarshaller());
        MarshallerFactory.add(new SessionInfoMarshaller());
        MarshallerFactory.add(new ShutdownInfoMarshaller());
        MarshallerFactory.add(new SubscriptionInfoMarshaller());
        MarshallerFactory.add(new TransactionInfoMarshaller());
        MarshallerFactory.add(new WireFormatInfoMarshaller());
        MarshallerFactory.add(new XATransactionIdMarshaller());
    }
}

