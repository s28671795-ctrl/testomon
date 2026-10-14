/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.management.ObjectName;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.ProducerBrokerExchange;
import org.apache.activemq.broker.jmx.BrokerViewMBean;
import org.apache.activemq.broker.jmx.SubscriptionViewMBean;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.broker.view.DotFileInterceptorSupport;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.ProducerId;
import org.apache.activemq.command.ProducerInfo;
import org.apache.activemq.filter.DestinationMapNode;
import org.apache.activemq.filter.DestinationNode;

public class ConnectionDotFileInterceptor
extends DotFileInterceptorSupport {
    protected static final String ID_SEPARATOR = "_";
    private final boolean redrawOnRemove;
    private boolean clearProducerCacheAfterRender;
    private final String domain = "org.apache.activemq";
    private BrokerViewMBean brokerView;
    private final Map<ProducerId, ProducerInfo> producers = new HashMap<ProducerId, ProducerInfo>();
    private final Map<ProducerId, Set<ActiveMQDestination>> producerDestinations = new HashMap<ProducerId, Set<ActiveMQDestination>>();
    private final Object lock = new Object();

    public ConnectionDotFileInterceptor(Broker next, String file, boolean redrawOnRemove) throws IOException {
        super(next, file);
        this.redrawOnRemove = redrawOnRemove;
    }

    @Override
    public Subscription addConsumer(ConnectionContext context, ConsumerInfo info) throws Exception {
        Subscription answer = super.addConsumer(context, info);
        this.generateFile();
        return answer;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void addProducer(ConnectionContext context, ProducerInfo info) throws Exception {
        super.addProducer(context, info);
        ProducerId producerId = info.getProducerId();
        Object object = this.lock;
        synchronized (object) {
            this.producers.put(producerId, info);
        }
        this.generateFile();
    }

    @Override
    public void removeConsumer(ConnectionContext context, ConsumerInfo info) throws Exception {
        super.removeConsumer(context, info);
        if (this.redrawOnRemove) {
            this.generateFile();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void removeProducer(ConnectionContext context, ProducerInfo info) throws Exception {
        super.removeProducer(context, info);
        ProducerId producerId = info.getProducerId();
        if (this.redrawOnRemove) {
            Object object = this.lock;
            synchronized (object) {
                this.producerDestinations.remove(producerId);
                this.producers.remove(producerId);
            }
            this.generateFile();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void send(ProducerBrokerExchange producerExchange, Message messageSend) throws Exception {
        super.send(producerExchange, messageSend);
        ProducerId producerId = messageSend.getProducerId();
        ActiveMQDestination destination = messageSend.getDestination();
        Object object = this.lock;
        synchronized (object) {
            Set<ActiveMQDestination> destinations = this.producerDestinations.get(producerId);
            if (destinations == null) {
                destinations = new HashSet<ActiveMQDestination>();
            }
            this.producerDestinations.put(producerId, destinations);
            destinations.add(destination);
        }
    }

    @Override
    protected void generateFile(PrintWriter writer) throws Exception {
        writer.println("digraph \"ActiveMQ Connections\" {");
        writer.println();
        writer.println("label=\"ActiveMQ Broker: " + this.getBrokerView().getBrokerId() + "\"];");
        writer.println();
        writer.println("node [style = \"rounded,filled\", fillcolor = yellow, fontname=\"Helvetica-Oblique\"];");
        writer.println();
        HashMap<String, String> clients = new HashMap<String, String>();
        HashMap<String, String> queues = new HashMap<String, String>();
        HashMap<String, String> topics = new HashMap<String, String>();
        this.printSubscribers(writer, clients, queues, "queue_", this.getBrokerView().getQueueSubscribers());
        writer.println();
        this.printSubscribers(writer, clients, topics, "topic_", this.getBrokerView().getTopicSubscribers());
        writer.println();
        this.printProducers(writer, clients, queues, topics);
        writer.println();
        this.writeLabels(writer, "green", "Client: ", clients);
        writer.println();
        this.writeLabels(writer, "red", "Queue: ", queues);
        this.writeLabels(writer, "blue", "Topic: ", topics);
        writer.println("}");
        if (this.clearProducerCacheAfterRender) {
            this.producerDestinations.clear();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void printProducers(PrintWriter writer, Map<String, String> clients, Map<String, String> queues, Map<String, String> topics) {
        Object object = this.lock;
        synchronized (object) {
            for (Map.Entry<ProducerId, Set<ActiveMQDestination>> entry : this.producerDestinations.entrySet()) {
                ProducerId producerId = entry.getKey();
                Set<ActiveMQDestination> destinationSet = entry.getValue();
                this.printProducers(writer, clients, queues, topics, producerId, destinationSet);
            }
        }
    }

    protected void printProducers(PrintWriter writer, Map<String, String> clients, Map<String, String> queues, Map<String, String> topics, ProducerId producerId, Set destinationSet) {
        for (ActiveMQDestination destination : destinationSet) {
            String clientId = producerId.getConnectionId();
            String safeClientId = this.asID(clientId);
            clients.put(safeClientId, clientId);
            String physicalName = destination.getPhysicalName();
            String safeDestinationId = this.asID(physicalName);
            if (destination.isTopic()) {
                safeDestinationId = "topic_" + safeDestinationId;
                topics.put(safeDestinationId, physicalName);
            } else {
                safeDestinationId = "queue_" + safeDestinationId;
                queues.put(safeDestinationId, physicalName);
            }
            String safeProducerId = this.asID(producerId.toString());
            writer.print(safeClientId);
            writer.print(" -> ");
            writer.print(safeProducerId);
            writer.println(";");
            writer.print(safeProducerId);
            writer.print(" -> ");
            writer.print(safeDestinationId);
            writer.println(";");
            writer.print(safeProducerId);
            writer.print(" [label = \"");
            String label = "Producer: " + producerId.getSessionId() + "-" + producerId.getValue();
            writer.print(label);
            writer.println("\"];");
        }
    }

    protected void printSubscribers(PrintWriter writer, Map<String, String> clients, Map<String, String> destinations, String type, ObjectName[] subscribers) {
        for (int i = 0; i < subscribers.length; ++i) {
            ObjectName name = subscribers[i];
            SubscriptionViewMBean subscriber = (SubscriptionViewMBean)this.getBrokerService().getManagementContext().newProxyInstance(name, SubscriptionViewMBean.class, true);
            String clientId = subscriber.getClientId();
            String safeClientId = this.asID(clientId);
            clients.put(safeClientId, clientId);
            String destination = subscriber.getDestinationName();
            String safeDestinationId = type + this.asID(destination);
            destinations.put(safeDestinationId, destination);
            String selector = subscriber.getSelector();
            String subscriberId = safeClientId + ID_SEPARATOR + subscriber.getSessionId() + ID_SEPARATOR + subscriber.getSubscriptionId();
            writer.print(subscriberId);
            writer.print(" -> ");
            writer.print(safeClientId);
            writer.println(";");
            writer.print(safeDestinationId);
            writer.print(" -> ");
            writer.print(subscriberId);
            writer.println(";");
            writer.print(subscriberId);
            writer.print(" [label = \"");
            String label = "Subscription: " + subscriber.getSessionId() + "-" + subscriber.getSubscriptionId();
            if (selector != null && selector.length() > 0) {
                label = label + "\\nSelector: " + selector;
            }
            writer.print(label);
            writer.println("\"];");
        }
    }

    protected void writeLabels(PrintWriter writer, String color, String prefix, Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String id = entry.getKey();
            String label = entry.getValue();
            writer.print(id);
            writer.print(" [ fillcolor = ");
            writer.print(color);
            writer.print(", label = \"");
            writer.print(prefix);
            writer.print(label);
            writer.println("\"];");
        }
    }

    protected String asID(String name) {
        StringBuffer buffer = new StringBuffer();
        int size = name.length();
        for (int i = 0; i < size; ++i) {
            char ch = name.charAt(i);
            if (Character.isLetterOrDigit(ch) || ch == '_') {
                buffer.append(ch);
                continue;
            }
            buffer.append('_');
        }
        return buffer.toString();
    }

    protected void printNodes(PrintWriter writer, DestinationMapNode node, String prefix) {
        String path = this.getPath(node);
        writer.print("  ");
        writer.print(prefix);
        writer.print(ID_SEPARATOR);
        writer.print(path);
        String label = path;
        if (prefix.equals("topic")) {
            label = "Topics";
        } else if (prefix.equals("queue")) {
            label = "Queues";
        }
        writer.print("[ label = \"");
        writer.print(label);
        writer.println("\" ];");
        Collection<DestinationNode> children = node.getChildren();
        for (DestinationMapNode destinationMapNode : children) {
            this.printNodes(writer, destinationMapNode, prefix + ID_SEPARATOR + path);
        }
    }

    protected void printNodeLinks(PrintWriter writer, DestinationMapNode node, String prefix) {
        String path = this.getPath(node);
        Collection<DestinationNode> children = node.getChildren();
        for (DestinationMapNode destinationMapNode : children) {
            writer.print("  ");
            writer.print(prefix);
            writer.print(ID_SEPARATOR);
            writer.print(path);
            writer.print(" -> ");
            writer.print(prefix);
            writer.print(ID_SEPARATOR);
            writer.print(path);
            writer.print(ID_SEPARATOR);
            writer.print(this.getPath(destinationMapNode));
            writer.println(";");
            this.printNodeLinks(writer, destinationMapNode, prefix + ID_SEPARATOR + path);
        }
    }

    protected String getPath(DestinationMapNode node) {
        String path = node.getPath();
        if (path.equals("*")) {
            return "root";
        }
        return path;
    }

    BrokerViewMBean getBrokerView() throws Exception {
        if (this.brokerView == null) {
            ObjectName brokerName = this.getBrokerService().getBrokerObjectName();
            this.brokerView = (BrokerViewMBean)this.getBrokerService().getManagementContext().newProxyInstance(brokerName, BrokerViewMBean.class, true);
        }
        return this.brokerView;
    }
}

