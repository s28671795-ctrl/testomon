/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.stream;

import java.util.Iterator;
import java.util.LinkedHashMap;
import org.simpleframework.xml.stream.NodeMap;
import org.simpleframework.xml.stream.OutputAttribute;
import org.simpleframework.xml.stream.OutputNode;

class OutputNodeMap
extends LinkedHashMap<String, OutputNode>
implements NodeMap<OutputNode> {
    private final OutputNode source;

    public OutputNodeMap(OutputNode source2) {
        this.source = source2;
    }

    @Override
    public OutputNode getNode() {
        return this.source;
    }

    @Override
    public String getName() {
        return this.source.getName();
    }

    @Override
    public OutputNode put(String name, String value) {
        OutputAttribute node = new OutputAttribute(this.source, name, value);
        if (this.source != null) {
            this.put(name, node);
        }
        return node;
    }

    @Override
    public OutputNode remove(String name) {
        return (OutputNode)super.remove(name);
    }

    @Override
    public OutputNode get(String name) {
        return (OutputNode)super.get(name);
    }

    @Override
    public Iterator<String> iterator() {
        return this.keySet().iterator();
    }
}

