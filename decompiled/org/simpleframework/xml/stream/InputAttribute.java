/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.stream;

import org.simpleframework.xml.stream.Attribute;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.InputNodeMap;
import org.simpleframework.xml.stream.NodeMap;
import org.simpleframework.xml.stream.Position;

class InputAttribute
implements InputNode {
    private InputNode parent;
    private String reference;
    private String prefix;
    private String name;
    private String value;
    private Object source;

    public InputAttribute(InputNode parent, String name, String value) {
        this.parent = parent;
        this.value = value;
        this.name = name;
    }

    public InputAttribute(InputNode parent, Attribute attribute) {
        this.reference = attribute.getReference();
        this.prefix = attribute.getPrefix();
        this.source = attribute.getSource();
        this.value = attribute.getValue();
        this.name = attribute.getName();
        this.parent = parent;
    }

    @Override
    public Object getSource() {
        return this.source;
    }

    @Override
    public InputNode getParent() {
        return this.parent;
    }

    @Override
    public Position getPosition() {
        return this.parent.getPosition();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getPrefix() {
        return this.prefix;
    }

    @Override
    public String getReference() {
        return this.reference;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public boolean isRoot() {
        return false;
    }

    @Override
    public boolean isElement() {
        return false;
    }

    @Override
    public InputNode getAttribute(String name) {
        return null;
    }

    @Override
    public NodeMap<InputNode> getAttributes() {
        return new InputNodeMap(this);
    }

    @Override
    public InputNode getNext() {
        return null;
    }

    @Override
    public InputNode getNext(String name) {
        return null;
    }

    @Override
    public void skip() {
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    public String toString() {
        return String.format("attribute %s='%s'", this.name, this.value);
    }
}

