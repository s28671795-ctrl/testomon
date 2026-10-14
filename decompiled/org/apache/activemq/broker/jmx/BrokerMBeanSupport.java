/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import java.util.Hashtable;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import org.apache.activemq.broker.region.policy.AbortSlowConsumerStrategy;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.ProducerInfo;
import org.apache.activemq.transaction.XATransaction;
import org.apache.activemq.util.JMXSupport;

public class BrokerMBeanSupport {
    public static ObjectName createBrokerObjectName(String jmxDomainName, String brokerName) throws MalformedObjectNameException {
        String objectNameStr = jmxDomainName + ":type=Broker,brokerName=";
        objectNameStr = objectNameStr + JMXSupport.encodeObjectNamePart(brokerName);
        return new ObjectName(objectNameStr);
    }

    public static ObjectName createDestinationName(ObjectName brokerObjectName, ActiveMQDestination destination) throws MalformedObjectNameException {
        return BrokerMBeanSupport.createDestinationName(brokerObjectName.toString(), destination);
    }

    public static ObjectName createDestinationName(String brokerObjectName, ActiveMQDestination destination) throws MalformedObjectNameException {
        String objectNameStr = brokerObjectName;
        objectNameStr = objectNameStr + BrokerMBeanSupport.createDestinationProperties(destination);
        return new ObjectName(objectNameStr);
    }

    public static ObjectName createDestinationName(String brokerObjectName, String type, String name) throws MalformedObjectNameException {
        String objectNameStr = brokerObjectName;
        objectNameStr = objectNameStr + BrokerMBeanSupport.createDestinationProperties(type, name);
        return new ObjectName(objectNameStr);
    }

    private static String createDestinationProperties(ActiveMQDestination destination) {
        String result = "";
        if (destination != null) {
            result = BrokerMBeanSupport.createDestinationProperties(destination.getDestinationTypeAsString(), destination.getPhysicalName());
        }
        return result;
    }

    private static String createDestinationProperties(String type, String name) {
        return ",destinationType=" + JMXSupport.encodeObjectNamePart(type) + ",destinationName=" + JMXSupport.encodeObjectNamePart(name);
    }

    public static ObjectName createSubscriptionName(ObjectName brokerObjectName, String connectionClientId, ConsumerInfo info) throws MalformedObjectNameException {
        return BrokerMBeanSupport.createSubscriptionName(brokerObjectName.toString(), connectionClientId, info);
    }

    public static ObjectName createSubscriptionName(String brokerObjectName, String connectionClientId, ConsumerInfo info) throws MalformedObjectNameException {
        String objectNameStr = brokerObjectName;
        objectNameStr = objectNameStr + BrokerMBeanSupport.createDestinationProperties(info.getDestination()) + ",endpoint=Consumer";
        objectNameStr = objectNameStr + ",clientId=" + JMXSupport.encodeObjectNamePart(connectionClientId);
        objectNameStr = objectNameStr + ",consumerId=";
        objectNameStr = info.isDurable() ? objectNameStr + "Durable(" + JMXSupport.encodeObjectNamePart(connectionClientId + ":" + info.getSubscriptionName()) + ")" : objectNameStr + JMXSupport.encodeObjectNamePart(info.getConsumerId().toString());
        return new ObjectName(objectNameStr);
    }

    public static ObjectName createProducerName(ObjectName brokerObjectName, String connectionClientId, ProducerInfo info) throws MalformedObjectNameException {
        return BrokerMBeanSupport.createProducerName(brokerObjectName.toString(), connectionClientId, info);
    }

    public static ObjectName createProducerName(String brokerObjectName, String connectionClientId, ProducerInfo producerInfo) throws MalformedObjectNameException {
        String objectNameStr = brokerObjectName;
        objectNameStr = producerInfo.getDestination() == null ? objectNameStr + ",endpoint=dynamicProducer" : objectNameStr + BrokerMBeanSupport.createDestinationProperties(producerInfo.getDestination()) + ",endpoint=Producer";
        objectNameStr = objectNameStr + ",clientId=" + JMXSupport.encodeObjectNamePart(connectionClientId);
        objectNameStr = objectNameStr + ",producerId=" + JMXSupport.encodeObjectNamePart(producerInfo.getProducerId().toString());
        return new ObjectName(objectNameStr);
    }

    public static ObjectName createXATransactionName(ObjectName brokerObjectName, XATransaction transaction) throws MalformedObjectNameException {
        return BrokerMBeanSupport.createXATransactionName(brokerObjectName.toString(), transaction);
    }

    public static ObjectName createXATransactionName(String brokerObjectName, XATransaction transaction) throws MalformedObjectNameException {
        String objectNameStr = brokerObjectName;
        objectNameStr = objectNameStr + ",transactionType=RecoveredXaTransaction";
        objectNameStr = objectNameStr + ",xid=" + JMXSupport.encodeObjectNamePart(transaction.getTransactionId().toString());
        return new ObjectName(objectNameStr);
    }

    public static ObjectName createLog4JConfigViewName(String brokerObjectName) throws MalformedObjectNameException {
        String objectNameStr = brokerObjectName;
        objectNameStr = objectNameStr + ",service=Log4JConfiguration";
        return new ObjectName(objectNameStr);
    }

    public static ObjectName createPersistenceAdapterName(String brokerObjectName, String name) throws MalformedObjectNameException {
        String objectNameStr = brokerObjectName;
        objectNameStr = objectNameStr + ",service=PersistenceAdapter";
        objectNameStr = objectNameStr + ",instanceName=" + JMXSupport.encodeObjectNamePart(name);
        return new ObjectName(objectNameStr);
    }

    public static ObjectName createAbortSlowConsumerStrategyName(ObjectName brokerObjectName, AbortSlowConsumerStrategy strategy) throws MalformedObjectNameException {
        return BrokerMBeanSupport.createAbortSlowConsumerStrategyName(brokerObjectName.toString(), strategy);
    }

    public static ObjectName createAbortSlowConsumerStrategyName(String brokerObjectName, AbortSlowConsumerStrategy strategy) throws MalformedObjectNameException {
        String objectNameStr = brokerObjectName;
        objectNameStr = objectNameStr + ",service=SlowConsumerStrategy,instanceName=" + JMXSupport.encodeObjectNamePart(strategy.getName());
        ObjectName objectName = new ObjectName(objectNameStr);
        return objectName;
    }

    public static ObjectName createConnectorName(ObjectName brokerObjectName, String type, String name) throws MalformedObjectNameException {
        return BrokerMBeanSupport.createConnectorName(brokerObjectName.toString(), type, name);
    }

    public static ObjectName createConnectorName(String brokerObjectName, String type, String name) throws MalformedObjectNameException {
        String objectNameStr = brokerObjectName;
        objectNameStr = objectNameStr + ",connector=" + type + ",connectorName=" + JMXSupport.encodeObjectNamePart(name);
        ObjectName objectName = new ObjectName(objectNameStr);
        return objectName;
    }

    public static ObjectName createNetworkConnectorName(ObjectName brokerObjectName, String type, String name) throws MalformedObjectNameException {
        return BrokerMBeanSupport.createNetworkConnectorName(brokerObjectName.toString(), type, name);
    }

    public static ObjectName createVirtualDestinationSelectorCacheName(ObjectName brokerObjectName, String type, String name) throws MalformedObjectNameException {
        String objectNameStr = brokerObjectName.toString();
        objectNameStr = objectNameStr + ",service=" + type + ",virtualDestinationSelectoCache=" + JMXSupport.encodeObjectNamePart(name);
        ObjectName objectName = new ObjectName(objectNameStr);
        return objectName;
    }

    public static ObjectName createNetworkConnectorName(String brokerObjectName, String type, String name) throws MalformedObjectNameException {
        String objectNameStr = brokerObjectName;
        objectNameStr = objectNameStr + ",connector=" + type + ",networkConnectorName=" + JMXSupport.encodeObjectNamePart(name);
        ObjectName objectName = new ObjectName(objectNameStr);
        return objectName;
    }

    public static ObjectName createConnectionViewByType(ObjectName connectorName, String type, String name) throws MalformedObjectNameException {
        String objectNameStr = connectorName.toString();
        objectNameStr = objectNameStr + ",connectionViewType=" + JMXSupport.encodeObjectNamePart(type);
        objectNameStr = objectNameStr + ",connectionName=" + JMXSupport.encodeObjectNamePart(name);
        return new ObjectName(objectNameStr);
    }

    public static ObjectName createNetworkBridgeObjectName(ObjectName connectorName, String remoteAddress) throws MalformedObjectNameException {
        Hashtable<String, String> map = new Hashtable<String, String>(connectorName.getKeyPropertyList());
        map.put("networkBridge", JMXSupport.encodeObjectNamePart(remoteAddress));
        return new ObjectName(connectorName.getDomain(), map);
    }

    public static ObjectName createNetworkOutBoundDestinationObjectName(ObjectName networkName, ActiveMQDestination destination) throws MalformedObjectNameException {
        String str = networkName.toString();
        str = str + ",direction=outbound" + BrokerMBeanSupport.createDestinationProperties(destination);
        return new ObjectName(str);
    }

    public static ObjectName createNetworkInBoundDestinationObjectName(ObjectName networkName, ActiveMQDestination destination) throws MalformedObjectNameException {
        String str = networkName.toString();
        str = str + ",direction=inbound" + BrokerMBeanSupport.createDestinationProperties(destination);
        return new ObjectName(str);
    }

    public static ObjectName createProxyConnectorName(ObjectName brokerObjectName, String type, String name) throws MalformedObjectNameException {
        return BrokerMBeanSupport.createProxyConnectorName(brokerObjectName.toString(), type, name);
    }

    public static ObjectName createProxyConnectorName(String brokerObjectName, String type, String name) throws MalformedObjectNameException {
        String objectNameStr = brokerObjectName;
        objectNameStr = objectNameStr + ",connector=" + type + ",proxyConnectorName=" + JMXSupport.encodeObjectNamePart(name);
        ObjectName objectName = new ObjectName(objectNameStr);
        return objectName;
    }

    public static ObjectName createJmsConnectorName(ObjectName brokerObjectName, String type, String name) throws MalformedObjectNameException {
        return BrokerMBeanSupport.createJmsConnectorName(brokerObjectName.toString(), type, name);
    }

    public static ObjectName createJmsConnectorName(String brokerObjectName, String type, String name) throws MalformedObjectNameException {
        String objectNameStr = brokerObjectName;
        objectNameStr = objectNameStr + ",connector=" + type + ",jmsConnectors=" + JMXSupport.encodeObjectNamePart(name);
        ObjectName objectName = new ObjectName(objectNameStr);
        return objectName;
    }

    public static ObjectName createJobSchedulerServiceName(ObjectName brokerObjectName) throws MalformedObjectNameException {
        return BrokerMBeanSupport.createJobSchedulerServiceName(brokerObjectName.toString());
    }

    public static ObjectName createJobSchedulerServiceName(String brokerObjectName) throws MalformedObjectNameException {
        String objectNameStr = brokerObjectName;
        objectNameStr = objectNameStr + ",service=JobScheduler,name=JMS";
        ObjectName objectName = new ObjectName(objectNameStr);
        return objectName;
    }

    public static ObjectName createHealthServiceName(ObjectName brokerObjectName) throws MalformedObjectNameException {
        return BrokerMBeanSupport.createHealthServiceName(brokerObjectName.toString());
    }

    public static ObjectName createHealthServiceName(String brokerObjectName) throws MalformedObjectNameException {
        String objectNameStr = brokerObjectName;
        objectNameStr = objectNameStr + ",service=Health";
        ObjectName objectName = new ObjectName(objectNameStr);
        return objectName;
    }

    public static ObjectName createConnectionQuery(String jmxDomainName, String brokerName, String name) throws MalformedObjectNameException {
        ObjectName brokerMBeanName = BrokerMBeanSupport.createBrokerObjectName(jmxDomainName, brokerName);
        return BrokerMBeanSupport.createConnectionQuery(brokerMBeanName.toString(), name);
    }

    public static ObjectName createConnectionQuery(String brokerMBeanName, String name) throws MalformedObjectNameException {
        return new ObjectName(brokerMBeanName + ",connector=*,connectorName=*,connectionViewType=*,connectionName=" + JMXSupport.encodeObjectNamePart(name));
    }

    public static ObjectName createQueueQuery(String brokerMBeanName) throws MalformedObjectNameException {
        return BrokerMBeanSupport.createConnectionQuery(brokerMBeanName, "*");
    }

    public static ObjectName createQueueQuery(String brokerMBeanName, String destinationName) throws MalformedObjectNameException {
        return new ObjectName(brokerMBeanName + ",destinationType=Queue,destinationName=" + JMXSupport.encodeObjectNamePart(destinationName));
    }

    public static ObjectName createTopicQuery(String brokerMBeanName) throws MalformedObjectNameException {
        return BrokerMBeanSupport.createConnectionQuery(brokerMBeanName, "*");
    }

    public static ObjectName createTopicQuery(String brokerMBeanName, String destinationName) throws MalformedObjectNameException {
        return new ObjectName(brokerMBeanName + ",destinationType=Topic,destinationName=" + JMXSupport.encodeObjectNamePart(destinationName));
    }

    public static ObjectName createConsumerQueury(String jmxDomainName, String clientId) throws MalformedObjectNameException {
        return BrokerMBeanSupport.createConsumerQueury(jmxDomainName, null, clientId);
    }

    public static ObjectName createConsumerQueury(String jmxDomainName, String brokerName, String clientId) throws MalformedObjectNameException {
        return new ObjectName(jmxDomainName + ":type=Broker,brokerName=" + (brokerName != null ? brokerName : "*") + ",destinationType=*,destinationName=*,endpoint=Consumer,clientId=" + JMXSupport.encodeObjectNamePart(clientId) + ",consumerId=*");
    }

    public static ObjectName createProducerQueury(String jmxDomainName, String clientId) throws MalformedObjectNameException {
        return BrokerMBeanSupport.createProducerQueury(jmxDomainName, null, clientId);
    }

    public static ObjectName createProducerQueury(String jmxDomainName, String brokerName, String clientId) throws MalformedObjectNameException {
        return new ObjectName(jmxDomainName + ":type=Broker,brokerName=" + (brokerName != null ? brokerName : "*") + ",destinationType=*,destinationName=*,endpoint=Producer,clientId=" + JMXSupport.encodeObjectNamePart(clientId) + ",producerId=*");
    }
}

