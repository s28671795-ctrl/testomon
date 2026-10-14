/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.stream;

import org.simpleframework.xml.stream.Mode;
import org.simpleframework.xml.stream.NamespaceMap;
import org.simpleframework.xml.stream.NodeMap;
import org.simpleframework.xml.stream.OutputNode;
import org.simpleframework.xml.stream.OutputNodeMap;

class OutputAttribute
implements OutputNode {
    private NamespaceMap scope;
    private OutputNode source;
    private String reference;
    private String name;
    private String value;

    public OutputAttribute(OutputNode source2, String name, String value) {
        this.scope = source2.getNamespaces();
        this.source = source2;
        this.value = value;
        this.name = name;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public OutputNode getParent() {
        return this.source;
    }

    @Override
    public NodeMap<OutputNode> getAttributes() {
        return new OutputNodeMap(this);
    }

    @Override
    public OutputNode getChild(String name) {
        return null;
    }

    @Override
    public String getComment() {
        return null;
    }

    @Override
    public void setComment(String comment) {
    }

    @Override
    public Mode getMode() {
        return Mode.INHERIT;
    }

    @Override
    public void setMode(Mode mode) {
    }

    @Override
    public void setData(boolean data) {
    }

    @Override
    public String getPrefix() {
        return this.scope.getPrefix(this.reference);
    }

    @Override
    public String getPrefix(boolean inherit) {
        return this.scope.getPrefix(this.reference);
    }

    @Override
    public String getReference() {
        return this.reference;
    }

    @Override
    public void setReference(String reference) {
        this.reference = reference;
    }

    @Override
    public NamespaceMap getNamespaces() {
        return this.scope;
    }

    @Override
    public OutputNode setAttribute(String name, String value) {
        return null;
    }

    @Override
    public void remove() {
    }

    @Override
    public void commit() {
    }

    @Override
    public boolean isRoot() {
        return false;
    }

    @Override
    public boolean isCommitted() {
        return true;
    }

    public String toString() {
        return String.format("attribute %s='%s'", this.name, this.value);
    }
}

