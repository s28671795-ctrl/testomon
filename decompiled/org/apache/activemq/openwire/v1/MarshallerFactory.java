/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.openwire.v1;

import org.apache.activemq.openwire.DataStreamMarshaller;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.openwire.v1.ActiveMQBytesMessageMarshaller;
import org.apache.activemq.openwire.v1.ActiveMQMapMessageMarshaller;
import org.apache.activemq.openwire.v1.ActiveMQMessageMarshaller;
import org.apache.activemq.openwire.v1.ActiveMQObjectMessageMarshaller;
import org.apache.activemq.openwire.v1.ActiveMQQueueMarshaller;
import org.apache.activemq.openwire.v1.ActiveMQStreamMessageMarshaller;
import org.apache.activemq.openwire.v1.ActiveMQTempQueueMarshaller;
import org.apache.activemq.openwire.v1.ActiveMQTempTopicMarshaller;
import org.apache.activemq.openwire.v1.ActiveMQTextMessageMarshaller;
import org.apache.activemq.openwire.v1.ActiveMQTopicMarshaller;
import org.apache.activemq.openwire.v1.BrokerIdMarshaller;
import org.apache.activemq.openwire.v1.BrokerInfoMarshaller;
import org.apache.activemq.openwire.v1.ConnectionControlMarshaller;
import org.apache.activemq.openwire.v1.ConnectionErrorMarshaller;
import org.apache.activemq.openwire.v1.ConnectionIdMarshaller;
import org.apache.activemq.openwire.v1.ConnectionInfoMarshaller;
import org.apache.activemq.openwire.v1.ConsumerControlMarshaller;
import org.apache.activemq.openwire.v1.ConsumerIdMarshaller;
import org.apache.activemq.openwire.v1.ConsumerInfoMarshaller;
import org.apache.activemq.openwire.v1.ControlCommandMarshaller;
import org.apache.activemq.openwire.v1.DataArrayResponseMarshaller;
import org.apache.activemq.openwire.v1.DataResponseMarshaller;
import org.apache.activemq.openwire.v1.DestinationInfoMarshaller;
import org.apache.activemq.openwire.v1.DiscoveryEventMarshaller;
import org.apache.activemq.openwire.v1.ExceptionResponseMarshaller;
import org.apache.activemq.openwire.v1.FlushCommandMarshaller;
import org.apache.activemq.openwire.v1.IntegerResponseMarshaller;
import org.apache.activemq.openwire.v1.JournalQueueAckMarshaller;
import org.apache.activemq.openwire.v1.JournalTopicAckMarshaller;
import org.apache.activemq.openwire.v1.JournalTraceMarshaller;
import org.apache.activemq.openwire.v1.JournalTransactionMarshaller;
import org.apache.activemq.openwire.v1.KeepAliveInfoMarshaller;
import org.apache.activemq.openwire.v1.LastPartialCommandMarshaller;
import org.apache.activemq.openwire.v1.LocalTransactionIdMarshaller;
import org.apache.activemq.openwire.v1.MessageAckMarshaller;
import org.apache.activemq.openwire.v1.MessageDispatchMarshaller;
import org.apache.activemq.openwire.v1.MessageDispatchNotificationMarshaller;
import org.apache.activemq.openwire.v1.MessageIdMarshaller;
import org.apache.activemq.openwire.v1.NetworkBridgeFilterMarshaller;
import org.apache.activemq.openwire.v1.PartialCommandMarshaller;
import org.apache.activemq.openwire.v1.ProducerIdMarshaller;
import org.apache.activemq.openwire.v1.ProducerInfoMarshaller;
import org.apache.activemq.openwire.v1.RemoveInfoMarshaller;
import org.apache.activemq.openwire.v1.RemoveSubscriptionInfoMarshaller;
import org.apache.activemq.openwire.v1.ReplayCommandMarshaller;
import org.apache.activemq.openwire.v1.ResponseMarshaller;
import org.apache.activemq.openwire.v1.SessionIdMarshaller;
import org.apache.activemq.openwire.v1.SessionInfoMarshaller;
import org.apache.activemq.openwire.v1.ShutdownInfoMarshaller;
import org.apache.activemq.openwire.v1.SubscriptionInfoMarshaller;
import org.apache.activemq.openwire.v1.TransactionInfoMarshaller;
import org.apache.activemq.openwire.v1.WireFormatInfoMarshaller;
import org.apache.activemq.openwire.v1.XATransactionIdMarshaller;

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
        MarshallerFactory.add(new LocalTransactionIdMarshaller());
        MarshallerFactory.add(new PartialCommandMarshaller());
        MarshallerFactory.add(new IntegerResponseMarshaller());
        MarshallerFactory.add(new ActiveMQQueueMarshaller());
        MarshallerFactory.add(new ActiveMQObjectMessageMarshaller());
        MarshallerFactory.add(new ConnectionIdMarshaller());
        MarshallerFactory.add(new ConnectionInfoMarshaller());
        MarshallerFactory.add(new ProducerInfoMarshaller());
        MarshallerFactory.add(new MessageDispatchNotificationMarshaller());
        MarshallerFactory.add(new SessionInfoMarshaller());
        MarshallerFactory.add(new TransactionInfoMarshaller());
        MarshallerFactory.add(new ActiveMQStreamMessageMarshaller());
        MarshallerFactory.add(new MessageAckMarshaller());
        MarshallerFactory.add(new ProducerIdMarshaller());
        MarshallerFactory.add(new MessageIdMarshaller());
        MarshallerFactory.add(new ActiveMQTempQueueMarshaller());
        MarshallerFactory.add(new RemoveSubscriptionInfoMarshaller());
        MarshallerFactory.add(new SessionIdMarshaller());
        MarshallerFactory.add(new DataArrayResponseMarshaller());
        MarshallerFactory.add(new JournalQueueAckMarshaller());
        MarshallerFactory.add(new ResponseMarshaller());
        MarshallerFactory.add(new ConnectionErrorMarshaller());
        MarshallerFactory.add(new ConsumerInfoMarshaller());
        MarshallerFactory.add(new XATransactionIdMarshaller());
        MarshallerFactory.add(new JournalTraceMarshaller());
        MarshallerFactory.add(new ConsumerIdMarshaller());
        MarshallerFactory.add(new ActiveMQTextMessageMarshaller());
        MarshallerFactory.add(new SubscriptionInfoMarshaller());
        MarshallerFactory.add(new JournalTransactionMarshaller());
        MarshallerFactory.add(new ControlCommandMarshaller());
        MarshallerFactory.add(new LastPartialCommandMarshaller());
        MarshallerFactory.add(new NetworkBridgeFilterMarshaller());
        MarshallerFactory.add(new ActiveMQBytesMessageMarshaller());
        MarshallerFactory.add(new WireFormatInfoMarshaller());
        MarshallerFactory.add(new ActiveMQTempTopicMarshaller());
        MarshallerFactory.add(new DiscoveryEventMarshaller());
        MarshallerFactory.add(new ReplayCommandMarshaller());
        MarshallerFactory.add(new ActiveMQTopicMarshaller());
        MarshallerFactory.add(new BrokerInfoMarshaller());
        MarshallerFactory.add(new DestinationInfoMarshaller());
        MarshallerFactory.add(new ShutdownInfoMarshaller());
        MarshallerFactory.add(new DataResponseMarshaller());
        MarshallerFactory.add(new ConnectionControlMarshaller());
        MarshallerFactory.add(new KeepAliveInfoMarshaller());
        MarshallerFactory.add(new FlushCommandMarshaller());
        MarshallerFactory.add(new ConsumerControlMarshaller());
        MarshallerFactory.add(new JournalTopicAckMarshaller());
        MarshallerFactory.add(new BrokerIdMarshaller());
        MarshallerFactory.add(new MessageDispatchMarshaller());
        MarshallerFactory.add(new ActiveMQMapMessageMarshaller());
        MarshallerFactory.add(new ActiveMQMessageMarshaller());
        MarshallerFactory.add(new RemoveInfoMarshaller());
        MarshallerFactory.add(new ExceptionResponseMarshaller());
    }
}

