/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.orbutil.graph;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.jboss.com.sun.corba.se.impl.orbutil.graph.Graph;
import org.jboss.com.sun.corba.se.impl.orbutil.graph.Node;
import org.jboss.com.sun.corba.se.impl.orbutil.graph.NodeData;

public class GraphImpl
extends AbstractSet<Node>
implements Graph {
    private Map<Node, NodeData> nodeToData = new HashMap<Node, NodeData>();

    public GraphImpl() {
    }

    public GraphImpl(Collection<Node> coll) {
        this();
        this.addAll(coll);
    }

    @Override
    public boolean add(Node obj) {
        Node node = obj;
        boolean found = this.nodeToData.keySet().contains(obj);
        if (!found) {
            NodeData nd = new NodeData();
            this.nodeToData.put(node, nd);
        }
        return !found;
    }

    @Override
    public Iterator<Node> iterator() {
        return this.nodeToData.keySet().iterator();
    }

    @Override
    public int size() {
        return this.nodeToData.keySet().size();
    }

    @Override
    public NodeData getNodeData(Node node) {
        return this.nodeToData.get(node);
    }

    private void clearNodeData() {
        for (Map.Entry<Node, NodeData> entry : this.nodeToData.entrySet()) {
            NodeData nd = entry.getValue();
            nd.clear();
        }
    }

    void visitAll(NodeVisitor nv) {
        boolean done = false;
        do {
            done = true;
            Map.Entry[] entries = this.nodeToData.entrySet().toArray(new Map.Entry[0]);
            for (int ctr = 0; ctr < entries.length; ++ctr) {
                Map.Entry current = entries[ctr];
                Node node = (Node)current.getKey();
                NodeData nd = (NodeData)current.getValue();
                if (nd.isVisited()) continue;
                nd.visited();
                done = false;
                nv.visit(this, node, nd);
            }
        } while (!done);
    }

    private void markNonRoots() {
        this.visitAll(new NodeVisitor(){

            @Override
            public void visit(Graph graph, Node node, NodeData nd) {
                for (Node child : node.getChildren()) {
                    graph.add(child);
                    NodeData cnd = graph.getNodeData(child);
                    cnd.notRoot();
                }
            }
        });
    }

    private Set<Node> collectRootSet() {
        HashSet<Node> result = new HashSet<Node>();
        for (Map.Entry<Node, NodeData> entry : this.nodeToData.entrySet()) {
            Node node = entry.getKey();
            NodeData nd = entry.getValue();
            if (!nd.isRoot()) continue;
            result.add(node);
        }
        return result;
    }

    @Override
    public Set<Node> getRoots() {
        this.clearNodeData();
        this.markNonRoots();
        return this.collectRootSet();
    }

    static interface NodeVisitor {
        public void visit(Graph var1, Node var2, NodeData var3);
    }
}

