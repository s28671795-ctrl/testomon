/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.stream;

import org.simpleframework.xml.stream.EventNode;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.InputNodeMap;
import org.simpleframework.xml.stream.InputPosition;
import org.simpleframework.xml.stream.NodeMap;
import org.simpleframework.xml.stream.NodeReader;
import org.simpleframework.xml.stream.Position;

class InputElement
implements InputNode {
    private final InputNodeMap map;
    private final NodeReader reader;
    private final InputNode parent;
    private final EventNode node;

    public InputElement(InputNode parent, NodeReader reader, EventNode node) {
        this.map = new InputNodeMap(this, node);
        this.reader = reader;
        this.parent = parent;
        this.node = node;
    }

    @Override
    public Object getSource() {
        return this.node.getSource();
    }

    @Override
    public InputNode getParent() {
        return this.parent;
    }

    @Override
    public Position getPosition() {
        return new InputPosition(this.node);
    }

    @Override
    public String getName() {
        return this.node.getName();
    }

    @Override
    public String getPrefix() {
        return this.node.getPrefix();
    }

    @Override
    public String getReference() {
        return this.node.getReference();
    }

    @Override
    public boolean isRoot() {
        return this.reader.isRoot(this);
    }

    @Override
    public boolean isElement() {
        return true;
    }

    @Override
    public InputNode getAttribute(String name) {
        return this.map.get(name);
    }

    @Override
    public NodeMap<InputNode> getAttributes() {
        return this.map;
    }

    @Override
    public String getValue() throws Exception {
        return this.reader.readValue(this);
    }

    @Override
    public InputNode getNext() throws Exception {
        return this.reader.readElement(this);
    }

    @Override
    public InputNode getNext(String name) throws Exception {
        return this.reader.readElement(this, name);
    }

    @Override
    public void skip() throws Exception {
        this.reader.skipElement(this);
    }

    @Override
    public boolean isEmpty() throws Exception {
        if (!this.map.isEmpty()) {
            return false;
        }
        return this.reader.isEmpty(this);
    }

    public String toString() {
        return String.format("element %s", this.getName());
    }
}

