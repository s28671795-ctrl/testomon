/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.view;

import java.io.PrintWriter;
import java.util.Collection;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.view.DotFileInterceptorSupport;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.filter.DestinationMap;
import org.apache.activemq.filter.DestinationMapNode;
import org.apache.activemq.filter.DestinationNode;

public class DestinationDotFileInterceptor
extends DotFileInterceptorSupport {
    protected static final String ID_SEPARATOR = "_";

    public DestinationDotFileInterceptor(Broker next, String file) {
        super(next, file);
    }

    @Override
    public Destination addDestination(ConnectionContext context, ActiveMQDestination destination, boolean create) throws Exception {
        Destination answer = super.addDestination(context, destination, create);
        this.generateFile();
        return answer;
    }

    @Override
    public void removeDestination(ConnectionContext context, ActiveMQDestination destination, long timeout2) throws Exception {
        super.removeDestination(context, destination, timeout2);
        this.generateFile();
    }

    @Override
    protected void generateFile(PrintWriter writer) throws Exception {
        ActiveMQDestination[] destinations = this.getDestinations();
        DestinationMap map = new DestinationMap();
        for (int i = 0; i < destinations.length; ++i) {
            ActiveMQDestination destination = destinations[i];
            map.put(destination, destination);
        }
        writer.println("digraph \"ActiveMQ Destinations\" {");
        writer.println();
        writer.println("node [style = \"rounded,filled\", fontname=\"Helvetica-Oblique\"];");
        writer.println();
        writer.println("topic_root [fillcolor = deepskyblue, label = \"Topics\" ];");
        writer.println("queue_root [fillcolor = deepskyblue, label = \"Queues\" ];");
        writer.println();
        writer.println("subgraph queues {");
        writer.println("  node [fillcolor=red];     ");
        writer.println("  label = \"Queues\"");
        writer.println();
        this.printNodeLinks(writer, map.getQueueRootNode(), "queue");
        writer.println("}");
        writer.println();
        writer.println("subgraph temp queues {");
        writer.println("  node [fillcolor=red];     ");
        writer.println("  label = \"TempQueues\"");
        writer.println();
        this.printNodeLinks(writer, map.getTempQueueRootNode(), "tempqueue");
        writer.println("}");
        writer.println();
        writer.println("subgraph topics {");
        writer.println("  node [fillcolor=green];     ");
        writer.println("  label = \"Topics\"");
        writer.println();
        this.printNodeLinks(writer, map.getTopicRootNode(), "topic");
        writer.println("}");
        writer.println();
        writer.println("subgraph temp topics {");
        writer.println("  node [fillcolor=green];     ");
        writer.println("  label = \"TempTopics\"");
        writer.println();
        this.printNodeLinks(writer, map.getTempTopicRootNode(), "temptopic");
        writer.println("}");
        writer.println();
        this.printNodes(writer, map.getQueueRootNode(), "queue");
        writer.println();
        this.printNodes(writer, map.getTempQueueRootNode(), "tempqueue");
        writer.println();
        this.printNodes(writer, map.getTopicRootNode(), "topic");
        writer.println();
        this.printNodes(writer, map.getTempTopicRootNode(), "temptopic");
        writer.println();
        writer.println("}");
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
}

