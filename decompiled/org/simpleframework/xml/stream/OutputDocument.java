/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.stream;

import org.simpleframework.xml.stream.Mode;
import org.simpleframework.xml.stream.NamespaceMap;
import org.simpleframework.xml.stream.NodeException;
import org.simpleframework.xml.stream.NodeMap;
import org.simpleframework.xml.stream.NodeWriter;
import org.simpleframework.xml.stream.OutputNode;
import org.simpleframework.xml.stream.OutputNodeMap;
import org.simpleframework.xml.stream.OutputStack;

class OutputDocument
implements OutputNode {
    private OutputNodeMap table = new OutputNodeMap(this);
    private NodeWriter writer;
    private OutputStack stack;
    private String reference;
    private String comment;
    private String value;
    private String name;
    private Mode mode = Mode.INHERIT;

    public OutputDocument(NodeWriter writer, OutputStack stack) {
        this.writer = writer;
        this.stack = stack;
    }

    @Override
    public String getPrefix() {
        return null;
    }

    @Override
    public String getPrefix(boolean inherit) {
        return null;
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
        return null;
    }

    @Override
    public OutputNode getParent() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getValue() throws Exception {
        return this.value;
    }

    @Override
    public String getComment() {
        return this.comment;
    }

    @Override
    public boolean isRoot() {
        return true;
    }

    @Override
    public Mode getMode() {
        return this.mode;
    }

    @Override
    public void setMode(Mode mode) {
        this.mode = mode;
    }

    @Override
    public OutputNode setAttribute(String name, String value) {
        return this.table.put(name, value);
    }

    @Override
    public NodeMap<OutputNode> getAttributes() {
        return this.table;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public void setData(boolean data) {
        this.mode = data ? Mode.DATA : Mode.ESCAPE;
    }

    @Override
    public OutputNode getChild(String name) throws Exception {
        return this.writer.writeElement(this, name);
    }

    @Override
    public void remove() throws Exception {
        if (this.stack.isEmpty()) {
            throw new NodeException("No root node");
        }
        this.stack.bottom().remove();
    }

    @Override
    public void commit() throws Exception {
        if (this.stack.isEmpty()) {
            throw new NodeException("No root node");
        }
        this.stack.bottom().commit();
    }

    @Override
    public boolean isCommitted() {
        return this.stack.isEmpty();
    }
}

