/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.openwire.v10;

import org.apache.activemq.openwire.DataStreamMarshaller;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.openwire.v10.ActiveMQBlobMessageMarshaller;
import org.apache.activemq.openwire.v10.ActiveMQBytesMessageMarshaller;
import org.apache.activemq.openwire.v10.ActiveMQMapMessageMarshaller;
import org.apache.activemq.openwire.v10.ActiveMQMessageMarshaller;
import org.apache.activemq.openwire.v10.ActiveMQObjectMessageMarshaller;
import org.apache.activemq.openwire.v10.ActiveMQQueueMarshaller;
import org.apache.activemq.openwire.v10.ActiveMQStreamMessageMarshaller;
import org.apache.activemq.openwire.v10.ActiveMQTempQueueMarshaller;
import org.apache.activemq.openwire.v10.ActiveMQTempTopicMarshaller;
import org.apache.activemq.openwire.v10.ActiveMQTextMessageMarshaller;
import org.apache.activemq.openwire.v10.ActiveMQTopicMarshaller;
import org.apache.activemq.openwire.v10.BrokerIdMarshaller;
import org.apache.activemq.openwire.v10.BrokerInfoMarshaller;
import org.apache.activemq.openwire.v10.ConnectionControlMarshaller;
import org.apache.activemq.openwire.v10.ConnectionErrorMarshaller;
import org.apache.activemq.openwire.v10.ConnectionIdMarshaller;
import org.apache.activemq.openwire.v10.ConnectionInfoMarshaller;
import org.apache.activemq.openwire.v10.ConsumerControlMarshaller;
import org.apache.activemq.openwire.v10.ConsumerIdMarshaller;
import org.apache.activemq.openwire.v10.ConsumerInfoMarshaller;
import org.apache.activemq.openwire.v10.ControlCommandMarshaller;
import org.apache.activemq.openwire.v10.DataArrayResponseMarshaller;
import org.apache.activemq.openwire.v10.DataResponseMarshaller;
import org.apache.activemq.openwire.v10.DestinationInfoMarshaller;
import org.apache.activemq.openwire.v10.DiscoveryEventMarshaller;
import org.apache.activemq.openwire.v10.ExceptionResponseMarshaller;
import org.apache.activemq.openwire.v10.FlushCommandMarshaller;
import org.apache.activemq.openwire.v10.IntegerResponseMarshaller;
import org.apache.activemq.openwire.v10.JournalQueueAckMarshaller;
import org.apache.activemq.openwire.v10.JournalTopicAckMarshaller;
import org.apache.activemq.openwire.v10.JournalTraceMarshaller;
import org.apache.activemq.openwire.v10.JournalTransactionMarshaller;
import org.apache.activemq.openwire.v10.KeepAliveInfoMarshaller;
import org.apache.activemq.openwire.v10.LastPartialCommandMarshaller;
import org.apache.activemq.openwire.v10.LocalTransactionIdMarshaller;
import org.apache.activemq.openwire.v10.MessageAckMarshaller;
import org.apache.activemq.openwire.v10.MessageDispatchMarshaller;
import org.apache.activemq.openwire.v10.MessageDispatchNotificationMarshaller;
import org.apache.activemq.openwire.v10.MessageIdMarshaller;
import org.apache.activemq.openwire.v10.MessagePullMarshaller;
import org.apache.activemq.openwire.v10.NetworkBridgeFilterMarshaller;
import org.apache.activemq.openwire.v10.PartialCommandMarshaller;
import org.apache.activemq.openwire.v10.ProducerAckMarshaller;
import org.apache.activemq.openwire.v10.ProducerIdMarshaller;
import org.apache.activemq.openwire.v10.ProducerInfoMarshaller;
import org.apache.activemq.openwire.v10.RemoveInfoMarshaller;
import org.apache.activemq.openwire.v10.RemoveSubscriptionInfoMarshaller;
import org.apache.activemq.openwire.v10.ReplayCommandMarshaller;
import org.apache.activemq.openwire.v10.ResponseMarshaller;
import org.apache.activemq.openwire.v10.SessionIdMarshaller;
import org.apache.activemq.openwire.v10.SessionInfoMarshaller;
import org.apache.activemq.openwire.v10.ShutdownInfoMarshaller;
import org.apache.activemq.openwire.v10.SubscriptionInfoMarshaller;
import org.apache.activemq.openwire.v10.TransactionInfoMarshaller;
import org.apache.activemq.openwire.v10.WireFormatInfoMarshaller;
import org.apache.activemq.openwire.v10.XATransactionIdMarshaller;

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

