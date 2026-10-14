/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.openwire.v3;

import org.apache.activemq.openwire.DataStreamMarshaller;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.openwire.v3.ActiveMQBlobMessageMarshaller;
import org.apache.activemq.openwire.v3.ActiveMQBytesMessageMarshaller;
import org.apache.activemq.openwire.v3.ActiveMQMapMessageMarshaller;
import org.apache.activemq.openwire.v3.ActiveMQMessageMarshaller;
import org.apache.activemq.openwire.v3.ActiveMQObjectMessageMarshaller;
import org.apache.activemq.openwire.v3.ActiveMQQueueMarshaller;
import org.apache.activemq.openwire.v3.ActiveMQStreamMessageMarshaller;
import org.apache.activemq.openwire.v3.ActiveMQTempQueueMarshaller;
import org.apache.activemq.openwire.v3.ActiveMQTempTopicMarshaller;
import org.apache.activemq.openwire.v3.ActiveMQTextMessageMarshaller;
import org.apache.activemq.openwire.v3.ActiveMQTopicMarshaller;
import org.apache.activemq.openwire.v3.BrokerIdMarshaller;
import org.apache.activemq.openwire.v3.BrokerInfoMarshaller;
import org.apache.activemq.openwire.v3.ConnectionControlMarshaller;
import org.apache.activemq.openwire.v3.ConnectionErrorMarshaller;
import org.apache.activemq.openwire.v3.ConnectionIdMarshaller;
import org.apache.activemq.openwire.v3.ConnectionInfoMarshaller;
import org.apache.activemq.openwire.v3.ConsumerControlMarshaller;
import org.apache.activemq.openwire.v3.ConsumerIdMarshaller;
import org.apache.activemq.openwire.v3.ConsumerInfoMarshaller;
import org.apache.activemq.openwire.v3.ControlCommandMarshaller;
import org.apache.activemq.openwire.v3.DataArrayResponseMarshaller;
import org.apache.activemq.openwire.v3.DataResponseMarshaller;
import org.apache.activemq.openwire.v3.DestinationInfoMarshaller;
import org.apache.activemq.openwire.v3.DiscoveryEventMarshaller;
import org.apache.activemq.openwire.v3.ExceptionResponseMarshaller;
import org.apache.activemq.openwire.v3.FlushCommandMarshaller;
import org.apache.activemq.openwire.v3.IntegerResponseMarshaller;
import org.apache.activemq.openwire.v3.JournalQueueAckMarshaller;
import org.apache.activemq.openwire.v3.JournalTopicAckMarshaller;
import org.apache.activemq.openwire.v3.JournalTraceMarshaller;
import org.apache.activemq.openwire.v3.JournalTransactionMarshaller;
import org.apache.activemq.openwire.v3.KeepAliveInfoMarshaller;
import org.apache.activemq.openwire.v3.LastPartialCommandMarshaller;
import org.apache.activemq.openwire.v3.LocalTransactionIdMarshaller;
import org.apache.activemq.openwire.v3.MessageAckMarshaller;
import org.apache.activemq.openwire.v3.MessageDispatchMarshaller;
import org.apache.activemq.openwire.v3.MessageDispatchNotificationMarshaller;
import org.apache.activemq.openwire.v3.MessageIdMarshaller;
import org.apache.activemq.openwire.v3.MessagePullMarshaller;
import org.apache.activemq.openwire.v3.NetworkBridgeFilterMarshaller;
import org.apache.activemq.openwire.v3.PartialCommandMarshaller;
import org.apache.activemq.openwire.v3.ProducerAckMarshaller;
import org.apache.activemq.openwire.v3.ProducerIdMarshaller;
import org.apache.activemq.openwire.v3.ProducerInfoMarshaller;
import org.apache.activemq.openwire.v3.RemoveInfoMarshaller;
import org.apache.activemq.openwire.v3.RemoveSubscriptionInfoMarshaller;
import org.apache.activemq.openwire.v3.ReplayCommandMarshaller;
import org.apache.activemq.openwire.v3.ResponseMarshaller;
import org.apache.activemq.openwire.v3.SessionIdMarshaller;
import org.apache.activemq.openwire.v3.SessionInfoMarshaller;
import org.apache.activemq.openwire.v3.ShutdownInfoMarshaller;
import org.apache.activemq.openwire.v3.SubscriptionInfoMarshaller;
import org.apache.activemq.openwire.v3.TransactionInfoMarshaller;
import org.apache.activemq.openwire.v3.WireFormatInfoMarshaller;
import org.apache.activemq.openwire.v3.XATransactionIdMarshaller;

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

