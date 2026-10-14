/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.openwire.v2;

import org.apache.activemq.openwire.DataStreamMarshaller;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.openwire.v2.ActiveMQBytesMessageMarshaller;
import org.apache.activemq.openwire.v2.ActiveMQMapMessageMarshaller;
import org.apache.activemq.openwire.v2.ActiveMQMessageMarshaller;
import org.apache.activemq.openwire.v2.ActiveMQObjectMessageMarshaller;
import org.apache.activemq.openwire.v2.ActiveMQQueueMarshaller;
import org.apache.activemq.openwire.v2.ActiveMQStreamMessageMarshaller;
import org.apache.activemq.openwire.v2.ActiveMQTempQueueMarshaller;
import org.apache.activemq.openwire.v2.ActiveMQTempTopicMarshaller;
import org.apache.activemq.openwire.v2.ActiveMQTextMessageMarshaller;
import org.apache.activemq.openwire.v2.ActiveMQTopicMarshaller;
import org.apache.activemq.openwire.v2.BrokerIdMarshaller;
import org.apache.activemq.openwire.v2.BrokerInfoMarshaller;
import org.apache.activemq.openwire.v2.ConnectionControlMarshaller;
import org.apache.activemq.openwire.v2.ConnectionErrorMarshaller;
import org.apache.activemq.openwire.v2.ConnectionIdMarshaller;
import org.apache.activemq.openwire.v2.ConnectionInfoMarshaller;
import org.apache.activemq.openwire.v2.ConsumerControlMarshaller;
import org.apache.activemq.openwire.v2.ConsumerIdMarshaller;
import org.apache.activemq.openwire.v2.ConsumerInfoMarshaller;
import org.apache.activemq.openwire.v2.ControlCommandMarshaller;
import org.apache.activemq.openwire.v2.DataArrayResponseMarshaller;
import org.apache.activemq.openwire.v2.DataResponseMarshaller;
import org.apache.activemq.openwire.v2.DestinationInfoMarshaller;
import org.apache.activemq.openwire.v2.DiscoveryEventMarshaller;
import org.apache.activemq.openwire.v2.ExceptionResponseMarshaller;
import org.apache.activemq.openwire.v2.FlushCommandMarshaller;
import org.apache.activemq.openwire.v2.IntegerResponseMarshaller;
import org.apache.activemq.openwire.v2.JournalQueueAckMarshaller;
import org.apache.activemq.openwire.v2.JournalTopicAckMarshaller;
import org.apache.activemq.openwire.v2.JournalTraceMarshaller;
import org.apache.activemq.openwire.v2.JournalTransactionMarshaller;
import org.apache.activemq.openwire.v2.KeepAliveInfoMarshaller;
import org.apache.activemq.openwire.v2.LastPartialCommandMarshaller;
import org.apache.activemq.openwire.v2.LocalTransactionIdMarshaller;
import org.apache.activemq.openwire.v2.MessageAckMarshaller;
import org.apache.activemq.openwire.v2.MessageDispatchMarshaller;
import org.apache.activemq.openwire.v2.MessageDispatchNotificationMarshaller;
import org.apache.activemq.openwire.v2.MessageIdMarshaller;
import org.apache.activemq.openwire.v2.MessagePullMarshaller;
import org.apache.activemq.openwire.v2.NetworkBridgeFilterMarshaller;
import org.apache.activemq.openwire.v2.PartialCommandMarshaller;
import org.apache.activemq.openwire.v2.ProducerIdMarshaller;
import org.apache.activemq.openwire.v2.ProducerInfoMarshaller;
import org.apache.activemq.openwire.v2.RemoveInfoMarshaller;
import org.apache.activemq.openwire.v2.RemoveSubscriptionInfoMarshaller;
import org.apache.activemq.openwire.v2.ReplayCommandMarshaller;
import org.apache.activemq.openwire.v2.ResponseMarshaller;
import org.apache.activemq.openwire.v2.SessionIdMarshaller;
import org.apache.activemq.openwire.v2.SessionInfoMarshaller;
import org.apache.activemq.openwire.v2.ShutdownInfoMarshaller;
import org.apache.activemq.openwire.v2.SubscriptionInfoMarshaller;
import org.apache.activemq.openwire.v2.TransactionInfoMarshaller;
import org.apache.activemq.openwire.v2.WireFormatInfoMarshaller;
import org.apache.activemq.openwire.v2.XATransactionIdMarshaller;

public final class MarshallerFactory {
    private static final DataStreamMarshaller[] MARSHALLER = new DataStreamMarshaller[256];

    private MarshallerFactory() {
    }

    private static void add(DataStreamMarshaller dsm) {
        MarshallerFactory.MARSHALLER[dsm.getDataStructureType()] = dsm;
    }

    public static DataStreamMarshaller[] createMarshallerMap(OpenWireFormat wireFormat) {
        return MARSHALLER;
    }

    static {
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

