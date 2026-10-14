/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.stream;

import java.util.Iterator;
import java.util.LinkedHashMap;
import org.simpleframework.xml.stream.Attribute;
import org.simpleframework.xml.stream.EventNode;
import org.simpleframework.xml.stream.InputAttribute;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.NodeMap;

class InputNodeMap
extends LinkedHashMap<String, InputNode>
implements NodeMap<InputNode> {
    private final InputNode source;

    protected InputNodeMap(InputNode source2) {
        this.source = source2;
    }

    public InputNodeMap(InputNode source2, EventNode element) {
        this.source = source2;
        this.build(element);
    }

    private void build(EventNode element) {
        for (Attribute entry : element) {
            InputAttribute value = new InputAttribute(this.source, entry);
            if (entry.isReserved()) continue;
            this.put(value.getName(), value);
        }
    }

    @Override
    public InputNode getNode() {
        return this.source;
    }

    @Override
    public String getName() {
        return this.source.getName();
    }

    @Override
    public InputNode put(String name, String value) {
        InputAttribute node = new InputAttribute(this.source, name, value);
        if (name != null) {
            this.put(name, node);
        }
        return node;
    }

    @Override
    public InputNode remove(String name) {
        return (InputNode)super.remove(name);
    }

    @Override
    public InputNode get(String name) {
        return (InputNode)super.get(name);
    }

    @Override
    public Iterator<String> iterator() {
        return this.keySet().iterator();
    }
}

