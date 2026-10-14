/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.orbutil.graph;

import java.util.Set;
import org.jboss.com.sun.corba.se.impl.orbutil.graph.Node;
import org.jboss.com.sun.corba.se.impl.orbutil.graph.NodeData;

public interface Graph
extends Set<Node> {
    public NodeData getNodeData(Node var1);

    public Set<Node> getRoots();
}

